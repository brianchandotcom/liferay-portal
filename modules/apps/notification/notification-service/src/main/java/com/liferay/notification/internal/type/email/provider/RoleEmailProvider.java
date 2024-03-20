/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.type.email.provider;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.notification.constants.NotificationRecipientSettingConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

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
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		RoleLocalService roleLocalService,
		UserGroupRoleLocalService userGroupRoleLocalService) {

		_accountEntryLocalService = accountEntryLocalService;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_roleLocalService = roleLocalService;
		_userGroupRoleLocalService = userGroupRoleLocalService;
	}

	@Override
	public String provide(NotificationContext notificationContext, Object value)
		throws PortalException {

		if (value == null) {
			return StringPool.BLANK;
		}

		Map<String, Object> termValues = notificationContext.getTermValues();

		if (termValues == null) {
			return _getEmailAddresses(
				ListUtil.toLongArray(
					_accountEntryLocalService.getAccountEntries(
						QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					AccountEntry::getAccountEntryGroupId),
				notificationContext.getCompanyId(), value);
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				MapUtil.getLong(
					notificationContext.getTermValues(), "objectDefinitionId"));

		if ((objectDefinition == null) ||
			!objectDefinition.isAccountEntryRestricted()) {

			return _getEmailAddresses(
				ListUtil.toLongArray(
					_accountEntryLocalService.getAccountEntries(
						QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					AccountEntry::getAccountEntryGroupId),
				notificationContext.getCompanyId(), value);
		}

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getAccountEntryRestrictedObjectFieldId());

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			MapUtil.getLong(
				notificationContext.getTermValues(), objectField.getName()));

		return _getEmailAddresses(
			new long[] {accountEntry.getAccountEntryGroupId()},
			notificationContext.getCompanyId(), value);
	}

	private String _getEmailAddresses(
			long[] accountEntryGroupIds, long companyId, Object value)
		throws PortalException {

		Set<String> emailAddresses = new HashSet<>();

		for (Map<String, String> roleMap : (List<Map<String, String>>)value) {
			Role role = _roleLocalService.fetchRole(
				companyId,
				roleMap.get(
					NotificationRecipientSettingConstants.NAME_ROLE_NAME));

			if ((role == null) ||
				(role.getType() != RoleConstants.TYPE_ACCOUNT)) {

				continue;
			}

			for (long accountEntryGroupId : accountEntryGroupIds) {
				for (UserGroupRole userGroupRole :
						_userGroupRoleLocalService.
							getUserGroupRolesByGroupAndRole(
								accountEntryGroupId, role.getRoleId())) {

					User user = userGroupRole.getUser();

					emailAddresses.add(user.getEmailAddress());
				}
			}
		}

		return StringUtil.merge(emailAddresses);
	}

	private final AccountEntryLocalService _accountEntryLocalService;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final RoleLocalService _roleLocalService;
	private final UserGroupRoleLocalService _userGroupRoleLocalService;

}