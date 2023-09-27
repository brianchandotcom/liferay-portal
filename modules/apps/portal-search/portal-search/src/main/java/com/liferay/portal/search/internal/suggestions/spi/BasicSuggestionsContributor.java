/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions.spi;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.rest.dto.v1_0.SuggestionsContributorConfiguration;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.spi.suggestions.SuggestionsContributor;
import com.liferay.portal.search.suggestions.Suggestion;
import com.liferay.portal.search.suggestions.SuggestionBuilder;
import com.liferay.portal.search.suggestions.SuggestionBuilderFactory;
import com.liferay.portal.search.suggestions.SuggestionsContributorResults;
import com.liferay.portal.search.suggestions.SuggestionsContributorResultsBuilderFactory;
import com.liferay.portal.search.web.constants.SearchResultsPortletKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.MutableRenderParameters;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = true, property = "search.suggestions.contributor.name=basic",
	service = SuggestionsContributor.class
)
public class BasicSuggestionsContributor implements SuggestionsContributor {

	@Override
	public SuggestionsContributorResults getSuggestionsContributorResults(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContext searchContext,
		SuggestionsContributorConfiguration
			suggestionsContributorConfiguration) {

		if (!_exceedsCharacterThreshold(
				(Map<String, Object>)
					suggestionsContributorConfiguration.getAttributes(),
				searchContext.getKeywords())) {

			return null;
		}

		SearchResponse searchResponse = _searcher.search(
			_getSearchRequest(
				searchContext,
				GetterUtil.getInteger(
					suggestionsContributorConfiguration.getSize(), 5)));

		SearchHits searchHits = searchResponse.getSearchHits();

		if (searchHits.getTotalHits() == 0) {
			return null;
		}

		return _toSuggestionsContributorResults(
			suggestionsContributorConfiguration.getDisplayGroupName(),
			liferayPortletRequest, liferayPortletResponse, searchContext,
			searchHits.getSearchHits());
	}

	private boolean _exceedsCharacterThreshold(
		Map<String, Object> attributes, String keywords) {

		int characterThreshold = _getCharacterThreshold(attributes);

		if (Validator.isBlank(keywords)) {
			if (characterThreshold == 0) {
				return true;
			}
		}
		else if (keywords.length() >= characterThreshold) {
			return true;
		}

		return false;
	}

