/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ratings.taglib.internal.className.provider;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.ratings.page.ratings.classname.provider.RatingsClassNameProvider;

/**
 * @author Roberto Díaz
 */
public class RatingsClassNameProviderUtil {

	public static String getRatingsClassName(String className) {
		RatingsClassNameProvider ratingsClassNameProvider =
			_serviceTrackerMap.getService(className);

		if (ratingsClassNameProvider != null) {
			return ratingsClassNameProvider.getClassName();
		}

		return className;
	}

	private static final ServiceTrackerMap<String, RatingsClassNameProvider>
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			SystemBundleUtil.getBundleContext(), RatingsClassNameProvider.class,
			"model.class.name");

}