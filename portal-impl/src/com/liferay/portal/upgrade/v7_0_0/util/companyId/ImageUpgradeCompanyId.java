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

package com.liferay.portal.upgrade.v7_0_0.util.companyId;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.UpgradeCompanyIdInTable;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Cristina González
 */
public class ImageUpgradeCompanyId implements UpgradeCompanyIdInTable {

	@Override
	public String getTableName() {
		return "Image";
	}

	@Override
	public void upgradeProcess() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select imageId from Image");

			rs = ps.executeQuery();

			while (rs.next()) {
				long imageId = rs.getLong("imageId");

				long companyId = _DEFAULT_COMPANY_ID;

				if (PropsValues.WEB_SERVER_SERVLET_CHECK_IMAGE_GALLERY) {
					PreparedStatement ps2 = con.prepareStatement(
						"select companyId from DLFileEntry " +
							"where largeImageId = " + imageId);

					ResultSet rs2 = ps2.executeQuery();

					companyId = rs2.getLong("companyId");
				}

				DB db = DBFactoryUtil.getDB();

				db.runSQL(
					"update Image set companyId = " + companyId +
						" where imageId = " + imageId);
			}
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn("The companyId was not updated in Image table", sqle);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final long _DEFAULT_COMPANY_ID = 0;

	private static final Log _log = LogFactoryUtil.getLog(
		ImageUpgradeCompanyId.class);

}