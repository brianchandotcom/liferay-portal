/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.internal.model.listener;

import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.layout.set.prototype.internal.sync.LayoutSetPrototypeSyncSessionManagerUtil;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Correa
 */
@Component(service = ModelListener.class)
public class LayoutSetPrototypeSyncBackgroundTaskModelListener
	extends BaseModelListener<BackgroundTask> {

	@Override
	public void onBeforeCreate(BackgroundTask backgroundTask) {
		if (!Objects.equals(
				backgroundTask.getTaskExecutorClassName(),
				BackgroundTaskExecutorNames.
					LAYOUT_SET_PROTOTYPE_SYNC_BACKGROUND_TASK_EXECUTOR)) {

			return;
		}

		LayoutSetPrototypeSyncSessionManagerUtil.contribute(backgroundTask);
	}

}