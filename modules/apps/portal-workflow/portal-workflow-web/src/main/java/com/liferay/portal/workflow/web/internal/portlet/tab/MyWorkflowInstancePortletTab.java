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

package com.liferay.portal.workflow.web.internal.portlet.tab;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.portlet.tab.WorkflowPortletTab;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adam Brandizzi
 */
@Component(
	immediate = true,
	property = "portal.workflow.tabs.name=" + WorkflowWebKeys.WORKFLOW_TAB_MY_SUBMISSIONS,
	service = WorkflowPortletTab.class
)
public class MyWorkflowInstancePortletTab extends WorkflowInstancePortletTab {

	@Override
	public String getName() {
		return WorkflowWebKeys.WORKFLOW_TAB_MY_SUBMISSIONS;
	}

	@Override
	public void prepareDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		if (SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses()) ||
			SessionErrors.contains(
				renderRequest, WorkflowException.class.getName())) {

			PortletSession portletSession = renderRequest.getPortletSession();

			PortletContext portletContext = portletSession.getPortletContext();

			PortletRequestDispatcher portletRequestDispatcher =
				portletContext.getRequestDispatcher("/instance/error.jsp");

			try {
				if (SessionErrors.contains(
						renderRequest, PrincipalException.getNestedClasses())) {

					portletRequestDispatcher.forward(
						renderRequest, renderResponse);
				}
				else {
					portletRequestDispatcher.include(
						renderRequest, renderResponse);
				}
			}
			catch (Exception exception) {
				throw new PortletException(exception);
			}
		}

		super.prepareDispatch(renderRequest, renderResponse);
	}

	@Override
	public void prepareProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		long workflowInstanceId = ParamUtil.getLong(
			actionRequest, "workflowInstanceId");

		try {
			_checkWorkflowInstance(actionRequest, workflowInstanceId);
		}
		catch (Exception exception) {
			if (workflowPreprocessorHelper.isSessionErrorException(exception)) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception, exception);
				}

				workflowPreprocessorHelper.hideDefaultErrorMessage(
					actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				throw new PortletException(exception);
			}
		}

		super.prepareProcessAction(actionRequest, actionResponse);
	}

	@Override
	protected void setWorkflowInstanceRenderRequestAttribute(
			RenderRequest renderRequest)
		throws PortalException {

		super.setWorkflowInstanceRenderRequestAttribute(renderRequest);

		WorkflowInstance workflowInstance =
			(WorkflowInstance)renderRequest.getAttribute(
				WebKeys.WORKFLOW_INSTANCE);

		_checkWorkflowInstance(renderRequest, workflowInstance);
	}

	private void _checkWorkflowInstance(
			PortletRequest portletRequest, long workflowInstanceId)
		throws PrincipalException, WorkflowException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		WorkflowInstance workflowInstance =
			WorkflowInstanceManagerUtil.getWorkflowInstance(
				themeDisplay.getCompanyId(), workflowInstanceId);

		_checkWorkflowInstance(portletRequest, workflowInstance);
	}

	private void _checkWorkflowInstance(
			PortletRequest portletRequest, WorkflowInstance workflowInstance)
		throws PrincipalException {

		if (workflowInstance == null) {
			return;
		}

		Map<String, Serializable> workflowContext =
			workflowInstance.getWorkflowContext();

		long companyId = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_COMPANY_ID));
		long userId = GetterUtil.getLong(
			workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		if ((user.getCompanyId() != companyId) ||
			(user.getUserId() != userId)) {

			StringBundler sb = new StringBundler(4);

			sb.append("User ");
			sb.append(userId);
			sb.append(" is not allowed to access workflow instance ");
			sb.append(workflowInstance.getWorkflowInstanceId());

			_log.error(sb.toString());

			throw new PrincipalException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MyWorkflowInstancePortletTab.class);

}