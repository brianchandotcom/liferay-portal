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

package com.liferay.commerce.context;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.account.configuration.CommerceAccountGroupServiceConfiguration;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.currency.exception.NoSuchCurrencyException;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.util.comparator.CommerceCurrencyPriorityComparator;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.util.AccountEntryAllowedTypesUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class BaseCommerceContextHttp implements CommerceContext {

	public BaseCommerceContextHttp(
		HttpServletRequest httpServletRequest,
		CommerceAccountHelper commerceAccountHelper,
		CommerceAccountLocalService commerceAccountLocalService,
		CommerceChannelAccountEntryRelLocalService
			commerceChannelAccountEntryRelLocalService,
		CommerceChannelLocalService commerceChannelLocalService,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		CommerceOrderHttpHelper commerceOrderHttpHelper,
		ConfigurationProvider configurationProvider, Portal portal) {

		_httpServletRequest = httpServletRequest;
		_commerceAccountHelper = commerceAccountHelper;
		_commerceAccountLocalService = commerceAccountLocalService;
		_commerceChannelAccountEntryRelLocalService =
			commerceChannelAccountEntryRelLocalService;
		_commerceChannelLocalService = commerceChannelLocalService;
		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_commerceOrderHttpHelper = commerceOrderHttpHelper;
		_portal = portal;

		try {
			CommerceChannel commerceChannel = _fetchCommerceChannel();

			if (commerceChannel != null) {
				_commerceAccountGroupServiceConfiguration =
					configurationProvider.getConfiguration(
						CommerceAccountGroupServiceConfiguration.class,
						new GroupServiceSettingsLocator(
							commerceChannel.getGroupId(),
							CommerceAccountConstants.SERVICE_NAME));
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	@Override
	public String[] getAccountEntryAllowedTypes() throws PortalException {
		if (_accountEntryAllowedTypes != null) {
			return _accountEntryAllowedTypes;
		}

		_accountEntryAllowedTypes =
			AccountEntryAllowedTypesUtil.getAllowedTypes(getCommerceSiteType());

		return _accountEntryAllowedTypes;
	}

	@Override
	public CommerceAccount getCommerceAccount() throws PortalException {
		CommerceChannel commerceChannel = _fetchCommerceChannel();

		if (commerceChannel == null) {
			return _commerceAccount;
		}

		AccountEntry accountEntry =
			_commerceAccountHelper.getCurrentAccountEntry(
				commerceChannel.getGroupId(), _httpServletRequest);

		if (accountEntry == null) {
			return null;
		}
		else if (accountEntry.isGuestAccount()) {
			return _commerceAccountLocalService.getGuestCommerceAccount(
				commerceChannel.getCompanyId());
		}

		_commerceAccount = _commerceAccountLocalService.getCommerceAccount(
			accountEntry.getAccountEntryId());

		return _commerceAccount;
	}

	@Override
	public long[] getCommerceAccountGroupIds() throws PortalException {
		if (_commerceAccountGroupIds != null) {
			return _commerceAccountGroupIds.clone();
		}

		CommerceAccount commerceAccount = getCommerceAccount();

		if (commerceAccount == null) {
			return new long[0];
		}

		_commerceAccountGroupIds =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				commerceAccount.getCommerceAccountId());

		return _commerceAccountGroupIds.clone();
	}

	@Override
	public long getCommerceChannelGroupId() throws PortalException {
		return _commerceChannelLocalService.
			getCommerceChannelGroupIdBySiteGroupId(
				_portal.getScopeGroupId(_httpServletRequest));
	}

	@Override
	public long getCommerceChannelId() throws PortalException {
		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				_portal.getScopeGroupId(_httpServletRequest));

		if (commerceChannel == null) {
			return 0;
		}

		return commerceChannel.getCommerceChannelId();
	}

	@Override
	public CommerceCurrency getCommerceCurrency() throws PortalException {
		if (_commerceCurrency != null) {
			return _commerceCurrency;
		}

		long commerceChannelId = 0;

		CommerceChannel commerceChannel = _fetchCommerceChannel();

		if (commerceChannel != null) {
			commerceChannelId = commerceChannel.getCommerceChannelId();
		}

		CommerceAccount commerceAccount = getCommerceAccount();

		if (commerceAccount != null) {
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
				_commerceChannelAccountEntryRelLocalService.
					fetchCommerceChannelAccountEntryRel(
						commerceAccount.getCommerceAccountId(),
						commerceChannelId,
						CommerceChannelAccountEntryRelConstants.TYPE_CURRENCY);

			if (commerceChannelAccountEntryRel != null) {
				CommerceCurrency commerceCurrency =
					_commerceCurrencyLocalService.getCommerceCurrency(
						commerceChannelAccountEntryRel.getClassPK());

				if (commerceCurrency.isActive()) {
					_commerceCurrency = commerceCurrency;

					return _commerceCurrency;
				}
			}
		}

		if (commerceChannel == null) {
			_commerceCurrency =
				_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
					_portal.getCompanyId(_httpServletRequest));
		}
		else {
			_commerceCurrency = _getCommerceCurrency(
				_portal.getCompanyId(_httpServletRequest),
				commerceChannel.getCommerceCurrencyCode());
		}

		return _commerceCurrency;
	}

	@Override
	public CommerceOrder getCommerceOrder() {
		try {
			_commerceOrder = _commerceOrderHttpHelper.getCurrentCommerceOrder(
				_httpServletRequest);

			return _commerceOrder;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	@Override
	public int getCommerceSiteType() {
		if (_commerceAccountGroupServiceConfiguration == null) {
			return CommerceAccountConstants.SITE_TYPE_B2C;
		}

		return _commerceAccountGroupServiceConfiguration.commerceSiteType();
	}

	private CommerceChannel _fetchCommerceChannel() throws PortalException {
		return _commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
			_portal.getScopeGroupId(_httpServletRequest));
	}

	private CommerceCurrency _getCommerceCurrency(
		long companyId, String currencyCode) {

		CommerceCurrency commerceCurrency = null;

		try {
			commerceCurrency =
				_commerceCurrencyLocalService.getCommerceCurrency(
					companyId, currencyCode);
		}
		catch (NoSuchCurrencyException noSuchCurrencyException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchCurrencyException);
			}
		}

		if ((commerceCurrency != null) && commerceCurrency.isActive()) {
			return commerceCurrency;
		}

		commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				companyId);

		if (commerceCurrency != null) {
			return commerceCurrency;
		}

		List<CommerceCurrency> commerceCurrencies =
			_commerceCurrencyLocalService.getCommerceCurrencies(
				companyId, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new CommerceCurrencyPriorityComparator(true));

		if (!commerceCurrencies.isEmpty()) {
			commerceCurrency = commerceCurrencies.get(0);
		}

		return commerceCurrency;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCommerceContextHttp.class);

	private String[] _accountEntryAllowedTypes;
	private CommerceAccount _commerceAccount;
	private long[] _commerceAccountGroupIds;
	private CommerceAccountGroupServiceConfiguration
		_commerceAccountGroupServiceConfiguration;
	private final CommerceAccountHelper _commerceAccountHelper;
	private final CommerceAccountLocalService _commerceAccountLocalService;
	private final CommerceChannelAccountEntryRelLocalService
		_commerceChannelAccountEntryRelLocalService;
	private final CommerceChannelLocalService _commerceChannelLocalService;
	private CommerceCurrency _commerceCurrency;
	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private CommerceOrder _commerceOrder;
	private final CommerceOrderHttpHelper _commerceOrderHttpHelper;
	private final HttpServletRequest _httpServletRequest;
	private final Portal _portal;

}