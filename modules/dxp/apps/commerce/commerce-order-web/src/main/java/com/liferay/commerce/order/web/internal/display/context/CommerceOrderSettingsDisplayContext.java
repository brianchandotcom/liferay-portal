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

package com.liferay.commerce.order.web.internal.display.context;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.web.internal.display.context.util.CommerceOrderRequestHelper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;

import java.util.List;

import javax.portlet.RenderRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderSettingsDisplayContext {

	public CommerceOrderSettingsDisplayContext(
		RenderRequest renderRequest,
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService,
		WorkflowDefinitionManager workflowDefinitionManager) {

		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
		_workflowDefinitionManager = workflowDefinitionManager;

		_commerceOrderRequestHelper = new CommerceOrderRequestHelper(
			renderRequest);
	}

	public List<WorkflowDefinition> getActiveWorkflowDefinitions()
		throws PortalException {

		return _workflowDefinitionManager.getActiveWorkflowDefinitions(
			_commerceOrderRequestHelper.getCompanyId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public WorkflowDefinitionLink getWorkflowDefinitionLink(long typePK)
		throws PortalException {

		WorkflowDefinitionLink workflowDefinitionLink = null;

		try {
			workflowDefinitionLink =
				_workflowDefinitionLinkLocalService.getWorkflowDefinitionLink(
					_commerceOrderRequestHelper.getCompanyId(),
					_commerceOrderRequestHelper.getScopeGroupId(),
					CommerceOrder.class.getName(), 0, typePK, true);
		}
		catch (NoSuchWorkflowDefinitionLinkException nswdle) {
		}

		return workflowDefinitionLink;
	}

	private final CommerceOrderRequestHelper _commerceOrderRequestHelper;
	private final WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;
	private final WorkflowDefinitionManager _workflowDefinitionManager;

}