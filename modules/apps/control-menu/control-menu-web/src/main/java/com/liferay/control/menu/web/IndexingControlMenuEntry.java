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

package com.liferay.control.menu.web;

import com.liferay.control.menu.BaseControlMenuEntry;
import com.liferay.control.menu.ControlMenuEntry;
import com.liferay.control.menu.constants.ControlMenuCategoryKeys;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.search.internal.background.task.ReindexPortalBackgroundTaskExecutor;
import com.liferay.portal.search.internal.background.task.ReindexSingleIndexerBackgroundTaskExecutor;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 *
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"control.menu.category.key=" + ControlMenuCategoryKeys.TOOLS,
		"service.ranking:Integer=500"
	},
	service = ControlMenuEntry.class
)
public class IndexingControlMenuEntry
	extends BaseControlMenuEntry implements ControlMenuEntry {

	@Override
	public Map<String, Object> getData(HttpServletRequest request) {
		Map<String, Object> data = super.getData(request);

		data.put("qa-id", "indexing");

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		data.put("title", getLabel(themeDisplay.getLocale()));

		return data;
	}

	@Override
	public String getIconCssClass(HttpServletRequest request) {
		return "icon-refresh";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle, "the-portal-is-currently-reindexing");
	}

	@Override
	public String getURL(HttpServletRequest request) {
		return null;
	}

	@Override
	public boolean hasAccessPermission(HttpServletRequest request)
		throws PortalException {

		int count = _backgroundTaskManager.getBackgroundTasksCount(
			CompanyConstants.SYSTEM,
			new String[] {
				ReindexPortalBackgroundTaskExecutor.class.getName(),
				ReindexSingleIndexerBackgroundTaskExecutor.class.getName()
			},
			false);

		if (count == 0) {
			return false;
		}

		return super.hasAccessPermission(request);
	}

	@Reference(unbind = "-")
	public void setBackgroundTaskManager(
		BackgroundTaskManager backgroundTaskManager) {

		_backgroundTaskManager = backgroundTaskManager;
	}

	private volatile BackgroundTaskManager _backgroundTaskManager;

}