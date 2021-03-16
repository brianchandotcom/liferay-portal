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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.engine.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.engine.spi.sort.SortTranslator;

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
@Component(immediate = true, service = SortTranslatorFactory.class)
public class SortTranslatorFactoryImpl implements SortTranslatorFactory {

	@Override
	public SortTranslator getTranslator(String type)
		throws IllegalArgumentException {

		ServiceComponentReference<SortTranslator> serviceComponentReference =
			_sortTranslators.get(type);

		if (serviceComponentReference == null) {
			throw new IllegalArgumentException(
				"Unable to find sort translator " + type);
		}

		return serviceComponentReference.getServiceComponent();
	}

	@Override
	public String[] getTranslatorTypes() {
		Set<String> set = _sortTranslators.keySet();

		return set.toArray(new String[0]);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerSortTranslator(
		SortTranslator sortTranslator, Map<String, Object> properties) {

		String type = (String)properties.get("type");

		if (Validator.isBlank(type)) {
			if (_log.isWarnEnabled()) {
				Class<?> clazz = sortTranslator.getClass();

				_log.warn(
					"Unable to add sort translator " + clazz.getName() +
						". Type property empty.");
			}

			return;
		}

		int serviceRanking = GetterUtil.get(
			properties.get("service.ranking"), 0);

		ServiceComponentReference<SortTranslator> serviceComponentReference =
			new ServiceComponentReference<>(sortTranslator, serviceRanking);

		if (_sortTranslators.containsKey(type)) {
			ServiceComponentReference<SortTranslator> previousReference =
				_sortTranslators.get(type);

			if (previousReference.compareTo(serviceComponentReference) < 0) {
				_sortTranslators.put(type, serviceComponentReference);
			}
		}
		else {
			_sortTranslators.put(type, serviceComponentReference);
		}
	}

	protected void unregisterSortTranslator(
		SortTranslator sortTranslator, Map<String, Object> properties) {

		String type = (String)properties.get("type");

		if (Validator.isBlank(type)) {
			return;
		}

		_sortTranslators.remove(type);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SortTranslatorFactoryImpl.class);

	private volatile Map<String, ServiceComponentReference<SortTranslator>>
		_sortTranslators = new ConcurrentHashMap<>();

}