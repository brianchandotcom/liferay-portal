/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions.spi;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.internal.configuration.AsahIndividualsConfiguration;
import com.liferay.portal.search.internal.configuration.AsahSearchKeywordsConfiguration;
import com.liferay.portal.search.internal.web.cache.AsahIndividualsWebCacheItem;
import com.liferay.portal.search.rest.dto.v1_0.SuggestionsContributorConfiguration;
import com.liferay.portal.search.suggestions.Suggestion;
import com.liferay.portal.search.suggestions.SuggestionBuilderFactory;
import com.liferay.portal.search.suggestions.SuggestionsContributorResults;
import com.liferay.portal.search.suggestions.SuggestionsContributorResultsBuilderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseAsahIndividualsSuggestionsContributor {

	@Activate
	protected void activate(Map<String, Object> properties) {
		asahIndividualsConfiguration = ConfigurableUtil.createConfigurable(
			AsahIndividualsConfiguration.class, properties);
	}

	protected SuggestionsContributorResults getSuggestionsContributorResults(
		AnalyticsSettingsManager analyticsSettingsManager, String endPointUsage,
		String endPointName, SearchContext searchContext, String sort,
		SuggestionBuilderFactory suggestionBuilderFactory,
		SuggestionsContributorConfiguration suggestionsContributorConfiguration,
		SuggestionsContributorResultsBuilderFactory
			suggestionsContributorResultsBuilderFactory,
		long userId) {

		if (!_isEnabled(
				analyticsSettingsManager, searchContext.getCompanyId())) {

			return null;
		}

		AnalyticsConfiguration analyticsConfiguration =
			_getAnalyticsConfiguration(
				analyticsSettingsManager, searchContext.getCompanyId());

		if (analyticsConfiguration == null) {
			return null;
		}

		Map<String, Object> attributes =
			(Map<String, Object>)
				suggestionsContributorConfiguration.getAttributes();

		if (!_exceedsCharacterThreshold(
				attributes, searchContext.getKeywords())) {

			return null;
		}

		JSONArray jsonArray = JSONUtil.getValueAsJSONArray(
			AsahIndividualsWebCacheItem.get(
				analyticsConfiguration, asahIndividualsConfiguration,
				_getContentType(attributes), searchContext.getCompanyId(),
				_getDisplayLanguageId(attributes, searchContext.getLocale()),
				endPointName, endPointUsage, _getGroupId(searchContext),
				_getMinCounts(attributes), _getPage(attributes),
				_getRangeKey(attributes),
				GetterUtil.getInteger(
					suggestionsContributorConfiguration.getSize(), 5),
				sort, userId),
			"JSONObject/_embedded", "JSONArray/" + endPointUsage);

		if (jsonArray.length() == 0) {
			return null;
		}

		return suggestionsContributorResultsBuilderFactory.builder(
		).displayGroupName(
			suggestionsContributorConfiguration.getDisplayGroupName()
		).suggestions(
			_getSuggestions(
				endPointUsage, jsonArray, searchContext,
				suggestionBuilderFactory)
		).build();
	}

	protected volatile AsahIndividualsConfiguration
		asahIndividualsConfiguration;
	protected volatile AsahSearchKeywordsConfiguration
		asahSearchKeywordsConfiguration;

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

	private AnalyticsConfiguration _getAnalyticsConfiguration(
		AnalyticsSettingsManager analyticsSettingsManager, long companyId) {

		try {
			return analyticsSettingsManager.getAnalyticsConfiguration(
				companyId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		return null;
	}

	private int _getCharacterThreshold(Map<String, Object> attributes) {
		if (attributes == null) {
			return _CHARACTER_THRESHOLD;
		}

		return MapUtil.getInteger(
			attributes, "characterThreshold", _CHARACTER_THRESHOLD);
	}

	private String _getContentType(Map<String, Object> attributes) {

		if (attributes == null) {
			return StringPool.BLANK;
		}

		return MapUtil.getString(attributes, "contentType", StringPool.BLANK);
	}

	private String _getDisplayLanguageId(
		Map<String, Object> attributes, Locale locale) {

		if ((attributes == null) ||
			MapUtil.getBoolean(attributes, "matchDisplayLanguageId", true)) {

			return LanguageUtil.getBCP47LanguageId(locale);
		}

		return StringPool.BLANK;
	}

	private long _getGroupId(SearchContext searchContext) {
		long[] groupIds = searchContext.getGroupIds();

		if ((groupIds == null) || (groupIds.length == 0)) {
			return 0;
		}

		return groupIds[0];
	}

	private int _getMinCounts(Map<String, Object> attributes) {
		if (attributes == null) {
			return _MIN_COUNTS;
		}

		return MapUtil.getInteger(attributes, "minCounts", _MIN_COUNTS);
	}

	private int _getPage(Map<String, Object> attributes) {
		if (attributes == null) {
			return 0;
		}

		return MapUtil.getInteger(attributes, "page", 0);
	}

	private int _getRangeKey(Map<String, Object> attributes) {
		if (attributes == null) {
			return 0;
		}

		return MapUtil.getInteger(attributes, "rangeKey", 0);
	}

	private List<Suggestion> _getSuggestions(
		String endPointUsage, JSONArray jsonArray, SearchContext searchContext,
		SuggestionBuilderFactory suggestionBuilderFactory) {

		List<Suggestion> suggestions = new ArrayList<>();

		String destinationBaseURL = StringBundler.concat(
			GetterUtil.getString(
				searchContext.getAttribute(
					"search.suggestions.destination.friendly.url"),
				"/search"),
			"?",
			GetterUtil.getString(
				searchContext.getAttribute(
					"search.suggestions.keywords.parameter.name"),
				"q"),
			"=");

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject itemJSONObject = jsonArray.getJSONObject(i);

			String assetURL = StringPool.BLANK;
			String text = StringPool.BLANK;

			if (endPointUsage.equals("recent-sites")) {
				assetURL = itemJSONObject.getString("url");
				text = itemJSONObject.getString("site name"); // api not return the site name
			}
			else if (endPointUsage.equals("search-keywords")) {
				assetURL =
					destinationBaseURL + itemJSONObject.getString("keywords");
				text = itemJSONObject.getString("keywords");
			}
			else if (endPointUsage.equals("recent-pages")) {
				assetURL = itemJSONObject.getString("url");
				text = itemJSONObject.getString("title"); // api not return the title
			}
			else if (endPointUsage.equals("recent-assets")) {
				assetURL = itemJSONObject.getString("url"); // URL is returning /search??
				text = itemJSONObject.getString("assetTitle");
			}

			suggestions.add(
				suggestionBuilderFactory.builder(
				).attribute(
					"assetURL", assetURL
				).score(
					1.0F
				).text(
					text
				).build());
		}

		return suggestions;
	}

	private boolean _isEnabled(
		AnalyticsSettingsManager analyticsSettingsManager, long companyId) {

		try {
			if (FeatureFlagManagerUtil.isEnabled("LPS-159643") &&
				analyticsSettingsManager.isAnalyticsEnabled(companyId)) {

				return true;
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return false;
	}

	private static final int _CHARACTER_THRESHOLD = 2;

	private static final int _MIN_COUNTS = 5;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAsahIndividualsSuggestionsContributor.class);

}