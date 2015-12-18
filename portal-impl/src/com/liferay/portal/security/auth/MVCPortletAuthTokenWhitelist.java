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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.util.StringPlus;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
@DoPrivileged
public class MVCPortletAuthTokenWhitelist extends BaseAuthTokenWhitelist {

	public MVCPortletAuthTokenWhitelist() {
		_csrfTokenServiceTracker = trackWhitelistServices(
			"auth.token.ignore.mvc.action", MVCActionCommand.class,
			_portletCSRFWhitelistActions);

		_portletInvocationTokenActionServiceTracker = trackWhitelistServices(
			"portlet.add.default.resource.check.whitelist.mvc.action",
			MVCActionCommand.class, _portletInvocationActionWhitelistActions);

		_portletInvocationTokenRenderServiceTracker = trackWhitelistServices(
			"portlet.add.default.resource.check.whitelist.mvc.action",
			MVCRenderCommand.class, _portletInvocationRenderWhitelistActions);

		_portletInvocationTokenResourceServiceTracker = trackWhitelistServices(
			"portlet.add.default.resource.check.whitelist.mvc.action",
			MVCResourceCommand.class,
			_portletInvocationResourceWhitelistActions);
	}

	public void destroy() {
		_csrfTokenServiceTracker.close();

		_portletInvocationTokenActionServiceTracker.close();
		_portletInvocationTokenRenderServiceTracker.close();
		_portletInvocationTokenResourceServiceTracker.close();
	}

