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

package com.liferay.data.engine.taglib.internal.servlet.taglib.util;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.renderer.DataLayoutRenderer;
import com.liferay.data.engine.renderer.DataLayoutRendererContext;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataRecord;
import com.liferay.data.engine.rest.dto.v2_0.DataRule;
import com.liferay.data.engine.rest.dto.v2_0.util.DataDefinitionDDMFormUtil;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v2_0.DataLayoutResource;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordResource;
import com.liferay.data.engine.taglib.servlet.taglib.definition.DataLayoutBuilderDefinition;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextFactory;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.spi.form.builder.settings.DDMFormBuilderSettingsRetrieverHelper;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Gabriel Albuquerque
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataLayoutTaglibUtil.class)
public class DataLayoutTaglibUtil {

	public static Set<Locale> getAvailableLocales(
		Long dataDefinitionId, Long dataLayoutId,
		HttpServletRequest httpServletRequest) {

		return _dataLayoutTaglibUtil._getAvailableLocales(
			dataDefinitionId, dataLayoutId, httpServletRequest);
	}

	public static JSONObject getContentTypeConfigJSONObject(
		String contentType) {

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypes.get(contentType);

		if (dataDefinitionContentType == null) {
			dataDefinitionContentType = _dataDefinitionContentTypes.get(
				"default");
		}

		return JSONUtil.put(
			"allowInvalidAvailableLocalesForProperty",
			dataDefinitionContentType.allowInvalidAvailableLocalesForProperty()
		).put(
			"allowReferencedDataDefinitionDeletion",
			dataDefinitionContentType.allowReferencedDataDefinitionDeletion()
		);
	}

	public static DataDefinition getDataDefinition(
			long dataDefinitionId, HttpServletRequest httpServletRequest)
		throws Exception {

		return _dataLayoutTaglibUtil._getDataDefinition(
			dataDefinitionId, httpServletRequest);
	}

	public static JSONObject getDataLayoutConfigJSONObject(
		String contentType, Locale locale) {

		DataLayoutBuilderDefinition dataLayoutBuilderDefinition =
			_dataLayoutBuilderDefinitions.get(contentType);

		if (dataLayoutBuilderDefinition == null) {
			dataLayoutBuilderDefinition = _dataLayoutBuilderDefinitions.get(
				"default");
		}

		JSONObject dataLayoutConfigJSONObject = JSONUtil.put(
			"allowFieldSets", dataLayoutBuilderDefinition.allowFieldSets()
		).put(
			"allowMultiplePages",
			dataLayoutBuilderDefinition.allowMultiplePages()
		).put(
			"allowNestedFields", dataLayoutBuilderDefinition.allowNestedFields()
		).put(
			"allowRules", dataLayoutBuilderDefinition.allowRules()
		).put(
			"allowSuccessPage", dataLayoutBuilderDefinition.allowSuccessPage()
		).put(
			"disabledProperties",
			dataLayoutBuilderDefinition.getDisabledProperties()
		).put(
			"disabledTabs", dataLayoutBuilderDefinition.getDisabledTabs()
		).put(
			"visibleProperties",
			dataLayoutBuilderDefinition.getVisibleProperties()
		);

		if (dataLayoutBuilderDefinition.allowRules()) {
			try {
				dataLayoutConfigJSONObject.put(
					"ruleSettings",
					JSONUtil.put(
						"dataProviderInstanceParameterSettingsURL",
						_dataLayoutTaglibUtil.
							_getDDMDataProviderInstanceParameterSettingsURL()
					).put(
						"dataProviderInstancesURL",
						_dataLayoutTaglibUtil._getDDMDataProviderInstancesURL()
					).put(
						"functionsMetadata",
						_dataLayoutTaglibUtil._getFunctionsMetadataJSONObject(
							locale)
					).put(
						"functionsURL", _dataLayoutTaglibUtil._getFunctionsURL()
					));
			}
			catch (JSONException jsonException) {
				_log.error(jsonException, jsonException);
			}
		}

		dataLayoutConfigJSONObject.put(
			"unimplementedProperties",
			dataLayoutBuilderDefinition.getUnimplementedProperties());

		return dataLayoutConfigJSONObject;
	}

