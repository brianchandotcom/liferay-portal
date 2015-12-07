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

package com.liferay.marketplace.upgrade.v1_0_0;

import com.liferay.marketplace.util.ContextUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Joan Kim
 */
public class UpgradeModule extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateModules();
	}

	protected void updateModules() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select moduleId, contextName from Marketplace_Module");

			rs = ps.executeQuery();

			while (rs.next()) {
				long moduleId = rs.getLong("moduleId");
				String contextName = rs.getString("contextName");

				String newContextName = null;

				try {
					newContextName = ContextUtil.getContextName(contextName);

					runSQL(
						"update Marketplace_Module set contextName = '" +
							newContextName + "' where moduleId = " + moduleId);
				}
				catch (IOException ioe) {
					_log.error(
						"Unable to update module + " + moduleId +
							" with the new context name " + newContextName,
						ioe);
				}
			}
		}
		catch (SQLException sqle) {
			_log.error("Unable to update modules", sqle);
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(UpgradeModule.class);

}