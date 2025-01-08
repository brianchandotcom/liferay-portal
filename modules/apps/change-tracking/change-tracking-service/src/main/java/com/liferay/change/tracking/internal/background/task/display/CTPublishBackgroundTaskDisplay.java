/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.background.task.display;

import com.liferay.portal.background.task.display.BaseBackgroundTaskDisplay;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Collections;
import java.util.Map;

/**
 * @author Máté Thurzó
 */
public class CTPublishBackgroundTaskDisplay extends BaseBackgroundTaskDisplay {

	public CTPublishBackgroundTaskDisplay(BackgroundTask backgroundTask) {
		super(backgroundTask);
	}

	@Override
	public int getPercentage() {
		if (backgroundTaskStatus != null) {
			double percentage = GetterUtil.getDouble(
				backgroundTaskStatus.getAttribute("currentPercentage"));

			return GetterUtil.getInteger(
				Math.max(Math.round(percentage * 100), PERCENTAGE_MIN));
		}

		if (backgroundTask.getStatus() ==
				BackgroundTaskConstants.STATUS_SUCCESSFUL) {

			return PERCENTAGE_MAX;
		}

		return PERCENTAGE_MIN;
	}

	@Override
	protected TemplateResource getTemplateResource() {
		return null;
	}

	@Override
	protected Map<String, Object> getTemplateVars() {
		return Collections.emptyMap();
	}

}