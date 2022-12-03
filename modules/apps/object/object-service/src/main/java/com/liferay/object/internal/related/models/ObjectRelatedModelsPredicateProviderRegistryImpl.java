/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.internal.related.models;

import com.liferay.object.related.models.ObjectRelatedModelsPredicateProvider;
import com.liferay.object.related.models.ObjectRelatedModelsPredicateProviderRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = ObjectRelatedModelsPredicateProviderRegistry.class)
public class ObjectRelatedModelsPredicateProviderRegistryImpl
	implements ObjectRelatedModelsPredicateProviderRegistry {

	@Override
	public ObjectRelatedModelsPredicateProvider
		getObjectRelatedModelsPredicateProvider(String className, String type) {

		String key = _getKey(className, type);

		ObjectRelatedModelsPredicateProvider
			objectRelatedModelsPredicateProvider =
				_serviceTrackerMap.getService(key);

		if (objectRelatedModelsPredicateProvider == null) {
			throw new IllegalArgumentException(
				"No object related models predicate provider found with key " +
					key);
		}

		return objectRelatedModelsPredicateProvider;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectRelatedModelsPredicateProvider.class, null,
			(serviceReference, emitter) -> {
				ObjectRelatedModelsPredicateProvider
					objectRelatedModelsPredicateProvider =
						bundleContext.getService(serviceReference);

				emitter.emit(
					_getKey(
						objectRelatedModelsPredicateProvider.getClassName(),
						objectRelatedModelsPredicateProvider.
							getObjectRelationshipType()));
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private String _getKey(String className, String type) {
		return className + StringPool.POUND + type;
	}

	private ServiceTrackerMap<String, ObjectRelatedModelsPredicateProvider>
		_serviceTrackerMap;

}