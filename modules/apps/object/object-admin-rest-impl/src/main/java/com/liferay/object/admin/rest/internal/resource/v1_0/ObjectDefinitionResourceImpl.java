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
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;

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

		_objectDefinitionService.deleteObjectDefinition(objectDefinitionId);
	}

	@Override
	public ObjectDefinition getObjectDefinition(Long objectDefinitionId)
		throws Exception {

		return _toObjectDefinition(
			_objectDefinitionService.getObjectDefinition(objectDefinitionId));
	}

	@Override
	public Page<ObjectDefinition> getObjectDefinitionsPage(
			Pagination pagination)
		throws PortalException {

		return Page.of(
			transform(
				_objectDefinitionService.getObjectDefinitions(
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toObjectDefinition),
			pagination, _objectDefinitionService.getObjectDefinitionsCount());
	}

	@Override
	public ObjectDefinition postObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		com.liferay.object.model.ObjectDefinition
			serviceBuilderObjectDefinition =
				_objectDefinitionService.addCustomObjectDefinition(
					contextUser.getUserId(), objectDefinition.getName(),
					transformToList(
						objectDefinition.getObjectFields(),
						this::_toObjectField));

		return _toObjectDefinition(
			_objectDefinitionService.publishCustomObjectDefinition(
				serviceBuilderObjectDefinition.getUserId(),
				serviceBuilderObjectDefinition.getObjectDefinitionId()));
	}

	private static ObjectField _toObjectField(
		com.liferay.object.model.ObjectField objectField) {

		return new ObjectField() {
			{
				id = objectField.getObjectFieldId();
				indexed = objectField.getIndexed();
				indexedAsKeyword = objectField.getIndexedAsKeyword();
				indexedLanguageId = objectField.getIndexedLanguageId();
				name = objectField.getName();
				required = objectField.isRequired();
				type = objectField.getType();
			}
		};
	}

	private ObjectDefinition _toObjectDefinition(
		com.liferay.object.model.ObjectDefinition objectDefinition) {

		HashMapBuilder.HashMapWrapper<String, Map<String, String>> mapBuilder =
			HashMapBuilder.<String, Map<String, String>>put(
				"get",
				addAction(
					ActionKeys.VIEW, objectDefinition.getObjectDefinitionId(),
					"getObjectDefinition",
					_objectDefinitionModelResourcePermission)
			).put(
				"update",
				addAction(
					ActionKeys.UPDATE, objectDefinition.getObjectDefinitionId(),
					"postObjectDefinition",
					_objectDefinitionModelResourcePermission)
			);

		if (!objectDefinition.isSystem()) {
			mapBuilder.put(
				"delete",
				addAction(
					ActionKeys.DELETE, objectDefinition.getObjectDefinitionId(),
					"deleteObjectDefinition",
					_objectDefinitionModelResourcePermission));
		}

		return new ObjectDefinition() {
			{
				actions = mapBuilder.build();
				dateCreated = objectDefinition.getCreateDate();
				dateModified = objectDefinition.getModifiedDate();
				id = objectDefinition.getObjectDefinitionId();
				name = objectDefinition.getShortName();
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

		com.liferay.object.model.ObjectField serviceBuilderObjectField =
			_objectFieldLocalService.createObjectField(0L);

		serviceBuilderObjectField.setIndexed(
			GetterUtil.getBoolean(objectField.getIndexed()));
		serviceBuilderObjectField.setIndexedAsKeyword(
			GetterUtil.getBoolean(objectField.getIndexedAsKeyword()));
		serviceBuilderObjectField.setIndexedLanguageId(
			objectField.getIndexedLanguageId());
		serviceBuilderObjectField.setName(objectField.getName());
		serviceBuilderObjectField.setRequired(
			GetterUtil.getBoolean(objectField.getRequired()));
		serviceBuilderObjectField.setType(objectField.getType());

		return serviceBuilderObjectField;
	}

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectDefinition)"
	)
	private ModelResourcePermission<com.liferay.object.model.ObjectDefinition>
		_objectDefinitionModelResourcePermission;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}