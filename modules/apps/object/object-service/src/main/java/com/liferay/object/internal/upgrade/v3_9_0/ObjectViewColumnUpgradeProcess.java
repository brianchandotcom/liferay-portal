/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v3_9_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Locale;
import java.util.Map;

/**
 * @author Paulo Albuquerque
 */
public class ObjectViewColumnUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Locale defaultLocale;

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select objectViewColumnId, companyId, objectFieldName from " +
					"ObjectViewColumn where label is null");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update ObjectViewColumn set label = ? where " +
						"objectViewColumnId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");

				defaultLocale = LocaleUtil.fromLanguageId(
					UpgradeProcessUtil.getDefaultLanguageId(companyId));

				String key = _labelKeys.get(
					resultSet.getString("objectFieldName"));

				preparedStatement2.setString(
					1,
					LocalizationUtil.updateLocalization(
						HashMapBuilder.put(
							defaultLocale, LanguageUtil.get(defaultLocale, key)
						).build(),
						StringPool.BLANK, "Label",
						UpgradeProcessUtil.getDefaultLanguageId(companyId)));

				preparedStatement2.setLong(
					2, resultSet.getLong("objectViewColumnId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private final Map<String, String> _labelKeys = HashMapBuilder.put(
		"creator", "author"
	).put(
		"dateCreated", "created-date"
	).put(
		"dateModified", "modified-date"
	).put(
		"id", "id"
	).put(
		"status", "workflow-status[object]"
	).build();

}