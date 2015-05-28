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

package com.liferay.portlet.portletconfiguration;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.language.AggregateResourceBundle;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.security.permission.PermissionPropagator;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletConfigFactoryUtil;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.portletconfiguration.action.ActionUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Sierra Andrés
 */
public class PortletConfigurationPortlet extends MVCPortlet {

	public void editSharing(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Portlet portlet = null;

		portlet = ActionUtil.getPortlet(actionRequest);

		PortletPreferences portletPreferences =
			ActionUtil.getLayoutPortletSetup(actionRequest, portlet);

		actionRequest = ActionUtil.getWrappedActionRequest(
			actionRequest, portletPreferences);

		updateAnyWebsite(actionRequest, portletPreferences);
		updateFacebook(actionRequest, portletPreferences);
		updateFriends(actionRequest, portletPreferences);
		updateGoogleGadget(actionRequest, portletPreferences);
		updateNetvibes(actionRequest, portletPreferences);

		portletPreferences.store();

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
	public void init(PortletConfig portletConfig) throws PortletException {
		if (portletConfig instanceof PortletConfigImpl) {
			PortletConfigurationPortletPortletConfig
				portletConfigurationPortletPortletConfig =
					new PortletConfigurationPortletPortletConfig(
						(PortletConfigImpl)portletConfig);

			super.init(portletConfigurationPortletPortletConfig);
		}
		else {
			super.init(portletConfig);
		}
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		_portletRequestThreadLocal.set(actionRequest);

		actionRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG, getPortletConfig());

		super.processAction(actionRequest, actionResponse);
	}

	@Override
	public void processEvent(
			EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, PortletException {

		_portletRequestThreadLocal.set(eventRequest);

		eventRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG, getPortletConfig());

		super.processEvent(eventRequest, eventResponse);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		_portletRequestThreadLocal.set(renderRequest);

		renderRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG, getPortletConfig());

		super.render(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		_portletRequestThreadLocal.set(resourceRequest);

		resourceRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG, getPortletConfig());

