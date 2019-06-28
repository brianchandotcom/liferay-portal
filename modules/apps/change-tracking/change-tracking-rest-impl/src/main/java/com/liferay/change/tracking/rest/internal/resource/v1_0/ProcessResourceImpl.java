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
import com.liferay.change.tracking.rest.constant.v1_0.ProcessType;
import com.liferay.change.tracking.rest.dto.v1_0.Process;
import com.liferay.change.tracking.rest.internal.dto.v1_0.util.CollectionUtil;
import com.liferay.change.tracking.rest.internal.dto.v1_0.util.ProcessUserUtil;
import com.liferay.change.tracking.rest.resource.v1_0.CollectionResource;
import com.liferay.change.tracking.rest.resource.v1_0.ProcessResource;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutorRegistry;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
			return _toProcess(ctProcess);
		}

		return new Process();
	}

	@Override
	public Page<Process> getProcessesPage(
			Long companyId, String keywords, ProcessType type, Long userId,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		List<Process> processes = _getProcesses(
			_getCTProcesses(
				companyId, GetterUtil.getLong(userId), keywords, type,
				pagination, sorts));

		return Page.of(processes, pagination, processes.size());
	}

	private Optional<BackgroundTaskDisplay> _getBackgroundTaskDisplayOptional(
		BackgroundTask backgroundTask,
		BackgroundTaskExecutor backgroundTaskExecutor) {

		Optional<com.liferay.portal.kernel.backgroundtask.BackgroundTask>
			serviceBuilderBackgroundTaskOptional =
				_getServiceBuilderBackgroundTaskOptional(backgroundTask);

		return serviceBuilderBackgroundTaskOptional.map(
			backgroundTaskExecutor::getBackgroundTaskDisplay);
	}

	private List<CTProcess> _getCTProcesses(
		long companyId, long userId, String keywords, ProcessType type,
		Pagination pagination, Sort[] sorts) {

		List<CTProcess> ctProcesses = null;

		if (ProcessType.PUBLISHED_LATEST.equals(type)) {
			Optional<CTProcess> latestCTProcessOptional =
				_ctEngineManager.getLatestCTProcessOptional(companyId);

			ctProcesses = latestCTProcessOptional.map(
				Collections::singletonList
			).orElse(
				Collections.emptyList()
			);
		}
		else {
			int status = _getStatus(type);

			QueryDefinition<CTProcess> queryDefinition =
				SearchUtil.getQueryDefinition(
					CTProcess.class, pagination, sorts);

			queryDefinition.setStatus(status);

			ctProcesses = _ctEngineManager.getCTProcesses(
				companyId, userId, keywords, queryDefinition);
		}

		return ctProcesses;
	}

	private List<Process> _getProcesses(List<CTProcess> ctProcesses) {
		Stream<CTProcess> stream = ctProcesses.stream();

		return stream.map(
			this::_toProcess
		).collect(
			Collectors.toList()
		);
	}

	private Optional<com.liferay.portal.kernel.backgroundtask.BackgroundTask>
		_getServiceBuilderBackgroundTaskOptional(
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

	private int _getStatus(ProcessType type) {
		int status = 0;

		if (ProcessType.ALL.equals(type)) {
			status = WorkflowConstants.STATUS_ANY;
		}
		else if (ProcessType.FAILED.equals(type)) {
			status = BackgroundTaskConstants.STATUS_FAILED;
		}
		else if (ProcessType.IN_PROGRESS.equals(type)) {
			status = BackgroundTaskConstants.STATUS_IN_PROGRESS;
		}
		else if (ProcessType.PUBLISHED.equals(type)) {
			status = BackgroundTaskConstants.STATUS_SUCCESSFUL;
		}

		return status;
	}

	private Process _toProcess(CTProcess ctProcess) {
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
				collection = CollectionUtil.toCollection(
					ctCollection, _ctEngineManager);
				companyId = ctProcess.getCompanyId();
				processId = ctProcess.getCtProcessId();
				dateCreated = ctProcess.getCreateDate();

				percentage = backgroundTaskOptional.flatMap(
					ProcessResourceImpl.this::
						_getServiceBuilderBackgroundTaskOptional
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

				processUser = ProcessUserUtil.toProcessUser(user);

				status = backgroundTaskOptional.map(
					BackgroundTask::getStatusLabel
				).orElse(
					StringPool.BLANK
				);
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