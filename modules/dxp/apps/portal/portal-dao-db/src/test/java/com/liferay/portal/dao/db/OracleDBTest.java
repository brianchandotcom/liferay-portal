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
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PropsUtil;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shinn Lok
 * @author Alberto Chaparro
 */
public class OracleDBTest extends BaseDBTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApplyMaxStringIndexLengthLimitation() throws Exception {
		DB db = getDB();

		Method method = ReflectionTestUtil.getMethod(
			db.getClass(), "replaceTemplate", String.class);

		PropsUtil.set(PropsKeys.DATABASE_STRING_INDEX_MAX_LENGTH, "-1");

		Assert.assertEquals(
			"create index IX on Test (cola);",
			method.invoke(db, "create index IX on Test (cola);"));

		Assert.assertEquals(
			"create index IX on Test (cola);",
			method.invoke(
				db, "create index IX on Test (cola[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb);",
			method.invoke(db, "create index IX on Test (cola, colb);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], colb);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb);",
			method.invoke(
				db,
				"create index IX on Test (cola, colb[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], " +
					"colb[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(db, "create index IX on Test (cola, colb, colc);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], colb, " +
					"colc);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(
				db,
				"create index IX on Test (cola, colb[$COLUMN_LENGTH:4000$], " +
					"colc);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(
				db,
				"create index IX on Test (cola, colb, " +
					"colc[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], " +
					"colb[$COLUMN_LENGTH:4000$], colc);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], colb, " +
					"colc[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], " +
					"colb[$COLUMN_LENGTH:4000$], " +
						"colc[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(
				db,
				"create index IX on Test (cola, colb[$COLUMN_LENGTH:4000$], " +
					"colc[$COLUMN_LENGTH:4000$]);"));

		PropsUtil.set(PropsKeys.DATABASE_STRING_INDEX_MAX_LENGTH, "256");

		Assert.assertEquals(
			"create index IX on Test (cola);",
			method.invoke(db, "create index IX on Test (cola);"));

		Assert.assertEquals(
			"create index IX on Test (substr(cola, 1, 256));",
			method.invoke(
				db, "create index IX on Test (cola[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb);",
			method.invoke(db, "create index IX on Test (cola, colb);"));

		Assert.assertEquals(
			"create index IX on Test (substr(cola, 1, 256), colb);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], colb);"));

		Assert.assertEquals(
			"create index IX on Test (cola, substr(colb, 1, 256));",
			method.invoke(
				db,
				"create index IX on Test (cola, colb[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola,substr(colb, 1, 256));",
			method.invoke(
				db,
				"create index IX on Test (cola,colb[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (substr(cola, 1, 256), substr(colb, 1, " +
				"256));",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], " +
					"colb[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (substr(cola, 1, 256),substr(colb, 1, " +
				"256));",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$]," +
					"colb[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(db, "create index IX on Test (cola, colb, colc);"));

		Assert.assertEquals(
			"create index IX on Test (substr(cola, 1, 256), colb, colc);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], colb, " +
					"colc);"));

		Assert.assertEquals(
			"create index IX on Test (cola, substr(colb, 1, 256), colc);",
			method.invoke(
				db,
				"create index IX on Test (cola, colb[$COLUMN_LENGTH:4000$], " +
					"colc);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, substr(colc, 1, 256));",
			method.invoke(
				db,
				"create index IX on Test (cola, colb, " +
					"colc[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (substr(cola, 1, 256), substr(colb, 1, " +
				"256), colc);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], " +
					"colb[$COLUMN_LENGTH:4000$], colc);"));

		Assert.assertEquals(
			"create index IX on Test (substr(cola, 1, 256), colb, " +
				"substr(colc, 1, 256));",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], colb, " +
					"colc[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (substr(cola, 1, 256), substr(colb, 1, " +
				"256), substr(colc, 1, 256));",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], " +
					"colb[$COLUMN_LENGTH:4000$], " +
						"colc[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola, substr(colb, 1, 256), " +
				"substr(colc, 1, 256));",
			method.invoke(
				db,
				"create index IX on Test (cola, colb[$COLUMN_LENGTH:4000$], " +
					"colc[$COLUMN_LENGTH:4000$]);"));

