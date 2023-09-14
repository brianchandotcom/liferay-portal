/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PortalInstances;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luis Ortiz
 */
public class UpgradeListTypeCompanyId extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		long defaultCompanyId = PortalInstances.getDefaultCompanyIdBySQL();

		if (DBPartition.isPartitionEnabled()) {
			_upgradeDBPartition(defaultCompanyId);

			return;
		}

		_upgrade(defaultCompanyId);
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns("ListType", "companyId LONG")
		};
	}

	private List<ListTypeEntry> _getListTypes() throws Exception {
		List<ListTypeEntry> listTypeEntries = new ArrayList<>();

		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				"select listTypeId, mvccVersion, name, type_ from ListType")) {

			while (resultSet.next()) {
				listTypeEntries.add(
					new ListTypeEntry(
						resultSet.getLong(1), resultSet.getLong(2),
						resultSet.getString(3), resultSet.getString(4)));
			}
		}

		return listTypeEntries;
	}

	private HashMap<Long, Long> _insertListTypes(
			List<ListTypeEntry> listTypeEntries, long companyId)
		throws Exception {

		HashMap<Long, Long> listTypeIdChanges = new HashMap<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into ListType (companyId, listTypeId, mvccVersion, " +
					"name, type_) values (?, ?, ?, ?, ?)")) {

			for (ListTypeEntry listTypeEntry : listTypeEntries) {
				long newListTypeId = increment(ListType.class.getName());

				preparedStatement.setLong(1, companyId);
				preparedStatement.setLong(2, newListTypeId);
				preparedStatement.setLong(3, listTypeEntry.getMvccVersion());
				preparedStatement.setString(4, listTypeEntry.getName());
				preparedStatement.setString(5, listTypeEntry.getType());

				listTypeIdChanges.put(
					listTypeEntry.getListTypeId(), newListTypeId);

				preparedStatement.execute();
			}
		}

		return listTypeIdChanges;
	}

	private void _updateListTypeIdReferences(
			HashMap<Long, Long> listTypeIdChanges, long companyId)
		throws Exception {

		for (Map.Entry<String, List<String>> listTypeReference :
				_listTypeReferences.entrySet()) {

			String table = listTypeReference.getKey();
			List<String> columns = listTypeReference.getValue();

			for (String column : columns) {
				try (PreparedStatement preparedStatement =
						connection.prepareStatement(
							StringBundler.concat(
								"update ", table, " set ", column,
								" = ? where ", column,
								" = ? and companyId = ?"))) {

					for (Map.Entry<Long, Long> listTypeIdChange :
							listTypeIdChanges.entrySet()) {

						preparedStatement.setLong(
							1, listTypeIdChange.getValue());
						preparedStatement.setLong(2, listTypeIdChange.getKey());
						preparedStatement.setLong(3, companyId);

						preparedStatement.executeUpdate();
					}
				}
			}
		}
	}

	private void _upgrade(long defaultCompanyId) throws Exception {
		dropIndexes("ListType", "name");

		runSQL("update ListType set companyId = " + defaultCompanyId);

		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		List<ListTypeEntry> listTypeEntries = _getListTypes();

		for (long companyId : companyIds) {
			if (companyId != defaultCompanyId) {
				HashMap<Long, Long> listTypeIdChanges = _insertListTypes(
					listTypeEntries, companyId);

				_updateListTypeIdReferences(listTypeIdChanges, companyId);
			}
		}
	}

	private void _upgradeDBPartition(long defaultCompanyId) throws Exception {
		boolean defaultCompany = false;

		if ((CompanyThreadLocal.getCompanyId() == defaultCompanyId) ||
			(CompanyThreadLocal.getCompanyId() == CompanyConstants.SYSTEM)) {

			defaultCompany = true;
		}

		if (defaultCompany) {
			runSQL("update ListType set companyId = " + defaultCompanyId);
		}
		else {
			DBPartitionUtil.replaceByTable(connection, "ListType");

			runSQL(
				"update ListType set companyId = " +
					CompanyThreadLocal.getCompanyId());
		}
	}

	private static final Map<String, List<String>> _listTypeReferences =
		HashMapBuilder.<String, List<String>>put(
			"Address", Collections.singletonList("listTypeId")
		).put(
			"Contact_", Arrays.asList("prefixListTypeId", "suffixListTypeId")
		).put(
			"EmailAddress", Collections.singletonList("listTypeId")
		).put(
			"Organization_", Collections.singletonList("statusListTypeId")
		).put(
			"OrgLabor", Collections.singletonList("listTypeId")
		).put(
			"Phone", Collections.singletonList("listTypeId")
		).put(
			"Website", Collections.singletonList("listTypeId")
		).build();

	private static class ListTypeEntry {

		public ListTypeEntry(
			long listTypeId, long mvccVersion, String name, String type) {

			_listTypeId = listTypeId;
			_mvccVersion = mvccVersion;
			_name = name;
			_type = type;
		}

		public long getListTypeId() {
			return _listTypeId;
		}

		public long getMvccVersion() {
			return _mvccVersion;
		}

		public String getName() {
			return _name;
		}

		public String getType() {
			return _type;
		}

		private final long _listTypeId;
		private final long _mvccVersion;
		private final String _name;
		private final String _type;

	}

}