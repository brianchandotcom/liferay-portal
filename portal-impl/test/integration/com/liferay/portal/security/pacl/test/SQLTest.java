/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl.test;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.test.rule.PACLTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class SQLTest {

	@ClassRule
	@Rule
	public static final PACLTestRule paclTestRule = new PACLTestRule();

	@Test
	public void testCreate1() throws Exception {
		try {
			executePreparedStatement(
				"create table TestPACL_CreateFailure (userId bigint)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testCreate2() throws Exception {
		try {
			executeStatement(
				"create table TestPACL_CreateFailure (userId bigint)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testCreate3() throws Exception {
		executePreparedStatement(
			"create table TestPACL_CreateSuccess (userId bigint)");

		executePreparedStatement("drop table TestPACL_CreateSuccess");
	}

	@Test
	public void testCreate4() throws Exception {
		executeStatement("create table TestPACL_CreateSuccess (userId bigint)");

		executeStatement("drop table TestPACL_CreateSuccess");
	}

	@Test
	public void testDrop1() throws Exception {
		try {
			executePreparedStatement("drop table TestPACL_DropFailure");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testDrop2() throws Exception {
		try {
			executeStatement("drop table TestPACL_DropFailure");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testDrop3() throws Exception {
		executePreparedStatement(
			"create table TestPACL_DropSuccess (userId bigint)");

		executePreparedStatement("drop table TestPACL_DropSuccess");
	}

	@Test
	public void testDrop4() throws Exception {
		executeStatement("create table TestPACL_DropSuccess (userId bigint)");

		executeStatement("drop table TestPACL_DropSuccess");
	}

	@Test
	public void testIndex1() throws Exception {
		try {
			executePreparedStatement(
				"create index index1 ON TestPACL_CreateFailure (userId)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testIndex2() throws Exception {
		try {
			executeStatement(
				"create index index1 ON TestPACL_CreateFailure (userId)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testIndex3() throws Exception {
		executePreparedStatement(
			"create index index1 ON TestPACL_CreateSuccess (userId)");
	}

	@Test
	public void testIndex4() throws Exception {
		executeStatement(
			"create index index1 ON TestPACL_CreateSuccess (userId)");
	}

	@Test
	public void testInsert1() throws Exception {
		try {
			executePreparedStatement(
				"insert into TestPACL_InsertFailure values (1)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testInsert2() throws Exception {
		try {
			executeStatement("insert into TestPACL_InsertFailure values (1)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testInsert3() throws Exception {
		executePreparedStatement(
			"create table TestPACL_InsertSuccess (userId bigint)");

		executePreparedStatement(
			"insert into TestPACL_InsertSuccess values (1)");

		executePreparedStatement("drop table TestPACL_InsertSuccess");
	}

	@Test
	public void testInsert4() throws Exception {
		executeStatement("create table TestPACL_InsertSuccess (userId bigint)");

		executeStatement("insert into TestPACL_InsertSuccess values (1)");

		executeStatement("drop table TestPACL_InsertSuccess");
	}

	@Test
	public void testReplace1() throws Exception {
		if (!isMySQL()) {
			return;
		}

		try {
			executePreparedStatement(
				"replace TestPACL_ReplaceFailure (userId) values (1)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testReplace2() throws Exception {
		if (!isMySQL()) {
			return;
		}

		try {
			executeStatement(
				"replace TestPACL_ReplaceFailure (userId) values (1)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testReplace3() throws Exception {
		if (!isMySQL()) {
			return;
		}

		executePreparedStatement(
			"create table TestPACL_ReplaceSuccess (userId bigint)");

		executePreparedStatement(
			"replace TestPACL_ReplaceSuccess (userId) values (1)");

		executePreparedStatement("drop table TestPACL_ReplaceSuccess");
	}

	@Test
	public void testReplace4() throws Exception {
		if (!isMySQL()) {
			return;
		}

		executeStatement(
			"create table TestPACL_ReplaceSuccess (userId bigint)");

		executeStatement("replace TestPACL_ReplaceSuccess (userId) values (1)");

		executeStatement("drop table TestPACL_ReplaceSuccess");
	}

	@Test
	public void testSelect1() throws Exception {
		try {
			executePreparedStatement(
				"select * from Counter inner join User_ on User_.userId = " +
					"Counter.currentId");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testSelect2() throws Exception {
		try {
			executeStatement(
				"select * from Counter inner join User_ on User_.userId = " +
					"Counter.currentId");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testSelect3() throws Exception {
		executePreparedStatement("select * from Counter");
	}

	@Test
	public void testSelect4() throws Exception {
		executeStatement("select * from Counter");
	}

	@Test
	public void testSelect5() throws Exception {
		executePreparedStatement("select * from TestPACL_Bar");
	}

	@Test
	public void testSelect6() throws Exception {
		executeStatement("select * from TestPACL_Bar");
	}

	@Test
	public void testSelect7() throws Exception {
		try {
			executePreparedStatement("select * from User_");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testSelect8() throws Exception {
		try {
			executeStatement("select * from User_");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testTruncate1() throws Exception {
		if (!isMySQL()) {
			return;
		}

		try {
			executePreparedStatement("truncate table TestPACL_TruncateFailure");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testTruncate2() throws Exception {
		if (!isMySQL()) {
			return;
		}

		try {
			executeStatement("truncate table TestPACL_TruncateFailure");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testTruncate3() throws Exception {
		if (!isMySQL()) {
			return;
		}

		executePreparedStatement(
			"create table TestPACL_TruncateSuccess (userId bigint)");

		executePreparedStatement("truncate table TestPACL_TruncateSuccess");

		executePreparedStatement("drop table TestPACL_TruncateSuccess");
	}

	@Test
	public void testTruncate4() throws Exception {
		if (!isMySQL()) {
			return;
		}

		executeStatement(
			"create table TestPACL_TruncateSuccess (userId bigint)");

		executeStatement("truncate table TestPACL_TruncateSuccess");

		executeStatement("drop table TestPACL_TruncateSuccess");
	}

	@Test
	public void testUpdate1() throws Exception {
		executeDB(
			"update ListType set name = 'Test PACL' where listTypeId = -123");
	}

	@Test
	public void testUpdate2() throws Exception {
		executePreparedStatement(
			"update ListType set name = 'Test PACL' where listTypeId = -123");
	}

	@Test
	public void testUpdate3() throws Exception {
		executeStatement(
			"update ListType set name = 'Test PACL' where listTypeId = -123");
	}

	@Test
	public void testUpdate4() throws Exception {
		try {
			executeDB(
				"update ListType set name = 'Test PACL' where listTypeId = " +
					"(select userId from User_)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testUpdate5() throws Exception {
		try {
			executePreparedStatement(
				"update ListType set name = 'Test PACL' where listTypeId = " +
					"(select userId from User_)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testUpdate6() throws Exception {
		try {
			executeStatement(
				"update ListType set name = 'Test PACL' where listTypeId = " +
					"(select userId from User_)");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testUpdate7() throws Exception {
		try {
			executeDB(
				"update User_ set firstName = 'Test PACL' where userId = -123");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testUpdate8() throws Exception {
		try {
			executePreparedStatement(
				"update User_ set firstName = 'Test PACL' where userId = -123");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testUpdate9() throws Exception {
		try {
			executeStatement(
				"update User_ set firstName = 'Test PACL' where userId = -123");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	protected void executeDB(String sql) throws Exception {
		DB db = DBFactoryUtil.getDB();

		db.runSQL(sql);
	}

	protected void executePreparedStatement(String sql) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = DataAccess.getConnection();

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.execute();
		}
		catch (SQLException se) {
		}
		finally {
			DataAccess.cleanUp(connection, preparedStatement);
		}
	}

	protected void executeStatement(String sql) throws Exception {
		Connection connection = null;
		Statement statement = null;

		try {
			connection = DataAccess.getConnection();

			statement = connection.createStatement();

			statement.execute(sql);
		}
		catch (SQLException se) {
		}
		finally {
			DataAccess.cleanUp(connection, statement);
		}
	}

	protected boolean isMySQL() {
		DB db = DBFactoryUtil.getDB();

		if (db.getDBType() == DBType.MYSQL) {
			return true;
		}

		return false;
	}

}