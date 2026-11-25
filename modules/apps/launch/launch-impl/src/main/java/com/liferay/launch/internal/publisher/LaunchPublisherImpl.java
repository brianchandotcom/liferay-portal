/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.internal.publisher;

import com.liferay.launch.internal.background.task.LaunchPublishBackgroundTaskExecutor;
import com.liferay.launch.internal.constants.LaunchDestinationNames;
import com.liferay.launch.internal.util.LaunchUtil;
import com.liferay.launch.publisher.LaunchPublisher;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Jose Luis Navarro
 */
@Component(service = LaunchPublisher.class)
public class LaunchPublisherImpl implements LaunchPublisher {

	@Override
	public void publish(long launchId, long userId) throws PortalException {
		ObjectEntry launchObjectEntry = LaunchUtil.getLaunchObjectEntry(
			launchId);

		if (launchObjectEntry.getStatus() ==
				WorkflowConstants.STATUS_APPROVED) {

			throw new PortalException(
				"Launch " + launchId + " was already published");
		}

		String name = String.valueOf(launchId);

		int backgroundTasksCount =
			_backgroundTaskManager.getBackgroundTasksCount(
				BackgroundTaskConstants.GROUP_ID_DEFAULT, name,
				LaunchPublishBackgroundTaskExecutor.class.getName(), false);

		if (backgroundTasksCount > 0) {
			throw new PortalException(
				"Launch " + launchId + " is already being published");
		}

		_backgroundTaskManager.addBackgroundTask(
			userId, BackgroundTaskConstants.GROUP_ID_DEFAULT, name, null,
			LaunchPublishBackgroundTaskExecutor.class,
			HashMapBuilder.<String, Serializable>put(
				"launchId", launchId
			).put(
				"scheduled",
				launchObjectEntry.getStatus() ==
					WorkflowConstants.STATUS_SCHEDULED
			).build(),
			null);
	}

	@Override
	public void schedulePublish(Date date, long launchId, long userId)
		throws PortalException {

		ObjectEntry launchObjectEntry = LaunchUtil.getLaunchObjectEntry(
			launchId);

		if (launchObjectEntry.getStatus() != WorkflowConstants.STATUS_DRAFT) {
			throw new PortalException(
				"Launch " + launchId + " must be open to be scheduled");
		}

		if ((date == null) ||
			(date.getTime() < (System.currentTimeMillis() + Time.MINUTE))) {

			SchedulerException schedulerException = new SchedulerException(
				"Launch " + launchId +
					" must be scheduled at least one minute in the future");

			schedulerException.setType(
				SchedulerException.TYPE_INVALID_START_DATE);

			throw schedulerException;
		}

		ServiceContext serviceContext = _createServiceContext(
			launchObjectEntry, userId);

		_updateScheduledDate(date, launchObjectEntry, serviceContext, userId);

		_objectEntryLocalService.updateStatus(
			userId, launchId, WorkflowConstants.STATUS_SCHEDULED,
			serviceContext);

		Message message = new Message();

		message.put("companyId", launchObjectEntry.getCompanyId());
		message.put("launchId", launchId);
		message.put("userId", userId);

		_schedulerEngineHelper.schedule(
			_triggerFactory.createTrigger(
				_getJobName(launchObjectEntry),
				LaunchDestinationNames.LAUNCH_SCHEDULED_PUBLISH, date, null, 0,
				null),
			StorageType.PERSISTED, String.valueOf(launchId),
			LaunchDestinationNames.LAUNCH_SCHEDULED_PUBLISH, message);
	}

	@Override
	public void unschedulePublish(long launchId) throws PortalException {
		ObjectEntry launchObjectEntry = LaunchUtil.getLaunchObjectEntry(
			launchId);

		if (launchObjectEntry.getStatus() ==
				WorkflowConstants.STATUS_SCHEDULED) {

			long userId = PrincipalThreadLocal.getUserId();

			ServiceContext serviceContext = _createServiceContext(
				launchObjectEntry, userId);

			_objectEntryLocalService.updateStatus(
				userId, launchId, WorkflowConstants.STATUS_DRAFT,
				serviceContext);

			_updateScheduledDate(
				null, launchObjectEntry, serviceContext, userId);
		}

		_schedulerEngineHelper.delete(
			_getJobName(launchObjectEntry),
			LaunchDestinationNames.LAUNCH_SCHEDULED_PUBLISH,
			StorageType.PERSISTED);
	}

	private ServiceContext _createServiceContext(
		ObjectEntry launchObjectEntry, long userId) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(launchObjectEntry.getCompanyId());
		serviceContext.setUserId(userId);
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		return serviceContext;
	}

	private String _getJobName(ObjectEntry launchObjectEntry) {
		return StringBundler.concat(
			launchObjectEntry.getObjectEntryId(), StringPool.AT,
			launchObjectEntry.getCompanyId());
	}

	private void _updateScheduledDate(
			Date date, ObjectEntry launchObjectEntry,
			ServiceContext serviceContext, long userId)
		throws PortalException {

		Map<String, Serializable> values = launchObjectEntry.getValues();

		values.put("scheduledDate", date);

		_objectEntryLocalService.updateObjectEntry(
			userId, launchObjectEntry.getObjectEntryId(),
			launchObjectEntry.getObjectEntryFolderId(), values, serviceContext);
	}

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}