/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.events;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.commerce.configuration.CommerceAccountServiceConfiguration;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.util.CommerceAccountHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Gianmarco Brunialti Masera
 */
@Component(property = "key=login.events.post", service = LifecycleAction.class)
public class LoginPostAction extends Action {

	@Override
	public void run(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			_addDefaultAccountRoles(httpServletRequest);

			Cookie[] cookies = httpServletRequest.getCookies();

			if (cookies == null) {
				return;
			}

			for (Cookie cookie : cookies) {
				String name = cookie.getName();

				if (name.startsWith(
						CommerceOrder.class.getName() + StringPool.POUND)) {

					HttpServletRequest originalHttpServletRequest =
						_portal.getOriginalServletRequest(httpServletRequest);

					HttpSession httpSession =
						originalHttpServletRequest.getSession();

					httpSession.setAttribute(name, cookie.getValue());

					_updateGuestCommerceOrder(
						cookie.getValue(),
						Long.valueOf(
							StringUtil.extractLast(name, StringPool.POUND)),
						httpServletRequest);

					break;
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _addDefaultAccountRoles(HttpServletRequest httpServletRequest)
		throws Exception {

		CommerceAccountServiceConfiguration
			commerceAccountServiceConfiguration =
				_configurationProvider.getSystemConfiguration(
					CommerceAccountServiceConfiguration.class);

		if (commerceAccountServiceConfiguration.
				applyDefaultRoleToExistingUsers()) {

			_commerceAccountHelper.addDefaultRoles(
				_portal.getUserId(httpServletRequest));
		}
	}

	private AccountEntry _getAccountEntry(
		int commerceSiteType, HttpServletRequest httpServletRequest,
		long userId) {

		AccountEntry accountEntry = null;

		try {
			if (commerceSiteType == CommerceChannelConstants.SITE_TYPE_B2B) {
				accountEntry = _getBusinessAccountEntry(userId);
			}
			else if (commerceSiteType ==
						CommerceChannelConstants.SITE_TYPE_B2X) {

				accountEntry = _getBusinessAccountEntry(userId);

				if (accountEntry == null) {
					accountEntry = _getPersonAccountEntry(
						httpServletRequest, userId);
				}
			}
			else {
				accountEntry = _getPersonAccountEntry(
					httpServletRequest, userId);
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return accountEntry;
	}

	private AccountEntry _getBusinessAccountEntry(long userId)
		throws PortalException {

		List<AccountEntry> userAccountEntries =
			_accountEntryLocalService.getUserAccountEntries(
				userId, null, null,
				new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS},
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (userAccountEntries.isEmpty()) {
			return null;
		}

		return userAccountEntries.get(0);
	}

	private AccountEntry _getPersonAccountEntry(
			HttpServletRequest httpServletRequest, long userId)
		throws PortalException {

		AccountEntry accountEntry =
			_accountEntryLocalService.fetchPersonAccountEntry(userId);

		if (accountEntry == null) {
			ServiceContext serviceContext = new ServiceContext();

			User user = _portal.getUser(httpServletRequest);

			serviceContext.setCompanyId(user.getCompanyId());

			serviceContext.setUserId(userId);

			accountEntry = _accountEntryLocalService.addAccountEntry(
				userId, AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
				user.getFullName(), null, null, user.getEmailAddress(), null,
				StringPool.BLANK, AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				accountEntry.getAccountEntryId(), userId);
		}

		return accountEntry;
	}

	private void _updateGuestCommerceOrder(
			String commerceOrderUuid, long commerceChannelGroupId,
			HttpServletRequest httpServletRequest)
		throws Exception {

		CommerceOrder commerceOrder = null;

		try {
			commerceOrder =
				_commerceOrderLocalService.getCommerceOrderByUuidAndGroupId(
					commerceOrderUuid, commerceChannelGroupId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return;
		}

		if (commerceOrder.getCommerceAccountId() !=
				AccountConstants.ACCOUNT_ENTRY_ID_GUEST) {

			return;
		}

		long userId = _portal.getUserId(httpServletRequest);

		AccountEntry accountEntry;

		if (FeatureFlagManagerUtil.isEnabled("LPD-35678")) {
			accountEntry = _getAccountEntry(
				_commerceAccountHelper.getCommerceSiteType(
					commerceChannelGroupId),
				httpServletRequest, userId);
		}
		else {
			accountEntry = _getPersonAccountEntry(httpServletRequest, userId);
		}

		if (accountEntry != null) {
			CommerceOrder userCommerceOrder =
				_commerceOrderLocalService.fetchCommerceOrder(
					accountEntry.getAccountEntryId(), commerceChannelGroupId,
					userId, CommerceOrderConstants.ORDER_STATUS_OPEN);

			if (userCommerceOrder != null) {
				CommerceContext commerceContext =
					_commerceContextFactory.create(
						_portal.getCompanyId(httpServletRequest),
						commerceChannelGroupId, userId,
						userCommerceOrder.getCommerceOrderId(),
						accountEntry.getAccountEntryId());

				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(httpServletRequest);

				PermissionThreadLocal.setPermissionChecker(
					PermissionCheckerFactoryUtil.create(
						_portal.getUser(httpServletRequest)));

				_commerceOrderLocalService.mergeGuestCommerceOrder(
					userId, commerceOrder.getCommerceOrderId(),
					userCommerceOrder.getCommerceOrderId(), commerceContext,
					serviceContext);
			}
			else {
				_commerceOrderLocalService.updateAccount(
					commerceOrder.getCommerceOrderId(), userId,
					accountEntry.getAccountEntryId());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LoginPostAction.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

}