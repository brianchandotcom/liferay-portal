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

package com.liferay.fragment.internal.upgrade.v1_0_2;

import com.liferay.layout.util.GroupControlPanelLayoutUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ricardo Couso
 */
public class UpgradePortletPreferences extends UpgradeProcess {

	protected void createGroupControlPanelLayouts() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select distinct FragmentEntryLink.groupId from " +
					"FragmentEntryLink inner join Group_ on " +
						"FragmentEntryLink.groupId = Group_.groupId");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long groupId = rs.getLong("groupId");

				Group group = GroupLocalServiceUtil.getGroup(groupId);

				long groupControlPanelPlid =
					GroupControlPanelLayoutUtil.getGroupControlPanelPlid(group);

				_groupIdGroupControlPanelPlids.put(
					groupId, groupControlPanelPlid);
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		createGroupControlPanelLayouts();
		populateCompanyControlPanelPlids();
		updatePortletPreferences();
	}

	protected void populateCompanyControlPanelPlids() throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append("select Layout.plid from Layout inner join Group_ on ");
		sb.append("Layout.groupId = Group_.groupId where Group_.groupKey = '");
		sb.append(GroupConstants.CONTROL_PANEL);
		sb.append("'");

		try (PreparedStatement ps = connection.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long controlPanelPlid = rs.getLong("plid");

				_companyControlPanelPlids.add(controlPanelPlid);
			}
		}
	}

	protected void updatePortletPreferences() throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("select FragmentEntryLink.groupId, ");
		sb.append("PortletPreferences.plid, ");
		sb.append("PortletPreferences.portletPreferencesId from ");
		sb.append("FragmentEntryLink inner join PortletPreferences on ");
		sb.append("PortletPreferences.portletId like CONCAT('%_INSTANCE_', ");
		sb.append("FragmentEntryLink.namespace)");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update PortletPreferences set plid = ? where " +
						"portletPreferencesId = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long portletPreferencesId = rs.getLong("portletPreferencesId");
				long plid = rs.getLong("plid");

				if (_companyControlPanelPlids.contains(plid)) {
					long groupId = rs.getLong("groupId");

					ps2.setLong(1, _groupIdGroupControlPanelPlids.get(groupId));

					ps2.setLong(2, portletPreferencesId);

					ps2.addBatch();
				}
			}

			ps2.executeBatch();
		}
	}

	private final List<Long> _companyControlPanelPlids = new ArrayList<>();
	private final Map<Long, Long> _groupIdGroupControlPanelPlids =
		new HashMap<>();

}