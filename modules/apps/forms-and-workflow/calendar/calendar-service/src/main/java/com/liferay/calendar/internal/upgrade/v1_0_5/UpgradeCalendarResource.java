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

package com.liferay.calendar.internal.upgrade.v1_0_5;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Adam Brandizzi
 */
public class UpgradeCalendarResource extends UpgradeProcess {

	public UpgradeCalendarResource(
		ClassNameLocalService classNameLocalService,
		CompanyLocalService companyLocaService,
		UserLocalService userLocalService) {

		_classNameLocalService = classNameLocalService;
		_companyLocaService = companyLocaService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeCalendarResourceUserIds();
	}

	protected long getCompanyAdminUserId(Company company)
		throws PortalException {

		Role role = RoleLocalServiceUtil.getRole(
			company.getCompanyId(), RoleConstants.ADMINISTRATOR);

		long[] userIds = UserLocalServiceUtil.getRoleUserIds(role.getRoleId());

		return userIds[0];
	}

	protected void updateCalendarUserId(long calendarId, long userId)
		throws SQLException {

		try (PreparedStatement ps = connection.prepareStatement(
				"update Calendar set userId = ? where calendarId = ?")) {

			ps.setLong(1, userId);
			ps.setLong(2, calendarId);

			ps.execute();
		}
	}

	protected void updateCalendarUserIds(
			long groupClassNameId, long defaultUserId, long adminUserId)
		throws SQLException {

		try (PreparedStatement ps = connection.prepareStatement(
				"select Calendar.calendarId from Calendar, CalendarResource " +
					"where CalendarResource.classNameId = ? and " +
						"CalendarResource.userId = ?")) {

			ps.setLong(1, groupClassNameId);
			ps.setLong(2, defaultUserId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long calendarId = rs.getLong(1);

					updateCalendarUserId(calendarId, adminUserId);
				}
			}
		}
	}

	protected void upgradeCalendarResourceUserId(
			long groupClassNameId, long defaultUserId, long companyAdminUserId)
		throws SQLException {

		try (PreparedStatement ps = connection.prepareStatement(
				"update CalendarResource set userId = ? where userId = ? and " +
					"classNameId = ?")) {

			ps.setLong(1, companyAdminUserId);
			ps.setLong(2, defaultUserId);
			ps.setLong(3, groupClassNameId);

			ps.execute();
		}
	}

	protected void upgradeCalendarResourceUserIds()
		throws PortalException, SQLException {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			for (Company company : _companyLocaService.getCompanies()) {
				long classNameId = _classNameLocalService.getClassNameId(
					Group.class);
				long defaultUserId = _userLocalService.getDefaultUserId(
					company.getCompanyId());
				long companyAdminUserId = getCompanyAdminUserId(company);

				upgradeCalendarResourceUserId(
					classNameId, defaultUserId, companyAdminUserId);

				updateCalendarUserIds(
					classNameId, defaultUserId, companyAdminUserId);
			}
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final CompanyLocalService _companyLocaService;
	private final UserLocalService _userLocalService;

}