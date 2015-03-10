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

package com.liferay.portal.security.auth.bundle.authtokenutil;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.AuthToken;
import com.liferay.portal.security.auth.PrincipalException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Manuel de la Peña
 */
@Component(
	immediate = true,
	property = {"service.ranking:Integer=" + Integer.MAX_VALUE}
)
public class TestAuthToken implements AuthToken {

	@Deprecated
	@Override
	public void check(HttpServletRequest request) throws PortalException {
	}

	@Override
	public void checkCSRFToken(HttpServletRequest request, String origin)
		throws PrincipalException {
	}

	@Override
	public String getToken(HttpServletRequest request) {
		return "TEST_TOKEN";
	}

	@Override
	public String getToken(
		HttpServletRequest request, long plid, String portletId) {

		return "TEST_TOKEN_BY_PLID_AND_PORTLETID";
	}

	@Override
	public boolean isValidPortletInvocationToken(
		HttpServletRequest request, long plid, String portletId,
		String strutsAction, String tokenValue) {

		return "VALID_PORTLET_INVOCATION_TOKEN".equals(tokenValue);
	}

}