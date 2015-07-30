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

package com.liferay.portlet.dynamicdatamapping.io;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

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

		return _ddmFormValuesJSONDeserializer;
	}

	public void setDDMFormValuesJSONDeserializer(
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
	}

	private static DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;

}