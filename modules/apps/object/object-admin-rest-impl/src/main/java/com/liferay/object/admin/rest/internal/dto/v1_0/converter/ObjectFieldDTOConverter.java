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
			com.liferay.object.model.ObjectField serviceBuilderObjectField)
		throws Exception {

		if (serviceBuilderObjectField == null) {
			return null;
		}

		ObjectField objectField = new ObjectField() {
			{
				businessType = ObjectField.BusinessType.create(
					serviceBuilderObjectField.getBusinessType());
				DBType = ObjectField.DBType.create(
					serviceBuilderObjectField.getDBType());
				defaultValue = serviceBuilderObjectField.getDefaultValue();
				externalReferenceCode =
					serviceBuilderObjectField.getExternalReferenceCode();
				id = serviceBuilderObjectField.getObjectFieldId();
				indexed = serviceBuilderObjectField.getIndexed();
				indexedAsKeyword =
					serviceBuilderObjectField.getIndexedAsKeyword();
				indexedLanguageId =
					serviceBuilderObjectField.getIndexedLanguageId();
				label = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderObjectField.getLabelMap());
				listTypeDefinitionId =
					serviceBuilderObjectField.getListTypeDefinitionId();
				name = serviceBuilderObjectField.getName();
				objectFieldSettings = TransformUtil.transformToArray(
					serviceBuilderObjectField.getObjectFieldSettings(),
					ObjectFieldSettingUtil::toObjectFieldSetting,
					ObjectFieldSetting.class);
				objectStateFlow = _toObjectStateFlowDTO(
					_objectStateFlowLocalService.getObjectFieldObjectStateFlow(
						serviceBuilderObjectField.getObjectFieldId()));
				relationshipType = ObjectField.RelationshipType.create(
					serviceBuilderObjectField.getRelationshipType());
				required = serviceBuilderObjectField.isRequired();
				state = serviceBuilderObjectField.isState();
				system = serviceBuilderObjectField.getSystem();
				type = ObjectField.Type.create(
					serviceBuilderObjectField.getDBType());
			}
		};

		objectField.setActions(dtoConverterContext.getActions());

		return objectField;
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