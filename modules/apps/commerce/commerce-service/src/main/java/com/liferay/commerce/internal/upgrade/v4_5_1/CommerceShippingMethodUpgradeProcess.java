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

package com.liferay.commerce.internal.upgrade.v4_5_1;

import com.liferay.commerce.internal.upgrade.base.BaseCommerceServiceUpgradeProcess;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Marco Leo
 */
public class CommerceShippingMethodUpgradeProcess
	extends BaseCommerceServiceUpgradeProcess {

	public CommerceShippingMethodUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		GroupLocalService groupLocalService) {

		_classNameLocalService = classNameLocalService;
		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				"select commerceShippingMethodId, groupId from " +
					"CommerceShippingMethod")) {

			PreparedStatement preparedStatement = null;

			while (resultSet.next()) {
				long groupId = resultSet.getLong("groupId");

				long commerceChannelGroupId =
					_getCommerceChannelGroupIdBySiteGroupId(groupId);

				if (commerceChannelGroupId == 0) {
					continue;
				}

				long commerceShippingMethodId = resultSet.getLong(
					"commerceShippingMethodId");

				preparedStatement = connection.prepareStatement(
					"update CommerceShippingMethod set groupId = ? where " +
						"commerceShippingMethodId = ?");

				preparedStatement.setLong(1, commerceChannelGroupId);
				preparedStatement.setLong(2, commerceShippingMethodId);

				preparedStatement.executeUpdate();
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

			try (ResultSet resultSet = s.executeQuery(sql)) {
				if (resultSet.next()) {
					companyId = resultSet.getLong("companyId");
					commerceChannelId = resultSet.getLong("commerceChannelId");
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