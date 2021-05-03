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

import com.liferay.portal.search.tuning.blueprints.engine.spi.clause.ClauseTranslator;
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
		return ServiceComponentReferenceUtil.getComponentKeys(
			_clauseTranslators);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerClauseTranslator(
		ClauseTranslator clauseTranslator, Map<String, Object> properties) {

		ServiceComponentReferenceUtil.addToMapByName(
			_clauseTranslators, clauseTranslator, properties);
	}

	protected void unregisterClauseTranslator(
		ClauseTranslator clauseTranslator, Map<String, Object> properties) {

		ServiceComponentReferenceUtil.removeFromMapByName(
			_clauseTranslators, clauseTranslator, properties);
	}

	private volatile Map<String, ServiceComponentReference<ClauseTranslator>>
		_clauseTranslators = new ConcurrentHashMap<>();

}