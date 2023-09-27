/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.login.authentication.internal.security.auth;

import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.AuthFailure;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 */
@Component(property = "key=auth.failure", service = AuthFailure.class)
public class LoginAuthFailure implements AuthFailure {

	@Override
	public void onFailureByEmailAddress(
			long companyId, String emailAddress,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			_userLocalService.checkLoginFailureByEmailAddress(
				companyId, emailAddress);
		}
		catch (Exception exception) {
			throw new AuthException(exception);
		}
	}

	@Override
	public void onFailureByScreenName(
			long companyId, String screenName, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			_userLocalService.checkLoginFailureByScreenName(
				companyId, screenName);
		}
		catch (Exception exception) {
			throw new AuthException(exception);
		}
	}

	@Override
	public void onFailureByUserId(
			long companyId, long userId, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			_userLocalService.checkLoginFailureById(userId);
		}
		catch (Exception exception) {
			throw new AuthException(exception);
		}
	}

	@Reference
	private UserLocalService _userLocalService;

}