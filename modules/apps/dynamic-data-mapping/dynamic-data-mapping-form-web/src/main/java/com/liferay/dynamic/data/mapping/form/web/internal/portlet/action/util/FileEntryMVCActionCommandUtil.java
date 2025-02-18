/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Igor Costa
 */
public class FileEntryMVCActionCommandUtil {

	public static void deleteFileEntry(
			long fileEntryId, ThemeDisplay themeDisplay)
		throws PortalException {

		if (fileEntryId == 0) {
			return;
		}

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(
			fileEntryId);

		if (dlFileEntry == null) {
			return;
		}

		User user = DDMFormUtil.getDDMFormDefaultUser(
			themeDisplay.getCompanyId());

		if (dlFileEntry.getUserId() == user.getUserId()) {
			DLFileEntryLocalServiceUtil.deleteFileEntry(fileEntryId);
		}
	}

}