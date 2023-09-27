/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = BackgroundTaskStatusRegistry.class)
public class BackgroundTaskStatusRegistryImpl
	implements BackgroundTaskStatusRegistry {

	@Override
	public BackgroundTaskStatus getBackgroundTaskStatus(long backgroundTaskId) {
		if (!_clusterMasterExecutor.isMaster()) {
			return _getMasterBackgroundTaskStatus(backgroundTaskId);
		}

		Map.Entry<BackgroundTaskStatus, BackgroundTaskStatusMessageTranslator>
			entry = _entries.get(backgroundTaskId);

		if (entry == null) {
			return null;
		}

		return entry.getKey();
	}

	@Override
	public BackgroundTaskStatusMessageTranslator
		getBackgroundTaskStatusMessageTranslator(long backgroundTaskId) {

		Map.Entry<BackgroundTaskStatus, BackgroundTaskStatusMessageTranslator>
			entry = _entries.get(backgroundTaskId);

		if (entry == null) {
			return null;
		}

		return entry.getValue();
	}

	@Override
	public BackgroundTaskStatus registerBackgroundTaskStatus(
		long backgroundTaskId,
		BackgroundTaskStatusMessageTranslator
			backgroundTaskStatusMessageTranslator) {

		Map.Entry<BackgroundTaskStatus, BackgroundTaskStatusMessageTranslator>
			entry = _entries.computeIfAbsent(
				backgroundTaskId,
				key -> new AbstractMap.SimpleImmutableEntry<>(
					new BackgroundTaskStatus(),
					backgroundTaskStatusMessageTranslator));

		return entry.getKey();
	}

	@Override
	public BackgroundTaskStatus unregisterBackgroundTaskStatus(
		long backgroundTaskId) {

		Map.Entry<BackgroundTaskStatus, BackgroundTaskStatusMessageTranslator>
			entry = _entries.remove(backgroundTaskId);

		if (entry == null) {
			return null;
		}

		return entry.getKey();
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
			_log.error(
				"Unable to get master background task status", exception);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BackgroundTaskStatusRegistryImpl.class);

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

	private final Map
		<Long,
		 Map.Entry<BackgroundTaskStatus, BackgroundTaskStatusMessageTranslator>>
			_entries = new ConcurrentHashMap<>();

}