/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.index;

import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.tools.DBUpgrader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class IndexStatusManagerImplRequireIndexReadWriteTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_dbUpgraderMockedStatic = Mockito.mockStatic(DBUpgrader.class);
		_startupHelperUtilMockedStatic = Mockito.mockStatic(
			StartupHelperUtil.class);
	}

	@After
	public void tearDown() {
		_dbUpgraderMockedStatic.close();
		_startupHelperUtilMockedStatic.close();
	}

	@Test
	public void testBookendsLikeSetupAndTeardown() {
		indexStatusManagerImpl.requireIndexReadWrite(true);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.setIndexReadOnly(false);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.requireIndexReadWrite(false);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());
	}

	@Test
	public void testReadOnlySetAfterBookends() {
		indexStatusManagerImpl.requireIndexReadWrite(true);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.requireIndexReadWrite(false);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.setIndexReadOnly(true);

		Assert.assertTrue(indexStatusManagerImpl.isIndexReadOnly());
	}

	@Test
	public void testReadOnlySetBeforeBookends() {
		indexStatusManagerImpl.setIndexReadOnly(true);

		Assert.assertTrue(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.requireIndexReadWrite(true);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.requireIndexReadWrite(false);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());
	}

	@Test
	public void testReadOnlySetBetweenBookends() {
		indexStatusManagerImpl.requireIndexReadWrite(true);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.setIndexReadOnly(true);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.requireIndexReadWrite(false);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());
	}

	@Test
	public void testReadOnlyWhenIsUpgradeClient() {
		_dbUpgraderMockedStatic.when(
			DBUpgrader::isUpgradeClient
		).thenReturn(
			true
		);

		_startupHelperUtilMockedStatic.when(
			StartupHelperUtil::isUpgrading
		).thenReturn(
			false
		);

		indexStatusManagerImpl.setIndexReadOnly(false);

		Assert.assertTrue(indexStatusManagerImpl.isIndexReadOnly());

		_dbUpgraderMockedStatic.when(
			DBUpgrader::isUpgradeClient
		).thenReturn(
			false
		);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());
	}

	@Test
	public void testReadOnlyWhenIsUpgrading() {
		_dbUpgraderMockedStatic.when(
			DBUpgrader::isUpgradeClient
		).thenReturn(
			false
		);

		_startupHelperUtilMockedStatic.when(
			StartupHelperUtil::isUpgrading
		).thenReturn(
			true
		);

		indexStatusManagerImpl.setIndexReadOnly(false);

		Assert.assertTrue(indexStatusManagerImpl.isIndexReadOnly());

		_startupHelperUtilMockedStatic.when(
			StartupHelperUtil::isUpgrading
		).thenReturn(
			false
		);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	protected IndexStatusManagerImpl indexStatusManagerImpl =
		new IndexStatusManagerImpl();

	private MockedStatic<DBUpgrader> _dbUpgraderMockedStatic;
	private MockedStatic<StartupHelperUtil> _startupHelperUtilMockedStatic;

}