		super.serveResource(resourceRequest, resourceResponse);
	}

	public void updateAnyWebsite(
			ActionRequest actionRequest, PortletPreferences portletPreferences)
		throws Exception {

		boolean widgetShowAddAppLink = ParamUtil.getBoolean(
			actionRequest, "widgetShowAddAppLink");

		portletPreferences.setValue(
			"lfrWidgetShowAddAppLink", String.valueOf(widgetShowAddAppLink));
	}

	public void updateRolePermissions(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");
		String modelResource = ParamUtil.getString(
			actionRequest, "modelResource");
		long[] roleIds = StringUtil.split(
			ParamUtil.getString(
				actionRequest, "rolesSearchContainerPrimaryKeys"), 0L);

		String selResource = PortletConstants.getRootPortletId(portletResource);

		if (Validator.isNotNull(modelResource)) {
			selResource = modelResource;
		}

		long resourceGroupId = ParamUtil.getLong(
			actionRequest, "resourceGroupId", themeDisplay.getScopeGroupId());
		String resourcePrimKey = ParamUtil.getString(
			actionRequest, "resourcePrimKey");

		Map<Long, String[]> roleIdsToActionIds = new HashMap<>();

		if (ResourceBlockLocalServiceUtil.isSupported(selResource)) {
			for (long roleId : roleIds) {
				List<String> actionIds = getActionIdsList(
					actionRequest, roleId, true);

				roleIdsToActionIds.put(
					roleId, actionIds.toArray(new String[actionIds.size()]));
			}

			ResourceBlockServiceUtil.setIndividualScopePermissions(
				themeDisplay.getCompanyId(), resourceGroupId, selResource,
				GetterUtil.getLong(resourcePrimKey), roleIdsToActionIds);
		}
		else {
			for (long roleId : roleIds) {
				String[] actionIds = getActionIds(actionRequest, roleId, false);

				roleIdsToActionIds.put(roleId, actionIds);
			}

			ResourcePermissionServiceUtil.setIndividualResourcePermissions(
				resourceGroupId, themeDisplay.getCompanyId(), selResource,
				resourcePrimKey, roleIdsToActionIds);
		}

		updateLayoutModifiedDate(selResource, resourcePrimKey);

		if (PropsValues.PERMISSIONS_PROPAGATION_ENABLED) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletResource);

			PermissionPropagator permissionPropagator =
				portlet.getPermissionPropagatorInstance();

			if (permissionPropagator != null) {
				permissionPropagator.propagateRolePermissions(
					actionRequest, modelResource, resourcePrimKey, roleIds);
			}
		}
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			String mvcPath = renderRequest.getParameter("mvcPath");

			Portlet portlet = ActionUtil.getPortlet(renderRequest);

			if (mvcPath.endsWith("edit_configuration.jsp")) {
				renderEditConfiguration(renderRequest, renderResponse, portlet);
			}
			else if (mvcPath.endsWith("edit_public_render_parameters.jsp")) {
				renderEditPublicParameters(renderRequest, portlet);
			}

			renderResponse.setTitle(
				ActionUtil.getTitle(portlet, renderRequest));
		}
		catch (Exception ex) {
			_log.error(ex.getMessage());
			include("/error.jsp", renderRequest, renderResponse);
		}

		super.doDispatch(renderRequest, renderResponse);
	}

	protected String[] getActionIds(
		ActionRequest actionRequest, long roleId, boolean includePreselected) {

		List<String> actionIds = getActionIdsList(
			actionRequest, roleId, includePreselected);

		return actionIds.toArray(new String[actionIds.size()]);
	}

	protected List<String> getActionIdsList(
		ActionRequest actionRequest, long roleId, boolean includePreselected) {

		List<String> actionIds = new ArrayList<>();

		Enumeration<String> enu = actionRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith(roleId + ActionUtil.ACTION)) {
				int pos = name.indexOf(ActionUtil.ACTION);

				String actionId = name.substring(
					pos + ActionUtil.ACTION.length());

				actionIds.add(actionId);
			}
			else if (includePreselected &&
					 name.startsWith(roleId + ActionUtil.PRESELECTED)) {

				int pos = name.indexOf(ActionUtil.PRESELECTED);

				String actionId = name.substring(
					pos + ActionUtil.PRESELECTED.length());

				actionIds.add(actionId);
			}
		}

		return actionIds;
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

	protected void renderEditConfiguration(
			RenderRequest renderRequest, RenderResponse renderResponse,
			Portlet portlet)
		throws Exception {

		PortletConfig portletConfig = (PortletConfig)renderRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		renderRequest = ActionUtil.getWrappedRenderRequest(renderRequest, null);

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
	}

	protected void renderEditPublicParameters(
			RenderRequest renderRequest, Portlet portlet)
		throws Exception {

		PortletPreferences portletPreferences =
			ActionUtil.getLayoutPortletSetup(renderRequest, portlet);

		renderRequest = ActionUtil.getWrappedRenderRequest(
			renderRequest, portletPreferences);

		ActionUtil.getLayoutPublicRenderParameters(renderRequest);

		ActionUtil.getPublicRenderParameterConfigurationList(
			renderRequest, portlet);
	}

	protected void updateFacebook(
			ActionRequest actionRequest, PortletPreferences portletPreferences)
		throws Exception {

		String facebookAPIKey = ParamUtil.getString(
			actionRequest, "facebookAPIKey");
		String facebookCanvasPageURL = ParamUtil.getString(
			actionRequest, "facebookCanvasPageURL");
		boolean facebookShowAddAppLink = ParamUtil.getBoolean(
			actionRequest, "facebookShowAddAppLink");

		portletPreferences.setValue("lfrFacebookApiKey", facebookAPIKey);
		portletPreferences.setValue(
			"lfrFacebookCanvasPageUrl", facebookCanvasPageURL);
		portletPreferences.setValue(
			"lfrFacebookShowAddAppLink",
			String.valueOf(facebookShowAddAppLink));
	}

	protected void updateFriends(
			ActionRequest actionRequest, PortletPreferences portletPreferences)
		throws Exception {

		boolean appShowShareWithFriendsLink = ParamUtil.getBoolean(
			actionRequest, "appShowShareWithFriendsLink");

		portletPreferences.setValue(
			"lfrAppShowShareWithFriendsLink",
			String.valueOf(appShowShareWithFriendsLink));
	}

	protected void updateGoogleGadget(
			ActionRequest actionRequest, PortletPreferences portletPreferences)
		throws Exception {

		boolean iGoogleShowAddAppLink = ParamUtil.getBoolean(
			actionRequest, "iGoogleShowAddAppLink");

		portletPreferences.setValue(
			"lfrIgoogleShowAddAppLink", String.valueOf(iGoogleShowAddAppLink));
	}

	protected void updateLayoutModifiedDate(
			String selResource, String resourcePrimKey)
		throws Exception {

		long plid = 0;

		int pos = resourcePrimKey.indexOf(PortletConstants.LAYOUT_SEPARATOR);

		if (pos != -1) {
			plid = GetterUtil.getLong(resourcePrimKey.substring(0, pos));
		}
		else if (selResource.equals(Layout.class.getName())) {
			plid = GetterUtil.getLong(resourcePrimKey);
		}

		if (plid <= 0) {
			return;
		}

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		if (layout != null) {
			layout.setModifiedDate(new Date());

			LayoutLocalServiceUtil.updateLayout(layout);

			CacheUtil.clearCache(layout.getCompanyId());
		}
	}

	protected void updateNetvibes(
			ActionRequest actionRequest, PortletPreferences portletPreferences)
		throws Exception {

		boolean netvibesShowAddAppLink = ParamUtil.getBoolean(
			actionRequest, "netvibesShowAddAppLink");

		portletPreferences.setValue(
			"lfrNetvibesShowAddAppLink",
			String.valueOf(netvibesShowAddAppLink));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletConfigurationPortlet.class);

	private final ThreadLocal<PortletRequest> _portletRequestThreadLocal =
		new AutoResetThreadLocal<>("_portletRequestThreadLocal");

	private class PortletConfigurationPortletPortletConfig
		extends PortletConfigImpl {

		@Override
		public ResourceBundle getResourceBundle(Locale locale) {
			try {
				PortletRequest portletRequest =
					_portletRequestThreadLocal.get();

				long companyId = PortalUtil.getCompanyId(portletRequest);

				String portletResource = ParamUtil.getString(
					portletRequest, "portletResource");

				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					companyId, portletResource);

				HttpServletRequest httpServletRequest =
					PortalUtil.getHttpServletRequest(portletRequest);

				PortletConfig portletConfig = PortletConfigFactoryUtil.create(
					portlet, httpServletRequest.getServletContext());

				return new AggregateResourceBundle(
					super.getResourceBundle(locale),
					portletConfig.getResourceBundle(locale));
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			return super.getResourceBundle(locale);
		}

		private PortletConfigurationPortletPortletConfig(
			PortletConfigImpl portletConfigImpl) {

			super(
				portletConfigImpl.getPortlet(),
				portletConfigImpl.getPortletContext());
		}

	}

}