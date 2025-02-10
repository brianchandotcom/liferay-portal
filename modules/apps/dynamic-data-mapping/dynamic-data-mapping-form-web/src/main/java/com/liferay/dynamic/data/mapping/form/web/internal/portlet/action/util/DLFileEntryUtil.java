/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.dynamic.data.mapping.util.DDMFormUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;

/**
 * @author Igor Costa
 */
public class DLFileEntryUtil {

	public static void deleteDLFileEntry(
			PortletRequest portletRequest,
			DLFileEntryLocalService dlFileEntryLocalService)
		throws PortalException {

		long fileEntryId = ParamUtil.getLong(portletRequest, "oldFileEntryId");

		if (fileEntryId == 0) {
			return;
		}

		DLFileEntry dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(
			fileEntryId);

		if (dlFileEntry == null) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = DDMFormUtil.getDDMFormDefaultUser(
			themeDisplay.getCompanyId());

		if (dlFileEntry.getUserId() == user.getUserId()) {
			dlFileEntryLocalService.deleteFileEntry(fileEntryId);
		}
	}

}