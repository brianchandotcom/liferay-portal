/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.util.comparator;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class EntryModifiedDateComparator
	extends StagedModelModifiedDateComparator<BookmarksEntry> {

	public EntryModifiedDateComparator() {
		this(false);
	}

	public EntryModifiedDateComparator(boolean ascending) {
		super(ascending);
	}

}