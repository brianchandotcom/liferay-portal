/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.internal.util;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.headless.admin.fragment.dto.v1_0.Configuration;
import com.liferay.headless.admin.fragment.dto.v1_0.Dependency;
import com.liferay.headless.admin.fragment.dto.v1_0.Field;
import com.liferay.headless.admin.fragment.dto.v1_0.FieldSet;
import com.liferay.headless.admin.fragment.dto.v1_0.ItemFragmentConfigurationFieldDefaultValue;
import com.liferay.headless.admin.fragment.dto.v1_0.ItemSelectorField;
import com.liferay.headless.admin.fragment.dto.v1_0.ItemSelectorTypeOptions;
import com.liferay.headless.admin.site.dto.v1_0.FragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.ItemFragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.dto.v1_0.ItemValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ScopeUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.scope.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 */
public class ConfigurationUtil {

	public static Configuration toConfiguration(
			DTOConverter
				<FragmentConfigurationField, FragmentConfigurationFieldValue>
					fragmentConfigurationFieldValueDTOConverter,
			FragmentEntry fragmentEntry)
		throws Exception {

		String configurationJSON = fragmentEntry.getConfiguration();

		if (Validator.isNull(configurationJSON)) {
			return null;
		}

		JSONObject configurationJSONObject = null;

		try {
			configurationJSONObject = JSONFactoryUtil.createJSONObject(
				configurationJSON);
		}
		catch (JSONException jsonException) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Fragment entry with ID ",
					fragmentEntry.getFragmentEntryId(),
					" has an approved configuration that is not valid JSON"),
				jsonException);
		}

		JSONArray fieldSetsJSONArray = configurationJSONObject.getJSONArray(
			"fieldSets");

		if (fieldSetsJSONArray == null) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Fragment entry with ID ",
					fragmentEntry.getFragmentEntryId(),
					" has an approved configuration without field sets"));
		}

		Configuration configuration = new Configuration();

		FieldSet[] fieldSets = JSONUtil.toArray(
			fieldSetsJSONArray,
			fieldSetJSONObject -> _toFieldSet(
				fieldSetJSONObject, fragmentConfigurationFieldValueDTOConverter,
				fragmentEntry),
			FieldSet.class);

		configuration.setFieldSets(() -> fieldSets);

		return configuration;
	}

	public static String toConfigurationJSON(
			Configuration configuration, long groupId,
			InfoItemServiceRegistry infoItemServiceRegistry)
		throws Exception {

		if (configuration == null) {
			return null;
		}

		JSONObject configurationJSONObject = JSONUtil.put(
			"fieldSets",
			JSONUtil.toJSONArray(
				configuration.getFieldSets(),
				fieldSet -> _toFieldSetJSONObject(
					fieldSet, groupId, infoItemServiceRegistry)));

		return configurationJSONObject.toString();
	}

	private static Long _getClassPK(
		String className, String externalReferenceCode, long groupId,
		InfoItemServiceRegistry infoItemServiceRegistry,
		String scopeExternalReferenceCode) {

		InfoItemDetailsProvider<Object> infoItemDetailsProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemDetailsProvider.class, className,
				ClassPKInfoItemIdentifier.INFO_ITEM_SERVICE_FILTER);

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemObjectProvider.class, className,
				ClassPKInfoItemIdentifier.INFO_ITEM_SERVICE_FILTER);

		if ((infoItemDetailsProvider == null) ||
			(infoItemObjectProvider == null)) {

			return null;
		}

		try {
			Object infoItem = infoItemObjectProvider.getInfoItem(
				groupId,
				new ERCInfoItemIdentifier(
					externalReferenceCode, scopeExternalReferenceCode));

			if (infoItem == null) {
				return null;
			}

			InfoItemDetails infoItemDetails =
				infoItemDetailsProvider.getInfoItemDetails(
					groupId, ClassPKInfoItemIdentifier.class, infoItem);

			if (infoItemDetails == null) {
				return null;
			}

			InfoItemReference infoItemReference =
				infoItemDetails.getInfoItemReference();

			if (infoItemReference == null) {
				return null;
			}

			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)
					infoItemReference.getInfoItemIdentifier();

			return classPKInfoItemIdentifier.getClassPK();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	private static String _getScopeExternalReferenceCode(Scope scope) {
		if (scope == null) {
			return null;
		}

		return scope.getExternalReferenceCode();
	}

	private static Dependency _toDependency(JSONObject dependencyJSONObject) {
		return new Dependency() {
			{
				setType(
					() -> Dependency.Type.create(
						dependencyJSONObject.getString("type")));
				setValue(() -> dependencyJSONObject.getString("value"));
			}
		};
	}

	private static JSONObject _toDependencyJSONObject(
		Map<String, Dependency> dependencyMap) {

		if (dependencyMap == null) {
			return null;
		}

		JSONObject dependencyJSONObject = JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, Dependency> entry : dependencyMap.entrySet()) {
			Dependency dependency = entry.getValue();

			dependencyJSONObject.put(
				entry.getKey(),
				JSONUtil.put(
					"type", String.valueOf(dependency.getType())
				).put(
					"value", dependency.getValue()
				));
		}

		return dependencyJSONObject;
	}

	private static Map<String, Dependency> _toDependencyMap(
		JSONObject typeOptionsJSONObject) {

		JSONObject dependencyJSONObject = typeOptionsJSONObject.getJSONObject(
			"dependency");

		if (dependencyJSONObject == null) {
			return null;
		}

		Map<String, Dependency> dependencyMap = new HashMap<>();

		for (String key : dependencyJSONObject.keySet()) {
			JSONObject entryJSONObject = dependencyJSONObject.getJSONObject(
				key);

			if (entryJSONObject == null) {
				continue;
			}

			dependencyMap.put(key, _toDependency(entryJSONObject));
		}

		if (dependencyMap.isEmpty()) {
			return null;
		}

		return dependencyMap;
	}

	private static Field _toField(
		JSONObject fieldJSONObject,
		DTOConverter
			<FragmentConfigurationField, FragmentConfigurationFieldValue>
				fragmentConfigurationFieldValueDTOConverter,
		FragmentEntry fragmentEntry) {

		if (fieldJSONObject == null) {
			return null;
		}

		String type = fieldJSONObject.getString("type");

		if (!Objects.equals(type, "itemSelector")) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Fragment entry with ID ",
					fragmentEntry.getFragmentEntryId(),
					" has an approved configuration with a field of unknown ",
					"type ", type));
		}

		Field field = new ItemSelectorField() {
			{
				setDefaultValue(
					() -> _toItemFragmentConfigurationFieldDefaultValue(
						_toItemValue(
							fieldJSONObject,
							fragmentConfigurationFieldValueDTOConverter,
							fragmentEntry)));
				setTypeOptions(
					() -> _toItemSelectorTypeOptions(
						fieldJSONObject.getJSONObject("typeOptions")));
			}
		};

		field.setDataType(
			() -> Field.DataType.create(fieldJSONObject.getString("dataType")));
		field.setDescription(
			() -> fieldJSONObject.getString("description", null));
		field.setLabel(() -> fieldJSONObject.getString("label", null));
		field.setLocalizable(
			() -> {
				if (!fieldJSONObject.has("localizable")) {
					return null;
				}

				return fieldJSONObject.getBoolean("localizable");
			});
		field.setName(() -> fieldJSONObject.getString("name"));
		field.setType(() -> Field.Type.create(type));

		return field;
	}

	private static JSONObject _toFieldJSONObject(
			Field field, long groupId,
			InfoItemServiceRegistry infoItemServiceRegistry)
		throws PortalException {

		JSONObject fieldJSONObject = JSONFactoryUtil.createJSONObject();

		if (field.getDataType() != null) {
			fieldJSONObject.put(
				"dataType", String.valueOf(field.getDataType()));
		}

		fieldJSONObject.put(
			"description", field.getDescription()
		).put(
			"label", field.getLabel()
		).put(
			"localizable", field.getLocalizable()
		).put(
			"name", field.getName()
		).put(
			"type", String.valueOf(field.getType())
		);

		if (field instanceof ItemSelectorField itemSelectorField) {
			fieldJSONObject.put(
				"defaultValue",
				_toItemJSONObject(
					groupId, infoItemServiceRegistry,
					itemSelectorField.getDefaultValue())
			).put(
				"typeOptions",
				_toTypeOptionsJSONObject(itemSelectorField.getTypeOptions())
			);
		}

		return fieldJSONObject;
	}

	private static FieldSet _toFieldSet(
			JSONObject fieldSetJSONObject,
			DTOConverter
				<FragmentConfigurationField, FragmentConfigurationFieldValue>
					fragmentConfigurationFieldValueDTOConverter,
			FragmentEntry fragmentEntry)
		throws Exception {

		if (fieldSetJSONObject == null) {
			return null;
		}

		FieldSet fieldSet = new FieldSet();

		fieldSet.setConfigurationRole(
			() -> FieldSet.ConfigurationRole.create(
				fieldSetJSONObject.getString("configurationRole")));
		fieldSet.setCustomComponentModule(
			() -> fieldSetJSONObject.getString("customComponentModule", null));

		Field[] fields = JSONUtil.toArray(
			fieldSetJSONObject.getJSONArray("fields"),
			fieldJSONObject -> _toField(
				fieldJSONObject, fragmentConfigurationFieldValueDTOConverter,
				fragmentEntry),
			Field.class);

		fieldSet.setFields(() -> fields);

		fieldSet.setLabel(() -> fieldSetJSONObject.getString("label", null));

		return fieldSet;
	}

	private static JSONObject _toFieldSetJSONObject(
			FieldSet fieldSet, long groupId,
			InfoItemServiceRegistry infoItemServiceRegistry)
		throws Exception {

		JSONObject fieldSetJSONObject = JSONFactoryUtil.createJSONObject();

		if (fieldSet.getConfigurationRole() != null) {
			fieldSetJSONObject.put(
				"configurationRole",
				String.valueOf(fieldSet.getConfigurationRole()));
		}

		fieldSetJSONObject.put(
			"customComponentModule", fieldSet.getCustomComponentModule()
		).put(
			"fields",
			JSONUtil.toJSONArray(
				fieldSet.getFields(),
				field -> _toFieldJSONObject(
					field, groupId, infoItemServiceRegistry))
		).put(
			"label", fieldSet.getLabel()
		);

		return fieldSetJSONObject;
	}

	private static FragmentConfigurationFieldValue
		_toFragmentConfigurationFieldValue(
			JSONObject fieldJSONObject,
			DTOConverter
				<FragmentConfigurationField, FragmentConfigurationFieldValue>
					fragmentConfigurationFieldValueDTOConverter,
			FragmentEntry fragmentEntry) {

		Object defaultValue = fieldJSONObject.opt("defaultValue");

		if (defaultValue instanceof String) {
			try {
				defaultValue = JSONFactoryUtil.createJSONObject(
					(String)defaultValue);
			}
			catch (JSONException jsonException) {
				if (_log.isDebugEnabled()) {
					_log.debug(jsonException);
				}

				return null;
			}
		}

		if (!(defaultValue instanceof JSONObject)) {
			return null;
		}

		try {
			return fragmentConfigurationFieldValueDTOConverter.toDTO(
				new DefaultDTOConverterContext(
					false, null,
					HashMapBuilder.<String, Object>put(
						"companyId", fragmentEntry.getCompanyId()
					).put(
						"fragmentFragmentConfigurationFieldValue", defaultValue
					).put(
						"scopeGroupId", fragmentEntry.getGroupId()
					).build(),
					null, null, null, null, null, null),
				new FragmentConfigurationField(
					fieldJSONObject.getString("name"),
					fieldJSONObject.getString("dataType"), null, false,
					fieldJSONObject.getString("type")));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	private static ItemFragmentConfigurationFieldDefaultValue
		_toItemFragmentConfigurationFieldDefaultValue(ItemValue itemValue) {

		if (itemValue == null) {
			return null;
		}

		return new ItemFragmentConfigurationFieldDefaultValue() {
			{
				setValue(() -> itemValue);
			}
		};
	}

	private static JSONObject _toItemJSONObject(
			long groupId, InfoItemServiceRegistry infoItemServiceRegistry,
			ItemFragmentConfigurationFieldDefaultValue
				itemFragmentConfigurationFieldDefaultValue)
		throws PortalException {

		if (itemFragmentConfigurationFieldDefaultValue == null) {
			return null;
		}

		ItemValue itemValue =
			itemFragmentConfigurationFieldDefaultValue.getValue();

		if ((itemValue == null) ||
			(itemValue.getItemExternalReference() == null)) {

			return null;
		}

		ItemExternalReference itemExternalReference =
			itemValue.getItemExternalReference();

		String className = itemExternalReference.getClassName();

		String scopeExternalReferenceCode =
			ScopeUtil.getItemScopeExternalReferenceCode(
				_getScopeExternalReferenceCode(
					itemExternalReference.getScope()),
				groupId);

		return JSONUtil.put(
			"className", className
		).put(
			"classNameId", String.valueOf(PortalUtil.getClassNameId(className))
		).put(
			"classPK",
			() -> {
				Long classPK = _getClassPK(
					className, itemExternalReference.getExternalReferenceCode(),
					groupId, infoItemServiceRegistry,
					scopeExternalReferenceCode);

				if (classPK == null) {
					return null;
				}

				return String.valueOf(classPK);
			}
		).put(
			"externalReferenceCode",
			itemExternalReference.getExternalReferenceCode()
		).put(
			"scopeExternalReferenceCode", scopeExternalReferenceCode
		);
	}

	private static ItemSelectorTypeOptions _toItemSelectorTypeOptions(
		JSONObject typeOptionsJSONObject) {

		if (typeOptionsJSONObject == null) {
			return null;
		}

		return new ItemSelectorTypeOptions() {
			{
				setClassName(
					() -> typeOptionsJSONObject.getString("className", null));
				setDependency(() -> _toDependencyMap(typeOptionsJSONObject));
				setEnableSelectTemplate(
					() -> {
						if (!typeOptionsJSONObject.has(
								"enableSelectTemplate")) {

							return null;
						}

						return typeOptionsJSONObject.getBoolean(
							"enableSelectTemplate");
					});
				setItemSubtype(
					() -> typeOptionsJSONObject.getString("itemSubtype", null));
				setItemType(
					() -> typeOptionsJSONObject.getString("itemType", null));
				setMimeTypes(
					() -> {
						JSONArray mimeTypesJSONArray =
							typeOptionsJSONObject.getJSONArray("mimeTypes");

						if (mimeTypesJSONArray == null) {
							return null;
						}

						return JSONUtil.toStringArray(mimeTypesJSONArray);
					});
			}
		};
	}

	private static ItemValue _toItemValue(
		JSONObject fieldJSONObject,
		DTOConverter
			<FragmentConfigurationField, FragmentConfigurationFieldValue>
				fragmentConfigurationFieldValueDTOConverter,
		FragmentEntry fragmentEntry) {

		FragmentConfigurationFieldValue fragmentConfigurationFieldValue =
			_toFragmentConfigurationFieldValue(
				fieldJSONObject, fragmentConfigurationFieldValueDTOConverter,
				fragmentEntry);

		if (!(fragmentConfigurationFieldValue instanceof
				ItemFragmentConfigurationFieldValue
					itemFragmentConfigurationFieldValue)) {

			return null;
		}

		ItemValue itemValue = itemFragmentConfigurationFieldValue.getValue();

		if (itemValue == null) {
			return null;
		}

		ItemExternalReference itemExternalReference =
			itemValue.getItemExternalReference();

		if ((itemExternalReference == null) ||
			Validator.isNull(
				itemExternalReference.getExternalReferenceCode())) {

			return null;
		}

		return itemValue;
	}

	private static JSONObject _toTypeOptionsJSONObject(
		ItemSelectorTypeOptions itemSelectorTypeOptions) {

		if (itemSelectorTypeOptions == null) {
			return null;
		}

		JSONObject typeOptionsJSONObject = JSONUtil.put(
			"className", itemSelectorTypeOptions.getClassName()
		).put(
			"dependency",
			_toDependencyJSONObject(itemSelectorTypeOptions.getDependency())
		).put(
			"enableSelectTemplate",
			itemSelectorTypeOptions.getEnableSelectTemplate()
		).put(
			"itemSubtype", itemSelectorTypeOptions.getItemSubtype()
		).put(
			"itemType", itemSelectorTypeOptions.getItemType()
		);

		String[] mimeTypes = itemSelectorTypeOptions.getMimeTypes();

		if (ArrayUtil.isNotEmpty(mimeTypes)) {
			typeOptionsJSONObject.put(
				"mimeTypes", JSONFactoryUtil.createJSONArray(mimeTypes));
		}

		if (typeOptionsJSONObject.length() == 0) {
			return null;
		}

		return typeOptionsJSONObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationUtil.class);

}