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
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectFieldUtil;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFieldResource;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

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
	public Page<ObjectField> getObjectDefinitionObjectFieldsPage(
			Long objectDefinitionId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_objectFieldLocalService.getObjectFields(objectDefinitionId),
				this::_toObjectField),
			pagination,
			_objectFieldLocalService.getObjectFieldsCount(objectDefinitionId));
	}

	@Override
	public ObjectField postObjectField(
			Long objectDefinitionId, ObjectField objectField)
		throws Exception {

		return ObjectFieldUtil.toObjectField(
			_objectFieldLocalService.addObjectField(
				contextUser.getUserId(), objectDefinitionId,
				objectField.getName(), objectField.getIndexed(),
				objectField.getIndexedAsKeyword(),
				objectField.getIndexedLanguageId(), objectField.getName(),
				objectField.getRequired(), objectField.getType()));
	}

	private ObjectField _toObjectField(
			com.liferay.object.model.ObjectField objectField)
		throws PortalException {

		return new ObjectField() {
			{
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
	private ObjectFieldLocalService _objectFieldLocalService;

}