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

package com.liferay.headless.builder.internal.util;

import com.liferay.headless.builder.internal.operation.Operation;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.osgi.util.service.Snapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Carlos Correa
 */
public class HeadlessBuilderUtil {

	public static Map<String, Object> getEntity(
		InfoItemFieldValues infoItemFieldValues, Operation.Response response) {

		Map<String, Object> entity = new HashMap<>();

		Map<String, InfoField> infoFields = response.getInfoFields();

		for (Map.Entry<String, InfoField> entry : infoFields.entrySet()) {
			entity.put(
				entry.getKey(),
				_getValue(infoItemFieldValues, entry.getValue()));
		}

		return entity;
	}

	public static <T> T getInfoItemService(
			String className, Class<T> serviceClass)
		throws Exception {

		InfoItemServiceRegistry infoItemServiceRegistry =
			_infoItemServiceRegistrySnapshot.get();

		T infoItemService = infoItemServiceRegistry.getFirstInfoItemService(
			serviceClass, className);

		if (infoItemService == null) {
			throw new NoSuchInfoItemException(
				serviceClass.getSimpleName() + " is not defined for " +
					className);
		}

		return infoItemService;
	}

	private static Object _getValue(
		InfoItemFieldValues infoItemFieldValues, InfoField infoField) {

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue(infoField.getName());

		return infoFieldValue.getValue();
	}

	private static final Snapshot<InfoItemServiceRegistry>
		_infoItemServiceRegistrySnapshot = new Snapshot<>(
			HeadlessBuilderUtil.class, InfoItemServiceRegistry.class);

}