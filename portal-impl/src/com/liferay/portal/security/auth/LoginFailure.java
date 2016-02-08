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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.AuthFailure;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 */
@OSGiBeanProperties(property = "key=auth.failure")
public class LoginFailure implements AuthFailure {

	@Override
	public void onFailureByEmailAddress(
			long companyId, String emailAddress,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			UserLocalServiceUtil.checkLoginFailureByEmailAddress(
				companyId, emailAddress);
		}
		catch (Exception e) {
			throw new AuthException(e);
		}
	}

	@Override
	public void onFailureByScreenName(
			long companyId, String screenName, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			UserLocalServiceUtil.checkLoginFailureByScreenName(
				companyId, screenName);
		}
		catch (Exception e) {
			throw new AuthException(e);
		}
	}

	@Override
	public void onFailureByUserId(
			long companyId, long userId, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			UserLocalServiceUtil.checkLoginFailureById(userId);
		}
		catch (Exception e) {
			throw new AuthException(e);
		}
	}

}