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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.engine.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.engine.internal.parameter.builder.ParameterBuilder;

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

		String type = (String)properties.get("type");

		if (Validator.isBlank(type)) {
			if (_log.isWarnEnabled()) {
				Class<?> clazz = parameterBuilder.getClass();

				_log.warn(
					"Unable to add parameter builder " + clazz.getName() +
						". Type property empty.");
			}

			return;
		}

		int serviceRanking = GetterUtil.get(
			properties.get("service.ranking"), 0);

		ServiceComponentReference<ParameterBuilder> serviceComponentReference =
			new ServiceComponentReference<>(parameterBuilder, serviceRanking);

		if (_parameterBuilders.containsKey(type)) {
			ServiceComponentReference<ParameterBuilder> previousReference =
				_parameterBuilders.get(type);

			if (previousReference.compareTo(serviceComponentReference) < 0) {
				_parameterBuilders.put(type, serviceComponentReference);
			}
		}
		else {
			_parameterBuilders.put(type, serviceComponentReference);
		}
	}

	protected void unregisterParameterBuilder(
		ParameterBuilder parameterBuilder, Map<String, Object> properties) {

		String type = (String)properties.get("type");

		if (Validator.isBlank(type)) {
			return;
		}

		_parameterBuilders.remove(type);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ParameterBuilderFactoryImpl.class);

	private volatile Map<String, ServiceComponentReference<ParameterBuilder>>
		_parameterBuilders = new ConcurrentHashMap<>();

}