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

package com.liferay.vldap.server.internal.portal.security.auth;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.Authenticator;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.vldap.server.internal.portal.security.samba.PortalSambaUtil;

import java.io.UnsupportedEncodingException;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Minhchau Dang
 */
@Component(
	immediate = true, property = "key=auth.pipeline.pre",
	service = Authenticator.class
)
public class SambaAuth implements Authenticator {

	public SambaAuth() {
		PortalSambaUtil.checkAttributes();
	}

	@Override
	public int authenticateByEmailAddress(
		long companyId, String emailAddress, String password,
		Map<String, String[]> headerMap, Map<String, String[]> parameterMap) {

		User user = _userLocalService.fetchUserByEmailAddress(
			companyId, emailAddress);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"User with email address " + emailAddress +
						" does not exist for company " + companyId);
			}
		}
		else {
			_setSambaPasswords(user, password);
		}

		return SUCCESS;
	}

	@Override
	public int authenticateByScreenName(
		long companyId, String screenName, String password,
		Map<String, String[]> headerMap, Map<String, String[]> parameterMap) {

		User user = _userLocalService.fetchUserByScreenName(
			companyId, screenName);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"User with screen name " + screenName +
						" does not exist for company " + companyId);
			}
		}
		else {
			_setSambaPasswords(user, password);
		}

		return SUCCESS;
	}

	@Override
	public int authenticateByUserId(
		long companyId, long userId, String password,
		Map<String, String[]> headerMap, Map<String, String[]> parameterMap) {

		User user = _userLocalService.fetchUserById(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("User with user ID " + userId + " does not exist");
			}
		}
		else {
			_setSambaPasswords(user, password);
		}

		return SUCCESS;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static void _setSambaPasswords(User user, String password) {
		try {
			PortalSambaUtil.setSambaLMPassword(user, password);
			PortalSambaUtil.setSambaNTPassword(user, password);
		}
		catch (UnsupportedEncodingException uee) {
			if (_log.isWarnEnabled()) {
				_log.warn(uee, uee);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(SambaAuth.class);

	private UserLocalService _userLocalService;

}