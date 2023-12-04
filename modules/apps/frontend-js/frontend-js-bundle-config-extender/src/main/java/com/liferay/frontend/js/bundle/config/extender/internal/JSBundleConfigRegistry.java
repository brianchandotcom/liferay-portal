/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.bundle.config.extender.internal;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringPool;

import java.net.URL;

import java.util.Collection;
import java.util.Dictionary;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = JSBundleConfigRegistry.class)
public class JSBundleConfigRegistry
	implements ServiceTrackerCustomizer
		<ServletContext, JSBundleConfigRegistry.JSConfig> {

	@Override
	public JSConfig addingService(
		ServiceReference<ServletContext> serviceReference) {

		Bundle bundle = serviceReference.getBundle();

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String jsConfig = headers.get("Liferay-JS-Config");

		if (jsConfig != null) {
			URL url = bundle.getEntry(jsConfig);

			if (url != null) {
				ServletContext servletContext = _bundleContext.getService(
					serviceReference);

				_lastModified = System.currentTimeMillis();

				return new JSConfig(servletContext, url);
			}
		}

		return null;
	}

	public Collection<JSConfig> getJSConfigs() {
		return _serviceTrackerList.toList();
	}

	public long getLastModified() {
		return _lastModified;
	}

	@Override
	public void modifiedService(
		ServiceReference<ServletContext> serviceReference, JSConfig jsConfig) {
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> serviceReference, JSConfig jsConfig) {

		if (jsConfig != null) {
			_bundleContext.ungetService(serviceReference);

			_lastModified = System.currentTimeMillis();
		}
	}

	public static class JSConfig {

		public ServletContext getServletContext() {
			return _servletContext;
		}

		public URL getURL() {
			return _url;
		}

		private JSConfig(ServletContext servletContext, URL url) {
			_servletContext = servletContext;
			_url = url;
		}

		private final ServletContext _servletContext;
		private final URL _url;

	}

	@Activate
	protected void activate(ComponentContext componentContext)
		throws Exception {

		_bundleContext = componentContext.getBundleContext();

		_serviceTrackerList = ServiceTrackerListFactory.open(
			_bundleContext, ServletContext.class, "(osgi.web.contextpath=*)",
			this);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private BundleContext _bundleContext;
	private volatile long _lastModified = System.currentTimeMillis();
	private ServiceTrackerList<JSConfig> _serviceTrackerList;

}