/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			liferayFilters = new HashSet<>();

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
		new HashMap<>();

}