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

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Portlet;
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
@SuppressWarnings("deprecation")
public abstract class BaseAuthTokenWhitelist implements AuthTokenWhitelist {

	@Override
	public Set<String> getOriginCSRFWhitelist() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> getPortletCSRFWhitelist() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> getPortletCSRFWhitelistActions() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> getPortletInvocationWhitelist() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> getPortletInvocationWhitelistActions() {
		return Collections.emptySet();
	}

	@Override
	public boolean isOriginCSRFWhitelisted(long companyId, String origin) {
		return false;
	}

	@Override
	public boolean isPortletCSRFWhitelisted(
		HttpServletRequest request, Portlet portlet) {

		return false;
	}

	@Deprecated
	@Override
	public boolean isPortletCSRFWhitelisted(
		long companyId, String portletId, String strutsAction) {

		return false;
	}

	@Override
	public boolean isPortletInvocationWhitelisted(
		HttpServletRequest request, Portlet portlet) {

		return false;
	}

	@Override
	public boolean isPortletInvocationWhitelisted(long companyId, String
		portletId, String strutsAction) {

		return false;
	}

	@Override
	public boolean isPortletURLCSRFWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		return false;
	}

	@Override
	public boolean isPortletURLPortletInvocationWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		return false;
	}

	@Override
	public boolean isValidSharedSecret(String sharedSecret) {
		return false;
	}

	@Override
	public Set<String> resetOriginCSRFWhitelist() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> resetPortletCSRFWhitelist() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> resetPortletInvocationWhitelist() {
		return Collections.emptySet();
	}

	@Override
	public Set<String> resetPortletInvocationWhitelistActions() {
		return Collections.emptySet();
	}

	protected void destroy() {
		for (ServiceRegistration<Object> serviceRegistration :
				serviceRegistrations.values()) {

			serviceRegistration.unregister();
		}
	}

	protected void registerPortalProperty(String propertyName) {
		Registry registry = RegistryUtil.getRegistry();

		String[] propertyValue = PropsUtil.getArray(propertyName);

		Map<String, Object> properties = new HashMap<>();

		properties.put(propertyName, propertyValue);
		properties.put("objectClass", Object.class.getName());

		ServiceRegistration<Object> serviceRegistration =
			registry.registerService(Object.class, new Object(), properties);

		serviceRegistrations.put(
			StringUtil.merge(propertyValue), serviceRegistration);
	}

	protected ServiceTracker<Object, Object> trackWhitelistServices(
		String whitelistName, Set whiteList) {

		Registry registry = RegistryUtil.getRegistry();

		ServiceTracker<Object, Object> serviceTracker = registry.trackServices(
			registry.getFilter(
				"(&(" + whitelistName + "=*)" +
					"(objectClass=java.lang.Object))"),
			new TokenWhitelistTrackerCustomizer(whitelistName, whiteList));

		serviceTracker.open();

		return serviceTracker;
	}

	protected final StringServiceRegistrationMap<Object> serviceRegistrations =
		new StringServiceRegistrationMapImpl<>();

	private class TokenWhitelistTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, Object> {

		public TokenWhitelistTrackerCustomizer(
			String whitelistName, Set whitelist) {

			_whitelistName = whitelistName;
			_whitelist = whitelist;
		}

		@Override
		public Object addingService(ServiceReference<Object> serviceReference) {
			List<String> authTokenIgnoreActions = StringPlus.asList(
				serviceReference.getProperty(_whitelistName));

			_whitelist.addAll(authTokenIgnoreActions);

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
				serviceReference.getProperty(_whitelistName));

			_whitelist.removeAll(authTokenIgnoreActions);

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);
		}

		private final Set _whitelist;
		private final String _whitelistName;

	}

}