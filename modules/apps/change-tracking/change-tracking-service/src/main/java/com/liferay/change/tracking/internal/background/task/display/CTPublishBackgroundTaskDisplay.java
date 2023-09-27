/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.background.task.display;

import com.liferay.portal.background.task.display.BaseBackgroundTaskDisplay;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.template.TemplateResource;

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
		return 0;
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