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

package com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal.aggregation;

import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.aggregation.AggregationJSONTranslator;
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
@Component(immediate = true, service = AggregationJSONTranslatorFactory.class)
public class AggregationJSONTranslatorFactoryImpl
	implements AggregationJSONTranslatorFactory {

	@Override
	public AggregationJSONTranslator getBuilder(String type)
		throws IllegalArgumentException {

		ServiceComponentReference<AggregationJSONTranslator>
			serviceComponentReference = _aggregationJSONTranslators.get(type);

		if (serviceComponentReference == null) {
			throw new IllegalArgumentException(
				"Unable to find aggregation JSON translator for " + type);
		}

		return serviceComponentReference.getServiceComponent();
	}

	@Override
	public String[] getBuilderTypes() {
		return ServiceComponentReferenceUtil.getComponentKeys(
			_aggregationJSONTranslators);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerAggregationJSONTranslator(
		AggregationJSONTranslator aggregationJSONTranslator,
		Map<String, Object> properties) {

		ServiceComponentReferenceUtil.addToMapByName(
			_aggregationJSONTranslators, aggregationJSONTranslator, properties);
	}

	protected void unregisterAggregationJSONTranslator(
		AggregationJSONTranslator aggregationJSONTranslator,
		Map<String, Object> properties) {

		ServiceComponentReferenceUtil.removeFromMapByName(
			_aggregationJSONTranslators, aggregationJSONTranslator, properties);
	}

	private volatile Map
		<String, ServiceComponentReference<AggregationJSONTranslator>>
			_aggregationJSONTranslators = new ConcurrentHashMap<>();

}