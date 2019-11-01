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

package com.liferay.adaptive.media.image.internal.scaler;

import com.liferay.adaptive.media.image.scaler.AMImageScaler;
import com.liferay.adaptive.media.image.scaler.AMImageScalerTracker;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Sergio González
 */
@Component(service = AMImageScalerTracker.class)
public class AMImageScalerTrackerImpl implements AMImageScalerTracker {

	@Override
	public AMImageScaler getAMImageScaler(String mimeType) {
		List<AMImageScaler> amImageScalers = _serviceTrackerMap.getService(
			mimeType);

		if (ListUtil.isNotEmpty(amImageScalers)) {
			for (AMImageScaler amImageScaler : amImageScalers) {
				if (amImageScaler.isEnabled()) {
					return amImageScaler;
				}
			}
		}

		amImageScalers = _serviceTrackerMap.getService("*");

		if (ListUtil.isNotEmpty(amImageScalers)) {
			for (AMImageScaler amImageScaler : amImageScalers) {
				if (amImageScaler.isEnabled()) {
					return amImageScaler;
				}
			}
		}

		if (_log.isWarnEnabled()) {
			_log.warn("Unable to find default image scaler");
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, AMImageScaler.class, "(mime.type=*)",
			new PropertyServiceReferenceMapper<>("mime.type"),
			new PropertyServiceReferenceComparator<>("service.ranking"));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AMImageScalerTrackerImpl.class);

	private ServiceTrackerMap<String, List<AMImageScaler>> _serviceTrackerMap;

}