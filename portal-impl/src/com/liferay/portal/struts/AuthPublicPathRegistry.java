/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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