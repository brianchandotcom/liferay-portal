/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.type.email.provider;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.notification.constants.NotificationRecipientSettingConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Carolina Barbosa
 */
public class RoleEmailProvider implements EmailProvider {

	public RoleEmailProvider(
		AccountEntryLocalService accountEntryLocalService,
		AccountEntryOrganizationRelLocalService
			accountEntryOrganizationRelLocalService,
		AccountEntryUserRelLocalService accountEntryUserRelLocalService,
		GroupLocalService groupLocalService,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		OrganizationLocalService organizationLocalService,
		RoleLocalService roleLocalService,
		UserGroupRoleLocalService userGroupRoleLocalService,
		UserLocalService userLocalService) {

		_accountEntryLocalService = accountEntryLocalService;
		_accountEntryOrganizationRelLocalService =
			accountEntryOrganizationRelLocalService;
		_accountEntryUserRelLocalService = accountEntryUserRelLocalService;
		_groupLocalService = groupLocalService;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_organizationLocalService = organizationLocalService;
		_roleLocalService = roleLocalService;
		_userGroupRoleLocalService = userGroupRoleLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	public String provide(NotificationContext notificationContext, Object value)
		throws PortalException {

		if (value == null) {
			return StringPool.BLANK;
		}

		ObjectDefinition objectDefinition = null;

		Map<String, Object> termValues = notificationContext.getTermValues();

		if (termValues != null) {
			objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					MapUtil.getLong(termValues, "objectDefinitionId"));
		}

		Map<Integer, long[]> groupIdsMap = new HashMap<>();

		if ((objectDefinition == null) ||
			!objectDefinition.isAccountEntryRestricted()) {

			groupIdsMap.put(
				RoleConstants.TYPE_ACCOUNT,
				ListUtil.toLongArray(
					_accountEntryLocalService.getAccountEntries(
						QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					AccountEntry::getAccountEntryGroupId));
			groupIdsMap.put(
				RoleConstants.TYPE_ORGANIZATION,
				ListUtil.toLongArray(
					_organizationLocalService.getOrganizations(
						QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					Organization::getGroupId));

			return _getEmailAddresses(
				notificationContext.getCompanyId(), groupIdsMap, value);
		}

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getAccountEntryRestrictedObjectFieldId());

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			MapUtil.getLong(termValues, objectField.getName()));

		groupIdsMap.put(
			RoleConstants.TYPE_ACCOUNT,
			new long[] {accountEntry.getAccountEntryGroupId()});

		Set<Long> groupIds = new HashSet<>();

		for (AccountEntryOrganizationRel accountEntryOrganizationRel :
				_accountEntryOrganizationRelLocalService.
					getAccountEntryOrganizationRels(
						accountEntry.getAccountEntryId())) {

			Organization organization =
				accountEntryOrganizationRel.getOrganization();

			groupIds.add(organization.getGroupId());

			for (Organization ancestorOrganization :
					organization.getAncestors()) {

				groupIds.add(ancestorOrganization.getGroupId());
			}
		}

		groupIdsMap.put(
			RoleConstants.TYPE_ORGANIZATION, ArrayUtil.toLongArray(groupIds));

		return _getEmailAddresses(
			notificationContext.getCompanyId(), groupIdsMap, value);
	}

	private String _getEmailAddresses(
			long companyId, Map<Integer, long[]> groupIdsMap, Object value)
		throws PortalException {

		Set<String> emailAddresses = new HashSet<>();

		for (Map<String, String> roleMap : (List<Map<String, String>>)value) {
			Role role = _roleLocalService.fetchRole(
				companyId,
				roleMap.get(
					NotificationRecipientSettingConstants.NAME_ROLE_NAME));

			if ((role == null) ||
				((role.getType() != RoleConstants.TYPE_ACCOUNT) &&
				 (role.getType() != RoleConstants.TYPE_ORGANIZATION) &&
				 (role.getType() != RoleConstants.TYPE_REGULAR))) {

				continue;
			}

			if (role.getType() == RoleConstants.TYPE_REGULAR) {
				List<User> users = _userLocalService.getRoleUsers(
					role.getRoleId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

				for (User user : users) {
					emailAddresses.add(user.getEmailAddress());
				}

				continue;
			}

			for (long groupId : groupIdsMap.get(role.getType())) {
				if (StringUtil.equals(
						role.getName(),
						AccountRoleConstants.
							REQUIRED_ROLE_NAME_ACCOUNT_MEMBER)) {

					Group group = _groupLocalService.fetchGroup(groupId);

					for (AccountEntryUserRel accountEntryUserRel :
							_accountEntryUserRelLocalService.
								getAccountEntryUserRelsByAccountEntryId(
									group.getClassPK())) {

						User user = accountEntryUserRel.getUser();

						emailAddresses.add(user.getEmailAddress());
					}

					continue;
				}

				for (UserGroupRole userGroupRole :
						_userGroupRoleLocalService.
							getUserGroupRolesByGroupAndRole(
								groupId, role.getRoleId())) {

					User user = userGroupRole.getUser();

					emailAddresses.add(user.getEmailAddress());
				}
			}
		}

		return StringUtil.merge(emailAddresses);
	}

	private final AccountEntryLocalService _accountEntryLocalService;
	private final AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;
	private final AccountEntryUserRelLocalService
		_accountEntryUserRelLocalService;
	private final GroupLocalService _groupLocalService;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final OrganizationLocalService _organizationLocalService;
	private final RoleLocalService _roleLocalService;
	private final UserGroupRoleLocalService _userGroupRoleLocalService;
	private final UserLocalService _userLocalService;

}