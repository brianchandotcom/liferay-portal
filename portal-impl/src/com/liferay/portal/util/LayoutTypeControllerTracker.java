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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.model.impl.LayoutTypeControllerImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Raymond Augé
 */
public class LayoutTypeControllerTracker {

	public static LayoutTypeController getLayoutTypeController(Layout layout) {
		return getLayoutTypeController(layout.getType());
	}

	public static LayoutTypeController getLayoutTypeController(String type) {
		LayoutTypeController layoutTypeController =
			_serviceTrackerMap.getService(type);

		if (layoutTypeController != null) {
			return layoutTypeController;
		}

		return _serviceTrackerMap.getService(LayoutConstants.TYPE_PORTLET);
	}

	public static Map<String, LayoutTypeController> getLayoutTypeControllers() {
		HashMap<String, LayoutTypeController> layoutTypeControllers =
			new HashMap<>();

		for (String type : _serviceTrackerMap.keySet()) {
			layoutTypeControllers.put(
				type, _serviceTrackerMap.getService(type));
		}

		return layoutTypeControllers;
	}

	public static String[] getTypes() {
		Set<String> types = _serviceTrackerMap.keySet();

		return types.toArray(new String[0]);
	}

	private static final String[] _LAYOUT_TYPES = {
		LayoutConstants.TYPE_PORTLET
	};

	private static final ServiceTrackerMap<String, LayoutTypeController>
		_serviceTrackerMap;

	static {
		Registry registry = RegistryUtil.getRegistry();

		for (String type : _LAYOUT_TYPES) {
			registry.registerService(
				LayoutTypeController.class, new LayoutTypeControllerImpl(type),
				HashMapBuilder.<String, Object>put(
					"layout.type", type
				).put(
					"service.ranking", Integer.MIN_VALUE
				).build());
		}

		_serviceTrackerMap = ServiceTrackerCollections.openSingleValueMap(
			LayoutTypeController.class, "layout.type");
	}

}