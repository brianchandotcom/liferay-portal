/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.model;

import com.liferay.petra.string.StringPool;

/**
 * @author     Alexander Chow
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             com.liferay.bookmarks.constants.BookmarksFolderConstants}
 */
@Deprecated
public class BookmarksFolderConstants {

	public static final long DEFAULT_PARENT_FOLDER_ID = 0;

	public static final String NAME_GENERAL_RESTRICTIONS = "blank";

	public static final String NAME_INVALID_CHARACTERS =
		StringPool.DOUBLE_SLASH + StringPool.SPACE +
			StringPool.DOUBLE_BACK_SLASH;

	public static final String NAME_RESERVED_WORDS = StringPool.NULL;

}