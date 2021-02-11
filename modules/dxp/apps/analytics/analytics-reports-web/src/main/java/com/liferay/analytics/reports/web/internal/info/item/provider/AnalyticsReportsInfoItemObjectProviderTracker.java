/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.web.internal.info.item.provider;

import com.liferay.analytics.reports.info.item.provider.AnalyticsReportsInfoItemObjectProvider;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(service = AnalyticsReportsInfoItemObjectProviderTracker.class)
public class AnalyticsReportsInfoItemObjectProviderTracker {

	public AnalyticsReportsInfoItemObjectProvider<?>
		getAnalyticsReportsInfoItemObjectProvider(String className) {

		return _analyticsReportsInfoItemObjectProviderServiceTrackerMap.
			getService(className);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_analyticsReportsInfoItemObjectProviderServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<AnalyticsReportsInfoItemObjectProvider<?>>)
					(Class<?>)AnalyticsReportsInfoItemObjectProvider.class,
				null,
				(serviceReference, emitter) -> {
					AnalyticsReportsInfoItemObjectProvider<?>
						analyticsReportsInfoItemObjectProvider =
							bundleContext.getService(serviceReference);

					try {
						emitter.emit(
							analyticsReportsInfoItemObjectProvider.
								getClassName());
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				},
				new PropertyServiceReferenceComparator<>("service.ranking"));
	}

	private ServiceTrackerMap<String, AnalyticsReportsInfoItemObjectProvider<?>>
		_analyticsReportsInfoItemObjectProviderServiceTrackerMap;

}