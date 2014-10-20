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

package com.liferay.bookmarks.service;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.util.BookmarksTestUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class BookmarksEntryServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddEntry() throws Exception {
		BookmarksTestUtil.addEntry(_group.getGroupId(), true);
	}

	@Test
	public void testDeleteEntry() throws Exception {
		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			_group.getGroupId(), true);

		BookmarksEntryServiceUtil.deleteEntry(entry.getEntryId());
	}

	@Test
	public void testGetEntry() throws Exception {
		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			_group.getGroupId(), true);

		BookmarksEntryServiceUtil.getEntry(entry.getEntryId());
	}

	@DeleteAfterTestRun
	private Group _group;

}