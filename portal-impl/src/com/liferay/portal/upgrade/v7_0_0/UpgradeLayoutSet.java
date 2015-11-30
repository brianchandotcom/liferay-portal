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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PortalUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Mohit Soni
 */
public class UpgradeLayoutSet extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeLayoutSet();
	}

	protected void updateGroupSite(long groupId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update Group_ set site = ? where groupId = ?");

			ps.setInt(1, 0);
			ps.setLong(2, groupId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to update Group " + groupId, e);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateLayoutSet(long layoutSetId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			ps = con.prepareStatement(
				"update LayoutSet set layoutSetPrototypeLinkEnabled = ? ," +
					" layoutSetPrototypeUuid = '' , modifiedDate = ?," +
						"pageCount = ? where layoutSetId = ?");

			ps.setInt(1, 0);
			ps.setTimestamp(2, timestamp);
			ps.setInt(3, 0);
			ps.setLong(4, layoutSetId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to update layoutSet " + layoutSetId, e);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void upgradeLayoutSet() throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			"com.liferay.portal.model.Organization");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(8);
			sb.append("select LayoutSet.layoutSetId, Group_.groupId,");
			sb.append("Layout.privateLayout, Group_.site, Group_.classNameId ");
			sb.append("from LayoutSet left join Group_ on ");
			sb.append("(Group_.groupid = LayoutSet.groupid) left join");
			sb.append(" Layout on (Layout.groupid = LayoutSet.groupid ");
			sb.append("and Layout.privateLayout = LayoutSet.privateLayout) ");
			sb.append("where LayoutSet.layoutSetPrototypeUuid != ''");
			sb.append("and LayoutSet.layoutSetPrototypeLinkEnabled = 1 ");

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long layoutSetId = rs.getLong("layoutSetId");
				long groupId = rs.getLong("groupId");
				String privateLayout = rs.getString("privateLayout");
				boolean isSite = rs.getBoolean("site");
				long orgClassNameId = rs.getLong("classNameId");

				boolean isOrganization =
					orgClassNameId == classNameId ? true : false;
				boolean updateLayoutSetRequired = false;

				if (isSite) {
					if ((privateLayout == null) && isOrganization) {
						updateLayoutSetRequired = true;
						}
				}
				else if (isOrganization) {
					updateLayoutSetRequired = true;
				}

				if (updateLayoutSetRequired) {
					updateLayoutSet(layoutSetId);

					updateGroupSite(groupId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeLayoutSet.class);

}