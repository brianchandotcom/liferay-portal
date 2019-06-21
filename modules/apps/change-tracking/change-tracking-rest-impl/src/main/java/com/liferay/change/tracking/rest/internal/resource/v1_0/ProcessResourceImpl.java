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

package com.liferay.change.tracking.rest.internal.resource.v1_0;

import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.rest.dto.v1_0.Process;
import com.liferay.change.tracking.rest.internal.dto.factory.v1_0.CollectionFactory;
import com.liferay.change.tracking.rest.internal.dto.factory.v1_0.ProcessUserFactory;
import com.liferay.change.tracking.rest.resource.v1_0.CollectionResource;
import com.liferay.change.tracking.rest.resource.v1_0.ProcessResource;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutorRegistry;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Máté Thurzó
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/process.properties",
	scope = ServiceScope.PROTOTYPE, service = ProcessResource.class
)
public class ProcessResourceImpl extends BaseProcessResourceImpl {

	@Override
	public Process getProcess(Long processId) throws Exception {
		CTProcess ctProcess = _ctProcessLocalService.fetchCTProcess(processId);

		if (ctProcess != null) {
			return _getProcess(ctProcess);
		}

		return new Process();
	}

	private Optional<com.liferay.portal.kernel.backgroundtask.BackgroundTask>
		_fetchPortalKernelBackgroundTaskOptional(
			BackgroundTask backgroundTask) {

		try {
			return Optional.of(
				_backgroundTaskManager.getBackgroundTask(
					backgroundTask.getBackgroundTaskId()));
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get background task", pe);
			}

			return Optional.empty();
		}
	}

	private Optional<BackgroundTaskDisplay> _getBackgroundTaskDisplayOptional(
		BackgroundTask backgroundTask,
		BackgroundTaskExecutor backgroundTaskExecutor) {

		Optional<com.liferay.portal.kernel.backgroundtask.BackgroundTask>
			portalKernelBackgroundTaskOptional =
				_fetchPortalKernelBackgroundTaskOptional(backgroundTask);

		return portalKernelBackgroundTaskOptional.map(
			backgroundTaskExecutor::getBackgroundTaskDisplay);
	}

	private Process _getProcess(CTProcess ctProcess) {
		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctProcess.getCtCollectionId());

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchBackgroundTask(
				ctProcess.getBackgroundTaskId());

		Optional<BackgroundTask> backgroundTaskOptional = Optional.ofNullable(
			backgroundTask);

		User user = _userLocalService.fetchUser(ctProcess.getUserId());

		return new Process() {
			{
				collection = _collectionFactory.toCollection(ctCollection);
				processId = ctProcess.getCtProcessId();
				dateCreated = ctProcess.getCreateDate();

				status = backgroundTaskOptional.map(
					BackgroundTask::getStatusLabel
				).orElse(
					StringPool.BLANK
				);

				percentage = backgroundTaskOptional.flatMap(
					ProcessResourceImpl.this::
						_fetchPortalKernelBackgroundTaskOptional
				).map(
					com.liferay.portal.kernel.backgroundtask.BackgroundTask::
						getTaskExecutorClassName
				).map(
					_backgroundTaskExecutorRegistry::getBackgroundTaskExecutor
				).flatMap(
					backgroundTaskExecutor -> _getBackgroundTaskDisplayOptional(
						backgroundTask, backgroundTaskExecutor)
				).map(
					BackgroundTaskDisplay::getPercentage
				).orElse(
					100
				);

				if (user != null) {
					processUser = _processUserFactory.toProcessUser(user);
				}
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProcessResourceImpl.class);

	@Reference
	private BackgroundTaskExecutorRegistry _backgroundTaskExecutorRegistry;

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private CollectionFactory _collectionFactory;

	@Reference
	private CollectionResource _collectionResource;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTProcessLocalService _ctProcessLocalService;

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private ProcessUserFactory _processUserFactory;

	@Reference
	private UserLocalService _userLocalService;

}