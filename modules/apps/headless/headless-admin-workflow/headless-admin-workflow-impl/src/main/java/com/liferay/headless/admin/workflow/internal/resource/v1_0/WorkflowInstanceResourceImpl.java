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

package com.liferay.headless.admin.workflow.internal.resource.v1_0;

import com.liferay.headless.admin.workflow.dto.v1_0.ChangeTransition;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowInstance;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowInstanceSubmit;
import com.liferay.headless.admin.workflow.internal.dto.v1_0.util.ObjectReviewedUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowInstanceResource;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		Map<String, Serializable> workflowContext = Stream.of(
			context.entrySet()
		).flatMap(
			Collection::parallelStream
		).filter(
			entry -> entry.getValue() instanceof Serializable
		).collect(
			Collectors.toMap(
				Map.Entry::getKey, entry -> (Serializable)entry.getValue())
		);

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
				currentNodeNames = Stream.of(
					workflowInstance.getCurrentNodeNames()
				).flatMap(
					List::stream
				).toArray(
					String[]::new
				);
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
			}
		};
	}

	@Reference
	private Language _language;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

}