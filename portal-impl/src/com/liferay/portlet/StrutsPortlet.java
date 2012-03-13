/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.PortletRequestProcessor;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletException;

/**
 * @author Brian Wing Shun Chan
 */
public class StrutsPortlet extends LiferayPortlet {

	@Override
	public void doAbout(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(WebKeys.PORTLET_STRUTS_ACTION, aboutAction);

		include(renderRequest, renderResponse);
	}

	@Override
	public void doConfig(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(WebKeys.PORTLET_STRUTS_ACTION, configAction);

		include(renderRequest, renderResponse);
	}

	@Override
	public void doEdit(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			renderRequest.setAttribute(
				WebKeys.PORTLET_STRUTS_ACTION, editAction);

			include(renderRequest, renderResponse);
		}
	}

	@Override
	public void doEditDefaults(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			renderRequest.setAttribute(
				WebKeys.PORTLET_STRUTS_ACTION, editDefaultsAction);

			include(renderRequest, renderResponse);
		}
	}

	@Override
	public void doEditGuest(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (renderRequest.getPreferences() == null) {
			super.doEdit(renderRequest, renderResponse);
		}
		else {
			renderRequest.setAttribute(
				WebKeys.PORTLET_STRUTS_ACTION, editGuestAction);

			include(renderRequest, renderResponse);
		}
	}

	@Override
	public void doHelp(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(WebKeys.PORTLET_STRUTS_ACTION, helpAction);

		include(renderRequest, renderResponse);
	}

	@Override
	public void doPreview(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			WebKeys.PORTLET_STRUTS_ACTION, previewAction);

		include(renderRequest, renderResponse);
	}

	@Override
	public void doPrint(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(WebKeys.PORTLET_STRUTS_ACTION, printAction);

		include(renderRequest, renderResponse);
	}

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(WebKeys.PORTLET_STRUTS_ACTION, viewAction);

		include(renderRequest, renderResponse);
	}

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(portletConfig);

		aboutAction = getInitParameter("about-action");
		configAction = getInitParameter("config-action");
		editAction = getInitParameter("edit-action");
		editDefaultsAction = getInitParameter("edit-defaults-action");
		editGuestAction = getInitParameter("edit-guest-action");
		helpAction = getInitParameter("help-action");
		previewAction = getInitParameter("preview-action");
		printAction = getInitParameter("print-action");
		viewAction = getInitParameter("view-action");

		copyRequestParameters = GetterUtil.getBoolean(
			getInitParameter("copy-request-parameters"), true);

		_portletConfig = (PortletConfigImpl)portletConfig;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		String path = actionRequest.getParameter("struts_action");

		if (Validator.isNotNull(path)) {

			// Call processAction of com.liferay.portal.struts.PortletAction

			try {
				PortletRequestProcessor processor =
					_getPortletRequestProcessor();

				processor.process(actionRequest, actionResponse, path);
			}
			catch (ServletException se) {
				throw new PortletException(se);
			}
		}

		if (copyRequestParameters) {
			PortalUtil.copyRequestParameters(actionRequest, actionResponse);
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		resourceRequest.setAttribute(WebKeys.PORTLET_STRUTS_ACTION, viewAction);

		// Call serveResource of com.liferay.portal.struts.PortletAction

		try {
			PortletRequestProcessor processor = _getPortletRequestProcessor();

			processor.process(resourceRequest, resourceResponse);
		}
		catch (ServletException se) {
			throw new PortletException(se);
		}
	}

	protected void include(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		// Call render of com.liferay.portal.struts.PortletAction

		Map<String, Object> strutsAttributes = null;

		if (_portletConfig.isWARFile()) {
			strutsAttributes = StrutsUtil.removeStrutsAttributes(
				getPortletContext(), renderRequest);
		}

		try {
			PortletRequestProcessor processor = _getPortletRequestProcessor();

			processor.process(renderRequest, renderResponse);
		}
		catch (ServletException se) {
			throw new PortletException(se);
		}
		finally {
			if (_portletConfig.isWARFile()) {
				StrutsUtil.setStrutsAttributes(renderRequest, strutsAttributes);
			}
		}

		if (copyRequestParameters) {
			PortalUtil.clearRequestParameters(renderRequest);
		}
	}

	protected String aboutAction;
	protected String configAction;
	protected boolean copyRequestParameters;
	protected String editAction;
	protected String editDefaultsAction;
	protected String editGuestAction;
	protected String helpAction;
	protected String previewAction;
	protected String printAction;
	protected String viewAction;

	private PortletRequestProcessor _getPortletRequestProcessor() {
		PortletContext portletContext = getPortletContext();

		return (PortletRequestProcessor)portletContext.getAttribute(
			WebKeys.PORTLET_STRUTS_PROCESSOR);
	}

	private PortletConfigImpl _portletConfig;

}