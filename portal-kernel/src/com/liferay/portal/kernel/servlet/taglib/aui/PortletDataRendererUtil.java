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

package com.liferay.portal.kernel.servlet.taglib.aui;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.io.IOException;
import java.io.Writer;

import java.util.Collection;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
public class PortletDataRendererUtil {

	public static void writeTo(
			Writer writer, Collection<PortletData> portletDatas)
		throws IOException {

		_portletDataRenderer.writeTo(writer, portletDatas);
	}

	private static volatile PortletDataRenderer _portletDataRenderer;
	private static final ServiceTracker
		<PortletDataRenderer, PortletDataRenderer> _serviceTracker;

	static {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_serviceTracker = new ServiceTracker<>(
			bundleContext, PortletDataRenderer.class,
			new ServiceTrackerCustomizer
				<PortletDataRenderer, PortletDataRenderer>() {

				@Override
				public PortletDataRenderer addingService(
					ServiceReference<PortletDataRenderer> serviceReference) {

					_portletDataRenderer = bundleContext.getService(
						serviceReference);

					return _portletDataRenderer;
				}

				@Override
				public void modifiedService(
					ServiceReference<PortletDataRenderer> serviceReference,
					PortletDataRenderer portletDataRenderer) {
				}

				@Override
				public void removedService(
					ServiceReference<PortletDataRenderer> serviceReference,
					PortletDataRenderer portletDataRenderer) {

					_portletDataRenderer = null;

					bundleContext.ungetService(serviceReference);
				}

			});

		_serviceTracker.open();
	}

}