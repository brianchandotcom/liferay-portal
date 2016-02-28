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

package com.liferay.portal.upgrade.v5_2_5_to_6_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Wesley Gong
 */
public class UpgradeGroup extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateParentGroupId();
	}

	protected Object[] getLayout(long plid) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(_GET_LAYOUT)) {
			ps.setLong(1, plid);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					long groupId = rs.getLong("groupId");

					return new Object[] {groupId};
				}

				return null;
			}
		}
	}

	protected void updateParentGroupId() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long classNameId = PortalUtil.getClassNameId(
				"com.liferay.portal.model.Layout");

			try (PreparedStatement ps = connection.prepareStatement(
					"select groupId, classPK from Group_ where classNameId = " +
						classNameId);
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					long classPK = rs.getLong("classPK");

					Object[] layout = getLayout(classPK);

					if (layout != null) {
						long layoutGroupId = (Long)layout[0];

						runSQL(
							"update Group_ set parentGroupId = " +
								layoutGroupId + " where groupId = " + groupId);
					}
				}
			}
		}
	}

	private static final String _GET_LAYOUT =
		"select groupId from Layout where plid = ?";

}