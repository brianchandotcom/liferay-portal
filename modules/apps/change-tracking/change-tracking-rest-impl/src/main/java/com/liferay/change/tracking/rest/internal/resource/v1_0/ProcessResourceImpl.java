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
import com.liferay.change.tracking.rest.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.dto.v1_0.Process;
import com.liferay.change.tracking.rest.dto.v1_0.ProcessUser;
import com.liferay.change.tracking.rest.internal.jaxrs.exception.ProcessCollectionNotFoundException;
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
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import javax.validation.constraints.NotNull;

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
	public Process getProcess(@NotNull Long processId) throws Exception {
		CTProcess ctProcess = _ctProcessLocalService.fetchCTProcess(processId);

		Process process;

		if (ctProcess != null) {
			process = _getProcess(ctProcess);
		}
		else {
			process = new Process();
		}

		return process;
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

	private Integer _getPercentage(
		BackgroundTask backgroundTask,
		Optional<BackgroundTask> backgroundTaskOptional) {

		return backgroundTaskOptional.flatMap(
			this::_fetchPortalKernelBackgroundTaskOptional
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
	}

	private String _getPortraitURL(User user) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		if (user != null) {
			return user.fetchPortraitURL(themeDisplay);
		}

		return UserConstants.getPortraitURL(
			themeDisplay.getPathImage(), true, 0, StringPool.BLANK);
	}

	private Process _getProcess(CTProcess ctProcess) throws Exception {
		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctProcess.getCtCollectionId());

		Collection collection;

		try {
			collection = _collectionResource.getCollection(
				ctCollection.getCtCollectionId());
		}
		catch (Exception e) {
			throw new ProcessCollectionNotFoundException(
				"Unable to get process collection " +
					ctCollection.getCtCollectionId(),
				e);
		}

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchBackgroundTask(
				ctProcess.getBackgroundTaskId());

		Optional<BackgroundTask> backgroundTaskOptional = Optional.ofNullable(
			backgroundTask);

		User user = _userLocalService.fetchUser(ctProcess.getUserId());

		Optional<User> userOptional = Optional.ofNullable(user);

		Process process = new Process();

		process.setCollection(collection);
		process.setCompanyId(ctProcess.getCompanyId());
		process.setDateCreated(ctProcess.getCreateDate());
		process.setPercentage(
			_getPercentage(backgroundTask, backgroundTaskOptional));
		process.setProcessId(ctProcess.getCtProcessId());
		process.setProcessUser(_getProcessUser(userOptional));

		process.setStatus(
			backgroundTaskOptional.map(
				BackgroundTask::getStatusLabel
			).orElse(
				StringPool.BLANK
			));

		return process;
	}

	private ProcessUser _getProcessUser(Optional<User> userOptional) {
		ProcessUser processUser = new ProcessUser();

		processUser.setUserInitials(
			userOptional.map(
				User::getInitials
			).orElse(
				StringPool.BLANK
			));

		processUser.setUserName(
			userOptional.map(
				User::getFullName
			).orElse(
				StringPool.BLANK
			));

		processUser.setUserPortraitURL(
			userOptional.map(
				this::_getPortraitURL
			).orElse(
				StringPool.BLANK
			));

		return processUser;
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
	private UserLocalService _userLocalService;

}