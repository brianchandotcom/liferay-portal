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

import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 */
public class LoginMaxFailures implements AuthFailure {

	@Override
	public void onFailureByEmailAddress(
			long companyId, String emailAddress,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			UserLocalServiceUtil.updateLockoutByEmailAddress(
				companyId, emailAddress, true);
		}
		catch (Exception e) {
			throw new AuthException();
		}
	}

	@Override
	public void onFailureByScreenName(
			long companyId, String screenName, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			UserLocalServiceUtil.updateLockoutByScreenName(
				companyId, screenName, true);
		}
		catch (Exception e) {
			throw new AuthException();
		}
	}

	@Override
	public void onFailureByUserId(
			long companyId, long userId, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			UserLocalServiceUtil.updateLockoutById(userId, true);
		}
		catch (Exception e) {
			throw new AuthException();
		}
	}

}