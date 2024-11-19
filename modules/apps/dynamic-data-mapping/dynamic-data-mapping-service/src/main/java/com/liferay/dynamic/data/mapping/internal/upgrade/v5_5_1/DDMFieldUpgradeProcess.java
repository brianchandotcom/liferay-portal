/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_5_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Alicia García
 */
public class DDMFieldUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgrade();
	}

	private void _upgrade() throws Exception {
		processConcurrently(
			SQLTransformer.transform(
				StringBundler.concat(
					"select DDMField.ctCollectionId, DDMField.fieldId, ",
					"DDMStructureVersion.companyId from DDMStructureVersion ",
					"inner join DDMField on ",
					"DDMStructureVersion.ctCollectionId = ",
					"DDMField.ctCollectionId and ",
					"DDMStructureVersion.structureVersionId = ",
					"DDMField.structureVersionId where DDMField.companyId = ",
					"0")),
			"update DDMField set companyId = ? where ctCollectionId = ? and " +
				"fieldId = ?",
			resultSet -> new Object[] {
				resultSet.getLong("ctCollectionId"),
				resultSet.getLong("fieldId"), resultSet.getLong("companyId")
			},
			(values, preparedStatement) -> {
				long companyId = (Long)values[2];
				long fieldId = (Long)values[1];

				preparedStatement.setLong(1, companyId);

				preparedStatement.setLong(2, (Long)values[0]);
				preparedStatement.setLong(3, fieldId);

				preparedStatement.executeUpdate();

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Update company ID for dynamic data mapping field ",
							fieldId, " from 0 to ", companyId));
				}
			},
			"Unable to update company IDs for dynamic data mapping fields");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFieldUpgradeProcess.class);

}