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

package com.liferay.portal.workflow.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.workflow.web.internal.constants.WorkflowPortletKeys;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
		"javax.portlet.name=" + WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW_INSTANCE,
		"javax.portlet.name=" + WorkflowPortletKeys.SITE_ADMINISTRATION_WORKFLOW,
		"javax.portlet.name=" + WorkflowPortletKeys.USER_WORKFLOW,
		"mvc.command.name=deleteWorkflowInstance"
	},
	service = MVCActionCommand.class
)
public class DeleteWorkflowInstanceMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteWorkflowInstance(
			Map<String, Serializable> workflowContext)
		throws PortalException {

		long companyId = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_COMPANY_ID));
		long groupId = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_GROUP_ID));
		String className = GetterUtil.getString(
			workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME));
		long classPK = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
			companyId, groupId, className, classPK);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			WorkflowInstance workflowInstance = getWorkflowInstance(
				actionRequest);

			Map<String, Serializable> workflowContext =
				workflowInstance.getWorkflowContext();

			validateUser(workflowContext);

			updateEntryStatus(workflowContext);

			deleteWorkflowInstance(workflowContext);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException ||
				e instanceof WorkflowException) {

				SessionErrors.add(actionRequest, e.getClass());

				PortletSession portletSession =
					actionRequest.getPortletSession();

				PortletContext portletContext =
					portletSession.getPortletContext();

				PortletRequestDispatcher portletRequestDispatcher =
					portletContext.getRequestDispatcher("/error.jsp");

				portletRequestDispatcher.include(actionRequest, actionResponse);
			}
			else {
				throw e;
			}
		}
	}

	protected WorkflowInstance getWorkflowInstance(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long workflowInstanceId = ParamUtil.getLong(
			actionRequest, "workflowInstanceId");

		return WorkflowInstanceManagerUtil.getWorkflowInstance(
			themeDisplay.getCompanyId(), workflowInstanceId);
	}

	protected void updateEntryStatus(Map<String, Serializable> workflowContext)
		throws PortalException {

		String className = GetterUtil.getString(
			workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME));

		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

		workflowHandler.updateStatus(
			WorkflowConstants.STATUS_DRAFT, workflowContext);
	}

	protected void validateUser(Map<String, Serializable> workflowContext)
		throws PortalException {

		long companyId = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_COMPANY_ID));
		long userId = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));

		long validUserId = _portal.getValidUserId(companyId, userId);

		workflowContext.put(
			WorkflowConstants.CONTEXT_USER_ID, String.valueOf(validUserId));
	}

	@Reference
	private Portal _portal;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}