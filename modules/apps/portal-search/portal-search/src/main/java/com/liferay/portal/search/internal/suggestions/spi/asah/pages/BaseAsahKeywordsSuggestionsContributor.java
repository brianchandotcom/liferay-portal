/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions.spi.asah.pages;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.internal.configuration.AsahSearchKeywordsConfiguration;
import com.liferay.portal.search.internal.suggestions.spi.asah.BaseAsahSuggestionsContributor;
import com.liferay.portal.search.internal.web.cache.AsahSearchKeywordsWebCacheItem;
import com.liferay.portal.search.rest.dto.v1_0.SuggestionsContributorConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseAsahKeywordsSuggestionsContributor
	extends BaseAsahSuggestionsContributor {

	@Activate
	protected void activate(Map<String, Object> properties) {
		asahSearchKeywordsConfiguration = ConfigurableUtil.createConfigurable(
			AsahSearchKeywordsConfiguration.class, properties);
	}

	@Override
	protected JSONObject getJSONObject(
		AnalyticsConfiguration analyticsConfiguration,
		Map<String, Object> attributes, String basePath, String path,
		SearchContext searchContext, String sort,
		SuggestionsContributorConfiguration
			suggestionsContributorConfiguration) {

		return AsahSearchKeywordsWebCacheItem.get(
			analyticsConfiguration, basePath, asahSearchKeywordsConfiguration,
			searchContext.getCompanyId(),
			getDisplayLanguageId(attributes, searchContext.getLocale()),
			getGroupId(searchContext), getMinCounts(attributes), path,
			GetterUtil.getInteger(
				suggestionsContributorConfiguration.getSize(), 5),
			sort);
	}

	protected volatile AsahSearchKeywordsConfiguration
		asahSearchKeywordsConfiguration;

}