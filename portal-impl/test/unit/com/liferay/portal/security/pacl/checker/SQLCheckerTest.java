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

import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.pacl.ActivePACLPolicy;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.util.PropsImpl;

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
		PropsUtil.setProps(new PropsImpl());

		Properties props = new Properties();

		props.put("security-manager-sql-tables-all", "prefix_*");
		props.put("security-manager-sql-tables-alter", "allowedTable");
		props.put("security-manager-sql-tables-create", "allowedTable");
		props.put("security-manager-sql-tables-delete", "allowedTable");
		props.put("security-manager-sql-tables-drop", "allowedTable");
		props.put("security-manager-sql-tables-insert", "allowedTable");
		props.put("security-manager-sql-tables-replace", "allowedTable");
		props.put("security-manager-sql-tables-select", "allowedTable");
		props.put("security-manager-sql-tables-truncate", "allowedTable");
		props.put("security-manager-sql-tables-update", "allowedTable");

		_paclPolicy = new ActivePACLPolicy(
			"test-context", Thread.currentThread().getContextClassLoader(),
			props);
	}

	@Test
	public void testAlterTable() {
		testSql(_SQL_ALTER_TABLE, "prefix_SomeTable", null, true);
		testSql(_SQL_ALTER_TABLE, "notAllowedTable", null, false);
		testSql(_SQL_ALTER_TABLE, "allowedTable", null, true);
	}

	@Test
	public void testCreateIndex() {
		testSql(_SQL_CREATE_INDEX, "prefix_SomeTable", null, true);
		testSql(_SQL_CREATE_INDEX, "notAllowedTable", null, false);
		testSql(_SQL_CREATE_INDEX, "allowedTable", null, true);
	}

	@Test
	public void testCreateTable() {
		testSql(_SQL_CREATE_TABLE, "prefix_SomeTable", null, true);
		testSql(_SQL_CREATE_TABLE, "notAllowedTable", null, false);
		testSql(_SQL_CREATE_TABLE, "allowedTable", null, true);
	}

	@Test
	public void testDelete() {
		testSql(_SQL_DELETE, "prefix_SomeTable", null, true);
		testSql(_SQL_DELETE, "notAllowedTable", null, false);
		testSql(_SQL_DELETE, "allowedTable", null, true);
	}

	@Test
	public void testDrop() {
		testSql(_SQL_DROP_TABLE, "prefix_SomeTable", null, true);
		testSql(_SQL_DROP_TABLE, "notAllowedTable", null, false);
		testSql(_SQL_DROP_TABLE, "allowedTable", null, true);
	}

	@Test
	public void testInsert() {
		testSql(_SQL_INSERT, "prefix_SomeTable", null, true);
		testSql(_SQL_INSERT, "notAllowedTable", null, false);
		testSql(_SQL_INSERT, "allowedTable", null, true);
	}

	@Test
	public void testReplace() {
		testSql(_SQL_REPLACE, "prefix_SomeTable", null, true);
		testSql(_SQL_REPLACE, "notAllowedTable", null, false);
		testSql(_SQL_REPLACE, "allowedTable", null, true);
	}

	@Test
	public void testSelect() {
		testSql(_SQL_SELECT, "prefix_SomeTable", null, true);
		testSql(_SQL_SELECT, "notAllowedTable", null, false);
		testSql(_SQL_SELECT, "allowedTable", null, true);

		testSql(_SQL_SELECT_MULTI, "prefix_SomeTable", "prefix_Another", true);
		testSql(_SQL_SELECT_MULTI, "notAllowedTable", "allowedTable", false);
		testSql(_SQL_SELECT_MULTI, "allowedTable", "allowedTable", true);
	}

	@Test
	public void testTruncate() {
		testSql(_SQL_TRUNCATE, "prefix_SomeTable", null, true);
		testSql(_SQL_TRUNCATE, "notAllowedTable", null, false);
		testSql(_SQL_TRUNCATE, "allowedTable", null, true);
	}

	@Test
	public void testUpdate() {
		testSql(_SQL_UPDATE, "prefix_SomeTable", null, true);
		testSql(_SQL_UPDATE, "notAllowedTable", null, false);
		testSql(_SQL_UPDATE, "allowedTable", null, true);
	}

	protected void testSql(
		String sql, String table1, String table2, boolean expected) {

		sql = StringUtil.replace(
			sql, new String[] {"<table>", "<another_table>"},
			new String[] {table1, table2});

		boolean actual = _paclPolicy.hasSQL(sql);

		Assert.assertEquals(expected, actual);
	}

	private static final String _SQL_ALTER_TABLE =
		"alter table <table> add column (test varchar(255))";
	private static final String _SQL_CREATE_INDEX =
			"create index index_name on <table> (id)";
	private static final String _SQL_CREATE_TABLE =
		"create table <table> (test varchar(255))";
	private static final String _SQL_DELETE =
		"delete from <table> where id = 12345";
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