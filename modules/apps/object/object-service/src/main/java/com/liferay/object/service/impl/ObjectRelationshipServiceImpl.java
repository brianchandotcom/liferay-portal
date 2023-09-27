/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.impl;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.base.ObjectRelationshipServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=object",
		"json.web.service.context.path=ObjectRelationship"
	},
	service = AopService.class
)
public class ObjectRelationshipServiceImpl
	extends ObjectRelationshipServiceBaseImpl {

	@Override
	public ObjectRelationship addObjectRelationship(
			long objectDefinitionId1, long objectDefinitionId2,
			long parameterObjectFieldId, String deletionType,
			Map<Locale, String> labelMap, String name, String type)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId1);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinition.getObjectDefinitionId(),
			ActionKeys.UPDATE);

		return objectRelationshipLocalService.addObjectRelationship(
			getUserId(), objectDefinitionId1, objectDefinitionId2,
			parameterObjectFieldId, deletionType, labelMap, name, type);
	}

	@Override
	public void addObjectRelationshipMappingTableValues(
			long objectRelationshipId, long primaryKey1, long primaryKey2,
			ServiceContext serviceContext)
		throws PortalException {

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.findByPrimaryKey(
				objectRelationshipId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectRelationship.getObjectDefinitionId1(),
			ActionKeys.UPDATE);

		objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			getUserId(), objectRelationshipId, primaryKey1, primaryKey2,
			serviceContext);
	}

	@Override
	public ObjectRelationship deleteObjectRelationship(
			long objectRelationshipId)
		throws PortalException {

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.findByPrimaryKey(
				objectRelationshipId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectRelationship.getObjectDefinitionId1(),
			ActionKeys.UPDATE);

		return objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationshipId);
	}

	@Override
	public ObjectRelationship getObjectRelationship(long objectRelationshipId)
		throws PortalException {

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.findByPrimaryKey(
				objectRelationshipId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectRelationship.getObjectDefinitionId1(),
			ActionKeys.VIEW);

		return objectRelationshipLocalService.getObjectRelationship(
			objectRelationshipId);
	}

	@Override
	public ObjectRelationship getObjectRelationship(
			long objectDefinitionId1, String name)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId1, ActionKeys.VIEW);

		return objectRelationshipLocalService.getObjectRelationship(
			objectDefinitionId1, name);
	}

	@Override
	public List<ObjectRelationship> getObjectRelationships(
			long objectDefinitionId1, int start, int end)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId1);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinition.getObjectDefinitionId(),
			ActionKeys.VIEW);

		return objectRelationshipLocalService.getObjectRelationships(
			objectDefinitionId1, start, end);
	}

	@Override
	public ObjectRelationship updateObjectRelationship(
			long objectRelationshipId, long parameterObjectFieldId,
			String deletionType, boolean edge, Map<Locale, String> labelMap)
		throws PortalException {

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.findByPrimaryKey(
				objectRelationshipId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectRelationship.getObjectDefinitionId1(),
			ActionKeys.UPDATE);

		return objectRelationshipLocalService.updateObjectRelationship(
			objectRelationshipId, parameterObjectFieldId, deletionType, edge,
			labelMap);
	}

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectDefinition)"
	)
	private ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

}