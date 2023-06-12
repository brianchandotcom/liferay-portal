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

import com.liferay.object.admin.rest.dto.v1_0.ObjectRelationship;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "dto.class.name=com.liferay.object.model.ObjectRelationship",
	service = DTOConverter.class
)
public class ObjectRelationshipDTOConverter
	implements DTOConverter
		<com.liferay.object.model.ObjectRelationship, ObjectRelationship> {

	@Override
	public String getContentType() {
		return ObjectRelationship.class.getSimpleName();
	}

	@Override
	public ObjectRelationship toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.object.model.ObjectRelationship
				serviceBuilderObjectRelationship)
		throws Exception {

		if (serviceBuilderObjectRelationship == null) {
			return null;
		}

		ObjectDefinition objectDefinition1 =
			_objectDefinitionLocalService.getObjectDefinition(
				serviceBuilderObjectRelationship.getObjectDefinitionId1());

		ObjectDefinition objectDefinition2 =
			_objectDefinitionLocalService.getObjectDefinition(
				serviceBuilderObjectRelationship.getObjectDefinitionId2());

		return new ObjectRelationship() {
			{
				actions = dtoConverterContext.getActions();
				deletionType = ObjectRelationship.DeletionType.create(
					serviceBuilderObjectRelationship.getDeletionType());
				id = serviceBuilderObjectRelationship.getObjectRelationshipId();
				label = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderObjectRelationship.getLabelMap());
				name = serviceBuilderObjectRelationship.getName();
				objectDefinitionExternalReferenceCode1 =
					objectDefinition1.getExternalReferenceCode();
				objectDefinitionExternalReferenceCode2 =
					objectDefinition2.getExternalReferenceCode();
				objectDefinitionId1 =
					serviceBuilderObjectRelationship.getObjectDefinitionId1();
				objectDefinitionId2 =
					serviceBuilderObjectRelationship.getObjectDefinitionId2();
				objectDefinitionModifiable2 = objectDefinition2.isModifiable();
				objectDefinitionName2 = objectDefinition2.getShortName();
				objectDefinitionSystem2 = objectDefinition2.isSystem();
				parameterObjectFieldId =
					serviceBuilderObjectRelationship.
						getParameterObjectFieldId();
				reverse = serviceBuilderObjectRelationship.isReverse();
				type = ObjectRelationship.Type.create(
					serviceBuilderObjectRelationship.getType());

				setParameterObjectFieldName(
					() -> {
						if (Validator.isNull(
								serviceBuilderObjectRelationship.
									getParameterObjectFieldId())) {

							return StringPool.BLANK;
						}

						ObjectField objectField =
							_objectFieldLocalService.getObjectField(
								serviceBuilderObjectRelationship.
									getParameterObjectFieldId());

						return objectField.getName();
					});
			}
		};
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}