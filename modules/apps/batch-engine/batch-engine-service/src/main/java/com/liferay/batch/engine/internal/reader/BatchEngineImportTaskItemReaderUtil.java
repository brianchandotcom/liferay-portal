/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.reader;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import com.liferay.batch.engine.action.ItemReaderPostAction;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ivica Cardic
 */
public class BatchEngineImportTaskItemReaderUtil {

	public static <T> T convertValue(
			BatchEngineImportTask batchEngineImportTask, Class<T> itemClass,
			Map<String, Object> fieldNameValueMap,
			List<ItemReaderPostAction> itemReaderPostActions)
		throws ReflectiveOperationException {

		Map<String, Serializable> extendedProperties = new HashMap<>();
		T item = itemClass.newInstance();

		boolean keepCreatorInfo = false;

		if (FeatureFlagManagerUtil.isEnabled("LPD-11036") &&
			StringUtil.equals(
				batchEngineImportTask.getParameterValue(
					"importCreatorStrategy"),
				"KEEP_CREATOR")) {

			keepCreatorInfo = true;
		}

		Set<String> restrictedFieldNames = _getRestrictedFieldNames(
			batchEngineImportTask);

		for (Map.Entry<String, Object> entry : fieldNameValueMap.entrySet()) {
			String name = entry.getKey();

			if (restrictedFieldNames.contains(name)) {
				continue;
			}

			Field field = null;

			for (Field declaredField : itemClass.getDeclaredFields()) {
				if (name.equals(declaredField.getName()) ||
					Objects.equals(
						StringPool.UNDERLINE + name, declaredField.getName())) {

					field = declaredField;

					break;
				}
			}

			if (field != null) {
				field.setAccessible(true);

				ObjectMapper objectMapper = _getObjectMapper(
					field, keepCreatorInfo);

				field.set(
					item,
					objectMapper.convertValue(
						entry.getValue(), field.getType()));

				continue;
			}

			for (Field declaredField : itemClass.getDeclaredFields()) {
				JsonAnySetter[] jsonAnySetters =
					declaredField.getAnnotationsByType(JsonAnySetter.class);

				if (jsonAnySetters.length > 0) {
					field = declaredField;

					break;
				}
			}

			if (field == null) {
				extendedProperties.put(
					entry.getKey(), (Serializable)entry.getValue());
			}
			else {
				field.setAccessible(true);

				Map<String, Object> map = (Map)field.get(item);

				map.put(entry.getKey(), entry.getValue());
			}
		}

		for (ItemReaderPostAction itemReaderPostAction :
				itemReaderPostActions) {

			itemReaderPostAction.run(
				batchEngineImportTask, extendedProperties, item);
		}

		return item;
	}

	public static Map<String, Object> mapFieldNames(
		Map<String, ? extends Serializable> fieldNameMappingMap,
		Map<String, Object> fieldNameValueMap) {

		if ((fieldNameMappingMap == null) || fieldNameMappingMap.isEmpty()) {
			return fieldNameValueMap;
		}

		Map<String, Object> targetFieldNameValueMap = new HashMap<>();

		for (Map.Entry<String, Object> entry : fieldNameValueMap.entrySet()) {
			String targetFieldName = (String)fieldNameMappingMap.get(
				entry.getKey());

			if (Validator.isNotNull(targetFieldName)) {
				Object object = targetFieldNameValueMap.get(targetFieldName);

				if ((object != null) && (object instanceof Map)) {
					Map<?, ?> map = (Map)object;

					map.putAll((Map)entry.getValue());
				}
				else {
					targetFieldNameValueMap.put(
						targetFieldName, entry.getValue());
				}

				continue;
			}

			String[] fieldNameParts = StringUtil.split(
				entry.getKey(), StringPool.PERIOD);

			targetFieldName = (String)fieldNameMappingMap.get(
				fieldNameParts[0]);

			if (Validator.isNull(targetFieldName)) {
				continue;
			}

			Matcher multiselectPicklistMatcher =
				_multiselectPicklistPattern.matcher(fieldNameParts[1]);

			if (multiselectPicklistMatcher.matches()) {
				if (fieldNameParts[1].startsWith("name_")) {
					continue;
				}

				List<Object> list =
					(List<Object>)targetFieldNameValueMap.computeIfAbsent(
						targetFieldName, key -> new ArrayList<>());

				list.add(entry.getValue());

				continue;
			}

			Map<String, Object> map =
				(Map<String, Object>)targetFieldNameValueMap.computeIfAbsent(
					targetFieldName, key -> new HashMap<>());

			for (int i = 1; i < fieldNameParts.length; i++) {
				if ((fieldNameParts.length - 1) > i) {
					map = (Map<String, Object>)map.computeIfAbsent(
						fieldNameParts[i], key -> new HashMap<>());

					continue;
				}

				map.put(fieldNameParts[i], entry.getValue());
			}
		}

		return targetFieldNameValueMap;
	}

