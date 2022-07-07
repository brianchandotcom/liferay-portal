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
import com.liferay.object.admin.rest.dto.v1_0.ObjectView;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "dto.class.name=com.liferay.object.model.ObjectRelationship",
	service = {DTOConverter.class, ObjectRelationshipDTOConverter.class}
)
public class ObjectRelationshipDTOConverter
	implements DTOConverter
		<com.liferay.object.model.ObjectRelationship, ObjectRelationship> {

	@Override
	public String getContentType() {
		return ObjectView.class.getSimpleName();
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

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				serviceBuilderObjectRelationship.getObjectDefinitionId2());

		ObjectRelationship objectRelationship = new ObjectRelationship() {
			{
				deletionType = ObjectRelationship.DeletionType.create(
					serviceBuilderObjectRelationship.getDeletionType());
				id = serviceBuilderObjectRelationship.getObjectRelationshipId();
				label = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderObjectRelationship.getLabelMap());
				name = serviceBuilderObjectRelationship.getName();
				objectDefinitionExternalReferenceCode2 =
					objectDefinition.getExternalReferenceCode();
				objectDefinitionId1 =
					serviceBuilderObjectRelationship.getObjectDefinitionId1();
				objectDefinitionId2 =
					serviceBuilderObjectRelationship.getObjectDefinitionId2();
				objectDefinitionName2 = objectDefinition.getShortName();
				parameterObjectFieldId =
					serviceBuilderObjectRelationship.
						getParameterObjectFieldId();
				reverse = serviceBuilderObjectRelationship.isReverse();
				type = ObjectRelationship.Type.create(
					serviceBuilderObjectRelationship.getType());
			}
		};

		objectRelationship.setActions(dtoConverterContext.getActions());

		return objectRelationship;
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}