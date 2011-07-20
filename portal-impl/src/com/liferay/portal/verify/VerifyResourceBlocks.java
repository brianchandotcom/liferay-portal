/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Connor McKay
 */
public class VerifyResourceBlocks extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			return;
		}

		for (String[] model : _MODELS) {
			verifyModel(model[0], model[1], model[2]);
		}
	}

	protected void verifyModel(
			String name, String tableName, String pkColumnName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"SELECT " + pkColumnName + ", companyId " +	" FROM " +
				tableName);

			rs = ps.executeQuery();

			while (rs.next()) {
				long primKey = rs.getLong(pkColumnName);
				long companyId = rs.getLong("companyId");

				ResourceBlock resourceBlock = ResourceBlockLocalServiceUtil.verifyResourceBlockId(companyId, name, primKey);

				if (_log.isInfoEnabled() &&
					(resourceBlock.getResourceBlockId() % 100 == 0)) {

					_log.info("Processed 100 resource blocks for " + name);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String[][] _MODELS = new String[][] {
		new String[] {
			BookmarksEntry.class.getName(),
			"BookmarksEntry",
			"entryId"
		},
		new String[] {
			BookmarksFolder.class.getName(),
			"BookmarksFolder",
			"folderId"
		}
	};

	private static Log _log = LogFactoryUtil.getLog(
		VerifyResourcePermissions.class);

}