		PropsUtil.set(PropsKeys.DATABASE_STRING_INDEX_MAX_LENGTH, "4000");

		Assert.assertEquals(
			"create index IX on Test (cola);",
			method.invoke(db, "create index IX on Test (cola);"));

		Assert.assertEquals(
			"create index IX on Test (cola);",
			method.invoke(
				db, "create index IX on Test (cola[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb);",
			method.invoke(db, "create index IX on Test (cola, colb);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], colb);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb);",
			method.invoke(
				db,
				"create index IX on Test (cola, colb[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola,colb);",
			method.invoke(
				db,
				"create index IX on Test (cola,colb[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], " +
					"colb[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola,colb);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$]," +
					"colb[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(db, "create index IX on Test (cola, colb, colc);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], colb, " +
					"colc);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(
				db,
				"create index IX on Test (cola, colb[$COLUMN_LENGTH:4000$], " +
					"colc);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(
				db,
				"create index IX on Test (cola, colb, " +
					"colc[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], " +
					"colb[$COLUMN_LENGTH:4000$], colc);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], colb, " +
					"colc[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(
				db,
				"create index IX on Test (cola[$COLUMN_LENGTH:4000$], " +
					"colb[$COLUMN_LENGTH:4000$], " +
						"colc[$COLUMN_LENGTH:4000$]);"));

		Assert.assertEquals(
			"create index IX on Test (cola, colb, colc);",
			method.invoke(
				db,
				"create index IX on Test (cola, colb[$COLUMN_LENGTH:4000$], " +
					"colc[$COLUMN_LENGTH:4000$]);"));
	}

	@Test
	public void testGetLongDefaultValue() {
		Assert.assertEquals("10", db.getDefaultValue("10 "));
	}

	@Test
	public void testGetVarcharDefaultValue() {
		Assert.assertEquals("test", db.getDefaultValue("'test'"));
	}

	@Test
	public void testRewordAlterColumnType() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR);\n",
			buildSQL("alter_column_type DLFolder userName VARCHAR(75);"));
	}

	@Test
	public void testRewordAlterColumnTypeLowerCase() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR);\n",
			buildSQL("alter_column_type DLFolder userName varchar(75);"));
	}

	@Test
	public void testRewordAlterColumnTypeNoSemicolon() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR);\n",
			buildSQL("alter_column_type DLFolder userName VARCHAR(75)"));
	}

	@Test
	public void testRewordAlterColumnTypeNotNullWhenNotNull() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR);\n",
			buildSQL(
				"alter_column_type DLFolder userName VARCHAR(75) not null;"));
	}

	@Test
	public void testRewordAlterColumnTypeNotNullWhenNull() throws Exception {
		_nullable = true;

		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR) not " +
				"null;\n",
			buildSQL(
				"alter_column_type DLFolder userName VARCHAR(75) not null;"));
	}

	@Test
	public void testRewordAlterColumnTypeNullWhenNotNull() throws Exception {
		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR) null;\n",
			buildSQL("alter_column_type DLFolder userName VARCHAR(75) null;"));
	}

	@Test
	public void testRewordAlterColumnTypeNullWhenNull() throws Exception {
		_nullable = true;

		Assert.assertEquals(
			"alter table DLFolder modify userName VARCHAR2(75 CHAR);\n",
			buildSQL("alter_column_type DLFolder userName VARCHAR(75) null;"));
	}

	@Test
	public void testRewordAlterColumnTypeString() throws Exception {
		Assert.assertEquals(
			"alter table BlogsEntry modify description VARCHAR2(4000 CHAR);\n",
			buildSQL("alter_column_type BlogsEntry description STRING;"));
	}

	@Override
	protected DB getDB() {
		return new OracleDB(0, 0) {

			@Override
			protected boolean isNullable(String tableName, String columnName) {
				return _nullable;
			}

		};
	}

	private boolean _nullable;

}