/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.rest.internal.security;

import com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.util.Base64;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Christopher Kian
 */
public class JWTTokenUtil {

	public static String generateToken() {
		try {
			Company company = CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId());

			Date now = new Date();

			SignedJWT signedJWT = new SignedJWT(
				new JWSHeader(JWSAlgorithm.HS256),
				new JWTClaimsSet.Builder(
				).expirationTime(
					new Date(now.getTime() + TimeUnit.MINUTES.toMillis(10))
				).issuer(
					company.getVirtualHostname()
				).issueTime(
					now
				).subject(
					String.valueOf(PrincipalThreadLocal.getUserId())
				).build());

			signedJWT.sign(new MACSigner(_getSecret()));

			return signedJWT.serialize();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to generate a signed token", exception);
			}

			return null;
		}
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