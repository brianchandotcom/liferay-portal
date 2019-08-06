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

package com.liferay.change.tracking.internal.background.task;

import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.engine.exception.CTEngineException;
import com.liferay.change.tracking.engine.exception.CTEntryCollisionCTEngineException;
import com.liferay.change.tracking.engine.exception.CTProcessCTEngineException;
import com.liferay.change.tracking.internal.background.task.display.CTPublishBackgroundTaskDisplay;
import com.liferay.change.tracking.internal.process.log.CTProcessLog;
import com.liferay.change.tracking.internal.process.util.CTProcessMessageSenderUtil;
import com.liferay.change.tracking.internal.util.CTEntryCollisionUtil;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistry;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = "background.task.executor.class.name=com.liferay.change.tracking.internal.background.task.CTPublishBackgroundTaskExecutor",
	service = AopService.class
)
public class CTPublishBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor implements AopService {

	public CTPublishBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new CTPublishBackgroundTaskStatusMessageTranslator());
		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_COMPANY);
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return _backgroundTaskExecutor;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long ctProcessId = GetterUtil.getLong(
			taskContextMap.get("ctProcessId"));
		long ctCollectionId = GetterUtil.getLong(
			taskContextMap.get("ctCollectionId"));
		boolean ignoreCollision = GetterUtil.getBoolean(
			taskContextMap.get("ignoreCollision"));

		try {
			_publishCTCollection(
				backgroundTask, ctProcessId, ctCollectionId, ignoreCollision);
		}
		catch (Throwable t) {
			CTProcessMessageSenderUtil.logCTProcessFailed();

			throw new CTProcessCTEngineException(
				backgroundTask.getCompanyId(), ctProcessId,
				"Unable to publish change tracking collection " +
					ctCollectionId,
				t);
		}

		CTProcessMessageSenderUtil.logCTProcessFinished();

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {BackgroundTaskExecutor.class};
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return new CTPublishBackgroundTaskDisplay(backgroundTask);
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		_backgroundTaskExecutor = (BackgroundTaskExecutor)aopProxy;
	}

	private void _checkExistingCollisions(
			CTEntry ctEntry, boolean ignoreCollision)
		throws CTEntryCollisionCTEngineException {

		if (!ctEntry.isCollision()) {
			return;
		}

		CTProcessMessageSenderUtil.logCTEntryCollision(
			ctEntry, ignoreCollision);

		if (!ignoreCollision) {
			throw new CTEntryCollisionCTEngineException(
				ctEntry.getCompanyId(), ctEntry.getCtEntryId());
		}
	}

	private void _publishCTCollection(
			BackgroundTask backgroundTask, long ctProcessId,
			long ctCollectionId, boolean ignoreCollision)
		throws Exception {

		CTProcessMessageSenderUtil.logCTProcessStarted(
			_ctProcessLocalService.getCTProcess(ctProcessId));

		List<CTEntry> ctEntries = _ctEngineManager.getCTEntries(ctCollectionId);

		if (ListUtil.isEmpty(ctEntries)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find change tracking entries with change " +
						"tracking collection ID " + ctCollectionId);
			}

			return;
		}

		_publishCTEntries(
			backgroundTask.getUserId(), ctCollectionId, ctEntries,
			ignoreCollision);

		BackgroundTaskStatus backgroundTaskStatus =
			_backgroundTaskStatusRegistry.getBackgroundTaskStatus(
				backgroundTask.getBackgroundTaskId());

		CTProcessLog ctProcessLog =
			(CTProcessLog)backgroundTaskStatus.getAttribute("ctProcessLog");

		String ctProcessLogJSON = ctProcessLog.toString();

		_backgroundTaskManager.addBackgroundTaskAttachment(
			backgroundTask.getUserId(), backgroundTask.getBackgroundTaskId(),
			"log", FileUtil.createTempFile(ctProcessLogJSON.getBytes()));
	}

	private void _publishCTEntries(
			long userId, long ctCollectionId, List<CTEntry> ctEntries,
			boolean ignoreCollision)
		throws Exception {

		User user = _userLocalService.getUser(userId);

		for (CTEntry ctEntry : ctEntries) {
			_checkExistingCollisions(ctEntry, ignoreCollision);

			_ctEntryLocalService.updateStatus(
				ctEntry.getCtEntryId(), WorkflowConstants.STATUS_APPROVED);

			CTEntryCollisionUtil.checkCollidingCTEntries(ctEntry);

			CTProcessMessageSenderUtil.logCTEntryPublished(ctEntry);
		}

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getCTCollectionOptional(
				user.getCompanyId(), ctCollectionId);

		CTCollection ctCollection = ctCollectionOptional.orElseThrow(
			() -> new CTEngineException(
				user.getCompanyId(),
				"Unable to find change tracking collection " + ctCollectionId));

		try {
			_ctCollectionLocalService.updateStatus(
				userId, ctCollection, WorkflowConstants.STATUS_APPROVED,
				new ServiceContext());
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to update the status of the published change " +
					"tracking collection",
				pe);

			throw pe;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTPublishBackgroundTaskExecutor.class);

	private BackgroundTaskExecutor _backgroundTaskExecutor;

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private BackgroundTaskStatusRegistry _backgroundTaskStatusRegistry;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTProcessLocalService _ctProcessLocalService;

	@Reference
	private UserLocalService _userLocalService;

}