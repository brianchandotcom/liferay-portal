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

package com.liferay.portal.vulcan.extension.util;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.extension.EntityExtensionHandler;
import com.liferay.portal.vulcan.extension.ExtensionProvider;
import com.liferay.portal.vulcan.extension.ExtensionProviderRegistry;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;

/**
 * @author Carlos Correa
 */
public class ExtensionUtil {

	public static EntityExtensionHandler getEntityExtensionHandler(
		String className, long companyId,
		ExtensionProviderRegistry extensionProviderRegistry) {

		List<ExtensionProvider> extensionProviders =
			extensionProviderRegistry.getExtensionProviders(
				companyId, className);

		if (ListUtil.isEmpty(extensionProviders)) {
			return null;
		}

		return new EntityExtensionHandler(className, extensionProviders);
	}

	public static Map<String, Serializable> getExtendedProperties(Object dto) {
		try {
			Field field = ReflectionUtil.getDeclaredField(
				dto.getClass(), "_extendedProperties");

			return (Map<String, Serializable>)field.get(dto);
		}
		catch (Exception exception) {
			_log.error(exception);

			return null;
		}
	}

	public static void setExtendedProperties(
		Object dto, Map<String, Serializable> extendedProperties) {

		try {
			Field field = ReflectionUtil.getDeclaredField(
				dto.getClass(), "_extendedProperties");

			field.set(dto, extendedProperties);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(ExtensionUtil.class);

}