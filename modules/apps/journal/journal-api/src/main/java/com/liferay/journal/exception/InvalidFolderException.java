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

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jonathan McCann
 */
@ProviderType
public class InvalidFolderException extends PortalException {

	public static final int CANNOT_MOVE_INTO_CHILD_FOLDER = 1;

	public static final int CANNOT_MOVE_INTO_ITSELF = 2;

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

		return null;
	}

	private final JournalFolder _folder;
	private final int _type;

}