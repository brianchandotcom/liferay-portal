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

package com.liferay.layout.seo.web.internal.servlet.taglib.ui;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.layout.seo.web.internal.constants.LayoutSEOScreenNavigationEntryConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alicia Garcia
 */
@Component(
	property = "screen.navigation.entry.order:Integer=3",
	service = ScreenNavigationEntry.class
)
public class LayoutCustomMetaTagsScreenNavigationEntry
	extends BaseLayoutScreenNavigationEntry {

	@Override
	public String getEntryKey() {
		return LayoutSEOScreenNavigationEntryConstants.
			ENTRY_KEY_CUSTOM_META_TAGS;
	}

	@Override
	protected String getJspPath() {
		return "/layout/screen/navigation/entries/custom_meta_tags.jsp";
	}

}