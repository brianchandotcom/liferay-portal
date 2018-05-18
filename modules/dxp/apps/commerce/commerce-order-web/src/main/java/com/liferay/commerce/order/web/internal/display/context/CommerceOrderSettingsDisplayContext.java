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