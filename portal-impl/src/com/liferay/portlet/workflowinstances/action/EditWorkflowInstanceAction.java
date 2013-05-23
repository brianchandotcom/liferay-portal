/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.workflowinstances.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Marcellus Tavares
 */
public class EditWorkflowInstanceAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			String redirect = null;

			if (cmd.equals(Constants.DELETE)) {
				redirect = deleteInstance(actionRequest);
			}
			else if (cmd.equals(Constants.SIGNAL)) {
				signalInstance(actionRequest);
			}

			if (redirect == null) {
				redirect = ParamUtil.getString(actionRequest, "redirect");
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException ||
				e instanceof WorkflowException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.workflow_instances.error");
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getWorkflowInstance(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof WorkflowException) {
				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.workflow_instances.error");
			}
			else {
				throw e;
			}
		}

		String forward = getForward(
			renderRequest, "portlet.workflow_instances.edit_workflow_instance");

		return mapping.findForward(forward);
	}

	protected String deleteInstance(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long workflowInstanceId = ParamUtil.getLong(
			actionRequest, "workflowInstanceId");

		WorkflowInstance workflowInstance =
			WorkflowInstanceManagerUtil.getWorkflowInstance(
				themeDisplay.getCompanyId(), workflowInstanceId);

		Map<String, Serializable> workflowContext =
			workflowInstance.getWorkflowContext();

		long companyId = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_COMPANY_ID));
		long userId = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long groupId = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_GROUP_ID));
		String className = GetterUtil.getString(
			workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME));
		long classPK = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		WorkflowHandler workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

		workflowHandler.updateStatus(
			WorkflowConstants.STATUS_DRAFT, workflowContext);

		WorkflowInstanceLinkLocalServiceUtil.deleteWorkflowInstanceLink(
			companyId, groupId, className, classPK);

		Layout layout = themeDisplay.getLayout();

		Group layoutGroup = layout.getGroup();

		if (layoutGroup.isControlPanel() &&
			(WorkflowInstanceManagerUtil.getWorkflowInstanceCount(
				companyId, userId, null, null, null) == 0)) {

			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			String portletId = PortalUtil.getPortletId(actionRequest);

			if (!PortletPermissionUtil.hasControlPanelAccessPermission(
					permissionChecker, groupId, portletId)) {

				return themeDisplay.getURLControlPanel();
			}
		}

		return null;
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected void signalInstance(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long workflowInstanceId = ParamUtil.getLong(
			actionRequest, "workflowInstanceId");

		String transitionName = ParamUtil.getString(
			actionRequest, "transitionName");

		WorkflowInstanceManagerUtil.signalWorkflowInstance(
			themeDisplay.getCompanyId(), themeDisplay.getUserId(),
			workflowInstanceId, transitionName, null);
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}