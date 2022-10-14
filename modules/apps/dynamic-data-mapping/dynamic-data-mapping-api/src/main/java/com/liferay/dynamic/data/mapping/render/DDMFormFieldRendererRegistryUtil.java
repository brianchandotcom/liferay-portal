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

package com.liferay.dynamic.data.mapping.render;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Pablo Carvalho
 */
public class DDMFormFieldRendererRegistryUtil {

	public static DDMFormFieldRenderer getDDMFormFieldRenderer(
		String ddmFormFieldType) {

		return _ddmFormFieldRendererRegistry.getDDMFormFieldRenderer(
			ddmFormFieldType);
	}

	private static volatile DDMFormFieldRendererRegistry
		_ddmFormFieldRendererRegistry =
			ServiceProxyFactory.newServiceTrackedInstance(
				DDMFormFieldRendererRegistry.class,
				DDMFormFieldRendererRegistryUtil.class,
				"_ddmFormFieldRendererRegistry", true);

}