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
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.base.ObjectEntryServiceBaseImpl;
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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

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

		ObjectEntry objectEntry = objectEntryPersistence.fetchByERC_G_C(
			externalReferenceCode, groupId, serviceContext.getCompanyId());

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
	public boolean hasModelResourcePermission(
			long objectDefinitionId, long objectEntryId, String actionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId);

		ModelResourcePermission<ObjectEntry> modelResourcePermission =
			_modelResourcePermissions.get(objectDefinition.getClassName());

		return modelResourcePermission.contains(
			getPermissionChecker(), objectEntryId, actionId);
	}

	@Override
	public boolean hasModelResourcePermission(
			ObjectEntry objectEntry, String actionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectEntry.getObjectDefinitionId());

		ModelResourcePermission<ObjectEntry> modelResourcePermission =
			_modelResourcePermissions.get(objectDefinition.getClassName());

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
	protected void activate(Map<String, Object> properties) {
		_objectConfiguration = ConfigurableUtil.createConfigurable(
			ObjectConfiguration.class, properties);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(&(com.liferay.object=true)(model.class.name=*))"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<ObjectEntry> modelResourcePermission,
		Map<String, Object> properties) {

		String className = (String)properties.get("model.class.name");

		_modelResourcePermissions.put(className, modelResourcePermission);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(&(com.liferay.object=true)(resource.name=*))"
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

		String className = (String)properties.get("model.class.name");

		_modelResourcePermissions.remove(className);
	}

	protected void unsetPortletResourcePermission(
		PortletResourcePermission portletResourcePermission,
		Map<String, Object> properties) {

		String resourceName = (String)properties.get("resource.name");

		_portletResourcePermissions.remove(resourceName);
	}

	private void _checkModelResourcePermission(
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

	private void _checkPermissions(ObjectEntry objectEntry, String actionId)
		throws PortalException {

		_checkModelResourcePermission(
			objectEntry.getObjectDefinitionId(), objectEntry.getObjectEntryId(),
			actionId);

		ObjectEntryPermissionUtil.checkAccountEntryPermission(
			_accountEntryLocalService, actionId, _objectDefinitionLocalService,
			objectEntry, _objectFieldLocalService, getUserId());
	}

	private void _checkPortletResourcePermission(
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

	private final Map<String, ModelResourcePermission<ObjectEntry>>
		_modelResourcePermissions = new ConcurrentHashMap<>();
	private volatile ObjectConfiguration _objectConfiguration;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	private final Map<String, PortletResourcePermission>
		_portletResourcePermissions = new ConcurrentHashMap<>();

}