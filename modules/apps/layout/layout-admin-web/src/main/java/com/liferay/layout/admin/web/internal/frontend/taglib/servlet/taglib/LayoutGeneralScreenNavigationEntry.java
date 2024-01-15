/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.layout.admin.constants.LayoutScreenNavigationEntryConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = ScreenNavigationEntry.class)
public class LayoutGeneralScreenNavigationEntry
	extends BaseLayoutScreenNavigationEntry {

	@Override
	public String getEntryKey() {
		return LayoutScreenNavigationEntryConstants.ENTRY_KEY_GENERAL;
	}

	@Override
	protected String getJspPath() {
		return "/layout/screen/navigation/entries/general.jsp";
	}

}