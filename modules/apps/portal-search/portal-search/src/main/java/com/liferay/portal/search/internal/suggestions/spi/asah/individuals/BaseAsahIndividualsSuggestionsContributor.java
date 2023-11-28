/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions.spi.asah.individuals;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.internal.configuration.AsahIndividualsConfiguration;
import com.liferay.portal.search.internal.suggestions.spi.asah.BaseAsahSuggestionsContributor;
import com.liferay.portal.search.internal.web.cache.AsahWebCacheItem;
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
		_asahIndividualsConfiguration = ConfigurableUtil.createConfigurable(
			AsahIndividualsConfiguration.class, properties);
	}

	@Override
	protected int getCharacterThreshold(Map<String, Object> attributes) {
		if (attributes == null) {
			return _CHARACTER_THRESHOLD;
		}

		return MapUtil.getInteger(
			attributes, "characterThreshold", _CHARACTER_THRESHOLD);
	}

	@Override
	protected JSONObject getJSONObject(
		AnalyticsConfiguration analyticsConfiguration,
		Map<String, Object> attributes, String basePath, String path,
		SearchContext searchContext, String sort,
		SuggestionsContributorConfiguration
			suggestionsContributorConfiguration) {

		String contentType = _getContentType(attributes);
		String displayLanguageId = getDisplayLanguageId(
			attributes, searchContext.getLocale());
		long groupId = getGroupId(searchContext);
		int minCounts = getMinCounts(attributes, _MIN_COUNTS);
		int page = _getPage(attributes);
		int rangeKey = _getRangeKey(attributes);
		int size = GetterUtil.getInteger(
			suggestionsContributorConfiguration.getSize(), 5);

		return AsahWebCacheItem.get(
			analyticsConfiguration, _asahIndividualsConfiguration,
			getURL(
				analyticsConfiguration,
				StringBundler.concat(
					basePath, StringPool.FORWARD_SLASH,
					_getHashedEmail(searchContext.getUserId())),
				contentType, displayLanguageId, groupId, minCounts, page, path,
				rangeKey, size, sort),
			StringBundler.concat(
				StringPool.POUND, searchContext.getCompanyId(),
				StringPool.POUND, contentType, StringPool.POUND,
				displayLanguageId, StringPool.POUND, groupId, StringPool.POUND,
				minCounts, StringPool.POUND, page, StringPool.POUND, rangeKey,
				StringPool.POUND, size, StringPool.POUND, sort));
	}

	protected String getURL(
		AnalyticsConfiguration analyticsConfiguration, String basePath,
		String contentType, String displayLanguageId, long groupId,
		long minCounts, int page, String path, int rangeKey, int size,
		String sort) {

		StringBundler sb = new StringBundler(22);

		sb.append(analyticsConfiguration.liferayAnalyticsFaroBackendURL());
		sb.append("/api/1.0/");
		sb.append(basePath);
		sb.append("/");

		sb.append(path);
		sb.append("?");

		if (minCounts > 0) {
			sb.append("&minCounts=");
			sb.append(minCounts);
		}

		if (!Validator.isBlank(contentType)) {
			sb.append("&contentType=");
			sb.append(contentType);
		}

		if (!Validator.isBlank(displayLanguageId)) {
			sb.append("&displayLanguageId=");
			sb.append(displayLanguageId);
		}

		if (groupId > 0) {
			sb.append("&groupId=");
			sb.append(groupId);
		}

		if (page > 0) {
			sb.append("&page=");
			sb.append(page);
		}

		if (rangeKey != 7) {
			sb.append("&rangeKey=");
			sb.append(rangeKey);
		}

		sb.append("&size=");
		sb.append(size);
		sb.append("&sort=");
		sb.append(sort);

		return sb.toString();
	}

	@Reference
	protected Portal portal;

	private String _getContentType(Map<String, Object> attributes) {
		if (attributes == null) {
			return StringPool.BLANK;
		}

		return MapUtil.getString(attributes, "contentType", StringPool.BLANK);
	}

	private String _getHashedEmail(long userId) {
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

	private static final int _CHARACTER_THRESHOLD = 0;

	private static final int _MIN_COUNTS = 1;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAsahIndividualsSuggestionsContributor.class);

	private volatile AsahIndividualsConfiguration _asahIndividualsConfiguration;

}