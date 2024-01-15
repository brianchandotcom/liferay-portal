/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.layout.admin.constants.LayoutScreenNavigationEntryConstants;
import com.liferay.layout.constants.LayoutTypeSettingsConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sandro Chinea
 */
@Component(service = ScreenNavigationEntry.class)
public class LayoutDesignScreenNavigationEntry
	extends BaseLayoutScreenNavigationEntry {

	@Override
	public String getEntryKey() {
		return LayoutScreenNavigationEntryConstants.ENTRY_KEY_DESIGN;
	}

	@Override
	public String getStatusLabel(Locale locale, Layout layout) {
		Layout draftLayout = layout;

		if (!layout.isDraftLayout()) {
			draftLayout = layout.fetchDraftLayout();
		}

		if (draftLayout == null) {
			return null;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			draftLayout.getTypeSettingsProperties();

		if (GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.getProperty(
					LayoutTypeSettingsConstants.
						KEY_DESIGN_CONFIGURATION_MODIFIED))) {

			return language.get(locale, "draft");
		}

		return null;
	}

	@Override
	protected String getJspPath() {
		return "/layout/screen/navigation/entries/design.jsp";
	}

}