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

package com.liferay.portal.kernel.search.query;

import com.liferay.portal.kernel.search.Query;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

/**
 * @author Michael C. Han
 */
public class FieldQueryFactoryUtil {

	public static Query createQuery(
		String field, String value, boolean like, boolean splitKeywords) {

		if (_instance._fieldQueryFactory == null) {
			throw new IllegalStateException("Uninitialized FieldQueryFactory");
		}

		return _instance._fieldQueryFactory.createQuery(
			field, value, like, splitKeywords);
	}

	private FieldQueryFactoryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			FieldQueryFactory.class,
			new FieldQueryFactoryServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private static final FieldQueryFactoryUtil _instance =
		new FieldQueryFactoryUtil();

	private FieldQueryFactory _fieldQueryFactory;
	private final ServiceTracker<FieldQueryFactory, FieldQueryFactory>
		_serviceTracker;

	private class FieldQueryFactoryServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<FieldQueryFactory, FieldQueryFactory> {

		@Override
		public FieldQueryFactory addingService(
			ServiceReference<FieldQueryFactory> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			_fieldQueryFactory = registry.getService(serviceReference);

			return _fieldQueryFactory;
		}

		@Override
		public void modifiedService(
			ServiceReference<FieldQueryFactory> serviceReference,
			FieldQueryFactory fieldQueryFactory) {
		}

		@Override
		public void removedService(
			ServiceReference<FieldQueryFactory> serviceReference,
			FieldQueryFactory fieldQueryFactory) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_fieldQueryFactory = null;
		}

	}

}