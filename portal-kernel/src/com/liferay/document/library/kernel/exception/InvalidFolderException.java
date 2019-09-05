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

package com.liferay.document.library.kernel.exception;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class InvalidFolderException extends PortalException {

	public static final int CANNOT_MOVE_INTO_CHILD_FOLDER = 1;

	public static final int CANNOT_MOVE_INTO_ITSELF = 2;

	public InvalidFolderException(int type, long folderId) {
		_type = type;
		_folderId = folderId;
	}

	public long getFolderId() {
		return _folderId;
	}

	public String getMessageArgument(Locale locale) {
		try {
			if (_folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				return LanguageUtil.get(locale, "home");
			}

			Folder folder = DLAppLocalServiceUtil.getFolder(_folderId);

			return folder.getName();
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			return StringPool.BLANK;
		}
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

	private static final Log _log = LogFactoryUtil.getLog(
		InvalidFolderException.class);

	private final long _folderId;
	private final int _type;

}