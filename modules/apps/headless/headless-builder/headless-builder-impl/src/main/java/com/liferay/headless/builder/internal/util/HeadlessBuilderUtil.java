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

import com.liferay.headless.builder.internal.dto.HeadlessBuilderDTO;
import com.liferay.headless.builder.internal.operation.Response;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.PrimaryKeyInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceRegistry;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = {})
public class HeadlessBuilderUtil {

	public static <T> T getInfoItemService(
			String className, Class<T> serviceClass)
		throws Exception {

		T infoItemService = _infoItemServiceRegistry.getFirstInfoItemService(
			serviceClass, className);

		if (infoItemService == null) {
			throw new NoSuchInfoItemException(
				String.format(
					"There is no %s defined for the class name '%s'",
					serviceClass.getSimpleName(), className));
		}

		return infoItemService;
	}

	public static HeadlessBuilderDTO toDTO(
		InfoItemFieldValues infoItemFieldValues, long primaryKey,
		Response response) {

		HeadlessBuilderDTO headlessBuilderDTO = new HeadlessBuilderDTO();

		Map<String, InfoField> infoFields = response.getInfoFields();

		for (Map.Entry<String, InfoField> entry : infoFields.entrySet()) {
			headlessBuilderDTO.put(
				entry.getKey(),
				_getValue(infoItemFieldValues, entry.getValue(), primaryKey));
		}

		headlessBuilderDTO.setName(response.getSchemaName());

		return headlessBuilderDTO;
	}

	@Reference(unbind = "-")
	protected void setInfoItemServiceRegistry(
		InfoItemServiceRegistry infoItemServiceRegistry) {

		_infoItemServiceRegistry = infoItemServiceRegistry;
	}

	private static Object _getValue(
		InfoItemFieldValues infoItemFieldValues, InfoField infoField,
		long primaryKey) {

		if (infoField.getInfoFieldType() instanceof PrimaryKeyInfoFieldType) {
			return primaryKey;
		}

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue(infoField.getName());

		return infoFieldValue.getValue();
	}

	private static InfoItemServiceRegistry _infoItemServiceRegistry;

}