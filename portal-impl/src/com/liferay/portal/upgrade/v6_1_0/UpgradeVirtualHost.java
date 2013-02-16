/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alexander Chow
 */
public class UpgradeVirtualHost extends UpgradeProcess {

	protected void addVirtualHost(
			long virtualHostId, long companyId, long layoutSetId,
			String hostname)
		throws Exception {

		runSQL(
			"insert into VirtualHost (virtualHostId, companyId, layoutSetId, " +
				"hostname) values (" + virtualHostId + ", " + companyId +
					", " + layoutSetId + ", '" + hostname + "')");
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateCompany();
		updateLayoutSet();
	}

	protected void updateCompany() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			String sql = SQLTransformer.transform(
				"select companyId, virtualHost from Company where " +
					"virtualHost is not empty");

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				String hostname = rs.getString("virtualHost");

				long virtualHostId = increment();

				addVirtualHost(virtualHostId, companyId, 0, hostname);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		runSQL("alter table Company drop column virtualHost");
	}

	protected void updateLayoutSet() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			String sql = SQLTransformer.transform(
				"select layoutSetId, companyId, virtualHost from LayoutSet " +
					"where virtualHost is not empty");

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				long layoutSetId = rs.getLong("layoutSetId");
				long companyId = rs.getLong("companyId");
				String hostname = rs.getString("virtualHost");

				long virtualHostId = increment();

				addVirtualHost(virtualHostId, companyId, layoutSetId, hostname);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		runSQL("alter table LayoutSet drop column virtualHost");
	}

}