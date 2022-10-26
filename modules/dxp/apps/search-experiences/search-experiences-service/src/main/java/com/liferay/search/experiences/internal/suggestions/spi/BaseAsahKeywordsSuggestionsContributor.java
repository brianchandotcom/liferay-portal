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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.rest.dto.v1_0.SuggestionsContributorConfiguration;
import com.liferay.portal.search.suggestions.Suggestion;
import com.liferay.portal.search.suggestions.SuggestionBuilderFactory;
import com.liferay.portal.search.suggestions.SuggestionsContributorResults;
import com.liferay.portal.search.suggestions.SuggestionsContributorResultsBuilderFactory;
import com.liferay.search.experiences.internal.configuration.AsahSearchKeywordsConfiguration;
import com.liferay.search.experiences.internal.util.AsahUtil;
import com.liferay.search.experiences.internal.web.cache.AsahSearchKeywordsWebCacheItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseAsahKeywordsSuggestionsContributor {

	@Activate
	protected void activate(Map<String, Object> properties) {
		asahSearchKeywordsConfiguration = ConfigurableUtil.createConfigurable(
			AsahSearchKeywordsConfiguration.class, properties);
	}

	protected SuggestionsContributorResults getSuggestionsContributorResults(
		SearchContext searchContext, String sort,
		SuggestionBuilderFactory suggestionBuilderFactory,
		SuggestionsContributorConfiguration suggestionsContributorConfiguration,
		SuggestionsContributorResultsBuilderFactory
			suggestionsContributorResultsBuilderFactory) {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-159643")) ||
			!AsahUtil.isAnalyticsEnabled(searchContext.getCompanyId())) {

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
			AsahSearchKeywordsWebCacheItem.get(
				asahSearchKeywordsConfiguration, searchContext.getCompanyId(),
				_getCount(attributes),
				_getDisplayLanguageId(attributes, searchContext.getLocale()),
				_getGroupId(searchContext),
				GetterUtil.getInteger(
					suggestionsContributorConfiguration.getSize(), 5),
				sort),
			"JSONObject/_embedded", "JSONArray/search-keywords");

		if (jsonArray.length() == 0) {
			return null;
		}

		return suggestionsContributorResultsBuilderFactory.builder(
		).displayGroupName(
			suggestionsContributorConfiguration.getDisplayGroupName()
		).suggestions(
			_getSuggestions(
				attributes, jsonArray, searchContext, suggestionBuilderFactory)
		).build();
	}

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

	private int _getCharacterThreshold(Map<String, Object> attributes) {
		if (attributes == null) {
			return _CHARACTER_THRESHOLD;
		}

		return MapUtil.getInteger(
			attributes, "characterThreshold", _CHARACTER_THRESHOLD);
	}

	private int _getCount(Map<String, Object> attributes) {
		if (attributes == null) {
			return _COUNT;
		}

		return MapUtil.getInteger(attributes, "count", _COUNT);
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

	private List<Suggestion> _getSuggestions(
		Map<String, Object> attributes, JSONArray jsonArray,
		SearchContext searchContext,
		SuggestionBuilderFactory suggestionBuilderFactory) {

		List<Suggestion> suggestions = new ArrayList<>();

		String destinationBaseURL = StringBundler.concat(
			GetterUtil.getString(
				searchContext.getAttribute(
					"search.suggestions.destination.friendly.url"),
				"/search"),
			"?", MapUtil.getString(attributes, "keywordsParameterName", "q"),
			"=");

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject itemJSONObject = jsonArray.getJSONObject(i);

			String keywords = itemJSONObject.getString("keywords");

			suggestions.add(
				suggestionBuilderFactory.builder(
				).attribute(
					"assetURL", destinationBaseURL + keywords
				).score(
					1.0F
				).text(
					itemJSONObject.getString("keywords")
				).build());
		}

		return suggestions;
	}

	private static final int _CHARACTER_THRESHOLD = 2;

	private static final int _COUNT = 5;

}