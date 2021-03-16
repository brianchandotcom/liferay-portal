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

package com.liferay.portal.search.tuning.blueprints.engine.internal.clause;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.engine.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.engine.spi.clause.ClauseTranslator;

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
@Component(immediate = true, service = ClauseTranslatorFactory.class)
public class ClauseTranslatorFactoryImpl implements ClauseTranslatorFactory {

	@Override
	public ClauseTranslator getTranslator(String type)
		throws IllegalArgumentException {

		ServiceComponentReference<ClauseTranslator> serviceComponentReference =
			_clauseTranslators.get(type);

		if (serviceComponentReference == null) {
			throw new IllegalArgumentException(
				"No registered clause translator for " + type);
		}

		return serviceComponentReference.getServiceComponent();
	}

	@Override
	public String[] getTranslatorTypes() {
		Set<String> set = _clauseTranslators.keySet();

		return set.toArray(new String[0]);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerClauseTranslator(
		ClauseTranslator clauseTranslator, Map<String, Object> properties) {

		String type = (String)properties.get("type");

		if (Validator.isBlank(type)) {
			if (_log.isWarnEnabled()) {
				Class<?> clazz = clauseTranslator.getClass();

				_log.warn(
					"Unable to add clause translator " + clazz.getName() +
						". Type property empty.");
			}

			return;
		}

		int serviceRanking = GetterUtil.get(
			properties.get("service.ranking"), 0);

		ServiceComponentReference<ClauseTranslator> serviceComponentReference =
			new ServiceComponentReference<>(clauseTranslator, serviceRanking);

		if (_clauseTranslators.containsKey(type)) {
			ServiceComponentReference<ClauseTranslator> previousReference =
				_clauseTranslators.get(type);

			if (previousReference.compareTo(serviceComponentReference) < 0) {
				_clauseTranslators.put(type, serviceComponentReference);
			}
		}
		else {
			_clauseTranslators.put(type, serviceComponentReference);
		}
	}

	protected void unregisterClauseTranslator(
		ClauseTranslator clauseTranslator, Map<String, Object> properties) {

		String type = (String)properties.get("type");

		if (Validator.isBlank(type)) {
			return;
		}

		_clauseTranslators.remove(type);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClauseTranslatorFactoryImpl.class);

	private volatile Map<String, ServiceComponentReference<ClauseTranslator>>
		_clauseTranslators = new ConcurrentHashMap<>();

}