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

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.dao.db.MySQLDB;
import com.liferay.portal.dao.db.OracleDB;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * @author László Csontos
 */
@PrepareForTest({DBFactoryUtil.class, SQLTransformer.class})
@RunWith(PowerMockRunner.class)
public class SQLTransformerTest extends PowerMockito {

	@BeforeClass
	public static void init() throws Exception {
		_getInstanceMethod = SQLTransformer.class.getDeclaredMethod(
			"_getInstance");

		_getInstanceMethod.setAccessible(true);
	}

	@Test
	public void testReplaceIsEmptyCheckWithMySQL() throws Exception {
		String inputSQL = _SQL_PREFIX + _SQL_INPUT_WHERE_IE;

		String expectedSQL =
			_SQL_PREFIX +
				"(st.someField IS NOT NULL AND st.someField = '') OR " +
				"(st.someOtherField IS NOT NULL AND st.someOtherField = '')";

		testReplaceEmptyCheck(
			MySQLDB.getInstance(), "_replaceIsEmptyCheck", inputSQL,
			expectedSQL);
	}

	@Test
	public void testReplaceIsEmptyCheckWithOracle() throws Exception {
		String inputSQL = _SQL_PREFIX + _SQL_INPUT_WHERE_IE;

		String expectedSQL =
			_SQL_PREFIX +
				"st.someField IS NULL OR st.someOtherField IS NULL";

		testReplaceEmptyCheck(
			OracleDB.getInstance(), "_replaceIsEmptyCheck", inputSQL,
			expectedSQL);
	}

	@Test
	public void testReplaceIsNotEmptyCheckWithMySQL() throws Exception {
		String inputSQL = _SQL_PREFIX + _SQL_INPUT_WHERE_INE;

		String expectedSQL =
			_SQL_PREFIX +
				"(st.someField IS NOT NULL AND st.someField != '') AND " +
				"(st.someOtherField IS NOT NULL AND st.someOtherField != '')";

		testReplaceEmptyCheck(
			MySQLDB.getInstance(), "_replaceIsNotEmptyCheck", inputSQL,
			expectedSQL);
	}

	@Test
	public void testReplaceIsNotEmptyCheckWithOracle() throws Exception {
		String inputSQL = _SQL_PREFIX + _SQL_INPUT_WHERE_INE;

		String expectedSQL =
			_SQL_PREFIX +
				"st.someField IS NOT NULL AND st.someOtherField IS NOT NULL";

		testReplaceEmptyCheck(
			OracleDB.getInstance(), "_replaceIsNotEmptyCheck", inputSQL,
			expectedSQL);
	}

	protected void setDB(DB db) throws Exception {
		mockStatic(DBFactoryUtil.class);

		when(
			DBFactoryUtil.getDB()
		).thenReturn(
			db
		);

		SQLTransformer.reloadSQLTransformer();

		_sqlTransformer = (SQLTransformer) _getInstanceMethod.invoke(null);
	}

	protected void testReplaceEmptyCheck(
			DB db, String methodName, String inputSQL, String expectedSQL)
		throws Exception {

		setDB(db);

		String actualSQL = Whitebox.invokeMethod(
			_sqlTransformer, methodName, inputSQL);

		Assert.assertEquals(expectedSQL, actualSQL);
	}

	private static final String _SQL_INPUT_WHERE_IE =
		"st.someField IS EMPTY OR st.someOtherField IS EMPTY";

	private static final String _SQL_INPUT_WHERE_INE =
		"st.someField IS NOT EMPTY AND st.someOtherField IS NOT EMPTY";

	private static final String _SQL_PREFIX =
		"SELECT * FROM someTable st WHERE ";

	private static Method _getInstanceMethod;

	private SQLTransformer _sqlTransformer;

}