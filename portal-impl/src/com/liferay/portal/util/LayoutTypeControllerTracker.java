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
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Collections;
import java.util.Map;
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
		LayoutTypeController layoutTypeController = _layoutTypeControllers.get(
			type);

		if (layoutTypeController != null) {
			return layoutTypeController;
		}

		return _layoutTypeControllers.get(LayoutConstants.TYPE_PORTLET);
	}

	public static Map<String, LayoutTypeController> getLayoutTypeControllers() {
		return Collections.unmodifiableMap(_layoutTypeControllers);
	}

	public static String[] getTypes() {
		Set<String> types = _layoutTypeControllers.keySet();

		return types.toArray(new String[0]);
	}

	private static final ConcurrentMap<String, LayoutTypeController>
		_layoutTypeControllers = new ConcurrentHashMap<>();
	private static final ServiceTracker
		<LayoutTypeController, LayoutTypeController> _serviceTracker;

	private static class LayoutTypeControllerServiceTrackerCustomizer
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

			_layoutTypeControllers.remove(type);
		}

	}

	static {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(layout.type=*)(objectClass=" +
				LayoutTypeController.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new LayoutTypeControllerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

}