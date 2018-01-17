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

package com.liferay.bookmarks.uad.entity;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Noah Sherrill
 */
public class BookmarksEntryUADEntityTest {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_bookmarksEntryUADEntity = new BookmarksEntryUADEntity(
			_USER_ID, _UAD_ENTITY_ID, _bookmarksEntry);
	}

	@Test
	public void testGetBookmarksEntry() throws Exception {
		Assert.assertEquals(
			_bookmarksEntry, _bookmarksEntryUADEntity.getBookmarksEntry());
	}

	@Test
	public void testGetUADEntityId() throws Exception {
		Assert.assertEquals(
			_UAD_ENTITY_ID, _bookmarksEntryUADEntity.getUADEntityId());
	}

	@Test
	public void testGetUADRegistryKey() throws Exception {
		Assert.assertEquals(
			BookmarksEntry.class.getName(),
			_bookmarksEntryUADEntity.getUADRegistryKey());
	}

	@Test
	public void testGetUserId() throws Exception {
		Assert.assertEquals(_USER_ID, _bookmarksEntryUADEntity.getUserId());
	}

	private static final String _UAD_ENTITY_ID = RandomTestUtil.randomString();

	private static final long _USER_ID = RandomTestUtil.randomLong();

	@Mock
	private BookmarksEntry _bookmarksEntry;

	private BookmarksEntryUADEntity _bookmarksEntryUADEntity;

}