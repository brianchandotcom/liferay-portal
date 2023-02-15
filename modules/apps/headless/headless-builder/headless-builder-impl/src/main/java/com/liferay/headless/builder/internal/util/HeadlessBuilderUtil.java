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

import com.liferay.headless.builder.internal.operation.HeadlessBuilderEntry;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemServiceRegistry;

import java.util.Collection;
import java.util.HashMap;
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

	public static HeadlessBuilderEntry toHeadlessBuilderEntry(
		Collection<InfoFieldValue<Object>> infoFieldValues, long primaryKey,
		String schemaName) {

		HeadlessBuilderEntry headlessBuilderEntry = new HeadlessBuilderEntry();

		Map<String, Object> content = new HashMap<>();

		for (InfoFieldValue<Object> infoFieldValue : infoFieldValues) {
			InfoField infoField = infoFieldValue.getInfoField();

			content.put(infoField.getName(), infoFieldValue.getValue());
		}

		headlessBuilderEntry.setContent(content);
		headlessBuilderEntry.setName(schemaName);
		headlessBuilderEntry.setPrimaryKey(primaryKey);

		return headlessBuilderEntry;
	}

	@Reference(unbind = "-")
	protected void setInfoItemServiceRegistry(
		InfoItemServiceRegistry infoItemServiceRegistry) {

		_infoItemServiceRegistry = infoItemServiceRegistry;
	}

	private static InfoItemServiceRegistry _infoItemServiceRegistry;

}