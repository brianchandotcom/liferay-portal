/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.service;

import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.service.BookmarksEntryServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksEntryServiceTest extends BaseBookmarksServiceTestCase {

	public void testAddEntry() throws Exception {
		addEntry();
	}

	public void testDeleteEntry() throws Exception {
		BookmarksEntry entry = addEntry();

		BookmarksEntryServiceUtil.deleteEntry(entry.getEntryId());
	}

	public void testGetEntry() throws Exception {
		BookmarksEntry entry = addEntry();

		BookmarksEntryServiceUtil.getEntry(entry.getEntryId());
	}

}