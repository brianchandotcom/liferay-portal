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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;

/**
 * @author Marcellus Tavares
 */
public class DDMFormJSONDeserializerUtil {

	public static DDMForm deserialize(String serializedDDMForm)
		throws PortalException {

		return getDDMFormJSONDeserializer().deserialize(serializedDDMForm);
	}

	public static DDMFormJSONDeserializer getDDMFormJSONDeserializer() {
		PortalRuntimePermission.checkGetBeanProperty(
			DDMFormJSONDeserializerUtil.class);

		return _ddmFormJSONDeserializer;
	}

	public void setDDMFormJSONDeserializer(
		DDMFormJSONDeserializer ddmFormJSONDeserializer) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddmFormJSONDeserializer = ddmFormJSONDeserializer;
	}

	private static DDMFormJSONDeserializer _ddmFormJSONDeserializer;

}