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

package com.liferay.journal.model;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author     Juan Fernández
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             com.liferay.journal.constants.JournalFolderConstants}
 */
@Deprecated
public class JournalFolderConstants {

	public static final long DEFAULT_PARENT_FOLDER_ID = 0;

	public static final String NAME_GENERAL_RESTRICTIONS = "blank";

	public static final String NAME_RESERVED_WORDS = StringPool.NULL;

	public static final int RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW = 1;

	public static final int RESTRICTION_TYPE_INHERIT = 0;

	public static final int RESTRICTION_TYPE_WORKFLOW = 2;

	public static String getNameInvalidCharacters(String[] charBlacklist) {
		return StringUtil.merge(charBlacklist, StringPool.SPACE);
	}

}