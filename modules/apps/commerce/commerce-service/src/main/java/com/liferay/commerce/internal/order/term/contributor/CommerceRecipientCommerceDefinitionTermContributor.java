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

package com.liferay.commerce.internal.order.term.contributor;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.CommerceAccountUserRelLocalService;
import com.liferay.commerce.constants.CommerceDefinitionTermConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceSubscriptionNotificationConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.order.CommerceDefinitionTermContributor;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.definition.term.contributor.key=" + CommerceRecipientCommerceDefinitionTermContributor.KEY,
		"commerce.notification.type.key=" + CommerceOrderConstants.ORDER_NOTIFICATION_AWAITING_SHIPMENT,
		"commerce.notification.type.key=" + CommerceOrderConstants.ORDER_NOTIFICATION_COMPLETED,
		"commerce.notification.type.key=" + CommerceOrderConstants.ORDER_NOTIFICATION_PARTIALLY_SHIPPED,
		"commerce.notification.type.key=" + CommerceOrderConstants.ORDER_NOTIFICATION_PLACED,
		"commerce.notification.type.key=" + CommerceOrderConstants.ORDER_NOTIFICATION_PROCESSING,
		"commerce.notification.type.key=" + CommerceOrderConstants.ORDER_NOTIFICATION_SHIPPED,
		"commerce.notification.type.key=" + CommerceSubscriptionNotificationConstants.SUBSCRIPTION_ACTIVATED,
		"commerce.notification.type.key=" + CommerceSubscriptionNotificationConstants.SUBSCRIPTION_CANCELLED,
		"commerce.notification.type.key=" + CommerceSubscriptionNotificationConstants.SUBSCRIPTION_RENEWED,
		"commerce.notification.type.key=" + CommerceSubscriptionNotificationConstants.SUBSCRIPTION_SUSPENDED
	},
	service = CommerceDefinitionTermContributor.class
)
public class CommerceRecipientCommerceDefinitionTermContributor
	implements CommerceDefinitionTermContributor {

	public static final String KEY =
		CommerceDefinitionTermConstants.RECIPIENT_DEFINITION_TERMS_CONTRIBUTOR;

	@Override
	public String getFilledTerm(String term, Object object, Locale locale)
		throws PortalException {

		CommerceOrder commerceOrder = null;

		if (object instanceof CommerceOrder) {
			commerceOrder = (CommerceOrder)object;
		}
		else if (object instanceof CommerceSubscriptionEntry) {
			CommerceSubscriptionEntry commerceSubscriptionEntry =
				(CommerceSubscriptionEntry)object;

			CommerceOrderItem commerceOrderItem =
				_commerceOrderItemLocalService.getCommerceOrderItem(
					commerceSubscriptionEntry.getCommerceOrderItemId());

			commerceOrder = commerceOrderItem.getCommerceOrder();
		}

		if (commerceOrder == null) {
			return term;
		}

		if (term.equals(_ACCOUNT_ROLE_ADMINISTRATOR)) {
			CommerceAccount commerceAccount =
				commerceOrder.getCommerceAccount();

			Role accountAdminRole = _roleLocalService.getRole(
				commerceOrder.getCompanyId(),
				CommerceAccountConstants.ROLE_NAME_ACCOUNT_ADMINISTRATOR);

			return _getUserIds(commerceAccount, accountAdminRole);
		}

		if (term.equals(_ACCOUNT_ROLE_ORDER_MANAGER)) {
			CommerceAccount commerceAccount =
				commerceOrder.getCommerceAccount();

			Role orderManagerRole = _roleLocalService.getRole(
				commerceOrder.getCompanyId(),
				CommerceAccountConstants.ROLE_NAME_ACCOUNT_ORDER_MANAGER);

			return _getUserIds(commerceAccount, orderManagerRole);
		}

		if (term.equals(_ORDER_CREATOR)) {
			CommerceAccount commerceAccount =
				commerceOrder.getCommerceAccount();

			if (commerceAccount.getType() ==
					CommerceAccountConstants.ACCOUNT_TYPE_PERSONAL) {

				User user = _userLocalService.getUser(
					commerceAccount.getUserId());

				return String.valueOf(user.getUserId());
			}

			return String.valueOf(commerceOrder.getUserId());
		}

		if (term.startsWith("[%USER_GROUP_")) {
			String[] s = term.split("_");

			String userGroupName = StringUtil.removeChars(s[2], '%', ']');

			UserGroup userGroup = _userGroupLocalService.getUserGroup(
				commerceOrder.getCompanyId(), userGroupName);

			return _getUserIds(userGroup);
		}

		return term;
	}

	@Override
	public String getLabel(String term, Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle, _commerceOrderDefinitionTermsMap.get(term));
	}

	@Override
	public List<String> getTerms() {
		return new ArrayList<>(_commerceOrderDefinitionTermsMap.keySet());
	}

	private String _getUserIds(CommerceAccount commerceAccount, Role role)
		throws PortalException {

		List<CommerceAccountUserRel> commerceAccountUserRels =
			_commerceAccountUserRelLocalService.getCommerceAccountUserRels(
				commerceAccount.getCommerceAccountId());

		StringBundler resultsSB = new StringBundler();

		for (CommerceAccountUserRel commerceAccountUserRel :
				commerceAccountUserRels) {

			List<Role> userRoles = _roleLocalService.getUserGroupRoles(
				commerceAccountUserRel.getCommerceAccountUserId(),
				commerceAccount.getCommerceAccountGroupId());

			if (userRoles.contains(role)) {
				resultsSB.append(
					commerceAccountUserRel.getCommerceAccountUserId());
				resultsSB.append(",");
			}
		}

		return resultsSB.toString();
	}

	private String _getUserIds(UserGroup userGroup) throws PortalException {
		List<User> groupUsers = _userLocalService.getUserGroupUsers(
			userGroup.getUserGroupId());

		StringBundler resultsSB = new StringBundler();

		for (User user : groupUsers) {
			resultsSB.append(user.getUserId());
			resultsSB.append(",");
		}

		return resultsSB.toString();
	}

	private static final String _ACCOUNT_ROLE_ADMINISTRATOR =
		"[%ACCOUNT_ROLE_ADMINISTRATOR%]";

	private static final String _ACCOUNT_ROLE_ORDER_MANAGER =
		"[%ACCOUNT_ROLE_ORDER_MANAGER%]";

	private static final String _ORDER_CREATOR = "[%ORDER_CREATOR%]";

	private static final String _USER_GROUP_NAME = "[%USER_GROUP_NAME%]";

	private static final Map<String, String> _commerceOrderDefinitionTermsMap =
		HashMapBuilder.put(
			_ACCOUNT_ROLE_ADMINISTRATOR, "account-role-administrator"
		).put(
			_ACCOUNT_ROLE_ORDER_MANAGER, "account-role-order-manager"
		).put(
			_ORDER_CREATOR, "order-creator-definition-term"
		).put(
			_USER_GROUP_NAME, "user-group-name"
		).build();

	@Reference
	private CommerceAccountUserRelLocalService
		_commerceAccountUserRelLocalService;

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}