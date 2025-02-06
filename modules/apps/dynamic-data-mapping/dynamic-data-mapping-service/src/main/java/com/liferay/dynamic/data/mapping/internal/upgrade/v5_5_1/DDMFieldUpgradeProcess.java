/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_5_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.DBTypeToSQLMap;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;

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

		_upgrade(companyIds);
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

	private void _upgrade(long[] companyIds) throws Exception {
		if (!_hasDDMFieldCompanyId0()) {
			return;
		}

		processConcurrently(
			ArrayUtil.toLongArray(companyIds),
			companyId -> {
				DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(
					StringBundler.concat(
						"update DDMField set companyId = ", companyId,
						" where exists (select 1 from DDMStructureVersion ",
						"where DDMStructureVersion.structureVersionId = ",
						"DDMField.structureVersionId and ",
						"DDMStructureVersion.ctCollectionId = ",
						"DDMField.ctCollectionId and ",
						"DDMStructureVersion.companyId = ", companyId,
						") and DDMField.companyId = 0"));

				String sql = StringBundler.concat(
					"update DDMField inner join DDMStructureVersion on ",
					"DDMStructureVersion.structureVersionId = ",
					"DDMField.structureVersionId and ",
					"DDMStructureVersion.ctCollectionId = ",
					"DDMField.ctCollectionId and ",
					"DDMStructureVersion.companyId = ", companyId,
					" set DDMField.companyId = ", companyId,
					" where DDMField.companyId = 0");

				dbTypeToSQLMap.add(DBType.MARIADB, sql);
				dbTypeToSQLMap.add(DBType.MYSQL, sql);

				runSQL(dbTypeToSQLMap);

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Update company IDs for dynamic data mapping ",
							"fields from 0 to ", companyId));
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