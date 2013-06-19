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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.ResourceServingConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.portletconfiguration.util.PortletConfigurationUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.ActionRequestWrapper;
import javax.portlet.filter.RenderRequestWrapper;
import javax.portlet.filter.ResourceRequestWrapper;

import javax.servlet.ServletContext;
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
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(actionRequest);
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

		PortletPreferences preferences = getPreferences(
			request, actionRequest.getPreferences());

		actionRequest = new ConfigurationActionRequestWrapper(
			actionRequest, preferences);

		configurationAction.processAction(
			portletConfig, actionRequest, actionResponse);
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(renderRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return mapping.findForward("portlet.portlet_configuration.error");
		}

		renderResponse.setTitle(getTitle(portlet, renderRequest));

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

				PortletPreferences preferences = getPreferences(
					request, renderRequest.getPreferences());

				renderRequest = new ConfigurationRenderRequestWrapper(
					renderRequest, preferences);

				request.setAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST, renderRequest);
			}
			else {
				_log.error("Configuration action returned a null path");
			}
		}

		return mapping.findForward(
			getForward(
				renderRequest,
				"portlet.portlet_configuration.edit_configuration"));
	}

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(resourceRequest);
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

		PortletPreferences preferences = getPreferences(
			request, resourceRequest.getPreferences());

		resourceRequest = new ConfigurationResourceRequestWrapper(
			resourceRequest, preferences);

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

	protected Portlet getPortlet(PortletRequest portletRequest)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		String portletId = ParamUtil.getString(
			portletRequest, "portletResource");

		if (!PortletPermissionUtil.contains(
				permissionChecker, themeDisplay.getLayout(), portletId,
				ActionKeys.CONFIGURATION)) {

			throw new PrincipalException();
		}

		return PortletLocalServiceUtil.getPortletById(companyId, portletId);
	}

	protected PortletPreferences getPreferences(
			HttpServletRequest request, PortletPreferences preferences)
		throws PortalException, SystemException {

		String portletResource = ParamUtil.getString(
			request, "portletResource");

		if (Validator.isNull(portletResource)) {
			return preferences;
		}

		return PortletPreferencesFactoryUtil.getPortletSetup(
			request, portletResource);
	}

	protected String getTitle(Portlet portlet, RenderRequest renderRequest)
		throws Exception {

		ServletContext servletContext =
			(ServletContext)renderRequest.getAttribute(WebKeys.CTX);

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		PortletPreferences portletSetup = getPreferences(
			request, renderRequest.getPreferences());

		String title = PortletConfigurationUtil.getPortletTitle(
			portletSetup, themeDisplay.getLanguageId());

		if (Validator.isNull(title)) {
			title = PortalUtil.getPortletTitle(
				portlet, servletContext, themeDisplay.getLocale());
		}

		return title;
	}

	private static Log _log = LogFactoryUtil.getLog(
		EditConfigurationAction.class);

	private class ConfigurationActionRequestWrapper
		extends ActionRequestWrapper {

		public ConfigurationActionRequestWrapper(
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

	private class ConfigurationRenderRequestWrapper
		extends RenderRequestWrapper {

		public ConfigurationRenderRequestWrapper(
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

	private class ConfigurationResourceRequestWrapper
		extends ResourceRequestWrapper {

		public ConfigurationResourceRequestWrapper(
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