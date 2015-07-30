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

package com.liferay.portlet.dynamicdatamapping.registry;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldTypeRegistryUtil {

	public static DDMFormFieldType getDDMFormFieldType(String name) {
		return getDDMFormFieldTypeRegistry().getDDMFormFieldType(name);
	}

	public static Set<String> getDDMFormFieldTypeNames() {
		return getDDMFormFieldTypeRegistry().getDDMFormFieldTypeNames();
	}

	public static DDMFormFieldTypeRegistry getDDMFormFieldTypeRegistry() {
		PortalRuntimePermission.checkGetBeanProperty(
			DDMFormFieldTypeRegistryUtil.class);

		return _ddmFormFieldTypeRegistry;
	}

	public static List<DDMFormFieldType> getDDMFormFieldTypes() {
		return getDDMFormFieldTypeRegistry().getDDMFormFieldTypes();
	}

	public void setDDMFormFieldTypeRegistry(
		DDMFormFieldTypeRegistry ddmFormFieldTypeRegistry) {

		PortalRuntimePermission.checkGetBeanProperty(getClass());

		_ddmFormFieldTypeRegistry = ddmFormFieldTypeRegistry;
	}

	private static DDMFormFieldTypeRegistry _ddmFormFieldTypeRegistry;

}