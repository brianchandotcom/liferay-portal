/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.workflow;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	property = "model.class.name=com.liferay.commerce.model.CommerceOrder",
	service = WorkflowHandler.class
)
public class CommerceOrderWorkflowHandler
	extends BaseWorkflowHandler<CommerceOrder> {

	@Override
	public String getClassName() {
		return CommerceOrder.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	@Override
	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, long classPK)
		throws PortalException {

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(classPK);

		long typePK = CommerceOrderConstants.TYPE_PK_FULFILLMENT;

		if (commerceOrder.isOpen()) {
			typePK = CommerceOrderConstants.TYPE_PK_APPROVAL;
		}

		return _workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
			commerceOrder.getCompanyId(), commerceOrder.getGroupId(),
			CommerceOrder.class.getName(), 0, typePK, true);
	}

	@Override
	public boolean isVisible() {
		return _VISIBLE;
	}

	@Override
	public void startWorkflowInstance(
			long companyId, long groupId, long userId, long classPK,
			CommerceOrder model, Map<String, Serializable> workflowContext)
		throws PortalException {

		_workflowInstanceLinkLocalService.startWorkflowInstance(
			companyId, groupId, userId, getClassName(), classPK,
			workflowContext, true);
	}

	@Override
	public CommerceOrder updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws PortalException {

		long userId = GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long classPK = GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		return _commerceOrderLocalService.updateStatus(
			userId, classPK, status, workflowContext);
	}

	private static final boolean _VISIBLE = false;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}