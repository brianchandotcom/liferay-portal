/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.suggestions.spi;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
	enabled = false, immediate = true,
	property = "search.suggestions.contributor.name=sxpBlueprint",
	service = SuggestionsContributor.class
)
public class SXPBlueprintSuggestionsContributor
	implements SuggestionsContributor {

	@Override
	public SuggestionsContributorResults getSuggestionsContributorResults(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContext searchContext,
		SuggestionsContributorConfiguration
			suggestionsContributorConfiguration) {

		Map<String, Object> attributes =
			(Map<String, Object>)
				suggestionsContributorConfiguration.getAttributes();

		if ((attributes == null) || !attributes.containsKey("sxpBlueprintId")) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Attributes do not contain search experiences blueprint " +
						"ID");
			}

			return null;
		}

		if (!_exceedsCharacterThreshold(
				(Map<String, Object>)attributes, searchContext.getKeywords())) {

			return null;
		}

		SearchResponse searchResponse = _searcher.search(
			_getSearchRequest(
				searchContext,
				GetterUtil.getInteger(
					suggestionsContributorConfiguration.getSize(), 5),
				MapUtil.getLong(attributes, "sxpBlueprintId")));

		SearchHits searchHits = searchResponse.getSearchHits();

		if (searchHits.getTotalHits() == 0) {
			return null;
		}

		return _suggestionsContributorResultsBuilderFactory.builder(
		).displayGroupName(
			suggestionsContributorConfiguration.getDisplayGroupName()
		).suggestions(
			_getSuggestions(
				attributes, liferayPortletRequest, liferayPortletResponse,
				searchContext, searchHits.getSearchHits())
		).build();
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

	private Layout _fetchLayoutByFriendlyURL(long groupId, String friendlyURL) {
		Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
			groupId, false, friendlyURL);

		if (layout != null) {
			return layout;
		}

		return _layoutLocalService.fetchLayoutByFriendlyURL(
			groupId, true, friendlyURL);
	}

	private AssetRenderer<?> _getAssetRenderer(
		AssetRendererFactory<?> assetRendererFactory, long entryClassPK) {

		try {
			return assetRendererFactory.getAssetRenderer(entryClassPK);
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return null;
		}
	}

	private String _getAssetURL(
		AssetRenderer<?> assetRenderer,
		AssetRendererFactory<?> assetRendererFactory, String entryClassName,
		long entryClassPK, Layout layout,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		try {
			if (layout == null) {
				return StringPool.BLANK;
			}

			String viewURL = null;

			PortletURL viewContentURL =
				PortletURLBuilder.createLiferayPortletURL(
					liferayPortletResponse, layout.getPlid(),
					SearchResultsPortletKeys.SEARCH_RESULTS,
					PortletRequest.RENDER_PHASE
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

	private Map<String, Object> _getFieldValues(
		Document document, List<String> fieldNames, Locale locale) {

		Map<String, Object> fieldValues = new HashMap<>();

		for (String fieldName : fieldNames) {
			fieldName = _replaceLanguageId(locale, fieldName);

			fieldValues.put(fieldName, document.getValue(fieldName));
		}

		return fieldValues;
	}

	private String[] _getNestedFieldValue(Document document, String fieldName) {
		String[] parts = StringUtil.split(fieldName, "\\.");

		Map<String, Field> nestedFields = (Map<String, Field>)document.getValue(
			parts[0]);

		if (nestedFields == null) {
			return null;
		}

		return GetterUtil.getStringValues(nestedFields.get(parts[1]));
	}

	private SearchRequest _getSearchRequest(
		SearchContext searchContext1, int size, long sxpBlueprintId) {

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		searchRequestBuilder.from(
			0
		).queryString(
			searchContext1.getKeywords()
		).size(
			size
		).withSearchContext(
			searchContext2 -> {
				searchContext2.setAttribute(
					"search.experiences.blueprint.id", sxpBlueprintId);
				searchContext2.setAttribute(
					"search.experiences.ip.address",
					GetterUtil.getString(
						searchContext1.getAttribute(
							"search.experiences.ip.address")));
				searchContext2.setAttribute(
					"search.experiences.scope.group.id",
					GetterUtil.getLong(
						searchContext1.getAttribute(
							"search.experiences.scope.group.id")));
				searchContext2.setCompanyId(searchContext1.getCompanyId());
				searchContext2.setGroupIds(searchContext1.getGroupIds());
				searchContext2.setKeywords(searchContext1.getKeywords());
				searchContext2.setLocale(searchContext1.getLocale());
				searchContext2.setTimeZone(searchContext1.getTimeZone());
				searchContext2.setUserId(searchContext1.getUserId());
			}
		);

		return searchRequestBuilder.build();
	}

	private Suggestion _getSuggestion(
		List<String> fieldNames, boolean includeAssetSearchSummary,
		boolean includeAssetURL, Layout layout,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContext searchContext, SearchHit searchHit, String text,
		boolean useAssetTitle) {

		SuggestionBuilder suggestionBuilder = _suggestionBuilderFactory.builder(
		).score(
			searchHit.getScore()
		).text(
			text
		);

		Document document = searchHit.getDocument();

		if (ListUtil.isNotEmpty(fieldNames)) {
			suggestionBuilder.attribute(
				"fields",
				_getFieldValues(
					document, fieldNames, searchContext.getLocale()));
		}

		if (!includeAssetSearchSummary && !includeAssetURL && !useAssetTitle) {
			return suggestionBuilder.build();
		}

		String entryClassName = document.getString(Field.ENTRY_CLASS_NAME);

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				entryClassName);

		if (assetRendererFactory == null) {
			return suggestionBuilder.build();
		}

		long entryClassPK = document.getLong(Field.ENTRY_CLASS_PK);

		AssetRenderer<?> assetRenderer = _getAssetRenderer(
			assetRendererFactory, entryClassPK);

		if (assetRenderer == null) {
			return suggestionBuilder.build();
		}

		if (includeAssetSearchSummary) {
			suggestionBuilder.attribute(
				"assetSearchSummary",
				assetRenderer.getSearchSummary(searchContext.getLocale()));
		}

		if (includeAssetURL) {
			suggestionBuilder.attribute(
				"assetURL",
				_getAssetURL(
					assetRenderer, assetRendererFactory, entryClassName,
					entryClassPK, layout, liferayPortletRequest,
					liferayPortletResponse));
		}

		if (useAssetTitle) {
			suggestionBuilder.text(
				assetRenderer.getTitle(searchContext.getLocale()));
		}

		return suggestionBuilder.build();
	}

	private List<Suggestion> _getSuggestions(
		Map<String, Object> attributes,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContext searchContext, List<SearchHit> searchHits) {

		List<Suggestion> suggestions = new ArrayList<>();

		List<String> fieldNames = (List<String>)attributes.get("fields");
		String fieldValueSeparator = MapUtil.getString(
			attributes, "fieldValueSeparator");
		boolean includeAssetSearchSummary = MapUtil.getBoolean(
			attributes, "includeAssetSearchSummary", true);
		boolean includeAssetURL = MapUtil.getBoolean(
			attributes, "includeAssetURL", true);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = _fetchLayoutByFriendlyURL(
			themeDisplay.getScopeGroupId(),
			GetterUtil.getString(
				searchContext.getAttribute(
					"search.suggestions.destination.friendly.url")));

		String textFieldName = MapUtil.getString(attributes, "textField");

		for (SearchHit searchHit : searchHits) {
			Document document = searchHit.getDocument();

			if (Validator.isBlank(textFieldName)) {
				suggestions.add(
					_getSuggestion(
						fieldNames, includeAssetSearchSummary, includeAssetURL,
						layout, liferayPortletRequest, liferayPortletResponse,
						searchContext, searchHit, StringPool.BLANK, true));

				continue;
			}

			List<String> texts = _getTexts(
				document,
				_replaceLanguageId(searchContext.getLocale(), textFieldName));

			for (String text : texts) {
				if (!Validator.isBlank(fieldValueSeparator)) {
					String[] parts = StringUtil.split(
						text, fieldValueSeparator);

					for (String part : parts) {
						suggestions.add(
							_getSuggestion(
								fieldNames, includeAssetSearchSummary,
								includeAssetURL, layout, liferayPortletRequest,
								liferayPortletResponse, searchContext,
								searchHit, part, false));
					}
				}
				else {
					suggestions.add(
						_getSuggestion(
							fieldNames, includeAssetSearchSummary,
							includeAssetURL, layout, liferayPortletRequest,
							liferayPortletResponse, searchContext, searchHit,
							text, false));
				}
			}
		}

		return suggestions;
	}

	private List<String> _getTexts(Document document, String fieldName) {
		if (StringUtil.contains(fieldName, ".")) {
			return Arrays.asList(_getNestedFieldValue(document, fieldName));
		}

		return document.getStrings(fieldName);
	}

	private String _replaceLanguageId(Locale locale, String fieldName) {
		return StringUtil.replace(
			fieldName, "${language_id}", LocaleUtil.toLanguageId(locale));
	}

	private static final int _CHARACTER_THRESHOLD = 2;

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintSuggestionsContributor.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

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