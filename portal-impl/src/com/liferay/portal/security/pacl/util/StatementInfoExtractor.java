/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl.util;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.TableRegistryUtil;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

import jodd.util.CharUtil;

/**
 * @author Zsolt Berentey
 */
public class StatementInfoExtractor {

	public static StatementInfo getStatementInfo(String sql) {
		StatementInfo statementInfo = extractStatementInfo(sql);

		if ((statementInfo != null) && statementInfo.isParseTables()) {
			List<String> tables = extractTableNames(
				sql, TableRegistryUtil.getTableNames(), statementInfo);

			statementInfo.setTableNames(tables);
		}

		return statementInfo;
	}

	protected static BitSet createLiteralBitSet(String sql) {
		BitSet bitSet = new BitSet();

		int openPos = -1;

		for (int i = 0; i < sql.length(); i++) {
			if (sql.charAt(i) == CharPool.APOSTROPHE) {
				if ((i > 0) && (sql.charAt(i - 1) == CharPool.BACK_SLASH)) {
					continue;
				}

				if (openPos == -1) {
					openPos = i;
				}
				else {
					bitSet.set(openPos + 1, i);

					openPos = -1;
				}
			}
		}

		return bitSet;
	}

	protected static StatementInfo extractStatementInfo(String sql) {
		BitSet literalBitSet = createLiteralBitSet(sql);

		StatementInfo statementInfo = new StatementInfo();

		statementInfo.setLiteralBitSet(literalBitSet);

		for (String keyword : _KEYWORDS) {
			if (sql.startsWith(keyword)) {
				statementInfo.setOperation(StringUtil.trim(keyword));

				break;
			}
		}

		if (statementInfo.getOperation() == null) {
			return null;
		}

		for (String object : _OBJECTS) {
			int pos = sql.indexOf(object);

			if ((pos > -1) && !literalBitSet.get(pos)) {
				statementInfo.setObjectType(StringUtil.trim(object));

				break;
			}
		}

		if (statementInfo.hasObjectType("table")) {
			statementInfo.setMainTable(true);
			statementInfo.setParseTables(true);

			return statementInfo;
		}

		for (StatementInfo tableOperation : _TABLE_OPERATIONS) {
			String operation = tableOperation.getOperation();

			if (operation.equals(statementInfo.getOperation())) {
				if ((tableOperation.getObjectType() == null) ||
					tableOperation.hasObjectType(
						statementInfo.getObjectType())) {

					statementInfo.setParseTables(
						tableOperation.isParseTables());
					statementInfo.setMainTable(tableOperation.hasMainTable());

					break;
				}
			}
		}

		return statementInfo;
	}

	protected static List<String> extractTableNames(
		String sql, List<String> registeredTables,
		StatementInfo statementInfo) {

		BitSet literalBitSet = statementInfo.getLiteralBitSet();

		List<TableName> tables = new ArrayList<TableName>();

		for (String tableName : registeredTables) {
			int fromPos = 0;

			while (true) {
				int pos = sql.indexOf(tableName, fromPos);

				if (pos > -1) {
					if (!literalBitSet.get(pos)) {
						if (!isContainedInColumnName(sql, tableName, pos)) {
							TableName foundTable = new TableName(
								pos, tableName);

							for (TableName table : tables) {
								if (table.matches(foundTable)) {
									foundTable = null;

									break;
								}
							}

							if (foundTable != null) {
								tables.add(foundTable);
							}
						}
					}

					fromPos = pos + 1;

					continue;
				}

				break;
			}
		}

		Collections.sort(tables);

		List<String> tableNames = new ArrayList<String>();

		for (TableName table : tables) {
			tableNames.add(table.getName());
		}

		return tableNames;
	}

