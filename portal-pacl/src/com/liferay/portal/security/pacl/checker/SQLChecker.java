/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.StringReader;

import java.security.Permission;

import java.util.List;
import java.util.Set;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.JSqlParser;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.TablesNamesFinder;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class SQLChecker extends BaseChecker {

	@Override
	public void afterPropertiesSet() {
		initTableNames();
	}

	@Override
	public AuthorizationProperty generateAuthorizationProperty(
		Object... arguments) {

		if ((arguments == null) || (arguments.length != 1) ||
			!(arguments[0] instanceof String)) {

			return null;
		}

		String sql = (String)arguments[0];

		Statement statement = null;

		try {
			statement = _jSqlParser.parse(new StringReader(sql));
		}
		catch (Exception e) {
			_log.error("Unable to parse SQL " + sql);

			return null;
		}

		String key = null;
		String value = null;

		if (statement instanceof CreateIndex) {
			key = "security-manager-sql-tables-index-create";

			CreateIndex createIndex = (CreateIndex)statement;

			Table table = createIndex.getTable();

			value = table.getName();
		}
		else if (statement instanceof CreateTable) {
			key = "security-manager-sql-tables-create";

			CreateTable createTable = (CreateTable)statement;

			Table table = createTable.getTable();

			value = table.getName();
		}
		else if (statement instanceof Delete) {
			key = "security-manager-sql-tables-delete";

			TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

			Delete delete = (Delete)statement;

			List<String> tableNames = tablesNamesFinder.getTableList(delete);

			value = StringUtil.merge(tableNames);
		}
		else if (statement instanceof Drop) {
			key = "security-manager-sql-tables-drop";

			Drop drop = (Drop)statement;

			value = drop.getName();
		}
		else if (statement instanceof Insert) {
			key = "security-manager-sql-tables-insert";

			TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

			Insert insert = (Insert)statement;

			List<String> tableNames = tablesNamesFinder.getTableList(insert);

			value = StringUtil.merge(tableNames);
		}
		else if (statement instanceof Replace) {
			key = "security-manager-sql-tables-replace";

			TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

			Replace replace = (Replace)statement;

			List<String> tableNames = tablesNamesFinder.getTableList(replace);

			value = StringUtil.merge(tableNames);
		}
		else if (statement instanceof Select) {
			key = "security-manager-sql-tables-select";

			TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

			Select select = (Select)statement;

			List<String> tableNames = tablesNamesFinder.getTableList(select);

			value = StringUtil.merge(tableNames);
		}
		else if (statement instanceof Truncate) {
			key = "security-manager-sql-tables-truncate";

			Truncate truncate = (Truncate)statement;

			Table table = truncate.getTable();

			value = table.getName();
		}
		else if (statement instanceof Update) {
			key = "security-manager-sql-tables-update";

			TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

			Update update = (Update)statement;

			List<String> tableNames = tablesNamesFinder.getTableList(update);

			value = StringUtil.merge(tableNames);
		}
		else {
			return null;
		}

		AuthorizationProperty authorizationProperty =
			new AuthorizationProperty();

		authorizationProperty.setKey(key);
		authorizationProperty.setValue(value);

		return authorizationProperty;
	}

	public boolean hasSQL(String sql) {
		Statement statement = null;

		try {
			statement = _jSqlParser.parse(new StringReader(sql));
		}
		catch (Exception e) {
			_log.error("Unable to parse SQL " + sql);

			return false;
		}

		if (statement instanceof CreateIndex) {
			CreateIndex createIndex = (CreateIndex)statement;

			return hasSQL(createIndex);
		}
		else if (statement instanceof CreateTable) {
			CreateTable createTable = (CreateTable)statement;

			return hasSQL(createTable);
		}
		else if (statement instanceof Select) {
			Select select = (Select)statement;

			return hasSQL(select);
		}
		else if (statement instanceof Delete) {
			Delete delete = (Delete)statement;

			return hasSQL(delete);
		}
		else if (statement instanceof Drop) {
			Drop drop = (Drop)statement;

			return hasSQL(drop);
		}
		else if (statement instanceof Insert) {
			Insert insert = (Insert)statement;

			return hasSQL(insert);
		}
		else if (statement instanceof Replace) {
			Replace replace = (Replace)statement;

			return hasSQL(replace);
		}
		else if (statement instanceof Select) {
			Select select = (Select)statement;

			return hasSQL(select);
		}
		else if (statement instanceof Truncate) {
			Truncate truncate = (Truncate)statement;

			return hasSQL(truncate);
		}
		else if (statement instanceof Update) {
			Update update = (Update)statement;

			return hasSQL(update);
		}

		return false;
	}

	@Override
	public boolean implies(Permission permission) {
		throw new UnsupportedOperationException();
	}

	protected boolean hasSQL(CreateIndex createIndex) {
		return isAllowedTable(createIndex.getTable(), _indexTableNames);
	}

	protected boolean hasSQL(CreateTable createTable) {
		return isAllowedTable(createTable.getTable(), _createTableNames);
	}

	protected boolean hasSQL(Delete delete) {
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

		List<String> tableNames = tablesNamesFinder.getTableList(delete);

		return isAllowedTables(tableNames, _deleteTableNames);
	}

	protected boolean hasSQL(Drop drop) {
		return isAllowedTable(drop.getName(), _dropTableNames);
	}

	protected boolean hasSQL(Insert insert) {
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

		List<String> tableNames = tablesNamesFinder.getTableList(insert);

		return isAllowedTables(tableNames, _insertTableNames);
	}

	protected boolean hasSQL(Replace replace) {
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

		List<String> tableNames = tablesNamesFinder.getTableList(replace);

		return isAllowedTables(tableNames, _replaceTableNames);
	}

	protected boolean hasSQL(Select select) {
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

		List<String> tableNames = tablesNamesFinder.getTableList(select);

		return isAllowedTables(tableNames, _selectTableNames);
	}

	protected boolean hasSQL(Truncate truncate) {
		return isAllowedTable(truncate.getTable(), _truncateTableNames);
	}

	protected boolean hasSQL(Update update) {
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

		List<String> tableNames = tablesNamesFinder.getTableList(update);

		return isAllowedTables(tableNames, _updateTableNames);
	}

	protected void initTableNames() {
		_allTableNames = getPropertySet("security-manager-sql-tables-all");
		_createTableNames = getPropertySet(
			"security-manager-sql-tables-create");
		_deleteTableNames = getPropertySet(
			"security-manager-sql-tables-delete");
		_dropTableNames = getPropertySet("security-manager-sql-tables-drop");
		_indexTableNames = getPropertySet("security-manager-sql-tables-index");
		_insertTableNames = getPropertySet(
			"security-manager-sql-tables-insert");
		_replaceTableNames = getPropertySet(
			"security-manager-sql-tables-replace");
		_selectTableNames = getPropertySet(
			"security-manager-sql-tables-select");
		_truncateTableNames = getPropertySet(
			"security-manager-sql-tables-truncate");
		_updateTableNames = getPropertySet(
			"security-manager-sql-tables-update");
	}

	protected boolean isAllowedTable(
		String tableName, Set<String> allowedTableNames) {

		if (_allTableNames.contains(tableName) ||
			allowedTableNames.contains(tableName)) {

			return true;
		}

		return false;
	}

	protected boolean isAllowedTable(
		Table table, Set<String> allowedTableNames) {

		String tableName = table.getName();

		return isAllowedTable(tableName, allowedTableNames);
	}

	protected boolean isAllowedTables(
		List<String> tableNames, Set<String> allowedTableNames) {

		for (String tableName : tableNames) {
			if (!isAllowedTable(tableName, allowedTableNames)) {
				return false;
			}
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(SQLChecker.class);

	private Set<String> _allTableNames;
	private Set<String> _createTableNames;
	private Set<String> _deleteTableNames;
	private Set<String> _dropTableNames;
	private Set<String> _indexTableNames;
	private Set<String> _insertTableNames;
	private JSqlParser _jSqlParser = new CCJSqlParserManager();
	private Set<String> _replaceTableNames;
	private Set<String> _selectTableNames;
	private Set<String> _truncateTableNames;
	private Set<String> _updateTableNames;

}