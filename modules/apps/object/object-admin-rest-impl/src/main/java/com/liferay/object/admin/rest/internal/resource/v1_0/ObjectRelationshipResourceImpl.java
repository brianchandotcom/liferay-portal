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
import com.liferay.object.admin.rest.internal.odata.entity.v1_0.ObjectRelationshipEntityModel;
import com.liferay.object.admin.rest.resource.v1_0.ObjectRelationshipResource;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;

import javax.ws.rs.core.MultivaluedMap;

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
	extends BaseObjectRelationshipResourceImpl
	implements EntityModelResource, NestedFieldSupport {

	@Override
	public void deleteObjectRelationship(Long objectRelationshipId)
		throws Exception {

		_objectRelationshipService.deleteObjectRelationship(
			objectRelationshipId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@NestedField(
		parentClass = ObjectDefinition.class, value = "objectRelationships"
	)
	@Override
	public Page<ObjectRelationship> getObjectDefinitionObjectRelationshipsPage(
			Long objectDefinitionId, String search, Filter filter,
			Pagination pagination)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
			},
			filter, com.liferay.object.model.ObjectRelationship.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(Field.NAME, search);
				searchContext.setAttribute(
					"objectDefinitionId", objectDefinitionId);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			null,
			document -> _toObjectRelationship(
				_objectRelationshipService.getObjectRelationship(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public ObjectRelationship getObjectRelationship(Long objectRelationshipId)
		throws Exception {

		return _toObjectRelationship(
			_objectRelationshipService.getObjectRelationship(
				objectRelationshipId));
	}

	@Override
	public ObjectRelationship postObjectDefinitionObjectRelationship(
			Long objectDefinitionId, ObjectRelationship objectRelationship)
		throws Exception {

		return _toObjectRelationship(
			_objectRelationshipService.addObjectRelationship(
				objectDefinitionId, objectRelationship.getObjectDefinitionId2(),
				objectRelationship.getDeletionTypeAsString(),
				LocalizedMapUtil.getLocalizedMap(objectRelationship.getLabel()),
				objectRelationship.getName(),
				objectRelationship.getTypeAsString()));
	}

	@Override
	public ObjectRelationship putObjectRelationship(
			Long objectRelationshipId, ObjectRelationship objectRelationship)
		throws Exception {

		return _toObjectRelationship(
			_objectRelationshipService.updateObjectRelationship(
				objectRelationshipId,
				objectRelationship.getDeletionTypeAsString(),
				LocalizedMapUtil.getLocalizedMap(
					objectRelationship.getLabel())));
	}

	private ObjectRelationship _toObjectRelationship(
			com.liferay.object.model.ObjectRelationship objectRelationship)
		throws Exception {

		com.liferay.object.model.ObjectDefinition objectDefinition1 =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());
		com.liferay.object.model.ObjectDefinition objectDefinition2 =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId2());

		return new ObjectRelationship() {
			{
				actions = HashMapBuilder.put(
					"delete",
					() -> {
						if (objectDefinition1.isApproved() ||
							objectRelationship.isReverse()) {

							return null;
						}

						return addAction(
							ActionKeys.DELETE, "deleteObjectRelationship",
							com.liferay.object.model.ObjectDefinition.class.
								getName(),
							objectRelationship.getObjectDefinitionId1());
					}
				).build();
				deletionType = ObjectRelationship.DeletionType.create(
					objectRelationship.getDeletionType());
				id = objectRelationship.getObjectRelationshipId();
				label = LocalizedMapUtil.getLanguageIdMap(
					objectRelationship.getLabelMap());
				name = objectRelationship.getName();
				objectDefinitionId1 =
					objectRelationship.getObjectDefinitionId1();
				objectDefinitionId2 =
					objectRelationship.getObjectDefinitionId2();
				objectDefinitionName2 = objectDefinition2.getShortName();
				type = ObjectRelationship.Type.create(
					objectRelationship.getType());
			}
		};
	}

	private static final EntityModel _entityModel =
		new ObjectRelationshipEntityModel();

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectRelationshipService _objectRelationshipService;

}