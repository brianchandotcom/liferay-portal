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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;

/**
 * @author Dennis Ju
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateSyncs();
	}

	protected void updateSyncs() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select syncId, modifiedDate from DLSync");

			rs = ps.executeQuery();

			while (rs.next()) {
				long syncId = rs.getLong("syncId");
				Date modifiedDate = rs.getDate("modifiedDate");

				long updateId = modifiedDate.getTime();

				runSQL(
					"update DLSync set updateId = " + updateId +
						" where syncId = " + syncId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}