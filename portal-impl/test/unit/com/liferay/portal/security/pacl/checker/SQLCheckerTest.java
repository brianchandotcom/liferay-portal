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

import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.pacl.ActivePACLPolicy;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.TableRegistryUtil;

import java.util.Properties;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Zsolt Berentey
 */
public class SQLCheckerTest {

	@Before
	public void setUp() throws Exception {
		PortalClassLoaderUtil.setClassLoader(
			Thread.currentThread().getContextClassLoader());

		TableRegistryUtil.init();

		PropsUtil.setProps(new PropsImpl());

		Properties props = new Properties();

		props.put("security-manager-sql-tables-all", _ALLOWED_PREFIX);
		props.put("security-manager-sql-tables-alter-table", _ALLOWED_TABLE);
		props.put("security-manager-sql-tables-create-index", _ALLOWED_TABLE);
		props.put("security-manager-sql-tables-create-any", _ALLOWED_TABLE);
		props.put("security-manager-sql-tables-delete", _ALLOWED_TABLE);
		props.put("security-manager-sql-tables-drop-index", "yes");
		props.put("security-manager-sql-tables-drop-table", _ALLOWED_TABLE);
		props.put("security-manager-sql-tables-insert", _ALLOWED_TABLE);
		props.put("security-manager-sql-tables-replace", _ALLOWED_TABLE);
		props.put("security-manager-sql-tables-select", _ALLOWED_TABLE);
		props.put("security-manager-sql-tables-truncate-table", _ALLOWED_TABLE);
		props.put("security-manager-sql-tables-update", _ALLOWED_TABLE);

		_paclPolicy = new ActivePACLPolicy(
			"test-context", PortalClassLoaderUtil.getClassLoader(), props);
	}

	@Test
	public void testAlterTable() {
		testSql(_SQL_ALTER_TABLE, _ALLOWED_PREFIX_TABLE_1, null, true);
		testSql(_SQL_ALTER_TABLE, _NOT_ALLOWED_TABLE, null, false);
		testSql(_SQL_ALTER_TABLE, _ALLOWED_TABLE, null, true);
		testSql(_SQL_ALTER_TABLE, _NOT_PROTECTED_TABLE, null, true);
	}

	@Test
	public void testCreateIndex() {
		testSql(_SQL_CREATE_INDEX, _ALLOWED_PREFIX_TABLE_1, null, true);
		testSql(_SQL_CREATE_INDEX, _NOT_ALLOWED_TABLE, null, false);
		testSql(_SQL_CREATE_INDEX, _ALLOWED_TABLE, null, true);
		testSql(_SQL_CREATE_INDEX, _NOT_PROTECTED_TABLE, null, true);
	}

	@Test
	public void testCreateTable() {
		testSql(_SQL_CREATE_TABLE, _ALLOWED_PREFIX_TABLE_1, null, true);
		testSql(_SQL_CREATE_TABLE, _NOT_ALLOWED_TABLE, null, false);
		testSql(_SQL_CREATE_TABLE, _ALLOWED_TABLE, null, true);
		testSql(_SQL_CREATE_TABLE, _NOT_PROTECTED_TABLE, null, true);
	}

	@Test
	public void testDelete() {
		testSql(_SQL_DELETE, _ALLOWED_PREFIX_TABLE_1, null, true);
		testSql(_SQL_DELETE, _NOT_ALLOWED_TABLE, null, false);
		testSql(_SQL_DELETE, _ALLOWED_TABLE, null, true);
		testSql(_SQL_DELETE, _NOT_PROTECTED_TABLE, null, true);
	}

	@Test
	public void testDrop() {
		testSql(_SQL_DROP_TABLE, _ALLOWED_PREFIX_TABLE_1, null, true);
		testSql(_SQL_DROP_TABLE, _NOT_ALLOWED_TABLE, null, false);
		testSql(_SQL_DROP_TABLE, _ALLOWED_TABLE, null, true);
		testSql(_SQL_DROP_TABLE, _NOT_PROTECTED_TABLE, null, true);

		testSql(_SQL_DROP_INDEX, null, null, true);
	}

	@Test
	public void testInsert() {
		testSql(_SQL_INSERT, _ALLOWED_PREFIX_TABLE_1, null, true);
		testSql(_SQL_INSERT, _NOT_ALLOWED_TABLE, null, false);
		testSql(_SQL_INSERT, _ALLOWED_TABLE, null, true);
		testSql(_SQL_INSERT, _NOT_PROTECTED_TABLE, null, true);
	}

