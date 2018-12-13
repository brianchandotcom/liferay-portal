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

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Tambara
 */
@Component(immediate = true, service = {})
public class DynamicSectionUtil {

	public static DynamicSectionReplace getReplace(String name) {
		return _dynamicSectionReplaceServiceTrackerMap.getService(name);
	}

	public static List<DynamicSection> getServices(String name) {
		return _dynamicSectionServiceTrackerMap.getService(name);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dynamicSectionServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, DynamicSection.class, "(name=*)",
				(serviceReference, emitter) -> emitter.emit(
					(String)serviceReference.getProperty("name")),
				ServiceReference::compareTo);

		_dynamicSectionReplaceServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DynamicSectionReplace.class, "name");
	}

	private static ServiceTrackerMap<String, DynamicSectionReplace>
		_dynamicSectionReplaceServiceTrackerMap;
	private static ServiceTrackerMap<String, List<DynamicSection>>
		_dynamicSectionServiceTrackerMap;

}