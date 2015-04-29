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

package com.liferay.workflow.definition.web.portlet.action;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.workflow.RequiredWorkflowDefinitionException;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionFileException;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Leonardo Barros
 */
public abstract class BaseWorkflowDefinitionActionCommand extends 
	BaseActionCommand {

	@Override
	protected void doProcessCommand(
		PortletRequest portletRequest, PortletResponse portletResponse) 
		throws Exception {
		
		try {
			doProcessWorkflowDefinitionCommand(portletRequest, portletResponse);
		}
		catch (Exception e) {
			if(e instanceof WorkflowDefinitionFileException) {
				hideDefaultErrorMessage(portletRequest);
				
				SessionErrors.add(portletRequest, e.getClass());
			}
			else if (e instanceof RequiredWorkflowDefinitionException) {
				hideDefaultErrorMessage(portletRequest);
				
				SessionErrors.add(portletRequest, e.getClass());
				
				PortletSession portletSession =
						portletRequest.getPortletSession();

				PortletContext portletContext =
					portletSession.getPortletContext();

				PortletRequestDispatcher portletRequestDispatcher =
					portletContext.getRequestDispatcher("/view.jsp");

				portletRequestDispatcher.include(
					portletRequest, portletResponse);
			}
			else if (e instanceof WorkflowException) {
				hideDefaultErrorMessage(portletRequest);
				
				SessionErrors.add(portletRequest, e.getClass());

				PortletSession portletSession =
					portletRequest.getPortletSession();

				PortletContext portletContext =
					portletSession.getPortletContext();

				PortletRequestDispatcher portletRequestDispatcher =
					portletContext.getRequestDispatcher("/error.jsp");

				portletRequestDispatcher.include(
					portletRequest, portletResponse);
			}
			else {
				throw e;
			}
		}
	}
	
	protected void hideDefaultErrorMessage(PortletRequest portletRequest) {
		SessionMessages.add(
			portletRequest,
			PortalUtil.getPortletId(portletRequest) +
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
	}
	
	protected abstract void doProcessWorkflowDefinitionCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception;

}
