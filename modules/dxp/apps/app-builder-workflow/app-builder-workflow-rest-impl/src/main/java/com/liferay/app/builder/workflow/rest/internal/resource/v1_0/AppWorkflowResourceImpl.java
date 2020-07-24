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
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowRoleAssignment;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowState;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowTask;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowTransition;
import com.liferay.app.builder.workflow.rest.internal.resource.v1_0.helper.AppWorkflowResourceHelper;
import com.liferay.app.builder.workflow.rest.resource.v1_0.AppWorkflowResource;
import com.liferay.app.builder.workflow.service.AppBuilderWorkflowTaskLinkLocalService;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.Node;
import com.liferay.portal.workflow.kaleo.definition.RoleAssignment;
import com.liferay.portal.workflow.kaleo.definition.State;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.definition.Transition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	public AppWorkflow getAppWorkflow(Long appId) throws Exception {
		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.getAppBuilderApp(appId);

		WorkflowDefinition workflowDefinition =
			_appWorkflowResourceHelper.getWorkflowDefinition(appBuilderApp);

		AppBuilderAppVersion latestAppBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				appBuilderApp.getAppBuilderAppId());

		return _toAppWorkflow(
			latestAppBuilderAppVersion,
			_appBuilderWorkflowTaskLinkLocalService.
				getAppBuilderWorkflowTaskLinks(
					appId,
					latestAppBuilderAppVersion.getAppBuilderAppVersionId()),
			appId, _appWorkflowResourceHelper.getDefinition(appBuilderApp),
			workflowDefinition.getWorkflowDefinitionId());
	}

	@Override
	public AppWorkflow postAppWorkflow(Long appId, AppWorkflow appWorkflow)
		throws Exception {

		List<AppBuilderWorkflowTaskLink> appBuilderWorkflowTaskLinks =
			new ArrayList<>();

		AppBuilderAppVersion latestAppBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				appId);

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
				_appBuilderAppLocalService.getAppBuilderApp(appId),
				contextCompany.getCompanyId(), definition,
				contextUser.getUserId());

		_workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(
			contextUser.getUserId(), contextCompany.getCompanyId(), 0,
			ResourceActionsUtil.getCompositeModelName(
				AppBuilderApp.class.getName(), DDLRecord.class.getName()),
			appId, 0, workflowDefinition.getName(),
			workflowDefinition.getVersion());

		return _toAppWorkflow(
			latestAppBuilderAppVersion, appBuilderWorkflowTaskLinks, appId,
			definition, workflowDefinition.getWorkflowDefinitionId());
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

	private AppWorkflow _toAppWorkflow(
		AppBuilderAppVersion appBuilderAppVersion,
		List<AppBuilderWorkflowTaskLink> appBuilderWorkflowTaskLinks,
		Long appWorkflowId, Definition definition, Long workflowDefinitionId) {

		return new AppWorkflow() {
			{
				appId = appWorkflowId;
				appVersion = appBuilderAppVersion.getVersion();
				appWorkflowDefinitionId = workflowDefinitionId;

				setAppWorkflowStates(
					() -> {
						List<State> states = new ArrayList<>();

						states.add(definition.getInitialState());
						states.addAll(definition.getTerminalStates());

						return _toAppWorkflowStates(states);
					});
				setAppWorkflowTasks(
					() -> {
						Map<String, List<AppBuilderWorkflowTaskLink>> map =
							Stream.of(
								appBuilderWorkflowTaskLinks
							).flatMap(
								List::stream
							).collect(
								Collectors.groupingBy(
									AppBuilderWorkflowTaskLink::
										getWorkflowTaskName,
									LinkedHashMap::new, Collectors.toList())
							);

						List<AppWorkflowTask> appWorkflowTasks = transform(
							map.entrySet(),
							entry -> _toAppWorkflowTask(
								entry.getValue(),
								definition.getNode(entry.getKey()),
								entry.getKey()));

						return appWorkflowTasks.toArray(new AppWorkflowTask[0]);
					});
			}
		};
	}

	private AppWorkflowRoleAssignment[] _toAppWorkflowRoleAssignments(
		Task task) {

		return Stream.of(
			task.getAssignments()
		).flatMap(
			Set::stream
		).filter(
			RoleAssignment.class::isInstance
		).map(
			RoleAssignment.class::cast
		).map(
			RoleAssignment::getRoleId
		).map(
			_roleLocalService::fetchRole
		).filter(
			Objects::nonNull
		).map(
			role -> new AppWorkflowRoleAssignment() {
				{
					roleId = role.getRoleId();
					roleName = role.getName();
				}
			}
		).toArray(
			AppWorkflowRoleAssignment[]::new
		);
	}

	private AppWorkflowState[] _toAppWorkflowStates(List<State> states) {
		return Stream.of(
			states
		).flatMap(
			List::stream
		).map(
			state -> new AppWorkflowState() {
				{
					appWorkflowTransitions = _toAppWorkflowTransitions(
						state.getOutgoingTransitionsList());
					initial = state.isInitial();
					name = state.getName();
				}
			}
		).toArray(
			AppWorkflowState[]::new
		);
	}

	private AppWorkflowTask _toAppWorkflowTask(
		List<AppBuilderWorkflowTaskLink> appBuilderWorkflowTaskLinks, Node node,
		String taskName) {

		return new AppWorkflowTask() {
			{
				appWorkflowDataLayoutLinks = transformToArray(
					appBuilderWorkflowTaskLinks,
					appBuilderWorkflowTaskLink ->
						new AppWorkflowDataLayoutLink() {
							{
								dataLayoutId =
									appBuilderWorkflowTaskLink.
										getDdmStructureLayoutId();
								readOnly =
									appBuilderWorkflowTaskLink.getReadOnly();
							}
						},
					AppWorkflowDataLayoutLink.class);
				appWorkflowRoleAssignments = _toAppWorkflowRoleAssignments(
					(Task)node);
				appWorkflowTransitions = _toAppWorkflowTransitions(
					node.getOutgoingTransitionsList());
				name = taskName;
			}
		};
	}

	private AppWorkflowTransition[] _toAppWorkflowTransitions(
		List<Transition> transitions) {

		return Stream.of(
			transitions
		).flatMap(
			List::stream
		).map(
			transition -> new AppWorkflowTransition() {
				{
					name = transition.getName();
					primary = transition.isDefault();

					setTransitionTo(
						() -> {
							Node targetNode = transition.getTargetNode();

							return targetNode.getName();
						});
				}
			}
		).toArray(
			AppWorkflowTransition[]::new
		);
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