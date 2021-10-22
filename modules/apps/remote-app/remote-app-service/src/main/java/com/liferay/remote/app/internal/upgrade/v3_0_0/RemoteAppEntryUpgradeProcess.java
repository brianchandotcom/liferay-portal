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

package com.liferay.remote.app.internal.upgrade.v3_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.remote.app.internal.upgrade.v3_0_0.util.RemoteAppEntryTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Locale;
import java.util.Map;

/**
 * @author Javier de Arcos
 */
public class RemoteAppEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn(RemoteAppEntryTable.TABLE_NAME, "name")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("defaultLanguageId", "VARCHAR(75)"));

			try (Statement s = connection.createStatement();
				ResultSet resultSet = s.executeQuery(
					"select mvccVersion, remoteAppEntryId, companyId, name " +
						"from RemoteAppEntry");
				PreparedStatement preparedStatement1 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection.prepareStatement(
							"update RemoteAppEntry set defaultLanguageId = ?"));
				PreparedStatement preparedStatement2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection.prepareStatement(
							StringBundler.concat(
								"insert into RemoteAppEntryLocalization ",
								"(mvccVersion, remoteAppEntryLocalizationId, ",
								"companyId, remoteAppEntryId, languageId, ",
								"description, name) values (?, ?, ?, ?, ?, ?, ",
								"?)")))) {

				while (resultSet.next()) {
					String name = resultSet.getString("name");

					if (Validator.isXml(name)) {
						long mvccVersion = resultSet.getLong("mvccVersion");
						long companyId = resultSet.getLong("companyId");
						long remoteAppEntryId = resultSet.getLong(
							"remoteAppEntryId");

						preparedStatement1.setString(
							1, LocalizationUtil.getDefaultLanguageId(name));

						preparedStatement1.addBatch();

						Map<Locale, String> localizations =
							LocalizationUtil.getLocalizationMap(name);

						for (Map.Entry<Locale, String> localization :
								localizations.entrySet()) {

							String languageId = LanguageUtil.getLanguageId(
								localization.getKey());
							String localizedName = localization.getValue();

							preparedStatement2.setLong(1, mvccVersion);
							preparedStatement2.setLong(2, increment());
							preparedStatement2.setLong(3, companyId);
							preparedStatement2.setLong(4, remoteAppEntryId);
							preparedStatement2.setString(5, languageId);
							preparedStatement2.setString(6, StringPool.BLANK);
							preparedStatement2.setString(7, localizedName);

							preparedStatement2.addBatch();
						}
					}
				}

				preparedStatement1.executeBatch();
				preparedStatement2.executeBatch();
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}

			alter(RemoteAppEntryTable.class, new AlterTableDropColumn("name"));
		}

		if (!hasColumn(RemoteAppEntryTable.TABLE_NAME, "sourceCodeURL")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("sourceCodeURL", "STRING null"));
		}

		if (!hasColumn(RemoteAppEntryTable.TABLE_NAME, "status")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("status", "INTEGER"));

			runSQL("update RemoteAppEntry set status = 0 where status is null");
		}

		if (!hasColumn(RemoteAppEntryTable.TABLE_NAME, "statusByUserId")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("statusByUserId", "LONG"));

			runSQL(
				"update RemoteAppEntry set statusByUserId = userId where " +
					"statusByUserId is null");
		}

		if (!hasColumn(RemoteAppEntryTable.TABLE_NAME, "statusByUserName")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("statusByUserName", "VARCHAR(75)"));

			runSQL(
				"update RemoteAppEntry set statusByUserName = (select " +
					"screenName from User_ where RemoteAppEntry.userId = " +
						"User_.userId)");
		}

		if (!hasColumn(RemoteAppEntryTable.TABLE_NAME, "statusDate")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("statusDate", "DATE"));

			runSQL(
				"update RemoteAppEntry set statusDate = modifiedDate where " +
					"statusDate is null");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteAppEntryUpgradeProcess.class);

}