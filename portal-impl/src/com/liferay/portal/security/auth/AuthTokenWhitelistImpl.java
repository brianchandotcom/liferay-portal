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
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.StringServiceRegistrationMap;
import com.liferay.registry.util.StringPlus;
import com.liferay.util.Encryptor;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Raymond Augé
 * @author Tomas Polesovsky
 */
@DoPrivileged
public class AuthTokenWhitelistImpl implements AuthTokenWhitelist {

	public AuthTokenWhitelistImpl() {
		resetOriginCSRFWhitelist();
		resetPortletCSRFWhitelist();
		resetPortletInvocationWhitelist();
		resetPortletInvocationWhitelistActions();

		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			registry.getFilter(
				"(&(" + PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS+"=*)" +
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
	public Set<String> getOriginCSRFWhitelist() {
		return _originCSRFWhitelist;
	}

	@Override
	public Set<String> getPortletCSRFWhitelist() {
		return _portletCSRFWhitelist;
	}

	@Override
	public Set<String> getPortletCSRFWhitelistActions() {
		return _portletCSRFWhitelistActions;
	}

	@Override
	public Set<String> getPortletInvocationWhitelist() {
		return _portletInvocationWhitelist;
	}

	@Override
	public Set<String> getPortletInvocationWhitelistActions() {
		return _portletInvocationWhitelistActions;
	}

	@Override
	public boolean isOriginCSRFWhitelisted(long companyId, String origin) {
		Set<String> whitelist = getOriginCSRFWhitelist();

		for (String whitelistedOrigins : whitelist) {
			if (origin.startsWith(whitelistedOrigins)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isPortletCSRFWhitelisted(
		long companyId, String portletId, String strutsAction) {

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		Set<String> whitelist = getPortletCSRFWhitelist();

		if (whitelist.contains(rootPortletId)) {
			return true;
		}

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
		long companyId, String portletId, String strutsAction) {

		Set<String> whitelist = getPortletInvocationWhitelist();

		if (whitelist.contains(portletId)) {
			return true;
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
	public boolean isValidSharedSecret(String sharedSecret) {
		if (Validator.isNull(sharedSecret)) {
			return false;
		}

		if (Validator.isNull(PropsValues.AUTH_TOKEN_SHARED_SECRET)) {
			return false;
		}

		return sharedSecret.equals(
			Encryptor.digest(PropsValues.AUTH_TOKEN_SHARED_SECRET));
	}

	@Override
	public Set<String> resetOriginCSRFWhitelist() {
		_originCSRFWhitelist = SetUtil.fromArray(
			PropsValues.AUTH_TOKEN_IGNORE_ORIGINS);
		_originCSRFWhitelist = Collections.unmodifiableSet(
			_originCSRFWhitelist);

		return _originCSRFWhitelist;
	}

	@Override
	public Set<String> resetPortletCSRFWhitelist() {
		_portletCSRFWhitelist = SetUtil.fromArray(
			PropsValues.AUTH_TOKEN_IGNORE_PORTLETS);
		_portletCSRFWhitelist = Collections.unmodifiableSet(
			_portletCSRFWhitelist);

		return _portletCSRFWhitelist;
	}

	@Override
	public Set<String> resetPortletInvocationWhitelist() {
		_portletInvocationWhitelist = SetUtil.fromArray(
			PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST);
		_portletInvocationWhitelist = Collections.unmodifiableSet(
			_portletInvocationWhitelist);

		return _portletInvocationWhitelist;
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

	private Set<String> _originCSRFWhitelist;
	private Set<String> _portletCSRFWhitelist;
	private final Set<String> _portletCSRFWhitelistActions =
		new ConcurrentHashSet<>();
	private Set<String> _portletInvocationWhitelist;
	private Set<String> _portletInvocationWhitelistActions;
	private final StringServiceRegistrationMap<Object> _serviceRegistrations =
		new StringServiceRegistrationMap<>();
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