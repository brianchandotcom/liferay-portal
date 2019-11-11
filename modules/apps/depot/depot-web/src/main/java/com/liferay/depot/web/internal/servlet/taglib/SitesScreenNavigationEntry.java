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

package com.liferay.depot.web.internal.servlet.taglib;

import com.liferay.depot.web.internal.constants.DepotScreenNavigationEntryConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

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
		return "/depot_entry/connect";
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
		return LanguageUtil.get(
			getResourceBundle(locale),
			"a-content-repository-can-be-connected-to-multiple-sites.-please-" +
				"add-those-sites-here");
	}

	@Override
	protected boolean isShowControls() {
		return false;
	}

}