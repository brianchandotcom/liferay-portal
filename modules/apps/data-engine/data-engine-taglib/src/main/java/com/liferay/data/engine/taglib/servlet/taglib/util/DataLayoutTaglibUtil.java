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

import com.liferay.data.engine.field.type.FieldType;
import com.liferay.data.engine.field.type.FieldTypeTracker;
import com.liferay.data.engine.renderer.DataLayoutRenderer;
import com.liferay.data.engine.renderer.DataLayoutRendererContext;
import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.client.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.client.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.client.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.client.resource.v1_0.DataLayoutResource;
import com.liferay.data.engine.rest.client.resource.v1_0.DataRecordResource;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextFactory;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataLayoutTaglibUtil.class)
public class DataLayoutTaglibUtil {

	public static Set<Locale> getAvailableLocales(
		long dataLayoutId, HttpServletRequest httpServletRequest) {

		return _dataLayoutTaglibUtil._getAvailableLocales(
			dataLayoutId, httpServletRequest);
	}

	public static JSONObject getDataLayoutJSONObject(
		Set<Locale> availableLocales, Long dataLayoutId,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return _dataLayoutTaglibUtil._getDataLayoutJSONObject(
			availableLocales, dataLayoutId, httpServletRequest,
			httpServletResponse);
	}

	public static Map<String, Object> getDataRecordValues(
			long dataRecordId, HttpServletRequest httpServletRequest)
		throws Exception {

		return _dataLayoutTaglibUtil._getDataRecordValues(
			dataRecordId, httpServletRequest);
	}

	public static JSONArray getFieldTypesJSONArray(
		HttpServletRequest httpServletRequest) {

		return _dataLayoutTaglibUtil._getFieldTypesJSONArray(
			httpServletRequest);
	}

	public static String renderDataLayout(
			long dataLayoutId,
			DataLayoutRendererContext dataLayoutRendererContext)
		throws Exception {

		return _dataLayoutTaglibUtil._dataLayoutRenderer.render(
			dataLayoutId, dataLayoutRendererContext);
	}

	public static String resolveFieldTypesModules() {
		return _dataLayoutTaglibUtil._resolveFieldTypesModules();
	}

	public static String resolveModule(String moduleName) {
		return _dataLayoutTaglibUtil._npmResolver.resolveModuleName(moduleName);
	}

	@Activate
	protected void activate() {
		_dataLayoutTaglibUtil = this;
	}

	@Deactivate
	protected void deactivate() {
		_dataLayoutTaglibUtil = null;
	}

	private JSONObject _createFieldContext(
		HttpServletRequest httpServletRequest, Locale locale, String type) {

		try {
			Class<?> ddmFormFieldTypeSettings = _getDDMFormFieldTypeSettings(
				type);

			DDMForm ddmFormFieldTypeSettingsDDMForm = DDMFormFactory.create(
				ddmFormFieldTypeSettings);

			DDMFormRenderingContext ddmFormRenderingContext =
				new DDMFormRenderingContext();

			ddmFormRenderingContext.setContainerId("settings");

			DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
				httpServletRequest, ddmFormFieldTypeSettingsDDMForm);

			_setTypeDDMFormFieldValue(ddmFormValues, type);

			ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

			ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);
			ddmFormRenderingContext.setLocale(locale);
			ddmFormRenderingContext.setPortletNamespace(
				ParamUtil.getString(httpServletRequest, "portletNamespace"));
			ddmFormRenderingContext.setReturnFullContext(true);

