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

package com.liferay.object.service.impl;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.base.ObjectEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=object",
		"json.web.service.context.path=ObjectEntry"
	},
	service = AopService.class
)
public class ObjectEntryServiceImpl extends ObjectEntryServiceBaseImpl {

	@Override
	public ObjectEntry addObjectEntry(
			long userId, long groupId, long objectDefinitionId,
			Map<String, Serializable> values, ServiceContext serviceContext)
		throws PortalException {

		checkPortletResourcePermission(
			groupId, objectDefinitionId, ActionKeys.ADD_ENTRY);

		return objectEntryLocalService.addObjectEntry(
			userId, groupId, objectDefinitionId, values, serviceContext);
	}

	@Override
	public ObjectEntry addOrUpdateObjectEntry(
			String externalReferenceCode, long userId, long groupId,
			long objectDefinitionId, Map<String, Serializable> values,
			ServiceContext serviceContext)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryPersistence.fetchByG_C_ERC(
			groupId, serviceContext.getCompanyId(), externalReferenceCode);

		if (objectEntry == null) {
			checkPortletResourcePermission(
				groupId, objectDefinitionId, ActionKeys.ADD_ENTRY);
		}
		else {
			checkModelResourcePermission(
				objectDefinitionId, objectEntry.getObjectEntryId(),
				ActionKeys.UPDATE);
		}

		return objectEntryLocalService.addOrUpdateObjectEntry(
			externalReferenceCode, userId, groupId, objectDefinitionId, values,
			serviceContext);
	}

	@Override
	public ObjectEntry deleteObjectEntry(long objectEntryId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryLocalService.getObjectEntry(
			objectEntryId);

		checkModelResourcePermission(
			objectEntry.getObjectDefinitionId(), objectEntry.getObjectEntryId(),
			ActionKeys.DELETE);

		return objectEntryLocalService.deleteObjectEntry(objectEntryId);
	}

	protected void checkModelResourcePermission(
			long objectDefinitionId, long objectEntryId, String actionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId);

		ModelResourcePermission<ObjectEntry> modelResourcePermission =
			_modelResourcePermissions.get(objectDefinition.getClassName());

		modelResourcePermission.check(
			getPermissionChecker(), objectEntryId, actionId);
	}

	protected void checkPortletResourcePermission(
			long groupId, long objectDefinitionId, String actionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId);

		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissions.get(objectDefinition.getResourceName());

		portletResourcePermission.check(
			getPermissionChecker(), groupId, actionId);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(&(model.class.name=*)(object=true))"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<ObjectEntry> modelResourcePermission,
		Map<String, Object> properties) {

		String resourceName = (String)properties.get("model.class.name");

		_modelResourcePermissions.put(resourceName, modelResourcePermission);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(&(resource.name=*)(object=true))"
	)
	protected void setPortletResourcePermission(
		PortletResourcePermission portletResourcePermission,
		Map<String, Object> properties) {

		String resourceName = (String)properties.get("resource.name");

		_portletResourcePermissions.put(
			resourceName, portletResourcePermission);
	}

	protected void unsetModelResourcePermission(
		ModelResourcePermission<ObjectEntry> modelResourcePermission,
		Map<String, Object> properties) {

		String resourceName = (String)properties.get("resource.name");

		_modelResourcePermissions.remove(resourceName);
	}

	protected void unsetPortletResourcePermission(
		PortletResourcePermission portletResourcePermission,
		Map<String, Object> properties) {

		String resourceName = (String)properties.get("resource.name");

		_portletResourcePermissions.remove(resourceName);
	}

	private final ConcurrentMap<String, ModelResourcePermission<ObjectEntry>>
		_modelResourcePermissions = new ConcurrentHashMap<>();

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private final ConcurrentMap<String, PortletResourcePermission>
		_portletResourcePermissions = new ConcurrentHashMap<>();

}