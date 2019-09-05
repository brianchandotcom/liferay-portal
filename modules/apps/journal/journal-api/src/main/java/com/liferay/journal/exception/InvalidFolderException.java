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

package com.liferay.journal.exception;

import com.liferay.journal.model.JournalFolder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Jonathan McCann
 */
public class InvalidFolderException extends PortalException {

	public static final int CANNOT_MOVE_INTO_CHILD_FOLDER = 1;

	public static final int CANNOT_MOVE_INTO_ITSELF = 2;

	public static final int INVALID_GROUP = 3;

	public static final int PARENT_FOLDER_DOES_NOT_EXIST = 4;

	public InvalidFolderException(int type) {
		_type = type;

		_folder = null;
	}

	public InvalidFolderException(JournalFolder folder, int type) {
		_folder = folder;
		_type = type;
	}

	public JournalFolder getFolder() {
		return _folder;
	}

	public String getMessageArgument(Locale locale) {
		if (_folder == null) {
			return LanguageUtil.get(locale, "home");
		}

		return _folder.getName();
	}

	public String getMessageKey() {
		if (_type == CANNOT_MOVE_INTO_CHILD_FOLDER) {
			return "unable-to-move-folder-x-into-one-of-its-children";
		}
		else if (_type == CANNOT_MOVE_INTO_ITSELF) {
			return "unable-to-move-folder-x-into-itself";
		}
		else if (_type == INVALID_GROUP) {
			return "folder-cannot-be-moved-to-another-site";
		}
		else if (_type == PARENT_FOLDER_DOES_NOT_EXIST) {
			return "parent-folder-does-not-exist";
		}

		return null;
	}

	private final JournalFolder _folder;
	private final int _type;

}