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

package com.liferay.bookmarks.internal.upgrade.v2_0_0;

import com.liferay.bookmarks.internal.upgrade.v2_0_0.util.BookmarksEntryTable;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.portal.upgrade.util.BaseUpgradeResourceBlock;

/**
 * @author Preston Crary
 */
public class UpgradeBookmarksEntryResourceBlock
	extends BaseUpgradeResourceBlock {

	@Override
	protected String getClassName() {
		return BookmarksEntry.class.getName();
	}

	@Override
	protected String getPrimaryKeyName() {
		return "entryId";
	}

	@Override
	protected Class<?> getTableClass() {
		return BookmarksEntryTable.class;
	}

	@Override
	protected boolean hasUserId() {
		return true;
	}

}