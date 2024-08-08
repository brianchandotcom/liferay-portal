/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions.spi.asah;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.rest.dto.v1_0.SuggestionsContributorConfiguration;
import com.liferay.portal.search.suggestions.Suggestion;
import com.liferay.portal.search.suggestions.SuggestionBuilderFactory;
import com.liferay.portal.search.suggestions.SuggestionsContributorResults;
import com.liferay.portal.search.suggestions.SuggestionsContributorResultsBuilderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 */
public abstract class BaseAsahSuggestionsContributor {

	protected boolean exceedsCharacterThreshold(
		Map<String, Object> attributes, String keywords) {

		int characterThreshold = getCharacterThreshold(attributes);

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

	protected AnalyticsConfiguration getAnalyticsConfiguration(
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

	protected String getAssetURL(
		String destinationBaseURL, JSONObject itemJSONObject) {

		return destinationBaseURL + itemJSONObject.getString("keywords");
	}

	protected abstract int getCharacterThreshold(
		Map<String, Object> attributes);

	protected String getDisplayLanguageId(
		Map<String, Object> attributes, Locale locale) {

		if ((attributes == null) ||
			MapUtil.getBoolean(attributes, "matchDisplayLanguageId", true)) {

			return LanguageUtil.getBCP47LanguageId(locale);
		}

		return StringPool.BLANK;
	}

	protected long getGroupId(SearchContext searchContext) {
		long[] groupIds = searchContext.getGroupIds();

		if (ArrayUtil.isEmpty(groupIds)) {
			return 0;
		}

		return groupIds[0];
	}

	protected abstract JSONObject getJSONObject(
		AnalyticsConfiguration analyticsConfiguration,
		Map<String, Object> attributes, String basePath, String path,
		SearchContext searchContext, String sort,
		SuggestionsContributorConfiguration
			suggestionsContributorConfiguration);

	protected int getMinCounts(Map<String, Object> attributes, int minCounts) {
		if (attributes == null) {
			return minCounts;
		}

		return MapUtil.getInteger(attributes, "minCounts", minCounts);
	}

	protected List<Suggestion> getSuggestions(
		JSONArray jsonArray, SearchContext searchContext) {

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

			String text = getText(itemJSONObject);

			if (Validator.isBlank(text)) {
				continue;
			}

			suggestions.add(
				suggestionBuilderFactory.builder(
				).attribute(
					"assetURL", getAssetURL(destinationBaseURL, itemJSONObject)
				).score(
					1.0F
				).text(
					text
				).build());
		}

		return suggestions;
	}

	protected SuggestionsContributorResults getSuggestionsContributorResults(
		String basePath, String path, SearchContext searchContext, String sort,
		SuggestionsContributorConfiguration
			suggestionsContributorConfiguration) {

		if (!isEnabled(
				analyticsSettingsManager, searchContext.getCompanyId())) {

			return null;
		}

		AnalyticsConfiguration analyticsConfiguration =
			getAnalyticsConfiguration(
				analyticsSettingsManager, searchContext.getCompanyId());

		if (analyticsConfiguration == null) {
			return null;
		}

		Map<String, Object> attributes =
			(Map<String, Object>)
				suggestionsContributorConfiguration.getAttributes();

		if (!exceedsCharacterThreshold(
				attributes, searchContext.getKeywords())) {

			return null;
		}

		JSONArray jsonArray = JSONUtil.getValueAsJSONArray(
			getJSONObject(
				analyticsConfiguration, attributes, basePath, path,
				searchContext, sort, suggestionsContributorConfiguration),
			"JSONObject/_embedded", "JSONArray/" + path);

		if (JSONUtil.isEmpty(jsonArray)) {
			return null;
		}

		return suggestionsContributorResultsBuilderFactory.builder(
		).displayGroupName(
			suggestionsContributorConfiguration.getDisplayGroupName()
		).suggestions(
			getSuggestions(jsonArray, searchContext)
		).build();
	}

	protected String getText(JSONObject itemJSONObject) {
		return itemJSONObject.getString("keywords");
	}

	protected abstract boolean isEnabled(
		AnalyticsSettingsManager analyticsSettingsManager, long companyId);

	@Reference
	protected AnalyticsSettingsManager analyticsSettingsManager;

	@Reference
	protected SuggestionBuilderFactory suggestionBuilderFactory;

	@Reference
	protected SuggestionsContributorResultsBuilderFactory
		suggestionsContributorResultsBuilderFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAsahSuggestionsContributor.class);

}