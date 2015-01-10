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

package com.liferay.portal.kernel.servlet.taglib;

import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

/**
 * @author Carlos Sierra Andrés
 */
public class TagDynamicIdFactoryRegistry {

	public static TagDynamicIdFactory getTagIdFactory(String tagClassName) {
		return _instance._tagIdFactories.getService(tagClassName);
	}

	private TagDynamicIdFactoryRegistry() {
		_tagIdFactories.open();
	}

	private static final TagDynamicIdFactoryRegistry _instance =
		new TagDynamicIdFactoryRegistry();

	private final ServiceTrackerMap<String, TagDynamicIdFactory>
		_tagIdFactories = ServiceTrackerCollections.singleValueMap(
			TagDynamicIdFactory.class, "tagClassName");

}