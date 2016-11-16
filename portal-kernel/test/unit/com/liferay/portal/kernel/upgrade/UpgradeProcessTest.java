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

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess.AlterColumnName;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mariano Alvaro Saiz
 */
public class UpgradeProcessTest {

	@Before
	public void setUp() {
		_upgradeProcess = new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
			}

		};
	}

	@Test
	public void testGetSQLUsesNewColumn() {
		AlterColumnName alterColumnName = _upgradeProcess.new AlterColumnName(
			_OLD_COLUMN_NAME, _NEW_COLUMN);

		String sql = alterColumnName.getSQL(_TABLE_NAME);

		Assert.assertTrue(sql.contains(_NEW_COLUMN));
	}

	@Test
	public void testShouldAddIndexIsCaseInsensitive() {
		AlterColumnName alterColumnName = _upgradeProcess.new AlterColumnName(
			_OLD_COLUMN_NAME, StringUtil.toUpperCase(_NEW_COLUMN_NAME));

		Assert.assertTrue(
			alterColumnName.shouldAddIndex(_newIndexColumnsNamesLowerCase));
	}

	@Test
	public void testShouldAddIndexIsFalseIfNewColumnNameIsNotInNewIndex() {
		AlterColumnName alterColumnName = _upgradeProcess.new AlterColumnName(
			_OLD_COLUMN_NAME, _NEW_COLUMN_NAME_NOT_IN_INDEX);

		Assert.assertFalse(
			alterColumnName.shouldAddIndex(_newIndexColumnNames));
	}

	@Test
	public void testShouldAddIndexIsTrueIfNewColumNameIsInNewIndex() {
		AlterColumnName alterColumnName = _upgradeProcess.new AlterColumnName(
			_OLD_COLUMN_NAME, _NEW_COLUMN_NAME);

		Assert.assertTrue(alterColumnName.shouldAddIndex(_newIndexColumnNames));
	}

	@Test
	public void testShouldAddIndexIsTrueIfNewColumnIsInNewIndex() {
		AlterColumnName alterColumnName = _upgradeProcess.new AlterColumnName(
			_OLD_COLUMN_NAME, _NEW_COLUMN);

		Assert.assertTrue(alterColumnName.shouldAddIndex(_newIndexColumnNames));
	}

	@Test
	public void testShouldDropIndexIsCaseInsensitive() {
		AlterColumnName alterColumnName = _upgradeProcess.new AlterColumnName(
			StringUtil.toUpperCase(_OLD_COLUMN_NAME), _NEW_COLUMN_NAME);

		Assert.assertTrue(
			alterColumnName.shouldDropIndex(_oldIndexColumnsNamesLowercase));
	}

	@Test
	public void testShouldDropIndexIsFalseIfOldColumnIsNotInOldIndex() {
		AlterColumnName alterColumnName = _upgradeProcess.new AlterColumnName(
			_OLD_COLUMN_NAME_NOT_IN_INDEX, _NEW_COLUMN_NAME);

		Assert.assertFalse(
			alterColumnName.shouldDropIndex(_oldIndexColumnNames));
	}

	@Test
	public void testShouldDropIndexIsTrueIfOldColumnIsInOldIndex() {
		AlterColumnName alterColumnName = _upgradeProcess.new AlterColumnName(
			_OLD_COLUMN_NAME, _NEW_COLUMN_NAME);

		Assert.assertTrue(
			alterColumnName.shouldDropIndex(_oldIndexColumnNames));
	}

	private static final String _NEW_COLUMN =
		UpgradeProcessTest._NEW_COLUMN_NAME + " VARCHAR2(30) NOT NULL";

	private static final String _NEW_COLUMN_NAME = "newColumnName";

	private static final String _NEW_COLUMN_NAME_NOT_IN_INDEX =
		"newNotIndexColumn";

	private static final String _OLD_COLUMN_NAME = "oldColumnName";

	private static final String _OLD_COLUMN_NAME_NOT_IN_INDEX =
		"oldNotIndexColumn";

	private static final String _TABLE_NAME = "Table";

	private static final List<String> _newIndexColumnNames = Arrays.asList(
		"newColumn1", _NEW_COLUMN_NAME, "newColumn2");
	private static final List<String> _newIndexColumnsNamesLowerCase =
		Arrays.asList(StringUtil.toLowerCase(_NEW_COLUMN_NAME));
	private static final List<String> _oldIndexColumnNames = Arrays.asList(
		"oldColumn1", _OLD_COLUMN_NAME, "oldColumn3");
	private static final List<String> _oldIndexColumnsNamesLowercase =
		Arrays.asList(StringUtil.toLowerCase(_OLD_COLUMN_NAME));

	private UpgradeProcess _upgradeProcess;

}