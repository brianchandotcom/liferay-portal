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

package com.liferay.object.rest.internal.manager.v1_0;

import com.liferay.object.rest.manager.v1_0.ObjectRelationshipElementsParser;
import com.liferay.object.rest.manager.v1_0.ObjectRelationshipElementsParserRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Carlos Correa
 * @author Sergio Jimenez del Coso
 */
@Component(service = ObjectRelationshipElementsParserRegistry.class)
public class ObjectRelationshipElementsParserRegistryImpl
	implements ObjectRelationshipElementsParserRegistry {

	@Override
	public ObjectRelationshipElementsParser getObjectRelationshipElementsParser(
			String className, long companyId, String type)
		throws Exception {

		String key = _getKey(className, companyId, type);

		ObjectRelationshipElementsParser objectRelationshipManager =
			_serviceTrackerMap.getService(key);

		if (objectRelationshipManager == null) {
			throw new IllegalArgumentException(
				"No object relationship manager found with key " + key);
		}

		return objectRelationshipManager;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectRelationshipElementsParser.class, null,
			(serviceReference, emitter) -> {
				ObjectRelationshipElementsParser
					objectRelationshipElementsParser = bundleContext.getService(
						serviceReference);

				emitter.emit(
					_getKey(
						objectRelationshipElementsParser.getClassName(),
						objectRelationshipElementsParser.getCompanyId(),
						objectRelationshipElementsParser.
							getObjectRelationshipType()));
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private String _getKey(String className, long companyId, String type) {
		return StringBundler.concat(
			className, StringPool.POUND, companyId, StringPool.POUND, type);
	}

	private ServiceTrackerMap<String, ObjectRelationshipElementsParser>
		_serviceTrackerMap;

}