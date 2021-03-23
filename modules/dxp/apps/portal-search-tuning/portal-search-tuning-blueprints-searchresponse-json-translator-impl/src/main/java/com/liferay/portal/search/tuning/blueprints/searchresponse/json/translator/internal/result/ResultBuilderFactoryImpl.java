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
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal.result.builder.DefaultResultBuilder;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.result.ResultBuilder;
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
@Component(immediate = true, service = ResultBuilderFactory.class)
public class ResultBuilderFactoryImpl implements ResultBuilderFactory {

	@Override
	public ResultBuilder getBuilder(String className) {
		ServiceComponentReference<ResultBuilder> serviceComponentReference =
			_resultBuilders.get(className);

		if (serviceComponentReference == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find result builder for " + className +
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

		ServiceComponentReferenceUtil.addToMapByProperty(
			_resultBuilders, resultBuilder, properties, "model.class.name");
	}

	protected void unregisterResultBuilder(
		ResultBuilder resultBuilder, Map<String, Object> properties) {

		ServiceComponentReferenceUtil.removeFromMapByProperty(
			_resultBuilders, resultBuilder, properties, "model.class.name");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResultBuilderFactoryImpl.class);

	private volatile Map<String, ServiceComponentReference<ResultBuilder>>
		_resultBuilders = new ConcurrentHashMap<>();

}