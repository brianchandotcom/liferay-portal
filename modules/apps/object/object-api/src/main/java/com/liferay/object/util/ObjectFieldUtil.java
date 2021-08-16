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

package com.liferay.object.util;

import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * @author Guilherme Camacho
 */
public class ObjectFieldUtil {

	public static ObjectField createObjectField(
		boolean indexed, boolean indexedAsKeyword,
		String label, String name, boolean required, String type) {

		return createObjectField(
			null, indexed, indexedAsKeyword, null, label, name, required, type);
	}

	public static ObjectField createObjectField(
		String label, String name, boolean required,
		String type) {

		return createObjectField(
			null, label, name, required, type);
	}

	public static ObjectField createObjectField(
		String dbColumnName, boolean indexed, boolean indexedAsKeyword,
		String indexedLanguageId, String label, String name,
		boolean required, String type) {

		ObjectField objectField = ObjectFieldLocalServiceUtil.createObjectField(
			0);

		objectField.setDBColumnName(dbColumnName);
		objectField.setIndexed(indexed);
		objectField.setIndexedAsKeyword(indexedAsKeyword);
		objectField.setIndexedLanguageId(indexedLanguageId);
		objectField.setLabelMap(LocalizedMapUtil.getLocalizedMap(label));
		objectField.setName(name);
		objectField.setRequired(required);
		objectField.setType(type);

		return objectField;
	}

	public static ObjectField createObjectField(
		String dbColumnName, String label, String name,
		boolean required, String type) {

		return createObjectField(
			dbColumnName, false, false, null, label, name, required, type);
	}

}