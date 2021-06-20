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

package com.liferay.object.admin.rest.internal.resource.v1_0;

import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = ObjectDefinitionResource.class
)
public class ObjectDefinitionResourceImpl
	extends BaseObjectDefinitionResourceImpl {

	@Override
	public void deleteObjectDefinition(Long objectDefinitionId)
		throws Exception {

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinitionId);
	}

	@Override
	public ObjectDefinition getObjectDefinition(Long objectDefinitionId)
		throws Exception {

		return _toObjectDefinition(
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId));
	}

	@Override
	public Page<ObjectDefinition> getObjectDefinitionsPage(
		Pagination pagination) {

		return Page.of(
			transform(
				_objectDefinitionLocalService.getObjectDefinitions(
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toObjectDefinition),
			pagination,
			_objectDefinitionLocalService.getObjectDefinitionsCount());
	}

	@Override
	public ObjectDefinition postObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		return _toObjectDefinition(
			_objectDefinitionLocalService.addObjectDefinition(
				contextUser.getUserId(), objectDefinition.getName(),
				transformToList(
					objectDefinition.getObjectFields(), this::_toObjectField)));
	}

	private static ObjectField _toObjectField(
		com.liferay.object.model.ObjectField objectField) {

		return new ObjectField() {
			{
				dateCreated = objectField.getCreateDate();
				dateModified = objectField.getModifiedDate();
				id = objectField.getObjectFieldId();
				indexed = objectField.getIndexed();
				indexedAsKeyword = objectField.getIndexedAsKeyword();
				indexedLanguageId = objectField.getIndexedLanguageId();
				name = objectField.getName();
				type = objectField.getType();
			}
		};
	}

	private ObjectDefinition _toObjectDefinition(
		com.liferay.object.model.ObjectDefinition objectDefinition) {

		return new ObjectDefinition() {
			{
				dateCreated = objectDefinition.getCreateDate();
				dateModified = objectDefinition.getModifiedDate();
				id = objectDefinition.getObjectDefinitionId();
				name = objectDefinition.getName();
				objectFields = transformToArray(
					_objectFieldLocalService.getObjectFields(
						objectDefinition.getObjectDefinitionId()),
					ObjectDefinitionResourceImpl::_toObjectField,
					ObjectField.class);
			}
		};
	}

	private com.liferay.object.model.ObjectField _toObjectField(
		ObjectField objectField) {

		com.liferay.object.model.ObjectField existingObjectField =
			_objectFieldLocalService.createObjectField(0L);

		existingObjectField.setIndexed(
			GetterUtil.getBoolean(objectField.getIndexed()));
		existingObjectField.setIndexedAsKeyword(
			GetterUtil.getBoolean(objectField.getIndexedAsKeyword()));
		existingObjectField.setIndexedLanguageId(
			objectField.getIndexedLanguageId());
		existingObjectField.setName(objectField.getName());
		existingObjectField.setType(objectField.getType());

		return existingObjectField;
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}