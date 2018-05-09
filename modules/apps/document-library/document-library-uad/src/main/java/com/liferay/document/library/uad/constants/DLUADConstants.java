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

package com.liferay.document.library.uad.constants;

/**
 * @author William Newbury
 */
public class DLUADConstants {

	public static final String APPLICATION_NAME = "DL";

	public static final String CLASS_NAME_DL_FILE_ENTRY =
		"com.liferay.document.library.kernel.model.DLFileEntry";

	public static final String CLASS_NAME_DL_FILE_ENTRY_TYPE =
		"com.liferay.document.library.kernel.model.DLFileEntryType";

	public static final String CLASS_NAME_DL_FILE_SHORTCUT =
		"com.liferay.document.library.kernel.model.DLFileShortcut";

	public static final String CLASS_NAME_DL_FILE_VERSION =
		"com.liferay.document.library.kernel.model.DLFileVersion";

	public static final String CLASS_NAME_DL_FOLDER =
		"com.liferay.document.library.kernel.model.DLFolder";

	public static final String[] USER_ID_FIELD_NAMES_DL_FILE_ENTRY = {"userId"};

	public static final String[] USER_ID_FIELD_NAMES_DL_FILE_ENTRY_TYPE =
		{"userId"};

	public static final String[] USER_ID_FIELD_NAMES_DL_FILE_SHORTCUT =
		{"userId", "statusByUserId"};

	public static final String[] USER_ID_FIELD_NAMES_DL_FILE_VERSION =
		{"userId", "statusByUserId"};

	public static final String[] USER_ID_FIELD_NAMES_DL_FOLDER =
		{"userId", "statusByUserId"};

}