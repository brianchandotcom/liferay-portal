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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.model.DDMField;
import com.liferay.dynamic.data.mapping.model.DDMFieldAttribute;
import com.liferay.dynamic.data.mapping.model.DDMFieldAttributeTable;
import com.liferay.dynamic.data.mapping.model.DDMFieldTable;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersionTable;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.model.impl.DDMFieldAttributeImpl;
import com.liferay.dynamic.data.mapping.service.base.DDMFieldLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFieldAttributePersistence;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructurePersistence;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMField",
	service = AopService.class
)
public class DDMFieldLocalServiceImpl extends DDMFieldLocalServiceBaseImpl {

	@Override
	public void deleteDDMFields(long structureId) {
		for (DDMFieldAttribute ddmFieldAttribute :
				_ddmFieldAttributePersistence.<List<DDMFieldAttribute>>dslQuery(
					DSLQueryFactoryUtil.select(
						DDMFieldAttributeTable.INSTANCE
					).from(
						DDMFieldAttributeTable.INSTANCE
					).innerJoinON(
						DDMFieldTable.INSTANCE,
						DDMFieldTable.INSTANCE.fieldId.eq(
							DDMFieldAttributeTable.INSTANCE.fieldId)
					).innerJoinON(
						DDMStructureVersionTable.INSTANCE,
						DDMStructureVersionTable.INSTANCE.structureVersionId.eq(
							DDMFieldTable.INSTANCE.structureVersionId
						).and(
							DDMStructureVersionTable.INSTANCE.structureId.eq(
								structureId)
						)
					))) {

			_ddmFieldAttributePersistence.remove(ddmFieldAttribute);
		}

		for (DDMField ddmField :
				ddmFieldPersistence.<List<DDMField>>dslQuery(
					DSLQueryFactoryUtil.select(
						DDMFieldTable.INSTANCE
					).from(
						DDMFieldTable.INSTANCE
					).innerJoinON(
						DDMStructureVersionTable.INSTANCE,
						DDMStructureVersionTable.INSTANCE.structureVersionId.eq(
							DDMFieldTable.INSTANCE.structureVersionId
						).and(
							DDMStructureVersionTable.INSTANCE.structureId.eq(
								structureId)
						)
					))) {

			ddmFieldPersistence.remove(ddmField);
		}
	}

	@Override
	public void deleteDDMFormValues(long storageId) {
		ddmFieldPersistence.removeByStorageId(storageId);

		_ddmFieldAttributePersistence.removeByStorageId(storageId);
	}

	@Override
	public DDMFormValues getDDMFormValues(DDMForm ddmForm, long storageId) {
		List<DDMField> ddmFields = ddmFieldPersistence.findByStorageId(
			storageId);

		if (ddmFields.isEmpty()) {
			return null;
		}

		Map<Long, DDMFieldInfo> ddmFieldInfoMap = new LinkedHashMap<>();

		for (DDMField ddmField : ddmFields) {
			if (ddmField.getParentFieldId() == 0) {
				ddmFieldInfoMap.put(
					ddmField.getFieldId(),
					new DDMFieldInfo(
						ddmField.getFieldName(), ddmField.getInstanceId(),
						ddmField.isLocalizable(), null));
			}
			else {
				DDMFieldInfo parentDDMFieldInfo = ddmFieldInfoMap.get(
					ddmField.getParentFieldId());

				DDMFieldInfo ddmFieldInfo = new DDMFieldInfo(
					ddmField.getFieldName(), ddmField.getInstanceId(),
					ddmField.isLocalizable(), parentDDMFieldInfo._instanceId);

				parentDDMFieldInfo._childDDMFieldInfos.add(ddmFieldInfo);

				ddmFieldInfoMap.put(ddmField.getFieldId(), ddmFieldInfo);
			}
		}

		for (DDMFieldAttribute ddmFieldAttribute :
				_ddmFieldAttributePersistence.findByStorageId(storageId)) {

			DDMFieldInfo ddmFieldInfo = ddmFieldInfoMap.get(
				ddmFieldAttribute.getFieldId());

			List<DDMFieldAttributeInfo> ddmFieldAttributeInfos =
				ddmFieldInfo._ddmFieldAttributeInfos.computeIfAbsent(
					ddmFieldAttribute.getLanguageId(),
					languageId -> new ArrayList<>());

			ddmFieldAttributeInfos.add(
				new DDMFieldAttributeInfo(
					ddmFieldAttribute.getAttributeName(),
					ddmFieldAttribute.getAttributeValue(), ddmFieldInfo,
					ddmFieldAttribute.getLanguageId()));
		}

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		DDMField rootDDMField = ddmFields.get(0);

		DDMFieldInfo rootDDMFieldInfo = ddmFieldInfoMap.remove(
			rootDDMField.getFieldId());

		for (DDMFieldAttributeInfo ddmFieldAttributeInfo :
				rootDDMFieldInfo._ddmFieldAttributeInfos.get(
					StringPool.BLANK)) {

			String attributeName = ddmFieldAttributeInfo._attributeName;

			if (Objects.equals(attributeName, "availableLanguageIds")) {
				for (String availableLanguageId :
						StringUtil.split(
							ddmFieldAttributeInfo._attributeValue)) {

					ddmFormValues.addAvailableLocale(
						LocaleUtil.fromLanguageId(availableLanguageId));
				}
			}
			else if (Objects.equals(attributeName, "defaultLanguageId")) {
				ddmFormValues.setDefaultLocale(
					LocaleUtil.fromLanguageId(
						ddmFieldAttributeInfo._attributeValue));
			}
		}

		for (DDMFieldInfo ddmFieldInfo : ddmFieldInfoMap.values()) {
			if (ddmFieldInfo._parentInstanceId == null) {
				ddmFormValues.addDDMFormFieldValue(
					_getDDMFormFieldValue(
						ddmFieldInfo, ddmFormValues.getDefaultLocale()));
			}
		}

		return ddmFormValues;
	}

