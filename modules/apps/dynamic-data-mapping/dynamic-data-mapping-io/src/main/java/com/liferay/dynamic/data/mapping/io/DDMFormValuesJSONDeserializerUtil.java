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

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesJSONDeserializerUtil {

	public static DDMFormValues deserialize(
			DDMForm ddmForm, String serializedDDMFormValues)
		throws PortalException {

		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer =
			getDDMFormValuesJSONDeserializer();

		return ddmFormValuesJSONDeserializer.deserialize(
			ddmForm, serializedDDMFormValues);
	}

	public static DDMFormValuesJSONDeserializer
		getDDMFormValuesJSONDeserializer() {

		PortalRuntimePermission.checkGetBeanProperty(
			DDMFormValuesJSONDeserializerUtil.class);

		return _serviceTracker.getService();
	}

	private static final ServiceTracker
		<DDMFormValuesJSONDeserializer, DDMFormValuesJSONDeserializer>
			_serviceTracker = ServiceTrackerFactory.open(
				FrameworkUtil.getBundle(
					DDMFormValuesJSONDeserializerUtil.class),
				DDMFormValuesJSONDeserializer.class);

}