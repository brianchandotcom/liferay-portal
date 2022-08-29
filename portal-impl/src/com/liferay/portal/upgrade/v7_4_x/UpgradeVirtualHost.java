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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Christopher Kian
 */
public class UpgradeVirtualHost extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select * from VirtualHost where hostname != " +
					"LOWER(hostname)")) {

			ResultSet resultSet = preparedStatement1.executeQuery();

			while (resultSet.next()) {
				String hostname = resultSet.getString("hostname");

				long virtualHostId = resultSet.getLong("virtualHostId");

				try (PreparedStatement preparedStatement2 =
						connection.prepareStatement(
							StringBundler.concat(
								"update VirtualHost set hostname = '",
								StringUtil.toLowerCase(hostname),
								"' WHERE virtualHostId = ",
								String.valueOf(virtualHostId)))) {

					preparedStatement2.executeUpdate();
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to convert virtual hostname to " +
								"lowercase. Deleting entry instead.");
					}

					runSQL(
						"delete from VirtualHost where virtualHostId = " +
							virtualHostId);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeVirtualHost.class);

}