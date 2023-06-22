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
@Component(property = "key=auth.max.failures", service = AuthFailure.class)
public class LoginMaxAuthFailure implements AuthFailure {

	@Override
	public void onFailureByEmailAddress(
			long companyId, String emailAddress,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			_userLocalService.updateLockoutByEmailAddress(
				companyId, emailAddress, true);
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
			_userLocalService.updateLockoutByScreenName(
				companyId, screenName, true);
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
			_userLocalService.updateLockoutById(userId, true);
		}
		catch (Exception exception) {
			throw new AuthException(exception);
		}
	}

	@Reference
	private UserLocalService _userLocalService;

}