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

package com.liferay.portal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class TestPropsValues {

	public static final String COMPANY_WEB_ID;

	public static final int JUNIT_DELAY_FACTOR = GetterUtil.getInteger(
		TestPropsUtil.get("junit.delay.factor"));

	public static final String PORTAL_URL = TestPropsUtil.get("portal.url");

	public static final String USER_PASSWORD = TestPropsUtil.get(
		"user.password");

	public static long getCompanyId() throws Exception {
		if (_companyId > 0) {
			return _companyId;
		}

		Company company = CompanyLocalServiceUtil.getCompanyByWebId(
			TestPropsValues.COMPANY_WEB_ID);

		_companyId = company.getCompanyId();

		return _companyId;
	}

	public static long getGroupId() throws Exception {
		if (_groupId > 0) {
			return _groupId;
		}

		Group group = GroupLocalServiceUtil.getGroup(
			getCompanyId(), GroupConstants.GUEST);

		_groupId = group.getGroupId();

		return _groupId;
	}

	public static HttpPrincipal getHttpPrincipal() throws Exception {
		return getHttpPrincipal(getLogin());
	}

	public static HttpPrincipal getHttpPrincipal(String login) {
		return getHttpPrincipal(login, true);
	}

	public static HttpPrincipal getHttpPrincipal(
		String login, boolean authenticated) {

		HttpPrincipal httpPrincipal = null;

		if (authenticated) {
			httpPrincipal = new HttpPrincipal(PORTAL_URL, login, USER_PASSWORD);
		}
		else {
			httpPrincipal = new HttpPrincipal(PORTAL_URL);
		}

		return httpPrincipal;
	}

	public static String getLogin() throws Exception {
		String login = null;

		String authType = PropsValues.COMPANY_SECURITY_AUTH_TYPE;

		if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
			login = getUser().getEmailAddress();
			login = HttpUtil.encodeURL(login);
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
			login = getUser().getScreenName();
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
			login = Long.toString(getUserId());
		}

		return login;
	}

	public static long getPlid() throws Exception {
		return getPlid(getGroupId());
	}

	public static long getPlid(long groupId) throws Exception {
		if (_plid > 0) {
			return _plid;
		}

		_plid = LayoutLocalServiceUtil.getDefaultPlid(groupId);

		return _plid;
	}

	public static URL getSoapURL(String serviceName) throws Exception {
		return getSoapURL(getLogin(), serviceName);
	}

	public static URL getSoapURL(
			String login, boolean authenticated, String serviceName)
		throws MalformedURLException {

		String url = PORTAL_URL;

		if (authenticated) {
			String password = USER_PASSWORD;

			int pos = url.indexOf("://");

			String protocol = url.substring(0, pos + 3);
			String host = url.substring(pos + 3);

			url =
				protocol + login + ":" + password + "@" + host +
					"/api/axis/" + serviceName;
		}
		else {
			url += "/api/axis/" + serviceName;
		}

		return new URL(url);
	}

	public static URL getSoapURL(String login, String serviceName)
		throws MalformedURLException {

		return getSoapURL(login, true, serviceName);
	}

	public static User getUser() throws Exception {
		if (_user == null) {
			Role role = RoleLocalServiceUtil.getRole(
				getCompanyId(), RoleConstants.ADMINISTRATOR);

			List<User> users = UserLocalServiceUtil.getRoleUsers(
				role.getRoleId(), 0, 2);

			_user = users.get(0);

			_userId = _user.getUserId();
		}

		return _user;
	}

	public static long getUserId() throws Exception {
		if (_userId == 0) {
			_userId = getUser().getUserId();
		}

		return _userId;
	}

	private static Log _log = LogFactoryUtil.getLog(TestPropsValues.class);

	private static long _companyId;
	private static long _groupId;
	private static long _plid;
	private static User _user;
	private static long _userId;

	static {
		String companyWebId = TestPropsUtil.get("company.web.id");

		try {
			if (Validator.isNull(companyWebId)) {
				companyWebId = PropsValues.COMPANY_DEFAULT_WEB_ID;

				TestPropsUtil.set("company.web.id", companyWebId);
			}
		}
		catch (Exception e) {
			_log.fatal("Error initializing test properties", e);
		}

		TestPropsUtil.printProperties();

		COMPANY_WEB_ID = companyWebId;
	}

}