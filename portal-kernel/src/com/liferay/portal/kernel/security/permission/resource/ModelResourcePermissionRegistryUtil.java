/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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