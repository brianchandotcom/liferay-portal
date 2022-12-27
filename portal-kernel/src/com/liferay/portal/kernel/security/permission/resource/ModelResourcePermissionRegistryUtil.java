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

package com.liferay.portal.kernel.security.permission.resource;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

/**
 * @author Rafael Praxedes
 */
public class ModelResourcePermissionRegistryUtil {

	public static <T extends ClassedModel> ModelResourcePermission<T>
		getModelResourcePermission(String modelClassName) {

		return (ModelResourcePermission<T>)
			_modelResourcePermissionServiceTrackerMap.getService(
				modelClassName);
	}

	private static final ServiceTrackerMap<String, ModelResourcePermission<?>>
		_modelResourcePermissionServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				SystemBundleUtil.getBundleContext(),
				(Class<ModelResourcePermission<?>>)
					(Class<?>)ModelResourcePermission.class,
				"model.class.name");

}