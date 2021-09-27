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

package com.liferay.search.experiences.internal.blueprint.aggregation.translator;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = AggregationTranslatorFactory.class)
public class AggregationTranslatorFactoryImpl
	implements AggregationTranslatorFactory {

	@Override
	public AggregationTranslator getTranslator(String name)
		throws IllegalArgumentException {

		AggregationTranslator aggregationTranslator =
			_aggregationTranslatorServiceTrackerMap.getService(name);

		if (aggregationTranslator == null) {
			throw new IllegalArgumentException(
				"Unable to find aggregation translator " + name);
		}

		return aggregationTranslator;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_aggregationTranslatorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, AggregationTranslator.class, "name");
	}

	@Deactivate
	protected void deactivate() {
		_aggregationTranslatorServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, AggregationTranslator>
		_aggregationTranslatorServiceTrackerMap;

}