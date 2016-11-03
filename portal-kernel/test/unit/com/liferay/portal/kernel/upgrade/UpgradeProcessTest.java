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

import org.mockito.Mockito;

/**
 * @author Mariano Alvaro Saiz
 */
public class UpgradeProcessTest {

	@Before
	public void setUp() {
		_upgradeProcess = Mockito.mock(UpgradeProcess.class);
	}

	@Test
	public void testGetSQLTakesIntoAccountWholeColumnDescription() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			_AN_OLD_COLUMN_NAME, _A_NEW_COMPOSED_COLUMN_NAME);

		String generatedSql = alterColName.getSQL(_A_TABLE);

		Assert.assertTrue(generatedSql.contains(_A_NEW_COMPOSED_COLUMN_NAME));
	}

	@Test
	public void testShouldAddIndexIfColumnFromIndexRenamed() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			_AN_OLD_COLUMN_NAME, _A_NEW_COLUMN_NAME);

		Assert.assertTrue(alterColName.shouldAddIndex(_aNewIndexColumns));
	}

	@Test
	public void testShouldAddIndexIfColumnWithAttributesFromIndexRenamed() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			_AN_OLD_COLUMN_NAME, _A_NEW_COMPOSED_COLUMN_NAME);

		Assert.assertTrue(alterColName.shouldAddIndex(_aNewIndexColumns));
	}

	@Test
	public void testShouldAddIndexIsCaseInsensitive() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			_AN_OLD_COLUMN_NAME, StringUtil.toUpperCase(_A_NEW_COLUMN_NAME));

		Assert.assertTrue(
			alterColName.shouldAddIndex(_aNewIndexWithLowercaseColumns));
	}

	@Test
	public void testShouldDropIndexIfColumnFromIndexRenamed() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			_AN_OLD_INDEX_COLUMN_NAME, _A_NEW_COLUMN_NAME);

		Assert.assertTrue(alterColName.shouldDropIndex(_oldIndexColumns));
	}

	@Test
	public void testShouldDropIndexIsCaseInsensitive() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			_AN_OLD_INDEX_COLUMN_NAME,
			StringUtil.toUpperCase(_A_NEW_COLUMN_NAME));

		Assert.assertTrue(
			alterColName.shouldDropIndex(_anOldIndexColumnsLowercase));
	}

	@Test
	public void testShouldntAddIndexIfColumnNotInIndexRenamed() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			_AN_OLD_COLUMN_NAME, _A_SIMPLE_NEW_COLUMN_NAME_NOT_IN_INDEX);

		Assert.assertFalse(alterColName.shouldAddIndex(_aNewIndexColumns));
	}

	@Test
	public void testShouldntDropIndexIfColumnNotInIndexRenamed() {
		AlterColumnName alterColName = _upgradeProcess.new AlterColumnName(
			_AN_OLD_COLUMN_NAME_NOT_IN_INDEX, _A_NEW_COLUMN_NAME);

		Assert.assertFalse(alterColName.shouldDropIndex(_oldIndexColumns));
	}

	private static final String _A_NEW_COLUMN_NAME = "newColumnName";

	private static final String _A_NEW_COMPOSED_COLUMN_NAME =
		_A_NEW_COLUMN_NAME + " VARCHAR2(30) NOT NULL";

	private static final String _A_SIMPLE_NEW_COLUMN_NAME_NOT_IN_INDEX =
		"newColumn";

	private static final String _A_TABLE = "table";

	private static final String _AN_OLD_COLUMN_NAME = "oldColumnName";

	private static final String _AN_OLD_COLUMN_NAME_NOT_IN_INDEX =
		"oldNotIndexColumn";

	private static final String _AN_OLD_INDEX_COLUMN_NAME = "oldIndexColumn";

	private static final List<String> _aNewIndexColumns = Arrays.asList(
		"newColumn1", _A_NEW_COLUMN_NAME, "newColumn2");
	private static final List<String> _aNewIndexWithLowercaseColumns =
		Arrays.asList(StringUtil.toLowerCase(_A_NEW_COLUMN_NAME));
	private static final List<String> _anOldIndexColumnsLowercase =
		Arrays.asList(StringUtil.toLowerCase(_AN_OLD_INDEX_COLUMN_NAME));
	private static final List<String> _oldIndexColumns = Arrays.asList(
		"oldColumn1", _AN_OLD_INDEX_COLUMN_NAME, "oldColumn3");

	private UpgradeProcess _upgradeProcess;

}