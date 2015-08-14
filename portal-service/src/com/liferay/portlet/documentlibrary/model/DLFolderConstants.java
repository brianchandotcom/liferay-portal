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

package com.liferay.portlet.documentlibrary.model;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeFormatter;

/**
 * <p>
 * This contains several utility methods for the purpose of determining folder
 * IDs and data repository IDs as used by back-end data systems like search and
 * Document Library stores. These repository IDs should not be confused with the
 * repository ID used by {@link
 * com.liferay.portal.service.impl.RepositoryServiceImpl}.
 * </p>
 *
 * @author Samuel Kong
 * @author Alexander Chow
 */
public class DLFolderConstants {

	public static final long DEFAULT_PARENT_FOLDER_ID = 0;

	public static final String NAME_GENERAL_RESTRICTIONS = "blank";

	public static final String NAME_LABEL = "folder-name";

	public static final int RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW = 1;

	public static final int RESTRICTION_TYPE_INHERIT = 0;

	public static final int RESTRICTION_TYPE_WORKFLOW = 2;

	public static String getClassName() {
		return DLFolder.class.getName();
	}

	/**
	 * Determine the data repository ID from the repository ID and folder ID.
	 * The folder ID may be zero, implying that it is the root folder for the
	 * given repository.
	 */
	public static long getDataRepositoryId(long repositoryId, long folderId) {
		if (folderId != DEFAULT_PARENT_FOLDER_ID) {
			return folderId;
		}
		else {
			return repositoryId;
		}
	}

	/**
	 * Determine the folder ID when no knowledge of it currently exists.
	 */
	public static long getFolderId(long groupId, long dataRepositoryId) {
		if (groupId != dataRepositoryId) {
			return dataRepositoryId;
		}
		else {
			return DEFAULT_PARENT_FOLDER_ID;
		}
	}

	public static final String getNameInvalidCharacters(
		String[] documentLibraryCharBlacklist) {

		return StringUtil.merge(documentLibraryCharBlacklist, StringPool.SPACE);
	}

	public static final String getNameInvalidEndCharacters(
		String[] documentLibraryCharLastBlacklist) {

		String folderNameInvalidEndCharacters = StringPool.BLANK;

		for (String blacklistLastChar : documentLibraryCharLastBlacklist) {
			if (blacklistLastChar.startsWith("\\u")) {
				blacklistLastChar = UnicodeFormatter.parseString(
					blacklistLastChar);
			}

			folderNameInvalidEndCharacters += blacklistLastChar +
				StringPool.SPACE;
		}

		return StringUtil.trimTrailing(folderNameInvalidEndCharacters);
	}

	public static final String getNameReservedWords(
		String[] documentLibraryNameBlacklist) {

		return StringPool.NULL + StringPool.COMMA_AND_SPACE + StringUtil.merge(
			documentLibraryNameBlacklist, StringPool.COMMA_AND_SPACE);
	}

}