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

package com.liferay.fragment.internal.upgrade.v1_1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ricardo Couso
 */
public class UpgradePortletPreferences extends UpgradeProcess {

	public UpgradePortletPreferences(LayoutLocalService layoutLocalService) {
		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_computeControlPanelPlids();

		if (_groupControlPanelPlids.isEmpty()) {
			return;
		}
	}

	private void _computeControlPanelPlids() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("select Layout.plid, Group_.groupKey from Layout inner ");
		sb.append("join Group_ on Layout.groupId = Group_.groupId where ");
		sb.append("Layout.type_ = '");
		sb.append(LayoutConstants.TYPE_CONTROL_PANEL);
		sb.append("';");

		try (PreparedStatement ps = connection.prepareStatement(sb.toString());
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				String groupKey = rs.getString("groupKey");

				long plid = rs.getLong("plid");

				Layout layout = _layoutLocalService.getLayout(plid);

				if (groupKey.equals(GroupConstants.CONTROL_PANEL)) {
					_companyControlPanelPlids.put(layout.getCompanyId(), plid);
				}
				else {
					_groupControlPanelPlids.put(layout.getGroupId(), plid);
				}
			}
		}
	}

	private static final Map<Long, Long> _companyControlPanelPlids =
		new HashMap<>();
	private static final Map<Long, Long> _groupControlPanelPlids =
		new HashMap<>();

	private final LayoutLocalService _layoutLocalService;

}