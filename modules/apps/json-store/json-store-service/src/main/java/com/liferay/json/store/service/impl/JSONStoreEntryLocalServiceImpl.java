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

package com.liferay.json.store.service.impl;

import com.liferay.json.store.constants.JSONStoreEntryConstants;
import com.liferay.json.store.model.JSONStoreEntry;
import com.liferay.json.store.model.JSONStoreEntryTable;
import com.liferay.json.store.service.base.JSONStoreEntryLocalServiceBaseImpl;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.sql.dsl.query.OrderByStep;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.json.store.model.JSONStoreEntry",
	service = AopService.class
)
public class JSONStoreEntryLocalServiceImpl
	extends JSONStoreEntryLocalServiceBaseImpl {

	@Override
	public void addJSON(
		long companyId, long classNameId, long classPK, String json) {

		_updateJSON(
			companyId, classNameId, classPK, Collections.emptyList(), json);
	}

	@Override
	public void deleteJSON(long classNameId, long classPK) {
		jsonStoreEntryPersistence.removeByCN_CPK(classNameId, classPK);
	}

	@Override
	public List<Long> getClassPKs(
		long companyId, long classNameId, Object[] pathParts, Object value,
		int start, int end) {

		OrderByStep orderByStep = _getOrderByStep(
			DSLQueryFactoryUtil.selectDistinct(
				JSONStoreEntryTable.INSTANCE.classPK),
			companyId, classNameId, pathParts, value);

		return jsonStoreEntryPersistence.dslQuery(
			orderByStep.orderBy(
				JSONStoreEntryTable.INSTANCE.classPK.ascending()
			).limit(
				start, end
			));
	}

	@Override
	public int getClassPKsCount(
		long companyId, long classNameId, Object[] pathParts, Object value) {

		OrderByStep orderByStep = _getOrderByStep(
			DSLQueryFactoryUtil.countDistinct(
				JSONStoreEntryTable.INSTANCE.classPK),
			companyId, classNameId, pathParts, value);

		Long count = jsonStoreEntryPersistence.dslQuery(orderByStep);

		return count.intValue();
	}

	@Override
	public JSONArray getJSONArray(long classNameId, long classPK) {
		JSONSerializable jsonSerializable = _getJSONSerializable(
			jsonStoreEntryPersistence.findByCN_CPK(classNameId, classPK));

		if (jsonSerializable == null) {
			return null;
		}

		return (JSONArray)jsonSerializable;
	}

	@Override
	public JSONObject getJSONObject(long classNameId, long classPK) {
		JSONSerializable jsonSerializable = _getJSONSerializable(
			jsonStoreEntryPersistence.findByCN_CPK(classNameId, classPK));

		if (jsonSerializable == null) {
			return null;
		}

		return (JSONObject)jsonSerializable;
	}

	@Override
	public JSONSerializable getJSONSerializable(
		long classNameId, long classPK) {

		return _getJSONSerializable(
			jsonStoreEntryPersistence.findByCN_CPK(classNameId, classPK));
	}

	@Override
	public String getJSONString(long classNameId, long classPK) {
		JSONSerializable jsonSerializable = _getJSONSerializable(
			jsonStoreEntryPersistence.findByCN_CPK(classNameId, classPK));

		if (jsonSerializable == null) {
			return null;
		}

		return jsonSerializable.toJSONString();
	}

	@Override
	public void updateJSON(
		long companyId, long classNameId, long classPK, String json) {

		_updateJSON(
			companyId, classNameId, classPK,
			jsonStoreEntryPersistence.findByCN_CPK(classNameId, classPK), json);
	}

	private JSONSerializable _getJSONSerializable(
		List<JSONStoreEntry> jsonStoreEntries) {

		Map<Long, JSONSerializable> map = new HashMap<>();

		for (JSONStoreEntry jsonStoreEntry : jsonStoreEntries) {
			JSONArray jsonArray = null;
			JSONObject jsonObject = null;

			if (jsonStoreEntry.getIndex() ==
					JSONStoreEntryConstants.JSON_OBJECT_INDEX) {

				jsonObject = (JSONObject)map.computeIfAbsent(
					jsonStoreEntry.getParentJSONStoreEntryId(),
					key -> _jsonFactory.createJSONObject());
			}
			else {
				jsonArray = (JSONArray)map.computeIfAbsent(
					jsonStoreEntry.getParentJSONStoreEntryId(),
					key -> _jsonFactory.createJSONArray());
			}

			int type = jsonStoreEntry.getType();

			if (type != JSONStoreEntryConstants.TYPE_EMPTY) {
				Object value = null;

				if (type == JSONStoreEntryConstants.TYPE_ARRAY) {
					value = map.computeIfAbsent(
						jsonStoreEntry.getPrimaryKey(),
						key -> _jsonFactory.createJSONArray());
				}
				else if (type == JSONStoreEntryConstants.TYPE_OBJECT) {
					value = map.computeIfAbsent(
						jsonStoreEntry.getPrimaryKey(),
						key -> _jsonFactory.createJSONObject());
				}
				else if (type == JSONStoreEntryConstants.TYPE_VALUE_LONG) {
					value = jsonStoreEntry.getValueLong();
				}
				else if (type ==
							JSONStoreEntryConstants.TYPE_VALUE_LONG_QUOTED) {

					value = String.valueOf(jsonStoreEntry.getValueLong());
				}
				else if (type == JSONStoreEntryConstants.TYPE_VALUE_STRING) {
					JSONDeserializer<?> jsonDeserializer =
						_jsonFactory.createJSONDeserializer();

					value = jsonDeserializer.deserialize(
						jsonStoreEntry.getValueString());
				}

				if (jsonArray == null) {
					jsonObject.put(jsonStoreEntry.getKey(), value);
				}
				else {
					jsonArray.put(value);
				}
			}
		}

		return map.get(JSONStoreEntryConstants.DEFAULT_PARENT_JSON_ENTRY_ID);
	}

	private OrderByStep _getOrderByStep(
		FromStep fromStep, long companyId, long classNameId, Object[] pathParts,
		Object value) {

		JoinStep joinStep = fromStep.from(JSONStoreEntryTable.INSTANCE);

		Predicate predicate = JSONStoreEntryTable.INSTANCE.companyId.eq(
			companyId
		).and(
			JSONStoreEntryTable.INSTANCE.classNameId.eq(classNameId)
		);

		Long valueLong = null;

		if (value instanceof Integer || value instanceof Long) {
			Number number = (Number)value;

			valueLong = number.longValue();
		}
		else {
			valueLong = _parseLong(value);
		}

		if (valueLong != null) {
			predicate = predicate.and(
				JSONStoreEntryTable.INSTANCE.type.in(
					new Integer[] {
						JSONStoreEntryConstants.TYPE_VALUE_LONG,
						JSONStoreEntryConstants.TYPE_VALUE_LONG_QUOTED
					})
			).and(
				JSONStoreEntryTable.INSTANCE.valueLong.eq(valueLong)
			);
		}
		else {
			JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

			predicate = predicate.and(
				JSONStoreEntryTable.INSTANCE.type.eq(
					JSONStoreEntryConstants.TYPE_VALUE_STRING)
			).and(
				DSLFunctionFactoryUtil.castClobText(
					JSONStoreEntryTable.INSTANCE.valueString
				).eq(
					jsonSerializer.serialize(value)
				)
			);
		}

		if (ArrayUtil.isEmpty(pathParts)) {
			return joinStep.where(predicate);
		}

		Object pathPart = pathParts[pathParts.length - 1];

		if (pathPart instanceof String) {
			predicate = predicate.and(
				JSONStoreEntryTable.INSTANCE.key.eq((String)pathPart));
		}
		else if (pathPart instanceof Integer) {
			predicate = predicate.and(
				JSONStoreEntryTable.INSTANCE.index.eq((Integer)pathPart));
		}

		JSONStoreEntryTable previousJSONStoreEntryTable =
			JSONStoreEntryTable.INSTANCE;

		Object previousPathPart = pathPart;

		for (int i = pathParts.length - 2; i >= 0; i--) {
			JSONStoreEntryTable aliasJSONStoreEntryTable =
				JSONStoreEntryTable.INSTANCE.as("aliasJSONStoreEntryTable" + i);

			joinStep = joinStep.innerJoinON(
				aliasJSONStoreEntryTable,
				aliasJSONStoreEntryTable.jsonStoreEntryId.eq(
					previousJSONStoreEntryTable.parentJSONStoreEntryId));

			pathPart = pathParts[i];

			if (pathPart instanceof String) {
				String key = (String)pathPart;

				predicate = predicate.and(aliasJSONStoreEntryTable.key.eq(key));
			}
			else if (pathPart instanceof Integer) {
				Integer index = (Integer)pathPart;

				predicate = predicate.and(
					aliasJSONStoreEntryTable.index.eq(index));
			}

			if (previousPathPart instanceof String) {
				predicate = predicate.and(
					aliasJSONStoreEntryTable.type.eq(
						JSONStoreEntryConstants.TYPE_OBJECT));
			}
			else if (previousPathPart instanceof Integer) {
				predicate = predicate.and(
					aliasJSONStoreEntryTable.type.eq(
						JSONStoreEntryConstants.TYPE_ARRAY));
			}

			previousJSONStoreEntryTable = aliasJSONStoreEntryTable;

			previousPathPart = pathPart;
		}

		return joinStep.where(predicate);
	}

	private Long _parseLong(Object object) {
		if (!(object instanceof String)) {
			return null;
		}

		String value = (String)object;

		int length = value.length();

		if (length <= 0) {
			return null;
		}

		int pos = 0;
		long limit = -Long.MAX_VALUE;
		boolean negative = false;

		char c = value.charAt(0);

		if (c < CharPool.NUMBER_0) {
			if (c == CharPool.MINUS) {
				limit = Long.MIN_VALUE;
				negative = true;
			}
			else if (c != CharPool.PLUS) {
				return null;
			}

			if (length == 1) {
				return null;
			}

			pos++;
		}

		long smallLimit = limit / 10;

		long result = 0;

		while (pos < length) {
			if (result < smallLimit) {
				return null;
			}

			c = value.charAt(pos++);

			if ((c < CharPool.NUMBER_0) || (c > CharPool.NUMBER_9)) {
				return null;
			}

			int number = c - CharPool.NUMBER_0;

			result *= 10;

			if (result < (limit + number)) {
				return null;
			}

			result -= number;
		}

		if (negative) {
			return result;
		}

		return -result;
	}

	private void _removeChildJSONStoreEntries(
		JSONStoreEntry jsonStoreEntry,
		Map<Long, List<JSONStoreEntry>> jsonStoreEntryMap) {

		List<JSONStoreEntry> jsonStoreEntries = jsonStoreEntryMap.get(
			jsonStoreEntry.getPrimaryKey());

		if (jsonStoreEntries == null) {
			return;
		}

		Queue<JSONStoreEntry> queue = new LinkedList<>(jsonStoreEntries);

		while ((jsonStoreEntry = queue.poll()) != null) {
			jsonStoreEntries = jsonStoreEntryMap.get(
				jsonStoreEntry.getPrimaryKey());

			if (jsonStoreEntries != null) {
				queue.addAll(jsonStoreEntries);
			}

			jsonStoreEntryPersistence.remove(jsonStoreEntry);
		}
	}

	private void _updateEmptyJSONStoreEntry(
		long companyId, long classNameId, long classPK,
		long parentJSONStoreEntryId, int index,
		Map<Long, List<JSONStoreEntry>> jsonStoreEntryMap,
		List<JSONStoreEntry> jsonStoreEntries) {

		JSONStoreEntry jsonStoreEntry = null;

		if ((jsonStoreEntries != null) && !jsonStoreEntries.isEmpty()) {
			jsonStoreEntry = jsonStoreEntries.get(0);

			for (int i = 1; i < jsonStoreEntries.size(); i++) {
				jsonStoreEntryPersistence.remove(jsonStoreEntries.get(i));

				_removeChildJSONStoreEntries(
					jsonStoreEntries.get(i), jsonStoreEntryMap);
			}
		}

		if (jsonStoreEntry == null) {
			jsonStoreEntry = jsonStoreEntryPersistence.create(
				counterLocalService.increment(JSONStoreEntry.class.getName()));

			jsonStoreEntry.setCompanyId(companyId);
			jsonStoreEntry.setClassNameId(classNameId);
			jsonStoreEntry.setClassPK(classPK);
			jsonStoreEntry.setParentJSONStoreEntryId(parentJSONStoreEntryId);
		}

		if (jsonStoreEntry.isNew() || (index != jsonStoreEntry.getIndex()) ||
			!Objects.equals(StringPool.BLANK, jsonStoreEntry.getKey()) ||
			(jsonStoreEntry.getType() != JSONStoreEntryConstants.TYPE_EMPTY) ||
			!Objects.equals(StringPool.BLANK, jsonStoreEntry.getValue())) {

			jsonStoreEntry.setIndex(index);
			jsonStoreEntry.setKey(StringPool.BLANK);
			jsonStoreEntry.setType(JSONStoreEntryConstants.TYPE_EMPTY);
			jsonStoreEntry.setValue(null);

			jsonStoreEntry = jsonStoreEntryPersistence.update(jsonStoreEntry);

			_removeChildJSONStoreEntries(jsonStoreEntry, jsonStoreEntryMap);
		}
	}

	private void _updateJSON(
		long companyId, long classNameId, long classPK,
		List<JSONStoreEntry> jsonStoreEntries, String json) {

		Map<Long, List<JSONStoreEntry>> jsonStoreEntryMap = new HashMap<>();

		for (JSONStoreEntry jsonStoreEntry : jsonStoreEntries) {
			List<JSONStoreEntry> values = jsonStoreEntryMap.computeIfAbsent(
				jsonStoreEntry.getParentJSONStoreEntryId(),
				key -> new ArrayList<>());

			values.add(jsonStoreEntry);
		}

		JSONDeserializer<?> jsonDeserializer =
			_jsonFactory.createJSONDeserializer();

		Object object = jsonDeserializer.deserialize(json);

		if (object instanceof List) {
			_updateJSONArray(
				companyId, classNameId, classPK, jsonStoreEntryMap,
				(List<?>)object,
				JSONStoreEntryConstants.DEFAULT_PARENT_JSON_ENTRY_ID);
		}
		else if (object instanceof Map) {
			_updateJSONObject(
				companyId, classNameId, classPK, jsonStoreEntryMap,
				(Map<String, Object>)object,
				JSONStoreEntryConstants.DEFAULT_PARENT_JSON_ENTRY_ID);
		}
		else {
			throw new IllegalArgumentException("Invalid JSON: " + json);
		}
	}

	private void _updateJSONArray(
		long companyId, long classNameId, long classPK,
		Map<Long, List<JSONStoreEntry>> jsonStoreEntryMap,
		List<?> jsonArrayList, long parentJSONStoreEntryId) {

		List<JSONStoreEntry> jsonStoreEntries = jsonStoreEntryMap.get(
			parentJSONStoreEntryId);

		int length = jsonArrayList.size();

		for (int i = 0; i < length; i++) {
			Object value = jsonArrayList.get(i);

			JSONStoreEntry jsonStoreEntry = null;

			if ((jsonStoreEntries != null) && (i < jsonStoreEntries.size())) {
				jsonStoreEntry = jsonStoreEntries.get(i);
			}

			_updateJSONStoreEntry(
				companyId, classNameId, classPK, parentJSONStoreEntryId, i,
				StringPool.BLANK, value, jsonStoreEntry, jsonStoreEntryMap);
		}

		if (length == 0) {
			_updateEmptyJSONStoreEntry(
				companyId, classNameId, classPK, parentJSONStoreEntryId, 0,
				jsonStoreEntryMap, jsonStoreEntries);
		}
		else if (jsonStoreEntries != null) {
			for (int i = length; i < jsonStoreEntries.size(); i++) {
				JSONStoreEntry jsonStoreEntry = jsonStoreEntries.get(i);

				jsonStoreEntryPersistence.remove(jsonStoreEntry);

				_removeChildJSONStoreEntries(jsonStoreEntry, jsonStoreEntryMap);
			}
		}
	}

	private void _updateJSONObject(
		long companyId, long classNameId, long classPK,
		Map<Long, List<JSONStoreEntry>> jsonStoreEntryMap,
		Map<String, Object> jsonObjectMap, long parentJSONStoreEntryId) {

		List<JSONStoreEntry> jsonStoreEntries = jsonStoreEntryMap.get(
			parentJSONStoreEntryId);

		Set<String> keySet = jsonObjectMap.keySet();

		for (Map.Entry<String, Object> entry : jsonObjectMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			JSONStoreEntry jsonStoreEntry = null;

			if (jsonStoreEntries != null) {
				for (JSONStoreEntry currentJSONStoreObjectMember :
						jsonStoreEntries) {

					if (key.equals(currentJSONStoreObjectMember.getKey())) {
						jsonStoreEntry = currentJSONStoreObjectMember;

						break;
					}
				}
			}

			_updateJSONStoreEntry(
				companyId, classNameId, classPK, parentJSONStoreEntryId,
				JSONStoreEntryConstants.JSON_OBJECT_INDEX, key, value,
				jsonStoreEntry, jsonStoreEntryMap);
		}

		if (keySet.isEmpty()) {
			_updateEmptyJSONStoreEntry(
				companyId, classNameId, classPK, parentJSONStoreEntryId,
				JSONStoreEntryConstants.JSON_OBJECT_INDEX, jsonStoreEntryMap,
				jsonStoreEntries);
		}
		else if (jsonStoreEntries != null) {
			for (JSONStoreEntry jsonStoreEntry : jsonStoreEntries) {
				if (!keySet.contains(jsonStoreEntry.getKey())) {
					jsonStoreEntryPersistence.remove(jsonStoreEntry);

					_removeChildJSONStoreEntries(
						jsonStoreEntry, jsonStoreEntryMap);
				}
			}
		}
	}

	private void _updateJSONStoreEntry(
		long companyId, long classNameId, long classPK,
		long parentJSONStoreEntryId, int index, String key, Object value,
		JSONStoreEntry jsonStoreEntry,
		Map<Long, List<JSONStoreEntry>> jsonStoreEntryMap) {

		if (jsonStoreEntry == null) {
			jsonStoreEntry = jsonStoreEntryPersistence.create(
				counterLocalService.increment(JSONStoreEntry.class.getName()));

			jsonStoreEntry.setCompanyId(companyId);
			jsonStoreEntry.setClassNameId(classNameId);
			jsonStoreEntry.setClassPK(classPK);
			jsonStoreEntry.setParentJSONStoreEntryId(parentJSONStoreEntryId);
			jsonStoreEntry.setIndex(index);
			jsonStoreEntry.setKey(key);
		}

		int type = JSONStoreEntryConstants.TYPE_NULL;

		if (value instanceof List) {
			type = JSONStoreEntryConstants.TYPE_ARRAY;

			_updateJSONArray(
				companyId, classNameId, classPK, jsonStoreEntryMap,
				(List<?>)value, jsonStoreEntry.getPrimaryKey());
		}
		else if (value instanceof Map) {
			type = JSONStoreEntryConstants.TYPE_OBJECT;

			_updateJSONObject(
				companyId, classNameId, classPK, jsonStoreEntryMap,
				(Map<String, Object>)value, jsonStoreEntry.getPrimaryKey());
		}
		else if (value != null) {
			Long valueLong = null;

			if (value instanceof Integer || value instanceof Long) {
				type = JSONStoreEntryConstants.TYPE_VALUE_LONG;

				Number number = (Number)value;

				valueLong = number.longValue();
			}
			else {
				type = JSONStoreEntryConstants.TYPE_VALUE_LONG_QUOTED;

				valueLong = _parseLong(value);
			}

			if (valueLong != null) {
				value = valueLong;
			}
			else if (value != null) {
				type = JSONStoreEntryConstants.TYPE_VALUE_STRING;

				JSONSerializer jsonSerializer =
					_jsonFactory.createJSONSerializer();

				value = jsonSerializer.serialize(value);
			}
		}

		if (jsonStoreEntry.isNew() || (index != jsonStoreEntry.getIndex()) ||
			!Objects.equals(key, jsonStoreEntry.getKey()) ||
			(type != jsonStoreEntry.getType()) ||
			(((type == JSONStoreEntryConstants.TYPE_VALUE_LONG) ||
			  (type == JSONStoreEntryConstants.TYPE_VALUE_LONG_QUOTED) ||
			  (type == JSONStoreEntryConstants.TYPE_VALUE_STRING)) &&
			 !Objects.equals(value, jsonStoreEntry.getValue()))) {

			jsonStoreEntry.setIndex(index);
			jsonStoreEntry.setKey(key);
			jsonStoreEntry.setType(type);
			jsonStoreEntry.setValue(value);

			jsonStoreEntry = jsonStoreEntryPersistence.update(jsonStoreEntry);

			if ((type != JSONStoreEntryConstants.TYPE_ARRAY) &&
				(type != JSONStoreEntryConstants.TYPE_OBJECT)) {

				_removeChildJSONStoreEntries(jsonStoreEntry, jsonStoreEntryMap);
			}
		}
	}

	@Reference
	private JSONFactory _jsonFactory;

}