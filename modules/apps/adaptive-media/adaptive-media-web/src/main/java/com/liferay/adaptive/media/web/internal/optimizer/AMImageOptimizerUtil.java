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