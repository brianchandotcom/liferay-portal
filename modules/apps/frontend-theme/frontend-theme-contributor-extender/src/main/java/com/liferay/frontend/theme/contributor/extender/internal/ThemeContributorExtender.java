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

package com.liferay.frontend.theme.contributor.extender.internal;

import com.liferay.frontend.theme.contributor.extender.BundleWebResources;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResources;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.net.URL;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Michael Bradford
 */
@Component(immediate = true, service = {})
public class ThemeContributorExtender
	implements ServiceTrackerCustomizer
		<ServletContext, Collection<ServiceRegistration<?>>> {

	@Override
	public Collection<ServiceRegistration<?>> addingService(
		ServiceReference<ServletContext> serviceReference) {

		Bundle bundle = serviceReference.getBundle();

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String type = headers.get("Liferay-Theme-Contributor-Type");

		if (type == null) {
			return null;
		}

		Map.Entry<List<String>, List<String>> entry = _scanBundleWebResources(
			bundle);

		if (entry == null) {
			return null;
		}

		int themeContributorWeight = GetterUtil.getInteger(
			headers.get("Liferay-Theme-Contributor-Weight"));

		Collection<ServiceRegistration<?>> serviceRegistrations =
			new ArrayList<>();

		ServletContext servletContext = _bundleContext.getService(
			serviceReference);

		serviceRegistrations.add(
			_bundleContext.registerService(
				PortalWebResources.class.getName(),
				new ThemeContributorPortalWebResources(bundle, servletContext),
				null));

		serviceRegistrations.add(
			_bundleContext.registerService(
				BundleWebResources.class,
				new BundleWebResourcesImpl(
					servletContext.getContextPath(), entry.getKey(),
					entry.getValue()),
				MapUtil.singletonDictionary(
					"service.ranking", themeContributorWeight)));

		return serviceRegistrations;
	}

	@Override
	public void modifiedService(
		ServiceReference<ServletContext> serviceReference,
		Collection<ServiceRegistration<?>> service) {
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> serviceReference,
		Collection<ServiceRegistration<?>> serviceRegistrations) {

		for (ServiceRegistration<?> serviceRegistration :
				serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_bundleContext.ungetService(serviceReference);
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				StringBundler.concat(
					"(&(objectClass=", ServletContext.class.getName(),
					")(osgi.web.symbolicname=*))")),
			this);

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private static Map.Entry<List<String>, List<String>>
		_scanBundleWebResources(Bundle bundle) {

		List<String> cssResourcePaths = new ArrayList<>();

		Enumeration<URL> cssEntries = bundle.findEntries(
			"/META-INF/resources", "*.css", true);

		if (cssEntries != null) {
			while (cssEntries.hasMoreElements()) {
				URL url = cssEntries.nextElement();

				String path = url.getFile();

				path = path.substring("/META-INF/resources".length());

				int index = path.lastIndexOf('/');

				if (!StringPool.UNDERLINE.equals(path.charAt(index + 1)) &&
					!path.endsWith("_rtl.css")) {

					cssResourcePaths.add(path);
				}
			}
		}

		List<String> jsResourcePaths = new ArrayList<>();

		Enumeration<URL> jsEntries = bundle.findEntries(
			"/META-INF/resources", "*.js", true);

		if (jsEntries != null) {
			while (jsEntries.hasMoreElements()) {
				URL url = jsEntries.nextElement();

				String path = url.getFile();

				jsResourcePaths.add(
					path.substring("/META-INF/resources".length()));
			}
		}

		if (cssResourcePaths.isEmpty() && jsResourcePaths.isEmpty()) {
			return null;
		}

		return new AbstractMap.SimpleImmutableEntry<>(
			cssResourcePaths, jsResourcePaths);
	}

	private BundleContext _bundleContext;
	private ServiceTracker<ServletContext, Collection<ServiceRegistration<?>>>
		_serviceTracker;

	private static class ThemeContributorPortalWebResources
		implements PortalWebResources {

		@Override
		public String getContextPath() {
			return _servletContext.getContextPath();
		}

		@Override
		public long getLastModified() {
			return _bundle.getLastModified();
		}

		@Override
		public String getResourceType() {
			return PortalWebResourceConstants.RESOURCE_TYPE_THEME_CONTRIBUTOR;
		}

		@Override
		public ServletContext getServletContext() {
			return _servletContext;
		}

		private ThemeContributorPortalWebResources(
			Bundle bundle, ServletContext servletContext) {

			_bundle = bundle;
			_servletContext = servletContext;
		}

		private final Bundle _bundle;
		private final ServletContext _servletContext;

	}

}