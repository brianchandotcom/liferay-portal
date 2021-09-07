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
import com.liferay.object.admin.rest.dto.v1_0.ObjectRelationship;
import com.liferay.object.admin.rest.resource.v1_0.ObjectRelationshipResource;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 * @author Marco Leo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-relationship.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, ObjectRelationshipResource.class}
)
public class ObjectRelationshipResourceImpl
	extends BaseObjectRelationshipResourceImpl implements NestedFieldSupport {

	@NestedField(
		parentClass = ObjectDefinition.class, value = "objectRelationships"
	)
	@Override
	public Page<ObjectRelationship> getObjectDefinitionObjectRelationshipsPage(
			Long objectDefinitionId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_objectRelationshipLocalService.getObjectRelationships(
					objectDefinitionId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toObjectRelationship));
	}

	@Override
	public ObjectRelationship postObjectDefinitionObjectRelationship(
			Long objectDefinitionId, ObjectRelationship objectRelationship)
		throws Exception {

		return _toObjectRelationship(
			_objectRelationshipLocalService.addObjectRelationship(
				contextUser.getUserId(),
				LocalizedMapUtil.getLocalizedMap(objectRelationship.getLabel()),
				objectRelationship.getName(), objectDefinitionId,
				objectRelationship.getObjectDefinitionId2(),
				objectRelationship.getType()));
	}

	private ObjectRelationship _toObjectRelationship(
		com.liferay.object.model.ObjectRelationship objectRelationship) {

		return new ObjectRelationship() {
			{
				id = objectRelationship.getObjectRelationshipId();
				label = LocalizedMapUtil.getI18nMap(
					objectRelationship.getLabelMap());
				name = objectRelationship.getName();
				objectDefinitionId1 =
					objectRelationship.getObjectDefinitionId1();
				objectDefinitionId2 =
					objectRelationship.getObjectDefinitionId2();
				type = objectRelationship.getType();
			}
		};
	}

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}