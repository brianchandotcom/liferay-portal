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

import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypeAccessPolicy;
import com.liferay.portal.model.impl.DefaultLayoutTypeAccessPolicyImpl;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

/**
 * @author Adolfo Pérez
 */
public class LayoutTypeAccessPolicyTracker {

	public static LayoutTypeAccessPolicy getLayoutTypeAccessPolicy(
		Layout layout) {

		return getLayoutTypeAccessPolicy(layout.getType());
	}

	public static LayoutTypeAccessPolicy getLayoutTypeAccessPolicy(
		String type) {

		return _instance._getLayoutTypeAccessPolicy(type);
	}

	private LayoutTypeAccessPolicy _getLayoutTypeAccessPolicy(String type) {
		LayoutTypeAccessPolicy layoutTypeAccessPolicy =
			_serviceTrackerMap.getService(type);

		if (layoutTypeAccessPolicy == null) {
			return DefaultLayoutTypeAccessPolicyImpl.create();
		}

		return layoutTypeAccessPolicy;
	}

	private static final LayoutTypeAccessPolicyTracker _instance =
		new LayoutTypeAccessPolicyTracker();

	private final ServiceTrackerMap<String, LayoutTypeAccessPolicy>
		_serviceTrackerMap = ServiceTrackerCollections.openSingleValueMap(
			LayoutTypeAccessPolicy.class,
			"(&(layout.type=*)(objectClass=" +
				LayoutTypeAccessPolicy.class.getName() + "))",
			new ServiceReferenceMapper<String, LayoutTypeAccessPolicy>() {

				@Override
				public void map(
					ServiceReference<LayoutTypeAccessPolicy> serviceReference,
					Emitter<String> emitter) {

					String layoutType = (String)serviceReference.getProperty(
						"layout.type");

					emitter.emit(layoutType);
				}

			});

}