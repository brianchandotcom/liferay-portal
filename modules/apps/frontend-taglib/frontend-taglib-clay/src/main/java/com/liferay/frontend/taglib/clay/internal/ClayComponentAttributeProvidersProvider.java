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

package com.liferay.frontend.taglib.clay.internal;

import com.liferay.frontend.taglib.clay.attribute.provider.ClayComponentAttributeProvider;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(immediate = true, service = {})
public class ClayComponentAttributeProvidersProvider {

	public static List<ClayComponentAttributeProvider>
		getClayComponentAttributeProviders(String key) {

		if (_clayComponentAttributeProvidersProvider == null) {
			_log.error(
				"Unable to get ClayComponentAttributeProvidersProvider when " +
					"retrieving list for key " + key);

			return Collections.emptyList();
		}

		ServiceTrackerMap<String, List<ClayComponentAttributeProvider>>
			clayComponentAttributeProviders =
				_clayComponentAttributeProvidersProvider.
					_clayComponentAttributeProviders;

		return clayComponentAttributeProviders.getService(key);
	}

	public ClayComponentAttributeProvidersProvider() {
		_clayComponentAttributeProvidersProvider = this;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_clayComponentAttributeProviders =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, ClayComponentAttributeProvider.class,
				"(clay.component.attribute.provider.key=*)",
				new PropertyServiceReferenceMapper<>(
					"clay.component.attribute.provider.key"),
				new PropertyServiceReferenceComparator<>("service.ranking"));
	}

	@Deactivate
	protected void deactivate() {
		_clayComponentAttributeProviders.close();

		_clayComponentAttributeProviders = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClayComponentAttributeProvidersProvider.class);

	private static ClayComponentAttributeProvidersProvider
		_clayComponentAttributeProvidersProvider;

	private ServiceTrackerMap<String, List<ClayComponentAttributeProvider>>
		_clayComponentAttributeProviders;

}