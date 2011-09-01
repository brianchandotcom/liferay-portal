/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class TestPropsValues {

	public static final String COMPANY_WEB_ID;

	public static final String PORTAL_URL = TestPropsUtil.get("portal.url");

	public static final String USER_PASSWORD =
		TestPropsUtil.get("user.password");

	private static Log _log = LogFactoryUtil.getLog(TestPropsValues.class);

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

	public static long getCompanyId() throws PortalException, SystemException {
		Company company = CompanyLocalServiceUtil.getCompanyByWebId(
			TestPropsValues.COMPANY_WEB_ID);

		return company.getCompanyId();
	}

	public static long getGroupId() throws PortalException, SystemException {
		Group group = GroupLocalServiceUtil.getGroup(
			getCompanyId(), GroupConstants.GUEST);

		return group.getGroupId();
	}

	public static long getUserId() throws PortalException, SystemException {
		Role administrator = RoleLocalServiceUtil.getRole(
			getCompanyId(), RoleConstants.ADMINISTRATOR);

		List<User> users = UserLocalServiceUtil.getRoleUsers(
			administrator.getRoleId(), 0, 2);

		User user = users.get(0);

		return user.getUserId();
	}

}