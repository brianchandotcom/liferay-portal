/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.rest.internal.web.cache;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheItem;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;

/**
 * @author Feliphe Marinho
 */
public abstract class BaseWebCacheItem implements WebCacheItem {

	protected static long getExpirationTime(String accessToken)
		throws Exception {

		SignedJWT signedJWT = SignedJWT.parse(accessToken);

		JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();

		Date expirationDate = jwtClaimsSet.getExpirationTime();

		return expirationDate.getTime();
	}

	protected static boolean isExpired(String accessToken) {
		try {
			if (getExpirationTime(accessToken) <=
					(System.currentTimeMillis() + Time.MINUTE)) {

				return true;
			}

			return false;
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseWebCacheItem.class);

}