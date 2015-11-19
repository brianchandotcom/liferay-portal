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

package com.liferay.control.menu.web.control.menu.entry;

import com.liferay.control.menu.BaseControlMenuEntry;
import com.liferay.control.menu.ControlMenuEntry;
import com.liferay.control.menu.constants.ControlMenuCategoryKeys;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.search.internal.background.task.ReindexPortalBackgroundTaskExecutor;
import com.liferay.portal.search.internal.background.task.ReindexSingleIndexerBackgroundTaskExecutor;

import java.util.Locale;

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
	public String getIconCssClass(HttpServletRequest request) {
		return "icon-refresh icon-spin";
	}

	@Override
	public String getLabel(Locale locale) {
		return "the-portal-is-currently-reindexing";
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

	private BackgroundTaskManager _backgroundTaskManager;

}