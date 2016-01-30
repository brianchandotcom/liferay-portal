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

package com.liferay.portal.kernel.security.auth;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
public abstract class BaseAuthTokenWhitelist implements AuthTokenWhitelist {

	@Deprecated
	@Override
	public Set<String> getOriginCSRFWhitelist() {
		return Collections.emptySet();
	}

	@Deprecated
	@Override
	public Set<String> getPortletCSRFWhitelist() {
		return Collections.emptySet();
	}

	@Deprecated
	@Override
	public Set<String> getPortletCSRFWhitelistActions() {
		return Collections.emptySet();
	}

	@Deprecated
	@Override
	public Set<String> getPortletInvocationWhitelist() {
		return Collections.emptySet();
	}

	@Deprecated
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

	@Deprecated
	@Override
	public boolean isPortletInvocationWhitelisted(
		long companyId, String portletId, String strutsAction) {

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

	@Deprecated
	@Override
	public Set<String> resetOriginCSRFWhitelist() {
		return Collections.emptySet();
	}

	@Deprecated
	@Override
	public Set<String> resetPortletCSRFWhitelist() {
		return Collections.emptySet();
	}

	@Deprecated
	@Override
	public Set<String> resetPortletInvocationWhitelist() {
		return Collections.emptySet();
	}

	@Deprecated
	@Override
	public Set<String> resetPortletInvocationWhitelistActions() {
		return Collections.emptySet();
	}

	protected void destroy() {
		for (ServiceRegistration<Object> serviceRegistration :
				serviceRegistrations.values()) {

			serviceRegistration.unregister();
		}

		for (ServiceTracker<Object, Object> serviceTracker : serviceTrackers) {
			serviceTracker.close();
		}
	}

	protected void registerPortalProperty(String key) {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		String[] values = PropsUtil.getArray(key);

		properties.put(key, values);

		ServiceRegistration<Object> serviceRegistration =
			registry.registerService(Object.class, new Object(), properties);

		serviceRegistrations.put(StringUtil.merge(values), serviceRegistration);
	}

	protected ServiceTracker<Object, Object> trackWhitelistServices(
		String whitelistName, Set<String> whiteList) {

		Registry registry = RegistryUtil.getRegistry();

		ServiceTracker<Object, Object> serviceTracker = registry.trackServices(
			registry.getFilter("(" + whitelistName + "=*)"),
			new TokenWhitelistTrackerCustomizer(whitelistName, whiteList));

		serviceTracker.open();

		serviceTrackers.add(serviceTracker);

		return serviceTracker;
	}

	protected final StringServiceRegistrationMap<Object> serviceRegistrations =
		new StringServiceRegistrationMapImpl<>();
	protected final List<ServiceTracker<Object, Object>> serviceTrackers =
		new ArrayList<>();

	private static class TokenWhitelistTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, Object> {

		public TokenWhitelistTrackerCustomizer(
			String whitelistName, Set<String> whitelist) {

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
		}

		private final Set<String> _whitelist;
		private final String _whitelistName;

	}

}