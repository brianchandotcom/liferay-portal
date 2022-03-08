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

package com.liferay.commerce.account.internal.util;

import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.manager.CurrentAccountEntryManager;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.commerce.account.configuration.CommerceAccountGroupServiceConfiguration;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountModel;
import com.liferay.commerce.account.model.impl.CommerceAccountImpl;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = CommerceAccountHelper.class
)
public class CommerceAccountHelperImpl implements CommerceAccountHelper {

	@Override
	public int countUserCommerceAccounts(
			long userId, long commerceChannelGroupId)
		throws PortalException {

		return _commerceAccountLocalService.getUserCommerceAccountsCount(
			userId, CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
			_getCommerceSiteType(commerceChannelGroupId), StringPool.BLANK);
	}

	@Override
	public String getAccountManagementPortletURL(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		long plid = _portal.getPlidFromPortletId(
			groupId, AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT);

		if (plid > 0) {
			PortletURL portletURL = _portletURLFactory.create(
				httpServletRequest,
				AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT, plid,
				PortletRequest.RENDER_PHASE);

			return portletURL.toString();
		}

		return StringPool.BLANK;
	}

	@Override
	public long[] getCommerceAccountGroupIds(long commerceAccountId) {
		List<AccountGroupRel> accountGroupRels =
			_accountGroupRelLocalService.getAccountGroupRels(
				AccountEntry.class.getName(), commerceAccountId);

		if (accountGroupRels.isEmpty()) {
			return new long[0];
		}

		Stream<AccountGroupRel> stream = accountGroupRels.stream();

		long[] accountGroupIds = stream.mapToLong(
			AccountGroupRel::getAccountGroupId
		).toArray();

		accountGroupIds = ArrayUtil.unique(accountGroupIds);

		Arrays.sort(accountGroupIds);

		return accountGroupIds;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), you must pass commerceChannelGroupId
	 */
	@Deprecated
	@Override
	public CommerceAccount getCurrentCommerceAccount(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return getCurrentCommerceAccount(
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				_portal.getScopeGroupId(httpServletRequest)),
			httpServletRequest);
	}

	@Override
	public CommerceAccount getCurrentCommerceAccount(
			long commerceChannelGroupId, HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByGroupId(
				commerceChannelGroupId);

		CommerceAccount commerceAccount = CommerceAccountImpl.fromAccountEntry(
			_currentAccountEntryManager.getCurrentAccountEntry(
				commerceChannel.getSiteGroupId(),
				_portal.getUserId(httpServletRequest)));

		if ((commerceAccount == null) || !commerceAccount.isActive()) {
			commerceAccount = _getSingleCommerceAccount(
				commerceChannelGroupId, httpServletRequest);

			if (commerceAccount == null) {
				setCurrentCommerceAccount(
					httpServletRequest, commerceChannelGroupId,
					CommerceAccountConstants.ACCOUNT_ID_GUEST);
			}
			else {
				setCurrentCommerceAccount(
					httpServletRequest, commerceChannelGroupId,
					commerceAccount.getCommerceAccountId());
			}
		}

		return commerceAccount;
	}

	@Override
	public long[] getUserCommerceAccountIds(
			long userId, long commerceChannelGroupId)
		throws PortalException {

		List<CommerceAccount> commerceAccounts =
			_commerceAccountLocalService.getUserCommerceAccounts(
				userId, CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				_getCommerceSiteType(commerceChannelGroupId), StringPool.BLANK,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		return ListUtil.toLongArray(
			commerceAccounts, CommerceAccountModel::getCommerceAccountId);
	}

	@Override
	public void setCurrentCommerceAccount(
			HttpServletRequest httpServletRequest, long commerceChannelGroupId,
			long commerceAccountId)
		throws PortalException {

		if (commerceAccountId > 0) {
			_checkAccountType(commerceChannelGroupId, commerceAccountId);
		}

		if (PortalSessionThreadLocal.getHttpSession() == null) {
			PortalSessionThreadLocal.setHttpSession(
				httpServletRequest.getSession());
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByGroupId(
				commerceChannelGroupId);

		long userId = _portal.getUserId(httpServletRequest);

		_currentAccountEntryManager.setCurrentAccountEntry(
			commerceAccountId, commerceChannel.getGroupId(), userId);
		_currentAccountEntryManager.setCurrentAccountEntry(
			commerceAccountId, commerceChannel.getSiteGroupId(), userId);
	}

	private void _checkAccountType(
			long commerceChannelGroupId, long commerceAccountId)
		throws PortalException {

		int commerceSiteType = _getCommerceSiteType(commerceChannelGroupId);

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.getCommerceAccount(commerceAccountId);

		if ((commerceSiteType == CommerceAccountConstants.SITE_TYPE_B2C) &&
			commerceAccount.isBusinessAccount()) {

			throw new PortalException(
				"Only personal accounts are allowed in a b2c site");
		}

		if ((commerceSiteType == CommerceAccountConstants.SITE_TYPE_B2B) &&
			commerceAccount.isPersonalAccount()) {

			throw new PortalException(
				"Only business accounts are allowed in a b2b site");
		}
	}

	private int _getCommerceSiteType(long commerceChannelGroupId)
		throws ConfigurationException {

		CommerceAccountGroupServiceConfiguration
			commerceAccountGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					CommerceAccountGroupServiceConfiguration.class,
					new GroupServiceSettingsLocator(
						commerceChannelGroupId,
						CommerceAccountConstants.SERVICE_NAME));

		return commerceAccountGroupServiceConfiguration.commerceSiteType();
	}

	private CommerceAccount _getSingleCommerceAccount(
			long commerceChannelGroupId, HttpServletRequest httpServletRequest)
		throws PortalException {

		User user = _portal.getUser(httpServletRequest);

		if ((user == null) || user.isDefaultUser()) {
			return _commerceAccountLocalService.getGuestCommerceAccount(
				_portal.getCompanyId(httpServletRequest));
		}

		int commerceSiteType = _getCommerceSiteType(commerceChannelGroupId);

		if ((commerceSiteType == CommerceAccountConstants.SITE_TYPE_B2C) ||
			(commerceSiteType == CommerceAccountConstants.SITE_TYPE_B2X)) {

			return _commerceAccountService.getPersonalCommerceAccount(
				user.getUserId());
		}

		List<CommerceAccount> userCommerceAccounts =
			_commerceAccountService.getUserCommerceAccounts(
				user.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				commerceSiteType, StringPool.BLANK, true, 0, 1);

		if (userCommerceAccounts.size() == 1) {
			return userCommerceAccounts.get(0);
		}

		return null;
	}

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Reference
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CurrentAccountEntryManager _currentAccountEntryManager;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}