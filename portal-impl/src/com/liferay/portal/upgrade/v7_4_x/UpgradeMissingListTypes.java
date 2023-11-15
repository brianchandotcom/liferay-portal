/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.util.PortalInstances;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Luis Ortiz
 */
public class UpgradeMissingListTypes extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (DBPartition.isPartitionEnabled()) {
			_upgradeCompany(CompanyThreadLocal.getCompanyId());

			return;
		}

		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			_upgradeCompany(companyId);
		}
	}

	private void _addListType(long companyId, String name, String type)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from ListType where companyId = ? and name = ? and " +
					"type_ = ?")) {

			preparedStatement.setLong(1, companyId);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, type);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return;
				}
			}
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into ListType (listTypeId, companyId, name, type_) " +
					"values (?, ?, ?, ?)")) {

			preparedStatement.setLong(1, increment(ListType.class.getName()));
			preparedStatement.setLong(2, companyId);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, type);

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add list type", exception);
			}
		}
	}

	private void _deleteListType(long companyId, String name, String type)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from ListType where companyId = ? and name = ? and " +
					"type_ = ?")) {

			preparedStatement.setLong(1, companyId);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, type);

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to delete list type", exception);
			}
		}
	}

	private void _upgradeCompany(long companyId) throws Exception {
		_addListType(
			companyId, "intranet",
			"com.liferay.portal.kernel.model.Company.website");
		_addListType(
			companyId, "public",
			"com.liferay.portal.kernel.model.Company.website");
		_deleteListType(
			companyId, "intranet",
			"com.liferay.account.model.AccountEntry.address");
		_deleteListType(
			companyId, "public",
			"com.liferay.account.model.AccountEntry.address");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeMissingListTypes.class);

}