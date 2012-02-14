/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_1;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jorge Ferrer
 */
public class UpgradeLayout extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateSourcePrototypeLayoutUuid();
	}

	protected long getLayoutPrototypeGroupId(String layoutPrototypeUuid)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select groupId from Group_ where classPK = (select " +
					"layoutPrototypeId from LayoutPrototype where uuid_ = ?)");

			ps.setString(1, layoutPrototypeUuid);

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");

				return groupId;
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return 0;
	}

	protected boolean isGroupPrivateLayout(
			long groupId, String sourcePrototypeLayoutUuid)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select count(*) from Layout where groupId = ? and " +
					"privateLayout = ? and uuid_ = ?");

			ps.setLong(1, groupId);
			ps.setBoolean(2, true);
			ps.setString(3, sourcePrototypeLayoutUuid);

			rs = ps.executeQuery();

			while (rs.next()) {
				long count = rs.getLong(1);

				if (count > 0) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return false;
	}

	protected void updateSourcePrototypeLayoutUuid() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			// Get pages with a sourcePrototypeLayoutUuid which have a page
			// template. If the layoutUuid points to the page template, remove
			// it. Otherwise, it points to a site template page, so leave it.

			ps = con.prepareStatement(
				"select plid, layoutPrototypeUuid, sourcePrototypeLayoutUuid " +
					"from Layout where layoutPrototypeUuid is not null and" +
						" layoutPrototypeUuid != '' and " +
							"sourcePrototypeLayoutUuid is not null and" +
								" sourcePrototypeLayoutUuid != ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");
				String layoutPrototypeUuid = rs.getString(
					"layoutPrototypeUuid");
				String sourcePrototypeLayoutUuid = rs.getString(
					"sourcePrototypeLayoutUuid");

				long groupId = getLayoutPrototypeGroupId(layoutPrototypeUuid);

				if (groupId == 0) {
					continue;
				}

				if (isGroupPrivateLayout(groupId, sourcePrototypeLayoutUuid)) {
					runSQL(
						"update Layout set sourcePrototypeLayoutUuid = null " +
							"where plid = " + plid);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}