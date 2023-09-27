/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.upgrade.v3_2_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alicia García
 */
public class DDMStructureLinkUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DLFileEntryType.fileEntryTypeId, ",
					"DLFileEntryType.dataDefinitionId from DLFileEntryType ",
					"inner join DDMStructureLink on ",
					"DDMStructureLink.structureId = ",
					"DLFileEntryType.dataDefinitionId and ",
					"DDMStructureLink.classPK = ",
					"DLFileEntryType.fileEntryTypeId"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"delete from DDMStructureLink where classPK = ? and " +
						"structureId = ?");
			ResultSet resultSet1 = preparedStatement1.executeQuery()) {

			while (resultSet1.next()) {
				preparedStatement2.setLong(1, resultSet1.getLong(1));
				preparedStatement2.setLong(2, resultSet1.getLong(2));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}