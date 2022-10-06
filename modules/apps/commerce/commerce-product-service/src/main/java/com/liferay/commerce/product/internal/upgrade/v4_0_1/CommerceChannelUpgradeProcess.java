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

package com.liferay.commerce.product.internal.upgrade.v4_0_1;

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Riccardo Ferrari
 */
public class CommerceChannelUpgradeProcess extends UpgradeProcess {

	public CommerceChannelUpgradeProcess(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select CommerceChannel.siteGroupId, Group_.groupId, ",
					"Group_.typeSettings from CommerceChannel inner join ",
					"Group_ on CommerceChannel.commerceChannelId = ",
					"Group_.classPK and Group_.classNameId = ",
					ClassNameLocalServiceUtil.getClassNameId(
						CommerceChannel.class)));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long siteGroupId = resultSet.getLong(1);

				if (siteGroupId == 0) {
					continue;
				}

				_updateGroupTypeSetting(
					resultSet.getLong(2), siteGroupId, resultSet.getString(3));
			}
		}
	}

	private void _updateGroupTypeSetting(
			long groupId, long siteGroupId, String typeSettings)
		throws Exception {

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.create(
				true
			).fastLoad(
				typeSettings
			).setProperty(
				"siteGroupId", String.valueOf(siteGroupId)
			).build();

		_groupLocalService.updateGroup(
			groupId, typeSettingsUnicodeProperties.toString());
	}

	private final GroupLocalService _groupLocalService;

}