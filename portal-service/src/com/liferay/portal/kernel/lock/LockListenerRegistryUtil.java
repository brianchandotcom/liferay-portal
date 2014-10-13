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

package com.liferay.portal.kernel.lock;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Chow
 * @author Peter Fellwock
 */
public class LockListenerRegistryUtil {

	public static LockListener getLockListener(String className) {
		return _instance._lockListeners.get(className);
	}

	private LockListenerRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			LockListener.class, new LockListenerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private static final LockListenerRegistryUtil _instance =
		new LockListenerRegistryUtil();

	private final Map<String, LockListener> _lockListeners =
		new HashMap<String, LockListener>();
	private final ServiceTracker<?, LockListener> _serviceTracker;

	private class LockListenerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<LockListener, LockListener> {

		@Override
		public LockListener addingService(
			ServiceReference<LockListener> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			LockListener lockListener = registry.getService(serviceReference);

			_lockListeners.put(lockListener.getClassName(), lockListener);

			return lockListener;
		}

		@Override
		public void modifiedService(
			ServiceReference<LockListener> serviceReference,
			LockListener lockListener) {
		}

		@Override
		public void removedService(
			ServiceReference<LockListener> serviceReference,
			LockListener lockListener) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_lockListeners.remove(lockListener);
		}
	}

}