	@Test
	public void testReplace() {
		testSql(_SQL_REPLACE, _ALLOWED_PREFIX_TABLE_1, null, true);
		testSql(_SQL_REPLACE, _NOT_ALLOWED_TABLE, null, false);
		testSql(_SQL_REPLACE, _ALLOWED_TABLE, null, true);
		testSql(_SQL_REPLACE, _NOT_PROTECTED_TABLE, null, true);
	}

	@Test
	public void testSelect() {
		testSql(_SQL_SELECT, _ALLOWED_PREFIX_TABLE_1, null, true);
		testSql(_SQL_SELECT, _NOT_ALLOWED_TABLE, null, false);
		testSql(_SQL_SELECT, _ALLOWED_TABLE, null, true);
		testSql(_SQL_SELECT, _NOT_PROTECTED_TABLE, null, true);

		testSql(
			_SQL_SELECT_MULTI, _ALLOWED_PREFIX_TABLE_1, _ALLOWED_PREFIX_TABLE_2,
			true);
		testSql(_SQL_SELECT_MULTI, _NOT_ALLOWED_TABLE, _ALLOWED_TABLE, false);
		testSql(_SQL_SELECT_MULTI, _ALLOWED_TABLE, _ALLOWED_TABLE, true);
		testSql(
			_SQL_SELECT_MULTI, _NOT_PROTECTED_TABLE, _NOT_ALLOWED_TABLE, false);
	}

	@Test
	public void testTruncate() {
		testSql(_SQL_TRUNCATE, _ALLOWED_PREFIX_TABLE_1, null, true);
		testSql(_SQL_TRUNCATE, _NOT_ALLOWED_TABLE, null, false);
		testSql(_SQL_TRUNCATE, _ALLOWED_TABLE, null, true);
		testSql(_SQL_TRUNCATE, _NOT_PROTECTED_TABLE, null, true);
	}

	@Test
	public void testUpdate() {
		testSql(_SQL_UPDATE, _ALLOWED_PREFIX_TABLE_1, null, true);
		testSql(_SQL_UPDATE, _NOT_ALLOWED_TABLE, null, false);
		testSql(_SQL_UPDATE, _ALLOWED_TABLE, null, true);
		testSql(_SQL_UPDATE, _NOT_PROTECTED_TABLE, null, true);
	}

	protected void testSql(
		String sql, String table1, String table2, boolean expected) {

		sql = StringUtil.replace(
			sql, new String[] {"<table>", "<another_table>"},
			new String[] {table1, table2});

		boolean actual = _paclPolicy.hasSQL(sql);

		Assert.assertEquals(expected, actual);
	}

	private static final String _ALLOWED_PREFIX = "users_*";
	private static final String _ALLOWED_PREFIX_TABLE_1 = "users_groups";
	private static final String _ALLOWED_PREFIX_TABLE_2 = "users_orgs";
	private static final String _ALLOWED_TABLE = "usergroup";
	private static final String _NOT_ALLOWED_TABLE = "usergroupgrouprole";
	private static final String _NOT_PROTECTED_TABLE = "foo";

	private static final String _SQL_ALTER_TABLE =
		"alter table <table> add column (test varchar(255))";
	private static final String _SQL_CREATE_INDEX =
		"create index index_name on <table> (id)";
	private static final String _SQL_CREATE_TABLE =
		"create table <table> (test varchar(255))";
	private static final String _SQL_DELETE =
		"delete from <table> where id = 12345";
	private static final String _SQL_DROP_INDEX = "drop index index_name";
	private static final String _SQL_DROP_TABLE = "drop table <table>";
	private static final String _SQL_INSERT =
		"insert into <table> (id, value) values (1, 2)";
	private static final String _SQL_REPLACE =
		"replace into <table> (id, value) values (1, 2)";
	private static final String _SQL_SELECT =
		"select * from <table> where id = 1";
	private static final String _SQL_SELECT_MULTI =
		"select <table>.* from <table> inner join <another_table> " +
			"on <table>.id = <another_table>.id where name = 'xyz'";
	private static final String _SQL_TRUNCATE = "truncate table <table>";
	private static final String _SQL_UPDATE =
		"update <table> set name = 'xy' where id = 1";

	private PACLPolicy _paclPolicy;

}