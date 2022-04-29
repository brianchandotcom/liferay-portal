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

package com.liferay.client.extension.internal.upgrade.v3_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Brian Wing Shun Chan
 */
public class ClientExtensionEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_insertClientExtensionEntries();

		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.remote.app.service'");

		runSQL("drop table RemoteAppEntry");

		runSQL(
			"delete from ServiceComponent where buildNamespace = 'RemoteApp'");
	}

	private void _insertClientExtensionEntries() throws Exception {
		String selectSQL = StringBundler.concat(
			"select mvccVersion, uuid_, externalReferenceCode, ",
			"remoteAppEntryId, companyId, userId, userName, createDate, ",
			"modifiedDate, customElementCSSURLs, ",
			"customElementHTMLElementName, customElementURLs, ",
			"customElementUseESM, description, friendlyURLMapping, iFrameURL, ",
			"instanceable, name, portletCategoryName, properties, ",
			"sourceCodeURL, type_, status, statusByUserId, statusByUserName, ",
			"statusDate from RemoteAppEntry");
		String insertSQL = StringBundler.concat(
			"insert into ClientExtensionEntry (mvccVersion, uuid_, ",
			"externalReferenceCode, clientExtensionEntryId, companyId, ",
			"userId, userName, createDate, modifiedDate, ",
			"customElementCSSURLs, customElementHTMLElementName, ",
			"customElementURLs, customElementUseESM, description, ",
			"friendlyURLMapping, iFrameURL, instanceable, name, ",
			"portletCategoryName, properties, sourceCodeURL, type_, status, ",
			"statusByUserId, statusByUserName, statusDate) values (?, ?, ?, ",
			"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ",
			"?, ?)");

		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(selectSQL);
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(insertSQL))) {

			while (resultSet.next()) {
				long mvccVersion = resultSet.getLong("mvccVersion");
				String uuid = resultSet.getString("uuid_");
				String externalReferenceCode = resultSet.getString(
					"externalReferenceCode");
				long remoteAppEntryId = resultSet.getLong("remoteAppEntryId");
				long companyId = resultSet.getLong("companyId");
				long userId = resultSet.getLong("userId");
				String userName = resultSet.getString("userName");
				Date createDate = resultSet.getDate("createDate");
				Date modifiedDate = resultSet.getDate("modifiedDate");
				String customElementCSSURLs = resultSet.getString(
					"customElementCSSURLs");
				String customElementHTMLElementName = resultSet.getString(
					"customElementHTMLElementName");
				String customElementURLs = resultSet.getString(
					"customElementURLs");
				boolean customElementUseESM = resultSet.getBoolean(
					"customElementUseESM");
				String description = resultSet.getString("description");
				String friendlyURLMapping = resultSet.getString(
					"friendlyURLMapping");
				String iFrameURL = resultSet.getString("iFrameURL");
				boolean instanceable = resultSet.getBoolean("instanceable");
				String name = resultSet.getString("name");
				String portletCategoryName = resultSet.getString(
					"portletCategoryName");
				String properties = resultSet.getString("properties");
				String sourceCodeURL = resultSet.getString("sourceCodeURL");
				String type = resultSet.getString("type_");
				int status = resultSet.getInt("status");
				long statusByUserId = resultSet.getLong("statusByUserId");
				String statusByUserName = resultSet.getString(
					"statusByUserName");
				Date statusDate = resultSet.getDate("statusDate");

				preparedStatement.setLong(1, mvccVersion);
				preparedStatement.setString(2, uuid);
				preparedStatement.setString(3, externalReferenceCode);
				preparedStatement.setLong(4, remoteAppEntryId);
				preparedStatement.setLong(5, companyId);
				preparedStatement.setLong(6, userId);
				preparedStatement.setString(7, userName);
				preparedStatement.setDate(8, createDate);
				preparedStatement.setDate(9, modifiedDate);
				preparedStatement.setString(10, customElementCSSURLs);
				preparedStatement.setString(11, customElementHTMLElementName);
				preparedStatement.setString(12, customElementURLs);
				preparedStatement.setBoolean(13, customElementUseESM);
				preparedStatement.setString(14, description);
				preparedStatement.setString(15, friendlyURLMapping);
				preparedStatement.setString(16, iFrameURL);
				preparedStatement.setBoolean(17, instanceable);
				preparedStatement.setString(18, name);
				preparedStatement.setString(19, portletCategoryName);
				preparedStatement.setString(20, properties);
				preparedStatement.setString(21, sourceCodeURL);
				preparedStatement.setString(22, type);
				preparedStatement.setInt(23, status);
				preparedStatement.setLong(24, statusByUserId);
				preparedStatement.setString(25, statusByUserName);
				preparedStatement.setDate(26, statusDate);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClientExtensionEntryUpgradeProcess.class);

}