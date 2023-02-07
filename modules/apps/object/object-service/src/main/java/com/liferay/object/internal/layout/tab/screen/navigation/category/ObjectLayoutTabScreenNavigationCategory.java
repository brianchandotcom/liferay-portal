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

package com.liferay.object.internal.layout.tab.screen.navigation.category;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectLayoutTab;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Feliphe Marinho
 */
public class ObjectLayoutTabScreenNavigationCategory
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<ObjectLayoutTab> {

	public ObjectLayoutTabScreenNavigationCategory(
		ObjectDefinition objectDefinition, ObjectLayoutTab objectLayoutTab) {

		_objectDefinition = objectDefinition;
		_objectLayoutTab = objectLayoutTab;
	}

	@Override
	public String getCategoryKey() {
		return String.valueOf(_objectLayoutTab.getObjectLayoutTabId());
	}

	@Override
	public String getEntryKey() {
		return String.valueOf(_objectLayoutTab.getObjectLayoutTabId());
	}

	@Override
	public String getLabel(Locale locale) {
		return _objectLayoutTab.getName(locale);
	}

	@Override
	public String getScreenNavigationKey() {
		return _objectDefinition.getClassName();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute("regularObjectLayoutTab", Boolean.TRUE);
	}

	private final ObjectDefinition _objectDefinition;
	private final ObjectLayoutTab _objectLayoutTab;

}