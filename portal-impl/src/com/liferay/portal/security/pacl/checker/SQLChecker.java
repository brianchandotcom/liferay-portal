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

package com.liferay.portal.security.pacl.checker;

import com.liferay.apache.derby.iapi.error.StandardException;
import com.liferay.apache.derby.iapi.services.context.ContextManager;
import com.liferay.apache.derby.iapi.services.context.ContextService;
import com.liferay.apache.derby.iapi.sql.compile.CompilerContext;
import com.liferay.apache.derby.iapi.sql.compile.Parser;
import com.liferay.apache.derby.iapi.sql.compile.Visitable;
import com.liferay.apache.derby.iapi.sql.compile.Visitor;
import com.liferay.apache.derby.iapi.sql.conn.LanguageConnectionContext;
import com.liferay.apache.derby.impl.jdbc.EmbedConnection;
import com.liferay.apache.derby.impl.sql.compile.AlterTableNode;
import com.liferay.apache.derby.impl.sql.compile.CompilerContextImpl;
import com.liferay.apache.derby.impl.sql.compile.CreateAliasNode;
import com.liferay.apache.derby.impl.sql.compile.CreateIndexNode;
import com.liferay.apache.derby.impl.sql.compile.CreateTableNode;
import com.liferay.apache.derby.impl.sql.compile.CreateTriggerNode;
import com.liferay.apache.derby.impl.sql.compile.CreateViewNode;
import com.liferay.apache.derby.impl.sql.compile.CursorNode;
import com.liferay.apache.derby.impl.sql.compile.DeleteNode;
import com.liferay.apache.derby.impl.sql.compile.DropAliasNode;
import com.liferay.apache.derby.impl.sql.compile.DropIndexNode;
import com.liferay.apache.derby.impl.sql.compile.DropTableNode;
import com.liferay.apache.derby.impl.sql.compile.DropTriggerNode;
import com.liferay.apache.derby.impl.sql.compile.DropViewNode;
import com.liferay.apache.derby.impl.sql.compile.FromBaseTable;
import com.liferay.apache.derby.impl.sql.compile.FromSubquery;
import com.liferay.apache.derby.impl.sql.compile.GrantNode;
import com.liferay.apache.derby.impl.sql.compile.GrantRoleNode;
import com.liferay.apache.derby.impl.sql.compile.InsertNode;
import com.liferay.apache.derby.impl.sql.compile.ParserImpl;
import com.liferay.apache.derby.impl.sql.compile.SubqueryNode;
import com.liferay.apache.derby.impl.sql.compile.TableName;
import com.liferay.apache.derby.impl.sql.compile.TypeCompilerFactoryImpl;
import com.liferay.apache.derby.impl.sql.compile.UpdateNode;
import com.liferay.apache.derby.jdbc.EmbeddedDriver;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.security.Permission;

import java.sql.DriverManager;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author Brian Wing Shun Chan
 * @author Zsolt Berentey
 */
public class SQLChecker extends BaseChecker {

	public void afterPropertiesSet() {
		initParser();
		initTableNames();
	}

	public void checkPermission(Permission permission) {
		throw new UnsupportedOperationException();
	}

	public boolean hasSQL(String sql) {
		Visitable statement = null;

		try {
			statement = _parser.parseStatement(sql);
		}
		catch (Exception e) {
			_log.error("Unable to parse SQL " + sql);

			return false;
		}

		StatementVisitor visitor = new StatementVisitor(this);

		try {
			visitor.visit(statement);
		}
		catch (StandardException se) {
			_log.error("Unable to determine access for SQL " + sql);

			return false;
		}

		return visitor.isAllowed();
	}

	public boolean isAllowed(int checkPermission, String tableName) {
		if ((_allTableNames == null) || (_tableNames == null)) {
			_log.error("SQL checker not correctly initialized.");

			return false;
		}

		for (TableNameWrapper wrapper : _allTableNames) {
			if (wrapper.equals(tableName)) {
				return true;
			}
		}

		Set<TableNameWrapper> allowedTableNames = _tableNames[checkPermission];

		if (allowedTableNames != null) {
			for (TableNameWrapper wrapper : allowedTableNames) {
				if (wrapper.equals(tableName)) {
					return true;
				}
			}
		}

		return false;
	}

