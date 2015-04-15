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
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.portletconfiguration.util.PortletConfigurationUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jesper Weissglas
 * @author Jorge Ferrer
 * @author Hugo Huijser
 */
public class EditScopeAction extends PortletAction {

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

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.SAVE)) {
			updateScope(actionRequest, portlet);
		}

		if (SessionErrors.isEmpty(actionRequest)) {
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

		renderResponse.setTitle(ActionUtil.getTitle(portlet, renderRequest));

		return actionMapping.findForward(
			getForward(
				renderRequest, "portlet.portlet_configuration.edit_scope"));
	}

	protected Tuple getNewScope(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String scopeType = ParamUtil.getString(actionRequest, "scopeType");

		long scopeGroupId = 0;
		String scopeName = null;

		if (Validator.isNull(scopeType)) {
			scopeGroupId = layout.getGroupId();
		}
		else if (scopeType.equals("company")) {
			scopeGroupId = themeDisplay.getCompanyGroupId();
			scopeName = themeDisplay.translate("global");
		}
		else if (scopeType.equals("layout")) {
			String scopeLayoutUuid = ParamUtil.getString(
				actionRequest, "scopeLayoutUuid");

			Layout scopeLayout =
				LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
					scopeLayoutUuid, layout.getGroupId());

			if (!scopeLayout.hasScopeGroup()) {
				Map<Locale, String> nameMap = new HashMap<>();

				String name = String.valueOf(scopeLayout.getPlid());

				nameMap.put(LocaleUtil.getDefault(), name);

				GroupLocalServiceUtil.addGroup(
					themeDisplay.getUserId(),
					GroupConstants.DEFAULT_PARENT_GROUP_ID,
					Layout.class.getName(), scopeLayout.getPlid(),
					GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, null, 0,
					true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null,
					false, true, null);
			}

			scopeGroupId = scopeLayout.getGroupId();
			scopeName = scopeLayout.getName(themeDisplay.getLocale());
		}
		else {
			throw new IllegalArgumentException(
				"Scope type " + scopeType + " is invalid");
		}

		return new Tuple(scopeGroupId, scopeName);
	}

	protected String getOldScopeName(
			ActionRequest actionRequest, Portlet portlet)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletPreferences portletPreferences = actionRequest.getPreferences();

		String scopeType = GetterUtil.getString(
			portletPreferences.getValue("lfrScopeType", null));

		if (Validator.isNull(scopeType)) {
			return null;
		}

		String scopeName = null;

		if (scopeType.equals("company")) {
			scopeName = themeDisplay.translate("global");
		}
		else if (scopeType.equals("layout")) {
			String scopeLayoutUuid = GetterUtil.getString(
				portletPreferences.getValue("lfrScopeLayoutUuid", null));

			Layout scopeLayout =
				LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
					scopeLayoutUuid, layout.getGroupId());

			if (scopeLayout != null) {
				scopeName = scopeLayout.getName(themeDisplay.getLocale());
			}
		}
		else {
			throw new IllegalArgumentException(
				"Scope type " + scopeType + " is invalid");
		}

		return scopeName;
	}

	protected String getPortletTitle(
		PortletRequest portletRequest, Portlet portlet,
		PortletPreferences portletPreferences) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletTitle = PortletConfigurationUtil.getPortletTitle(
			portletPreferences, themeDisplay.getLanguageId());

		if (Validator.isNull(portletTitle)) {
			ServletContext servletContext =
				(ServletContext)portletRequest.getAttribute(WebKeys.CTX);

			portletTitle = PortalUtil.getPortletTitle(
				portlet, servletContext, themeDisplay.getLocale());
		}

		return portletTitle;
	}

	protected void updateScope(ActionRequest actionRequest, Portlet portlet)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String oldScopeName = getOldScopeName(actionRequest, portlet);

		PortletPreferences portletPreferences = actionRequest.getPreferences();

		String scopeType = ParamUtil.getString(actionRequest, "scopeType");

		portletPreferences.setValue("lfrScopeType", scopeType);

		String scopeLayoutUuid = ParamUtil.getString(
			actionRequest, "scopeLayoutUuid");

		if (!scopeType.equals("layout")) {
			scopeLayoutUuid = StringPool.BLANK;
		}

		portletPreferences.setValue("lfrScopeLayoutUuid", scopeLayoutUuid);

		String portletTitle = getPortletTitle(
			actionRequest, portlet, portletPreferences);

		Tuple newScopeTuple = getNewScope(actionRequest);

		String newScopeName = (String)newScopeTuple.getObject(1);

		String newPortletTitle = PortalUtil.getNewPortletTitle(
			portletTitle, oldScopeName, newScopeName);

		if (!newPortletTitle.equals(portletTitle)) {
			portletPreferences.setValue(
				"portletSetupTitle_" + themeDisplay.getLanguageId(),
				newPortletTitle);
			portletPreferences.setValue(
				"portletSetupUseCustomTitle", Boolean.TRUE.toString());
		}

		portletPreferences.store();
	}

}