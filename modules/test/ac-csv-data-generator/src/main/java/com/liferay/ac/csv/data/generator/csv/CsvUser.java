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

package com.liferay.ac.csv.data.generator.csv;

import com.liferay.ac.csv.data.generator.util.GeneratedDataUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.apache.commons.csv.CSVRecord;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(immediate = true, service = CsvUser.class)
public class CsvUser {

	public User addUser(CSVRecord csvRecord) throws PortalException {
		String emailAddress = csvRecord.get("emailAddress");
		String password = csvRecord.get("password");
		String screenName = csvRecord.get("screenName");
		String firstName = csvRecord.get("firstName");
		String middleName = csvRecord.get("middleName");
		String lastName = csvRecord.get("lastName");
		String jobTitle = csvRecord.get("jobTitle");

		String gender = csvRecord.get("gender");

		boolean male = gender.equalsIgnoreCase("male");

		int javaMonthOffset = 1; //java.util.Date months start at 0 (meaning 0 for January)

		int birthdayMonth =
			GetterUtil.getInteger(csvRecord.get("birthdayMonth")) -
				javaMonthOffset;

		int birthdayDay = GetterUtil.getInteger(csvRecord.get("birthdayDay"));

		int birthdayYear = GetterUtil.getInteger(csvRecord.get("birthdayYear"));

		return _userLocalService.addUser(
			_generatedDataUtil.getDefaultUserId(),
			_generatedDataUtil.getCompanyId(), false, password, password, false,
			screenName, emailAddress, 0, StringPool.BLANK,
			LocaleUtil.getDefault(), firstName, middleName, lastName, 0, 0,
			male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, _groupIds,
			_organizationIds, _roleIds, _userGroupIds, false,
			new ServiceContext());
	}

	public void assignOrganizations(long[] organizationIds) {
		_organizationIds = organizationIds;
	}

	public void assignRoles(long[] roleIds) {
		_roleIds = roleIds;
	}

	public void assignUserGroups(long[] userGroupIds) {
		_userGroupIds = userGroupIds;
	}

	@Reference
	private GeneratedDataUtil _generatedDataUtil;

	private long[] _groupIds;
	private long[] _organizationIds;
	private long[] _roleIds;
	private long[] _userGroupIds;

	@Reference
	private UserLocalService _userLocalService;

}