/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Ziemer
 */
public class ContentSectionDisplayContext {

	public String getAPIURL() {
		StringBundler sb = new StringBundler(3);

		sb.append("/o/search/v1.0/search?emptySearch=true");
		sb.append("&nestedFields=embedded&entryClassNames=");

		sb.append(ArrayUtil.toString(_CONTENT_CLASS_NAMES, StringPool.BLANK));

		return sb.toString();
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return new ArrayList<>();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return new ArrayList<>();
	}

	private static final String[] _CONTENT_CLASS_NAMES = {
		"com.liferay.blogs.model.BlogsEntry",
		"com.liferay.bookmarks.model.BookmarksEntry",
		"com.liferay.bookmarks.model.BookmarksFolder",
		"com.liferay.document.library.kernel.model.DLFileShortcut",
		"com.liferay.document.library.kernel.model.DLFolder",
		"com.liferay.dynamic.data.mapping.model.DDMFormInstance",
		"com.liferay.journal.model.JournalArticle",
		"com.liferay.journal.model.JournalFolder",
		"com.liferay.knowledge.base.model.KBArticle",
		"com.liferay.knowledge.base.model.KBFolder",
		"com.liferay.message.boards.model.MBCategory",
		"com.liferay.message.boards.model.MBThread"
	};

}