	public abstract static class CreatorMixin {

		@JsonProperty(access = JsonProperty.Access.READ_WRITE)
		public String externalReferenceCode;

		@JsonProperty(access = JsonProperty.Access.READ_WRITE)
		public Long id;

	}

	private static ObjectMapper _getObjectMapper(
			Field field, boolean keepCreatorInfo)
		throws IllegalAccessException, InstantiationException {

		if (keepCreatorInfo && StringUtil.equals(field.getName(), "creator")) {
			return new ObjectMapper() {
				{
					addMixIn(field.getType(), CreatorMixin.class);

					SimpleModule simpleModule = new SimpleModule();

					simpleModule.addDeserializer(
						Map.class, new MapStdDeserializer());

					registerModule(simpleModule);
				}
			};
		}

		JsonDeserialize[] jsonDeserializes = field.getAnnotationsByType(
			JsonDeserialize.class);

		if (ArrayUtil.isEmpty(jsonDeserializes)) {
			return _objectMapper;
		}

		JsonDeserialize jsonDeserialize = jsonDeserializes[0];

		return new ObjectMapper() {
			{
				SimpleModule simpleModule = new SimpleModule();

				simpleModule.addDeserializer(
					field.getType(),
					jsonDeserialize.using(
					).newInstance());

				registerModule(simpleModule);
			}
		};
	}

	private static Set<String> _getRestrictedFieldNames(
		BatchEngineImportTask batchEngineImportTask) {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-29367")) {
			return new HashSet<>();
		}

		Map<String, Serializable> parameters =
			batchEngineImportTask.getParameters();

		if (parameters == null) {
			return new HashSet<>();
		}

		String restrictedFieldNames = MapUtil.getString(
			parameters, "restrictedFieldNames");

		if (Validator.isBlank(restrictedFieldNames)) {
			return new HashSet<>();
		}

		return SetUtil.fromArray(StringUtil.split(restrictedFieldNames));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineImportTaskItemReaderUtil.class);

	private static final Pattern _multiselectPicklistPattern = Pattern.compile(
		"key_\\d+|name_\\d+");

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			SimpleModule simpleModule = new SimpleModule();

			simpleModule.addDeserializer(Map.class, new MapStdDeserializer());

			registerModule(simpleModule);
		}
	};

	private static class MapStdDeserializer
		extends StdDeserializer<Map<String, Object>> {

		public MapStdDeserializer() {
			this(Map.class);
		}

		@Override
		public Map<String, Object> deserialize(
				JsonParser jsonParser,
				DeserializationContext deserializationContext)
			throws IOException {

			try {
				return deserializationContext.readValue(
					jsonParser, LinkedHashMap.class);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				Map<String, Object> map = new LinkedHashMap<>();

				String string = jsonParser.getValueAsString();

				for (String line : string.split(StringPool.RETURN_NEW_LINE)) {
					String[] lineParts = line.split(StringPool.COLON);

					map.put(lineParts[0], lineParts[1]);
				}

				return map;
			}
		}

		protected MapStdDeserializer(Class<?> clazz) {
			super(clazz);
		}

	}

}