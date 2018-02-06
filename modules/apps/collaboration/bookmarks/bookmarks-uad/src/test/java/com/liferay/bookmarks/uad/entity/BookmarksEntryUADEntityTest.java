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
import com.liferay.bookmarks.uad.constants.BookmarksUADConstants;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.test.util.BaseUADEntityTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Noah Sherrill
 */
public class BookmarksEntryUADEntityTest extends BaseUADEntityTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		super.setUp();
	}

	@Test
	public void testGetBookmarksEntry() throws Exception {
		BookmarksEntryUADEntity bookmarksEntryUADEntity =
			(BookmarksEntryUADEntity)uadEntity;

		Assert.assertEquals(
			_bookmarksEntry, bookmarksEntryUADEntity.getBookmarksEntry());
	}

	@Override
	protected UADEntity createUADEntity(long userId, String uadEntityId) {
		return new BookmarksEntryUADEntity(
			userId, uadEntityId, _bookmarksEntry);
	}

	@Override
	protected String getUADRegistryKey() {
		return BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY;
	}

	@Mock
	private BookmarksEntry _bookmarksEntry;

}