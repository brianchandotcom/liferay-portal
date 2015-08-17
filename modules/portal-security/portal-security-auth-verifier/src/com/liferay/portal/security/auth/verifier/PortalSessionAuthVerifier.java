/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth.verifier;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AccessControlContext;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.util.PortalUtil;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component(
	immediate = true,
	property = {
		"auth.verifier.PortalSessionAuthVerifier.hosts.allowed=",
		"auth.verifier.PortalSessionAuthVerifier.urls.includes=/api/json/*,/api/jsonws/*,/c/portal/json_service/*"
	}
)
public class PortalSessionAuthVerifier implements AuthVerifier {

	public static final String AUTH_TYPE = HttpServletRequest.FORM_AUTH;

	@Override
	public String getAuthType() {
		return AUTH_TYPE;
	}

	@Override
	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties properties)
		throws AuthException {

		try {
			AuthVerifierResult authVerifierResult = new AuthVerifierResult();

			HttpServletRequest request = accessControlContext.getRequest();

			User user = PortalUtil.getUser(request);

			if ((user == null) || user.isDefaultUser()) {
				return authVerifierResult;
			}

			authVerifierResult.setPasswordBasedAuthentication(true);
			authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
			authVerifierResult.setUserId(user.getUserId());

			return authVerifierResult;
		}
		catch (PortalException pe) {
			throw new AuthException(pe);
		}
		catch (SystemException se) {
			throw new AuthException(se);
		}
	}

}