/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.upgrade.v1_0_5;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

/**
 * @author Adam Brandizzi
 */
public class CalendarResourceUpgradeProcess extends UpgradeProcess {

	public CalendarResourceUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		CompanyLocalService companyLocalService,
		UserLocalService userLocalService) {

		_classNameLocalService = classNameLocalService;
		_companyLocalService = companyLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeCalendarResourceUserIds();
	}

	private long _getCompanyAdminUserId(Company company)
		throws PortalException {

		Role role = RoleLocalServiceUtil.getRole(
			company.getCompanyId(), RoleConstants.ADMINISTRATOR);

		long[] userIds = UserLocalServiceUtil.getRoleUserIds(role.getRoleId());

		if (!ArrayUtil.isEmpty(userIds)) {
			return userIds[0];
		}

		List<Group> groups = GroupLocalServiceUtil.getRoleGroups(
			role.getRoleId());

		for (Group group : groups) {
			if (group.isOrganization()) {
				userIds = OrganizationLocalServiceUtil.getUserPrimaryKeys(
					group.getClassPK());

				if (!ArrayUtil.isEmpty(userIds)) {
					return userIds[0];
				}
			}
			else if (group.isRegularSite()) {
				userIds = GroupLocalServiceUtil.getUserPrimaryKeys(
					group.getGroupId());

				if (!ArrayUtil.isEmpty(userIds)) {
					return userIds[0];
				}
			}
			else if (group.isUserGroup()) {
				userIds = UserGroupLocalServiceUtil.getUserPrimaryKeys(
					group.getClassPK());

				if (!ArrayUtil.isEmpty(userIds)) {
					return userIds[0];
				}
			}
		}

		throw new UpgradeException(
			"Unable to find an administrator user in company " +
				company.getCompanyId());
	}

	private void _updateCalendarUserId(long calendarId, long userId)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update Calendar set userId = ? where calendarId = ?")) {

			preparedStatement.setLong(1, userId);
			preparedStatement.setLong(2, calendarId);

			preparedStatement.execute();
		}
	}

	private void _updateCalendarUserIds(
			long groupClassNameId, long guestUserId, long adminUserId)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select Calendar.calendarId from Calendar join ",
					"CalendarResource on Calendar.calendarResourceId = ",
					"CalendarResource.calendarResourceId where ",
					"CalendarResource.classNameId = ? and ",
					"CalendarResource.userId = ?"))) {

			preparedStatement.setLong(1, groupClassNameId);
			preparedStatement.setLong(2, guestUserId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long calendarId = resultSet.getLong(1);

					_updateCalendarUserId(calendarId, adminUserId);
				}
			}
		}
	}

	private void _upgradeCalendarResourceUserId(
			long groupClassNameId, long guestUserId, long companyAdminUserId)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update CalendarResource set userId = ? where userId = ? and " +
					"classNameId = ?")) {

			preparedStatement.setLong(1, companyAdminUserId);
			preparedStatement.setLong(2, guestUserId);
			preparedStatement.setLong(3, groupClassNameId);

			preparedStatement.execute();
		}
	}

	private void _upgradeCalendarResourceUserIds() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_companyLocalService.forEachCompany(
				company -> {
					long classNameId = _classNameLocalService.getClassNameId(
						Group.class);
					long guestUserId = _userLocalService.getGuestUserId(
						company.getCompanyId());
					long companyAdminUserId = _getCompanyAdminUserId(company);

					_updateCalendarUserIds(
						classNameId, guestUserId, companyAdminUserId);

					_upgradeCalendarResourceUserId(
						classNameId, guestUserId, companyAdminUserId);
				});
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final CompanyLocalService _companyLocalService;
	private final UserLocalService _userLocalService;

}