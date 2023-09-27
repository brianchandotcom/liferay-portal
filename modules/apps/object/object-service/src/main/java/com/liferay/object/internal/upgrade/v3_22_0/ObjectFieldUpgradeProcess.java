/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v3_22_0;

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
import java.sql.Timestamp;

import java.util.Locale;

/**
 * @author Paulo Albuquerque
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
			"select ObjectDefinition.objectDefinitionId, ",
				"ObjectDefinition.companyId, ObjectDefinition.userName, ",
			"ObjectDefinition.userId from ObjectDefinition where ",
			"ObjectDefinition.system_ = [$FALSE$]")));

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
						 "required, state_, system_) values (?, ?, ?, ?, ?, ",
						 "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ",
						 "?, ?, ?)"));

			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				preparedStatement2.setLong(1, 0);

				String uuid = _portalUUID.generate();

				preparedStatement2.setString(2, uuid);

				preparedStatement2.setLong(3, increment());

				long companyId = resultSet.getLong("companyId");

				preparedStatement2.setLong(4, companyId);

				preparedStatement2.setLong(5, resultSet.getLong("userId"));
				preparedStatement2.setString(
					6, resultSet.getString("userName"));

				Timestamp timestamp = new Timestamp(System.currentTimeMillis());

				preparedStatement2.setTimestamp(7, timestamp);
				preparedStatement2.setTimestamp(8, timestamp);

				preparedStatement2.setString(9, uuid);
				preparedStatement2.setLong(10, 0);
				preparedStatement2.setLong(
					11, resultSet.getLong("objectDefinitionId"));
				preparedStatement2.setString(
					12, ObjectFieldConstants.BUSINESS_TYPE_TEXT);
				preparedStatement2.setString(
					13,
					ObjectEntryTable.INSTANCE.externalReferenceCode.getName());
				preparedStatement2.setString(14, "ObjectEntry");
				preparedStatement2.setString(
					15, ObjectFieldConstants.DB_TYPE_STRING);
				preparedStatement2.setString(16, null);
				preparedStatement2.setBoolean(17, false);
				preparedStatement2.setBoolean(18, false);
				preparedStatement2.setString(19, null);

				Locale defaultLocale = LocaleUtil.fromLanguageId(
					UpgradeProcessUtil.getDefaultLanguageId(companyId));

				preparedStatement2.setString(
					20,
					LocalizationUtil.getXml(
						new LocalizedValuesMap() {
							{
								put(
									defaultLocale,
									LanguageUtil.get(
										defaultLocale,
										"external-reference-code"));
							}
						},
						"Label"));

				preparedStatement2.setString(21, "externalReferenceCode");
				preparedStatement2.setString(22, null);
				preparedStatement2.setBoolean(23, false);
				preparedStatement2.setBoolean(24, false);
				preparedStatement2.setBoolean(25, true);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private final PortalUUID _portalUUID;

}