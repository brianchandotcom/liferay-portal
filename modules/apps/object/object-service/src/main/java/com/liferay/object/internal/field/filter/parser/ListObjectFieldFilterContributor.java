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

package com.liferay.object.internal.field.filter.parser;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectViewFilterColumnConstants;
import com.liferay.object.exception.ObjectViewFilterColumnException;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContributor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = {
		"object.field.filter.type.key=" + ObjectViewFilterColumnConstants.FILTER_TYPE_EXCLUDES,
		"object.field.filter.type.key=" + ObjectViewFilterColumnConstants.FILTER_TYPE_INCLUDES
	},
	service = ObjectFieldFilterContributor.class
)
public class ListObjectFieldFilterContributor
	implements ObjectFieldFilterContributor {

	@Override
	public Map<String, Object> parse(
			long listTypeDefinitionId, long objectDefinitionId, Locale locale,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws PortalException {

		return HashMapBuilder.<String, Object>put(
			"exclude",
			ObjectViewFilterColumnConstants.FILTER_TYPE_EXCLUDES.equals(
				objectViewFilterColumn.getFilterType())
		).put(
			"itemsValues",
			_toItemsValues(
				objectDefinitionId, listTypeDefinitionId, locale,
				objectViewFilterColumn)
		).build();
	}

	@Override
	public String toValueSummary(
			Locale locale, ObjectField objectField,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws PortalException {

		if (Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

			Map<String, Object> preloadedData = parse(
				objectField.getListTypeDefinitionId(),
				objectField.getObjectDefinitionId(), locale,
				objectViewFilterColumn);

			return StringUtil.merge(
				ListUtil.toList(
					(List<Map<String, String>>)preloadedData.get("itemsValues"),
					itemValue -> itemValue.get("label")),
				StringPool.COMMA_AND_SPACE);
		}

		Map<String, Object> preloadedData = parse(
			0L, objectField.getObjectDefinitionId(), locale,
			objectViewFilterColumn);

		if (Objects.equals(
				objectViewFilterColumn.getObjectFieldName(), Field.STATUS)) {

			return StringUtil.merge(
				ListUtil.toList(
					(List<Integer>)preloadedData.get("itemsValues"),
					itemValue -> _language.get(
						locale, WorkflowConstants.getStatusLabel(itemValue))),
				StringPool.COMMA_AND_SPACE);
		}

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				fetchObjectRelationshipByObjectFieldId2(
					objectField.getObjectFieldId());

		return StringUtil.merge(
			ListUtil.toList(
				(List<Map<String, Object>>)preloadedData.get("itemsValues"),
				itemValue -> {
					try {
						return _objectEntryLocalService.getTitleValue(
							GetterUtil.getLong(itemValue.get("value")),
							objectRelationship.getObjectDefinitionId1());
					}
					catch (PortalException portalException) {
						throw new RuntimeException(portalException);
					}
				}),
			StringPool.COMMA_AND_SPACE);
	}

	@Override
	public void validate(
			long objectDefinitionId,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			objectViewFilterColumn.getJSON());

		JSONArray jsonArray = jsonObject.getJSONArray(
			objectViewFilterColumn.getFilterType());

		if (jsonArray == null) {
			throw new ObjectViewFilterColumnException(
				"JSON array is null for filter type " +
					objectViewFilterColumn.getFilterType());
		}

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectDefinitionId, objectViewFilterColumn.getObjectFieldName());

		if (Objects.equals(objectField.getName(), Field.STATUS)) {
			try {
				_toIntegerList(jsonArray);
			}
			catch (Exception exception) {
				throw new ObjectViewFilterColumnException(
					"JSON array is invalid for filter type " +
						objectViewFilterColumn.getFilterType(),
					exception);
			}
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

			_validate(jsonArray, objectField);
		}
	}

	private List<Integer> _toIntegerList(JSONArray jsonArray) {
		List<Integer> statuses = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			statuses.add((Integer)jsonArray.get(i));
		}

		return statuses;
	}

	private Object _toItemsValues(
			long objectDefinitionId, long listTypeDefinitionId, Locale locale,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			objectViewFilterColumn.getJSON());

		JSONArray jsonArray = jsonObject.getJSONArray(
			objectViewFilterColumn.getFilterType());

		if (Objects.equals(
				objectViewFilterColumn.getObjectFieldName(), Field.STATUS)) {

			return _toIntegerList(jsonArray);
		}

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectDefinitionId, objectViewFilterColumn.getObjectFieldName());

		if (Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

			List<Map<String, String>> itemsValues = new ArrayList<>();

			for (int i = 0; i < jsonArray.length(); i++) {
				ListTypeEntry listTypeEntry =
					_listTypeEntryLocalService.fetchListTypeEntry(
						listTypeDefinitionId, jsonArray.getString(i));

				itemsValues.add(
					HashMapBuilder.put(
						"label", listTypeEntry.getName(locale)
					).put(
						"value", jsonArray.getString(i)
					).build());
			}

			return itemsValues;
		}

		List<Map<String, Object>> itemsValues = new ArrayList<>();

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				fetchObjectRelationshipByObjectFieldId2(
					objectField.getObjectFieldId());

		ObjectDefinition objectDefinition1 =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());

		if (objectDefinition1.isSystem()) {
			for (int i = 0; i < jsonArray.length(); i++) {
				itemsValues.add(
					HashMapBuilder.<String, Object>put(
						"label",
						_objectEntryLocalService.getTitleValue(
							GetterUtil.getLong(jsonArray.get(i)),
							objectDefinition1.getObjectDefinitionId())
					).put(
						"value", jsonArray.getLong(i)
					).build());
			}

			return itemsValues;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			ObjectEntry objectEntry =
				_objectEntryLocalService.getObjectEntryByERC_ODI(
					(String)jsonArray.get(i),
					objectDefinition1.getObjectDefinitionId());

			itemsValues.add(
				HashMapBuilder.<String, Object>put(
					"label", objectEntry.getTitleValue()
				).put(
					"value", objectEntry.getObjectEntryId()
				).build());
		}

		return itemsValues;
	}

	private void _validate(JSONArray jsonArray, ObjectField objectField)
		throws PortalException {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				fetchObjectRelationshipByObjectFieldId2(
					objectField.getObjectFieldId());

		ObjectDefinition objectDefinition1 =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());

		if (objectDefinition1.isSystem()) {
			PersistedModelLocalService persistedModelLocalService =
				_persistedModelLocalServiceRegistry.
					getPersistedModelLocalService(
						objectDefinition1.getClassName());

			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					persistedModelLocalService.getPersistedModel(
						GetterUtil.getLong(jsonArray.get(i)));
				}
				catch (NoSuchModelException noSuchModelException) {
					throw new ObjectViewFilterColumnException(
						noSuchModelException.getMessage());
				}
			}
		}
		else {
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					_objectEntryLocalService.getObjectEntryByERC_ODI(
						(String)jsonArray.get(i),
						objectRelationship.getObjectDefinitionId1());
				}
				catch (PortalException portalException) {
					throw new ObjectViewFilterColumnException(
						portalException.getMessage());
				}
			}
		}
	}

	@Reference
	private Language _language;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;

}