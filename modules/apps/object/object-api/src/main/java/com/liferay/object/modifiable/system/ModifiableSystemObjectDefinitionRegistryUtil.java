/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.modifiable.system;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Carolina Barbosa
 */
public class ModifiableSystemObjectDefinitionRegistryUtil {

	public static ModifiableSystemObjectDefinition
		getModifiableSystemObjectDefinition(String name) {

		return _serviceTrackerMap.getService(name);
	}

	private static final ServiceTrackerMap
		<String, ModifiableSystemObjectDefinition> _serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ModifiableSystemObjectDefinitionRegistryUtil.class);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundle.getBundleContext(), ModifiableSystemObjectDefinition.class,
			"object.definition.name");
	}

}