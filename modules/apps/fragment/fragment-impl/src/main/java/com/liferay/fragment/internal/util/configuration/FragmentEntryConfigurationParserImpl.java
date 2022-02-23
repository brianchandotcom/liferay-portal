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

package com.liferay.fragment.internal.util.configuration;

import com.liferay.fragment.constants.FragmentConfigurationFieldDataType;
import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.frontend.token.definition.FrontendToken;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.frontend.token.definition.FrontendTokenMapping;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.layout.list.retriever.DefaultLayoutListRetrieverContext;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverTracker;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = FragmentEntryConfigurationParser.class)
public class FragmentEntryConfigurationParserImpl
	implements FragmentEntryConfigurationParser {

	@Override
	public JSONObject getConfigurationDefaultValuesJSONObject(
		String configuration) {

		List<FragmentConfigurationField> fragmentConfigurationFields =
			getFragmentConfigurationFields(configuration);

		JSONObject defaultValuesJSONObject = JSONFactoryUtil.createJSONObject();

		for (FragmentConfigurationField fragmentConfigurationField :
				fragmentConfigurationFields) {

			defaultValuesJSONObject.put(
				fragmentConfigurationField.getName(),
				getFieldValue(
					fragmentConfigurationField,
					LocaleUtil.getMostRelevantLocale(), null));
		}

		return defaultValuesJSONObject;
	}

	@Override
	public Object getConfigurationFieldValue(
		String editableValues, String fieldName,
		FragmentConfigurationFieldDataType fragmentConfigurationFieldDataType) {

		try {
			JSONObject editableValuesJSONObject =
				JSONFactoryUtil.createJSONObject(editableValues);

			JSONObject configurationValuesJSONObject =
				editableValuesJSONObject.getJSONObject(
					_KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

			if (configurationValuesJSONObject == null) {
				return null;
			}

			return _getFieldValue(
				fragmentConfigurationFieldDataType,
				configurationValuesJSONObject.getString(fieldName));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to parse configuration JSON: " + editableValues,
					jsonException);
			}
		}

		return null;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 * #getConfigurationJSONObject(String, String, Locale)}
	 */
	@Deprecated
	@Override
	public JSONObject getConfigurationJSONObject(
			String configuration, String editableValues)
		throws JSONException {

		return getConfigurationJSONObject(
			configuration, editableValues, LocaleUtil.getMostRelevantLocale());
	}

	@Override
	public JSONObject getConfigurationJSONObject(
			String configuration, String editableValues, Locale locale)
		throws JSONException {

		JSONObject configurationDefaultValuesJSONObject =
			getConfigurationDefaultValuesJSONObject(configuration);

		if (configurationDefaultValuesJSONObject == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			editableValues);

		JSONObject configurationValuesJSONObject =
			editableValuesJSONObject.getJSONObject(
				_KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		if (configurationValuesJSONObject == null) {
			return configurationDefaultValuesJSONObject;
		}

		List<FragmentConfigurationField> configurationFields =
			getFragmentConfigurationFields(configuration);

		for (FragmentConfigurationField configurationField :
				configurationFields) {

			String name = configurationField.getName();

			Object object = configurationValuesJSONObject.get(name);

			if (Validator.isNull(object)) {
				continue;
			}

			configurationDefaultValuesJSONObject.put(
				name,
				getFieldValue(
					configurationField, locale,
					configurationValuesJSONObject.getString(name)));
		}

		return configurationDefaultValuesJSONObject;
	}

	@Override
	public Map<String, Object> getContextObjects(
		JSONObject configurationValuesJSONObject, String configuration,
		long[] segmentsEntryIds) {

		HashMap<String, Object> contextObjects = new HashMap<>();

		List<FragmentConfigurationField> fragmentConfigurationFields =
			getFragmentConfigurationFields(configuration);

		for (FragmentConfigurationField fragmentConfigurationField :
				fragmentConfigurationFields) {

			String name = fragmentConfigurationField.getName();

			if (StringUtil.equalsIgnoreCase(
					fragmentConfigurationField.getType(), "itemSelector")) {

				Object contextObject = _getInfoDisplayObjectEntry(
					configurationValuesJSONObject.getString(name));

				if (contextObject != null) {
					contextObjects.put(
						name + _CONTEXT_OBJECT_SUFFIX, contextObject);
				}

				continue;
			}

			if (StringUtil.equalsIgnoreCase(
					fragmentConfigurationField.getType(),
					"collectionSelector")) {

				Object contextListObject = _getInfoListObjectEntry(
					configurationValuesJSONObject.getString(name),
					segmentsEntryIds);

				if (contextListObject != null) {
					contextObjects.put(
						name + _CONTEXT_OBJECT_LIST_SUFFIX, contextListObject);
				}
			}
		}

		return contextObjects;
	}

	@Override
	public Object getFieldValue(
		FragmentConfigurationField fragmentConfigurationField, Locale locale,
		String value) {

		value = GetterUtil.getString(value);

		if (fragmentConfigurationField.isLocalizable() &&
			JSONUtil.isValid(value)) {

			try {
				JSONObject valueJSONObject = JSONFactoryUtil.createJSONObject(
					value);

				value = valueJSONObject.getString(
					LocaleUtil.toLanguageId(locale),
					valueJSONObject.getString(
						LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
						fragmentConfigurationField.getDefaultValue()));
			}
			catch (JSONException jsonException) {
				_log.error(
					"Unable to parse configuration value JSON", jsonException);
			}
		}
		else if (Validator.isNull(value)) {
			value = fragmentConfigurationField.getDefaultValue();
		}

		if (StringUtil.equalsIgnoreCase(
				fragmentConfigurationField.getType(), "checkbox")) {

			return _getFieldValue(
				FragmentConfigurationFieldDataType.BOOLEAN, value);
		}
		else if (StringUtil.equalsIgnoreCase(
					fragmentConfigurationField.getType(),
					"collectionSelector")) {

			return _getInfoListObjectEntryJSONObject(value);
		}
		else if (StringUtil.equalsIgnoreCase(
					fragmentConfigurationField.getType(), "colorPalette")) {

			JSONObject jsonObject = (JSONObject)_getFieldValue(
				FragmentConfigurationFieldDataType.OBJECT, value);

			if (jsonObject.isNull("color") && !jsonObject.isNull("cssClass")) {
				jsonObject.put("color", jsonObject.getString("cssClass"));
			}

			return jsonObject;
		}
		else if (StringUtil.equalsIgnoreCase(
					fragmentConfigurationField.getType(), "colorPicker")) {

			String fieldValue = (String)_getFieldValue(
				FragmentConfigurationFieldDataType.STRING, value);

			return _getColorPickerCssVariable(fieldValue);
		}
		else if (StringUtil.equalsIgnoreCase(
					fragmentConfigurationField.getType(), "itemSelector")) {

			return _getInfoDisplayObjectEntryJSONObject(value);
		}
		else if (StringUtil.equalsIgnoreCase(
					fragmentConfigurationField.getType(), "select") ||
				 StringUtil.equalsIgnoreCase(
					 fragmentConfigurationField.getType(), "text")) {

			FragmentConfigurationFieldDataType
				fragmentConfigurationFieldDataType =
					fragmentConfigurationField.
						getFragmentConfigurationFieldDataType();

			if (fragmentConfigurationFieldDataType == null) {
				fragmentConfigurationFieldDataType =
					FragmentConfigurationFieldDataType.STRING;
			}

			return _getFieldValue(fragmentConfigurationFieldDataType, value);
		}

		return _getFieldValue(FragmentConfigurationFieldDataType.STRING, value);
	}

	@Override
	public Object getFieldValue(
		String configuration, String editableValues, Locale locale,
		String name) {

		JSONObject editableValuesJSONObject = null;

		try {
			editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
				editableValues);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}

		JSONObject configurationValuesJSONObject =
			editableValuesJSONObject.getJSONObject(
				_KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		if (configurationValuesJSONObject == null) {
			return null;
		}

		List<FragmentConfigurationField> fragmentConfigurationFields =
			getFragmentConfigurationFields(configuration);

		for (FragmentConfigurationField fragmentConfigurationField :
				fragmentConfigurationFields) {

			if (!Objects.equals(fragmentConfigurationField.getName(), name)) {
				continue;
			}

			return getFieldValue(
				fragmentConfigurationField, locale,
				configurationValuesJSONObject.getString(name));
		}

		return null;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 * #getFieldValue(String, String, Locale, String)}
	 */
	@Deprecated
	@Override
	public Object getFieldValue(
		String configuration, String editableValues, String name) {

		return getFieldValue(
			configuration, editableValues, LocaleUtil.getMostRelevantLocale(),
			name);
	}

	@Override
	public List<FragmentConfigurationField> getFragmentConfigurationFields(
		String configuration) {

		JSONArray fieldSetsJSONArray = _getFieldSetsJSONArray(configuration);

		if (fieldSetsJSONArray == null) {
			return Collections.emptyList();
		}

		List<FragmentConfigurationField> fragmentConfigurationFields =
			new ArrayList<>();

		Iterator<JSONObject> iterator1 = fieldSetsJSONArray.iterator();

		iterator1.forEachRemaining(
			fieldSetJSONObject -> {
				JSONArray fieldSetFieldsJSONArray =
					fieldSetJSONObject.getJSONArray("fields");

				Iterator<JSONObject> iterator2 =
					fieldSetFieldsJSONArray.iterator();

				iterator2.forEachRemaining(
					fieldSetFieldsJSONObject -> fragmentConfigurationFields.add(
						new FragmentConfigurationField(
							fieldSetFieldsJSONObject)));
			});

		return fragmentConfigurationFields;
	}

	@Override
	public String translateConfiguration(
		JSONObject jsonObject, ResourceBundle resourceBundle) {

		JSONArray fieldSetsJSONArray = jsonObject.getJSONArray("fieldSets");

		if (fieldSetsJSONArray == null) {
			return StringPool.BLANK;
		}

		Iterator<JSONObject> iterator = fieldSetsJSONArray.iterator();

		iterator.forEachRemaining(
			fieldSetJSONObject -> {
				String fieldSetLabel = fieldSetJSONObject.getString("label");

				fieldSetJSONObject.put(
					"label",
					LanguageUtil.get(
						resourceBundle, fieldSetLabel, fieldSetLabel));

				JSONArray fieldsJSONArray = fieldSetJSONObject.getJSONArray(
					"fields");

				Iterator<JSONObject> fieldsIterator =
					fieldsJSONArray.iterator();

				fieldsIterator.forEachRemaining(
					fieldJSONObject -> _translateConfigurationField(
						fieldJSONObject, resourceBundle));
			});

		return jsonObject.toString();
	}

	private String _getColorPickerCssVariable(String fieldValue) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if ((serviceContext == null) || Validator.isNull(fieldValue)) {
			return fieldValue;
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		FrontendTokenDefinition frontendTokenDefinition =
			_frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				themeDisplay.getThemeId());

		if (frontendTokenDefinition == null) {
			return fieldValue;
		}

		Collection<FrontendToken> frontendTokens =
			frontendTokenDefinition.getFrontendTokens();

		for (FrontendToken frontendToken : frontendTokens) {
			try {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					frontendToken.getJSON(LocaleUtil.getMostRelevantLocale()));

				if (!Objects.equals(jsonObject.getString("name"), fieldValue)) {
					continue;
				}

				List<FrontendTokenMapping> frontendTokenMappings =
					new ArrayList<>(
						frontendToken.getFrontendTokenMappings(
							FrontendTokenMapping.TYPE_CSS_VARIABLE));

				if (frontendTokenMappings.isEmpty()) {
					return fieldValue;
				}

				FrontendTokenMapping frontendTokenMapping =
					frontendTokenMappings.get(0);

				return "var(--" + frontendTokenMapping.getValue() + ")";
			}
			catch (JSONException jsonException) {
				if (_log.isDebugEnabled()) {
					_log.debug(jsonException);
				}

				return fieldValue;
			}
		}

		return fieldValue;
	}

	private JSONArray _getFieldSetsJSONArray(String configuration) {
		try {
			JSONObject configurationJSONObject =
				JSONFactoryUtil.createJSONObject(configuration);

			return configurationJSONObject.getJSONArray("fieldSets");
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to parse configuration JSON: " + configuration,
					jsonException);
			}
		}

		return null;
	}

	private Object _getFieldValue(
		FragmentConfigurationFieldDataType fragmentConfigurationFieldDataType,
		String value) {

		if (fragmentConfigurationFieldDataType ==
				FragmentConfigurationFieldDataType.ARRAY) {

			try {
				return JSONFactoryUtil.createJSONArray(value);
			}
			catch (JSONException jsonException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to parse configuration JSON: " + value,
						jsonException);
				}
			}
		}
		else if (fragmentConfigurationFieldDataType ==
					FragmentConfigurationFieldDataType.BOOLEAN) {

			return GetterUtil.getBoolean(value);
		}
		else if (fragmentConfigurationFieldDataType ==
					FragmentConfigurationFieldDataType.DOUBLE) {

			return GetterUtil.getDouble(value);
		}
		else if (fragmentConfigurationFieldDataType ==
					FragmentConfigurationFieldDataType.INTEGER) {

			return GetterUtil.getInteger(value);
		}
		else if (fragmentConfigurationFieldDataType ==
					FragmentConfigurationFieldDataType.OBJECT) {

			try {
				return JSONFactoryUtil.createJSONObject(value);
			}
			catch (JSONException jsonException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to parse configuration JSON: " + value,
						jsonException);
				}
			}
		}
		else if (fragmentConfigurationFieldDataType ==
					FragmentConfigurationFieldDataType.STRING) {

			return value;
		}

		return null;
	}

	private Object _getInfoDisplayObjectEntry(String value) {
		if (Validator.isNull(value)) {
			return null;
		}

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			String className = GetterUtil.getString(
				jsonObject.getString("className"));

			InfoItemObjectProvider<?> infoItemObjectProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemObjectProvider.class, className);

			if (infoItemObjectProvider == null) {
				return null;
			}

			long classPK = GetterUtil.getLong(jsonObject.getString("classPK"));

			return infoItemObjectProvider.getInfoItem(
				new ClassPKInfoItemIdentifier(classPK));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get entry: " + value, exception);
			}
		}

		return null;
	}

	private JSONObject _getInfoDisplayObjectEntryJSONObject(String value) {
		if (Validator.isNull(value) ||
			Objects.equals(value, JSONFactoryUtil.getNullJSON())) {

			return JSONFactoryUtil.createJSONObject();
		}

		try {
			JSONObject configurationValueJSONObject =
				JSONFactoryUtil.createJSONObject(value);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerialize(
					_getInfoDisplayObjectEntry(value)));

			jsonObject.put(
				"className",
				GetterUtil.getString(
					configurationValueJSONObject.getString("className"))
			).put(
				"classNameId",
				GetterUtil.getString(
					configurationValueJSONObject.getString("classNameId"))
			).put(
				"classPK",
				GetterUtil.getLong(
					configurationValueJSONObject.getString("classPK"))
			).put(
				"template", configurationValueJSONObject.get("template")
			).put(
				"title", configurationValueJSONObject.getString("title")
			);

			return jsonObject;
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to serialize info display object entry to JSON: " +
						value,
					jsonException);
			}
		}

		return null;
	}

	private Object _getInfoListObjectEntry(
		String value, long[] segmentsEntryIds) {

		if (Validator.isNull(value)) {
			return Collections.emptyList();
		}

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			if (jsonObject.length() <= 0) {
				return Collections.emptyList();
			}

			String type = jsonObject.getString("type");

			LayoutListRetriever<?, ListObjectReference> layoutListRetriever =
				(LayoutListRetriever<?, ListObjectReference>)
					_layoutListRetrieverTracker.getLayoutListRetriever(type);

			if (layoutListRetriever == null) {
				return Collections.emptyList();
			}

			ListObjectReferenceFactory<?> listObjectReferenceFactory =
				_listObjectReferenceFactoryTracker.getListObjectReference(type);

			if (listObjectReferenceFactory == null) {
				return Collections.emptyList();
			}

			DefaultLayoutListRetrieverContext
				defaultLayoutListRetrieverContext =
					new DefaultLayoutListRetrieverContext();

			defaultLayoutListRetrieverContext.setSegmentsEntryIds(
				segmentsEntryIds);

			return layoutListRetriever.getList(
				listObjectReferenceFactory.getListObjectReference(jsonObject),
				defaultLayoutListRetrieverContext);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get collection: " + value, exception);
			}
		}

		return Collections.emptyList();
	}

	private JSONObject _getInfoListObjectEntryJSONObject(String value) {
		if (Validator.isNull(value)) {
			return JSONFactoryUtil.createJSONObject();
		}

		try {
			return JSONFactoryUtil.createJSONObject(value);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to serialize info list object entry to JSON: " +
						value,
					jsonException);
			}
		}

		return null;
	}

	private void _translateConfigurationField(
		JSONObject fieldJSONObject, ResourceBundle resourceBundle) {

		String fieldDescription = fieldJSONObject.getString("description");

		fieldJSONObject.put(
			"description",
			LanguageUtil.get(
				resourceBundle, fieldDescription, fieldDescription));

		String fieldLabel = fieldJSONObject.getString("label");

		fieldJSONObject.put(
			"label", LanguageUtil.get(resourceBundle, fieldLabel, fieldLabel));

		String type = fieldJSONObject.getString("type");

		if (!Objects.equals(type, "select") && !Objects.equals(type, "text")) {
			return;
		}

		JSONObject typeOptionsJSONObject = fieldJSONObject.getJSONObject(
			"typeOptions");

		if (typeOptionsJSONObject == null) {
			return;
		}

		if (Objects.equals(type, "select")) {
			JSONArray validValuesJSONArray = typeOptionsJSONObject.getJSONArray(
				"validValues");

			Iterator<JSONObject> validValuesIterator =
				validValuesJSONArray.iterator();

			validValuesIterator.forEachRemaining(
				validValueJSONObject -> {
					String value = validValueJSONObject.getString("value");

					String label = validValueJSONObject.getString(
						"label", value);

					validValueJSONObject.put(
						"label",
						LanguageUtil.get(resourceBundle, label, label));
				});
		}
		else {
			JSONObject validationJSONObject =
				typeOptionsJSONObject.getJSONObject("validation");

			if ((validationJSONObject != null) &&
				validationJSONObject.has("errorMessage")) {

				String errorMessage = validationJSONObject.getString(
					"errorMessage");

				validationJSONObject.put(
					"errorMessage",
					LanguageUtil.get(
						resourceBundle, errorMessage, errorMessage));
			}
		}
	}

	private static final String _CONTEXT_OBJECT_LIST_SUFFIX = "ObjectList";

	private static final String _CONTEXT_OBJECT_SUFFIX = "Object";

	private static final String _KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR =
		"com.liferay.fragment.entry.processor.freemarker." +
			"FreeMarkerFragmentEntryProcessor";

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryConfigurationParserImpl.class);

	@Reference
	private FrontendTokenDefinitionRegistry _frontendTokenDefinitionRegistry;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private LayoutListRetrieverTracker _layoutListRetrieverTracker;

	@Reference
	private ListObjectReferenceFactoryTracker
		_listObjectReferenceFactoryTracker;

}