	@Override
	public int getDDMFormValuesCount(long structureId) {
		Long count = ddmFieldPersistence.dslQuery(
			DSLQueryFactoryUtil.count(
			).from(
				DDMFieldTable.INSTANCE
			).innerJoinON(
				DDMStructureVersionTable.INSTANCE,
				DDMStructureVersionTable.INSTANCE.structureVersionId.eq(
					DDMFieldTable.INSTANCE.structureVersionId
				).and(
					DDMStructureVersionTable.INSTANCE.structureId.eq(
						structureId)
				)
			));

		return count.intValue();
	}

	@Override
	public int getDDMFormValuesCount(
		long companyId, String fieldType, Map<String, Object> attributes) {

		JoinStep joinStep = DSLQueryFactoryUtil.count(
		).from(
			DDMFieldTable.INSTANCE
		);

		int aliasCount = 0;

		Column<DDMFieldAttributeTable, String> languageIdColumn = null;

		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			aliasCount++;

			DDMFieldAttributeTable aliasDDMFieldAttributeTable =
				DDMFieldAttributeTable.INSTANCE.as(
					"aliasDDMFieldAttributeTable" + aliasCount);

			Predicate predicate = DDMFieldTable.INSTANCE.fieldId.eq(
				aliasDDMFieldAttributeTable.fieldId);

			if (languageIdColumn != null) {
				predicate = predicate.and(
					aliasDDMFieldAttributeTable.languageId.eq(
						languageIdColumn));
			}

			String key = entry.getKey();
			String value = null;

			if ((key == null) || key.isEmpty()) {
				predicate = predicate.and(
					aliasDDMFieldAttributeTable.attributeName.eq(
						key
					).or(
						aliasDDMFieldAttributeTable.attributeName.isNull()
					).withParentheses());

				value = String.valueOf(entry.getValue());
			}
			else {
				predicate = predicate.and(
					aliasDDMFieldAttributeTable.attributeName.eq(key));

				value = jsonSerializer.serialize(entry.getValue());
			}

			Expression<String> valueExpression =
				aliasDDMFieldAttributeTable.smallAttributeValue;

			if (value.length() >
					DDMFieldAttributeImpl.SMALL_ATTRIBUTE_VALUE_MAX_LENGTH) {

				valueExpression = DSLFunctionFactoryUtil.castClobText(
					aliasDDMFieldAttributeTable.largeAttributeValue);
			}

			joinStep = joinStep.innerJoinON(
				aliasDDMFieldAttributeTable,
				predicate.and(valueExpression.eq(value)));

			languageIdColumn = aliasDDMFieldAttributeTable.languageId;
		}

