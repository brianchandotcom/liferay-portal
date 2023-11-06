/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions.spi.asah.individuals;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.internal.configuration.AsahIndividualsConfiguration;
import com.liferay.portal.search.internal.suggestions.spi.asah.BaseAsahSuggestionsContributor;
import com.liferay.portal.search.internal.web.cache.AsahIndividualsWebCacheItem;
import com.liferay.portal.search.rest.dto.v1_0.SuggestionsContributorConfiguration;

import java.security.MessageDigest;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 */
public abstract class BaseAsahIndividualsSuggestionsContributor
	extends BaseAsahSuggestionsContributor {

	@Activate
	protected void activate(Map<String, Object> properties) {
		asahIndividualsConfiguration = ConfigurableUtil.createConfigurable(
			AsahIndividualsConfiguration.class, properties);
	}

	protected String getHashedEmail(long userId) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

			messageDigest.update(
				portal.getUserEmailAddress(
					userId
				).getBytes());

			byte[] digest = messageDigest.digest();

			StringBuilder sb = new StringBuilder();

			for (byte b : digest) {
				sb.append(String.format("%02x", b));
			}

			return sb.toString();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return StringPool.BLANK;
	}

	@Override
	protected JSONObject getJSONObject(
		AnalyticsConfiguration analyticsConfiguration,
		Map<String, Object> attributes, String basePath, String path,
		SearchContext searchContext, String sort,
		SuggestionsContributorConfiguration
			suggestionsContributorConfiguration) {

		return AsahIndividualsWebCacheItem.get(
			analyticsConfiguration, asahIndividualsConfiguration,
			_getContentType(attributes), searchContext.getCompanyId(),
			getDisplayLanguageId(attributes, searchContext.getLocale()),
			basePath, path, getGroupId(searchContext), getMinCounts(attributes),
			_getPage(attributes), _getRangeKey(attributes),
			GetterUtil.getInteger(
				suggestionsContributorConfiguration.getSize(), 5),
			sort);
	}

	protected volatile AsahIndividualsConfiguration
		asahIndividualsConfiguration;

	@Reference
	protected Portal portal;

	private String _getContentType(Map<String, Object> attributes) {
		if (attributes == null) {
			return StringPool.BLANK;
		}

		return MapUtil.getString(attributes, "contentType", StringPool.BLANK);
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

		// what should be the default?

		return MapUtil.getInteger(attributes, "rangeKey", 0);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAsahIndividualsSuggestionsContributor.class);

}