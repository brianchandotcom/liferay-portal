/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.web.cache;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.search.internal.configuration.AsahSearchKeywordsConfiguration;

/**
 * @author Petteri Karttunen
 */
public class AsahSearchKeywordsWebCacheItem extends BaseAsahWebCacheItem {

	public static JSONObject get(
		AnalyticsConfiguration analyticsConfiguration, String basePath,
		AsahSearchKeywordsConfiguration asahSearchKeywordsConfiguration,
		long companyId, String displayLanguageId, long groupId, int minCounts,
		String path, int size, String sort) {

		try {
			return (JSONObject)WebCachePoolUtil.get(
				StringBundler.concat(
					AsahSearchKeywordsWebCacheItem.class.getName(),
					StringPool.POUND, companyId, StringPool.POUND, minCounts,
					StringPool.POUND, displayLanguageId, StringPool.POUND,
					groupId, StringPool.POUND, sort),
				new AsahSearchKeywordsWebCacheItem(
					analyticsConfiguration, basePath,
					asahSearchKeywordsConfiguration, displayLanguageId, groupId,
					minCounts, path, size, sort));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	public AsahSearchKeywordsWebCacheItem(
		AnalyticsConfiguration analyticsConfiguration, String basePath,
		AsahSearchKeywordsConfiguration asahSearchKeywordsConfiguration,
		String displayLanguageId, long groupId, int minCounts, String path,
		int size, String sort) {

		super(
			analyticsConfiguration, basePath, displayLanguageId, groupId,
			minCounts, path, size, sort);

		_asahSearchKeywordsConfiguration = asahSearchKeywordsConfiguration;
	}

	@Override
	public long getRefreshTime() {
		return _asahSearchKeywordsConfiguration.cacheTimeout();
	}

	@Override
	protected String getURL() {
		StringBundler sb = new StringBundler(15);

		sb.append(analyticsConfiguration.liferayAnalyticsFaroBackendURL());
		sb.append("/api/1.0/");
		sb.append(basePath);
		sb.append("/");
		sb.append(path);

		sb.append("?minCounts=");
		sb.append(minCounts);

		if (!Validator.isBlank(displayLanguageId)) {
			sb.append("&displayLanguageId=");
			sb.append(displayLanguageId);
		}

		if (groupId > 0) {
			sb.append("&groupId=");
			sb.append(groupId);
		}

		sb.append("&size=");
		sb.append(size);
		sb.append("&sort=");
		sb.append(sort);

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AsahSearchKeywordsWebCacheItem.class);

	private final AsahSearchKeywordsConfiguration
		_asahSearchKeywordsConfiguration;

}