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

package com.liferay.data.engine.rest.internal.dto.v1_0.util;

import com.liferay.data.engine.field.type.FieldType;
import com.liferay.data.engine.field.type.FieldTypeTracker;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionRule;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jeyvison Nascimento
 */
public class DataDefinitionUtil {

	public static JSONObject getFieldTypeMetadataJSONObject(
		AcceptLanguage acceptLanguage,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMFormTemplateContextFactory ddmFormTemplateContextFactory,
		DDMFormValuesFactory ddmFormValuesFactory, FieldType fieldType,
		FieldTypeTracker fieldTypeTracker,
		HttpServletRequest httpServletRequest, NPMResolver npmResolver,
		ResourceBundle resourceBundle) {

		Map<String, Object> fieldTypeProperties =
			fieldTypeTracker.getFieldTypeProperties(fieldType.getName());

		return JSONUtil.put(
			"description",
			_translate(
				MapUtil.getString(
					fieldTypeProperties, "data.engine.field.type.description"),
				resourceBundle)
		).put(
			"group",
			MapUtil.getString(
				fieldTypeProperties, "data.engine.field.type.group")
		).put(
			"icon",
			MapUtil.getString(
				fieldTypeProperties, "data.engine.field.type.icon")
		).put(
			"javaScriptModule",
			_resolveModuleName(
				MapUtil.getString(
					fieldTypeProperties, "data.engine.field.type.js.module"),
				npmResolver)
		).put(
			"label",
			_translate(
				MapUtil.getString(
					fieldTypeProperties, "data.engine.field.type.label"),
				resourceBundle)
		).put(
			"name", fieldType.getName()
		).put(
			"settingsContext",
			_createFieldContextJSONObject(
				ddmFormFieldTypeServicesTracker, ddmFormTemplateContextFactory,
				ddmFormValuesFactory, httpServletRequest,
				acceptLanguage.getPreferredLocale(), fieldType.getName())
		).put(
			"system",
			MapUtil.getBoolean(
				fieldTypeProperties, "data.engine.field.type.system")
		);
	}

