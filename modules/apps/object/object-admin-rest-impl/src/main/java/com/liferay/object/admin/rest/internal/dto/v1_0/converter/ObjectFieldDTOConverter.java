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

package com.liferay.object.admin.rest.internal.dto.v1_0.converter;

import com.liferay.object.admin.rest.dto.v1_0.NextObjectState;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.dto.v1_0.ObjectFieldSetting;
import com.liferay.object.admin.rest.dto.v1_0.ObjectStateFlow;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectState;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "dto.class.name=com.liferay.object.model.ObjectField",
	service = {DTOConverter.class, ObjectFieldDTOConverter.class}
)
public class ObjectFieldDTOConverter
	implements DTOConverter<com.liferay.object.model.ObjectField, ObjectField> {

	@Override
	public String getContentType() {
		return ObjectField.class.getSimpleName();
	}

	@Override
	public ObjectField toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.object.model.ObjectField objectField)
		throws Exception {

		if (objectField == null) {
			return null;
		}

		ObjectField objectFieldDTO = new ObjectField() {
			{
				businessType = ObjectField.BusinessType.create(
					objectField.getBusinessType());
				DBType = ObjectField.DBType.create(objectField.getDBType());
				defaultValue = objectField.getDefaultValue();
				externalReferenceCode = objectField.getExternalReferenceCode();
				id = objectField.getObjectFieldId();
				indexed = objectField.getIndexed();
				indexedAsKeyword = objectField.getIndexedAsKeyword();
				indexedLanguageId = objectField.getIndexedLanguageId();
				label = LocalizedMapUtil.getLanguageIdMap(
					objectField.getLabelMap());
				listTypeDefinitionId = objectField.getListTypeDefinitionId();
				name = objectField.getName();
				objectFieldSettings = TransformUtil.transformToArray(
					objectField.getObjectFieldSettings(),
					ObjectFieldSettingUtil::toObjectFieldSetting,
					ObjectFieldSetting.class);
				objectStateFlow = _toObjectStateFlowDTO(
					_objectStateFlowLocalService.getObjectFieldObjectStateFlow(
						objectField.getObjectFieldId()));
				relationshipType = ObjectField.RelationshipType.create(
					objectField.getRelationshipType());
				required = objectField.isRequired();
				state = objectField.isState();
				system = objectField.getSystem();
				type = ObjectField.Type.create(objectField.getDBType());
			}
		};

		objectFieldDTO.setActions(dtoConverterContext.getActions());

		return objectFieldDTO;
	}

	private NextObjectState _toNextObjectStateDTO(ObjectState nextObjectState) {
		return new NextObjectState() {
			{
				listTypeEntryId = nextObjectState.getListTypeEntryId();
			}
		};
	}

	private com.liferay.object.admin.rest.dto.v1_0.ObjectState
		_toObjectStateDTO(ObjectState objectState) {

		return new com.liferay.object.admin.rest.dto.v1_0.ObjectState() {
			{
				id = objectState.getObjectStateId();
				listTypeEntryId = objectState.getListTypeEntryId();
				nextObjectStates = TransformUtil.transformToArray(
					_objectStateLocalService.getNextObjectStates(
						objectState.getObjectStateId()),
					nextObjectState -> _toNextObjectStateDTO(nextObjectState),
					NextObjectState.class);
			}
		};
	}

	private ObjectStateFlow _toObjectStateFlowDTO(
		com.liferay.object.model.ObjectStateFlow objectStateFlow) {

		if (objectStateFlow == null) {
			return null;
		}

		return new ObjectStateFlow() {
			{
				id = objectStateFlow.getObjectStateFlowId();
				objectStates = TransformUtil.transformToArray(
					_objectStateLocalService.getObjectStateFlowObjectStates(
						objectStateFlow.getObjectStateFlowId()),
					objectState -> _toObjectStateDTO(objectState),
					com.liferay.object.admin.rest.dto.v1_0.ObjectState.class);
			}
		};
	}

	@Reference
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

	@Reference
	private ObjectStateLocalService _objectStateLocalService;

}