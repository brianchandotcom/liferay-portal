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

package com.liferay.portlet.portletconfiguration.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.ResourceServingConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryConstants;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

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
			SessionErrors.add(actionRequest, pe.getClass());

			setForward(actionRequest, "portlet.portlet_configuration.error");

			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String settingsScope = ParamUtil.getString(
			actionRequest, "settingsScope");

		PortletPreferences portletPreferences = getPortletPreferences(
			themeDisplay, portlet.getPortletId(), settingsScope);

		actionRequest = ActionUtil.getWrappedActionRequest(
			actionRequest, portletPreferences);

		ConfigurationAction configurationAction = getConfigurationAction(
			portlet);

		if (configurationAction == null) {
			return;
		}

		configurationAction.processAction(
			portletConfig, actionRequest, actionResponse);

		Layout layout = themeDisplay.getLayout();

		PortletLayoutListener portletLayoutListener =
			portlet.getPortletLayoutListenerInstance();

		if (portletLayoutListener != null) {
			portletLayoutListener.onSetup(
				portlet.getPortletId(), layout.getPlid());
		}
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
			SessionErrors.add(renderRequest, pe.getClass());

			return actionMapping.findForward(
				"portlet.portlet_configuration.error");
		}

		renderRequest = ActionUtil.getWrappedRenderRequest(renderRequest, null);

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
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return;
		}

		resourceRequest = ActionUtil.getWrappedResourceRequest(
			resourceRequest, null);

		ResourceServingConfigurationAction resourceServingConfigurationAction =
			(ResourceServingConfigurationAction)getConfigurationAction(portlet);

		if (resourceServingConfigurationAction == null) {
			return;
		}

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

	protected PortletPreferences getPortletPreferences(
		ThemeDisplay themeDisplay, String portletId, String settingsScope) {

		if (Validator.isNull(settingsScope) ||
			settingsScope.equals(
				PortletPreferencesFactoryConstants.
					SETTINGS_SCOPE_PORTLET_INSTANCE)) {

			return null;
		}

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId(),
				themeDisplay.getPlid(), portletId, settingsScope);

		return PortletPreferencesLocalServiceUtil.getPreferences(
			portletPreferencesIds);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditConfigurationAction.class);

}