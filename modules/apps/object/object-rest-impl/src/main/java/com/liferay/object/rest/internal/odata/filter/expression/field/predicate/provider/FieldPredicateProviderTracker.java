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

package com.liferay.object.rest.internal.odata.filter.expression.field.predicate.provider;

import com.liferay.object.odata.filter.expression.field.predicate.provider.FieldPredicateProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = FieldPredicateProviderTracker.class)
public class FieldPredicateProviderTracker {

	public FieldPredicateProvider getFieldPredicateProvider(String key) {
		FieldPredicateProvider fieldPredicateProvider =
			_serviceTrackerMap.getService(key);

		if ((fieldPredicateProvider != null) &&
			FeatureFlagManagerUtil.isEnabled("LPS-176651")) {

			return fieldPredicateProvider;
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FieldPredicateProvider.class,
			"field.predicate.provider.key");
	}

	private ServiceTrackerMap<String, FieldPredicateProvider>
		_serviceTrackerMap;

}