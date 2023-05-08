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

import com.liferay.adaptive.media.image.internal.configuration.AMImageMagickConfiguration;
import com.liferay.adaptive.media.image.scaler.AMImageScaler;
import com.liferay.adaptive.media.image.scaler.AMImageScalerRegistry;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.StringPlus;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Sergio González
 */
@Component(
	configurationPid = "com.liferay.adaptive.media.image.internal.configuration.AMImageMagickConfiguration",
	service = AMImageScalerRegistry.class
)
public class AMImageScalerRegistryImpl implements AMImageScalerRegistry {

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
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_amImageMagickConfiguration = ConfigurableUtil.createConfigurable(
			AMImageMagickConfiguration.class, properties);

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, AMImageScaler.class, null,
			(serviceReference, emitter) -> {
				AMImageScaler amImageScaler = bundleContext.getService(
					serviceReference);

				if (amImageScaler instanceof AMImageMagickImageScaler) {
					List<String> mimeTypes = Arrays.asList(
						_amImageMagickConfiguration.
							imageMagickSupportedMimeTypes());

					mimeTypes.forEach(emitter::emit);
				}
				else {
					List<String> mimeTypes = StringPlus.asList(
						serviceReference.getProperty("mime.type"));

					mimeTypes.forEach(emitter::emit);
				}
			},
			new PropertyServiceReferenceComparator<>("service.ranking"));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AMImageScalerRegistryImpl.class);

	private volatile AMImageMagickConfiguration _amImageMagickConfiguration;
	private volatile ServiceTrackerMap<String, List<AMImageScaler>>
		_serviceTrackerMap;

}