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

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/s-la.properties",
	scope = ServiceScope.PROTOTYPE, service = SLAResource.class
)
public class SLAResourceImpl extends BaseSLAResourceImpl {

	@Override
	public boolean deleteProcessSla(Long processId, Long slaId)
		throws Exception {

		_workflowMetricsSLADefinitionLocalService.
			deleteWorkflowMetricsSLADefinition(slaId);

		return true;
	}

	@Override
	public SLA getProcessSla(Long processId, Long slaId) throws Exception {
		return _toSLA(
			_workflowMetricsSLADefinitionLocalService.
				getWorkflowMetricsSLADefinition(slaId));
	}

	@Override
	public Page<SLA> getProcessSLAsPage(Long processId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowMetricsSLADefinitionLocalService.
					getWorkflowMetricsSLADefinitions(
						contextCompany.getCompanyId(), processId),
				this::_toSLA),
			pagination,
			_workflowMetricsSLADefinitionLocalService.
				getWorkflowMetricsSLADefinitionsCount(
					contextCompany.getCompanyId(), processId));
	}

	@Override
	public SLA postProcessSlas(Long processId, SLA sla) throws Exception {
		return _toSLA(
			_workflowMetricsSLADefinitionLocalService.
				addWorkflowMetricsSLADefinition(
					sla.getName(), sla.getDescription(), sla.getDuration(),
					processId, sla.getStartNodeNames(), sla.getPauseNodeNames(),
					sla.getStopNodeNames(), _createServiceContext()));
	}

	@Override
	public SLA putProcessSla(Long processId, Long slaId, SLA sla)
		throws Exception {

		return _toSLA(
			_workflowMetricsSLADefinitionLocalService.
				updateWorkflowMetricsSLADefinition(
					slaId, sla.getName(), sla.getDescription(),
					sla.getDuration(), sla.getStartNodeNames(),
					sla.getPauseNodeNames(), sla.getStopNodeNames(),
					_createServiceContext()));
	}

	private ServiceContext _createServiceContext() {
		return new ServiceContext() {
			{
				setCompanyId(contextCompany.getCompanyId());
				setUserId(_getUserId());
			}
		};
	}

	private long _getUserId() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.getUserId();
	}

	private SLA _toSLA(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		return new SLA() {
			{
				setDescription(workflowMetricsSLADefinition.getDescription());
				setDuration(workflowMetricsSLADefinition.getDuration());
				setName(workflowMetricsSLADefinition.getName());
				setPauseNodeNames(
					StringUtil.split(
						workflowMetricsSLADefinition.getPauseNodeNames()));
				setProcessId(workflowMetricsSLADefinition.getProcessId());
				setStartNodeNames(
					StringUtil.split(
						workflowMetricsSLADefinition.getStartNodeNames()));
				setStopNodeNames(
					StringUtil.split(
						workflowMetricsSLADefinition.getStopNodeNames()));
			}
		};
	}

	@Reference
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

}