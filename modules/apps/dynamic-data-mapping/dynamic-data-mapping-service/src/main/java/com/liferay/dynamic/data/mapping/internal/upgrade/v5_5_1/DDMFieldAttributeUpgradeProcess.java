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

/**
 * @author Alicia García
 */
public class DDMFieldAttributeUpgradeProcess extends UpgradeProcess {

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

	private void _upgrade() throws Exception {
		processConcurrently(
			SQLTransformer.transform(
				StringBundler.concat(
					"select DDMFieldAttribute.ctCollectionId, ",
					"DDMFieldAttribute.fieldAttributeId, ",
					"DDMStructureVersion.companyId from DDMStructureVersion ",
					"inner join DDMField on ",
					"DDMStructureVersion.ctCollectionId = ",
					"DDMField.ctCollectionId and ",
					"DDMStructureVersion.structureVersionId = ",
					"DDMField.structureVersionId inner join DDMFieldAttribute ",
					"on DDMFieldAttribute.ctCollectionId = ",
					"DDMField.ctCollectionId and DDMFieldAttribute.fieldId = ",
					"DDMField.fieldId where DDMFieldAttribute.companyId = 0")),
			"update DDMFieldAttribute set companyId = ? where ctCollectionId " +
				"= ? and fieldAttributeId = ?",
			resultSet -> new Object[] {
				resultSet.getLong("ctCollectionId"),
				resultSet.getLong("fieldAttributeId"),
				resultSet.getLong("companyId")
			},
			(values, preparedStatement) -> {
				long companyId = (Long)values[2];
				long fieldAttributeId = (Long)values[1];

				preparedStatement.setLong(1, companyId);

				preparedStatement.setLong(2, (Long)values[0]);
				preparedStatement.setLong(3, fieldAttributeId);

				preparedStatement.executeUpdate();

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Update company ID for dynamic data mapping field ",
							"attribute ", fieldAttributeId, " from 0 to ",
							companyId));
				}
			},
			"Unable to update company IDs for dynamic data mapping field " +
				"attributes");
	}

	private void _upgradeByCompanyId(long companyId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update DDMFieldAttribute set companyId = ? where companyId " +
					"= 0")) {

			preparedStatement.setLong(1, companyId);

			preparedStatement.executeUpdate();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFieldAttributeUpgradeProcess.class);

}