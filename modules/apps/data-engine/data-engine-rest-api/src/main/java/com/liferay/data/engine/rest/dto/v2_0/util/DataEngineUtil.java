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

package com.liferay.data.engine.rest.dto.v2_0.util;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;

import jodd.bean.BeanCopy;

import jodd.typeconverter.TypeConverterManager;

/**
 * @author Leonardo Barros
 */
public class DataEngineUtil {

	public static DataDefinition toDataDefinition(Object value) {
		return (DataDefinition)TypeConverterManager.convertType(
			value, DataDefinition.class);
	}

	static {
		TypeConverterManager.register(
			DataDefinition.class, value -> _copy(value, DataDefinition.class));
		TypeConverterManager.register(
			DataDefinitionField.class,
			value -> _copy(value, DataDefinitionField.class));
		TypeConverterManager.register(
			DataLayout.class, value -> _copy(value, DataLayout.class));
		TypeConverterManager.register(
			DataLayoutColumn.class,
			value -> _copy(value, DataLayoutColumn.class));
		TypeConverterManager.register(
			DataLayoutPage.class, value -> _copy(value, DataLayoutPage.class));
		TypeConverterManager.register(
			DataLayoutRow.class, value -> _copy(value, DataLayoutRow.class));
	}

	private static <T> T _copy(Object value, Class<?> destinationClass) {
		try {
			Object newInstance = destinationClass.newInstance();

			BeanCopy beanCopy = new BeanCopy(value, newInstance);

			beanCopy.copy();

			return (T)newInstance;
		}
		catch (Exception exception) {
			return null;
		}
	}

}