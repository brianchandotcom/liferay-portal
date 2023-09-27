/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.internal.resource.v1_0;

import com.liferay.headless.admin.workflow.dto.v1_0.ChangeTransition;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowInstance;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowInstanceSubmit;
import com.liferay.headless.admin.workflow.internal.dto.v1_0.util.ObjectReviewedUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowInstanceResource;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowNode;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/workflow-instance.properties",
	scope = ServiceScope.PROTOTYPE, service = WorkflowInstanceResource.class
)
@CTAware
public class WorkflowInstanceResourceImpl
	extends BaseWorkflowInstanceResourceImpl {

	@Override
	public void deleteWorkflowInstance(Long workflowInstanceId)
		throws Exception {

		_workflowInstanceManager.deleteWorkflowInstance(
			contextCompany.getCompanyId(), workflowInstanceId);
	}

	@Override
	public WorkflowInstance getWorkflowInstance(Long workflowInstanceId)
		throws Exception {

		try {
			return _toWorkflowInstance(
				_workflowInstanceManager.getWorkflowInstance(
					contextCompany.getCompanyId(), workflowInstanceId));
		}
		catch (WorkflowException workflowException) {
			Throwable throwable = workflowException.getCause();

			if (throwable instanceof NoSuchModelException) {
				throw (NoSuchModelException)throwable;
			}

			throw workflowException;
		}
	}

	@Override
	public Page<WorkflowInstance> getWorkflowInstancesPage(
			String assetClassName, Long assetPrimaryKey, Boolean completed,
			Pagination pagination)
		throws Exception {

		return Page.of(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.VIEW, "getWorkflowInstancesPage",
					WorkflowConstants.RESOURCE_NAME, null)
			).build(),
			transform(
				_workflowInstanceManager.getWorkflowInstances(
					contextCompany.getCompanyId(), contextUser.getUserId(),
					assetClassName, assetPrimaryKey,
					GetterUtil.getBoolean(completed),
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toWorkflowInstance),
			pagination,
			_workflowInstanceManager.getWorkflowInstanceCount(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				assetClassName, assetPrimaryKey,
				GetterUtil.getBoolean(completed)));
	}

	@Override
	public WorkflowInstance postWorkflowInstanceChangeTransition(
			Long workflowInstanceId, ChangeTransition changeTransition)
		throws Exception {

		return _toWorkflowInstance(
			_workflowInstanceManager.signalWorkflowInstance(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				workflowInstanceId, changeTransition.getTransitionName(),
				null));
	}

	@Override
	public WorkflowInstance postWorkflowInstanceSubmit(
			WorkflowInstanceSubmit workflowInstanceSubmit)
		throws Exception {

		return _toWorkflowInstance(
			_workflowInstanceManager.startWorkflowInstance(
				contextCompany.getCompanyId(),
				workflowInstanceSubmit.getSiteId(), contextUser.getUserId(),
				workflowInstanceSubmit.getWorkflowDefinitionName(),
				GetterUtil.getInteger(
					workflowInstanceSubmit.getWorkflowDefinitionVersion()),
				workflowInstanceSubmit.getTransitionName(),
				_toWorkflowContext(
					workflowInstanceSubmit.getContext(),
					workflowInstanceSubmit.getSiteId())));
	}

	private Map<String, Serializable> _toWorkflowContext(
			Map<String, ?> context, long siteId)
		throws Exception {

		Map<String, Serializable> workflowContext = new HashMap<>();

		for (Map.Entry<String, ?> entry : context.entrySet()) {
			Object value = entry.getValue();

			if (value instanceof Serializable) {
				workflowContext.put(entry.getKey(), (Serializable)value);
			}
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			contextHttpServletRequest);

		serviceContext.setScopeGroupId(siteId);

		workflowContext.put(
			WorkflowConstants.CONTEXT_SERVICE_CONTEXT, serviceContext);

		return workflowContext;
	}

	private WorkflowInstance _toWorkflowInstance(
			com.liferay.portal.kernel.workflow.WorkflowInstance
				workflowInstance)
		throws Exception {

		return new WorkflowInstance() {
			{
				completed = workflowInstance.isComplete();
				currentNodeNames = transformToArray(
					workflowInstance.getCurrentWorkflowNodes(),
					WorkflowNode::getName, String.class);
				dateCompletion = workflowInstance.getEndDate();
				dateCreated = workflowInstance.getStartDate();
				id = workflowInstance.getWorkflowInstanceId();
				objectReviewed = ObjectReviewedUtil.toObjectReviewed(
					contextAcceptLanguage.getPreferredLocale(),
					workflowInstance.getWorkflowContext());
				workflowDefinitionName =
					workflowInstance.getWorkflowDefinitionName();
				workflowDefinitionVersion = String.valueOf(
					workflowInstance.getWorkflowDefinitionVersion());

				setActions(
					() -> HashMapBuilder.put(
						"changeTransition",
						addAction(
							ActionKeys.UPDATE,
							workflowInstance.getWorkflowInstanceId(),
							"postWorkflowInstanceChangeTransition",
							_workflowInstanceModelResourcePermission)
					).put(
						"delete",
						addAction(
							ActionKeys.DELETE,
							workflowInstance.getWorkflowInstanceId(),
							"deleteWorkflowInstance",
							_workflowInstanceModelResourcePermission)
					).build());
			}
		};
	}

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.workflow.WorkflowInstance)"
	)
	private ModelResourcePermission<?> _workflowInstanceModelResourcePermission;

}