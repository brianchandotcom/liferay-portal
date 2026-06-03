/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.rest.internal.web.cache;

import com.liferay.oauth.client.LocalOAuthClient;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;

/**
 * @author Rafael Praxedes
 */
public class AIHubCellUserTokenWebCacheItem implements WebCacheItem {

	public static String get(
		LocalOAuthClient localOAuthClient, OAuth2Application oAuth2Application,
		long userId) {

		return (String)WebCachePoolUtil.get(
			StringBundler.concat(
				AIHubCellUserTokenWebCacheItem.class.getName(),
				StringPool.POUND, oAuth2Application.getCompanyId(),
				StringPool.POUND, oAuth2Application.getOAuth2ApplicationId(),
				StringPool.POUND, userId),
			new AIHubCellUserTokenWebCacheItem(
				localOAuthClient, oAuth2Application, userId));
	}

	public AIHubCellUserTokenWebCacheItem(
		LocalOAuthClient localOAuthClient, OAuth2Application oAuth2Application,
		long userId) {

		_localOAuthClient = localOAuthClient;
		_oAuth2Application = oAuth2Application;
		_userId = userId;
	}

	@Override
	public Object convert(String key) {
		try {
			String responseJSON = TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> _localOAuthClient.requestTokens(
					_oAuth2Application, _userId));

			if (Validator.isNull(responseJSON)) {
				return null;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				responseJSON);

			_refreshTime =
				(long)(jsonObject.getLong("expires_in") * 0.8 * Time.SECOND);

			return jsonObject.get("access_token");
		}
		catch (Throwable throwable) {
			if (_log.isDebugEnabled()) {
				_log.debug(throwable);
			}

			return null;
		}
	}

	@Override
	public long getRefreshTime() {
		return _refreshTime;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AIHubCellUserTokenWebCacheItem.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

	private final LocalOAuthClient _localOAuthClient;
	private final OAuth2Application _oAuth2Application;
	private long _refreshTime;
	private final long _userId;

}