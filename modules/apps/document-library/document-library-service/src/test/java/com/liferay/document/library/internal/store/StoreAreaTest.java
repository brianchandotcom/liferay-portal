/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.store;

import com.liferay.document.library.kernel.store.StoreArea;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class StoreAreaTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetPath() throws Exception {
		Assert.assertEquals("1/", StoreArea.LIVE.getPath(1));
		Assert.assertEquals("1/2", StoreArea.LIVE.getPath(1, 2));
		Assert.assertEquals("1/2/a", StoreArea.LIVE.getPath(1, 2, "a"));
		Assert.assertEquals("1/2/a/b", StoreArea.LIVE.getPath(1, 2, "a", "b"));
		Assert.assertEquals(
			"1/2/a/b", StoreArea.LIVE.getPath(1, 2, "a/", "/b"));
		Assert.assertEquals("1/2/a/b", StoreArea.LIVE.getPath(1, 2, "a/", "b"));
		Assert.assertEquals(
			"1/2/a/b/c", StoreArea.LIVE.getPath(1, 2, "a/", "/b/", "/c"));
		Assert.assertEquals("_deleted/1/", StoreArea.DELETED.getPath(1));
		Assert.assertEquals(
			"_deleted/1/2/a/b", StoreArea.DELETED.getPath(1, 2, "a/", "/b"));
	}

	@Test
	public void testRelocate() {
		_testRelocate(StoreArea.DELETED);
		_testRelocate(StoreArea.LIVE);
		_testRelocate(StoreArea.NEW);
	}

	private void _testRelocate(StoreArea storeArea) {
		String path = storeArea.getPath(1, 2, "test");

		Assert.assertEquals(
			"_deleted/1/2/test", storeArea.relocate(path, StoreArea.DELETED));
		Assert.assertEquals(
			"1/2/test", storeArea.relocate(path, StoreArea.LIVE));
		Assert.assertEquals(
			"_new/1/2/test", storeArea.relocate(path, StoreArea.NEW));
	}

}