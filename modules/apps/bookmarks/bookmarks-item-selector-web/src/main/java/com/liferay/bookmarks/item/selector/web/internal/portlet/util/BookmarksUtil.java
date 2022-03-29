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

package com.liferay.bookmarks.item.selector.web.internal.portlet.util;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.util.comparator.EntryCreateDateComparator;
import com.liferay.bookmarks.util.comparator.EntryModifiedDateComparator;
import com.liferay.bookmarks.util.comparator.EntryNameComparator;
import com.liferay.bookmarks.util.comparator.EntryPriorityComparator;
import com.liferay.bookmarks.util.comparator.EntryURLComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Lu Liu
 */
public class BookmarksUtil {

	public static OrderByComparator<BookmarksEntry> getEntryOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<BookmarksEntry> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new EntryCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new EntryModifiedDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new EntryNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("priority")) {
			orderByComparator = new EntryPriorityComparator(orderByAsc);
		}
		else if (orderByCol.equals("url")) {
			orderByComparator = new EntryURLComparator(orderByAsc);
		}

		return orderByComparator;
	}

}