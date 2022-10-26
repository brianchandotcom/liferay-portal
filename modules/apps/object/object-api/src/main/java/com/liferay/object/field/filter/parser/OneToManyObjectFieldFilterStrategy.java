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

package com.liferay.object.field.filter.parser;

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.object.exception.ObjectViewFilterColumnException;
import com.liferay.object.field.frontend.data.set.filter.OneToManyAutocompleteFDSFilter;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataTracker;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class OneToManyObjectFieldFilterStrategy
	extends BaseObjectFieldFilterStrategy {

	public OneToManyObjectFieldFilterStrategy(
		Locale locale, ObjectDefinition objectDefinition1,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectField objectField,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		ObjectViewFilterColumn objectViewFilterColumn,
		PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry,
		SystemObjectDefinitionMetadataTracker
			systemObjectDefinitionMetadataTracker) {

		super(locale, objectViewFilterColumn);

		_objectDefinition1 = objectDefinition1;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectField = objectField;
		_objectFieldLocalService = objectFieldLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_persistedModelLocalServiceRegistry =
			persistedModelLocalServiceRegistry;
		_systemObjectDefinitionMetadataTracker =
			systemObjectDefinitionMetadataTracker;
	}

	@Override
	public FDSFilter getFDSFilter() throws PortalException {
		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				fetchObjectRelationshipByObjectFieldId2(
					_objectField.getObjectFieldId());

		ObjectDefinition objectDefinition1 =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());

		ObjectField titleObjectField = null;

		if (Validator.isNull(objectDefinition1.getTitleObjectFieldId())) {
			titleObjectField = _objectFieldLocalService.getObjectField(
				objectDefinition1.getObjectDefinitionId(), "id");
		}
		else {
			titleObjectField = _objectFieldLocalService.getObjectField(
				objectDefinition1.getTitleObjectFieldId());
		}

		String restContextPath = null;

		if (objectDefinition1.isSystem()) {
			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
				_systemObjectDefinitionMetadataTracker.
					getSystemObjectDefinitionMetadata(
						objectDefinition1.getName());

			restContextPath =
				"/o/" + systemObjectDefinitionMetadata.getRESTContextPath();
		}
		else {
			restContextPath = "/o" + objectDefinition1.getRESTContextPath();
		}

		return new OneToManyAutocompleteFDSFilter(
			parse(), restContextPath, titleObjectField.getLabel(locale),
			_objectField.getName(), titleObjectField.getName());
	}

	@Override
	public List<Map<String, Object>> getItemsValues() throws PortalException {
		List<Map<String, Object>> itemsValues = new ArrayList<>();

		JSONArray jsonArray = getJSONArray();

		if (_objectDefinition1.isSystem()) {
			for (int i = 0; i < jsonArray.length(); i++) {
				itemsValues.add(
					HashMapBuilder.<String, Object>put(
						"label",
						_objectEntryLocalService.getTitleValue(
							_objectDefinition1.getObjectDefinitionId(),
							GetterUtil.getLong(jsonArray.get(i)))
					).put(
						"value", jsonArray.getLong(i)
					).build());
			}

			return itemsValues;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
				(String)jsonArray.get(i),
				_objectDefinition1.getObjectDefinitionId());

			if (objectEntry == null) {
				continue;
			}

			itemsValues.add(
				HashMapBuilder.<String, Object>put(
					"label", objectEntry.getTitleValue()
				).put(
					"value", objectEntry.getObjectEntryId()
				).build());
		}

		return itemsValues;
	}

	@Override
	public String toValueSummary() throws PortalException {
		return StringUtil.merge(
			ListUtil.toList(
				getItemsValues(), itemValue -> itemValue.get("label")),
			StringPool.COMMA_AND_SPACE);
	}

	@Override
	public void validate() throws PortalException {
		super.validate();

		JSONArray jsonArray = getJSONArray();

		if (_objectDefinition1.isSystem()) {
			PersistedModelLocalService persistedModelLocalService =
				_persistedModelLocalServiceRegistry.
					getPersistedModelLocalService(
						_objectDefinition1.getClassName());

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
				if (Validator.isNull(
						_objectEntryLocalService.fetchObjectEntry(
							(String)jsonArray.get(i),
							_objectDefinition1.getObjectDefinitionId()))) {

					throw new ObjectViewFilterColumnException(
						StringBundler.concat(
							"No ", _objectDefinition1.getShortName(),
							" exists with the external reference code ",
							(String)jsonArray.get(i)));
				}
			}
		}
	}

	private final ObjectDefinition _objectDefinition1;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectField _objectField;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;
	private final SystemObjectDefinitionMetadataTracker
		_systemObjectDefinitionMetadataTracker;

}