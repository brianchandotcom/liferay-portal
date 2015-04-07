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

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.portletconfiguration.util.PublicRenderParameterConfiguration;

import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Alberto Montero
 */
public class EditPublicRenderParametersAction extends PortletAction {

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

		PortletPreferences portletPreferences =
			ActionUtil.getLayoutPortletSetup(actionRequest, portlet);

		actionRequest = ActionUtil.getWrappedActionRequest(
			actionRequest, portletPreferences);

		updatePreferences(actionRequest, portlet);

		if (!SessionErrors.isEmpty(actionRequest)) {
			return;
		}

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		SessionMessages.add(
			actionRequest,
			PortalUtil.getPortletId(actionRequest) +
				SessionMessages.KEY_SUFFIX_REFRESH_PORTLET,
			portletResource);

		SessionMessages.add(
			actionRequest,
			PortalUtil.getPortletId(actionRequest) +
				SessionMessages.KEY_SUFFIX_UPDATED_CONFIGURATION);

		String redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(actionRequest, "redirect"));

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
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

		PortletPreferences portletPreferences =
			ActionUtil.getLayoutPortletSetup(renderRequest, portlet);

		renderRequest = ActionUtil.getWrappedRenderRequest(
			renderRequest, portletPreferences);

		ActionUtil.getLayoutPublicRenderParameters(renderRequest);

		ActionUtil.getPublicRenderParameterConfigurationList(
			renderRequest, portlet);

		renderResponse.setTitle(ActionUtil.getTitle(portlet, renderRequest));

		return actionMapping.findForward(
			getForward(
				renderRequest,
				"portlet.portlet_configuration.edit_public_render_parameters"));
	}

	protected void updatePreferences(
			ActionRequest actionRequest, Portlet portlet)
		throws Exception {

		PortletPreferences portletPreferences = actionRequest.getPreferences();

		Enumeration<String> enu = portletPreferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith(
					PublicRenderParameterConfiguration.IGNORE_PREFIX) ||
				name.startsWith(
					PublicRenderParameterConfiguration.MAPPING_PREFIX)) {

				portletPreferences.reset(name);
			}
		}

		for (PublicRenderParameter publicRenderParameter :
				portlet.getPublicRenderParameters()) {

			String ignoreKey = PublicRenderParameterConfiguration.getIgnoreKey(
				publicRenderParameter);

			boolean ignoreValue = ParamUtil.getBoolean(
				actionRequest, ignoreKey);

			if (ignoreValue) {
				portletPreferences.setValue(
					ignoreKey, String.valueOf(Boolean.TRUE));
			}
			else {
				String mappingKey =
					PublicRenderParameterConfiguration.getMappingKey(
						publicRenderParameter);

				String mappingValue = ParamUtil.getString(
					actionRequest, mappingKey);

				if (Validator.isNotNull(mappingValue)) {
					portletPreferences.setValue(mappingKey, mappingValue);
				}
			}
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			portletPreferences.store();
		}
	}

}