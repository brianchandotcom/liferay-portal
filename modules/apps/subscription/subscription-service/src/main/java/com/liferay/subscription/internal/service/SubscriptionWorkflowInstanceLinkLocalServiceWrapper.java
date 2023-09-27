/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.subscription.internal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalServiceWrapper;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ServiceWrapper.class)
public class SubscriptionWorkflowInstanceLinkLocalServiceWrapper
	extends WorkflowInstanceLinkLocalServiceWrapper {

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