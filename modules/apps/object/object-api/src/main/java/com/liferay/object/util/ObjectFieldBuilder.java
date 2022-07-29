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
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class ObjectFieldBuilder {

	public ObjectField build() {
		return _objectField;
	}

	public ObjectFieldBuilder businessType(String businessType) {
		_objectField.setBusinessType(businessType);

		return this;
	}

	public ObjectFieldBuilder dbColumnName(String dbColumnName) {
		_objectField.setDBColumnName(dbColumnName);

		return this;
	}

	public ObjectFieldBuilder dbTableName(String dbTableName) {
		_objectField.setDBTableName(dbTableName);

		return this;
	}

	public ObjectFieldBuilder dbType(String dbType) {
		_objectField.setDBType(dbType);

		return this;
	}

	public ObjectFieldBuilder defaultValue(String defaultValue) {
		_objectField.setDefaultValue(defaultValue);

		return this;
	}

	public ObjectFieldBuilder externalReferenceCode(
		String externalReferenceCode) {

		_objectField.setExternalReferenceCode(externalReferenceCode);

		return this;
	}

	public ObjectFieldBuilder indexed(boolean indexed) {
		_objectField.setIndexed(indexed);

		return this;
	}

	public ObjectFieldBuilder indexedAsKeyword(boolean indexedAsKeyword) {
		_objectField.setIndexedAsKeyword(indexedAsKeyword);

		return this;
	}

	public ObjectFieldBuilder indexedLanguageId(String indexedLanguageId) {
		_objectField.setIndexedLanguageId(indexedLanguageId);

		return this;
	}

	public ObjectFieldBuilder labelMap(Map<Locale, String> labelMap) {
		_objectField.setLabelMap(labelMap);

		return this;
	}

	public ObjectFieldBuilder listTypeDefinitionId(long listTypeDefinitionId) {
		_objectField.setListTypeDefinitionId(listTypeDefinitionId);

		return this;
	}

	public ObjectFieldBuilder name(String name) {
		_objectField.setName(name);

		return this;
	}

	public ObjectFieldBuilder objectDefinitionId(long objectDefinitionId) {
		_objectField.setObjectDefinitionId(objectDefinitionId);

		return this;
	}

	public ObjectFieldBuilder objectFieldId(long objectFieldId) {
		_objectField.setObjectFieldId(objectFieldId);

		return this;
	}

	public ObjectFieldBuilder objectFieldSettings(
		List<ObjectFieldSetting> objectFieldSettings) {

		_objectField.setObjectFieldSettings(objectFieldSettings);

		return this;
	}

	public ObjectFieldBuilder required(boolean required) {
		_objectField.setRequired(required);

		return this;
	}

	public ObjectFieldBuilder state(boolean state) {
		_objectField.setState(state);

		return this;
	}

	public ObjectFieldBuilder system(boolean system) {
		_objectField.setSystem(system);

		return this;
	}

	public ObjectFieldBuilder userId(long userId) {
		_objectField.setUserId(userId);

		return this;
	}

	private final ObjectField _objectField =
		ObjectFieldLocalServiceUtil.createObjectField(0);

}