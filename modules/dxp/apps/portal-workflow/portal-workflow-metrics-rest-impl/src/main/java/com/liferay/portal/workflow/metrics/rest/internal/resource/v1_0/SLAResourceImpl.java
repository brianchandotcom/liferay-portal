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
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/sla.properties",
	scope = ServiceScope.PROTOTYPE, service = SLAResource.class
)
public class SLAResourceImpl extends BaseSLAResourceImpl {

	@Override
	public void deleteSLA(Long slaId) throws Exception {
		_workflowMetricsSLADefinitionLocalService.
			deleteWorkflowMetricsSLADefinition(slaId);
	}

	@Override
	public Page<SLA> getProcessSLAsPage(
			Long processId, Integer status, Pagination pagination)
		throws Exception {

		if (status != null) {
			return Page.of(
				transform(
					_workflowMetricsSLADefinitionLocalService.
						getWorkflowMetricsSLADefinitions(
							contextCompany.getCompanyId(), processId, status,
							pagination.getStartPosition(),
							pagination.getEndPosition(), null),
					this::_toSLA),
				pagination,
				_workflowMetricsSLADefinitionLocalService.
					getWorkflowMetricsSLADefinitionsCount(
						contextCompany.getCompanyId(), processId, status));
		}

		int draftCount =
			_workflowMetricsSLADefinitionLocalService.
				getWorkflowMetricsSLADefinitionsCount(
					contextCompany.getCompanyId(), processId,
					WorkflowConstants.STATUS_DRAFT);

		if (draftCount == 0) {
			return Page.of(
				transform(
					_workflowMetricsSLADefinitionLocalService.
						getWorkflowMetricsSLADefinitions(
							contextCompany.getCompanyId(), processId,
							WorkflowConstants.STATUS_APPROVED,
							pagination.getStartPosition(),
							pagination.getEndPosition(), null),
					this::_toSLA),
				pagination,
				_workflowMetricsSLADefinitionLocalService.
					getWorkflowMetricsSLADefinitionsCount(
						contextCompany.getCompanyId(), processId));
		}

		List<WorkflowMetricsSLADefinition> workflowMetricsSLADefinitions =
			_workflowMetricsSLADefinitionLocalService.
				getWorkflowMetricsSLADefinitions(
					contextCompany.getCompanyId(), processId,
					WorkflowConstants.STATUS_DRAFT,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null);

		if (workflowMetricsSLADefinitions.size() < pagination.getPageSize()) {
			workflowMetricsSLADefinitions = new ArrayList<>(
				workflowMetricsSLADefinitions);

			workflowMetricsSLADefinitions.addAll(
				_workflowMetricsSLADefinitionLocalService.
					getWorkflowMetricsSLADefinitions(
						contextCompany.getCompanyId(), processId,
						WorkflowConstants.STATUS_APPROVED,
						pagination.getStartPosition() +
							workflowMetricsSLADefinitions.size() - draftCount,
						pagination.getEndPosition() - draftCount, null));
		}

		return Page.of(
			transform(workflowMetricsSLADefinitions, this::_toSLA), pagination,
			_workflowMetricsSLADefinitionLocalService.
				getWorkflowMetricsSLADefinitionsCount(
					contextCompany.getCompanyId(), processId));
	}

	@Override
	public SLA getSLA(Long slaId) throws Exception {
		return _toSLA(
			_workflowMetricsSLADefinitionLocalService.
				getWorkflowMetricsSLADefinition(slaId));
	}

	@Override
	public SLA postProcessSLA(Long processId, SLA sla) throws Exception {
		return _toSLA(
			_workflowMetricsSLADefinitionLocalService.
				addWorkflowMetricsSLADefinition(
					sla.getName(), sla.getDescription(), sla.getDuration(),
					processId, sla.getPauseNodeKeys(), sla.getStartNodeKeys(),
					sla.getStopNodeKeys(), _createServiceContext()));
	}

	@Override
	public SLA putSLA(Long slaId, SLA sla) throws Exception {
		return _toSLA(
			_workflowMetricsSLADefinitionLocalService.
				updateWorkflowMetricsSLADefinition(
					slaId, sla.getName(), sla.getDescription(),
					sla.getDuration(), sla.getPauseNodeKeys(),
					sla.getStartNodeKeys(), sla.getStopNodeKeys(),
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
				dateModified = workflowMetricsSLADefinition.getModifiedDate();
				description = workflowMetricsSLADefinition.getDescription();
				duration = workflowMetricsSLADefinition.getDuration();
				id = workflowMetricsSLADefinition.getPrimaryKey();
				name = workflowMetricsSLADefinition.getName();
				pauseNodeKeys = StringUtil.split(
					workflowMetricsSLADefinition.getPauseNodeKeys());
				processId = workflowMetricsSLADefinition.getProcessId();
				startNodeKeys = StringUtil.split(
					workflowMetricsSLADefinition.getStartNodeKeys());
				stopNodeKeys = StringUtil.split(
					workflowMetricsSLADefinition.getStopNodeKeys());
				status = workflowMetricsSLADefinition.getStatus();
			}
		};
	}

	@Reference
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

}