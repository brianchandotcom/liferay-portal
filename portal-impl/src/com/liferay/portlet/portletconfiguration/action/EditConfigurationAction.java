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

package com.liferay.portlet.portletconfiguration.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.ResourceServingConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.ActionRequestWrapper;
import javax.portlet.filter.RenderRequestWrapper;
import javax.portlet.filter.ResourceRequestWrapper;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class EditConfigurationAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = ActionUtil.getPortlet(actionRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				actionRequest, PrincipalException.class.getName());

			setForward(actionRequest, "portlet.portlet_configuration.error");

			return;
		}

		ConfigurationAction configurationAction = getConfigurationAction(
			portlet);

		if (configurationAction == null) {
			return;
		}

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		PortletPreferences portletPreferences =
			ActionUtil.getPortletPreferences(
				request, actionRequest.getPreferences());

		actionRequest = new ConfigurationActionRequest(
			actionRequest, portletPreferences);

		configurationAction.processAction(
			portletConfig, actionRequest, actionResponse);
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = ActionUtil.getPortlet(renderRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return actionMapping.findForward(
				"portlet.portlet_configuration.error");
		}

		renderResponse.setTitle(ActionUtil.getTitle(portlet, renderRequest));

		ConfigurationAction configurationAction = getConfigurationAction(
			portlet);

		if (configurationAction != null) {
			String path = configurationAction.render(
				portletConfig, renderRequest, renderResponse);

			if (_log.isDebugEnabled()) {
				_log.debug("Configuration action returned render path " + path);
			}

			if (Validator.isNotNull(path)) {
				renderRequest.setAttribute(
					WebKeys.CONFIGURATION_ACTION_PATH, path);

				HttpServletRequest request = PortalUtil.getHttpServletRequest(
					renderRequest);

				PortletPreferences portletPreferences =
					ActionUtil.getPortletPreferences(
						request, renderRequest.getPreferences());

				renderRequest = new ConfigurationRenderRequest(
					renderRequest, portletPreferences);

				request.setAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST, renderRequest);
			}
			else {
				_log.error("Configuration action returned a null path");
			}
		}

		return actionMapping.findForward(
			getForward(
				renderRequest,
				"portlet.portlet_configuration.edit_configuration"));
	}

	@Override
	public void serveResource(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = ActionUtil.getPortlet(resourceRequest);
		}
		catch (PrincipalException pe) {
			return;
		}

		ResourceServingConfigurationAction resourceServingConfigurationAction =
			(ResourceServingConfigurationAction)getConfigurationAction(portlet);

		if (resourceServingConfigurationAction == null) {
			return;
		}

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			resourceRequest);

		PortletPreferences portletPreferences =
			ActionUtil.getPortletPreferences(
				request, resourceRequest.getPreferences());

		resourceRequest = new ConfigurationResourceRequest(
			resourceRequest, portletPreferences);

		resourceServingConfigurationAction.serveResource(
			portletConfig, resourceRequest, resourceResponse);
	}

	protected ConfigurationAction getConfigurationAction(Portlet portlet)
		throws Exception {

		if (portlet == null) {
			return null;
		}

		ConfigurationAction configurationAction =
			portlet.getConfigurationActionInstance();

		if (configurationAction == null) {
			_log.error(
				"Configuration action for portlet " + portlet.getPortletId() +
					" is null");
		}

		return configurationAction;
	}

	private static Log _log = LogFactoryUtil.getLog(
		EditConfigurationAction.class);

	private class ConfigurationActionRequest extends ActionRequestWrapper {

		public ConfigurationActionRequest(
			ActionRequest actionRequest,
			PortletPreferences portletPreferences) {

			super(actionRequest);

			_portletPreferences = portletPreferences;
		}

		@Override
		public PortletPreferences getPreferences() {
			return _portletPreferences;
		}

		private PortletPreferences _portletPreferences;

	}

	private class ConfigurationRenderRequest extends RenderRequestWrapper {

		public ConfigurationRenderRequest(
			RenderRequest renderRequest,
			PortletPreferences portletPreferences) {

			super(renderRequest);

			_portletPreferences = portletPreferences;
		}

		@Override
		public PortletPreferences getPreferences() {
			return _portletPreferences;
		}

		private PortletPreferences _portletPreferences;

	}

	private class ConfigurationResourceRequest extends ResourceRequestWrapper {

		public ConfigurationResourceRequest(
			ResourceRequest resourceRequest,
			PortletPreferences portletPreferences) {

			super(resourceRequest);

			_portletPreferences = portletPreferences;
		}

		@Override
		public PortletPreferences getPreferences() {
			return _portletPreferences;
		}

		private PortletPreferences _portletPreferences;

	}

}