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

import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.object.configuration.ObjectConfiguration;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.entry.permission.util.ObjectEntryPermissionUtil;
import com.liferay.object.exception.ObjectEntryCountException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.base.ObjectEntryServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	configurationPid = "com.liferay.object.configuration.ObjectConfiguration",
	property = {
		"json.web.service.context.name=object",
		"json.web.service.context.path=ObjectEntry"
	},
	service = AopService.class
)
public class ObjectEntryServiceImpl extends ObjectEntryServiceBaseImpl {

	@Override
	public ObjectEntry addObjectEntry(
			long groupId, long objectDefinitionId,
			Map<String, Serializable> values, ServiceContext serviceContext)
		throws PortalException {

		_checkPortletResourcePermission(
			groupId, objectDefinitionId, ObjectActionKeys.ADD_OBJECT_ENTRY);

		_validateSubmissionLimit(objectDefinitionId, getUser());

		return objectEntryLocalService.addObjectEntry(
			getUserId(), groupId, objectDefinitionId, values, serviceContext);
	}

	@Override
	public ObjectEntry addOrUpdateObjectEntry(
			String externalReferenceCode, long groupId, long objectDefinitionId,
			Map<String, Serializable> values, ServiceContext serviceContext)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryPersistence.fetchByERC_C_ODI(
			externalReferenceCode, serviceContext.getCompanyId(),
			objectDefinitionId);

		if (objectEntry == null) {
			_checkPortletResourcePermission(
				groupId, objectDefinitionId, ObjectActionKeys.ADD_OBJECT_ENTRY);
		}
		else {
			_checkModelResourcePermission(
				objectDefinitionId, objectEntry.getObjectEntryId(),
				ActionKeys.UPDATE);
		}

