/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_5_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alicia García
 */
public class DDMFieldUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (DBPartition.isPartitionEnabled()) {
			_upgradeByCompanyId(CompanyThreadLocal.getCompanyId());

			return;
		}

		long[] companyIds = PortalInstancePool.getCompanyIds();

		if (companyIds.length == 1) {
			_upgradeByCompanyId(companyIds[0]);

			return;
		}

		_upgrade();
	}

	private boolean _hasDDMFieldCompanyId0() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from DDMField where companyId = 0")) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int count = resultSet.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	private void _upgrade() throws Exception {
		if (!_hasDDMFieldCompanyId0()) {
			return;
		}

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

	private void _upgradeByCompanyId(long companyId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update DDMField set companyId = ? where companyId = 0")) {

			preparedStatement.setLong(1, companyId);

			preparedStatement.executeUpdate();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFieldUpgradeProcess.class);

}