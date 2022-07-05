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

package com.liferay.object.internal.upgrade.v3_18_0;

import com.liferay.object.model.ObjectEntryTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Locale;

/**
 * @author Mateus Santana
 */
public class ObjectFieldUpgradeProcess extends UpgradeProcess {

	public ObjectFieldUpgradeProcess(PortalUUID portalUUID) {
		_portalUUID = portalUUID;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
			StringBundler.concat("SELECT ObjectDefinition.companyId, ",
				"ObjectDefinition.dbTableName, ",
				"ObjectDefinition.objectDefinitionId, ",
				"ObjectDefinition.userName, ObjectDefinition.userId, ",
				"ObjectDefinition.system_ FROM ObjectDefinition WHERE ",
				"ObjectDefinition.objectDefinitionId NOT IN (SELECT DISTINCT ",
				"ObjectField.objectDefinitionId FROM ObjectField WHERE ",
				"(ObjectField.name = \"creator\" or ObjectField.name = ",
				"\"dateCreated\" or ObjectField.name = \"dateModified\" or ",
				"ObjectField.name = \"id\" or ObjectField.name = \"status\") ",
				"AND ObjectField.system_ = true)"));

			ResultSet resultSet = preparedStatement1.executeQuery()
			 ) {

			while (resultSet.next()) {
				PreparedStatement preparedStatement2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						StringBundler.concat(
							"INSERT INTO ObjectField (mvccVersion, uuid_, ",
							"objectFieldId, companyId, userId, userName, ",
							"createDate, modifiedDate, externalReferenceCode, ",
							"listTypeDefinitionId, objectDefinitionId, ",
							"businessType, dbColumnName, dbTableName, dbType, ",
							"defaultValue, indexed, indexedAsKeyWord, ",
							"indexedLanguageId, label, name, ",
							"relationshipType, required, state_, system_) ",
							"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ",
							"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));

				long companyId = resultSet.getLong("companyId");
				String dbTableName = resultSet.getString("dbTableName");
				Locale defaultLocale = LocaleUtil.fromLanguageId(
					UpgradeProcessUtil.getDefaultLanguageId(companyId));
				Timestamp now = new Timestamp(System.currentTimeMillis());
				long objectDefinitionId = resultSet.getLong(
					"objectDefinitionId");
				long userId = resultSet.getLong("userId");
				String userName = resultSet.getString("userName");

				if (resultSet.getLong("system_") == 0) {
					dbTableName = "ObjectEntry";
				}

				_insertObjectField(
					preparedStatement2, companyId, userId, userName, now,
					objectDefinitionId, "Text",
					ObjectEntryTable.INSTANCE.userName.getName(), dbTableName,
					"String",
					LocalizationUtil.getXml(
						new LocalizedValuesMap() {
							{
								put(
									defaultLocale,
									LanguageUtil.get(defaultLocale, "author"));
							}
						},
						"Label"),
					"creator");
				_insertObjectField(
					preparedStatement2, companyId, userId, userName, now,
					objectDefinitionId, "Date",
					ObjectEntryTable.INSTANCE.createDate.getName(), dbTableName,
					"Date",
					LocalizationUtil.getXml(
						new LocalizedValuesMap() {
							{
								put(
									defaultLocale,
									LanguageUtil.get(
										defaultLocale, "create-date"));
							}
						},
						"Label"),
					"createDate");
				_insertObjectField(
					preparedStatement2, companyId, userId, userName, now,
					objectDefinitionId, "Integer",
					ObjectEntryTable.INSTANCE.objectEntryId.getName(),
					dbTableName, "Integer",
					LocalizationUtil.getXml(
						new LocalizedValuesMap() {
							{
								put(
									defaultLocale,
									LanguageUtil.get(defaultLocale, "id"));
							}
						},
						"Label"),
					"id");
				_insertObjectField(
					preparedStatement2, companyId, userId, userName, now,
					objectDefinitionId, "Date",
					ObjectEntryTable.INSTANCE.modifiedDate.getName(),
					dbTableName, "Date",
					LocalizationUtil.getXml(
						new LocalizedValuesMap() {
							{
								put(
									defaultLocale,
									LanguageUtil.get(
										defaultLocale, "modified-date"));
							}
						},
						"Label"),
					"modifiedDate");
				_insertObjectField(
					preparedStatement2, companyId, userId, userName, now,
					objectDefinitionId, "Text",
					ObjectEntryTable.INSTANCE.status.getName(), dbTableName,
					"Integer",
					LocalizationUtil.getXml(
						new LocalizedValuesMap() {
							{
								put(
									defaultLocale,
									LanguageUtil.get(defaultLocale, "status"));
							}
						},
						"Label"),
					"status");

				preparedStatement2.executeBatch();
			}
		}
	}

	private void _insertObjectField(
			PreparedStatement preparedStatement, long companyId, long userId,
			String userName, Timestamp now, long objectDefinitionId,
			String businessType, String dbColumnName, String dbTableName,
			String dbType, String label, String name)
		throws SQLException {

		String uuid = _portalUUID.generate();

		preparedStatement.setLong(1, 0);
		preparedStatement.setString(2, uuid);
		preparedStatement.setLong(3, increment());
		preparedStatement.setLong(4, companyId);
		preparedStatement.setLong(5, userId);
		preparedStatement.setString(6, userName);
		preparedStatement.setTimestamp(7, now);
		preparedStatement.setTimestamp(8, now);
		preparedStatement.setString(9, uuid);
		preparedStatement.setLong(10, 0);
		preparedStatement.setLong(11, objectDefinitionId);
		preparedStatement.setString(12, businessType);
		preparedStatement.setString(13, dbColumnName);
		preparedStatement.setString(14, dbTableName);
		preparedStatement.setString(15, dbType);
		preparedStatement.setNull(16, 0);
		preparedStatement.setInt(17, 0);
		preparedStatement.setInt(18, 0);
		preparedStatement.setNull(19, 0);
		preparedStatement.setString(20, label);
		preparedStatement.setString(21, name);
		preparedStatement.setNull(22, 0);
		preparedStatement.setInt(23, 0);
		preparedStatement.setInt(24, 0);
		preparedStatement.setInt(25, 1);

		preparedStatement.addBatch();
	}

	private final PortalUUID _portalUUID;

}