		Long count = ddmFieldPersistence.dslQuery(
			joinStep.where(
				DDMFieldTable.INSTANCE.companyId.eq(
					companyId
				).and(
					DDMFieldTable.INSTANCE.fieldType.eq(fieldType)
				)));

		return count.intValue();
	}

	@Override
	public void updateDDMFormValues(
			long structureId, long storageId, DDMFormValues ddmFormValues)
		throws PortalException {

		DDMStructure ddmStructure = _ddmStructurePersistence.findByPrimaryKey(
			structureId);

		DDMStructureVersion ddmStructureVersion =
			ddmStructure.getStructureVersion();

		DDMForm ddmForm = ddmFormValues.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		DDMFieldInfo rootDDMFieldInfo = new DDMFieldInfo(
			StringPool.BLANK, StringPool.BLANK, false, null);

		Map<String, DDMFieldInfo> ddmFieldInfoMap = LinkedHashMapBuilder.put(
			StringPool.BLANK, rootDDMFieldInfo
		).build();

		rootDDMFieldInfo._ddmFieldAttributeInfos.put(
			StringPool.BLANK,
			Arrays.asList(
				new DDMFieldAttributeInfo(
					"availableLanguageIds",
					StringUtil.merge(
						ddmFormValues.getAvailableLocales(),
						LocaleUtil::toLanguageId, StringPool.COMMA),
					rootDDMFieldInfo, StringPool.BLANK),
				new DDMFieldAttributeInfo(
					"defaultLanguageId",
					LocaleUtil.toLanguageId(ddmFormValues.getDefaultLocale()),
					rootDDMFieldInfo, StringPool.BLANK)));

		_collectDDMFieldInfos(
			ddmFieldInfoMap, ddmFormFieldsMap,
			ddmFormValues.getDDMFormFieldValues(), null);

		DDMFormUpdateContext ddmFormUpdateContext = _getDDMFormUpdateContext(
			ddmFieldInfoMap, storageId);

		long batchCounter = 0;

		if (ddmFormUpdateContext._newDDMFieldsCount > 0) {
			batchCounter = counterLocalService.increment(
				DDMField.class.getName(),
				ddmFormUpdateContext._newDDMFieldsCount);

			batchCounter -= ddmFormUpdateContext._newDDMFieldsCount;
		}

		int priority = 0;

		Map<String, Long> instanceToFieldIdMap = new HashMap<>();

		for (Map.Entry<DDMField, DDMFieldInfo> entry :
				ddmFormUpdateContext._ddmFieldEntries) {

			DDMField ddmField = entry.getKey();
			DDMFieldInfo ddmFieldInfo = entry.getValue();

			if (ddmFieldInfo == null) {
				ddmFieldPersistence.remove(ddmField);

				continue;
			}

			if (ddmField == null) {
				ddmField = ddmFieldPersistence.create(++batchCounter);
			}

			long parentFieldId = 0;

			if (ddmFieldInfo._parentInstanceId != null) {
				parentFieldId = instanceToFieldIdMap.get(
					ddmFieldInfo._parentInstanceId);
			}

			ddmField.setStructureVersionId(
				ddmStructureVersion.getStructureVersionId());
			ddmField.setParentFieldId(parentFieldId);
			ddmField.setStorageId(storageId);
			ddmField.setFieldName(ddmFieldInfo._fieldName);
			ddmField.setPriority(priority);
			ddmField.setInstanceId(ddmFieldInfo._instanceId);

			if (ddmFieldInfo != rootDDMFieldInfo) {
				DDMFormField ddmFormField = ddmFormFieldsMap.get(
					ddmFieldInfo._fieldName);

				ddmField.setFieldType(ddmFormField.getType());
				ddmField.setLocalizable(ddmFormField.isLocalizable());
			}

			ddmField = ddmFieldPersistence.update(ddmField);

			priority++;

			instanceToFieldIdMap.put(
				ddmField.getInstanceId(), ddmField.getFieldId());
		}

		if (ddmFormUpdateContext._newDDMFieldAttributesCount > 0) {
			batchCounter = counterLocalService.increment(
				DDMFieldAttribute.class.getName(),
				ddmFormUpdateContext._newDDMFieldAttributesCount);

			batchCounter -= ddmFormUpdateContext._newDDMFieldAttributesCount;
		}

		for (Map.Entry<DDMFieldAttribute, DDMFieldAttributeInfo> entry :
				ddmFormUpdateContext._ddmFieldAttributeEntries) {

			DDMFieldAttribute ddmFieldAttribute = entry.getKey();
			DDMFieldAttributeInfo ddmFieldAttributeInfo = entry.getValue();

			if (ddmFieldAttributeInfo == null) {
				_ddmFieldAttributePersistence.remove(ddmFieldAttribute);

				continue;
			}

			if (ddmFieldAttribute == null) {
				ddmFieldAttribute = _ddmFieldAttributePersistence.create(
					++batchCounter);
			}

			long fieldId = instanceToFieldIdMap.get(
				ddmFieldAttributeInfo._ddmFieldInfo._instanceId);

			ddmFieldAttribute.setFieldId(fieldId);

			ddmFieldAttribute.setStorageId(storageId);
			ddmFieldAttribute.setLanguageId(ddmFieldAttributeInfo._languageId);
			ddmFieldAttribute.setAttributeName(
				ddmFieldAttributeInfo._attributeName);
			ddmFieldAttribute.setAttributeValue(
				ddmFieldAttributeInfo._attributeValue);

			_ddmFieldAttributePersistence.update(ddmFieldAttribute);
		}
	}

	private void _collectDDMFieldInfos(
		Map<String, DDMFieldInfo> ddmFieldInfoMap,
		Map<String, DDMFormField> ddmFormFieldMap,
		List<DDMFormFieldValue> ddmFormValues, String parentInstanceId) {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormValues) {
			DDMFormField ddmFormField = ddmFormFieldMap.get(
				ddmFormFieldValue.getName());

			DDMFieldInfo ddmFieldInfo = new DDMFieldInfo(
				ddmFormFieldValue.getName(), ddmFormFieldValue.getInstanceId(),
				ddmFormField.isLocalizable(), parentInstanceId);

			ddmFieldInfoMap.put(ddmFieldInfo._instanceId, ddmFieldInfo);

			Value value = ddmFormFieldValue.getValue();

			if (value != null) {
				Map<Locale, String> values = value.getValues();

				for (Map.Entry<Locale, String> entry : values.entrySet()) {
					String languageId = LanguageUtil.getLanguageId(
						entry.getKey());

					ddmFieldInfo._ddmFieldAttributeInfos.put(
						languageId,
						_getDDMFieldAttributeInfos(
							ddmFieldInfo, languageId, entry.getValue()));
				}
			}

			_collectDDMFieldInfos(
				ddmFieldInfoMap, ddmFormFieldMap,
				ddmFormFieldValue.getNestedDDMFormFieldValues(),
				ddmFieldInfo._instanceId);
		}
	}

	private List<DDMFieldAttributeInfo> _getDDMFieldAttributeInfos(
		DDMFieldInfo ddmFieldInfo, String languageId, String valueString) {

		int length = valueString.length();

		if ((length > 1) &&
			(valueString.charAt(0) == CharPool.OPEN_CURLY_BRACE) &&
			(valueString.charAt(length - 1) == CharPool.CLOSE_CURLY_BRACE)) {

			try {
				JSONSerializer jsonSerializer =
					_jsonFactory.createJSONSerializer();

				JSONObject jsonObject = _jsonFactory.createJSONObject(
					valueString);

				Set<String> keySet = jsonObject.keySet();

				if (!keySet.isEmpty()) {
					List<DDMFieldAttributeInfo> ddmFieldAttributeInfos =
						new ArrayList<>(keySet.size());

					for (String key : jsonObject.keySet()) {
						ddmFieldAttributeInfos.add(
							new DDMFieldAttributeInfo(
								key,
								jsonSerializer.serialize(jsonObject.get(key)),
								ddmFieldInfo, languageId));
					}

					return ddmFieldAttributeInfos;
				}
			}
			catch (JSONException jsonException) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to parse: " + valueString, jsonException);
				}
			}
		}

		return Collections.singletonList(
			new DDMFieldAttributeInfo(
				StringPool.BLANK, valueString, ddmFieldInfo, languageId));
	}

	private DDMFormFieldValue _getDDMFormFieldValue(
		DDMFieldInfo ddmFieldInfo, Locale defaultLocale) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(ddmFieldInfo._instanceId);
		ddmFormFieldValue.setName(ddmFieldInfo._fieldName);

		if (ddmFieldInfo._localizable) {
			Value value = new LocalizedValue(defaultLocale);

			for (Map.Entry<String, List<DDMFieldAttributeInfo>> entry :
					ddmFieldInfo._ddmFieldAttributeInfos.entrySet()) {

				Locale locale = LocaleUtil.fromLanguageId(
					entry.getKey(), true, false);

				if (locale != null) {
					value.addString(locale, _getValueString(entry.getValue()));
				}
			}

			ddmFormFieldValue.setValue(value);
		}
		else {
			List<DDMFieldAttributeInfo> ddmFieldAttributeInfos =
				ddmFieldInfo._ddmFieldAttributeInfos.get(StringPool.BLANK);

			if (ddmFieldAttributeInfos != null) {
				ddmFormFieldValue.setValue(
					new UnlocalizedValue(
						_getValueString(ddmFieldAttributeInfos)));
			}
		}

		for (DDMFieldInfo childDDMFieldInfo :
				ddmFieldInfo._childDDMFieldInfos) {

			ddmFormFieldValue.addNestedDDMFormFieldValue(
				_getDDMFormFieldValue(childDDMFieldInfo, defaultLocale));
		}

		return ddmFormFieldValue;
	}

	private DDMFormUpdateContext _getDDMFormUpdateContext(
		Map<String, DDMFieldInfo> ddmFieldInfoMap, long storageId) {

		List<Map.Entry<DDMField, DDMFieldInfo>> ddmFieldEntries =
			new ArrayList<>();

		int newDDMFieldsCount = 0;

		List<DDMField> ddmFields = ddmFieldPersistence.findByStorageId(
			storageId);

		Set<Long> matchedDDMFieldIds = new HashSet<>();

		iterate:
		for (DDMFieldInfo ddmFieldInfo : ddmFieldInfoMap.values()) {
			for (DDMField ddmField : ddmFields) {
				if (ddmFieldInfo._instanceId.equals(ddmField.getInstanceId())) {
					matchedDDMFieldIds.add(ddmField.getFieldId());

					ddmFieldEntries.add(
						new AbstractMap.SimpleImmutableEntry<>(
							ddmField, ddmFieldInfo));

					continue iterate;
				}
			}

			ddmFieldEntries.add(
				new AbstractMap.SimpleImmutableEntry<>(null, ddmFieldInfo));

			newDDMFieldsCount++;
		}

		if (matchedDDMFieldIds.size() < ddmFields.size()) {
			for (DDMField ddmField : ddmFields) {
				if (!matchedDDMFieldIds.contains(ddmField.getFieldId())) {
					ddmFieldEntries.add(
						new AbstractMap.SimpleImmutableEntry<>(ddmField, null));
				}
			}
		}

		List<Map.Entry<DDMFieldAttribute, DDMFieldAttributeInfo>>
			ddmFieldAttributeEntries = new ArrayList<>();

		int newDDMFieldAttributesCount = 0;

		List<DDMFieldAttribute> ddmFieldAttributes =
			_ddmFieldAttributePersistence.findByStorageId(storageId);

		Set<Long> matchedDDMFieldAttributeIds = new HashSet<>();

		for (Map.Entry<DDMField, DDMFieldInfo> ddmFieldEntry :
				ddmFieldEntries) {

			DDMFieldInfo ddmFieldInfo = ddmFieldEntry.getValue();

			if (ddmFieldInfo == null) {
				continue;
			}

			DDMField ddmField = ddmFieldEntry.getKey();

			for (List<DDMFieldAttributeInfo> ddmFieldAttributeInfos :
					ddmFieldInfo._ddmFieldAttributeInfos.values()) {

				iterate:
				for (DDMFieldAttributeInfo ddmFieldAttributeInfo :
						ddmFieldAttributeInfos) {

					if (ddmField != null) {
						for (DDMFieldAttribute ddmFieldAttribute :
								ddmFieldAttributes) {

							if ((ddmField.getFieldId() ==
									ddmFieldAttribute.getFieldId()) &&
								Objects.equals(
									ddmFieldAttributeInfo._languageId,
									ddmFieldAttribute.getLanguageId()) &&
								Objects.equals(
									ddmFieldAttributeInfo._attributeName,
									ddmFieldAttribute.getAttributeName())) {

								matchedDDMFieldAttributeIds.add(
									ddmFieldAttribute.getFieldAttributeId());

								ddmFieldAttributeEntries.add(
									new AbstractMap.SimpleImmutableEntry<>(
										ddmFieldAttribute,
										ddmFieldAttributeInfo));

								continue iterate;
							}
						}
					}

					ddmFieldAttributeEntries.add(
						new AbstractMap.SimpleImmutableEntry<>(
							null, ddmFieldAttributeInfo));

					newDDMFieldAttributesCount++;
				}
			}
		}

		if (matchedDDMFieldAttributeIds.size() < ddmFieldAttributes.size()) {
			for (DDMFieldAttribute ddmFieldAttribute : ddmFieldAttributes) {
				if (!matchedDDMFieldAttributeIds.contains(
						ddmFieldAttribute.getFieldAttributeId())) {

					ddmFieldAttributeEntries.add(
						new AbstractMap.SimpleImmutableEntry<>(
							ddmFieldAttribute, null));
				}
			}
		}

		return new DDMFormUpdateContext(
			ddmFieldAttributeEntries, ddmFieldEntries,
			newDDMFieldAttributesCount, newDDMFieldsCount);
	}

	private String _getValueString(
		List<DDMFieldAttributeInfo> ddmFieldAttributeInfos) {

		if (ddmFieldAttributeInfos.size() == 1) {
			DDMFieldAttributeInfo ddmFieldAttributeInfo =
				ddmFieldAttributeInfos.get(0);

			if (ddmFieldAttributeInfo._attributeName.isEmpty()) {
				return ddmFieldAttributeInfo._attributeValue;
			}
		}

		Map<String, Object> map = new TreeMap<>();

		JSONDeserializer<Object> jsonDeserializer =
			_jsonFactory.createJSONDeserializer();

		for (DDMFieldAttributeInfo ddmFieldAttributeInfo :
				ddmFieldAttributeInfos) {

			map.put(
				ddmFieldAttributeInfo._attributeName,
				jsonDeserializer.deserialize(
					ddmFieldAttributeInfo._attributeValue));
		}

		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		return jsonSerializer.serialize(map);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFieldLocalServiceImpl.class);

	@Reference
	private DDMFieldAttributePersistence _ddmFieldAttributePersistence;

	@Reference
	private DDMStructurePersistence _ddmStructurePersistence;

	@Reference
	private JSONFactory _jsonFactory;

	private static class DDMFieldAttributeInfo {

		private DDMFieldAttributeInfo(
			String attributeName, String attributeValue,
			DDMFieldInfo ddmFieldInfo, String languageId) {

			_attributeName = attributeName;
			_attributeValue = attributeValue;
			_ddmFieldInfo = ddmFieldInfo;
			_languageId = languageId;
		}

		private final String _attributeName;
		private final String _attributeValue;
		private final DDMFieldInfo _ddmFieldInfo;
		private final String _languageId;

	}

	private static class DDMFieldInfo {

		private DDMFieldInfo(
			String fieldName, String instanceId, boolean localizable,
			String parentInstanceId) {

			_fieldName = fieldName;
			_instanceId = instanceId;
			_localizable = localizable;
			_parentInstanceId = parentInstanceId;
		}

		private final List<DDMFieldInfo> _childDDMFieldInfos =
			new ArrayList<>();
		private final Map<String, List<DDMFieldAttributeInfo>>
			_ddmFieldAttributeInfos = new HashMap<>();
		private final String _fieldName;
		private final String _instanceId;
		private final boolean _localizable;
		private final String _parentInstanceId;

	}

	private static class DDMFormUpdateContext {

		private DDMFormUpdateContext(
			List<Map.Entry<DDMFieldAttribute, DDMFieldAttributeInfo>>
				ddmFieldAttributeEntries,
			List<Map.Entry<DDMField, DDMFieldInfo>> ddmFieldEntries,
			int newDDMFieldAttributesCount, int newDDMFieldsCount) {

			_ddmFieldAttributeEntries = ddmFieldAttributeEntries;
			_ddmFieldEntries = ddmFieldEntries;
			_newDDMFieldAttributesCount = newDDMFieldAttributesCount;
			_newDDMFieldsCount = newDDMFieldsCount;
		}

		private final List<Map.Entry<DDMFieldAttribute, DDMFieldAttributeInfo>>
			_ddmFieldAttributeEntries;
		private final List<Map.Entry<DDMField, DDMFieldInfo>> _ddmFieldEntries;
		private final int _newDDMFieldAttributesCount;
		private final int _newDDMFieldsCount;

	}

}