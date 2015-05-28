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

package com.liferay.portal.security.auth.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.security.auth.BaseAutoLogin;
import com.liferay.portal.security.exportimport.UserImporterUtil;
import com.liferay.portal.security.sso.SSOUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
@Component(
	immediate = true,
	property = {"request.header.auth.hosts.allowed=255.255.255.255"},
	service = AutoLogin.class
)
public class RequestHeaderAutoLogin extends BaseAutoLogin {

	@Activate
	public void activate(Map<String, String> properties) {
		String[] hostsAllowedArray = StringUtil.split(
			properties.get("request.header.auth.hosts.allowed"));

		for (int i = 0; i < hostsAllowedArray.length; i++) {
			_hostsAllowed.add(hostsAllowedArray[i]);
		}
	}

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String remoteAddr = request.getRemoteAddr();

		if (SSOUtil.isAccessAllowed(request, _hostsAllowed)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Access allowed for " + remoteAddr);
			}
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn("Access denied for " + remoteAddr);
			}

			return null;
		}

		long companyId = PortalUtil.getCompanyId(request);

		String screenName = request.getHeader(HttpHeaders.LIFERAY_SCREEN_NAME);

		if (Validator.isNull(screenName)) {
			return null;
		}

		User user = null;

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.REQUEST_HEADER_AUTH_IMPORT_FROM_LDAP,
				PropsValues.REQUEST_HEADER_AUTH_IMPORT_FROM_LDAP)) {

			try {
				user = UserImporterUtil.importUser(
					companyId, StringPool.BLANK, screenName);
			}
			catch (Exception e) {
			}
		}

		if (user == null) {
			user = UserLocalServiceUtil.getUserByScreenName(
				companyId, screenName);
		}

		String[] credentials = new String[3];

		credentials[0] = String.valueOf(user.getUserId());
		credentials[1] = user.getPassword();
		credentials[2] = Boolean.TRUE.toString();

		return credentials;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RequestHeaderAutoLogin.class);

	private final Set<String> _hostsAllowed = new HashSet<>();

}