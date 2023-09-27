/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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