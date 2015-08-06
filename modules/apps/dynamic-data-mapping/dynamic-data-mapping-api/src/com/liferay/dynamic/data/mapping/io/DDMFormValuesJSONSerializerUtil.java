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

import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesJSONSerializerUtil {

	public static DDMFormValuesJSONSerializer getDDMFormValuesJSONSerializer() {
		PortalRuntimePermission.checkGetBeanProperty(
			DDMFormValuesJSONSerializerUtil.class);

		return _ddmFormValuesJSONSerializer;
	}

	public static String serialize(DDMFormValues ddmFormValues) {
		DDMFormValuesJSONSerializer ddmFormValuesJSONSerializer =
			getDDMFormValuesJSONSerializer();

		return ddmFormValuesJSONSerializer.serialize(ddmFormValues);
	}

	public void setDDMFormValuesJSONSerializer(
		DDMFormValuesJSONSerializer ddmFormValuesJSONSerializer) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddmFormValuesJSONSerializer = ddmFormValuesJSONSerializer;
	}

	private static DDMFormValuesJSONSerializer _ddmFormValuesJSONSerializer;

}