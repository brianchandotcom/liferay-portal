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

package com.liferay.commerce.internal.upgrade.v9_3_0;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Dictionary;

import org.apache.felix.cm.file.ConfigurationHandler;

/**
 * @author Danny Situ
 */
public class ConfigurationUpgradeProcess extends UpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		if (!hasTable("Configuration_")) {
			return;
		}

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select * from Configuration_ where configurationId LIKE " +
					"'%com.liferay.commerce.account%'");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update Configuration_ set configurationId = ?, " +
						"dictionary = ? where configurationId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				String dictionaryString = resultSet.getString("dictionary");

				if (Validator.isNull(dictionaryString)) {
					continue;
				}

				Dictionary<String, String> dictionary =
					ConfigurationHandler.read(
						new UnsyncByteArrayInputStream(
							dictionaryString.getBytes(StringPool.UTF8)));

				String configurationId = resultSet.getString("configurationId");

				String servicePid = dictionary.get("service.pid");

				if (servicePid != null) {
					dictionary.put(
						"service.pid",
						StringUtil.replace(
							servicePid, "com.liferay.commerce.account",
							"com.liferay.commerce"));
				}

				String serviceFactoryPid = dictionary.get("service.factoryPid");

				if (serviceFactoryPid != null) {
					dictionary.put(
						"service.factoryPid",
						StringUtil.replace(
							serviceFactoryPid, "com.liferay.commerce.account",
							"com.liferay.commerce"));
				}

				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
					new UnsyncByteArrayOutputStream();

				ConfigurationHandler.write(
					unsyncByteArrayOutputStream, dictionary);

				preparedStatement2.setString(
					1,
					StringUtil.replace(
						configurationId, "com.liferay.commerce.account",
						"com.liferay.commerce"));
				preparedStatement2.setString(
					2, unsyncByteArrayOutputStream.toString());
				preparedStatement2.setString(3, configurationId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}