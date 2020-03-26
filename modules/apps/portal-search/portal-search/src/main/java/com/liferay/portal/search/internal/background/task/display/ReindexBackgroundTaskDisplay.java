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

package com.liferay.portal.search.internal.background.task.display;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.display.BaseBackgroundTaskDisplay;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;

/**
 * @author Andrew Betts
 */
public class ReindexBackgroundTaskDisplay extends BaseBackgroundTaskDisplay {

	public ReindexBackgroundTaskDisplay(BackgroundTask backgroundTask) {
		super(backgroundTask);
	}

	@Override
	public int getPercentage() {
		return GetterUtil.getInteger(
			getBackgroundTaskStatusAttributeLong("percentage"),
			PERCENTAGE_NONE);
	}

	@Override
	protected TemplateResource getTemplateResource() {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return new URLTemplateResource(
			_PROGRESS_TEMPLATE, classLoader.getResource(_PROGRESS_TEMPLATE));
	}

	@Override
	protected Map<String, Object> getTemplateVars() {
		return null;
	}

	private static final String _PROGRESS_TEMPLATE =
		"com/liferay/portal/search/internal/background/task/display" +
			"/dependencies/reindex_background_task_progress.ftl";

}