		return objectEntryLocalService.addOrUpdateObjectEntry(
			externalReferenceCode, getUserId(), groupId, objectDefinitionId,
			values, serviceContext);
	}

	@Override
	public ObjectEntry deleteObjectEntry(long objectEntryId)
		throws PortalException {

		_checkPermissions(
			objectEntryLocalService.getObjectEntry(objectEntryId),
			ActionKeys.DELETE);

		return objectEntryLocalService.deleteObjectEntry(objectEntryId);
	}

	@Override
	public ObjectEntry deleteObjectEntry(
			String externalReferenceCode, long companyId, long groupId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryLocalService.getObjectEntry(
			externalReferenceCode, companyId, groupId);

		_checkPermissions(objectEntry, ActionKeys.DELETE);

		return objectEntryLocalService.deleteObjectEntry(objectEntry);
	}

	@Override
	public ObjectEntry fetchObjectEntry(long objectEntryId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryLocalService.fetchObjectEntry(
			objectEntryId);

		if (objectEntry != null) {
			_checkPermissions(objectEntry, ActionKeys.VIEW);
		}

		return objectEntry;
	}

	@Override
	public List<ObjectEntry> getManyToManyObjectEntries(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean related, boolean reverse, int start, int end)
		throws PortalException {

		List<ObjectEntry> objectEntries =
			objectEntryLocalService.getManyToManyObjectEntries(
				groupId, objectRelationshipId, primaryKey, related, reverse,
				start, end);

		for (ObjectEntry objectEntry : objectEntries) {
			_checkModelResourcePermission(
				objectEntry.getObjectDefinitionId(),
				objectEntry.getObjectEntryId(), ActionKeys.VIEW);
		}

		return objectEntries;
	}

	@Override
	public int getManyToManyObjectEntriesCount(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean related, boolean reverse)
		throws PortalException {

		return objectEntryLocalService.getManyToManyObjectEntriesCount(
			groupId, objectRelationshipId, primaryKey, related, reverse);
	}

	@Override
	public ObjectEntry getObjectEntry(long objectEntryId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryLocalService.getObjectEntry(
			objectEntryId);

		_checkPermissions(objectEntry, ActionKeys.VIEW);

		return objectEntry;
	}

	@Override
	public ObjectEntry getObjectEntry(
			String externalReferenceCode, long companyId, long groupId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryLocalService.getObjectEntry(
			externalReferenceCode, companyId, groupId);

		_checkPermissions(objectEntry, ActionKeys.VIEW);

		return objectEntry;
	}

	@Override
	public List<ObjectEntry> getOneToManyObjectEntries(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean related, int start, int end)
		throws PortalException {

		List<ObjectEntry> objectEntries =
			objectEntryLocalService.getOneToManyObjectEntries(
				groupId, objectRelationshipId, primaryKey, related, start, end);

		for (ObjectEntry objectEntry : objectEntries) {
			_checkModelResourcePermission(
				objectEntry.getObjectDefinitionId(),
				objectEntry.getObjectEntryId(), ActionKeys.VIEW);
		}

		return objectEntries;
	}

	@Override
	public int getOneToManyObjectEntriesCount(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean related)
		throws PortalException {

		return objectEntryLocalService.getOneToManyObjectEntriesCount(
			groupId, objectRelationshipId, primaryKey, related);
	}

	@Override
	public boolean hasModelResourcePermission(
			long objectDefinitionId, long objectEntryId, String actionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		ModelResourcePermission<ObjectEntry> modelResourcePermission =
			_modelResourcePermissionsServiceTrackerMap.getService(
				objectDefinition.getClassName());

		return modelResourcePermission.contains(
			getPermissionChecker(), objectEntryId, actionId);
	}

	@Override
	public boolean hasModelResourcePermission(
			ObjectEntry objectEntry, String actionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectEntry.getObjectDefinitionId());

		ModelResourcePermission<ObjectEntry> modelResourcePermission =
			_modelResourcePermissionsServiceTrackerMap.getService(
				objectDefinition.getClassName());

		return modelResourcePermission.contains(
			getPermissionChecker(), objectEntry, actionId);
	}

	@Override
	public ObjectEntry updateObjectEntry(
			long objectEntryId, Map<String, Serializable> values,
			ServiceContext serviceContext)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryLocalService.getObjectEntry(
			objectEntryId);

		_checkModelResourcePermission(
			objectEntry.getObjectDefinitionId(), objectEntry.getObjectEntryId(),
			ActionKeys.UPDATE);

		return objectEntryLocalService.updateObjectEntry(
			getUserId(), objectEntryId, values, serviceContext);
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_modelResourcePermissionsServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<ModelResourcePermission<ObjectEntry>>)
					(Class<?>)ModelResourcePermission.class,
				"(&(com.liferay.object=true)(model.class.name=*))",
				(serviceReference, emitter) -> emitter.emit(
					(String)serviceReference.getProperty("model.class.name")));
		_objectConfiguration = ConfigurableUtil.createConfigurable(
			ObjectConfiguration.class, properties);
		_portletResourcePermissionsServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, PortletResourcePermission.class,
				"(&(com.liferay.object=true)(resource.name=*))",
				(serviceReference, emitter) -> emitter.emit(
					(String)serviceReference.getProperty("resource.name")));
	}

	@Deactivate
	protected void deactivate() {
		_modelResourcePermissionsServiceTrackerMap.close();
		_portletResourcePermissionsServiceTrackerMap.close();
	}

	private void _checkModelResourcePermission(
			long objectDefinitionId, long objectEntryId, String actionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		ModelResourcePermission<ObjectEntry> modelResourcePermission =
			_modelResourcePermissionsServiceTrackerMap.getService(
				objectDefinition.getClassName());

		modelResourcePermission.check(
			getPermissionChecker(), objectEntryId, actionId);
	}

	private void _checkPermissions(ObjectEntry objectEntry, String actionId)
		throws PortalException {

		_checkModelResourcePermission(
			objectEntry.getObjectDefinitionId(), objectEntry.getObjectEntryId(),
			actionId);

		ObjectEntryPermissionUtil.checkAccountEntryPermission(
			_accountEntryLocalService, actionId,
			_objectDefinitionPersistence.findByPrimaryKey(
				objectEntry.getObjectDefinitionId()),
			objectEntry, _objectFieldLocalService, getUserId());
	}

	private void _checkPortletResourcePermission(
			long groupId, long objectDefinitionId, String actionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissionsServiceTrackerMap.getService(
				objectDefinition.getResourceName());

		portletResourcePermission.check(
			getPermissionChecker(), groupId, actionId);
	}

	private void _validateSubmissionLimit(long objectDefinitionId, User user)
		throws PortalException {

		if (!user.isDefaultUser()) {
			return;
		}

		int count = objectEntryPersistence.countByU_ODI(
			user.getUserId(), objectDefinitionId);
		long maximumNumberOfGuestUserObjectEntriesPerObjectDefinition =
			_objectConfiguration.
				maximumNumberOfGuestUserObjectEntriesPerObjectDefinition();

		if (count >= maximumNumberOfGuestUserObjectEntriesPerObjectDefinition) {
			throw new ObjectEntryCountException(
				StringBundler.concat(
					"Unable to exceed ",
					maximumNumberOfGuestUserObjectEntriesPerObjectDefinition,
					" guest object entries for object definition ",
					objectDefinitionId));
		}
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	private volatile ServiceTrackerMap
		<String, ModelResourcePermission<ObjectEntry>>
			_modelResourcePermissionsServiceTrackerMap;
	private volatile ObjectConfiguration _objectConfiguration;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	private volatile ServiceTrackerMap<String, PortletResourcePermission>
		_portletResourcePermissionsServiceTrackerMap;

}