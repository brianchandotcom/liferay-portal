/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.impl;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.object.configuration.ObjectConfiguration;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.entry.util.ObjectEntryThreadLocal;
import com.liferay.object.exception.ObjectDefinitionAccountEntryRestrictedException;
import com.liferay.object.exception.ObjectEntryCountException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.base.ObjectEntryServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionRegistryUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

		if (!ObjectEntryThreadLocal.isSkipObjectEntryResourcePermission()) {
			_checkPortletResourcePermission(
				ObjectActionKeys.ADD_OBJECT_ENTRY, groupId, objectDefinitionId,
				values);
		}

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
				ObjectActionKeys.ADD_OBJECT_ENTRY, groupId, objectDefinitionId,
				values);
		}
		else {
			checkModelResourcePermission(
				objectDefinitionId, objectEntry.getObjectEntryId(),
				ActionKeys.UPDATE);
		}

		return objectEntryLocalService.addOrUpdateObjectEntry(
			externalReferenceCode, getUserId(), groupId, objectDefinitionId,
			values, serviceContext);
	}

	@Override
	public void checkModelResourcePermission(
			long objectDefinitionId, long objectEntryId, String actionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		ModelResourcePermission<ObjectEntry> modelResourcePermission =
			ModelResourcePermissionRegistryUtil.getModelResourcePermission(
				objectDefinition.getClassName());

		modelResourcePermission.check(
			getPermissionChecker(), objectEntryId, actionId);
	}

	@Override
	public ObjectEntry deleteObjectEntry(long objectEntryId)
		throws PortalException {

		if (!ObjectEntryThreadLocal.isSkipObjectEntryResourcePermission()) {
			_checkPermission(
				ActionKeys.DELETE,
				objectEntryLocalService.getObjectEntry(objectEntryId));
		}

		return objectEntryLocalService.deleteObjectEntry(objectEntryId);
	}

	@Override
	public ObjectEntry deleteObjectEntry(
			String externalReferenceCode, long companyId, long groupId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryLocalService.getObjectEntry(
			externalReferenceCode, companyId, groupId);

		_checkPermission(ActionKeys.DELETE, objectEntry);

		return objectEntryLocalService.deleteObjectEntry(objectEntry);
	}

	@Override
	public ObjectEntry fetchManyToOneObjectEntry(
			long groupId, long objectRelationshipId, long primaryKey)
		throws PortalException {

		ObjectEntry objectEntry =
			objectEntryLocalService.fetchManyToOneObjectEntry(
				groupId, objectRelationshipId, primaryKey);

		if ((objectEntry != null) &&
			!ObjectEntryThreadLocal.isSkipObjectEntryResourcePermission()) {

			objectEntryService.checkModelResourcePermission(
				objectEntry.getObjectDefinitionId(),
				objectEntry.getObjectEntryId(), ActionKeys.VIEW);
		}

		return objectEntry;
	}

	@Override
	public ObjectEntry fetchObjectEntry(long objectEntryId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryLocalService.fetchObjectEntry(
			objectEntryId);

		if (objectEntry != null) {
			_checkPermission(ActionKeys.VIEW, objectEntry);
		}

		return objectEntry;
	}

	@Override
	public List<ObjectEntry> getManyToManyObjectEntries(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean related, boolean reverse, String search, int start, int end)
		throws PortalException {

		List<ObjectEntry> objectEntries =
			objectEntryLocalService.getManyToManyObjectEntries(
				groupId, objectRelationshipId, primaryKey, related, reverse,
				search, start, end);

		if (!ObjectEntryThreadLocal.isSkipObjectEntryResourcePermission()) {
			for (ObjectEntry objectEntry : objectEntries) {
				objectEntryService.checkModelResourcePermission(
					objectEntry.getObjectDefinitionId(),
					objectEntry.getObjectEntryId(), ActionKeys.VIEW);
			}
		}

		return objectEntries;
	}

	@Override
	public int getManyToManyObjectEntriesCount(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean related, boolean reverse, String search)
		throws PortalException {

		return objectEntryLocalService.getManyToManyObjectEntriesCount(
			groupId, objectRelationshipId, primaryKey, related, reverse,
			search);
	}

	@Override
	public ModelResourcePermission<ObjectEntry> getModelResourcePermission(
			ObjectEntry objectEntry)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectEntry.getObjectDefinitionId());

		return ModelResourcePermissionRegistryUtil.getModelResourcePermission(
			objectDefinition.getClassName());
	}

	@Override
	public ObjectEntry getObjectEntry(long objectEntryId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryLocalService.getObjectEntry(
			objectEntryId);

		if (!ObjectEntryThreadLocal.isSkipObjectEntryResourcePermission()) {
			_checkPermission(ActionKeys.VIEW, objectEntry);
		}

		return objectEntry;
	}

	@Override
	public ObjectEntry getObjectEntry(
			String externalReferenceCode, long companyId, long groupId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryLocalService.getObjectEntry(
			externalReferenceCode, companyId, groupId);

		if (!ObjectEntryThreadLocal.isSkipObjectEntryResourcePermission()) {
			_checkPermission(ActionKeys.VIEW, objectEntry);
		}

		return objectEntry;
	}

	@Override
	public List<ObjectEntry> getOneToManyObjectEntries(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean related, String search, int start, int end)
		throws PortalException {

		List<ObjectEntry> objectEntries =
			objectEntryLocalService.getOneToManyObjectEntries(
				groupId, objectRelationshipId, primaryKey, related, search,
				start, end);

		if (!ObjectEntryThreadLocal.isSkipObjectEntryResourcePermission()) {
			for (ObjectEntry objectEntry : objectEntries) {
				objectEntryService.checkModelResourcePermission(
					objectEntry.getObjectDefinitionId(),
					objectEntry.getObjectEntryId(), ActionKeys.VIEW);
			}
		}

		return objectEntries;
	}

	@Override
	public int getOneToManyObjectEntriesCount(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean related, String search)
		throws PortalException {

		return objectEntryLocalService.getOneToManyObjectEntriesCount(
			groupId, objectRelationshipId, primaryKey, related, search);
	}

	@Override
	public boolean hasModelResourcePermission(
			long objectDefinitionId, long objectEntryId, String actionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		ModelResourcePermission<ObjectEntry> modelResourcePermission =
			ModelResourcePermissionRegistryUtil.getModelResourcePermission(
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
			ModelResourcePermissionRegistryUtil.getModelResourcePermission(
				objectDefinition.getClassName());

		return modelResourcePermission.contains(
			getPermissionChecker(), objectEntry, actionId);
	}

	@Override
	public boolean hasModelResourcePermission(
			User user, long objectEntryId, String actionId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryLocalService.getObjectEntry(
			objectEntryId);

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectEntry.getObjectDefinitionId());

		ModelResourcePermission<ObjectEntry> modelResourcePermission =
			ModelResourcePermissionRegistryUtil.getModelResourcePermission(
				objectDefinition.getClassName());

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			user);

		return modelResourcePermission.contains(
			permissionChecker, objectEntryId, actionId);
	}

	@Override
	public boolean hasPortletResourcePermission(
			long groupId, long objectDefinitionId, String actionId)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_getPortletResourcePermission(objectDefinitionId);

		return portletResourcePermission.contains(
			getPermissionChecker(), groupId, actionId);
	}

	@Override
	public ObjectEntry updateObjectEntry(
			long objectEntryId, Map<String, Serializable> values,
			ServiceContext serviceContext)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryLocalService.getObjectEntry(
			objectEntryId);

		if (!ObjectEntryThreadLocal.isSkipObjectEntryResourcePermission()) {
			checkModelResourcePermission(
				objectEntry.getObjectDefinitionId(),
				objectEntry.getObjectEntryId(), ActionKeys.UPDATE);
		}

		return objectEntryLocalService.updateObjectEntry(
			getUserId(), objectEntryId, values, serviceContext);
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

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
		_portletResourcePermissionsServiceTrackerMap.close();
	}

	private void _checkPermission(String actionId, ObjectEntry objectEntry)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectEntry.getObjectDefinitionId());

		ModelResourcePermission<ObjectEntry> modelResourcePermission =
			ModelResourcePermissionRegistryUtil.getModelResourcePermission(
				objectDefinition.getClassName());

		modelResourcePermission.check(
			getPermissionChecker(), objectEntry, actionId);
	}

	private void _checkPortletResourcePermission(
			String actionId, long groupId, long objectDefinitionId,
			Map<String, Serializable> values)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_getPortletResourcePermission(objectDefinitionId);

		PermissionChecker permissionChecker = getPermissionChecker();

		portletResourcePermission.check(permissionChecker, groupId, actionId);

		if (permissionChecker.hasPermission(
				groupId, portletResourcePermission.getResourceName(), 0,
				actionId)) {

			return;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		if (!objectDefinition.isAccountEntryRestricted()) {
			return;
		}

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getAccountEntryRestrictedObjectFieldId());

		long accountEntryId = MapUtil.getLong(values, objectField.getName());

		if (accountEntryId == 0) {
			return;
		}

		long[] accountEntryIds = ListUtil.toLongArray(
			_accountEntryLocalService.getUserAccountEntries(
				getUserId(), AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
				null,
				new String[] {
					AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
				},
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS),
			AccountEntry::getAccountEntryId);

		if (!ArrayUtil.contains(accountEntryIds, accountEntryId)) {
			throw new ObjectDefinitionAccountEntryRestrictedException(
				StringBundler.concat(
					"User ", getUserId(),
					" does not have access to account entry ", accountEntryId));
		}

		Set<Long> rolesIds = new HashSet<>();

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			accountEntryId);

		rolesIds.addAll(
			TransformUtil.transform(
				_userGroupRoleLocalService.getUserGroupRoles(
					permissionChecker.getUserId(),
					accountEntry.getAccountEntryGroupId()),
				UserGroupRole::getRoleId));

		List<AccountEntryOrganizationRel> accountEntryOrganizationRels =
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRels(accountEntryId);

		for (AccountEntryOrganizationRel accountEntryOrganizationRel :
				accountEntryOrganizationRels) {

			Organization organization =
				accountEntryOrganizationRel.getOrganization();

			Group group = _groupLocalService.getOrganizationGroup(
				objectDefinition.getCompanyId(),
				organization.getOrganizationId());

			rolesIds.addAll(
				TransformUtil.transform(
					_userGroupRoleLocalService.getUserGroupRoles(
						permissionChecker.getUserId(), group.getGroupId()),
					UserGroupRole::getRoleId));

			for (Organization ancestorOrganization :
					organization.getAncestors()) {

				group = _groupLocalService.getOrganizationGroup(
					objectDefinition.getCompanyId(),
					ancestorOrganization.getOrganizationId());

				rolesIds.addAll(
					TransformUtil.transform(
						_userGroupRoleLocalService.getUserGroupRoles(
							permissionChecker.getUserId(), group.getGroupId()),
						UserGroupRole::getRoleId));
			}
		}

		for (Long roleId : rolesIds) {
			ResourcePermission resourcePermission =
				_resourcePermissionLocalService.fetchResourcePermission(
					objectDefinition.getCompanyId(),
					objectDefinition.getResourceName(),
					ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", roleId);

			if (resourcePermission == null) {
				continue;
			}

			if (resourcePermission.hasActionId(actionId)) {
				return;
			}
		}

		throw new PrincipalException.MustHavePermission(
			permissionChecker, objectDefinition.getResourceName(), 0, actionId);
	}

	private PortletResourcePermission _getPortletResourcePermission(
			long objectDefinitionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		return _portletResourcePermissionsServiceTrackerMap.getService(
			objectDefinition.getResourceName());
	}

	private void _validateSubmissionLimit(long objectDefinitionId, User user)
		throws PortalException {

		if (!user.isGuestUser()) {
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

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	private volatile ObjectConfiguration _objectConfiguration;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	private volatile ServiceTrackerMap<String, PortletResourcePermission>
		_portletResourcePermissionsServiceTrackerMap;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}