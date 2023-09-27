/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model.dao;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ReleaseConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Adolfo Pérez
 */
public class ReleaseDAO {

	public void addRelease(Connection connection, String bundleSymbolicName)
		throws SQLException {

		if (hasRelease(connection, bundleSymbolicName)) {
			return;
		}

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into Release_ (mvccVersion, releaseId, ",
					"createDate, modifiedDate, servletContextName, ",
					"schemaVersion, buildNumber, buildDate, verified, state_, ",
					"testString) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))) {

			preparedStatement.setLong(1, 0);
			preparedStatement.setLong(2, increment());
			preparedStatement.setTimestamp(3, timestamp);
			preparedStatement.setTimestamp(4, timestamp);
			preparedStatement.setString(5, bundleSymbolicName);
			preparedStatement.setString(6, "0.0.1");
			preparedStatement.setInt(7, 001);
			preparedStatement.setTimestamp(8, timestamp);
			preparedStatement.setBoolean(9, false);
			preparedStatement.setInt(10, 0);
			preparedStatement.setString(11, ReleaseConstants.TEST_STRING);

			preparedStatement.execute();
		}
	}

	protected boolean hasRelease(
			Connection connection, String bundleSymbolicName)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from Release_ where servletContextName = ?")) {

			preparedStatement.setString(1, bundleSymbolicName);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next();
			}
		}
	}

	protected long increment() {
		return CounterLocalServiceUtil.increment();
	}

}