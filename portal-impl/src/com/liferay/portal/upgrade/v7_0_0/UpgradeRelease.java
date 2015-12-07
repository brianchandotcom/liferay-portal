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
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Miguel Pastor
 */
public class UpgradeRelease extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select distinct buildNumber from Release_ " +
					"where schemaVersion is null");

			rs = ps.executeQuery();

			while (rs.next()) {
				String buildNumber = rs.getString("buildNumber");

				String schemaVersion = toSchemaVersion(buildNumber);

				runSQL(
					"update Release_ set schemaVersion = '" + schemaVersion +
						"' where buildNumber = " + buildNumber +
							" and schemaVersion is null");
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected String toSchemaVersion(String buildNumber) {
		char[] chars = buildNumber.toCharArray();

		StringBundler sb = new StringBundler(2 * chars.length);

		int i = 0;

		for (; i < chars.length - 1; i++) {
			sb.append(chars[i]);
			sb.append(".");
		}

		sb.append(chars[i]);

		return sb.toString();
	}

}