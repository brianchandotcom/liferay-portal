/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v9_0_2;

import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Leite
 */
public class ObjectFolderUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"select ObjectFolder.objectFolderId, ",
						"ObjectFolder.companyId from ObjectFolder where ",
						"ObjectFolder.externalReferenceCode = ",
						"'uncategorized'")));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"update ObjectFolder set externalReferenceCode = '",
						ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_DEFAULT,
						"', label = ?, name = '",
						ObjectFolderConstants.NAME_DEFAULT,
						"' where objectFolderId = ?"));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				preparedStatement2.setString(
					1,
					LocalizationUtil.getXml(
						new LocalizedValuesMap() {
							{
								put(
									LocaleUtil.fromLanguageId(
										UpgradeProcessUtil.getDefaultLanguageId(
											resultSet.getLong("companyId"))),
									ObjectFolderConstants.NAME_DEFAULT);
							}
						},
						"Label"));
				preparedStatement2.setLong(
					2, resultSet.getLong("objectFolderId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}