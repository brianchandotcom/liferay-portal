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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;

/**
 * @author Pablo Carvalho
 */
public class DDMFormXSDSerializerUtil {

	public static DDMFormXSDSerializer getDDMFormXSDSerializer() {
		PortalRuntimePermission.checkGetBeanProperty(
			DDMFormXSDSerializerUtil.class);

		return _ddmFormXSDSerializer;
	}

	public static String serialize(DDMForm ddmForm) {
		return getDDMFormXSDSerializer().serialize(ddmForm);
	}

	public void setDDMFormXSDSerializer(
		DDMFormXSDSerializer ddmFormXSDSerializer) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddmFormXSDSerializer = ddmFormXSDSerializer;
	}

	private static DDMFormXSDSerializer _ddmFormXSDSerializer;

}