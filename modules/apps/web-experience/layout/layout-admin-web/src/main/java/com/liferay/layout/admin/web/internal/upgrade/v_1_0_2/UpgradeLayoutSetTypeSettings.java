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

package com.liferay.layout.admin.web.internal.upgrade.v_1_0_2;

import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alec Shay
 */
public class UpgradeLayoutSetTypeSettings extends UpgradeProcess {

	public UpgradeLayoutSetTypeSettings(
		GroupLocalService groupLocalService,
		LayoutSetLocalService layoutSetLocalService) {

		_groupLocalService = groupLocalService;
		_layoutSetLocalService = layoutSetLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateRobots();
	}

	protected void updateLayoutSetTypeSettings(
			String key, String property, long groupId, boolean privateLayout)
		throws Exception {

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			groupId, privateLayout);

		UnicodeProperties typeSettings = layoutSet.getSettingsProperties();

		typeSettings.setProperty(key, property);

		_layoutSetLocalService.updateSettings(
			groupId, privateLayout, typeSettings.toString());
	}

	protected void updateRobots() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select groupId, typeSettings from Group_ where typeSettings " +
					"like '%robots.txt%'")) {

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					String typeSettings = rs.getString("typeSettings");

					UnicodeProperties typeSettingsProperties =
						new UnicodeProperties();

					typeSettingsProperties.load(typeSettings);

					String privateRobots = typeSettingsProperties.getProperty(
						"true-robots.txt");

					if (privateRobots != null) {
						updateLayoutSetTypeSettings(
							"true-robots.txt", privateRobots, groupId, true);

						typeSettingsProperties.remove("true-robots.txt");
					}

					String publicRobots = typeSettingsProperties.getProperty(
						"false-robots.txt");

					if (publicRobots != null) {
						updateLayoutSetTypeSettings(
							"false-robots.txt", publicRobots, groupId, false);

						typeSettingsProperties.remove("false-robots.txt");
					}

					_groupLocalService.updateGroup(
						groupId, typeSettingsProperties.toString());
				}
			}
		}
	}

	private final GroupLocalService _groupLocalService;
	private final LayoutSetLocalService _layoutSetLocalService;

}