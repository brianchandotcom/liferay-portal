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

package com.liferay.users.admin.demo.data.creator.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.users.admin.demo.data.creator.OmniAdminUserDemoDataCreator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = OmniAdminUserDemoDataCreator.class)
public class OmniAdminUserDemoDataCreatorImpl
	implements OmniAdminUserDemoDataCreator {

	public User create(long companyId, String emailAddress)
		throws PortalException {

		User user = _userLocalService.fetchUserByEmailAddress(
			companyId, emailAddress);

		if (user != null) {
			return user;
		}

		String emailAccountName = emailAddress.substring(
			0, emailAddress.indexOf(StringPool.AT));

		String[] fullNameArray = StringUtil.split(
			emailAccountName, StringPool.PERIOD);

		String firstName = StringUtil.randomString();
		String lastName = StringUtil.randomString();

		if (fullNameArray.length > 0) {
			firstName = StringUtil.upperCaseFirstLetter(fullNameArray[0]);
		}

		if (fullNameArray.length > 1) {
			lastName = StringUtil.upperCaseFirstLetter(fullNameArray[1]);
		}

		boolean autoPassword = true;
		String password1 = "test";
		String password2 = "test";
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.SPAIN;
		String middleName = StringPool.BLANK;
		long prefixId = 0;
		long suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.FEBRUARY;
		int birthdayDay = 17;
		int birthdayYear = 1985;
		String jobTitle = "Software Engineer";
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendMail = false;

		user = _userLocalService.addUser(
			UserConstants.USER_ID_DEFAULT, companyId, autoPassword, password1,
			password2, true, StringPool.BLANK, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendMail,
			new ServiceContext());

		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		_userLocalService.addRoleUser(role.getRoleId(), user);

		user = _userLocalService.getUser(user.getUserId());

		_users.add(user);

		return user;
	}

	public void delete() throws PortalException {
		for (User user : _users) {
			_userLocalService.deleteUser(user);
		}
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private RoleLocalService _roleLocalService;
	private UserLocalService _userLocalService;
	private final List<User> _users = new ArrayList();

}