/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.web.internal.optimizer;

import com.liferay.adaptive.media.image.optimizer.AMImageOptimizer;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Sergio González
 */
public class AMImageOptimizerUtil {

	public static void optimize(long companyId) {
		Set<String> modelClassNames = _serviceTrackerMap.keySet();

		for (String modelClassName : modelClassNames) {
			AMImageOptimizer amImageOptimizer = _serviceTrackerMap.getService(
				modelClassName);

			amImageOptimizer.optimize(companyId);
		}
	}

	public static void optimize(long companyId, String configurationEntryUuid) {
		Set<String> modelClassNames = _serviceTrackerMap.keySet();

		for (String modelClassName : modelClassNames) {
			AMImageOptimizer amImageOptimizer = _serviceTrackerMap.getService(
				modelClassName);

			amImageOptimizer.optimize(companyId, configurationEntryUuid);
		}
	}

	private static final ServiceTrackerMap<String, AMImageOptimizer>
		_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AMImageOptimizerUtil.class);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundle.getBundleContext(), AMImageOptimizer.class,
			"adaptive.media.key");
	}

}