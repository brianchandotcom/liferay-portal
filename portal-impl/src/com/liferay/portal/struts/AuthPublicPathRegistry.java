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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Mika Koivisto
 * @author Raymond Augé
 */
public class AuthPublicPathRegistry {

	public static boolean contains(String path) {
		return _paths.contains(path);
	}

	public static void register(String... paths) {
		Collections.addAll(_paths, paths);
	}

	public static void unregister(String... paths) {
		_paths.removeAll(Arrays.asList(paths));
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final Set<String> _paths = Collections.newSetFromMap(
		new ConcurrentHashMap<>());
	private static final ServiceTracker<Object, Object> _serviceTracker;

	private static class AuthPublicTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, Object> {

		@Override
		public Object addingService(ServiceReference<Object> serviceReference) {
			List<String> paths = StringUtil.asList(
				serviceReference.getProperty("auth.public.path"));

			for (String path : paths) {
				_paths.add(path);
			}

			return _bundleContext.getService(serviceReference);
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference, Object object) {
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference, Object object) {

			List<String> paths = StringUtil.asList(
				serviceReference.getProperty("auth.public.path"));

			for (String path : paths) {
				_paths.remove(path);
			}

			_bundleContext.ungetService(serviceReference);
		}

	}

	static {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext,
			SystemBundleUtil.createFilter(
				"(&(auth.public.path=*)(objectClass=java.lang.Object))"),
			new AuthPublicTrackerCustomizer());

		_serviceTracker.open();
	}

}