	private String _getAssetURL(
		AssetRenderer<?> assetRenderer,
		AssetRendererFactory<?> assetRendererFactory, String entryClassName,
		long entryClassPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		try {
			PortletURL viewContentURL =
				PortletURLBuilder.createLiferayPortletURL(
					liferayPortletResponse,
					SearchResultsPortletKeys.SEARCH_RESULTS,
					PortletRequest.RENDER_PHASE
				).setRedirect(
					_portal.getCurrentURL(liferayPortletRequest)
				).setPortletMode(
					PortletMode.VIEW
				).setWindowState(
					WindowState.MAXIMIZED
				).buildPortletURL();

			MutableRenderParameters mutableRenderParameters =
				viewContentURL.getRenderParameters();

			mutableRenderParameters.setValue("mvcPath", "/view_content.jsp");

			AssetEntry assetEntry = _assetEntryLocalService.getEntry(
				entryClassName, entryClassPK);

			mutableRenderParameters.setValue(
				"assetEntryId", String.valueOf(assetEntry.getEntryId()));

			mutableRenderParameters.setValue(
				"type", assetRendererFactory.getType());

			String viewURL = null;

			if (assetRenderer != null) {
				viewURL = assetRenderer.getURLViewInContext(
					liferayPortletRequest, liferayPortletResponse,
					viewContentURL.toString());
			}

			if (Validator.isNull(viewURL)) {
				viewURL = viewContentURL.toString();
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return HttpComponentsUtil.setParameter(
				viewURL, "p_l_back_url", themeDisplay.getURLCurrent());
		}
		catch (Exception exception) {
			_log.error(
				StringBundler.concat(
					"Unable to get view URL for class ", entryClassName,
					" with primary key ", entryClassPK),
				exception);
		}

		return StringPool.BLANK;
	}

	private int _getCharacterThreshold(Map<String, Object> attributes) {
		if (attributes == null) {
			return _CHARACTER_THRESHOLD;
		}

		return MapUtil.getInteger(
			attributes, "characterThreshold", _CHARACTER_THRESHOLD);
	}

	private SearchRequest _getSearchRequest(
		SearchContext searchContext1, int size) {

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		searchRequestBuilder.withSearchContext(
			searchContext2 -> {
				searchContext2.setCompanyId(searchContext1.getCompanyId());
				searchContext2.setGroupIds(searchContext1.getGroupIds());
				searchContext2.setKeywords(searchContext1.getKeywords());
				searchContext2.setLocale(searchContext1.getLocale());
				searchContext2.setTimeZone(searchContext1.getTimeZone());
				searchContext2.setUserId(searchContext1.getUserId());
			});

		searchRequestBuilder.size(
			size
		).queryString(
			searchContext1.getKeywords()
		).from(
			0
		);

		return searchRequestBuilder.build();
	}

	private Suggestion _getSuggestion(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Locale locale,
		SearchHit searchHit) {

		Document document = searchHit.getDocument();

		String entryClassName = document.getString(Field.ENTRY_CLASS_NAME);

		SuggestionBuilder suggestionBuilder = _suggestionBuilderFactory.builder(
		).attribute(
			"fields",
			HashMapBuilder.<String, Object>put(
				Field.ENTRY_CLASS_NAME, entryClassName
			).build()
		).score(
			searchHit.getScore()
		);

		String text = null;

		try {
			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(entryClassName);

			if (assetRendererFactory == null) {
				return null;
			}

			long entryClassPK = document.getLong(Field.ENTRY_CLASS_PK);

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(entryClassPK);

			if (assetRenderer != null) {
				suggestionBuilder.attribute(
					"assetSearchSummary",
					assetRenderer.getSummary(
						liferayPortletRequest, liferayPortletResponse));
				suggestionBuilder.attribute(
					"assetURL",
					_getAssetURL(
						assetRenderer, assetRendererFactory, entryClassName,
						entryClassPK, liferayPortletRequest,
						liferayPortletResponse));

				text = assetRenderer.getTitle(locale);
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		if (text == null) {
			text = _getText(searchHit.getDocument(), locale);
		}

		return suggestionBuilder.text(
			text
		).build();
	}

	private String _getText(Document document, Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		String text = document.getString(
			StringBundler.concat(
				Field.TITLE, StringPool.UNDERLINE, languageId));

		if (Validator.isBlank(text)) {
			text = document.getString("localized_title_" + languageId);
		}

		if (Validator.isBlank(text)) {
			text = document.getString(
				StringBundler.concat(
					Field.NAME, StringPool.UNDERLINE, languageId));
		}

		if (Validator.isBlank(text)) {
			text = document.getString(Field.TITLE);
		}

		return text;
	}

	private SuggestionsContributorResults _toSuggestionsContributorResults(
		String displayGroupName, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContext searchContext, List<SearchHit> searchHits) {

		return _suggestionsContributorResultsBuilderFactory.builder(
		).displayGroupName(
			displayGroupName
		).suggestions(
			TransformUtil.transform(
				searchHits,
				searchHit -> _getSuggestion(
					liferayPortletRequest, liferayPortletResponse,
					searchContext.getLocale(), searchHit))
		).build();
	}

	private static final int _CHARACTER_THRESHOLD = 2;

	private static final Log _log = LogFactoryUtil.getLog(
		BasicSuggestionsContributor.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SuggestionBuilderFactory _suggestionBuilderFactory;

	@Reference
	private SuggestionsContributorResultsBuilderFactory
		_suggestionsContributorResultsBuilderFactory;

}