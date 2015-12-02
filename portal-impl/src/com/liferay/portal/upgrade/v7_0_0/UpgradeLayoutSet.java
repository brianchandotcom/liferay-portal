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
import com.liferay.portal.model.Organization;
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

			StringBundler sb = new StringBundler(4);

			sb.append("update LayoutSet set modifiedDate = ?, pageCount = ?, ");
			sb.append("layoutSetPrototypeUuid = ?, ");
			sb.append("layoutSetPrototypeLinkEnabled = ?  where layoutSetId ");
			sb.append("= ?");

			ps = con.prepareStatement(sb.toString());

			Timestamp now = new Timestamp(System.currentTimeMillis());

			ps.setTimestamp(1, now);

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
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(10);

			sb.append("select LayoutSet.layoutSetId, Layout.privateLayout, ");
			sb.append("Group_.groupId, Group_.site from LayoutSet left join ");
			sb.append("Group_ on (Group_.groupId = LayoutSet.groupId) ");
			sb.append("left join Layout on ");
			sb.append("(Layout.groupId = LayoutSet.groupId and ");
			sb.append("Layout.privateLayout = LayoutSet.privateLayout)" );
			sb.append("where LayoutSet.layoutSetPrototypeUuid != '' ");
			sb.append("and LayoutSet.layoutSetPrototypeLinkEnabled = 1 and ");
			sb.append("Group_.classNameId = ");
			sb.append(PortalUtil.getClassNameId(Organization.class));

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long layoutSetId = rs.getLong("layoutSetId");
				String privateLayout = rs.getString("privateLayout");
				long groupId = rs.getLong("groupId");
				boolean isSite = rs.getBoolean("site");

				if (!isSite || (privateLayout == null)) {
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