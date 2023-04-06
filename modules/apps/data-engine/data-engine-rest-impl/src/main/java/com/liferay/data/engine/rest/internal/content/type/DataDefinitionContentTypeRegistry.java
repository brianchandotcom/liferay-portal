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

package com.liferay.data.engine.rest.internal.content.type;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.rest.resource.exception.DataDefinitionValidationException;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Leonardo Barros
 */
@Component(service = DataDefinitionContentTypeRegistry.class)
public class DataDefinitionContentTypeRegistry {

	public Long getClassNameId(String contentType) throws Exception {
		DataDefinitionContentType dataDefinitionContentType =
			getDataDefinitionContentType(contentType);

		Long id = dataDefinitionContentType.getClassNameId();

		if (id == null) {
			throw new DataDefinitionValidationException.MustSetValidContentType(
				contentType);
		}

		return id;
	}

	public DataDefinitionContentType getDataDefinitionContentType(
		long classNameId) {

		for (DataDefinitionContentType dataDefinitionContentType :
				_serviceTrackerMap.values()) {

			if (dataDefinitionContentType.getClassNameId() == classNameId) {
				return dataDefinitionContentType;
			}
		}

		return null;
	}

	public DataDefinitionContentType getDataDefinitionContentType(
			String contentType)
		throws Exception {

		DataDefinitionContentType dataDefinitionContentType =
			_serviceTrackerMap.getService(contentType);

		if (dataDefinitionContentType == null) {
			throw new DataDefinitionValidationException.MustSetValidContentType(
				contentType);
		}

		return dataDefinitionContentType;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DataDefinitionContentType.class, "content.type");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, DataDefinitionContentType>
		_serviceTrackerMap;

}