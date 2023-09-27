/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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