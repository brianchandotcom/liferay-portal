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

package com.liferay.portal.language.extender.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Preston Crary
 */
public class ServiceTrackerResourceBundleLoader
	implements ResourceBundleLoader {

	public ServiceTrackerResourceBundleLoader(
			BundleContext bundleContext, String aggregate, int aggregateId)
		throws InvalidSyntaxException {

		List<String> filterStrings = StringUtil.split(aggregate);

		_serviceTrackers = new ArrayList<>(filterStrings.size());

		for (String filterString : filterStrings) {
			Filter filter = bundleContext.createFilter(
				StringBundler.concat(
					"(&(objectClass=", ResourceBundleLoader.class.getName(),
					")", filterString, "(|(!(aggregateId=*))(!(aggregateId=",
					aggregateId, "))))"));

			ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>
				serviceTracker = new ServiceTracker<>(
					bundleContext, filter, null);

			_serviceTrackers.add(serviceTracker);
		}

		for (ServiceTracker<?, ?> serviceTracker : _serviceTrackers) {
			serviceTracker.open();
		}
	}

	public void close() {
		for (ServiceTracker<?, ?> serviceTracker : _serviceTrackers) {
			serviceTracker.close();
		}
	}

	@Override
	public ResourceBundle loadResourceBundle(Locale locale) {
		List<ResourceBundle> resourceBundles = new ArrayList<>();

		for (ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>
				serviceTracker : _serviceTrackers) {

			ResourceBundleLoader resourceBundleLoader =
				serviceTracker.getService();

			if (resourceBundleLoader != null) {
				ResourceBundle resourceBundle =
					resourceBundleLoader.loadResourceBundle(locale);

				if (resourceBundle != null) {
					resourceBundles.add(resourceBundle);
				}
			}
		}

		if (resourceBundles.isEmpty()) {
			return null;
		}

		if (resourceBundles.size() == 1) {
			return resourceBundles.get(0);
		}

		return new AggregateResourceBundle(
			resourceBundles.toArray(new ResourceBundle[0]));
	}

	private final List
		<ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>>
			_serviceTrackers;

}