/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static final int CANNOT_COPY_INTO_CHILD_FOLDER = 1;

	public static final int CANNOT_COPY_INTO_ITSELF = 2;

	public static final int CANNOT_MOVE_INTO_CHILD_FOLDER = 3;

	public static final int CANNOT_MOVE_INTO_ITSELF = 4;

	public static final int INVALID_ROOT_FOLDER = 5;

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
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			return StringPool.BLANK;
		}
	}

	public String getMessageKey() {
		if (_type == CANNOT_COPY_INTO_CHILD_FOLDER) {
			return "unable-to-copy-folder-x-into-one-of-its-children";
		}
		else if (_type == CANNOT_COPY_INTO_ITSELF) {
			return "unable-to-copy-folder-x-into-itself";
		}
		else if (_type == CANNOT_MOVE_INTO_CHILD_FOLDER) {
			return "unable-to-move-folder-x-into-one-of-its-children";
		}
		else if (_type == CANNOT_MOVE_INTO_ITSELF) {
			return "unable-to-move-folder-x-into-itself";
		}
		else if (_type == INVALID_ROOT_FOLDER) {
			return "invalid-root-folder-x";
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InvalidFolderException.class);

	private final long _folderId;
	private final int _type;

}