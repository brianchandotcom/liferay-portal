/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.servlet.filters.secure.NonceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tomas Polesovsky
 */
public class DigestAuthenticationAuthVerifier implements AuthVerifier {

	public String getAuthType() {
		return HttpServletRequest.DIGEST_AUTH;
	}

	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties configuration)
		throws AuthException {

		try {
			AuthVerifierResult authVerifierResult = new AuthVerifierResult();

			HttpServletRequest request = accessControlContext.getRequest();

			long userId = PortalUtil.getDigestAuthUserId(request);

			if (userId == 0) {

				// Deprecated

				boolean forcedDigestAuth = MapUtil.getBoolean(
					accessControlContext.getSettings(), "digest_auth");

				if (forcedDigestAuth) {
					HttpServletResponse response =
						accessControlContext.getResponse();

					// Must generate a new nonce for each 401 (RFC2617, 3.2.1)

					long companyId = PortalInstances.getCompanyId(request);

					String remoteAddress = request.getRemoteAddr();

					String nonce = NonceUtil.generate(companyId, remoteAddress);

					StringBundler sb = new StringBundler(4);

					sb.append(_DIGEST_REALM);
					sb.append(", nonce=\"");
					sb.append(nonce);
					sb.append("\"");

					response.setHeader(
						HttpHeaders.WWW_AUTHENTICATE, sb.toString());

					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

					authVerifierResult.setState(
						AuthVerifierResult.State.INVALID_CREDENTIALS);
				}

				return authVerifierResult;
			}

			authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
			authVerifierResult.setUserId(userId);

			return authVerifierResult;
		}
		catch (PortalException pe) {
			throw new AuthException(pe);
		}
		catch (SystemException se) {
			throw new AuthException(se);
		}
	}

	private static final String _DIGEST_REALM =
		"Digest realm=\"" + Portal.PORTAL_REALM + "\"";

}