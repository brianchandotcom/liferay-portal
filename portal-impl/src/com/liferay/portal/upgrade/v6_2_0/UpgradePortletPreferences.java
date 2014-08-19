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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Julio Camarero
 */
public class UpgradePortletPreferences extends UpgradeProcess {

	protected void deletePortletPreferences(long portletPreferencesId)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Deleting portlet preferences " + portletPreferencesId);
		}

		runSQL(
			"delete from PortletPreferences where portletPreferencesId = " +
				portletPreferencesId);
	}

	@Override
	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("select PortletPreferences.portletPreferencesId, ");
			sb.append("PortletPreferences.plid,");
			sb.append("PortletPreferences.portletId, Layout.typeSettings ");
			sb.append("from PortletPreferences inner join Layout on ");
			sb.append("PortletPreferences.plid = Layout.plid where ");
			sb.append("preferences like '%<portlet-preferences />%'");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				long portletPreferencesId = rs.getLong("portletPreferencesId");
				String portletId = GetterUtil.getString(
					rs.getString("portletId"));
				String typeSettings = GetterUtil.getString(
					rs.getString("typeSettings"));

				if (typeSettings.contains(portletId)) {
					continue;
				}

				deletePortletPreferences(portletPreferencesId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletPreferences.class);

}