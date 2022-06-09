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
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
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
					"Blueprint ID not found in contributor configuration");
			}
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

		return _toSuggestionsContributorResults(
			attributes,
			suggestionsContributorConfiguration.getDisplayGroupName(),
			liferayPortletRequest, liferayPortletResponse, searchContext,
			searchHits.getSearchHits());
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

	private String _getAssetURL(
		AssetRenderer<?> assetRenderer,
		AssetRendererFactory<?> assetRendererFactory, String entryClassName,
		long entryClassPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Layout searchLayout) {

		try {
			if (searchLayout == null) {
				return StringPool.BLANK;
			}

			PortletURL viewContentURL =
				PortletURLBuilder.createLiferayPortletURL(
					liferayPortletResponse, searchLayout.getPlid(),
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

	private Map<String, Object> _getFields(
		Document document, String[] fields, Locale locale) {

		Map<String, Object> map = new HashMap<>();

		List<String> fieldList = Arrays.asList(fields);

		fieldList.forEach(
			additionalField -> {
				String fieldName = _replaceLanguageId(locale, additionalField);

				map.put(fieldName, document.getValue(fieldName));
			});

		return map;
	}

	private String[] _getNestedFieldValue(Document document, String fieldName) {
		String[] parts = StringUtil.split(fieldName, "\\.");

		Map<String, Field> nestedFieldMap =
			(Map<String, Field>)document.getValue(parts[0]);

		if (nestedFieldMap == null) {
			return null;
		}

		return GetterUtil.getStringValues(nestedFieldMap.get(parts[1]));
	}

	private SearchRequest _getSearchRequest(
		SearchContext searchContext1, int size, long sxpBlueprintId) {

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		searchRequestBuilder.withSearchContext(
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
		String[] fields, boolean includeAssetSearchSummary,
		boolean includeAssetURL, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContext searchContext, SearchHit searchHit, Layout searchLayout,
		String text, boolean useAssetTitle) {

		SuggestionBuilder suggestionBuilder = _suggestionBuilderFactory.builder(
		).score(
			searchHit.getScore()
		).text(
			text
		);

		Document document = searchHit.getDocument();

		if (fields.length > 0) {
			suggestionBuilder.attribute(
				"fields",
				_getFields(document, fields, searchContext.getLocale()));
		}

		if (includeAssetSearchSummary || includeAssetURL || useAssetTitle) {
			String entryClassName = document.getString(Field.ENTRY_CLASS_NAME);

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
					if (includeAssetSearchSummary) {
						suggestionBuilder.attribute(
							"assetSearchSummary",
							assetRenderer.getSearchSummary(
								searchContext.getLocale()));
					}

					if (includeAssetURL) {
						suggestionBuilder.attribute(
							"assetURL",
							_getAssetURL(
								assetRenderer, assetRendererFactory,
								entryClassName, entryClassPK,
								liferayPortletRequest, liferayPortletResponse,
								searchLayout));
					}

					if (useAssetTitle) {
						suggestionBuilder.text(
							assetRenderer.getTitle(searchContext.getLocale()));
					}
				}
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		return suggestionBuilder.build();
	}

	private List<String> _getTexts(Document document, String textField) {
		if (StringUtil.contains(textField, ".")) {
			return Arrays.asList(_getNestedFieldValue(document, textField));
		}

		return document.getStrings(textField);
	}

	private String _replaceLanguageId(Locale locale, String textField) {
		return StringUtil.replace(
			textField, "${language_id}", LocaleUtil.toLanguageId(locale));
	}

	private SuggestionsContributorResults _toSuggestionsContributorResults(
		Map<String, Object> attributes, String displayGroupName,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContext searchContext, List<SearchHit> searchHits) {

		String[] fields = GetterUtil.getStringValues(attributes.get("fields"));

		String fieldValueSeparator = MapUtil.getString(
			attributes, "fieldValueSeparator");

		boolean includeAssetSearchSummary = MapUtil.getBoolean(
			attributes, "includeAssetSearchSummary", true);

		boolean includeAssetURL = MapUtil.getBoolean(
			attributes, "includeAssetURL", true);

		String textField = MapUtil.getString(attributes, "textField");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout searchLayout = _fetchLayoutByFriendlyURL(
			themeDisplay.getScopeGroupId(),
			GetterUtil.getString(
				searchContext.getAttribute(
					"search.suggestions.destination.friendly.url")));

		List<Suggestion> suggestions = new ArrayList<>();

		searchHits.forEach(
			searchHit -> {
				Document document = searchHit.getDocument();

				if (!Validator.isBlank(textField)) {
					String field = _replaceLanguageId(
						searchContext.getLocale(), textField);

					List<String> texts = _getTexts(document, field);

					for (String text : texts) {
						if (!Validator.isBlank(fieldValueSeparator)) {
							String[] parts = StringUtil.split(
								text, fieldValueSeparator);

							for (String s : parts) {
								suggestions.add(
									_getSuggestion(
										fields, includeAssetSearchSummary,
										includeAssetURL, liferayPortletRequest,
										liferayPortletResponse, searchContext,
										searchHit, searchLayout, s, false));
							}
						}
						else {
							suggestions.add(
								_getSuggestion(
									fields, includeAssetSearchSummary,
									includeAssetURL, liferayPortletRequest,
									liferayPortletResponse, searchContext,
									searchHit, searchLayout, text, false));
						}
					}
				}
				else {
					suggestions.add(
						_getSuggestion(
							fields, includeAssetSearchSummary, includeAssetURL,
							liferayPortletRequest, liferayPortletResponse,
							searchContext, searchHit, searchLayout, null,
							true));
				}
			});

		return _suggestionsContributorResultsBuilderFactory.builder(
		).displayGroupName(
			displayGroupName
		).suggestions(
			suggestions
		).build();
	}

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