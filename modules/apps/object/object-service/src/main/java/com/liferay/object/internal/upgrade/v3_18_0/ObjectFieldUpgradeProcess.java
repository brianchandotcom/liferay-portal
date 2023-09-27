/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v3_18_0;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
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
				SQLTransformer.transform(
					StringBundler.concat(
						"select ObjectDefinition.companyId, ",
						"ObjectDefinition.dbTableName, ",
						"ObjectDefinition.objectDefinitionId, ",
						"ObjectDefinition.userName, ObjectDefinition.userId, ",
						"ObjectDefinition.system_ from ObjectDefinition where ",
						"ObjectDefinition.objectDefinitionId not in (select ",
						"distinct ObjectField.objectDefinitionId from ",
						"ObjectField where (ObjectField.name = 'creator' or ",
						"ObjectField.name = 'createDate' or ObjectField.name ",
						"= 'id' or ObjectField.name = 'modifiedDate' or ",
						"ObjectField.name = 'status') and ObjectField.system_ ",
						"= [$TRUE$])")));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into ObjectField (mvccVersion, uuid_, ",
						"objectFieldId, companyId, userId, userName, ",
						"createDate, modifiedDate, externalReferenceCode, ",
						"listTypeDefinitionId, objectDefinitionId, ",
						"businessType, dbColumnName, dbTableName, dbType, ",
						"defaultValue, indexed, indexedAsKeyWord, ",
						"indexedLanguageId, label, name, relationshipType, ",
						"required, state_, system_) values (?, ?, ?, ?, ?, ?, ",
						"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ",
						"?, ?)"));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");
				String dbTableName = resultSet.getString("dbTableName");
				Locale defaultLocale = LocaleUtil.fromLanguageId(
					UpgradeProcessUtil.getDefaultLanguageId(companyId));
				long objectDefinitionId = resultSet.getLong(
					"objectDefinitionId");
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				long userId = resultSet.getLong("userId");
				String userName = resultSet.getString("userName");

				if (!resultSet.getBoolean("system_")) {
					dbTableName = "ObjectEntry";
				}

				_insertObjectField(
					preparedStatement2, companyId, userId, userName, timestamp,
					objectDefinitionId, ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectEntryTable.INSTANCE.userName.getName(), dbTableName,
					ObjectFieldConstants.DB_TYPE_STRING,
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
					preparedStatement2, companyId, userId, userName, timestamp,
					objectDefinitionId, ObjectFieldConstants.BUSINESS_TYPE_DATE,
					ObjectEntryTable.INSTANCE.createDate.getName(), dbTableName,
					ObjectFieldConstants.DB_TYPE_DATE,
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
					preparedStatement2, companyId, userId, userName, timestamp,
					objectDefinitionId,
					ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
					ObjectEntryTable.INSTANCE.objectEntryId.getName(),
					dbTableName, ObjectFieldConstants.DB_TYPE_LONG,
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
					preparedStatement2, companyId, userId, userName, timestamp,
					objectDefinitionId, ObjectFieldConstants.BUSINESS_TYPE_DATE,
					ObjectEntryTable.INSTANCE.modifiedDate.getName(),
					dbTableName, ObjectFieldConstants.DB_TYPE_DATE,
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
					preparedStatement2, companyId, userId, userName, timestamp,
					objectDefinitionId, ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectEntryTable.INSTANCE.status.getName(), dbTableName,
					ObjectFieldConstants.DB_TYPE_INTEGER,
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
			}

			preparedStatement2.executeBatch();
		}
	}

	private void _insertObjectField(
			PreparedStatement preparedStatement, long companyId, long userId,
			String userName, Timestamp timestamp, long objectDefinitionId,
			String businessType, String dbColumnName, String dbTableName,
			String dbType, String label, String name)
		throws SQLException {

		preparedStatement.setLong(1, 0);

		String uuid = _portalUUID.generate();

		preparedStatement.setString(2, uuid);

		preparedStatement.setLong(3, increment());
		preparedStatement.setLong(4, companyId);
		preparedStatement.setLong(5, userId);
		preparedStatement.setString(6, userName);
		preparedStatement.setTimestamp(7, timestamp);
		preparedStatement.setTimestamp(8, timestamp);
		preparedStatement.setString(9, uuid);
		preparedStatement.setLong(10, 0);
		preparedStatement.setLong(11, objectDefinitionId);
		preparedStatement.setString(12, businessType);
		preparedStatement.setString(13, dbColumnName);
		preparedStatement.setString(14, dbTableName);
		preparedStatement.setString(15, dbType);
		preparedStatement.setString(16, null);
		preparedStatement.setBoolean(17, false);
		preparedStatement.setBoolean(18, false);
		preparedStatement.setString(19, null);
		preparedStatement.setString(20, label);
		preparedStatement.setString(21, name);
		preparedStatement.setString(22, null);
		preparedStatement.setBoolean(23, false);
		preparedStatement.setBoolean(24, false);
		preparedStatement.setBoolean(25, true);

		preparedStatement.addBatch();
	}

	private final PortalUUID _portalUUID;

}