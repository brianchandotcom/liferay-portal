/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.content.type;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.rest.resource.exception.DataDefinitionValidationException;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Leonardo Barros
 */
public class DataDefinitionContentTypeRegistryUtil {

	public static Long getClassNameId(String contentType) throws Exception {
		DataDefinitionContentType dataDefinitionContentType =
			getDataDefinitionContentType(contentType);

		Long id = dataDefinitionContentType.getClassNameId();

		if (id == null) {
			throw new DataDefinitionValidationException.MustSetValidContentType(
				contentType);
		}

		return id;
	}

	public static DataDefinitionContentType getDataDefinitionContentType(
		long classNameId) {

		for (DataDefinitionContentType dataDefinitionContentType :
				_serviceTrackerMap.values()) {

			if (dataDefinitionContentType.getClassNameId() == classNameId) {
				return dataDefinitionContentType;
			}
		}

		return null;
	}

	public static DataDefinitionContentType getDataDefinitionContentType(
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

	private static final ServiceTrackerMap<String, DataDefinitionContentType>
		_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DataDefinitionContentTypeRegistryUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DataDefinitionContentType.class, "content.type");
	}

}