	public static JSONObject getDataLayoutJSONObject(
		Set<Locale> availableLocales, Long dataDefinitionId, Long dataLayoutId,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return _dataLayoutTaglibUtil._getDataLayoutJSONObject(
			availableLocales, dataDefinitionId, dataLayoutId,
			httpServletRequest, httpServletResponse);
	}

	public static Map<String, Object> getDataRecordValues(
			Long dataRecordId, HttpServletRequest httpServletRequest)
		throws Exception {

		return _dataLayoutTaglibUtil._getDataRecordValues(
			dataRecordId, httpServletRequest);
	}

	public static Long getDefaultDataLayoutId(
			Long dataDefinitionId, HttpServletRequest httpServletRequest)
		throws Exception {

		return _dataLayoutTaglibUtil._getDefaultDataLayoutId(
			dataDefinitionId, httpServletRequest);
	}

	public static JSONArray getFieldTypesJSONArray(
			HttpServletRequest httpServletRequest, Set<String> scopes)
		throws Exception {

		return _dataLayoutTaglibUtil._getFieldTypesJSONArray(
			httpServletRequest, scopes);
	}

	public static String renderDataLayout(
			Long dataLayoutId,
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

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDataDefinitionContentType(
		DataDefinitionContentType dataDefinitionContentType,
		Map<String, Object> properties) {

		String contentType = GetterUtil.getString(
			properties.get("content.type"));

		if (Validator.isNull(contentType)) {
			return;
		}

		_dataDefinitionContentTypes.put(contentType, dataDefinitionContentType);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDataLayoutBuilderDefinition(
		DataLayoutBuilderDefinition dataLayoutBuilderDefinition,
		Map<String, Object> properties) {

		String contentType = GetterUtil.getString(
			properties.get("content.type"));

		if (Validator.isNull(contentType)) {
			return;
		}

		_dataLayoutBuilderDefinitions.put(
			contentType, dataLayoutBuilderDefinition);
	}

	@Deactivate
	protected void deactivate() {
		_dataLayoutTaglibUtil = null;
	}

	protected void removeDataDefinitionContentType(
		DataDefinitionContentType dataDefinitionContentType,
		Map<String, Object> properties) {

		String contentType = GetterUtil.getString(
			properties.get("content.type"));

		if (Validator.isNull(contentType)) {
			return;
		}

		_dataDefinitionContentTypes.remove(contentType);
	}

	protected void removeDataLayoutBuilderDefinition(
		DataLayoutBuilderDefinition dataLayoutBuilderDefinition,
		Map<String, Object> properties) {

		String contentType = GetterUtil.getString(
			properties.get("content.type"));

		if (Validator.isNull(contentType)) {
			return;
		}

		_dataLayoutBuilderDefinitions.remove(contentType);
	}

	private Set<Locale> _getAvailableLocales(
		Long dataDefinitionId, Long dataLayoutId,
		HttpServletRequest httpServletRequest) {

		if (Validator.isNull(dataDefinitionId) &&
			Validator.isNull(dataLayoutId)) {

			return SetUtil.fromArray(
				new Locale[] {LocaleThreadLocal.getSiteDefaultLocale()});
		}

		try {
			DataDefinition dataDefinition = null;

			if (Validator.isNotNull(dataDefinitionId)) {
				dataDefinition = _getDataDefinition(
					dataDefinitionId, httpServletRequest);
			}
			else {
				DataLayout dataLayout = _getDataLayout(
					dataLayoutId, httpServletRequest);

				dataDefinition = _getDataDefinition(
					dataLayout.getDataDefinitionId(), httpServletRequest);
			}

			return Stream.of(
				dataDefinition.getAvailableLanguageIds()
			).map(
				LocaleUtil::fromLanguageId
			).collect(
				Collectors.toSet()
			);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return SetUtil.fromArray(
			new Locale[] {LocaleThreadLocal.getSiteDefaultLocale()});
	}

	private DataDefinition _getDataDefinition(
			Long dataDefinitionId, HttpServletRequest httpServletRequest)
		throws Exception {

		DataDefinitionResource.Builder dataDefinitionResourceBuilder =
			_dataDefinitionResourceFactory.create();

		DataDefinitionResource dataDefinitionResource =
			dataDefinitionResourceBuilder.httpServletRequest(
				httpServletRequest
			).user(
				_portal.getUser(httpServletRequest)
			).build();

		return dataDefinitionResource.getDataDefinition(dataDefinitionId);
	}

	private DataLayout _getDataLayout(
			Long dataLayoutId, HttpServletRequest httpServletRequest)
		throws Exception {

		DataLayoutResource.Builder dataLayoutResourceBuilder =
			_dataLayoutResourceFactory.create();

		DataLayoutResource dataLayoutResource =
			dataLayoutResourceBuilder.httpServletRequest(
				httpServletRequest
			).user(
				_portal.getUser(httpServletRequest)
			).build();

		return dataLayoutResource.getDataLayout(dataLayoutId);
	}

	private JSONObject _getDataLayoutJSONObject(
		Set<Locale> availableLocales, Long dataDefinitionId, Long dataLayoutId,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			String dataLayoutString = ParamUtil.getString(
				httpServletRequest, "dataLayout");

			if (Validator.isNotNull(dataLayoutString)) {
				DataLayoutDDMFormAdapter dataLayoutDDMFormAdapter =
					new DataLayoutDDMFormAdapter(
						availableLocales, DataLayout.toDTO(dataLayoutString),
						httpServletRequest, httpServletResponse);

				return dataLayoutDDMFormAdapter.toJSONObject();
			}

			DataLayout dataLayout = null;

			if (Validator.isNotNull(dataLayoutId)) {
				dataLayout = _getDataLayout(dataLayoutId, httpServletRequest);
			}
			else {
				DataDefinition dataDefinition = _getDataDefinition(
					dataDefinitionId, httpServletRequest);

				dataLayout = dataDefinition.getDefaultDataLayout();
			}

			DataLayoutDDMFormAdapter dataLayoutDDMFormAdapter =
				new DataLayoutDDMFormAdapter(
					availableLocales, dataLayout, httpServletRequest,
					httpServletResponse);

			return dataLayoutDDMFormAdapter.toJSONObject();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return _jsonFactory.createJSONObject();
		}
	}

	private Map<String, Object> _getDataRecordValues(
			Long dataRecordId, HttpServletRequest httpServletRequest)
		throws Exception {

		if (Validator.isNull(dataRecordId)) {
			return Collections.emptyMap();
		}

		DataRecordResource.Builder dataRecordResourceBuilder =
			_dataRecordResourceFactory.create();

		DataRecordResource dataRecordResource = dataRecordResourceBuilder.user(
			_portal.getUser(httpServletRequest)
		).build();

		DataRecord dataRecord = dataRecordResource.getDataRecord(dataRecordId);

		return dataRecord.getDataRecordValues();
	}

	private String _getDDMDataProviderInstanceParameterSettingsURL() {
		return _ddmFormBuilderSettingsRetrieverHelper.
			getDDMDataProviderInstanceParameterSettingsURL();
	}

	private String _getDDMDataProviderInstancesURL() {
		return _ddmFormBuilderSettingsRetrieverHelper.
			getDDMDataProviderInstancesURL();
	}

	private Long _getDefaultDataLayoutId(
			Long dataDefinitionId, HttpServletRequest httpServletRequest)
		throws Exception {

		DataDefinition dataDefinition = getDataDefinition(
			dataDefinitionId, httpServletRequest);

		if (dataDefinition == null) {
			return 0L;
		}

		DataLayout dataLayout = dataDefinition.getDefaultDataLayout();

		if (dataLayout == null) {
			return 0L;
		}

		return dataLayout.getId();
	}

	private JSONArray _getFieldTypesJSONArray(
			HttpServletRequest httpServletRequest, Set<String> scopes)
		throws Exception {

		JSONArray fieldTypesJSONArray = _jsonFactory.createJSONArray();

		DataDefinitionResource.Builder dataDefinitionResourceBuilder =
			_dataDefinitionResourceFactory.create();

		DataDefinitionResource dataDefinitionResource =
			dataDefinitionResourceBuilder.httpServletRequest(
				httpServletRequest
			).user(
				_portal.getUser(httpServletRequest)
			).build();

		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(
				dataDefinitionResource.
					getDataDefinitionDataDefinitionFieldFieldTypes());

			if (SetUtil.isEmpty(scopes)) {
				return jsonArray;
			}

			for (JSONObject jsonObject : (Iterable<JSONObject>)jsonArray) {
				String[] fieldTypeScopes = StringUtil.split(
					jsonObject.getString("scope"));

				boolean anyMatch = Stream.of(
					fieldTypeScopes
				).anyMatch(
					scope -> scopes.contains(scope)
				);

				if (anyMatch) {
					fieldTypesJSONArray.put(jsonObject);
				}
			}

			return fieldTypesJSONArray;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return fieldTypesJSONArray;
		}
	}

	private JSONObject _getFunctionsMetadataJSONObject(Locale locale)
		throws JSONException {

		return JSONFactoryUtil.createJSONObject(
			_ddmFormBuilderSettingsRetrieverHelper.
				getSerializedDDMExpressionFunctionsMetadata(locale));
	}

	private String _getFunctionsURL() {
		return _ddmFormBuilderSettingsRetrieverHelper.getDDMFunctionsURL();
	}

	private boolean _hasJavascriptModule(String name) {
		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(name);

		return Validator.isNotNull(ddmFormFieldType.getModuleName());
	}

	private String _resolveFieldTypeModule(String name) {
		return _resolveModuleName(
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(name));
	}

	private String _resolveFieldTypesModules() {
		Set<String> ddmFormFieldTypeNames =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeNames();

		Stream<String> stream = ddmFormFieldTypeNames.stream();

		return stream.filter(
			_dataLayoutTaglibUtil::_hasJavascriptModule
		).map(
			_dataLayoutTaglibUtil::_resolveFieldTypeModule
		).collect(
			Collectors.joining(StringPool.COMMA)
		);
	}

	private String _resolveModuleName(DDMFormFieldType ddmFormFieldType) {
		if (Validator.isNull(ddmFormFieldType.getModuleName())) {
			return StringPool.BLANK;
		}

		if (ddmFormFieldType.isCustomDDMFormFieldType()) {
			return ddmFormFieldType.getModuleName();
		}

		return _npmResolver.resolveModuleName(ddmFormFieldType.getModuleName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataLayoutTaglibUtil.class);

	private static final Map<String, DataDefinitionContentType>
		_dataDefinitionContentTypes = new ConcurrentHashMap<>();
	private static final Map<String, DataLayoutBuilderDefinition>
		_dataLayoutBuilderDefinitions = new ConcurrentHashMap<>();
	private static DataLayoutTaglibUtil _dataLayoutTaglibUtil;

	@Reference
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	@Reference
	private DataLayoutRenderer _dataLayoutRenderer;

	@Reference
	private DataLayoutResource.Factory _dataLayoutResourceFactory;

	@Reference
	private DataRecordResource.Factory _dataRecordResourceFactory;

	@Reference
	private DDMFormBuilderContextFactory _ddmFormBuilderContextFactory;

	@Reference
	private DDMFormBuilderSettingsRetrieverHelper
		_ddmFormBuilderSettingsRetrieverHelper;

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
			DDMForm ddmForm = null;

			if (_dataLayout.getId() == null) {
				DataDefinition dataDefinition = DataDefinition.toDTO(
					_httpServletRequest.getParameter("dataDefinition"));

				ddmForm = DataDefinitionDDMFormUtil.toDDMForm(
					dataDefinition, _ddmFormFieldTypeServicesTracker);
			}
			else {
				ddmForm = _getDDMForm();
			}

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

			ddmFormTemplateContext.put("rules", _getDataRulesJSONArray());

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

			DDMFormFieldValidationExpression ddmFormFieldValidationExpression =
				ddmFormFieldValidation.getDDMFormFieldValidationExpression();

			return new UnlocalizedValue(
				JSONUtil.put(
					"errorMessage",
					LocalizedValueUtil.toJSONObject(
						LocalizedValueUtil.toLocalizedValuesMap(
							ddmFormFieldValidation.
								getErrorMessageLocalizedValue()))
				).put(
					"expression",
					JSONUtil.put(
						"name", ddmFormFieldValidationExpression.getName()
					).put(
						"value", ddmFormFieldValidationExpression.getValue()
					)
				).put(
					"parameter",
					LocalizedValueUtil.toJSONObject(
						LocalizedValueUtil.toLocalizedValuesMap(
							ddmFormFieldValidation.
								getParameterLocalizedValue()))
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
					availableLocales,
					Optional.ofNullable(
						(DDMFormFieldOptions)propertyValue
					).orElse(
						new DDMFormFieldOptions()
					));
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
								"reference",
								ddmFormFieldOptions.getOptionReference(
									optionValue)
							).put(
								"value", optionValue
							);
						}));
			}

			return new UnlocalizedValue(jsonObject.toString());
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

		private JSONArray _getDataRulesJSONArray() {
			JSONArray dataRulesJSONArray = _jsonFactory.createJSONArray();

			for (DataRule dataRule : _dataLayout.getDataRules()) {
				JSONObject dataRuleJSONObject = _jsonFactory.createJSONObject();

				JSONArray jsonArray = _jsonFactory.createJSONArray();

				for (Map<String, Object> action : dataRule.getActions()) {
					JSONObject jsonObject = _jsonFactory.createJSONObject();

					action.forEach(jsonObject::put);

					jsonArray.put(jsonObject);
				}

				dataRuleJSONObject.put("actions", jsonArray);

				jsonArray = _jsonFactory.createJSONArray();

				for (Map<String, Object> condition : dataRule.getConditions()) {
					JSONObject jsonObject = _jsonFactory.createJSONObject();

					condition.forEach(jsonObject::put);

					jsonArray.put(jsonObject);
				}

				dataRuleJSONObject.put(
					"conditions", jsonArray
				).put(
					"logicalOperator", dataRule.getLogicalOperator()
				).put(
					"name", LocalizedValueUtil.toJSONObject(dataRule.getName())
				);

				dataRulesJSONArray.put(dataRuleJSONObject);
			}

			return dataRulesJSONArray;
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

			ddmStructure.setDefinition(jsonObject.toJSONString());

			return ddmStructure.getDDMForm();
		}

		private DDMFormLayout _getDDMFormLayout() throws Exception {
			String definition = null;

			if (_dataLayout.getId() == null) {
				definition = _dataLayout.toString();
			}
			else {
				DDMStructureLayout ddmStructureLayout =
					_ddmStructureLayoutLocalService.getStructureLayout(
						_dataLayout.getId());

				definition = ddmStructureLayout.getDefinition();
			}

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				StringUtil.replace(
					definition,
					new String[] {
						"columnSize", "dataLayoutColumns", "dataLayoutPages",
						"dataLayoutRows"
					},
					new String[] {"size", "columns", "pages", "rows"}));

			return _deserializeDDMFormLayout(jsonObject.toJSONString());
		}

		private List<Map<String, Object>> _getNestedFields(
			Map<String, Object> field) {

			List<Map<String, Object>> nestedFields =
				(List<Map<String, Object>>)field.get("nestedFields");

			if (nestedFields != null) {
				Stream<Map<String, Object>> stream = nestedFields.stream();

				return stream.flatMap(
					this::_getNestedFieldsStream
				).collect(
					Collectors.toList()
				);
			}

			return new ArrayList<>();
		}

		private Stream<Map<String, Object>> _getNestedFieldsStream(
			Map<String, Object> field) {

			List<Map<String, Object>> nestedFieldsList = new ArrayList<>(
				Arrays.asList(field));

			nestedFieldsList.addAll(_getNestedFields(field));

			return nestedFieldsList.stream();
		}

		private boolean _isFieldSet(Map<String, Object> field) {
			if (Objects.equals(field.get("type"), "fieldset")) {
				return true;
			}

			return false;
		}

		private void _populateDDMFormFieldSettingsContext(
				Map<String, DDMFormField> ddmFormFieldsMap,
				Map<String, Object> ddmFormTemplateContext)
			throws Exception {

			UnsafeConsumer<Map<String, Object>, Exception> unsafeConsumer =
				field -> {
					DDMFormField ddmFormField = ddmFormFieldsMap.get(
						MapUtil.getString(field, "fieldName"));

					if (_isFieldSet(field)) {
						ddmFormField.setProperty("rows", field.get("rows"));
					}

					field.put(
						"settingsContext",
						_createDDMFormFieldSettingContext(ddmFormField));
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

							List<Map<String, Object>> nestedFields =
								_getNestedFields(field);

							for (Map<String, Object> nestedField :
									nestedFields) {

								unsafeConsumer.accept(nestedField);
							}
						}
					}
				}
			}
		}

		private final Set<Locale> _availableLocales;
		private final DataLayout _dataLayout;
		private final HttpServletRequest _httpServletRequest;
		private final HttpServletResponse _httpServletResponse;

	}

}