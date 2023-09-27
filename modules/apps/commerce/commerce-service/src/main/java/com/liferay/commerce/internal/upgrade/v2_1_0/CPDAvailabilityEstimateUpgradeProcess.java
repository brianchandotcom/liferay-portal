/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v2_1_0;

import com.liferay.commerce.model.impl.CPDAvailabilityEstimateModelImpl;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.List;
import java.util.Objects;

/**
 * @author Alec Sloan
 */
public class CPDAvailabilityEstimateUpgradeProcess extends UpgradeProcess {

	public CPDAvailabilityEstimateUpgradeProcess(
		CPDefinitionLocalService cpDefinitionLocalService) {

		_cpDefinitionLocalService = cpDefinitionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_addIndexes(CPDAvailabilityEstimateModelImpl.TABLE_NAME);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update CPDAvailabilityEstimate set CProductId = ? where " +
					"CPDefinitionId = ?");
			Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				"select distinct CPDefinitionId from " +
					"CPDAvailabilityEstimate")) {

			while (resultSet.next()) {
				long cpDefinitionId = resultSet.getLong("CPDefinitionId");

				CPDefinition cpDefinition =
					_cpDefinitionLocalService.getCPDefinition(cpDefinitionId);

				preparedStatement.setLong(1, cpDefinition.getCProductId());

				preparedStatement.setLong(2, cpDefinitionId);

				preparedStatement.execute();
			}
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CPDAvailabilityEstimate", "CProductId LONG")
		};
	}

	private void _addIndexes(String tableName) throws Exception {
		Class<?> clazz = getClass();

		List<ObjectValuePair<String, IndexMetadata>> indexesSQL = getIndexesSQL(
			clazz.getClassLoader(), tableName);

		for (ObjectValuePair<String, IndexMetadata> indexSQL : indexesSQL) {
			IndexMetadata indexMetadata = indexSQL.getValue();

			if (_log.isInfoEnabled()) {
				_log.info(
					String.format(
						"Adding index %s to table %s",
						indexMetadata.getIndexName(), tableName));
			}

			if (!_tableHasIndex(tableName, indexMetadata.getIndexName())) {
				runSQL(indexMetadata.getCreateSQL(null));
			}
			else if (_log.isInfoEnabled()) {
				_log.info(
					String.format(
						"Index %s already exists on table %s",
						indexMetadata.getIndexName(), tableName));
			}
		}
	}

	private boolean _tableHasIndex(String tableName, String indexName)
		throws Exception {

		DatabaseMetaData metadata = connection.getMetaData();

		try (ResultSet resultSet = metadata.getIndexInfo(
				null, null, tableName, false, false)) {

			while (resultSet.next()) {
				String curIndexName = resultSet.getString("index_name");

				if (Objects.equals(indexName, curIndexName)) {
					return true;
				}
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDAvailabilityEstimateUpgradeProcess.class);

	private final CPDefinitionLocalService _cpDefinitionLocalService;

}