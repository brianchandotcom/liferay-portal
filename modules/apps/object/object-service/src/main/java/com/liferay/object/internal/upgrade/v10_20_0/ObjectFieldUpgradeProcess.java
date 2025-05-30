/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v10_20_0;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Locale;

/**
 * @author Jhosseph Gonzalez
 */
public class ObjectFieldUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
			SQLTransformer.transform(
				StringBundler.concat(
					"select ObjectDefinition.objectDefinitionId, ",
					"ObjectDefinition.companyId, ObjectDefinition.userId, ",
					"ObjectDefinition.userName from ObjectDefinition where ",
					"ObjectDefinition.objectDefinitionId not in (select ",
					"distinct ObjectField.objectDefinitionId from ObjectField ",
					"where ObjectField.name in ('displaydate', ",
					"'expirationDate','reviewdate')) and ObjectDefinition.",
					"system_ = [$FALSE$]")));
			 PreparedStatement preparedStatement2 =
				 AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					 connection,
					 StringBundler.concat(
						 "insert into ObjectField (mvccVersion, uuid_, ",
						 "externalReferenceCode, objectFieldId, companyId, ",
						 "userId, userName, createDate, modifiedDate, ",
						 "listTypeDefinitionId, objectDefinitionId, ",
						 "businessType, dbColumnName, dbTableName, dbType, ",
						 "indexed, indexedAsKeyword, indexedLanguageId, ",
						 "label, localized, name, readOnly, ",
						 "readOnlyConditionExpression, relationshipType,",
						 "required, state_, system_) values (",
						 "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ",
						 "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
				 ));
			 PreparedStatement preparedStatement3 =
				 AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					 connection,
					 StringBundler.concat(
						 "insert into ObjectFieldSetting",
						 "(mvccVersion, uuid_, objectFieldSettingId, ",
						 "companyId, userId, userName, createDate, ",
						 "modifiedDate, objectFieldId , name, value) values ",
						 "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));

			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");

				Locale defaultLocale = LocaleUtil.fromLanguageId(
					UpgradeProcessUtil.getDefaultLanguageId(companyId));

				Timestamp now = new Timestamp(System.currentTimeMillis());
				long objectDefinitionId = resultSet.getLong(
					"objectDefinitionId");
				long userId = resultSet.getLong("userId");
				String userName = resultSet.getString("userName");

				_insertObjectField(
					companyId, "display-date", defaultLocale, "displayDate",
					objectDefinitionId, preparedStatement2, preparedStatement3,
					now, userId, userName);
				_insertObjectField(
					companyId, "expiration-date", defaultLocale,
					"expirationDate", objectDefinitionId, preparedStatement2,
					preparedStatement3, now, userId, userName);
				_insertObjectField(
					companyId, "review-date", defaultLocale, "reviewDate",
					objectDefinitionId, preparedStatement2, preparedStatement3,
					now, userId, userName);

				preparedStatement2.executeBatch();
				preparedStatement3.executeBatch();
			}
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"ObjectDefinition", "enableObjectEntrySchedule BOOLEAN")
		};
	}

	private void _insertObjectField(
			long companyId, String key, Locale locale, String name,
			long objectDefinitionId,
			PreparedStatement objectFieldPreparedStatement,
			PreparedStatement objectFieldSettingPreparedStatement,
			Timestamp timestamp, long userId, String userName)
		throws SQLException {

		objectFieldPreparedStatement.setLong(1, 0);

		String uuid = PortalUUIDUtil.generate();

		objectFieldPreparedStatement.setString(2, uuid);
		objectFieldPreparedStatement.setString(3, uuid);

		long objectFieldId = increment();

		objectFieldPreparedStatement.setLong(4, objectFieldId);

		objectFieldPreparedStatement.setLong(5, companyId);
		objectFieldPreparedStatement.setLong(6, userId);
		objectFieldPreparedStatement.setString(7, userName);
		objectFieldPreparedStatement.setTimestamp(8, timestamp);
		objectFieldPreparedStatement.setTimestamp(9, timestamp);
		objectFieldPreparedStatement.setLong(10, 0);
		objectFieldPreparedStatement.setLong(11, objectDefinitionId);
		objectFieldPreparedStatement.setString(
			12, ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME);
		objectFieldPreparedStatement.setString(13, name);
		objectFieldPreparedStatement.setString(
			14, ObjectEntryTable.INSTANCE.getTableName());
		objectFieldPreparedStatement.setString(
			15, ObjectFieldConstants.DB_TYPE_DATE_TIME);
		objectFieldPreparedStatement.setBoolean(16, true);
		objectFieldPreparedStatement.setBoolean(17, false);
		objectFieldPreparedStatement.setString(18, null);
		objectFieldPreparedStatement.setString(
			19,
			LocalizationUtil.getXml(
				new LocalizedValuesMap() {
					{
						put(locale, LanguageUtil.get(locale, key));
					}
				},
				"Label"));
		objectFieldPreparedStatement.setBoolean(20, false);
		objectFieldPreparedStatement.setString(21, name);
		objectFieldPreparedStatement.setString(22, null);
		objectFieldPreparedStatement.setString(23, null);
		objectFieldPreparedStatement.setString(24, null);
		objectFieldPreparedStatement.setBoolean(25, false);
		objectFieldPreparedStatement.setBoolean(26, false);
		objectFieldPreparedStatement.setBoolean(27, true);

		objectFieldPreparedStatement.addBatch();

		objectFieldSettingPreparedStatement.setLong(1, 0);
		objectFieldSettingPreparedStatement.setString(
			2, PortalUUIDUtil.generate());
		objectFieldSettingPreparedStatement.setLong(3, increment());
		objectFieldSettingPreparedStatement.setLong(4, companyId);
		objectFieldSettingPreparedStatement.setLong(5, userId);
		objectFieldSettingPreparedStatement.setString(6, userName);
		objectFieldSettingPreparedStatement.setTimestamp(7, timestamp);
		objectFieldSettingPreparedStatement.setTimestamp(8, timestamp);
		objectFieldSettingPreparedStatement.setLong(9, objectFieldId);
		objectFieldSettingPreparedStatement.setString(
			10, ObjectFieldSettingConstants.NAME_TIME_STORAGE);
		objectFieldSettingPreparedStatement.setString(
			11, ObjectFieldSettingConstants.VALUE_CONVERT_TO_UTC);

		objectFieldSettingPreparedStatement.addBatch();
	}

}