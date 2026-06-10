/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.internal.messaging;

import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.layout.set.prototype.constants.LayoutSetPrototypeConstants;
import com.liferay.layout.set.prototype.internal.sync.LayoutSetPrototypeSyncSessionManagerUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.model.BackgroundTaskTable;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(
	property = "destination.name=" + DestinationNames.BACKGROUND_TASK_STATUS,
	service = MessageListener.class
)
public class
	LayoutSetPrototypeSyncExportImportBackgroundTaskStatusMessageListener
		implements MessageListener {

	@Override
	public void receive(Message message) throws MessageListenerException {
		if (!Objects.equals(
				message.getString("taskExecutorClassName"),
				BackgroundTaskExecutorNames.
					LAYOUT_SET_PROTOTYPE_SYNC_EXPORT_IMPORT_BACKGROUND_TASK_EXECUTOR)) {

			return;
		}

		int backgroundTaskStatus = message.getInteger("status");

		if ((backgroundTaskStatus !=
				BackgroundTaskConstants.STATUS_CANCELLED) &&
			(backgroundTaskStatus !=
				BackgroundTaskConstants.STATUS_COMPLETED_WITH_ERRORS) &&
			(backgroundTaskStatus != BackgroundTaskConstants.STATUS_FAILED) &&
			(backgroundTaskStatus !=
				BackgroundTaskConstants.STATUS_SUCCESSFUL)) {

			return;
		}

		long backgroundTaskId = message.getLong("backgroundTaskId");

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchBackgroundTask(backgroundTaskId);

		if (backgroundTask == null) {
			return;
		}

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		String syncSessionId = MapUtil.getString(
			taskContextMap, LayoutSetPrototypeConstants.KEY_SYNC_SESSION_ID);

		if (Validator.isNull(syncSessionId)) {
			if (backgroundTaskStatus ==
					BackgroundTaskConstants.STATUS_SUCCESSFUL) {

				_deleteBackgroundTask(backgroundTask);
			}

			return;
		}

		Long[] layoutSetGroupIds = (Long[])taskContextMap.get(
			LayoutSetPrototypeSyncSessionManagerUtil.KEY_LAYOUT_SET_GROUP_IDS);

		if (layoutSetGroupIds == null) {
			return;
		}

		BackgroundTask[] incompletedBackgroundTasks = _getBackgroundTasks(
			false, layoutSetGroupIds, syncSessionId);

		if (incompletedBackgroundTasks.length > 0) {
			return;
		}

		Lock lock = _acquireLock(syncSessionId);

		if (lock == null) {
			return;
		}

		try {
			BackgroundTask[] completedBackgroundTasks = _getBackgroundTasks(
				true, layoutSetGroupIds, syncSessionId);

			if (completedBackgroundTasks.length < layoutSetGroupIds.length) {
				return;
			}

			long layoutSetPrototypeId = MapUtil.getLong(
				taskContextMap,
				LayoutSetPrototypeSyncSessionManagerUtil.
					KEY_LAYOUT_SET_PROTOTYPE_ID);

			LayoutSetPrototype layoutSetPrototype =
				_layoutSetPrototypeLocalService.fetchLayoutSetPrototype(
					layoutSetPrototypeId);

			if (layoutSetPrototype == null) {
				return;
			}

			Set<Integer> backgroundTaskStatuses = new HashSet<>(
				TransformUtil.transformToList(
					completedBackgroundTasks, BackgroundTask::getStatus));

			if (MapUtil.getBoolean(
					taskContextMap,
					LayoutSetPrototypeSyncSessionManagerUtil.
						KEY_PRE_VALIDATION_ERRORS)) {

				backgroundTaskStatuses.add(
					BackgroundTaskConstants.STATUS_FAILED);
			}

			LayoutSetPrototypeSyncSessionManagerUtil.postNotification(
				backgroundTaskStatuses, layoutSetPrototype.getNameMap(),
				MapUtil.getLong(
					taskContextMap,
					LayoutSetPrototypeSyncSessionManagerUtil.KEY_SYNC_USER_ID));

			for (BackgroundTask completedBackgroundTask :
					completedBackgroundTasks) {

				if (completedBackgroundTask.getStatus() ==
						BackgroundTaskConstants.STATUS_SUCCESSFUL) {

					_deleteBackgroundTask(completedBackgroundTask);
				}
				else {
					_markNotified(completedBackgroundTask);
				}
			}
		}
		finally {
			_lockManager.unlock(
				lock.getClassName(), lock.getKey(), lock.getOwner());
		}
	}

	private Lock _acquireLock(String syncSessionId) {
		String lockClassName = LayoutSetPrototype.class.getName();
		String lockKey = "sync-notify:" + syncSessionId;
		String owner = PortalUUIDUtil.generate();

		Lock lock = _lockManager.lock(lockClassName, lockKey, owner);

		if (!owner.equals(lock.getOwner())) {
			Date createDate = lock.getCreateDate();

			if ((System.currentTimeMillis() - createDate.getTime()) <
					_LOCK_EXPIRATION_TIME) {

				return null;
			}

			lock = _lockManager.lock(
				lockClassName, lockKey, lock.getOwner(), owner);

			if (!owner.equals(lock.getOwner())) {
				return null;
			}
		}

		return lock;
	}

	private void _deleteBackgroundTask(BackgroundTask backgroundTask) {
		try {
			_backgroundTaskLocalService.deleteBackgroundTask(
				backgroundTask.getBackgroundTaskId());
		}
		catch (Exception exception) {
			_log.error(
				"Unable to delete background task " +
					backgroundTask.getBackgroundTaskId(),
				exception);
		}
	}

	private BackgroundTask[] _getBackgroundTasks(
		boolean completed, Long[] layoutSetGroupIds, String syncSessionId) {

		List<BackgroundTask> backgroundTasks =
			_backgroundTaskLocalService.dslQuery(
				DSLQueryFactoryUtil.select(
					BackgroundTaskTable.INSTANCE
				).from(
					BackgroundTaskTable.INSTANCE
				).where(
					BackgroundTaskTable.INSTANCE.taskExecutorClassName.eq(
						BackgroundTaskExecutorNames.
							LAYOUT_SET_PROTOTYPE_SYNC_EXPORT_IMPORT_BACKGROUND_TASK_EXECUTOR
					).and(
						BackgroundTaskTable.INSTANCE.completed.eq(completed)
					).and(
						BackgroundTaskTable.INSTANCE.groupId.in(
							layoutSetGroupIds)
					)
				));

		return TransformUtil.transformToArray(
			backgroundTasks,
			backgroundTask -> {
				Map<String, Serializable> taskContextMap =
					backgroundTask.getTaskContextMap();

				String taskSyncSessionId = MapUtil.getString(
					taskContextMap,
					LayoutSetPrototypeConstants.KEY_SYNC_SESSION_ID);

				if (!Objects.equals(syncSessionId, taskSyncSessionId) ||
					(completed &&
					 MapUtil.getBoolean(
						 taskContextMap,
						 LayoutSetPrototypeSyncSessionManagerUtil.
							 KEY_NOTIFIED))) {

					return null;
				}

				return backgroundTask;
			},
			BackgroundTask.class);
	}

	private void _markNotified(BackgroundTask backgroundTask) {
		try {
			Map<String, Serializable> taskContextMap =
				backgroundTask.getTaskContextMap();

			taskContextMap.put(
				LayoutSetPrototypeSyncSessionManagerUtil.KEY_NOTIFIED,
				Boolean.TRUE);

			backgroundTask.setTaskContextMap(taskContextMap);

			backgroundTask = _backgroundTaskLocalService.updateBackgroundTask(
				backgroundTask);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to mark background task " +
					backgroundTask.getBackgroundTaskId() + " as notified",
				exception);
		}
	}

	private static final long _LOCK_EXPIRATION_TIME = 5 * 60 * 1000;

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeSyncExportImportBackgroundTaskStatusMessageListener.
			class);

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@Reference
	private LockManager _lockManager;

}