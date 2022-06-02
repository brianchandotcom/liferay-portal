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

package com.liferay.marketplace.internal.upgrade.v1_0_0;

import com.liferay.marketplace.internal.util.ContextUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Joan Kim
 * @author Ryan Park
 */
public class ModuleUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateModules();
	}

	protected void updateModules() throws Exception {
		alterTableAddColumn(
			"Marketplace_Module", "bundleSymbolicName", "VARCHAR(500)");
		alterTableAddColumn(
			"Marketplace_Module", "bundleVersion", "VARCHAR(75)");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select moduleId, contextName from Marketplace_Module");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long moduleId = resultSet.getLong("moduleId");
				String contextName = resultSet.getString("contextName");

				String newContextName = null;

				try {
					newContextName = ContextUtil.getContextName(contextName);

					runSQL(
						StringBundler.concat(
							"update Marketplace_Module set contextName = '",
							newContextName, "' where moduleId = ", moduleId));
				}
				catch (IOException ioException) {
					_log.error(
						StringBundler.concat(
							"Unable to update module + ", moduleId,
							" with the new context name ", newContextName),
						ioException);
				}
			}
		}
		catch (SQLException sqlException) {
			_log.error("Unable to update modules", sqlException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModuleUpgradeProcess.class);

}