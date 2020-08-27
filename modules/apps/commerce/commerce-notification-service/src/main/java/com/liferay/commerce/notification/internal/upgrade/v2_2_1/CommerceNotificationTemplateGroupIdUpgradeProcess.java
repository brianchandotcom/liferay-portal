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

package com.liferay.commerce.notification.internal.upgrade.v2_2_1;

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Marco Leo
 */
public class CommerceNotificationTemplateGroupIdUpgradeProcess
	extends UpgradeProcess {

	public CommerceNotificationTemplateGroupIdUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		GroupLocalService groupLocalService) {

		_classNameLocalService = classNameLocalService;
		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (Statement s = connection.createStatement();

			ResultSet rs = s.executeQuery(
				"select commerceNotificationTemplateId, groupId from " +
					"CommerceNotificationTemplate")) {

			PreparedStatement ps = null;

			while (rs.next()) {
				long commerceNotificationTemplateId = rs.getLong(
					"commerceNotificationTemplateId");

				long groupId = rs.getLong("groupId");

				long channelGroupId = _getCommerceChannelGroupIdBySiteGroupId(
					groupId);

				if (channelGroupId == 0) {
					continue;
				}

				ps = connection.prepareStatement(
					"update CommerceNotificationTemplate set groupId = ? " +
						"where commerceNotificationTemplateId = ?");

				ps.setLong(1, channelGroupId);
				ps.setLong(2, commerceNotificationTemplateId);

				ps.executeUpdate();
			}
		}
	}

	private long _getCommerceChannelGroupIdBySiteGroupId(long groupId)
		throws SQLException {

		long companyId = 0;
		long commerceChannelId = 0;

		String sql =
			"select * from CommerceChannel where siteGroupId = " + groupId;

		try (Statement s = connection.createStatement()) {
			s.setMaxRows(1);

			try (ResultSet rs = s.executeQuery(sql)) {
				if (rs.next()) {
					companyId = rs.getLong("companyId");
					commerceChannelId = rs.getLong("commerceChannelId");
				}
			}
		}

		long classNameId = _classNameLocalService.getClassNameId(
			CommerceChannel.class.getName());

		Group group = _groupLocalService.fetchGroup(
			companyId, classNameId, commerceChannelId);

		if (group != null) {
			return group.getGroupId();
		}

		return 0;
	}

	private final ClassNameLocalService _classNameLocalService;
	private final GroupLocalService _groupLocalService;

}