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

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppVersion;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.service.AppBuilderAppVersionLocalService;
import com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflow;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowDataLayoutLink;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowTask;
import com.liferay.app.builder.workflow.rest.internal.dto.v1_0.util.AppWorkflowUtil;
import com.liferay.app.builder.workflow.rest.internal.resource.v1_0.helper.AppWorkflowResourceHelper;
import com.liferay.app.builder.workflow.rest.resource.v1_0.AppWorkflowResource;
import com.liferay.app.builder.workflow.service.AppBuilderWorkflowTaskLinkLocalService;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.workflow.kaleo.definition.Definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/app-workflow.properties",
	scope = ServiceScope.PROTOTYPE, service = AppWorkflowResource.class
)
public class AppWorkflowResourceImpl extends BaseAppWorkflowResourceImpl {

	@Override
	public void deleteAppWorkflow(Long appId) throws Exception {
		WorkflowDefinitionLink workflowDefinitionLink =
			_workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
				contextCompany.getCompanyId(), 0,
				ResourceActionsUtil.getCompositeModelName(
					AppBuilderApp.class.getName(), DDLRecord.class.getName()),
				appId, 0);

		if (workflowDefinitionLink != null) {
			_appBuilderWorkflowTaskLinkLocalService.
				deleteAppBuilderWorkflowTaskLinks(appId);

			_workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(
				workflowDefinitionLink);

			_appWorkflowResourceHelper.undeployWorkflowDefinition(
				appId, contextCompany.getCompanyId(), contextUser.getUserId());
		}
	}

	@Override
	public AppWorkflow getAppWorkflow(Long appId) throws Exception {
		AppBuilderAppVersion latestAppBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				appId);
		WorkflowDefinition latestWorkflowDefinition =
			_appWorkflowResourceHelper.getLatestWorkflowDefinition(
				appId, contextCompany.getCompanyId());

		return AppWorkflowUtil.toAppWorkflow(
			latestAppBuilderAppVersion,
			_appBuilderWorkflowTaskLinkLocalService.
				getAppBuilderWorkflowTaskLinks(
					appId,
					latestAppBuilderAppVersion.getAppBuilderAppVersionId()),
			appId,
			_appWorkflowResourceHelper.getDefinition(
				appId, contextCompany.getCompanyId()),
			_roleLocalService::fetchRole,
			latestWorkflowDefinition.getWorkflowDefinitionId());
	}

	@Override
	public AppWorkflow postAppWorkflow(Long appId, AppWorkflow appWorkflow)
		throws Exception {

		AppBuilderAppVersion latestAppBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				appId);

		List<AppBuilderWorkflowTaskLink> appBuilderWorkflowTaskLinks =
			new ArrayList<>();

		if (Objects.nonNull(appWorkflow.getAppWorkflowTasks())) {
			for (AppWorkflowTask appWorkflowTask :
					appWorkflow.getAppWorkflowTasks()) {

				for (AppWorkflowDataLayoutLink appWorkflowDataLayoutLink :
						appWorkflowTask.getAppWorkflowDataLayoutLinks()) {

					appBuilderWorkflowTaskLinks.add(
						_appBuilderWorkflowTaskLinkLocalService.
							addAppBuilderWorkflowTaskLink(
								contextCompany.getCompanyId(), appId,
								latestAppBuilderAppVersion.
									getAppBuilderAppVersionId(),
								appWorkflowDataLayoutLink.getDataLayoutId(),
								GetterUtil.getBoolean(
									appWorkflowDataLayoutLink.getReadOnly()),
								appWorkflowTask.getName()));
				}
			}
		}

		Definition definition = _appWorkflowResourceHelper.toDefinition(
			_appBuilderAppLocalService.getAppBuilderApp(appId), appWorkflow,
			contextAcceptLanguage.getPreferredLocale());

		WorkflowDefinition workflowDefinition =
			_appWorkflowResourceHelper.deployWorkflowDefinition(
				_appBuilderAppLocalService.getAppBuilderApp(appId), definition,
				contextUser.getUserId());

		_workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(
			contextUser.getUserId(), contextCompany.getCompanyId(), 0,
			ResourceActionsUtil.getCompositeModelName(
				AppBuilderApp.class.getName(), DDLRecord.class.getName()),
			appId, 0, workflowDefinition.getName(),
			workflowDefinition.getVersion());

		return AppWorkflowUtil.toAppWorkflow(
			latestAppBuilderAppVersion, appBuilderWorkflowTaskLinks, appId,
			definition, _roleLocalService::fetchRole,
			workflowDefinition.getWorkflowDefinitionId());
	}

	@Override
	public AppWorkflow putAppWorkflow(Long appId, AppWorkflow appWorkflow)
		throws Exception {

		AppBuilderAppVersion latestAppBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				appId);

		_appBuilderWorkflowTaskLinkLocalService.
			deleteAppBuilderWorkflowTaskLinks(
				appId, latestAppBuilderAppVersion.getAppBuilderAppVersionId());

		_workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(
			contextCompany.getCompanyId(), 0,
			ResourceActionsUtil.getCompositeModelName(
				AppBuilderApp.class.getName(), DDLRecord.class.getName()),
			appId, 0);

		return postAppWorkflow(appId, appWorkflow);
	}

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Reference
	private AppBuilderAppVersionLocalService _appBuilderAppVersionLocalService;

	@Reference
	private AppBuilderWorkflowTaskLinkLocalService
		_appBuilderWorkflowTaskLinkLocalService;

	@Reference
	private AppWorkflowResourceHelper _appWorkflowResourceHelper;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}