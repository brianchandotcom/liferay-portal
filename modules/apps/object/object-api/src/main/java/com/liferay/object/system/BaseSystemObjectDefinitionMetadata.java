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

package com.liferay.object.system;

import com.liferay.object.model.ObjectField;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public abstract class BaseSystemObjectDefinitionMetadata
	implements SystemObjectDefinitionMetadata {

	@Override
	public String getModelClassName() {
		Class<?> modelClass = getModelClass();

		return modelClass.getName();
	}

	@Override
	public String getName() {
		Table table = getTable();

		String tableName = table.getName();

		if (tableName.endsWith("_")) {
			return tableName.substring(0, tableName.length() - 1);
		}

		return tableName;
	}

	@Override
	public String getRESTDTOIdPropertyName() {
		return "id";
	}

	protected Map<Locale, String> createLabelMap(String labelKey) {
		return LocalizedMapUtil.getLocalizedMap(_translate(labelKey));
	}

	protected ObjectField createObjectField(
		String businessType, String dbType, String labelKey, String name,
		boolean required, boolean system) {

		return createObjectField(
			businessType, null, dbType, labelKey, name, required, system);
	}

	protected ObjectField createObjectField(
		String businessType, String dbColumnName, String dbType,
		String labelKey, String name, boolean required, boolean system) {

		return ObjectFieldUtil.createObjectField(
			0, businessType, dbColumnName, dbType, false, false, null,
			_translate(labelKey), name, required, system);
	}

	private String _translate(String labelKey) {
		return LanguageUtil.get(LocaleUtil.getDefault(), labelKey);
	}

}