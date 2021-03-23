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

package com.liferay.portal.search.tuning.blueprints.engine.internal.parameter;

import com.liferay.portal.search.tuning.blueprints.engine.internal.parameter.builder.ParameterBuilder;
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
@Component(immediate = true, service = ParameterBuilderFactory.class)
public class ParameterBuilderFactoryImpl implements ParameterBuilderFactory {

	@Override
	public ParameterBuilder getBuilder(String type)
		throws IllegalArgumentException {

		ServiceComponentReference<ParameterBuilder> serviceComponentReference =
			_parameterBuilders.get(type);

		if (serviceComponentReference == null) {
			throw new IllegalArgumentException(
				"No registered parameter builder " + type);
		}

		return serviceComponentReference.getServiceComponent();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerParameterBuilder(
		ParameterBuilder parameterBuilder, Map<String, Object> properties) {

		ServiceComponentReferenceUtil.addToMapByName(
			_parameterBuilders, parameterBuilder, properties);
	}

	protected void unregisterParameterBuilder(
		ParameterBuilder parameterBuilder, Map<String, Object> properties) {

		ServiceComponentReferenceUtil.removeFromMapByName(
			_parameterBuilders, parameterBuilder, properties);
	}

	private volatile Map<String, ServiceComponentReference<ParameterBuilder>>
		_parameterBuilders = new ConcurrentHashMap<>();

}