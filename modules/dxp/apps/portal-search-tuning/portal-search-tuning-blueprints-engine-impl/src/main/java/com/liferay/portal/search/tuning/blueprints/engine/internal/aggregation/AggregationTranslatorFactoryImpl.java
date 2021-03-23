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

package com.liferay.portal.search.tuning.blueprints.engine.internal.aggregation;

import com.liferay.portal.search.tuning.blueprints.engine.spi.aggregation.AggregationTranslator;
import com.liferay.portal.search.tuning.blueprints.engine.spi.aggregation.AggregationTranslatorFactory;
import com.liferay.portal.search.tuning.blueprints.util.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.util.component.ServiceComponentReferenceUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = AggregationTranslatorFactory.class)
public class AggregationTranslatorFactoryImpl
	implements AggregationTranslatorFactory {

	@Override
	public AggregationTranslator getTranslator(String name)
		throws IllegalArgumentException {

		ServiceComponentReference<AggregationTranslator>
			serviceComponentReference = _aggregationTranslators.get(name);

		if (serviceComponentReference == null) {
			throw new IllegalArgumentException(
				"Unable to find aggregation translator " + name);
		}

		return serviceComponentReference.getServiceComponent();
	}

	@Override
	public String[] getTranslatorTypes() {
		return ServiceComponentReferenceUtil.getComponentKeys(
			_aggregationTranslators);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerAggregationTranslator(
		AggregationTranslator aggregationTranslator,
		Map<String, Object> properties) {

		ServiceComponentReferenceUtil.addToMapByName(
			_aggregationTranslators, aggregationTranslator, properties);
	}

	protected void unregisterAggregationTranslator(
		AggregationTranslator aggregationTranslator,
		Map<String, Object> properties) {

		ServiceComponentReferenceUtil.removeFromMapByName(
			_aggregationTranslators, aggregationTranslator, properties);
	}

	private volatile Map
		<String, ServiceComponentReference<AggregationTranslator>>
			_aggregationTranslators = new ConcurrentHashMap<>();

}