	public static DataDefinition toDataDefinition(
			DDMStructure ddmStructure, FieldTypeTracker fieldTypeTracker)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			ddmStructure.getDefinition());

		return new DataDefinition() {
			{
				availableLanguageIds = _getAvailableLanguageIds(jsonObject);
				dataDefinitionFields = JSONUtil.toArray(
					jsonObject.getJSONArray("fields"),
					fieldJSONObject -> _toDataDefinitionField(
						fieldTypeTracker, fieldJSONObject),
					DataDefinitionField.class);
				dataDefinitionKey = ddmStructure.getStructureKey();
				dataDefinitionRules = JSONUtil.toArray(
					jsonObject.getJSONArray("rules"),
					ruleJSONObject -> _toDataDefinitionRule(ruleJSONObject),
					DataDefinitionRule.class);
				dateCreated = ddmStructure.getCreateDate();
				dateModified = ddmStructure.getModifiedDate();
				defaultLanguageId = jsonObject.getString("defaultLanguageId");
				description = LocalizedValueUtil.toStringObjectMap(
					ddmStructure.getDescriptionMap());
				id = ddmStructure.getStructureId();
				name = LocalizedValueUtil.toStringObjectMap(
					ddmStructure.getNameMap());
				siteId = ddmStructure.getGroupId();
				storageType = ddmStructure.getStorageType();
				userId = ddmStructure.getUserId();
			}
		};
	}

	public static String toJSON(
			DataDefinition dataDefinition, FieldTypeTracker fieldTypeTracker)
		throws Exception {

		return JSONUtil.put(
			"availableLanguageIds",
			JSONUtil.toJSONArray(
				dataDefinition.getAvailableLanguageIds(),
				languageId -> languageId)
		).put(
			"defaultLanguageId", dataDefinition.getDefaultLanguageId()
		).put(
			"fields",
			JSONUtil.toJSONArray(
				dataDefinition.getDataDefinitionFields(),
				dataDefinitionField -> _toJSONObject(
					dataDefinitionField, fieldTypeTracker))
		).put(
			"rules",
			JSONUtil.toJSONArray(
				dataDefinition.getDataDefinitionRules(),
				dataDefinitionRule -> _toJSONObject(dataDefinitionRule))
		).toString();
	}

	private static JSONObject _createFieldContextJSONObject(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMFormTemplateContextFactory ddmFormTemplateContextFactory,
		DDMFormValuesFactory ddmFormValuesFactory,
		HttpServletRequest httpServletRequest, Locale locale, String type) {

		try {
			DDMFormFieldType ddmFormFieldType =
				ddmFormFieldTypeServicesTracker.getDDMFormFieldType(type);

			DDMForm ddmFormFieldTypeSettingsDDMForm = DDMFormFactory.create(
				ddmFormFieldType.getDDMFormFieldTypeSettings());

			DDMFormRenderingContext ddmFormRenderingContext =
				new DDMFormRenderingContext();

			ddmFormRenderingContext.setContainerId("settings");

			DDMFormValues ddmFormValues = ddmFormValuesFactory.create(
				httpServletRequest, ddmFormFieldTypeSettingsDDMForm);

			_setTypeDDMFormFieldValue(ddmFormValues, type);

			ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

			ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);
			ddmFormRenderingContext.setLocale(locale);
			ddmFormRenderingContext.setPortletNamespace(
				ParamUtil.getString(httpServletRequest, "portletNamespace"));
			ddmFormRenderingContext.setReturnFullContext(true);

			return JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerializeDeep(
					ddmFormTemplateContextFactory.create(
						ddmFormFieldTypeSettingsDDMForm,
						DDMFormLayoutFactory.create(
							ddmFormFieldType.getDDMFormFieldTypeSettings()),
						ddmFormRenderingContext)));
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return null;
	}

	private static String[] _getAvailableLanguageIds(JSONObject jsonObject) {
		return JSONUtil.toStringArray(
			jsonObject.getJSONArray("availableLanguageIds"));
	}

	private static String _resolveModuleName(
		String moduleName, NPMResolver npmResolver) {

		if (Validator.isNull(moduleName)) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(moduleName);
	}

	private static void _setTypeDDMFormFieldValue(
		DDMFormValues ddmFormValues, String type) {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			"type");

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		ddmFormFieldValue.setValue(new UnlocalizedValue(type));
	}

	private static DataDefinitionField _toDataDefinitionField(
			FieldTypeTracker fieldTypeTracker, JSONObject jsonObject)
		throws Exception {

		if (jsonObject.has("type")) {
			FieldType fieldType = fieldTypeTracker.getFieldType(
				jsonObject.getString("type"));

			return DataDefinitionFieldUtil.toDataDefinitionField(
				fieldType.deserialize(fieldTypeTracker, jsonObject));
		}

		return new DataDefinitionField();
	}

	private static DataDefinitionRule _toDataDefinitionRule(
		JSONObject jsonObject) {

		return new DataDefinitionRule() {
			{
				dataDefinitionFieldNames = JSONUtil.toStringArray(
					jsonObject.getJSONArray("fields"));
				dataDefinitionRuleParameters =
					DataDefinitionRuleParameterUtil.
						toDataDefinitionRuleParameters(
							jsonObject.getJSONObject("parameters"));
				name = jsonObject.getString("name");
				ruleType = jsonObject.getString("ruleType");
			}
		};
	}

	private static JSONObject _toJSONObject(
			DataDefinitionField dataDefinitionField,
			FieldTypeTracker fieldTypeTracker)
		throws Exception {

		FieldType fieldType = fieldTypeTracker.getFieldType(
			dataDefinitionField.getFieldType());

		return fieldType.toJSONObject(
			fieldTypeTracker,
			DataDefinitionFieldUtil.toSPIDataDefinitionField(
				dataDefinitionField));
	}

	private static JSONObject _toJSONObject(
			DataDefinitionRule dataDefinitionRule)
		throws Exception {

		return JSONUtil.put(
			"fields",
			JSONFactoryUtil.createJSONArray(
				dataDefinitionRule.getDataDefinitionFieldNames())
		).put(
			"name", dataDefinitionRule.getName()
		).put(
			"parameters",
			DataDefinitionRuleParameterUtil.toJSONObject(
				dataDefinitionRule.getDataDefinitionRuleParameters())
		).put(
			"ruleType", dataDefinitionRule.getRuleType()
		);
	}

	private static String _translate(
		String key, ResourceBundle resourceBundle) {

		if (Validator.isNull(key)) {
			return StringPool.BLANK;
		}

		return GetterUtil.getString(
			ResourceBundleUtil.getString(resourceBundle, key), key);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataDefinitionUtil.class);

}