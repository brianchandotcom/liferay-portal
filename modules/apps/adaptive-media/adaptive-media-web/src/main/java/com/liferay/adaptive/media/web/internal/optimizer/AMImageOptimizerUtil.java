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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = {})
public class AMImageOptimizerUtil {

	public static void optimize(long companyId) {
		if (_serviceTrackerMap == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to optimize for company " + companyId +
						" because the component is not active");
			}

			return;
		}

		Set<String> modelClassNames = _serviceTrackerMap.keySet();

		for (String modelClassName : modelClassNames) {
			AMImageOptimizer amImageOptimizer = _serviceTrackerMap.getService(
				modelClassName);

			amImageOptimizer.optimize(companyId);
		}
	}

	public static void optimize(long companyId, String configurationEntryUuid) {
		if (_serviceTrackerMap == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to optimize for company " + companyId +
						" because the component is not active");
			}

			return;
		}

		Set<String> modelClassNames = _serviceTrackerMap.keySet();

		for (String modelClassName : modelClassNames) {
			AMImageOptimizer amImageOptimizer = _serviceTrackerMap.getService(
				modelClassName);

			amImageOptimizer.optimize(companyId, configurationEntryUuid);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AMImageOptimizer.class, "adaptive.media.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();

		_serviceTrackerMap = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AMImageOptimizerUtil.class);

	private static ServiceTrackerMap<String, AMImageOptimizer>
		_serviceTrackerMap;

}