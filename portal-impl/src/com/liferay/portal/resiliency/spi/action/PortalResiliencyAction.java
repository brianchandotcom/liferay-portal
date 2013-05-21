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

package com.liferay.portal.resiliency.spi.action;

import com.liferay.portal.kernel.portlet.ActionResult;
import com.liferay.portal.kernel.portlet.PortletContainer;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.resiliency.spi.agent.SPIAgent;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.resiliency.spi.agent.AgentRequest;
import com.liferay.portal.resiliency.spi.agent.AgentResponse;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.util.WebKeys;

import java.util.List;

import javax.portlet.Event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Shuyang Zhou
 */
public class PortalResiliencyAction extends Action {

	public static final String PORTAL_RESILIENCY_ACTION =
		"PORTAL_RESILIENCY_ACTION";

	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		AgentRequest agentRequest = (AgentRequest)request.getAttribute(
			SPIAgent.SPI_AGENT_REQUEST);
		AgentResponse agentResponse = (AgentResponse)request.getAttribute(
			SPIAgent.SPI_AGENT_RESPONSE);

		HttpSession httpSession = request.getSession();

		agentRequest.populateSessionAttributes(httpSession);

		PrincipalThreadLocal.setPassword(
			(String)httpSession.getAttribute(WebKeys.USER_PASSWORD));

		try {
			_doExecute(request, response);
		}
		finally {
			agentResponse.captureRequestSessionAttributes(request);

			request.setAttribute(PORTAL_RESILIENCY_ACTION, Boolean.TRUE);
		}

		return null;
	}

	private void _doExecute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		PortletContainer portletContainer =
			PortletContainerUtil.getPortletContainer();

		Portlet portlet = (Portlet)request.getAttribute(
			SPIAgent.SPI_AGENT_PORTLET);

		portletContainer.preparePortlet(request, portlet);

		SPIAgent.Lifecycle lifecycle = (SPIAgent.Lifecycle)request.getAttribute(
			SPIAgent.SPI_AGENT_LIFECYCLE);

		switch (lifecycle) {

			case ACTION :

				// Action and event may make a transient change to Layout type
				// setting, this really should be done via regular request
				// attribute. But to avoid massive refactor, spi simply sends
				// back changed layout type setting to mpi.

				Layout requestLayout = (Layout)request.getAttribute(
					WebKeys.LAYOUT);

				String typeSettings = requestLayout.getTypeSettings();

				ActionResult actionResult = portletContainer.processAction(
					request, response, portlet);

				String newTypeSettings = requestLayout.getTypeSettings();

				if (!newTypeSettings.equals(typeSettings)) {
					request.setAttribute(
						SPIAgent.SPI_AGENT_LAYOUT_TYPE_SETTINGS,
						newTypeSettings);
				}

				request.setAttribute(
					SPIAgent.SPI_AGENT_ACTION_RESULT, actionResult);

				break;

			case EVENT :

				requestLayout = (Layout)request.getAttribute(WebKeys.LAYOUT);

				typeSettings = requestLayout.getTypeSettings();

				Layout layout = (Layout)request.getAttribute(
					SPIAgent.SPI_AGENT_LAYOUT);

				Event event = (Event)request.getAttribute(
					SPIAgent.SPI_AGENT_EVENT);

				List<Event> events = portletContainer.processEvent(
					request, response, portlet, layout, event);

				newTypeSettings = requestLayout.getTypeSettings();

				if (!newTypeSettings.equals(typeSettings)) {
					request.setAttribute(
						SPIAgent.SPI_AGENT_LAYOUT_TYPE_SETTINGS,
						newTypeSettings);
				}

				request.setAttribute(SPIAgent.SPI_AGENT_EVENT_RESULT, events);

				break;

			case RENDER :

				portletContainer.render(request, response, portlet);

				break;

			case RESOURCE :

				portletContainer.serveResource(request, response, portlet);

				break;

			default :
				throw new IllegalArgumentException(
					"Unkown lifecycle " + lifecycle);
		}
	}

}