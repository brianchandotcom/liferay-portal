/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
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
import com.liferay.portal.search.internal.configuration.AsahIndividualsConfiguration;

/**
 * @author Gustavo Lima
 */
public class AsahIndividualsWebCacheItem extends BaseAsahWebCacheItem {

	public static JSONObject get(
		AnalyticsConfiguration analyticsConfiguration,
		AsahIndividualsConfiguration asahIndividualsConfiguration,
		String contentType, long companyId, String displayLanguageId,
		String basePath, String path, long groupId, int minCounts, int page,
		int rangeKey, int size, String sort) {

		try {
			return (JSONObject)WebCachePoolUtil.get(
				StringBundler.concat(
					AsahIndividualsWebCacheItem.class.getName(),
					StringPool.POUND, companyId, StringPool.POUND, minCounts,
					StringPool.POUND, displayLanguageId, StringPool.POUND,
					groupId, StringPool.POUND, sort),
				new AsahIndividualsWebCacheItem(
					analyticsConfiguration, asahIndividualsConfiguration,
					contentType, displayLanguageId, basePath, path, groupId,
					minCounts, page, rangeKey, size, sort));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	public AsahIndividualsWebCacheItem(
		AnalyticsConfiguration analyticsConfiguration,
		AsahIndividualsConfiguration asahIndividualsConfiguration,
		String contentType, String displayLanguageId, String basePath,
		String path, long groupId, int minCounts, int page, int rangeKey,
		int size, String sort) {

		super(
			analyticsConfiguration, basePath, displayLanguageId, groupId,
			minCounts, path, size, sort);

		_asahIndividualsConfiguration = asahIndividualsConfiguration;
		_contentType = contentType;
		_page = page;
		_rangeKey = rangeKey;
	}

	@Override
	public long getRefreshTime() {
		return _asahIndividualsConfiguration.cacheTimeout();
	}

	@Override
	protected String getURL() {
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

		if (!Validator.isBlank(_contentType)) {
			sb.append("&contentType=");
			sb.append(_contentType);
		}

		if (!Validator.isBlank(displayLanguageId)) {
			sb.append("&displayLanguageId=");
			sb.append(displayLanguageId);
		}

		if (groupId > 0) {
			sb.append("&groupId=");
			sb.append(groupId);
		}

		if (_page > 0) {
			sb.append("&page=");
			sb.append(_page);
		}

		if (_rangeKey != 7) {
			sb.append("&rangeKey=");
			sb.append(_rangeKey);
		}

		sb.append("&size=");
		sb.append(size);
		sb.append("&sort=");
		sb.append(sort);

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AsahIndividualsWebCacheItem.class);

	private final AsahIndividualsConfiguration _asahIndividualsConfiguration;
	private final String _contentType;
	private final int _page;
	private final int _rangeKey;

}