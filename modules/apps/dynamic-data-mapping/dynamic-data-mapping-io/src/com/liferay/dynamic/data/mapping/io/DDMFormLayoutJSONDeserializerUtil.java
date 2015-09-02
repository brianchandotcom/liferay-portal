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

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayoutJSONDeserializerUtil {

	public static DDMFormLayout deserialize(String serializedDDMFormLayout)
		throws PortalException {

		return getDDMFormLayoutJSONDeserializer().deserialize(
			serializedDDMFormLayout);
	}

	public static DDMFormLayoutJSONDeserializer
		getDDMFormLayoutJSONDeserializer() {

		PortalRuntimePermission.checkGetBeanProperty(
			DDMFormLayoutJSONDeserializerUtil.class);

		return _ddmFormLayoutJSONDeserializer;
	}

	public void setDDMFormLayoutJSONDeserializer(
		DDMFormLayoutJSONDeserializer ddmFormLayoutJSONDeserializer) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddmFormLayoutJSONDeserializer = ddmFormLayoutJSONDeserializer;
	}

	private static DDMFormLayoutJSONDeserializer _ddmFormLayoutJSONDeserializer;

}