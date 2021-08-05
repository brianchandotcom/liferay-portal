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
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public abstract class BaseSystemObjectDefinitionMetadata
	implements SystemObjectDefinitionMetadata {

	protected ObjectField createObjectField(
		Map<Locale, String> labelMap, String name, boolean required,
		String type) {

		return createObjectField(null, labelMap, name, required, type);
	}

	protected ObjectField createObjectField(
		String dbColumnName, Map<Locale, String> labelMap, String name,
		boolean required, String type) {

		ObjectField objectField = objectFieldLocalService.createObjectField(0);

		objectField.setDBColumnName(dbColumnName);
		objectField.setIndexed(false);
		objectField.setIndexedAsKeyword(false);
		objectField.setLabelMap(labelMap);
		objectField.setName(name);
		objectField.setRequired(required);
		objectField.setType(type);

		return objectField;
	}

	protected ObjectField createObjectField(
		String label, String name, boolean required, String type) {

		return createObjectField(
			null, Collections.singletonMap(LocaleUtil.getSiteDefault(), label),
			name, required, type);
	}

	@Reference
	protected ObjectFieldLocalService objectFieldLocalService;

}