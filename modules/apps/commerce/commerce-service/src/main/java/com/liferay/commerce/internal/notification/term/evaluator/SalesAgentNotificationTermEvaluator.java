/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.notification.term.evaluator;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.notification.term.evaluator.NotificationTermEvaluator;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Luca Pellizzon
 */
public class SalesAgentNotificationTermEvaluator
	implements NotificationTermEvaluator {

	public SalesAgentNotificationTermEvaluator(
		ModelResourcePermission<AccountEntry>
			accountEntryModelResourcePermission,
		CommerceOrderLocalService commerceOrderLocalService,
		ObjectDefinition objectDefinition,
		PermissionCheckerFactory permissionCheckerFactory,
		RoleLocalService roleLocalService, UserLocalService userLocalService) {

		_accountEntryModelResourcePermission =
			accountEntryModelResourcePermission;
		_commerceOrderLocalService = commerceOrderLocalService;
		_objectDefinition = objectDefinition;
		_permissionCheckerFactory = permissionCheckerFactory;
		_roleLocalService = roleLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	public String evaluate(Context context, Object object, String termName)
		throws PortalException {

		if (!(object instanceof Map) || !termName.equals("[%SALES_AGENT%]") ||
			!"CommerceOrder".equalsIgnoreCase(
				_objectDefinition.getShortName())) {

			return termName;
		}

		Map<String, Object> termValues = (Map<String, Object>)object;

		return StringUtil.merge(_getEmailAddresses(termValues));
	}

	private List<String> _getEmailAddresses(Map<String, Object> termValues)
		throws PortalException {

		List<String> emailAddresses = new ArrayList<>();

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				GetterUtil.getLong(termValues.get("id")));

		Role salesAgentRole = _roleLocalService.getRole(
			commerceOrder.getCompanyId(), "Sales Agent");

		List<User> roleUsers = _userLocalService.getRoleUsers(
			salesAgentRole.getRoleId());

		for (User user : roleUsers) {
			if (_accountEntryModelResourcePermission.contains(
					_permissionCheckerFactory.create(user),
					commerceOrder.getCommerceAccountId(), ActionKeys.VIEW)) {

				emailAddresses.add(user.getEmailAddress());
			}
		}

		return emailAddresses;
	}

	private final ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;
	private final CommerceOrderLocalService _commerceOrderLocalService;
	private final ObjectDefinition _objectDefinition;
	private final PermissionCheckerFactory _permissionCheckerFactory;
	private final RoleLocalService _roleLocalService;
	private final UserLocalService _userLocalService;

}