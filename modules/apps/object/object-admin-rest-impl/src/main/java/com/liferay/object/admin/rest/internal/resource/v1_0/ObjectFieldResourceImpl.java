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
import com.liferay.object.admin.rest.resource.v1_0.ObjectFieldResource;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.fields.NestedField;
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
	properties = "OSGI-INF/liferay/rest/v1_0/object-field.properties",
	scope = ServiceScope.PROTOTYPE, service = ObjectFieldResource.class
)
public class ObjectFieldResourceImpl extends BaseObjectFieldResourceImpl {

	@NestedField(parentClass = ObjectDefinition.class, value = "objectFields")
	@Override
	public Page<ObjectField> getObjectDefinitionFieldsPage(
			Long objectDefinitionId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_objectFieldLocalService.getObjectFields(objectDefinitionId),
				this::_toObjectField),
			pagination, _objectFieldLocalService.getObjectFieldsCount());
	}

	protected Map<String, String> addAction(String actionKey) {

		//TODO Replace this with proper logic using permission

		return HashMapBuilder.put(
			actionKey, ""
		).build();
	}

	private ObjectField _toObjectField(
			com.liferay.object.model.ObjectField objectField)
		throws PortalException {

		HashMapBuilder.HashMapWrapper<String, Map<String, String>> mapBuilder =
			HashMapBuilder.<String, Map<String, String>>put(
				"get", addAction(ActionKeys.VIEW)
			).put(
				"update", addAction(ActionKeys.UPDATE)
			);

		com.liferay.object.model.ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectField.getObjectDefinitionId());

		if (!objectDefinition.isSystem()) {
			mapBuilder.put("delete", addAction(ActionKeys.DELETE));
		}

		return new ObjectField() {
			{
				actions = mapBuilder.build();
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

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}