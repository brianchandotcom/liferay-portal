/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AccessControlContext;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.util.PortalUtil;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

/**
 * PortalSessionAuthVerifier returns authenticated user based on portal
 * session authentication (e.g. login portlet, AutoLogins, ...).
 *
 * @author Tomas Polesovsky
 */
public class PortalSessionAuthVerifier implements AuthVerifier {

	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties configuration)
		throws AuthException {

		AuthVerifierResult result = new AuthVerifierResult();

		try {
			HttpServletRequest request =
				accessControlContext.getHttpServletRequest();
			User user = PortalUtil.getUser(request);

			if (user == null) {
				return result;
			}

			result.setState(AuthVerifierResult.State.SUCCESS);
			result.setUserId(user.getUserId());

			return result;
		}
		catch (PortalException e) {
			throw new AuthException(e);
		}
		catch (SystemException e) {
			throw new AuthException(e);
		}
	}

}