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

package com.liferay.portal.background.task.internal;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.cluster.BaseClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, service = ClusterMasterTokenTransitionListener.class
)
public class BackgroundTaskClusterMasterTokenTransitionListener
	extends BaseClusterMasterTokenTransitionListener {

	@Reference(unbind = "-")
	public void setBackgroundTaskManager(
		BackgroundTaskManager backgroundTaskManager) {

		_backgroundTaskManager = backgroundTaskManager;
	}

	@Override
	protected void doMasterTokenAcquired() throws Exception {
		_backgroundTaskManager.cleanUpBackgroundTasks();
	}

	@Override
	protected void doMasterTokenReleased() throws Exception {
	}

	private volatile BackgroundTaskManager _backgroundTaskManager;

}