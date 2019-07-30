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
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
	public Process getProcess(Long processId) {
		return _toProcess(_ctProcessLocalService.fetchCTProcess(processId));
	}

	@Override
	public Page<Process> getProcessesPage(
		Long companyId, String keywords, ProcessType processType, Long userId,
		Pagination pagination, Sort[] sorts) {

		List<Process> processes = transform(
			_getCTProcesses(
				companyId, keywords, processType, userId, pagination, sorts),
			this::_toProcess);

		long total = _getCTProcessesCount(
			companyId, keywords, processType, userId);

		return Page.of(processes, pagination, total);
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
		Long companyId, String keywords, ProcessType processType, Long userId,
		Pagination pagination, Sort[] sorts) {

		if (ProcessType.PUBLISHED_LATEST.equals(processType)) {
			Optional<CTProcess> latestCTProcessOptional =
				_ctEngineManager.getLatestCTProcessOptional(companyId);

			return latestCTProcessOptional.map(
				Collections::singletonList
			).orElse(
				Collections.emptyList()
			);
		}

		QueryDefinition<CTProcess> queryDefinition =
			SearchUtil.getQueryDefinition(CTProcess.class, pagination, sorts);

		queryDefinition.setStatus(_toStatus(processType));

		return _ctEngineManager.getCTProcesses(
			companyId, userId, keywords, queryDefinition);
	}

	private long _getCTProcessesCount(
		Long companyId, String keywords, ProcessType processType, Long userId) {

		if (ProcessType.PUBLISHED_LATEST.equals(processType)) {
			Optional<CTProcess> latestCTProcessOptional =
				_ctEngineManager.getLatestCTProcessOptional(companyId);

			if (latestCTProcessOptional.isPresent()) {
				return 1L;
			}

			return 0L;
		}

		QueryDefinition<CTProcess> queryDefinition = new QueryDefinition<>();

		queryDefinition.setStatus(_toStatus(processType));

		return _ctProcessLocalService.getCTProcessesCount(
			companyId, userId, keywords, queryDefinition);
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

	private Process _toProcess(CTProcess ctProcess) {
		if (ctProcess == null) {
			return null;
		}

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
				dateCreated = ctProcess.getCreateDate();
				id = ctProcess.getCtProcessId();

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

	private int _toStatus(ProcessType processType) {
		int status = 0;

		if (ProcessType.ALL.equals(processType)) {
			status = WorkflowConstants.STATUS_ANY;
		}
		else if (ProcessType.FAILED.equals(processType)) {
			status = BackgroundTaskConstants.STATUS_FAILED;
		}
		else if (ProcessType.IN_PROGRESS.equals(processType)) {
			status = BackgroundTaskConstants.STATUS_IN_PROGRESS;
		}
		else if (ProcessType.PUBLISHED.equals(processType)) {
			status = BackgroundTaskConstants.STATUS_SUCCESSFUL;
		}

		return status;
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
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTProcessLocalService _ctProcessLocalService;

	@Reference
	private UserLocalService _userLocalService;

}