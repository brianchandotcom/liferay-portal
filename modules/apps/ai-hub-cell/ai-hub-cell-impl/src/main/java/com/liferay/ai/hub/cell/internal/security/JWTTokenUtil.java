/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.internal.security;

import com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;

/**
 * @author Rafael Praxedes
 */
public class JWTTokenUtil {

	public static long getUserId(String issuer, String token) {
		JWTClaimsSet jwtClaimsSet = null;

		try {
			SignedJWT signedJWT = SignedJWT.parse(token);

			if (!signedJWT.verify(new MACVerifier(_getSecret()))) {
				if (_log.isDebugEnabled()) {
					_log.debug("Invalid JWT signature");
				}

				return 0;
			}

			jwtClaimsSet = signedJWT.getJWTClaimsSet();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to parse and verify the JWT token", exception);
			}

			return 0;
		}

		if (Validator.isNull(issuer) ||
			!issuer.equals(jwtClaimsSet.getIssuer())) {

			if (_log.isDebugEnabled()) {
				_log.debug("Invalid JWT issuer");
			}

			return 0;
		}

		Date expirationDate = jwtClaimsSet.getExpirationTime();

		if ((expirationDate == null) || expirationDate.before(new Date())) {
			if (_log.isDebugEnabled()) {
				_log.debug("The JWT token is expired");
			}

			return 0;
		}

		return GetterUtil.getLong(jwtClaimsSet.getSubject());
	}

	private static byte[] _getSecret() throws Exception {
		AIHubCellConfiguration aiHubCellConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				AIHubCellConfiguration.class,
				CompanyThreadLocal.getCompanyId());

		return Base64.decode(aiHubCellConfiguration.secret());
	}

	private static final Log _log = LogFactoryUtil.getLog(JWTTokenUtil.class);

}