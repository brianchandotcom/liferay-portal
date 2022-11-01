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

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.render.ObjectFieldRenderingContext;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true,
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_FORMULA,
	service = {
		FormulaObjectFieldBusinessType.class, ObjectFieldBusinessType.class
	}
)
public class FormulaObjectFieldBusinessType implements ObjectFieldBusinessType {

	@Override
	public Set<String> getAllowedObjectFieldSettingsNames() {
		return SetUtil.fromArray("output", "script");
	}

	@Override
	public String getDBType() {
		return ObjectFieldConstants.DB_TYPE_STRING;
	}

	@Override
	public String getDDMFormFieldTypeName() {
		return DDMFormFieldTypeConstants.TEXT;
	}

	@Override
	public String getDescription(Locale locale) {
		return _language.get(
			locale,
			"add-an-algorithm-that-derives-its-value-from-other-fields");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "formula");
	}

	@Override
	public String getName() {
		return ObjectFieldConstants.BUSINESS_TYPE_FORMULA;
	}

	@Override
	public Map<String, Object> getProperties(
			ObjectField objectField,
			ObjectFieldRenderingContext objectFieldRenderingContext)
		throws PortalException {

		Map<String, Object> properties = new HashMap<>();

		ListUtil.isNotEmptyForEach(
			_objectFieldSettingLocalService.getObjectFieldObjectFieldSettings(
				objectField.getObjectFieldId()),
			objectFieldSetting -> properties.put(
				objectFieldSetting.getName(), objectFieldSetting.getValue()));

		return properties;
	}

	@Override
	public PropertyDefinition.PropertyType getPropertyType() {
		return PropertyDefinition.PropertyType.TEXT;
	}

	@Override
	public Set<String> getRequiredObjectFieldSettingsNames() {
		return SetUtil.fromArray("output");
	}

	@Override
	public void validateObjectFieldSettings(
			long objectDefinitionId, String objectFieldName,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		ObjectFieldBusinessType.super.validateObjectFieldSettings(
			objectDefinitionId, objectFieldName, objectFieldSettings);

		Map<String, String> objectFieldSettingsValues = new HashMap<>();

		objectFieldSettings.forEach(
			objectFieldSetting -> objectFieldSettingsValues.put(
				objectFieldSetting.getName(), objectFieldSetting.getValue()));

		String script = objectFieldSettingsValues.get("script");

		if (script == null) {
			return;
		}

		try {
			_ddmExpressionFactory.createExpression(
				CreateExpressionRequest.Builder.newBuilder(
					script
				).build());
		}
		catch (DDMExpressionException ddmExpressionException) {
			throw new ScriptingException(ddmExpressionException);
		}
	}

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

	@Reference
	private Language _language;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

}