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
import com.liferay.portal.kernel.util.StringPool;
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

	protected void updateLayoutSet(long layoutSetId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			ps = con.prepareStatement(
				"update LayoutSet set modifiedDate = ?, pageCount = ?," +
				" layoutSetPrototypeUuid = ?," +
				" layoutSetPrototypeLinkEnabled = ?  where layoutSetId = ?");

			ps.setTimestamp(1, timestamp);
			ps.setInt(2, 0);
			ps.setString(3, StringPool.BLANK);
			ps.setBoolean(4, false);
			ps.setLong(5, layoutSetId);

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
			sb.append("select LayoutSet.layoutSetId, Layout.privateLayout,");
			sb.append(" Group_.groupId, Group_.site, Group_.classNameId");
			sb.append(" from LayoutSet left join Group_ on");
			sb.append(" (Group_.groupId = LayoutSet.groupId) left join");
			sb.append(" Layout on (Layout.groupId = LayoutSet.groupId");
			sb.append(" and Layout.privateLayout = LayoutSet.privateLayout)");
			sb.append(" where LayoutSet.layoutSetPrototypeUuid != ''");
			sb.append(" and LayoutSet.layoutSetPrototypeLinkEnabled = 1");

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long layoutSetId = rs.getLong("layoutSetId");
				String privateLayout = rs.getString("privateLayout");
				long groupId = rs.getLong("groupId");
				boolean isSite = rs.getBoolean("site");
				long orgClassNameId = rs.getLong("classNameId");

				if ((classNameId == orgClassNameId) &&
					(!isSite || (privateLayout == null))) {
						updateLayoutSet(layoutSetId);

						runSQL(
							"update Group_ set site = FALSE where groupId = " +
								groupId);
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