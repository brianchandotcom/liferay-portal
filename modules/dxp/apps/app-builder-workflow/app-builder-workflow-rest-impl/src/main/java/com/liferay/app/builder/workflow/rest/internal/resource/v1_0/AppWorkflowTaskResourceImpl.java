/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.app.builder.workflow.rest.internal.resource.v1_0;

import com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowTask;
import com.liferay.app.builder.workflow.rest.resource.v1_0.AppWorkflowTaskResource;
import com.liferay.app.builder.workflow.service.AppBuilderWorkflowTaskLinkLocalService;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/app-workflow-task.properties",
	scope = ServiceScope.PROTOTYPE, service = AppWorkflowTaskResource.class
)
public class AppWorkflowTaskResourceImpl
	extends BaseAppWorkflowTaskResourceImpl {

	@Override
	public Page<AppWorkflowTask> getAppWorkflowTasksPage(Long appId)
		throws Exception {

		return _toAppWorkflowTaskPage(
			appId,
			_appBuilderWorkflowTaskLinkLocalService.
				getAppBuilderWorkflowTaskLinks(appId));
	}

	@Override
	public Page<AppWorkflowTask> postAppWorkflowTasks(
			Long appId, AppWorkflowTask[] appWorkflowTasks)
		throws Exception {

		_appBuilderWorkflowTaskLinkLocalService.
			deleteAppBuilderWorkflowTaskLinks(appId);

		List<AppBuilderWorkflowTaskLink> appBuilderWorkflowTaskLinks =
			new ArrayList<>();

		for (AppWorkflowTask appWorkflowTask : appWorkflowTasks) {
			for (Long dataLayoutId : appWorkflowTask.getDataLayoutIds()) {
				appBuilderWorkflowTaskLinks.add(
					_appBuilderWorkflowTaskLinkLocalService.
						addAppBuilderWorkflowTaskLink(
							contextCompany.getCompanyId(), appId, dataLayoutId,
							appWorkflowTask.getName()));
			}
		}

		return _toAppWorkflowTaskPage(
			appId,
			_appBuilderWorkflowTaskLinkLocalService.
				getAppBuilderWorkflowTaskLinks(appId));
	}

	private AppWorkflowTask _toAppWorkflowTask(
		long appId,
		List<AppBuilderWorkflowTaskLink> appBuilderWorkflowTaskLinks,
		String workflowTaskName) {

		AppWorkflowTask appWorkflowTask = new AppWorkflowTask();

		appWorkflowTask.setAppId(appId);
		appWorkflowTask.setDataLayoutIds(
			transformToArray(
				appBuilderWorkflowTaskLinks,
				AppBuilderWorkflowTaskLink::getDdmStructureLayoutId,
				Long.class));
		appWorkflowTask.setName(workflowTaskName);

		return appWorkflowTask;
	}

	private Page<AppWorkflowTask> _toAppWorkflowTaskPage(
		Long appId,
		List<AppBuilderWorkflowTaskLink> appBuilderWorkflowTaskLinks) {

		Map<String, List<AppBuilderWorkflowTaskLink>> map = Stream.of(
			appBuilderWorkflowTaskLinks
		).flatMap(
			List::stream
		).collect(
			Collectors.groupingBy(
				AppBuilderWorkflowTaskLink::getWorkflowTaskName,
				LinkedHashMap::new, Collectors.toList())
		);

		return Page.of(
			transform(
				map.entrySet(),
				entry -> _toAppWorkflowTask(
					appId, entry.getValue(), entry.getKey())));
	}

	@Reference
	private AppBuilderWorkflowTaskLinkLocalService
		_appBuilderWorkflowTaskLinkLocalService;

}