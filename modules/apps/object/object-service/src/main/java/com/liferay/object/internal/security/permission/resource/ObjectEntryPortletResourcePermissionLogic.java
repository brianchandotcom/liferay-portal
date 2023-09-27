/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.security.permission.resource;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionLogic;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * @author Marco Leo
 */
public class ObjectEntryPortletResourcePermissionLogic
	implements PortletResourcePermissionLogic {

	public ObjectEntryPortletResourcePermissionLogic(
		AccountEntryLocalService accountEntryLocalService,
		GroupLocalService groupLocalService,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		OrganizationLocalService organizationLocalService) {

		_accountEntryLocalService = accountEntryLocalService;
		_groupLocalService = groupLocalService;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_organizationLocalService = organizationLocalService;
	}

	@Override
	public Boolean contains(
		PermissionChecker permissionChecker, String name, Group group,
		String actionId) {

		try {
			return _contains(permissionChecker, name, group, actionId);
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return false;
		}
	}

	private Boolean _contains(
			PermissionChecker permissionChecker, String name, Group group,
			String actionId)
		throws PortalException {

		if (permissionChecker.hasPermission(group, name, 0, actionId)) {
			return true;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				GetterUtil.getLong(
					StringUtil.removeSubstring(name, "com.liferay.object#")));

		if (!objectDefinition.isAccountEntryRestricted()) {
			return false;
		}

		List<AccountEntry> accountEntries =
			_accountEntryLocalService.getUserAccountEntries(
				permissionChecker.getUserId(),
				AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, null,
				new String[] {
					AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
				},
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (AccountEntry accountEntry : accountEntries) {
			if (permissionChecker.hasPermission(
					accountEntry.getAccountEntryGroupId(), name, 0, actionId)) {

				return true;
			}
		}

		List<Organization> organizations =
			_organizationLocalService.getUserOrganizations(
				permissionChecker.getUserId());

		for (Organization organization : organizations) {
			Group organizationGroup = _groupLocalService.getOrganizationGroup(
				permissionChecker.getCompanyId(),
				organization.getOrganizationId());

			if (permissionChecker.hasPermission(
					organizationGroup.getGroupId(), name, 0, actionId)) {

				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryPortletResourcePermissionLogic.class);

	private final AccountEntryLocalService _accountEntryLocalService;
	private final GroupLocalService _groupLocalService;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final OrganizationLocalService _organizationLocalService;

}