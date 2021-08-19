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

package com.liferay.fragment.collection.filter.internal;

import com.liferay.fragment.collection.filter.FragmentCollectionFilter;
import com.liferay.fragment.collection.filter.FragmentCollectionFilterTracker;
import com.liferay.fragment.exception.FragmentEntryConfigurationException;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Pablo Molina
 */
@Component(immediate = true, service = FragmentCollectionFilterTracker.class)
public class FragmentCollectionFilterTrackerImpl
	implements FragmentCollectionFilterTracker {

	@Override
	public FragmentCollectionFilter getFragmentCollectionFilter(String key) {
		return _serviceTrackerMap.getService(key);
	}

	@Override
	public List<FragmentCollectionFilter> getFragmentCollectionFilters() {
		return new ArrayList<>(_serviceTrackerMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FragmentCollectionFilter.class, null,
			(serviceReference, emitter) -> {
				FragmentCollectionFilter fragmentCollectionFilter =
					bundleContext.getService(serviceReference);

				emitter.emit(fragmentCollectionFilter.getFilterKey());
			},
			new FragmentCollectionFilterServiceTrackerCustomizer(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCollectionFilterTrackerImpl.class);

	@Reference
	private FragmentEntryValidator _fragmentEntryValidator;

	private ServiceTrackerMap<String, FragmentCollectionFilter>
		_serviceTrackerMap;

	private class FragmentCollectionFilterServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<FragmentCollectionFilter, FragmentCollectionFilter> {

		public FragmentCollectionFilterServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public FragmentCollectionFilter addingService(
			ServiceReference<FragmentCollectionFilter> serviceReference) {

			FragmentCollectionFilter fragmentCollectionFilter =
				_bundleContext.getService(serviceReference);

			try {
				_fragmentEntryValidator.validateConfiguration(
					fragmentCollectionFilter.getConfiguration());

				return fragmentCollectionFilter;
			}
			catch (FragmentEntryConfigurationException
						fragmentEntryConfigurationException) {

				_log.error(
					String.format(
						"Fragment collection filter with filter key %s and " +
							"label %s could not be registered due to invalid " +
								"configuration",
						fragmentCollectionFilter.getFilterKey(),
						fragmentCollectionFilter.getLabel(
							LocaleUtil.getMostRelevantLocale())),
					fragmentEntryConfigurationException);
			}

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<FragmentCollectionFilter> serviceReference,
			FragmentCollectionFilter fragmentCollectionFilter) {
		}

		@Override
		public void removedService(
			ServiceReference<FragmentCollectionFilter> serviceReference,
			FragmentCollectionFilter fragmentCollectionFilter) {

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

}