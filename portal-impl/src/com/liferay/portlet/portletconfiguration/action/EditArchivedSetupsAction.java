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

import com.liferay.portal.NoSuchPortletItemException;
import com.liferay.portal.PortletItemNameException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.settings.ArchivedSettings;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jorge Ferrer
 * @author Raymond Augé
 */
public class EditArchivedSetupsAction extends PortletAction {

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
		}

		actionRequest = ActionUtil.getWrappedActionRequest(actionRequest, null);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.SAVE)) {
				updateSetup(actionRequest, portlet);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreSetup(actionRequest, portlet);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteSetup(actionRequest, portlet);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchPortletItemException ||
				e instanceof PortletItemNameException) {

				SessionErrors.add(actionRequest, e.getClass());

				sendRedirect(actionRequest, actionResponse);
			}
			else if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(
					actionRequest, "portlet.portlet_configuration.error");
			}
			else {
				throw e;
			}
		}

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
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return actionMapping.findForward(
				"portlet.portlet_configuration.error");
		}

		renderRequest = ActionUtil.getWrappedRenderRequest(renderRequest, null);

		renderResponse.setTitle(ActionUtil.getTitle(portlet, renderRequest));

		return actionMapping.findForward(
			getForward(
				renderRequest,
				"portlet.portlet_configuration.edit_archived_setups"));
	}

	protected void deleteSetup(ActionRequest actionRequest, Portlet portlet)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(actionRequest, "name");

		ArchivedSettings archivedSettings =
			SettingsFactoryUtil.getPortletInstanceArchivedSettings(
				themeDisplay.getSiteGroupId(), portlet.getRootPortletId(),
				name);

		archivedSettings.delete();
	}

	protected void restoreSetup(ActionRequest actionRequest, Portlet portlet)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Settings portletInstanceSettings =
			SettingsFactoryUtil.getPortletInstanceSettings(
				themeDisplay.getLayout(), portlet.getPortletId());

		ModifiableSettings portletInstanceModifiableSettings =
			portletInstanceSettings.getModifiableSettings();

		String name = ParamUtil.getString(actionRequest, "name");

		ArchivedSettings archivedSettings =
			SettingsFactoryUtil.getPortletInstanceArchivedSettings(
				themeDisplay.getSiteGroupId(), portlet.getRootPortletId(),
				name);

		portletInstanceModifiableSettings.setValues(archivedSettings);

		portletInstanceModifiableSettings.store();
	}

	protected void updateSetup(ActionRequest actionRequest, Portlet portlet)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(actionRequest, "name");

		ArchivedSettings archivedSettings =
			SettingsFactoryUtil.getPortletInstanceArchivedSettings(
				themeDisplay.getSiteGroupId(), portlet.getRootPortletId(),
				name);

		Settings portletInstanceSettings =
			SettingsFactoryUtil.getPortletInstanceSettings(
				themeDisplay.getLayout(), portlet.getPortletId());

		ModifiableSettings portletInstanceModifiableSettings =
			portletInstanceSettings.getModifiableSettings();

		archivedSettings.setValues(portletInstanceModifiableSettings);

		archivedSettings.store();
	}

}