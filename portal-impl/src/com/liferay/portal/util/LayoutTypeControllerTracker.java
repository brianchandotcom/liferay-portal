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

package com.liferay.portal.util;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.model.impl.LayoutTypeControllerImpl;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Raymond Augé
 */
public class LayoutTypeControllerTracker {

	public static LayoutTypeController getLayoutTypeController(Layout layout) {
		return getLayoutTypeController(layout.getType());
	}

	public static LayoutTypeController getLayoutTypeController(String type) {
		return _instance._getLayoutTypeController(type);
	}

	public static Map<String, LayoutTypeController> getLayoutTypeControllers() {
		return Collections.unmodifiableMap(_instance._layoutTypeControllers);
	}

	public static String[] getTypes() {
		return _instance._getTypes();
	}

	private LayoutTypeControllerTracker() {
		for (String type : _LAYOUT_TYPES) {
			_defaultLayoutTypeControllers.put(
				type, new LayoutTypeControllerImpl(type));
		}

		Registry registry = RegistryUtil.getRegistry();

		_registerDefaults(registry);

		Filter filter = registry.getFilter(
			"(&(layout.type=*)(objectClass=" +
				LayoutTypeController.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new LayoutTypeControllerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private LayoutTypeController _getLayoutTypeController(String type) {
		LayoutTypeController layoutTypeController = _layoutTypeControllers.get(
			type);

		if (layoutTypeController != null) {
			return layoutTypeController;
		}

		return _layoutTypeControllers.get(LayoutConstants.TYPE_PORTLET);
	}

	private String[] _getTypes() {
		Set<String> types = _layoutTypeControllers.keySet();

		return types.toArray(new String[types.size()]);
	}

	private void _registerDefaults(Registry registry) {
		Set<Entry<String, LayoutTypeController>> entries =
			_defaultLayoutTypeControllers.entrySet();

		for (Entry<String, LayoutTypeController> entry : entries) {
			Map<String, Object> properties = new HashMap<>();

			properties.put("layout.type", entry.getKey());

			registry.registerService(
				LayoutTypeController.class, entry.getValue(), properties);
		}
	}

	private static final String[] _LAYOUT_TYPES = new String[] {
		LayoutConstants.TYPE_EMBEDDED, LayoutConstants.TYPE_LINK_TO_LAYOUT,
		LayoutConstants.TYPE_PANEL, LayoutConstants.TYPE_PORTLET,
		LayoutConstants.TYPE_URL
	};

	private static final LayoutTypeControllerTracker _instance =
		new LayoutTypeControllerTracker();

	private final Map<String, LayoutTypeController>
		_defaultLayoutTypeControllers = new ConcurrentHashMap<>();
	private final ConcurrentMap<String, LayoutTypeController>
		_layoutTypeControllers = new ConcurrentHashMap<>();
	private final ServiceTracker<LayoutTypeController, LayoutTypeController>
		_serviceTracker;

	private class LayoutTypeControllerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<LayoutTypeController, LayoutTypeController> {

		@Override
		@SuppressWarnings("unchecked")
		public LayoutTypeController addingService(
			ServiceReference<LayoutTypeController> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			LayoutTypeController layoutTypeController = registry.getService(
				serviceReference);

			String type = (String)serviceReference.getProperty("layout.type");

			_layoutTypeControllers.put(type, layoutTypeController);

			return layoutTypeController;
		}

		@Override
		public void modifiedService(
			ServiceReference<LayoutTypeController> serviceReference,
			LayoutTypeController layoutTypeController) {
		}

		@Override
		public void removedService(
			ServiceReference<LayoutTypeController> serviceReference,
			LayoutTypeController layoutTypeController) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String type = (String)serviceReference.getProperty("layout.type");

			if (_defaultLayoutTypeControllers.containsKey(type)) {
				_layoutTypeControllers.replace(
					type, layoutTypeController,
					_defaultLayoutTypeControllers.get(type));
			}
			else {
				_layoutTypeControllers.remove(type);
			}
		}

	}

}