	@Override
	public boolean isPortletCSRFWhitelisted(
		HttpServletRequest request, Portlet portlet) {

		String portletId = portlet.getPortletId();

		String namespace = PortalUtil.getPortletNamespace(portletId);

		String[] actionNameParameterValues = ParamUtil.getParameterValues(
			request, namespace + ActionRequest.ACTION_NAME);

		String actionNameParameterValue = StringUtil.merge(
			actionNameParameterValues);

		String[] actionNames = StringUtil.split(actionNameParameterValue);

		if (actionNames.length > 0) {
			String rootPortletId = PortletConstants.getRootPortletId(portletId);

			for (String actionName : actionNames) {
				if (!_portletCSRFWhitelistActions.contains(
						getWhitelistValue(rootPortletId, actionName))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public boolean isPortletInvocationWhitelisted(
		HttpServletRequest request, Portlet portlet) {

		String portletId = portlet.getPortletId();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isLifecycleAction()) {
			return isActionInvocationWhitelisted(request, portletId);
		}

		else if (themeDisplay.isLifecycleRender()) {
			return isRenderInvocationWhitelisted(request, portletId);
		}

		else if (themeDisplay.isLifecycleResource()) {
			return isResourceInvocationWhitelisted(request, portletId);
		}

		return false;
	}

	@Override
	public boolean isPortletURLCSRFWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		String portletId = liferayPortletURL.getPortletId();

		Map<String, String[]> parameterMap =
			liferayPortletURL.getParameterMap();

		String[] actionNameParameterValues = parameterMap.get(
			ActionRequest.ACTION_NAME);

		String actionNameParameterValue = StringUtil.merge(
			actionNameParameterValues);

		String[] actionNames = StringUtil.split(actionNameParameterValue);

		if (actionNames.length > 0) {
			String rootPortletId = PortletConstants.getRootPortletId(portletId);

			for (String actionName : actionNames) {
				if (!_portletCSRFWhitelistActions.contains(
						getWhitelistValue(rootPortletId, actionName))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public boolean isPortletURLPortletInvocationWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		String portletId = liferayPortletURL.getPortletId();

		String lifecycle = liferayPortletURL.getLifecycle();

		if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			return isActionInvocationWhitelisted(liferayPortletURL, portletId);
		}

		else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			return isRenderInvocationWhitelisted(liferayPortletURL, portletId);
		}

		else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			return isResourceInvocationWhitelisted(
				liferayPortletURL, portletId);
		}

		return false;
	}

	protected String getWhitelistValue(
		String portletName, String whitelistAction) {

		return portletName + StringPool.POUND + whitelistAction;
	}

	protected boolean isActionInvocationWhitelisted(
		HttpServletRequest request, String portletId) {

		String namespace = PortalUtil.getPortletNamespace(portletId);

		String[] actionNameParameterValues = ParamUtil.getParameterValues(
			request, namespace + ActionRequest.ACTION_NAME);

		String actionNameParameterValue = StringUtil.merge(
			actionNameParameterValues);

		String[] mvcActionCommandNames = StringUtil.split(
			actionNameParameterValue);

		if (mvcActionCommandNames.length > 0) {
			String rootPortletId = PortletConstants.getRootPortletId(portletId);

			for (String mvcActionCommandName : mvcActionCommandNames) {
				if (!_portletInvocationActionWhitelistActions.contains(
						getWhitelistValue(
							rootPortletId, mvcActionCommandName))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected boolean isActionInvocationWhitelisted(
		LiferayPortletURL liferayPortletURL, String portletId) {

		Map<String, String[]> parameterMap =
			liferayPortletURL.getParameterMap();

		String[] actionNameParameterValues = parameterMap.get(
			ActionRequest.ACTION_NAME);

		String actionNameParameterValue = StringUtil.merge(
			actionNameParameterValues);

		String[] mvcActionCommandNames = StringUtil.split(
			actionNameParameterValue);

		if (mvcActionCommandNames.length > 0) {
			String rootPortletId = PortletConstants.getRootPortletId(portletId);

			for (String mvcActionCommandName : mvcActionCommandNames) {
				if (!_portletInvocationActionWhitelistActions.contains(
						getWhitelistValue(
							rootPortletId, mvcActionCommandName))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected boolean isRenderInvocationWhitelisted(
		HttpServletRequest request, String portletId) {

		String namespace = PortalUtil.getPortletNamespace(portletId);

		String mvcRenderCommandName = ParamUtil.getString(
			request, namespace + "mvcRenderCommandName");

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		if (_portletInvocationRenderWhitelistActions.contains(
				getWhitelistValue(rootPortletId, mvcRenderCommandName))) {

			return true;
		}

		return false;
	}

	protected boolean isRenderInvocationWhitelisted(
		LiferayPortletURL liferayPortletURL, String portletId) {

		String mvcRenderCommandName = liferayPortletURL.getParameter(
			"mvcRenderCommandName");

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		if (_portletInvocationRenderWhitelistActions.contains(
				getWhitelistValue(rootPortletId, mvcRenderCommandName))) {

			return true;
		}

		return false;
	}

	protected boolean isResourceInvocationWhitelisted(
		HttpServletRequest request, String portletId) {

		String ppid = ParamUtil.getString(request, "p_p_id");

		if (!portletId.equals(ppid)) {
			return false;
		}

		String mvcResourceCommandName = ParamUtil.getString(
			request, "p_p_resource_id");

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		if (_portletInvocationResourceWhitelistActions.contains(
				getWhitelistValue(rootPortletId, mvcResourceCommandName))) {

			return true;
		}

		return false;
	}

	protected boolean isResourceInvocationWhitelisted(
		LiferayPortletURL liferayPortletURL, String portletId) {

		String mvcResourceCommandName = liferayPortletURL.getResourceID();

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		if (_portletInvocationResourceWhitelistActions.contains(
				getWhitelistValue(rootPortletId, mvcResourceCommandName))) {

			return true;
		}

		return false;
	}

	protected ServiceTracker<Object, Object> trackWhitelistServices(
		String whitelistName, Class serviceClass, Set whiteList) {

		Registry registry = RegistryUtil.getRegistry();

		ServiceTracker<Object, Object> serviceTracker = registry.trackServices(
			registry.getFilter(
				"(&(&(" + whitelistName + "=*)(javax.portlet.name=*))" +
				"(objectClass=" + serviceClass.getName() + "))"),
			new TokenWhitelistTrackerCustomizer(whitelistName, whiteList));

		serviceTracker.open();

		return serviceTracker;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MVCPortletAuthTokenWhitelist.class);

	private final ServiceTracker<Object, Object> _csrfTokenServiceTracker;
	private final Set<String> _portletCSRFWhitelistActions =
		new ConcurrentHashSet<>();
	private final Set<String> _portletInvocationActionWhitelistActions =
		new ConcurrentHashSet<>();
	private final Set<String> _portletInvocationRenderWhitelistActions =
		new ConcurrentHashSet<>();
	private final Set<String> _portletInvocationResourceWhitelistActions =
		new ConcurrentHashSet<>();
	private final ServiceTracker<Object, Object>
		_portletInvocationTokenActionServiceTracker;
	private final ServiceTracker<Object, Object>
		_portletInvocationTokenRenderServiceTracker;
	private final ServiceTracker<Object, Object>
		_portletInvocationTokenResourceServiceTracker;

	private class TokenWhitelistTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, Object> {

		public TokenWhitelistTrackerCustomizer(
			String whitelistName, Set whitelist) {

			_whitelistName = whitelistName;
			_whitelist = whitelist;
		}

		@Override
		public Object addingService(ServiceReference<Object> serviceReference) {
			List<String> whitelistActions = StringPlus.asList(
				serviceReference.getProperty("mvc.command.name"));

			List<String> portletNames = StringPlus.asList(
				serviceReference.getProperty("javax.portlet.name"));

			for (String portletName : portletNames) {
				for (String whitelistAction : whitelistActions) {
					_whitelist.add(
						getWhitelistValue(portletName, whitelistAction));
				}
			}

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference, Object object) {

			removedService(serviceReference, object);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference, Object object) {

			List<String> whitelistActions = StringPlus.asList(
				serviceReference.getProperty("mvc.command.name"));

			List<String> portletNames = StringPlus.asList(
				serviceReference.getProperty("javax.portlet.name"));

			for (String portletName : portletNames) {
				for (String whitelistAction : whitelistActions) {
					_whitelist.remove(
						getWhitelistValue(portletName, whitelistAction));
				}
			}
		}

		private final Set _whitelist;
		private final String _whitelistName;

	}

}