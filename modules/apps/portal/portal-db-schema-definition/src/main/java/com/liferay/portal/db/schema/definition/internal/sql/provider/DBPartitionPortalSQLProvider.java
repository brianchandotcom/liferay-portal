/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.sql.provider;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.db.PostgreSQLDB;
import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import javax.sql.DataSource;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBPartitionPortalSQLProvider extends BaseSQLProvider {

	public static void clearCache() {
		_controlTableNames = null;
		_partitionIndexesSQL = null;
		_partitionTablesSQL = null;
		_rulesTableColumn = null;
	}

	public DBPartitionPortalSQLProvider(DBType dbType, long companyId)
		throws Exception {

		super(dbType);

		_companyId = companyId;

		_dbPartitionName = DBPartitionUtil.getPartitionName(companyId);

		_objectSQLProvider = new ObjectSQLProvider(db, companyId);

		if (companyId != PortalInstancePool.getDefaultCompanyId()) {
			_partitionPrefix = _dbPartitionName + StringPool.PERIOD;
		}

		if ((_partitionIndexesSQL == null) || (_partitionTablesSQL == null)) {
			_partitionTablesSQL = _getPartitionTablesSQL();
			_partitionIndexesSQL = _getPartitionIndexesSQL();
		}
	}

	@Override
	public String getIndexesSQL() {
		if (_companyId == PortalInstancePool.getDefaultCompanyId()) {
			return super.getIndexesSQL() + StringPool.NEW_LINE +
				_objectSQLProvider.getIndexesSQL();
		}

		return StringBundler.concat(
			_addIndexesPartition(_partitionIndexesSQL), StringPool.NEW_LINE,
			_addIndexesPartition(_objectSQLProvider.getIndexesSQL()));
	}

	@Override
	public String getTablesSQL() {
		if (_companyId == PortalInstancePool.getDefaultCompanyId()) {
			return super.getTablesSQL() + StringPool.NEW_LINE +
				_objectSQLProvider.getTablesSQL();
		}

		Supplier<String> rulesSQLSupplier = () -> StringPool.BLANK;

		if (db.getDBType() == DBType.POSTGRESQL) {
			rulesSQLSupplier = this::_getRules;
		}

		return StringBundler.concat(
			_getCreatePartitionSQL(), _addTablesPartition(_partitionTablesSQL),
			StringPool.NEW_LINE, _getViewsSQL(), StringPool.NEW_LINE,
			_addTablesPartition(_objectSQLProvider.getTablesSQL()),
			rulesSQLSupplier.get());
	}

	private String _addIndexesPartition(String sql) {
		return StringUtil.replace(sql, " on ", " on " + _partitionPrefix);
	}

	private String _addTablesPartition(String sql) {
		return StringUtil.replace(
			sql, "create table ", "create table " + _partitionPrefix);
	}

	private String _getCreatePartitionSQL() {
		if (_companyId == PortalInstancePool.getDefaultCompanyId()) {
			return StringPool.NEW_LINE;
		}

		if (db.getDBType() == DBType.MYSQL) {
			return StringBundler.concat(
				"create schema if not exists ", _dbPartitionName,
				" character set utf8;", StringPool.NEW_LINE);
		}

		return StringBundler.concat(
			"create schema if not exists ", _dbPartitionName,
			StringPool.SEMICOLON, StringPool.NEW_LINE);
	}

	private String _getPartitionIndexesSQL() {
		StringBundler sb = new StringBundler();

		List<String> regexControlTableNames = new ArrayList<>(
			_controlTableNames.size());

		for (String controlTableName : _controlTableNames) {
			regexControlTableNames.add(
				" on " + StringUtil.toLowerCase(controlTableName) +
					StringPool.SPACE);
		}

		outer:
		for (String line :
				StringUtil.split(super.getIndexesSQL(), CharPool.SEMICOLON)) {

			String lowerCaseLine = StringUtil.trim(
				StringUtil.toLowerCase(line));

			for (String regexControlTableName : regexControlTableNames) {
				if (StringUtil.count(lowerCaseLine, regexControlTableName) >
						0) {

					continue outer;
				}
			}

			sb.append(line);
			sb.append(StringPool.SEMICOLON);
		}

		return sb.toString();
	}

	private String _getPartitionTablesSQL() throws Exception {
		String[] createTableSQLs = StringUtil.split(
			super.getTablesSQL(), CharPool.SEMICOLON);

		_controlTableNames = new ArrayList<>();
		_rulesTableColumn = new HashSet<>();

		StringBundler sb = new StringBundler();

		DataSource dataSource = InfrastructureUtil.getDataSource();

		try (Connection connection = dataSource.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			for (String createTableSQL : createTableSQLs) {
				createTableSQL = StringUtil.trim(createTableSQL);

				if (StringUtil.startsWith(
						createTableSQL, "create or replace rule")) {

					_rulesTableColumn.add(
						PostgreSQLDB.getRuleTableColumn(createTableSQL));

					continue;
				}

				if (StringUtil.startsWith(createTableSQL, "create table")) {
					String[] parts = createTableSQL.split(StringPool.SPACE);

					if (dbInspector.isControlTable(parts[2])) {
						_controlTableNames.add(parts[2]);

						continue;
					}
				}

				sb.append(
					createTableSQL + StringPool.SEMICOLON +
						StringPool.NEW_LINE);
			}
		}

		return sb.toString();
	}

	private String _getRules() {
		StringBundler sb = new StringBundler();

		for (String[] ruleTableColumn : _rulesTableColumn) {
			sb.append(
				PostgreSQLDB.getCreateRulesSQL(
					_dbPartitionName, ruleTableColumn[0], ruleTableColumn[1]));
		}

		return sb.toString();
	}

	private String _getViewsSQL() {
		StringBundler sb = new StringBundler(_controlTableNames.size());

		for (String controlTableName : _controlTableNames) {
			sb.append(
				StringBundler.concat(
					"create or replace view ", _dbPartitionName,
					StringPool.PERIOD, controlTableName, " as select * from ",
					controlTableName, StringPool.SEMICOLON,
					StringPool.NEW_LINE));
		}

		return sb.toString();
	}

	private static List<String> _controlTableNames;
	private static String _partitionIndexesSQL;
	private static String _partitionTablesSQL;
	private static Set<String[]> _rulesTableColumn;

	private final long _companyId;
	private final String _dbPartitionName;
	private final ObjectSQLProvider _objectSQLProvider;
	private String _partitionPrefix = StringPool.BLANK;

}