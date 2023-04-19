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

package com.liferay.frontend.taglib.dynamic.section.internal.util;

import com.liferay.frontend.taglib.dynamic.section.DynamicSection;
import com.liferay.frontend.taglib.dynamic.section.DynamicSectionReplace;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Matthew Tambara
 */
public class DynamicSectionUtil {

	public static DynamicSectionReplace getReplace(String name) {
		return _dynamicSectionReplaceServiceTrackerMap.getService(name);
	}

	public static List<DynamicSection> getServices(String name) {
		return _dynamicSectionServiceTrackerMap.getService(name);
	}

	private static final ServiceTrackerMap<String, DynamicSectionReplace>
		_dynamicSectionReplaceServiceTrackerMap;
	private static final ServiceTrackerMap<String, List<DynamicSection>>
		_dynamicSectionServiceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DynamicSectionUtil.class);

		_dynamicSectionServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundle.getBundleContext(), DynamicSection.class, "(name=*)",
				(serviceReference, emitter) -> emitter.emit(
					(String)serviceReference.getProperty("name")),
				ServiceReference::compareTo);

		_dynamicSectionReplaceServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundle.getBundleContext(), DynamicSectionReplace.class, "name");
	}

}