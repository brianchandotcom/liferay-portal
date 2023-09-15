/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.security.permission.resource;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.object.definition.tree.Edge;
import com.liferay.object.definition.tree.Node;
import com.liferay.object.definition.tree.Tree;
import com.liferay.object.definition.tree.TreeFactory;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.function.transform.TransformUtil;
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
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectEntryModelResourcePermission
	implements ModelResourcePermission<ObjectEntry> {

	public ObjectEntryModelResourcePermission(
		AccountEntryLocalService accountEntryLocalService,
		AccountEntryOrganizationRelLocalService
			accountEntryOrganizationRelLocalService,
		GroupLocalService groupLocalService, String modelName,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		PortletResourcePermission portletResourcePermission,
		ResourcePermissionLocalService resourcePermissionLocalService,
		TreeFactory treeFactory,
		UserGroupRoleLocalService userGroupRoleLocalService) {

		_accountEntryLocalService = accountEntryLocalService;
		_accountEntryOrganizationRelLocalService =
			accountEntryOrganizationRelLocalService;
		_groupLocalService = groupLocalService;
		_modelName = modelName;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_portletResourcePermission = portletResourcePermission;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_treeFactory = treeFactory;
		_userGroupRoleLocalService = userGroupRoleLocalService;
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long objectEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, objectEntryId, actionId)) {
			_throwPrincipalException(
				actionId,
				_objectEntryLocalService.getObjectEntry(objectEntryId),
				permissionChecker);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, ObjectEntry objectEntry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, objectEntry, actionId)) {
			_throwPrincipalException(actionId, objectEntry, permissionChecker);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long objectEntryId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_objectEntryLocalService.getObjectEntry(objectEntryId), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, ObjectEntry objectEntry,
			String actionId)
		throws PortalException {

		User user = permissionChecker.getUser();

		objectEntry = _getContextObjectEntry(objectEntry);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectEntry.getObjectDefinitionId());

		if (user.isGuestUser()) {
			return permissionChecker.hasPermission(
				objectEntry.getGroupId(), objectDefinition.getClassName(),
				objectEntry.getObjectEntryId(), actionId);
		}

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(),
				objectDefinition.getClassName(), objectEntry.getObjectEntryId(),
				objectEntry.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				objectEntry.getGroupId(), objectDefinition.getClassName(),
				objectEntry.getObjectEntryId(), actionId)) {

			return true;
		}

		if (!objectDefinition.isAccountEntryRestricted()) {
			return false;
		}

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getAccountEntryRestrictedObjectFieldId());

		long accountEntryId = MapUtil.getLong(
			objectEntry.getValues(), objectField.getName());

		if (accountEntryId == 0) {
			return true;
		}

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			accountEntryId);

		if (Objects.equals(actionId, ActionKeys.VIEW)) {
			return ArrayUtil.contains(
				ListUtil.toLongArray(
					_accountEntryLocalService.getUserAccountEntries(
						permissionChecker.getUserId(),
						AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, null,
						new String[] {
							AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
							AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
						},
						WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS),
					AccountEntry::getAccountEntryId),
				accountEntryId);
		}

		Set<Long> rolesIds = new HashSet<>();

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
					objectDefinition.getClassName(),
					ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", roleId);

			if (resourcePermission == null) {
				continue;
			}

			if (resourcePermission.hasActionId(actionId)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String getModelName() {
		return _modelName;
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	private ObjectEntry _getContextObjectEntry(ObjectEntry objectEntry)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectEntry.getObjectDefinitionId());

		if (!objectDefinition.isRootDescendantNode()) {
			return objectEntry;
		}

		Tree tree = _treeFactory.create(
			objectDefinition.getRootObjectDefinitionId());

		Node node = tree.getNode(objectDefinition.getObjectDefinitionId());

		while (!node.isRoot()) {
			Edge edge = node.getEdge();

			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.getObjectRelationship(
					edge.getObjectRelationshipId());

			ObjectField objectField = _objectFieldLocalService.getObjectField(
				objectRelationship.getObjectFieldId2());

			objectEntry = _objectEntryLocalService.getObjectEntry(
				MapUtil.getLong(
					objectEntry.getValues(), objectField.getName()));

			node = tree.getNode(objectEntry.getObjectDefinitionId());
		}

		return objectEntry;
	}

	private void _throwPrincipalException(
			String actionId, ObjectEntry objectEntry,
			PermissionChecker permissionChecker)
		throws PortalException {

		objectEntry = _getContextObjectEntry(objectEntry);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectEntry.getObjectDefinitionId());

		throw new PrincipalException.MustHavePermission(
			permissionChecker, objectDefinition.getClassName(),
			objectEntry.getObjectEntryId(), actionId);
	}

	private final AccountEntryLocalService _accountEntryLocalService;
	private final AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;
	private final GroupLocalService _groupLocalService;
	private final String _modelName;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final PortletResourcePermission _portletResourcePermission;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final TreeFactory _treeFactory;
	private final UserGroupRoleLocalService _userGroupRoleLocalService;

}