/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.depot.web.internal.constants.DepotScreenNavigationEntryConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "screen.navigation.entry.order:Integer=2",
	service = ScreenNavigationEntry.class
)
public class SitesScreenNavigationEntry extends BaseDepotScreenNavigationEntry {

	@Override
	public String getActionCommandName() {
		return "/depot/connect_depot_entry";
	}

	@Override
	public String getEntryKey() {
		return DepotScreenNavigationEntryConstants.ENTRY_KEY_SITES;
	}

	@Override
	public String getJspPath() {
		return "/screen/navigation/entries/sites.jsp";
	}

	@Override
	protected String getDescription(Locale locale) {
		return _language.get(
			getResourceBundle(locale),
			"an-asset-library-can-be-connected-to-multiple-sites.-please-add-" +
				"those-sites-here");
	}

	@Override
	protected boolean isShowControls() {
		return false;
	}

	@Reference
	private Language _language;

}