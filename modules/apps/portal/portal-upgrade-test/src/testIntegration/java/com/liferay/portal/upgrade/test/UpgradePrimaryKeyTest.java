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

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradePrimaryKey;

import java.sql.SQLException;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class UpgradePrimaryKeyTest extends BaseUpgradePrimaryKeyTestCase {

	@Test
	public void testAddExistingNotNullColumnAsPrimaryKey() throws Exception {
		UpgradePrimaryKey upgradePrimaryKey = new UpgradePrimaryKey(
			"notNullColumn LONG not null", getTableName());

		upgradePrimaryKey.upgrade();

		_validatePrimaryKeys("notNullColumn");
	}

	@Test
	public void testAddExistingNullableColumnAsPrimaryKey() throws Exception {
		UpgradePrimaryKey upgradePrimaryKey = new UpgradePrimaryKey(
			"nullableColumn LONG null", getTableName());

		upgradePrimaryKey.upgrade();

		_validatePrimaryKeys("nullableColumn");
	}

	@Test
	public void testAddMultipleColumnsAsPrimaryKey() throws Exception {
		int numPrimaryKeys = RandomTestUtil.randomInt(2, 6);

		String[] primaryKeyColumnNames = new String[numPrimaryKeys];

		for (int i = 0; i < numPrimaryKeys; i++) {
			primaryKeyColumnNames[i] = "columnName" + i;

			UpgradePrimaryKey upgradePrimaryKey = new UpgradePrimaryKey(
				primaryKeyColumnNames[i] + " LONG null", getTableName());

			upgradePrimaryKey.upgrade();
		}

		_validatePrimaryKeys(primaryKeyColumnNames);
	}

	@Test
	public void testAddNumericColumnAsPrimaryKey() throws Exception {
		UpgradePrimaryKey upgradePrimaryKey = new UpgradePrimaryKey(
			"longColumn LONG not null", getTableName());

		upgradePrimaryKey.upgrade();

		_validatePrimaryKeys("longColumn");
	}

	@Test
	public void testAddStringColumnAsPrimaryKey() throws Exception {
		UpgradePrimaryKey upgradePrimaryKey = new UpgradePrimaryKey(
			"stringColumn STRING not null", getTableName());

		upgradePrimaryKey.upgrade();

		_validatePrimaryKeys("stringColumn");
	}

	@Override
	protected String getSqlCreateTable() {
		return StringBundler.concat(
			"create table ", getTableName(), " (id LONG not null primary key, ",
			"notNullColumn LONG not null, nullableColumn LONG null)");
	}

	@Override
	protected String getTableName() {
		return "UpgradePrimaryKeyTest";
	}

	private void _validatePrimaryKeys(String... newPrimaryKeyColumnNames)
		throws SQLException {

		List<String> primaryKeyColumnNames = getPrimaryKeyColumnNames();

		for (String newPrimaryKeyColumnName : newPrimaryKeyColumnNames) {
			if (!primaryKeyColumnNames.contains(
					dbInspector.normalizeName(newPrimaryKeyColumnName))) {

				Assert.fail(newPrimaryKeyColumnName + " must be primary key");
			}
		}
	}

}