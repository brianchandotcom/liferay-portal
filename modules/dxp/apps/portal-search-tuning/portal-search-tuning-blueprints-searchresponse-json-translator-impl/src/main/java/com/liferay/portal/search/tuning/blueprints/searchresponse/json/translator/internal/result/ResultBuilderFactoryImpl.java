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

package com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal.result;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.engine.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal.result.builder.DefaultResultBuilder;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.result.ResultBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = ResultBuilderFactory.class)
public class ResultBuilderFactoryImpl implements ResultBuilderFactory {

	@Override
	public ResultBuilder getBuilder(String type) {
		ServiceComponentReference<ResultBuilder> serviceComponentReference =
			_resultBuilders.get(type);

		if (serviceComponentReference == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find result builder for " + type +
						". Falling back to default.");
			}

			return new DefaultResultBuilder();
		}

		return serviceComponentReference.getServiceComponent();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerResultBuilder(
		ResultBuilder resultBuilder, Map<String, Object> properties) {

		String type = (String)properties.get("model.class.name");

		Class<?> clazz = resultBuilder.getClass();

		if (Validator.isBlank(type)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to add result builder " + clazz.getName() +
						". model.class.name property empty.");
			}

			return;
		}

		int serviceRanking = GetterUtil.get(
			properties.get("service.ranking"), 0);

		ServiceComponentReference<ResultBuilder> serviceComponentReference =
			new ServiceComponentReference<>(resultBuilder, serviceRanking);

		if (_resultBuilders.containsKey(type)) {
			ServiceComponentReference<ResultBuilder> previousReference =
				_resultBuilders.get(type);

			if (previousReference.compareTo(serviceComponentReference) < 0) {
				_resultBuilders.put(type, serviceComponentReference);
			}
		}
		else {
			_resultBuilders.put(type, serviceComponentReference);
		}
	}

	protected void unregisterResultBuilder(
		ResultBuilder resultBuilder, Map<String, Object> properties) {

		String type = (String)properties.get("model.class.name");

		if (Validator.isBlank(type)) {
			return;
		}

		_resultBuilders.remove(type);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResultBuilderFactoryImpl.class);

	private volatile Map<String, ServiceComponentReference<ResultBuilder>>
		_resultBuilders = new ConcurrentHashMap<>();

}