			return _jsonFactory.createJSONObject(
				_jsonFactory.looseSerializeDeep(
					_ddmFormTemplateContextFactory.create(
						ddmFormFieldTypeSettingsDDMForm,
						DDMFormLayoutFactory.create(ddmFormFieldTypeSettings),
						ddmFormRenderingContext)));
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return null;
	}

	private Set<Locale> _getAvailableLocales(
		long dataLayoutId, HttpServletRequest httpServletRequest) {

		if (dataLayoutId == 0) {
			return new HashSet() {
				{
					add(LocaleThreadLocal.getDefaultLocale());
				}
			};
		}

		try {
			DataLayout dataLayout = _getDataLayout(
				dataLayoutId, httpServletRequest);

			DataDefinition dataDefinition = _getDataDefinition(
				dataLayout.getDataDefinitionId(), httpServletRequest);

			return Stream.of(
				dataDefinition.getAvailableLanguageIds()
			).map(
				LocaleUtil::fromLanguageId
			).collect(
				Collectors.toSet()
			);
		}
		catch (Exception e) {
			return new HashSet() {
				{
					add(LocaleThreadLocal.getDefaultLocale());
				}
			};
		}
	}

	private DataDefinition _getDataDefinition(
			long dataDefinitionId, HttpServletRequest httpServletRequest)
		throws Exception {

		String sessionId = CookieKeys.getCookie(
			httpServletRequest, CookieKeys.JSESSIONID);

		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).endpoint(
				_portal.getHost(httpServletRequest),
				httpServletRequest.getServerPort(),
				httpServletRequest.getScheme()
			).header(
				"Cookie", "JSESSIONID=" + sessionId
			).parameter(
				"p_auth", AuthTokenUtil.getToken(httpServletRequest)
			).build();

		return dataDefinitionResource.getDataDefinition(dataDefinitionId);
	}

	private DataLayout _getDataLayout(
			long dataLayoutId, HttpServletRequest httpServletRequest)
		throws Exception {

		DataLayoutResource dataLayoutResource = DataLayoutResource.builder(
		).endpoint(
			_portal.getHost(httpServletRequest),
			httpServletRequest.getServerPort(), httpServletRequest.getScheme()
		).header(
			"Cookie",
			"JSESSIONID=" +
				CookieKeys.getCookie(httpServletRequest, CookieKeys.JSESSIONID)
		).parameter(
			"p_auth", AuthTokenUtil.getToken(httpServletRequest)
		).build();

		return dataLayoutResource.getDataLayout(dataLayoutId);
	}

	private JSONObject _getDataLayoutJSONObject(
		Set<Locale> availableLocales, Long dataLayoutId,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (dataLayoutId == null) {
			return _jsonFactory.createJSONObject();
		}

		try {
			DataLayout dataLayout = _getDataLayout(
				dataLayoutId, httpServletRequest);

			DataLayoutDDMFormAdapter dataLayoutDDMFormAdapter =
				new DataLayoutDDMFormAdapter(
					availableLocales, dataLayout, httpServletRequest,
					httpServletResponse);

			return dataLayoutDDMFormAdapter.toJSONObject();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return _jsonFactory.createJSONObject();
		}
	}

	private Map<String, Object> _getDataRecordValues(
			long dataRecordId, HttpServletRequest httpServletRequest)
		throws Exception {

		if (dataRecordId == 0) {
			return Collections.emptyMap();
		}

		DataRecordResource dataRecordResource = DataRecordResource.builder(
		).endpoint(
			_portal.getHost(httpServletRequest),
			httpServletRequest.getServerPort(), httpServletRequest.getScheme()
		).header(
			"Cookie",
			"JSESSIONID=" +
				CookieKeys.getCookie(httpServletRequest, CookieKeys.JSESSIONID)
		).parameter(
			"p_auth", AuthTokenUtil.getToken(httpServletRequest)
		).build();

		DataRecord dataRecord = dataRecordResource.getDataRecord(dataRecordId);

		return dataRecord.getDataRecordValues();
	}

	private Class<?> _getDDMFormFieldTypeSettings(String type) {
		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(type);

		return ddmFormFieldType.getDDMFormFieldTypeSettings();
	}

	private JSONObject _getFieldTypeMetadataJSONObject(
		FieldType fieldType, HttpServletRequest httpServletRequest) {

		Map<String, Object> fieldTypeProperties =
			_fieldTypeTracker.getFieldTypeProperties(fieldType.getName());

		return JSONUtil.put(
			"description",
			_getLanguageTerm(
				MapUtil.getString(
					fieldTypeProperties, "data.engine.field.type.description"),
				LocaleThreadLocal.getThemeDisplayLocale())
		).put(
			"displayOrder",
			MapUtil.getInteger(
				fieldTypeProperties, "data.engine.field.type.display.order")
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
				httpServletRequest, LocaleThreadLocal.getThemeDisplayLocale(),
				fieldType.getName())
		).put(
			"system",
			MapUtil.getBoolean(
				fieldTypeProperties, "data.engine.field.type.system")
		);
	}

	private JSONArray _getFieldTypesJSONArray(
		HttpServletRequest httpServletRequest) {

		Collection<FieldType> fieldTypes = _fieldTypeTracker.getFieldTypes();

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		Stream<FieldType> stream = fieldTypes.stream();

		stream.map(
			fieldType -> _dataLayoutTaglibUtil._getFieldTypeMetadataJSONObject(
				fieldType, httpServletRequest)
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
		return _getJavaScriptModule(
			MapUtil.getString(
				_fieldTypeTracker.getFieldTypeProperties(fieldType.getName()),
				"data.engine.field.type.js.module"));
	}

	private String _resolveFieldTypesModules() {
		Collection<FieldType> fieldTypes = _fieldTypeTracker.getFieldTypes();

		Stream<FieldType> stream = fieldTypes.stream();

		return stream.filter(
			_dataLayoutTaglibUtil::_hasJavascriptModule
		).map(
			_dataLayoutTaglibUtil::_resolveFieldTypeModule
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
		DataLayoutTaglibUtil.class);

	private static DataLayoutTaglibUtil _dataLayoutTaglibUtil;

	@Reference
	private DataLayoutRenderer _dataLayoutRenderer;

	@Reference
	private DDMFormBuilderContextFactory _ddmFormBuilderContextFactory;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private FieldTypeTracker _fieldTypeTracker;

	@Reference(target = "(ddm.form.deserializer.type=json)")
	private DDMFormDeserializer _jsonDDMFormDeserializer;

	@Reference(target = "(ddm.form.layout.deserializer.type=json)")
	private DDMFormLayoutDeserializer _jsonDDMFormLayoutDeserializer;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

	private class DataLayoutDDMFormAdapter {

		public DataLayoutDDMFormAdapter(
			Set<Locale> availableLocales, DataLayout dataLayout,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

			_availableLocales = availableLocales;
			_dataLayout = dataLayout;
			_httpServletRequest = httpServletRequest;
			_httpServletResponse = httpServletResponse;
		}

		public JSONObject toJSONObject() throws Exception {
			if (_dataLayout.getId() == null) {
				return _jsonFactory.createJSONObject();
			}

			DDMForm ddmForm = _getDDMForm();

			Map<String, Object> ddmFormTemplateContext =
				_ddmFormTemplateContextFactory.create(
					ddmForm, _getDDMFormLayout(),
					new DDMFormRenderingContext() {
						{
							setHttpServletRequest(_httpServletRequest);
							setHttpServletResponse(_httpServletResponse);
							setLocale(_httpServletRequest.getLocale());
							setPortletNamespace(StringPool.BLANK);
						}
					});

			_populateDDMFormFieldSettingsContext(
				ddmForm.getDDMFormFieldsMap(true), ddmFormTemplateContext);

			return _jsonFactory.createJSONObject(
				_jsonFactory.looseSerializeDeep(ddmFormTemplateContext));
		}

		private Map<String, Object> _createDDMFormFieldSettingContext(
				DDMFormField ddmFormField)
			throws Exception {

			DDMFormFieldType ddmFormFieldType =
				_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(
					ddmFormField.getType());

			DDMForm ddmForm = DDMFormFactory.create(
				ddmFormFieldType.getDDMFormFieldTypeSettings());

			return _ddmFormTemplateContextFactory.create(
				ddmForm,
				DDMFormLayoutFactory.create(
					ddmFormFieldType.getDDMFormFieldTypeSettings()),
				new DDMFormRenderingContext() {
					{
						setContainerId("settings");
						setDDMFormValues(
							_createDDMFormFieldSettingContextDDMFormValues(
								ddmFormField, ddmForm));
						setHttpServletRequest(_httpServletRequest);
						setHttpServletResponse(_httpServletResponse);
						setLocale(_httpServletRequest.getLocale());
						setPortletNamespace(StringPool.BLANK);
					}
				});
		}

		private DDMFormValues _createDDMFormFieldSettingContextDDMFormValues(
				DDMFormField ddmFormField,
				DDMForm ddmFormFieldTypeSettingsDDMForm)
			throws Exception {

			DDMFormValues ddmFormValues = new DDMFormValues(
				ddmFormFieldTypeSettingsDDMForm);

			DDMForm ddmForm = ddmFormField.getDDMForm();
			Map<String, Object> ddmFormFieldProperties =
				ddmFormField.getProperties();

			for (DDMFormField ddmFormFieldTypeSetting :
					ddmFormFieldTypeSettingsDDMForm.getDDMFormFields()) {

				ddmFormValues.addDDMFormFieldValue(
					new DDMFormFieldValue() {
						{
							setName(ddmFormFieldTypeSetting.getName());
							setValue(
								_createDDMFormFieldValue(
									ddmForm.getAvailableLocales(),
									ddmFormFieldTypeSetting,
									ddmFormFieldProperties.get(
										ddmFormFieldTypeSetting.getName())));
						}
					});
			}

			return ddmFormValues;
		}

		private Value _createDDMFormFieldValue(
			DDMFormFieldValidation ddmFormFieldValidation) {

			if (ddmFormFieldValidation == null) {
				return new UnlocalizedValue(StringPool.BLANK);
			}

			return new UnlocalizedValue(
				JSONUtil.put(
					"errorMessage", ddmFormFieldValidation.getErrorMessage()
				).put(
					"expression", ddmFormFieldValidation.getExpression()
				).toString());
		}

		private Value _createDDMFormFieldValue(
				Set<Locale> availableLocales,
				DDMFormField ddmFormFieldTypeSetting, Object propertyValue)
			throws Exception {

			if (ddmFormFieldTypeSetting.isLocalizable()) {
				return (LocalizedValue)propertyValue;
			}

			if (Objects.equals(
					ddmFormFieldTypeSetting.getDataType(), "ddm-options")) {

				return _createDDMFormFieldValue(
					availableLocales, (DDMFormFieldOptions)propertyValue);
			}
			else if (Objects.equals(
						ddmFormFieldTypeSetting.getType(), "validation")) {

				return _createDDMFormFieldValue(
					(DDMFormFieldValidation)propertyValue);
			}

			return new UnlocalizedValue(String.valueOf(propertyValue));
		}

		private Value _createDDMFormFieldValue(
				Set<Locale> availableLocales,
				DDMFormFieldOptions ddmFormFieldOptions)
			throws Exception {

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			for (Locale availableLocale : availableLocales) {
				jsonObject.put(
					LocaleUtil.toLanguageId(availableLocale),
					JSONUtil.toJSONArray(
						ddmFormFieldOptions.getOptionsValues(),
						optionValue -> {
							LocalizedValue localizedValue =
								ddmFormFieldOptions.getOptionLabels(
									optionValue);

							return JSONUtil.put(
								"label",
								localizedValue.getString(availableLocale)
							).put(
								"value", optionValue
							);
						}));
			}

			return new UnlocalizedValue(jsonObject.toString());
		}

		private DDMForm _deserializeDDMForm(String content) {
			DDMFormDeserializerDeserializeRequest.Builder builder =
				DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
					content);

			DDMFormDeserializerDeserializeResponse
				ddmFormDeserializerDeserializeResponse =
					_jsonDDMFormDeserializer.deserialize(builder.build());

			return ddmFormDeserializerDeserializeResponse.getDDMForm();
		}

		private DDMFormLayout _deserializeDDMFormLayout(String content) {
			DDMFormLayoutDeserializerDeserializeRequest.Builder builder =
				DDMFormLayoutDeserializerDeserializeRequest.Builder.newBuilder(
					content);

			DDMFormLayoutDeserializerDeserializeResponse
				ddmFormLayoutDeserializerDeserializeResponse =
					_jsonDDMFormLayoutDeserializer.deserialize(builder.build());

			return ddmFormLayoutDeserializerDeserializeResponse.
				getDDMFormLayout();
		}

		private DDMForm _getDDMForm() throws Exception {
			DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
				_dataLayout.getDataDefinitionId());

			String dataDefinitionJSON = ddmStructure.getDefinition();

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				StringUtil.replace(
					dataDefinitionJSON, "defaultValue", "predefinedValue"));

			jsonObject = jsonObject.put(
				"availableLanguageIds",
				JSONUtil.toJSONArray(
					_availableLocales,
					availableLocale -> LanguageUtil.getLanguageId(
						availableLocale))
			).put(
				"defaultLanguageId", ddmStructure.getDefaultLanguageId()
			);

			_transformOptions(jsonObject, "columns");
			_transformOptions(jsonObject, "options");
			_transformOptions(jsonObject, "rows");

			return _deserializeDDMForm(jsonObject.toJSONString());
		}

		private DDMFormLayout _getDDMFormLayout() throws Exception {
			DDMStructureLayout ddmStructureLayout =
				_ddmStructureLayoutLocalService.getStructureLayout(
					_dataLayout.getId());

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				StringUtil.replace(
					ddmStructureLayout.getDefinition(),
					new String[] {
						"columnSize", "dataLayoutColumns", "dataLayoutPages",
						"dataLayoutRows"
					},
					new String[] {"size", "columns", "pages", "rows"}));

			return _deserializeDDMFormLayout(jsonObject.toJSONString());
		}

		private void _populateDDMFormFieldSettingsContext(
				Map<String, DDMFormField> ddmFormFieldsMap,
				Map<String, Object> ddmFormTemplateContext)
			throws Exception {

			UnsafeConsumer<Map<String, Object>, Exception> unsafeConsumer =
				field -> {
					String fieldName = MapUtil.getString(field, "fieldName");

					field.put(
						"settingsContext",
						_createDDMFormFieldSettingContext(
							ddmFormFieldsMap.get(fieldName)));
				};

			List<Map<String, Object>> pages =
				(List<Map<String, Object>>)ddmFormTemplateContext.get("pages");

			for (Map<String, Object> page : pages) {
				List<Map<String, Object>> rows =
					(List<Map<String, Object>>)page.get("rows");

				for (Map<String, Object> row : rows) {
					List<Map<String, Object>> columns =
						(List<Map<String, Object>>)row.get("columns");

					for (Map<String, Object> column : columns) {
						List<Map<String, Object>> fields =
							(List<Map<String, Object>>)column.get("fields");

						for (Map<String, Object> field : fields) {
							unsafeConsumer.accept(field);
						}
					}
				}
			}
		}

		private void _transformOptions(JSONObject jsonObject, String key)
			throws Exception {

			JSONArray jsonArray = jsonObject.getJSONArray("fields");

			if (jsonArray == null) {
				return;
			}

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject fieldJSONObject = jsonArray.getJSONObject(i);

				if (!fieldJSONObject.has(key)) {
					continue;
				}

				Map<String, JSONObject> optionLabelsJSONObjects =
					new TreeMap<>();

				JSONObject optionsJSONObject = fieldJSONObject.getJSONObject(
					key);

				Iterator<String> keys = optionsJSONObject.keys();

				while (keys.hasNext()) {
					String languageId = keys.next();

					JSONArray localizedOptionsJSONArray =
						optionsJSONObject.getJSONArray(languageId);

					for (int j = 0; j < localizedOptionsJSONArray.length();
						 j++) {

						JSONObject localizedOptionJSONObject =
							localizedOptionsJSONArray.getJSONObject(j);

						JSONObject optionLabelsJSONObject =
							optionLabelsJSONObjects.getOrDefault(
								localizedOptionJSONObject.getString("value"),
								_jsonFactory.createJSONObject());

						optionLabelsJSONObject.put(
							languageId,
							localizedOptionJSONObject.getString("label"));

						optionLabelsJSONObjects.putIfAbsent(
							localizedOptionJSONObject.getString("value"),
							optionLabelsJSONObject);
					}
				}

				fieldJSONObject.put(
					key,
					JSONUtil.toJSONArray(
						optionLabelsJSONObjects.entrySet(),
						entry -> JSONUtil.put(
							"label", entry.getValue()
						).put(
							"value", entry.getKey()
						)));
			}
		}

		private final Set<Locale> _availableLocales;
		private final DataLayout _dataLayout;
		private final HttpServletRequest _httpServletRequest;
		private final HttpServletResponse _httpServletResponse;

	}

}