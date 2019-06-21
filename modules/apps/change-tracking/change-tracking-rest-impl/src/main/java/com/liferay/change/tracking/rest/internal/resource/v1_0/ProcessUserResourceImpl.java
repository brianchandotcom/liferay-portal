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

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.rest.constant.v1_0.ProcessType;
import com.liferay.change.tracking.rest.dto.v1_0.ProcessUser;
import com.liferay.change.tracking.rest.internal.dto.factory.v1_0.ProcessUserFactory;
import com.liferay.change.tracking.rest.resource.v1_0.ProcessUserResource;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Máté Thurzó
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/process-user.properties",
	scope = ServiceScope.PROTOTYPE, service = ProcessUserResource.class
)
public class ProcessUserResourceImpl extends BaseProcessUserResourceImpl {

	@Override
	public Page<ProcessUser> getProcessUsersPage(
			Long companyId, String keywords, ProcessType processType,
			Pagination pagination)
		throws Exception {

		List<CTProcess> ctProcesses = null;

		if (ProcessType.PUBLISHED_LATEST == processType) {
			Optional<CTProcess> latestCTProcessOptional =
				_ctEngineManager.getLatestCTProcessOptional(companyId);

			ctProcesses = latestCTProcessOptional.map(
				Collections::singletonList
			).orElse(
				Collections.emptyList()
			);
		}
		else {
			ctProcesses = _ctEngineManager.getCTProcesses(
				companyId, CTConstants.USER_FILTER_ALL, keywords,
				_getQueryDefinition(pagination, processType));
		}

		Stream<CTProcess> stream = ctProcesses.stream();

		List<ProcessUser> processUsers = stream.map(
			CTProcess::getUserId
		).distinct(
		).map(
			_userLocalService::fetchUser
		).filter(
			Objects::nonNull
		).map(
			_processUserFactory::toProcessUser
		).collect(
			Collectors.toList()
		);

		return Page.of(processUsers, pagination, processUsers.size());
	}

	private QueryDefinition _getQueryDefinition(
		Pagination pagination, ProcessType processType) {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setEnd(pagination.getEndPosition());
		queryDefinition.setStart(pagination.getStartPosition());

		int status = 0;

		if (ProcessType.ALL == processType) {
			status = WorkflowConstants.STATUS_ANY;
		}
		else if (ProcessType.FAILED == processType) {
			status = BackgroundTaskConstants.STATUS_FAILED;
		}
		else if (ProcessType.IN_PROGRESS == processType) {
			status = BackgroundTaskConstants.STATUS_IN_PROGRESS;
		}
		else if (ProcessType.PUBLISHED.equals(processType)) {
			status = BackgroundTaskConstants.STATUS_SUCCESSFUL;
		}

		queryDefinition.setStatus(status);

		return queryDefinition;
	}

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private ProcessUserFactory _processUserFactory;

	@Reference
	private UserLocalService _userLocalService;

}