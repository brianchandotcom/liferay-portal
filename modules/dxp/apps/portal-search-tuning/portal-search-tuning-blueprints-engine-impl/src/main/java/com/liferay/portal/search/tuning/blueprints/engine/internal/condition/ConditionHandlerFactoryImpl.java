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

package com.liferay.portal.search.tuning.blueprints.engine.internal.condition;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.engine.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.engine.spi.clause.ConditionHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = ConditionHandlerFactory.class)
public class ConditionHandlerFactoryImpl implements ConditionHandlerFactory {

	@Override
	public ConditionHandler getHandler(String name)
		throws IllegalArgumentException {

		ServiceComponentReference<ConditionHandler> serviceComponentReference =
			_conditionHandlers.get(name);

		if (serviceComponentReference == null) {
			throw new IllegalArgumentException(
				"No registered condition handler for " + name);
		}

		return serviceComponentReference.getServiceComponent();
	}

	@Override
	public String[] getHandlerNames() {
		Set<String> set = _conditionHandlers.keySet();

		return set.toArray(new String[0]);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerConditionHandler(
		ConditionHandler conditionHandler, Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			if (_log.isWarnEnabled()) {
				Class<?> clazz = conditionHandler.getClass();

				_log.warn(
					"Unable to add condition handler " + clazz.getName() +
						". Name property empty.");
			}

			return;
		}

		int serviceRanking = GetterUtil.get(
			properties.get("service.ranking"), 0);

		ServiceComponentReference<ConditionHandler> serviceComponentReference =
			new ServiceComponentReference<>(conditionHandler, serviceRanking);

		if (_conditionHandlers.containsKey(name)) {
			ServiceComponentReference<ConditionHandler> previousReference =
				_conditionHandlers.get(name);

			if (previousReference.compareTo(serviceComponentReference) < 0) {
				_conditionHandlers.put(name, serviceComponentReference);
			}
		}
		else {
			_conditionHandlers.put(name, serviceComponentReference);
		}
	}

	protected void unregisterConditionHandler(
		ConditionHandler conditionHandler, Map<String, Object> properties) {

		String name = (String)properties.get("name");

		if (Validator.isBlank(name)) {
			return;
		}

		_conditionHandlers.remove(name);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConditionHandlerFactoryImpl.class);

	private volatile Map<String, ServiceComponentReference<ConditionHandler>>
		_conditionHandlers = new ConcurrentHashMap<>();

}