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

import com.liferay.change.tracking.engine.exception.CTEntryCollisionCTEngineException;
import com.liferay.change.tracking.internal.background.task.display.CTPublishBackgroundTaskDisplay;
import com.liferay.change.tracking.internal.process.log.CTProcessLog;
import com.liferay.change.tracking.internal.process.util.CTProcessMessageUtil;
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
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageSender;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistry;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

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

		long ctCollectionId = GetterUtil.getLong(
			taskContextMap.get("ctCollectionId"));
		boolean ignoreCollision = GetterUtil.getBoolean(
			taskContextMap.get("ignoreCollision"));

		CTCollection ctCollection = _ctCollectionLocalService.getCTCollection(
			ctCollectionId);

		List<CTEntry> ctEntries = _ctEntryLocalService.getCTCollectionCTEntries(
			ctCollectionId);

		_backgroundTaskStatusMessageSender.sendBackgroundTaskStatusMessage(
			CTProcessMessageUtil.getCTProcessStartedMessage(ctEntries.size()));

		for (CTEntry ctEntry : ctEntries) {
			if (ctEntry.isCollision()) {
				_backgroundTaskStatusMessageSender.
					sendBackgroundTaskStatusMessage(
						CTProcessMessageUtil.getCTEntryCollisionMessage(
							ctEntry, ignoreCollision));

				if (!ignoreCollision) {
					throw new CTEntryCollisionCTEngineException(
						ctEntry.getCompanyId(), ctEntry.getCtEntryId());
				}
			}

			_ctEntryLocalService.updateStatus(
				ctEntry.getCtEntryId(), WorkflowConstants.STATUS_APPROVED);

			CTEntryCollisionUtil.checkCollidingCTEntries(
				_ctEntryLocalService, ctEntry.getCompanyId(),
				ctEntry.getModelClassPK(), ctEntry.getModelResourcePrimKey());

			_backgroundTaskStatusMessageSender.sendBackgroundTaskStatusMessage(
				CTProcessMessageUtil.getCTEntryPublishedMessage(ctEntry));
		}

		_ctCollectionLocalService.updateStatus(
			backgroundTask.getUserId(), ctCollection,
			WorkflowConstants.STATUS_APPROVED);

		_backgroundTaskStatusMessageSender.sendBackgroundTaskStatusMessage(
			CTProcessMessageUtil.getCTProcessFinishedMessage());

		BackgroundTaskStatus backgroundTaskStatus =
			_backgroundTaskStatusRegistry.getBackgroundTaskStatus(
				backgroundTask.getBackgroundTaskId());

		CTProcessLog ctProcessLog =
			(CTProcessLog)backgroundTaskStatus.getAttribute("ctProcessLog");

		String ctProcessLogJSON = ctProcessLog.toString();

		_backgroundTaskManager.addBackgroundTaskAttachment(
			backgroundTask.getUserId(), backgroundTask.getBackgroundTaskId(),
			"log", FileUtil.createTempFile(ctProcessLogJSON.getBytes()));

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

	private BackgroundTaskExecutor _backgroundTaskExecutor;

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private BackgroundTaskStatusMessageSender
		_backgroundTaskStatusMessageSender;

	@Reference
	private BackgroundTaskStatusRegistry _backgroundTaskStatusRegistry;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTProcessLocalService _ctProcessLocalService;

}