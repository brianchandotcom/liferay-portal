/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.notification.term.evaluator;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderNoteLocalService;
import com.liferay.commerce.util.CommerceChannelConfigurationUtil;
import com.liferay.notification.term.evaluator.NotificationTermEvaluator;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderNoteRecipientIdsNotificationTermEvaluator
	implements NotificationTermEvaluator {

	public CommerceOrderNoteRecipientIdsNotificationTermEvaluator(
		AccountEntryUserRelLocalService accountEntryUserRelLocalService,
		AccountRoleLocalService accountRoleLocalService,
		CommerceOrderLocalService commerceOrderLocalService,
		CommerceOrderNoteLocalService commerceOrderNoteLocalService,
		ObjectDefinition objectDefinition, RoleLocalService roleLocalService,
		UserLocalService userLocalService) {

		_accountEntryUserRelLocalService = accountEntryUserRelLocalService;
		_accountRoleLocalService = accountRoleLocalService;
		_commerceOrderLocalService = commerceOrderLocalService;
		_commerceOrderNoteLocalService = commerceOrderNoteLocalService;
		_objectDefinition = objectDefinition;
		_roleLocalService = roleLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	public String evaluate(Context context, Object object, String termName)
		throws PortalException {

		if (!(object instanceof Map) ||
			!termName.equals("[%COMMERCEORDERNOTE_RECIPIENT_IDS%]") ||
			!"CommerceOrderNote".equalsIgnoreCase(
				_objectDefinition.getShortName())) {

			return termName;
		}

		Map<String, Object> termValues = (Map<String, Object>)object;

		return StringUtil.merge(_getUserIds(termValues));
	}

	private List<Long> _getUserIds(
		long accountEntryId, long accountRoleId, long userId) {

		return TransformUtil.transform(
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountEntryId(
					accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			accountEntryUserRel -> {
				User accountEntryUserRelUser = accountEntryUserRel.getUser();

				if (accountEntryUserRelUser.getUserId() == userId) {
					return null;
				}

				if (_accountRoleLocalService.hasUserAccountRole(
						accountEntryUserRel.getAccountEntryId(), accountRoleId,
						accountEntryUserRelUser.getUserId())) {

					return accountEntryUserRelUser.getUserId();
				}

				return null;
			});
	}

	private List<Long> _getUserIds(Map<String, Object> termValues)
		throws PortalException {

		CommerceOrderNote commerceOrderNote =
			_commerceOrderNoteLocalService.getCommerceOrderNote(
				GetterUtil.getLong(termValues.get("id")));

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commerceOrderNote.getCommerceOrderId());

		User user = commerceOrderNote.getUser();

		Role buyerRole = _roleLocalService.getRole(
			user.getCompanyId(), AccountRoleConstants.ROLE_NAME_ACCOUNT_BUYER);

		AccountRole buyerAccountRole =
			_accountRoleLocalService.getAccountRoleByRoleId(
				buyerRole.getRoleId());

		if (_accountRoleLocalService.hasUserAccountRole(
				commerceOrder.getCommerceAccountId(),
				buyerAccountRole.getAccountRoleId(), user.getUserId())) {

			Role orderManagerRole = _roleLocalService.getRole(
				user.getCompanyId(),
				AccountRoleConstants.ROLE_NAME_ACCOUNT_ORDER_MANAGER);

			AccountRole orderManagerAccountRole =
				_accountRoleLocalService.getAccountRoleByRoleId(
					orderManagerRole.getRoleId());

			return _getUserIds(
				commerceOrder.getCommerceAccountId(),
				orderManagerAccountRole.getAccountRoleId(), user.getUserId());
		}

		if (commerceOrderNote.isRestricted() ||
			CommerceChannelConfigurationUtil.isUserNotificationScopeEnabled(
				commerceOrder.getGroupId()) ||
			(commerceOrder.isOpen() &&
			 CommerceOrderConstants.ORDER_VISIBILITY_SCOPE_USER.equals(
				 CommerceChannelConfigurationUtil.
					 getOpenCommerceOrderVisibilityScope(
						 commerceOrder.getGroupId(), true))) ||
			(!commerceOrder.isOpen() &&
			 CommerceOrderConstants.ORDER_VISIBILITY_SCOPE_USER.equals(
				 CommerceChannelConfigurationUtil.
					 getPlacedCommerceOrderVisibilityScope(
						 commerceOrder.getGroupId(), true)))) {

			User commerceOrderUser = _userLocalService.getUser(
				commerceOrder.getUserId());

			if (commerceOrderUser.getUserId() == user.getUserId()) {
				return Collections.emptyList();
			}

			return Collections.singletonList(commerceOrderUser.getUserId());
		}

		return _getUserIds(
			commerceOrder.getCommerceAccountId(),
			buyerAccountRole.getAccountRoleId(), user.getUserId());
	}

	private final AccountEntryUserRelLocalService
		_accountEntryUserRelLocalService;
	private final AccountRoleLocalService _accountRoleLocalService;
	private final CommerceOrderLocalService _commerceOrderLocalService;
	private final CommerceOrderNoteLocalService _commerceOrderNoteLocalService;
	private final ObjectDefinition _objectDefinition;
	private final RoleLocalService _roleLocalService;
	private final UserLocalService _userLocalService;

}