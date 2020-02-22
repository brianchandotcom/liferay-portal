/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.test.BaseDBTestCase;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.lang.reflect.Method;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Miguel Pastor
 * @author Alberto Chaparro
 */
public class SybaseDBTest extends BaseDBTestCase {

	@BeforeClass
	public static void setUpClass() {
		_props = PropsUtil.getProps();

		PropsTestUtil.setProps(
			PropsKeys.DATABASE_STRING_INDEX_MAX_LENGTH, "-1");
	}

	@AfterClass
	public static void tearDownClass() {
		PropsUtil.setProps(_props);
	}

	@Test
	public void testApplyMaxStringIndexLengthLimitation() throws Exception {
		DB db = getDB();

		Method method = ReflectionTestUtil.getMethod(
			db.getClass(), "replaceTemplate", String.class);

		Assert.assertEquals(
			"create index 0 on Test (test);",
			method.invoke(
				db, "create index 0 on Test (test[$COLUMN_LENGTH:200$]);"));

		Assert.assertNull(
			method.invoke(
				db, "create index 0 on Test (test[$COLUMN_LENGTH:1251$]);"));
	}

	@Test
	public void testRewordAlterColumnType() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder modify userName varchar(75)\ngo\n",
			buildSQL("alter_column_type DLFolder userName VARCHAR(75);"));
	}

	@Test
	public void testRewordAlterColumnTypeNoSemicolon() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder modify userName varchar(75)\ngo\n",
			buildSQL("alter_column_type DLFolder userName VARCHAR(75)"));
	}

	@Test
	public void testRewordAlterColumnTypeNotNull() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder modify userName varchar(75) not null;\n",
			buildSQL(
				"alter_column_type DLFolder userName VARCHAR(75) not null;"));
	}

	@Test
	public void testRewordAlterColumnTypeNull() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder modify userName varchar(75) null;\n",
			buildSQL("alter_column_type DLFolder userName VARCHAR(75) null;"));
	}

	@Test
	public void testRewordRenameTable() throws Exception {
		Assert.assertEquals(
			"exec sp_rename a, b;\n", buildSQL(RENAME_TABLE_QUERY));
	}

	@Override
	protected DB getDB() {
		return new SybaseDB(0, 0);
	}

	private static Props _props;

}