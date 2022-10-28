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

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistry;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;

import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = BackgroundTaskStatusRegistry.class)
public class BackgroundTaskStatusRegistryImpl
	implements BackgroundTaskStatusRegistry {

	@Override
	public BackgroundTaskStatus getBackgroundTaskStatus(long backgroundTaskId) {
		if (!_clusterMasterExecutor.isMaster()) {
			return _getMasterBackgroundTaskStatus(backgroundTaskId);
		}

		Map.Entry<BackgroundTaskStatus, BackgroundTaskStatusMessageTranslator>
			backgroundTaskStatusEntry = _backgroundTaskStatuses.get(
				backgroundTaskId);

		if (backgroundTaskStatusEntry == null) {
			return null;
		}

		return backgroundTaskStatusEntry.getKey();
	}

	@Override
	public BackgroundTaskStatusMessageTranslator
		getBackgroundTaskStatusMessageTranslator(long backgroundTaskId) {

		Map.Entry<BackgroundTaskStatus, BackgroundTaskStatusMessageTranslator>
			backgroundTaskStatusEntry = _backgroundTaskStatuses.get(
				backgroundTaskId);

		if (backgroundTaskStatusEntry == null) {
			return null;
		}

		return backgroundTaskStatusEntry.getValue();
	}

	@Override
	public BackgroundTaskStatus registerBackgroundTaskStatus(
		long backgroundTaskId,
		BackgroundTaskStatusMessageTranslator
			backgroundTaskStatusMessageTranslator) {

		Map.Entry<BackgroundTaskStatus, BackgroundTaskStatusMessageTranslator>
			backgroundTaskStatusEntry = _backgroundTaskStatuses.computeIfAbsent(
				backgroundTaskId,
				key -> new AbstractMap.SimpleImmutableEntry<>(
					new BackgroundTaskStatus(),
					backgroundTaskStatusMessageTranslator));

		return backgroundTaskStatusEntry.getKey();
	}

	@Override
	public BackgroundTaskStatus unregisterBackgroundTaskStatus(
		long backgroundTaskId) {

		Map.Entry<BackgroundTaskStatus, BackgroundTaskStatusMessageTranslator>
			backgroundTaskStatusEntry = _backgroundTaskStatuses.remove(
				backgroundTaskId);

		if (backgroundTaskStatusEntry == null) {
			return null;
		}

		return backgroundTaskStatusEntry.getKey();
	}

	private BackgroundTaskStatus _getMasterBackgroundTaskStatus(
		long backgroundTaskId) {

		try {
			MethodHandler methodHandler = new MethodHandler(
				BackgroundTaskStatusRegistryUtil.class.getDeclaredMethod(
					"getBackgroundTaskStatus", long.class),
				backgroundTaskId);

			Future<BackgroundTaskStatus> future =
				_clusterMasterExecutor.executeOnMaster(methodHandler);

			return future.get();
		}
		catch (Exception exception) {
			_log.error("Unable to retrieve status from master node", exception);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BackgroundTaskStatusRegistryImpl.class);

	private final Map
		<Long,
		 Map.Entry<BackgroundTaskStatus, BackgroundTaskStatusMessageTranslator>>
			_backgroundTaskStatuses = new ConcurrentHashMap<>();

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

}