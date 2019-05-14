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

package com.liferay.data.engine.taglib.servlet.taglib.util;

import com.liferay.data.engine.spi.field.type.FieldType;
import com.liferay.data.engine.spi.field.type.FieldTypeTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataEngineTaglibUtil.class)
public class DataEngineTaglibUtil {

	public static JSONObject getDataLayoutJSONObject(
		long dataLayoutId, Locale locale, HttpServletRequest request) {

		if (dataLayoutId == 0) {
			return JSONFactoryUtil.createJSONObject();
		}

		return _instance._getDataLayoutJSONObject(
			dataLayoutId, locale, request);
	}

	public static JSONArray getFieldTypesJSONArray(HttpServletRequest request) {
		return _instance._getFieldTypesJSONArray(request);
	}

	public static String resolveFieldTypesModules() {
		return _instance._resolveFieldTypesModules();
	}

	public static String resolveModule(String moduleName) {
		return _instance._npmResolver.resolveModuleName(moduleName);
	}

	@Activate
	protected void activate() {
		_instance = this;
	}

	@Deactivate
	protected void deactivate() {
		_instance = null;
	}

	private JSONObject _createFieldContext(
		Locale locale, HttpServletRequest request, String type) {

		try {
			String portletNamespace = ParamUtil.getString(
				request, "portletNamespace");

			Class<?> ddmFormFieldTypeSettings = _getDDMFormFieldTypeSettings(
				type);

			DDMForm ddmFormFieldTypeSettingsDDMForm = DDMFormFactory.create(
				ddmFormFieldTypeSettings);

			DDMFormLayout ddmFormFieldTypeSettingsDDMFormLayout =
				DDMFormLayoutFactory.create(ddmFormFieldTypeSettings);

			DDMFormRenderingContext ddmFormRenderingContext =
				new DDMFormRenderingContext();

			DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
				request, ddmFormFieldTypeSettingsDDMForm);

			_setTypeDDMFormFieldValue(ddmFormValues, type);

			ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

			ddmFormRenderingContext.setHttpServletRequest(request);
			ddmFormRenderingContext.setContainerId("settings");
			ddmFormRenderingContext.setLocale(locale);
			ddmFormRenderingContext.setPortletNamespace(portletNamespace);
			ddmFormRenderingContext.setReturnFullContext(true);

			String json = _jsonFactory.looseSerializeDeep(
				_ddmFormTemplateContextFactory.create(
					ddmFormFieldTypeSettingsDDMForm,
					ddmFormFieldTypeSettingsDDMFormLayout,
					ddmFormRenderingContext));

			return _jsonFactory.createJSONObject(json);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return null;
	}

	private JSONObject _getDataLayoutJSONObject(
		long dataLayoutId, Locale locale, HttpServletRequest request) {

		try {
			String portletNamespace = ParamUtil.getString(
				request, "portletNamespace");

			DDMStructureLayout ddmStructureLayout =
				_ddmStructureLayoutLocalService.getDDMStructureLayout(
					dataLayoutId);

			DDMStructureVersion ddmStructureVersion =
				_ddmStructureVersionLocalService.getDDMStructureVersion(
					ddmStructureLayout.getStructureVersionId());

			DDMFormRenderingContext ddmFormRenderingContext =
				new DDMFormRenderingContext();

			ddmFormRenderingContext.setHttpServletRequest(request);
			ddmFormRenderingContext.setContainerId("layoutBuilder");
			ddmFormRenderingContext.setLocale(locale);
			ddmFormRenderingContext.setPortletNamespace(portletNamespace);
			ddmFormRenderingContext.setReturnFullContext(true);

			String json = _jsonFactory.looseSerializeDeep(
				_ddmFormTemplateContextFactory.create(
					ddmStructureVersion.getDDMForm(),
					ddmStructureLayout.getDDMFormLayout(),
					ddmFormRenderingContext));

			return _jsonFactory.createJSONObject(json);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return JSONFactoryUtil.createJSONObject();
	}

	private Class<?> _getDDMFormFieldTypeSettings(String type) {
		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(type);

		return ddmFormFieldType.getDDMFormFieldTypeSettings();
	}

	private JSONObject _getFieldTypeMetadataJSONObject(
		FieldType fieldType, HttpServletRequest request) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		Map<String, Object> fieldTypeProperties =
			_fieldTypeTracker.getFieldTypeProperties(fieldType.getName());

		jsonObject.put(
			"description",
			_getLanguageTerm(
				MapUtil.getString(
					fieldTypeProperties, "data.engine.field.type.description"),
				LocaleThreadLocal.getThemeDisplayLocale())
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
			_getJavaScriptModule(
				MapUtil.getString(
					fieldTypeProperties, "data.engine.field.type.js.module"))
		).put(
			"label",
			_getLanguageTerm(
				MapUtil.getString(
					fieldTypeProperties, "data.engine.field.type.label"),
				LocaleThreadLocal.getThemeDisplayLocale())
		).put(
			"name", fieldType.getName()
		).put(
			"settingsContext",
			_createFieldContext(
				LocaleThreadLocal.getThemeDisplayLocale(), request,
				fieldType.getName())
		);

		return jsonObject;
	}

	private JSONArray _getFieldTypesJSONArray(HttpServletRequest request) {
		Collection<FieldType> fieldTypes = _fieldTypeTracker.getFieldTypes();

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		Stream<FieldType> stream = fieldTypes.stream();

		stream.map(
			fieldType -> _instance._getFieldTypeMetadataJSONObject(
				fieldType, request)
		).forEach(
			jsonArray::put
		);

		return jsonArray;
	}

	private String _getJavaScriptModule(String moduleName) {
		if (Validator.isNull(moduleName)) {
			return StringPool.BLANK;
		}

		return _npmResolver.resolveModuleName(moduleName);
	}

	private String _getLanguageTerm(String key, Locale locale) {
		if (Validator.isNull(key)) {
			return StringPool.BLANK;
		}

		return GetterUtil.getString(
			ResourceBundleUtil.getString(_getResourceBundle(locale), key), key);
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return new AggregateResourceBundle(
			ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass()),
			_portal.getResourceBundle(locale));
	}

	private boolean _hasJavascriptModule(FieldType fieldType) {
		Map<String, Object> fieldTypeProperties =
			_fieldTypeTracker.getFieldTypeProperties(fieldType.getName());

		return fieldTypeProperties.containsKey(
			"data.engine.field.type.js.module");
	}

	private String _resolveFieldTypeModule(FieldType fieldType) {
		Map<String, Object> fieldTypeProperties =
			_fieldTypeTracker.getFieldTypeProperties(fieldType.getName());

		return _getJavaScriptModule(
			MapUtil.getString(
				fieldTypeProperties, "data.engine.field.type.js.module"));
	}

	private String _resolveFieldTypesModules() {
		Collection<FieldType> fieldTypes = _fieldTypeTracker.getFieldTypes();

		Stream<FieldType> stream = fieldTypes.stream();

		return stream.filter(
			_instance::_hasJavascriptModule
		).map(
			_instance::_resolveFieldTypeModule
		).collect(
			Collectors.joining(StringPool.COMMA)
		);
	}

	private void _setTypeDDMFormFieldValue(
		DDMFormValues ddmFormValues, String type) {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			"type");

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		ddmFormFieldValue.setValue(new UnlocalizedValue(type));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataEngineTaglibUtil.class);

	private static DataEngineTaglibUtil _instance;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private FieldTypeTracker _fieldTypeTracker;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

}