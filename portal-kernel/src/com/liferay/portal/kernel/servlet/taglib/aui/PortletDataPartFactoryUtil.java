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

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
public class PortletDataPartFactoryUtil {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), use {@link PortletDataPartFactory#createESMPortletDataPart(String, Collection)} if possible
	 * @review
	 */
	@Deprecated
	public static PortletDataPart createAMDPortletDataPart(
		String content, String amdRequire) {

		return _portletDataPartFactory.createAMDPortletDataPart(
			content, amdRequire);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), use {@link PortletDataPartFactory#createESMPortletDataPart(String, Collection)} if possible
	 * @review
	 */
	@Deprecated
	public static PortletDataPart createAUIPortletDataPart(
		String content, String modules) {

		return _portletDataPartFactory.createAUIPortletDataPart(
			content, modules);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), use {@link PortletDataPartFactory#createESMPortletDataPart(String, Collection)} if possible
	 * @review
	 */
	@Deprecated
	public static PortletDataPart createSimplePortletDataPart(String content) {
		return _portletDataPartFactory.createSimplePortletDataPart(content);
	}

	private static volatile PortletDataPartFactory _portletDataPartFactory;
	private static final ServiceTracker
		<PortletDataPartFactory, PortletDataPartFactory> _serviceTracker;

	static {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_serviceTracker = new ServiceTracker<>(
			bundleContext, PortletDataPartFactory.class,
			new ServiceTrackerCustomizer
				<PortletDataPartFactory, PortletDataPartFactory>() {

				@Override
				public PortletDataPartFactory addingService(
					ServiceReference<PortletDataPartFactory> serviceReference) {

					_portletDataPartFactory = bundleContext.getService(
						serviceReference);

					return _portletDataPartFactory;
				}

				@Override
				public void modifiedService(
					ServiceReference<PortletDataPartFactory> serviceReference,
					PortletDataPartFactory portletDataPartFactory) {
				}

				@Override
				public void removedService(
					ServiceReference<PortletDataPartFactory> serviceReference,
					PortletDataPartFactory portletDataPartFactory) {

					_portletDataPartFactory = null;

					bundleContext.ungetService(serviceReference);
				}

			});

		_serviceTracker.open();
	}

}