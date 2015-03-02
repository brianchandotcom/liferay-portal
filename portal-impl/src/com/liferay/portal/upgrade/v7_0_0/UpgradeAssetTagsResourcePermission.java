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
import com.liferay.portlet.asset.model.AssetTag;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Andrew Betts
 */
public class UpgradeAssetTagsResourcePermission extends UpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"delete from ResourcePermission where name = ?");

			ps.setString(1, AssetTag.class.getName());

			ps.execute();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

}