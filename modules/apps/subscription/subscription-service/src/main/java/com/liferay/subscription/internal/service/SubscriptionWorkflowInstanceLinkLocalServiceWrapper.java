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

package com.liferay.subscription.internal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalServiceWrapper;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class SubscriptionWorkflowInstanceLinkLocalServiceWrapper
	extends WorkflowInstanceLinkLocalServiceWrapper {

	public SubscriptionWorkflowInstanceLinkLocalServiceWrapper() {
		super(null);
	}

	public SubscriptionWorkflowInstanceLinkLocalServiceWrapper(
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {

		super(workflowInstanceLinkLocalService);
	}

	@Override
	public WorkflowInstanceLink deleteWorkflowInstanceLink(
			WorkflowInstanceLink workflowInstanceLink)
		throws PortalException {

		WorkflowInstanceLink deletedWorkflowInstanceLink =
			super.deleteWorkflowInstanceLink(workflowInstanceLink);

		if (deletedWorkflowInstanceLink == null) {
			return null;
		}

		_subscriptionLocalService.deleteSubscriptions(
			workflowInstanceLink.getCompanyId(),
			WorkflowInstance.class.getName(),
			workflowInstanceLink.getWorkflowInstanceId());

		return deletedWorkflowInstanceLink;
	}

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}