	protected Set<TableNameWrapper> getWrappedPropertySet(String key) {
		Set<TableNameWrapper> propertySet = new HashSet<TableNameWrapper>();

		for (String property : getPropertyArray(key)) {
			propertySet.add(new TableNameWrapper(property));
		}

		return propertySet;
	}

	protected void initParser() {
		if (_parser != null) {
			return;
		}

		try {
			new EmbeddedDriver();

			EmbedConnection conn = (EmbedConnection)DriverManager.getConnection(
				_CONNECTION_URL);

			ContextManager contextManager = conn.getContextManager();

			LanguageConnectionContext languageConnectionContext =
				(LanguageConnectionContext)contextManager.getContext(
					_LANGUAGE_CONNECTION_CONTEXT);

			ContextService contextService = ContextService.getFactory();

			contextService.setCurrentContextManager(contextManager);

			CompilerContext compilerContext = new CompilerContextImpl(
				contextManager, languageConnectionContext,
				new TypeCompilerFactoryImpl());

			_parser = new ParserImpl(compilerContext);
		}
		catch (Exception e) {

			// This is initialization with no external dependencies. Nothing
			// should ever go wrong, so fail loudly.

			throw new IllegalStateException(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected void initTableNames() {
		_tableNames = new Set[9];

		_allTableNames = getWrappedPropertySet(
			"security-manager-sql-tables-all");
		_tableNames[StatementVisitor.ST_ALTER] = getWrappedPropertySet(
			"security-manager-sql-tables-alter");
		_tableNames[StatementVisitor.ST_CREATE] = getWrappedPropertySet(
			"security-manager-sql-tables-create");
		_tableNames[StatementVisitor.ST_DELETE] = getWrappedPropertySet(
			"security-manager-sql-tables-delete");
		_tableNames[StatementVisitor.ST_DROP] = getWrappedPropertySet(
			"security-manager-sql-tables-drop");
		_tableNames[StatementVisitor.ST_INSERT] = getWrappedPropertySet(
			"security-manager-sql-tables-insert");
		_tableNames[StatementVisitor.ST_SELECT] = getWrappedPropertySet(
			"security-manager-sql-tables-select");
		_tableNames[StatementVisitor.ST_TRUNCATE] = getWrappedPropertySet(
			"security-manager-sql-tables-truncate");
		_tableNames[StatementVisitor.ST_UPDATE] = getWrappedPropertySet(
			"security-manager-sql-tables-update");
	}

	private static final String _CONNECTION_URL =
		"jdbc:derby:memory:dummy;create=true";
	private static final String _LANGUAGE_CONNECTION_CONTEXT =
		"LanguageConnectionContext";

	private static Log _log = LogFactoryUtil.getLog(SQLChecker.class);
	private static Parser _parser = null;

	private Set<TableNameWrapper> _allTableNames;
	private Set<TableNameWrapper>[] _tableNames;

	private class StatementVisitor implements Visitor {

		public static final int ST_ALTER = 0;
		public static final int ST_CREATE = 1;
		public static final int ST_DELETE = 2;
		public static final int ST_DROP = 3;
		public static final int ST_GRANT = 4;
		public static final int ST_INSERT = 5;
		public static final int ST_NONE = -1;
		public static final int ST_SELECT = 6;
		public static final int ST_TRUNCATE = 7;
		public static final int ST_UPDATE = 8;

		public StatementVisitor(SQLChecker sqlChecker) {
			_sqlChecker = sqlChecker;
		}

		public boolean isAllowed() {
			if (_allowed == null) {
				return false;
			}

			return _allowed;
		}

		public boolean skipChildren(Visitable node) throws StandardException {
			return false;
		}

		public boolean stopTraversal() {
			return false;
		}

		public Visitable visit(Visitable node) throws StandardException {
			if (!_visitedNodes.contains(node)) {
				_visitedNodes.add(node);

				boolean changeState = changeState(node);

				String tableName = getTableName(node);

				if (tableName != null) {
					if (currentState.isEmpty()) {
						_allowed = false;
					}
					else {
						boolean allowed = _sqlChecker.isAllowed(
							currentState.peek(), tableName);

						if (!allowed || (allowed && (_allowed == null))) {
							_allowed = allowed;
						}
					}

					if ((_allowed != null) && !_allowed) {
						return node;
					}
				}

				node.accept( this );

				if (changeState) {
					currentState.pop();
				}
			}

			return node;
		}

		public boolean visitChildrenFirst(Visitable node) {
			return false;
		}

		protected boolean changeState(Visitable node) {
			int state = ST_NONE;

			if (node instanceof AlterTableNode) {
				String statementString =
					((AlterTableNode) node).statementToString();

				if (statementString.startsWith("TRUNCATE")) {
					state = ST_TRUNCATE;
				}
				else {
					state = ST_ALTER;
				}
			}
			else if ((node instanceof CreateTableNode) ||
					 (node instanceof CreateIndexNode) ||
					 (node instanceof CreateViewNode) ||
					 (node instanceof CreateTriggerNode) ||
					 (node instanceof CreateAliasNode)) {

				state = ST_CREATE;
			}
			else if (node instanceof DeleteNode) {
				state = ST_DELETE;
			}
			else if ((node instanceof DropTableNode) ||
					 (node instanceof DropIndexNode) ||
					 (node instanceof DropViewNode) ||
					 (node instanceof DropTriggerNode) ||
					 (node instanceof DropAliasNode)) {

				state = ST_DROP;
			}
			else if ((node instanceof GrantNode) ||
					 (node instanceof GrantRoleNode)) {

				state = ST_GRANT;
			}
			else if (node instanceof InsertNode) {
				state = ST_INSERT;
			}
			else if ((node instanceof CursorNode) ||
					 (node instanceof SubqueryNode) ||
					 (node instanceof FromSubquery)) {

				state = ST_SELECT;
			}
			else if (node instanceof UpdateNode) {
				state = ST_UPDATE;
			}

			if (state != ST_NONE) {
				currentState.push(state);

				return true;
			}

			return false;
		}

		protected String getTableName(Visitable node) {
			if (node instanceof AlterTableNode) {
				return ((AlterTableNode)node).getRelativeName();
			}
			else if (node instanceof CreateTableNode) {
				return ((CreateTableNode)node).getRelativeName();
			}
			else if (node instanceof CreateIndexNode) {
				TableName tableName =
					((CreateIndexNode)node).getIndexTableName();

				return tableName.getTableName();
			}
			else if (node instanceof DropTableNode) {
				return ((DropTableNode)node).getRelativeName();
			}
			else if (node instanceof FromBaseTable) {
				return ((FromBaseTable)node).getBaseTableName();
			}
			else if (node instanceof TableName) {
				return ((TableName)node).getTableName();
			}

			return null;
		}

		private Boolean _allowed = null;
		private Stack<Integer> currentState = new Stack<Integer>();
		private SQLChecker _sqlChecker;
		private Set<Visitable> _visitedNodes = new HashSet<Visitable>();

	}

	private class TableNameWrapper {

		public TableNameWrapper(String tableName) {
			if (tableName.contains(StringPool.STAR)) {
				_wildCardCheck = true;

				try {
					_tableNamePattern = Pattern.compile(
						tableName.replaceAll("\\*", ".*?"),
						Pattern.CASE_INSENSITIVE);
				}
				catch (PatternSyntaxException pse) {
					_wildCardCheck = false;
				}
			}

			_tableName = tableName;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof String)) {
				return false;
			}

			String tableName = (String)obj;

			if (_wildCardCheck) {
				Matcher m = _tableNamePattern.matcher(tableName);

				return m.matches();
			}

			return _tableName.equalsIgnoreCase(tableName);
		}

		private String _tableName = null;
		private Pattern _tableNamePattern = null;
		private boolean _wildCardCheck = false;

	}

}