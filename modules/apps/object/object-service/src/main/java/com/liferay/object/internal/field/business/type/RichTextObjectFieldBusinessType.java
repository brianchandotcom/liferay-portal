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

package com.liferay.object.internal.field.business.type;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.render.ObjectFieldRenderingContext;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT,
	service = ObjectFieldBusinessType.class
)
public class RichTextObjectFieldBusinessType
	implements ObjectFieldBusinessType {

	@Override
	public String getDBType() {
		return ObjectFieldConstants.DB_TYPE_CLOB;
	}

	@Override
	public String getDDMFormFieldTypeName() {
		return DDMFormFieldTypeConstants.RICH_TEXT;
	}

	@Override
	public String getDescription(Locale locale) {
		return _language.get(locale, "create-rich-text-content");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "rich-text");
	}

	@Override
	public String getName() {
		return ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT;
	}

	@Override
	public Map<String, Object> getProperties(
		ObjectField objectField,
		ObjectFieldRenderingContext objectFieldRenderingContext) {

		return HashMapBuilder.<String, Object>put(
			"localizedObjectField", objectField.isLocalized()
		).build();
	}

	@Override
	public PropertyDefinition.PropertyType getPropertyType() {
		return PropertyDefinition.PropertyType.TEXT;
	}

	@Override
	public Object getValue(
			ObjectField objectField, long userId, Map<String, Object> values)
		throws PortalException {

		ObjectDefinition objectDefinition = objectField.getObjectDefinition();

		return SanitizerUtil.sanitize(
			objectField.getCompanyId(), 0, objectField.getUserId(),
			objectDefinition.getClassName(), 0, ContentTypes.TEXT_HTML,
			Sanitizer.MODE_ALL,
			String.valueOf(
				ObjectFieldBusinessType.super.getValue(
					objectField, userId, values)),
			null);
	}

	@Reference
	private Language _language;

}