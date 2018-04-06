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

package com.liferay.portal.security.sso.bundle.ssoutil;

import com.liferay.portal.kernel.security.sso.SSO;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE
)
public class TestSSOImpl implements SSO {

	@Override
	public String getSessionExpirationRedirectUrl(long companyId) {
		return "getSessionExpirationRedirectUrl:" + companyId;
	}

	@Override
	public String getSignInURL(long companyId, String defaultSignInURL) {
		return defaultSignInURL + ":" + companyId;
	}

	@Override
	public boolean isLoginRedirectRequired(long companyId) {
		if (companyId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRedirectRequired(long companyId) {
		if (companyId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isSessionRedirectOnExpire(long companyId) {
		if (companyId == 1) {
			return true;
		}

		return false;
	}

}