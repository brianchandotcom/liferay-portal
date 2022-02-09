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

package com.liferay.remote.app.web.internal.servlet.taglib;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	immediate = true,
	service = {DynamicInclude.class, RemoteAppTopHeadDynamicInclude.class}
)
public class RemoteAppTopHeadDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		for (String url : _urls.keySet()) {
			printWriter.println(
				"<script type=\"module\" src=\"" + url + "\"></script>");
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}

	public synchronized void registerURLs(String portletName, String[] urls) {
		synchronized (this) {
			_portletURLsMap.put(portletName, urls);

			for (String url : urls) {
				Integer referenceCount = _urls.get(url);

				if (referenceCount == null) {
					_urls.put(url, _ONE);
				}
				else {
					_urls.put(url, referenceCount + 1);
				}
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker<>(
			bundleContext, Portlet.class, _serviceTrackerCustomizer);

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_serviceTracker = null;

		_bundleContext = null;
	}

	private static final Integer _ONE = 1;

	private BundleContext _bundleContext;
	private final ConcurrentMap<String, String[]> _portletURLsMap =
		new ConcurrentHashMap<>();
	private ServiceTracker<Portlet, String> _serviceTracker;

	private final ServiceTrackerCustomizer<Portlet, String>
		_serviceTrackerCustomizer =
			new ServiceTrackerCustomizer<Portlet, String>() {

				@Override
				public String addingService(
					ServiceReference<Portlet> serviceReference) {

					Portlet portlet = _bundleContext.getService(
						serviceReference);

					return portlet.getPortletName();
				}

				@Override
				public void modifiedService(
					ServiceReference<Portlet> serviceReference,
					String portletName) {
				}

				@Override
				public void removedService(
					ServiceReference<Portlet> serviceReference,
					String portletName) {

					synchronized (this) {
						String[] urls = _portletURLsMap.remove(portletName);

						if (urls == null) {
							return;
						}

						for (String url : urls) {
							Integer referenceCount = _urls.get(url);

							if (referenceCount.equals(_ONE)) {
								_urls.remove(url);
							}
							else {
								_urls.put(url, referenceCount - 1);
							}
						}
					}
				}

			};

	private final ConcurrentMap<String, Integer> _urls =
		new ConcurrentHashMap<>();

}