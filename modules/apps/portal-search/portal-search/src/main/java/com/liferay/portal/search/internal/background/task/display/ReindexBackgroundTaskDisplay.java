/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.background.task.display;

import com.liferay.portal.background.task.display.BaseBackgroundTaskDisplay;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
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
			getBackgroundTaskStatusAttributeString("percentage"),
			PERCENTAGE_MIN);
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