/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet.taglib;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

/**
 * @author Carlos Sierra Andrés
 */
public class TagDynamicIdFactoryRegistry {

	public static TagDynamicIdFactory getTagDynamicIdFactory(
		String tagClassName) {

		return _tagDynamicIdFactories.getService(tagClassName);
	}

	private static final ServiceTrackerMap<String, TagDynamicIdFactory>
		_tagDynamicIdFactories = ServiceTrackerMapFactory.openSingleValueMap(
			SystemBundleUtil.getBundleContext(), TagDynamicIdFactory.class,
			"tagClassName");

}