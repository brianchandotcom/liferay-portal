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

import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.util.TableRegistryUtil;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.testng.Assert;

/**
 * @author Zsolt Berentey
 */
public class StatementInfoExtractorTest {

	@Before
	public void setUp() throws Exception {
		PortalClassLoaderUtil.setClassLoader(
			Thread.currentThread().getContextClassLoader());

		TableRegistryUtil.init();
	}

	@Test
	public void testMainTable() {
		StatementInfo info = StatementInfoExtractor.getStatementInfo(
			"drop index index_name");

		assertMainTable(info, false, null);

		info = StatementInfoExtractor.getStatementInfo(
			"delete from users_usergroups where exists (select 1 from user_)");

		assertMainTable(info, true, "users_usergroups");

		info = StatementInfoExtractor.getStatementInfo(
			"select * from Users_UserGroups where id = 1 and exists " +
				"(select 1 from usergroup inner join usergroupgrouprole)");

		assertMainTable(info, false, null);
	}

	@Test
	public void testObjectType() {
		StatementInfo info = StatementInfoExtractor.getStatementInfo(
			"create unique index 'database' on usergroup (col1, col2)");

		assertObjectType(info, "index");

		info = StatementInfoExtractor.getStatementInfo(
			"select * from Users_UserGroups where id = 1");

		assertObjectType(info, null);

		info = StatementInfoExtractor.getStatementInfo(
			"alter table user_ do something here");

		assertObjectType(info, "table");
	}

	@Test
	public void testReadTables() {
		StatementInfo info = StatementInfoExtractor.getStatementInfo(
			"select * from user_, usergroup where username = 'assetentry' or " +
				"username like 'some % assetentry %' and exists " +
					"(select 1 from usergroup inner join usergroupgrouprole)");

		assertReadTables(
			info, new String[] {"user_", "usergroup", "usergroupgrouprole"});

		info = StatementInfoExtractor.getStatementInfo("drop index index_name");

		assertReadTables(info, null);

		info = StatementInfoExtractor.getStatementInfo("select * from Counter");

		assertReadTables(info, new String[] {"counter"});
	}

	@Test
	public void testTableNameMatching() {
		StatementInfo info;

		for (Tuple match : _matches) {
			String sql = (String)match.getObject(0);

			info = StatementInfoExtractor.getStatementInfo(sql);

			assertReadTablesContains(
				sql, info, "company", (Boolean)match.getObject(1));
		}
	}

	protected void assertMainTable(
		StatementInfo info, boolean hasMain, String expectedName) {

		Assert.assertEquals(info.hasMainTable(), hasMain);

		if (expectedName == null) {
			Assert.assertNull(info.getMainTable());
		}
		else {
			Assert.assertEquals(info.getMainTable(), expectedName);
		}
	}

	protected void assertObjectType(StatementInfo info, String expectedType) {
		if (expectedType == null) {
			Assert.assertNull(info.getObjectType());
		}
		else {
			Assert.assertEquals(info.getObjectType(), expectedType);
		}
	}

	protected void assertReadTables(
		StatementInfo info, String[] expectedNames) {

		List<String> readTables = info.getReadTables();

		if (expectedNames == null) {
			Assert.assertEquals(readTables.size(), 0);
		}
		else {
			Assert.assertEquals(readTables.size(), expectedNames.length);

			for (String expectedName : expectedNames) {
				Assert.assertTrue(readTables.contains(expectedName));
			}
		}
	}

	protected void assertReadTablesContains(
		String sql, StatementInfo info, String tableName, boolean contains) {

		List<String> readTables = info.getReadTables();

		Assert.assertEquals(
			readTables.contains(tableName), contains, "SQL: " + sql);
	}

	private static Tuple[] _matches = {
		new Tuple("select * from user_ where user_.companyId = 1", false),
		new Tuple(
			"select * from user_ where schema.company.name = 'something'",
			true),
		new Tuple("select * from user_,`company` where ...", true),
		new Tuple("select * from user_,company where ...", true),
		new Tuple(
			"select * from user_,schema.company where ...",
			true),
		new Tuple("select * from user_ where mycompany=1234", false),
		new Tuple("select * from user_ where company=1234", false),
		new Tuple(
			"select * from user_ where user_.companyId=[company].companyId",
			true)
	};

}