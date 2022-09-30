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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
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
				"select virtualHostId, hostname from VirtualHost where " +
					"hostname != LOWER(hostname)");
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"update VirtualHost set hostname = ? where virtualHostId = " +
					"?")) {

			ResultSet resultSet = preparedStatement1.executeQuery();

			while (resultSet.next()) {
				String hostname = resultSet.getString("hostname");

				preparedStatement2.setString(
					1, StringUtil.toLowerCase(hostname));

				long virtualHostId = resultSet.getLong("virtualHostId");

				preparedStatement2.setLong(2, virtualHostId);

				try {
					preparedStatement2.executeUpdate();
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Deleting duplicate virtual host ",
								virtualHostId, " with hostname ", hostname),
							exception);
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