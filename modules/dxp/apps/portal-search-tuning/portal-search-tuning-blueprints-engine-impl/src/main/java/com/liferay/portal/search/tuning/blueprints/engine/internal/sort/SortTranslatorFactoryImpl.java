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

package com.liferay.portal.search.tuning.blueprints.engine.internal.sort;

import com.liferay.portal.search.tuning.blueprints.engine.spi.sort.SortTranslator;
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
@Component(immediate = true, service = SortTranslatorFactory.class)
public class SortTranslatorFactoryImpl implements SortTranslatorFactory {

	@Override
	public SortTranslator getTranslator(String name)
		throws IllegalArgumentException {

		ServiceComponentReference<SortTranslator> serviceComponentReference =
			_sortTranslators.get(name);

		if (serviceComponentReference == null) {
			throw new IllegalArgumentException(
				"Unable to find sort translator " + name);
		}

		return serviceComponentReference.getServiceComponent();
	}

	@Override
	public String[] getTranslatorTypes() {
		return ServiceComponentReferenceUtil.getComponentKeys(_sortTranslators);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerSortTranslator(
		SortTranslator sortTranslator, Map<String, Object> properties) {

		ServiceComponentReferenceUtil.addToMapByName(
			_sortTranslators, sortTranslator, properties);
	}

	protected void unregisterSortTranslator(
		SortTranslator sortTranslator, Map<String, Object> properties) {

		ServiceComponentReferenceUtil.removeFromMapByName(
			_sortTranslators, sortTranslator, properties);
	}

	private volatile Map<String, ServiceComponentReference<SortTranslator>>
		_sortTranslators = new ConcurrentHashMap<>();

}