	protected static boolean isContainedInColumnName(
		String sql, String tableName, int pos) {

		int afterPos = pos + tableName.length();

		if ((sql.length() > afterPos) &&
			(sql.charAt(afterPos) == CharPool.PERIOD)) {

			return false;
		}

		for (int i = pos - 1; i >= 0; i--) {
			int cp = sql.codePointAt(i);

			if (Character.isWhitespace(cp)) {
				break;
			}

			if (Character.isLetterOrDigit(cp)) {
				return true;
			}

			char c = sql.charAt(i);

			// These can occur before table names

			if (CharUtil.equalsOne(c, _ALLOWED_BEFORE_TABLE_NAMES)) {
				break;
			}

			// Since we're matching full table names, finding any of these
			// characters before the table name means that it's not really
			// a table name, but a column name

			if (CharUtil.equalsOne(c, _NOT_ALLOWED_BEFORE_TABLE_NAMES)) {
				return true;
			}
		}

		if (sql.length() <= afterPos) {
			return false;
		}

		for (int i = afterPos; i < sql.length(); i++) {
			int cp = sql.codePointAt(i);

			// Based on the assumption that we do not allow column names to
			// be the same as table names, finding a space after a matched
			// string means it's a table name

			if (Character.isWhitespace(cp)) {
				return false;
			}

			if (Character.isLetterOrDigit(cp)) {
				return true;
			}

			char c = sql.charAt(i);

			// At this point finding any of these characters after the matched
			// table confirms that it's a table name

			if (CharUtil.equalsOne(c, _ALLOWED_AFTER_TABLE_NAMES)) {
				return false;
			}

			// These characters are either invalid after a table name or
			// they are allowed in a table name, but since we're matching
			// full table names, this is not a possible case

			if (CharUtil.equalsOne(c, _NOT_ALLOWED_AFTER_TABLE_NAMES)) {
				return true;
			}
		}

		return false;
	}

	private static final char[] _ALLOWED_AFTER_TABLE_NAMES = {
		CharPool.BACK_TICK, CharPool.CLOSE_BRACKET, CharPool.QUOTE
	};

	private static final char[] _ALLOWED_BEFORE_TABLE_NAMES = {
		CharPool.BACK_TICK, CharPool.COMMA, CharPool.OPEN_BRACKET,
		CharPool.OPEN_PARENTHESIS, CharPool.PERIOD, CharPool.QUOTE,
	};

	private static final String[] _KEYWORDS = {
		"alter ", "create ", "delete ", "drop ", "insert ", "merge ", "rename ",
		"replace ", "select ", "truncate ", "update "
	};

	private static final char[] _NOT_ALLOWED_AFTER_TABLE_NAMES = {
		CharPool.AT, CharPool.DASH, CharPool.DOLLAR, CharPool.EQUAL,
		CharPool.EXCLAMATION, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.UNDERLINE,
	};

	private static final char[] _NOT_ALLOWED_BEFORE_TABLE_NAMES = {
		CharPool.AT, CharPool.DASH, CharPool.DOLLAR, CharPool.UNDERLINE
	};

	private static final String[] _OBJECTS = {
		" database ", " table ", " index ", " view "
	};

	private static final StatementInfo[] _TABLE_OPERATIONS = {
		new StatementInfo ("create", "index", true, true),
		new StatementInfo ("create", "view", true, false),
		new StatementInfo ("delete", null, true, true),
		new StatementInfo ("insert", null, true, true),
		new StatementInfo ("merge", null, true, true),
		new StatementInfo ("rename", null, true, true),
		new StatementInfo ("replace", null, true, true),
		new StatementInfo ("select", null, true, false),
		new StatementInfo ("update", null, true, true)
	};

	private static class TableName implements Comparable<TableName> {

		public TableName(int startPos, String name) {
			_startPos = startPos;
			_endPos = startPos + name.length();
			_name = name;
		}

		public int compareTo(TableName table) {
			if (_startPos < table._startPos) {
				return -1;
			}

			if (_startPos > table._startPos) {
				return 1;
			}

			return 0;
		}

		public boolean matches(TableName table) {
			if (_name.equals(table.getName()) ||
				((_startPos <= table._startPos) &&
					(_endPos >= table._endPos))) {

				return true;
			}

			return false;
		}

		public String getName() {
			return _name;
		}

		private int _endPos;
		private String _name;
		private int _startPos;

	}

}