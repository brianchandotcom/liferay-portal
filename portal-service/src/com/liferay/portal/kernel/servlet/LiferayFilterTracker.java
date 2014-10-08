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

package com.liferay.portal.kernel.servlet;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayFilterTracker {

	public static void addLiferayFilter(LiferayFilter liferayFilter) {
		Class<?> clazz = liferayFilter.getClass();

		Set<LiferayFilter> liferayFilters = _liferayFilters.get(
			clazz.getName());

		if (liferayFilters == null) {
			liferayFilters = new HashSet<LiferayFilter>();

			_liferayFilters.put(clazz.getName(), liferayFilters);
		}

		liferayFilters.add(liferayFilter);
	}

	public static Set<String> getClassNames() {
		return Collections.unmodifiableSet(_liferayFilters.keySet());
	}

	public static Set<LiferayFilter> getLiferayFilters(String className) {
		Set<LiferayFilter> liferayFilters = _liferayFilters.get(className);

		if (liferayFilters == null) {
			return Collections.emptySet();
		}

		return Collections.unmodifiableSet(liferayFilters);
	}

	public static void removeLiferayFilter(LiferayFilter liferayFilter) {
		Class<?> clazz = liferayFilter.getClass();

		Set<LiferayFilter> liferayFilters = _liferayFilters.get(
			clazz.getName());

		if (liferayFilters != null) {
			liferayFilters.remove(liferayFilter);
		}
	}

	private static final Map<String, Set<LiferayFilter>> _liferayFilters =
		new HashMap<String, Set<LiferayFilter>>();

}