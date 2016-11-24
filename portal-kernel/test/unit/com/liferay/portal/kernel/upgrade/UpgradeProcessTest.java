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
	public void testGetSQLTakesIntoAccountWholeColumnDescription() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			_OLD_COLUMN_NAME, _NEW_COMPOSED_COLUMN_NAME);

		String generatedSql = alterColName.getSQL(_TABLE_NAME);

		Assert.assertTrue(generatedSql.contains(_NEW_COMPOSED_COLUMN_NAME));
	}

	@Test
	public void testShouldAddIndexIfColumnFromIndexRenamed() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			_OLD_COLUMN_NAME, _NEW_COLUMN_NAME);

		Assert.assertTrue(alterColName.shouldAddIndex(_newIndexColumns));
	}

	@Test
	public void testShouldAddIndexIfColumnFromIndexRenamedWithAttributes() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			_OLD_COLUMN_NAME, _NEW_COMPOSED_COLUMN_NAME);

		Assert.assertTrue(alterColName.shouldAddIndex(_newIndexColumns));
	}

	@Test
	public void testShouldAddIndexIsCaseInsensitive() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			_OLD_COLUMN_NAME, StringUtil.toUpperCase(_NEW_COLUMN_NAME));

		Assert.assertTrue(
			alterColName.shouldAddIndex(_newIndexColumnsLowerCase));
	}

	@Test
	public void testShouldDropIndexIfColumnFromIndexRenamed() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			_OLD_COLUMN_NAME, _NEW_COLUMN_NAME);

		Assert.assertTrue(alterColName.shouldDropIndex(_oldIndexColumns));
	}

	@Test
	public void testShouldDropIndexIsCaseInsensitive() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			StringUtil.toUpperCase(_OLD_COLUMN_NAME), _NEW_COLUMN_NAME);

		Assert.assertTrue(
			alterColName.shouldDropIndex(_oldIndexColumnsLowercase));
	}

	@Test
	public void testShouldNotAddIndexIfColumnNotInIndexRenamed() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			_OLD_COLUMN_NAME, _NEW_COLUMN_NAME_NOT_IN_INDEX);

		Assert.assertFalse(alterColName.shouldAddIndex(_newIndexColumns));
	}

	@Test
	public void testShouldNotDropIndexIfColumnNotInIndexRenamed() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			_OLD_COLUMN_NAME_NOT_IN_INDEX, _NEW_COLUMN_NAME);

		Assert.assertFalse(alterColName.shouldDropIndex(_oldIndexColumns));
	}

	private static final String _NEW_COLUMN_NAME = "newColumnName";

	private static final String _NEW_COLUMN_NAME_NOT_IN_INDEX =
		"newNotIndexColumn";

	private static final String _NEW_COMPOSED_COLUMN_NAME =
		_NEW_COLUMN_NAME + " VARCHAR2(30) NOT NULL";

	private static final String _OLD_COLUMN_NAME = "oldColumnName";

	private static final String _OLD_COLUMN_NAME_NOT_IN_INDEX =
		"oldNotIndexColumn";

	private static final String _TABLE_NAME = "table";

	private static final List<String> _newIndexColumns = Arrays.asList(
		"newColumn1", _NEW_COLUMN_NAME, "newColumn2");
	private static final List<String> _newIndexColumnsLowerCase = Arrays.asList(
		StringUtil.toLowerCase(_NEW_COLUMN_NAME));
	private static final List<String> _oldIndexColumns = Arrays.asList(
		"oldColumn1", _OLD_COLUMN_NAME, "oldColumn3");
	private static final List<String> _oldIndexColumnsLowercase = Arrays.asList(
		StringUtil.toLowerCase(_OLD_COLUMN_NAME));

	private UpgradeProcess _upgradeProcess;

}