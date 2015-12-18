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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.StringServiceRegistrationMap;
import com.liferay.registry.collections.StringServiceRegistrationMapImpl;
import com.liferay.registry.util.StringPlus;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
@DoPrivileged
public class StrutsPortletAuthTokenWhitelist extends BaseAuthTokenWhitelist {

	public StrutsPortletAuthTokenWhitelist() {
		resetPortletInvocationWhitelistActions();

		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			registry.getFilter(
				"(&(" + PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS + "=*)" +
					"(objectClass=java.lang.Object))"),
			new AuthTokenIgnoreActionsServiceTrackerCustomizer());

		_serviceTracker.open();

		registerPortalProperty();
	}

	public void destroy() {
		for (ServiceRegistration<Object> serviceRegistration :
				_serviceRegistrations.values()) {

			serviceRegistration.unregister();
		}

		_serviceTracker.close();
	}

	@Override
	public Set<String> getPortletCSRFWhitelistActions() {
		return _portletCSRFWhitelistActions;
	}

	@Override
	public Set<String> getPortletInvocationWhitelistActions() {
		return _portletInvocationWhitelistActions;
	}

	@Override
	public boolean isPortletCSRFWhitelisted(
		HttpServletRequest request, Portlet portlet) {

		long companyId = portlet.getCompanyId();
		String portletId = portlet.getPortletId();

		String namespace = PortalUtil.getPortletNamespace(portletId);

		String strutsAction = ParamUtil.getString(
			request, namespace + "struts_action");

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		if (Validator.isNotNull(strutsAction)) {
			Set<String> whitelistActions = getPortletCSRFWhitelistActions();

			if (whitelistActions.contains(strutsAction) &&
				isValidStrutsAction(companyId, rootPortletId, strutsAction)) {

				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isPortletInvocationWhitelisted(
		HttpServletRequest request, Portlet portlet) {

		long companyId = portlet.getCompanyId();
		String portletId = portlet.getPortletId();

		String namespace = PortalUtil.getPortletNamespace(portletId);

		String strutsAction = ParamUtil.getString(
			request, namespace + "struts_action");

		if (Validator.isNull(strutsAction)) {
			strutsAction = ParamUtil.getString(request, "struts_action");
		}

		if (Validator.isNotNull(strutsAction)) {
			Set<String> whitelistActions =
				getPortletInvocationWhitelistActions();

			if (whitelistActions.contains(strutsAction) &&
				isValidStrutsAction(companyId, portletId, strutsAction)) {

				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isPortletURLCSRFWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		String strutsAction = liferayPortletURL.getParameter("struts_action");

		if (Validator.isBlank(strutsAction)) {
			return false;
		}

		Set<String> whitelistActions = getPortletCSRFWhitelistActions();

		if (whitelistActions.contains(strutsAction)) {
			long companyId = 0;

			long plid = liferayPortletURL.getPlid();

			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				companyId = layout.getCompanyId();
			}
			catch (PortalException e) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to load layout " + plid, e);
				}

				return false;
			}

			String portletId = liferayPortletURL.getPortletId();

			String rootPortletId = PortletConstants.getRootPortletId(portletId);

			if (isValidStrutsAction(companyId, rootPortletId, strutsAction)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isPortletURLPortletInvocationWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		String strutsAction = liferayPortletURL.getParameter("struts_action");

		if (Validator.isBlank(strutsAction)) {
			return false;
		}

		Set<String> whitelistActions = getPortletInvocationWhitelistActions();

		if (whitelistActions.contains(strutsAction)) {
			long companyId = 0;

			long plid = liferayPortletURL.getPlid();

			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				companyId = layout.getCompanyId();
			}
			catch (PortalException e) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to load layout " + plid, e);
				}

				return false;
			}

			String portletId = liferayPortletURL.getPortletId();

			if (isValidStrutsAction(companyId, portletId, strutsAction)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Set<String> resetPortletInvocationWhitelistActions() {
		_portletInvocationWhitelistActions = SetUtil.fromArray(
			PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_ACTIONS);
		_portletInvocationWhitelistActions = Collections.unmodifiableSet(
			_portletInvocationWhitelistActions);

		return _portletInvocationWhitelistActions;
	}

	protected boolean isValidStrutsAction(
		long companyId, String portletId, String strutsAction) {

		try {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, portletId);

			if (portlet == null) {
				return false;
			}

			String strutsPath = strutsAction.substring(
				1, strutsAction.lastIndexOf(CharPool.SLASH));

			if (strutsPath.equals(portlet.getStrutsPath()) ||
				strutsPath.equals(portlet.getParentStrutsPath())) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	protected void registerPortalProperty() {
		Registry registry = RegistryUtil.getRegistry();

		for (String authTokenIgnoreAction :
				PropsValues.AUTH_TOKEN_IGNORE_ACTIONS) {

			Map<String, Object> properties = new HashMap<>();

			properties.put(
				PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS, authTokenIgnoreAction);
			properties.put("objectClass", Object.class.getName());

			ServiceRegistration<Object> serviceRegistration =
				registry.registerService(
					Object.class, new Object(), properties);

			_serviceRegistrations.put(
				authTokenIgnoreAction, serviceRegistration);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StrutsPortletAuthTokenWhitelist.class);

	private final Set<String> _portletCSRFWhitelistActions =
		new ConcurrentHashSet<>();
	private Set<String> _portletInvocationWhitelistActions;
	private final StringServiceRegistrationMap<Object> _serviceRegistrations =
		new StringServiceRegistrationMapImpl<>();
	private final ServiceTracker<Object, Object> _serviceTracker;

	private class AuthTokenIgnoreActionsServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, Object> {

		@Override
		public Object addingService(ServiceReference<Object> serviceReference) {
			List<String> authTokenIgnoreActions = StringPlus.asList(
				serviceReference.getProperty(
					PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS));

			_portletCSRFWhitelistActions.addAll(authTokenIgnoreActions);

			Registry registry = RegistryUtil.getRegistry();

			return registry.getService(serviceReference);
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

			List<String> authTokenIgnoreActions = StringPlus.asList(
				serviceReference.getProperty(
					PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS));

			_portletCSRFWhitelistActions.removeAll(authTokenIgnoreActions);

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);
		}

	}

}