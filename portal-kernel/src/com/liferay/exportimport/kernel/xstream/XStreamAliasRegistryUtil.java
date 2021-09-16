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

package com.liferay.exportimport.kernel.xstream;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Máté Thurzó
 */
public class XStreamAliasRegistryUtil {

	public static Map<Class<?>, String> getAliases() {
		return new HashMap<>(_xstreamAliases);
	}

	private XStreamAliasRegistryUtil() {
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final ServiceTracker<XStreamAlias, XStreamAlias>
		_serviceTracker;
	private static final Map<Class<?>, String> _xstreamAliases =
		new ConcurrentHashMap<>();

	private static class XStreamAliasServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<XStreamAlias, XStreamAlias> {

		@Override
		public XStreamAlias addingService(
			ServiceReference<XStreamAlias> serviceReference) {

			XStreamAlias xStreamAlias = _bundleContext.getService(
				serviceReference);

			_xstreamAliases.put(
				xStreamAlias.getClazz(), xStreamAlias.getName());

			return xStreamAlias;
		}

		@Override
		public void modifiedService(
			ServiceReference<XStreamAlias> serviceReference,
			XStreamAlias xStreamAlias) {
		}

		@Override
		public void removedService(
			ServiceReference<XStreamAlias> serviceReference,
			XStreamAlias xStreamAlias) {

			_bundleContext.ungetService(serviceReference);

			_xstreamAliases.remove(xStreamAlias.getClazz());
		}

	}

	static {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext, XStreamAlias.class,
			new XStreamAliasServiceTrackerCustomizer());

		_serviceTracker.open();
	}

}