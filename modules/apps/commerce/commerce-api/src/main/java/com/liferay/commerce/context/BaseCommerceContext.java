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

import com.liferay.commerce.account.configuration.CommerceAccountGroupServiceConfiguration;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.AccountEntryAllowedTypesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class BaseCommerceContext implements CommerceContext {

	public BaseCommerceContext(
		long companyId, long commerceChannelGroupId, long orderId,
		long commerceAccountId, CommerceAccountHelper commerceAccountHelper,
		CommerceAccountLocalService commerceAccountLocalService,
		CommerceAccountService commerceAccountService,
		CommerceChannelLocalService commerceChannelLocalService,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		CommerceOrderService commerceOrderService,
		ConfigurationProvider configurationProvider) {

		_companyId = companyId;
		_commerceChannelGroupId = commerceChannelGroupId;
		_orderId = orderId;
		_commerceAccountId = commerceAccountId;
		_commerceAccountHelper = commerceAccountHelper;
		_commerceAccountLocalService = commerceAccountLocalService;
		_commerceAccountService = commerceAccountService;
		_commerceChannelLocalService = commerceChannelLocalService;
		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_commerceOrderService = commerceOrderService;

		try {
			if (getCommerceChannelGroupId() > 0) {
				_commerceAccountGroupServiceConfiguration =
					configurationProvider.getConfiguration(
						CommerceAccountGroupServiceConfiguration.class,
						new GroupServiceSettingsLocator(
							_commerceChannelGroupId,
							CommerceAccountConstants.SERVICE_NAME));
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
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
		if (_commerceAccount != null) {
			return _commerceAccount;
		}

		if (_commerceAccountId == CommerceAccountConstants.ACCOUNT_ID_GUEST) {
			return _commerceAccountLocalService.getGuestCommerceAccount(
				_companyId);
		}

		_commerceAccount = _commerceAccountService.getCommerceAccount(
			_commerceAccountId);

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
		return _commerceChannelGroupId;
	}

	@Override
	public long getCommerceChannelId() throws PortalException {
		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByGroupId(
				_commerceChannelGroupId);

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

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByGroupId(
				_commerceChannelGroupId);

		_commerceCurrency = _commerceCurrencyLocalService.getCommerceCurrency(
			_companyId, commerceChannel.getCommerceCurrencyCode());

		return _commerceCurrency;
	}

	@Override
	public CommerceOrder getCommerceOrder() throws PortalException {
		_commerceOrder = _commerceOrderService.fetchCommerceOrder(_orderId);

		return _commerceOrder;
	}

	@Override
	public int getCommerceSiteType() {
		if (_commerceAccountGroupServiceConfiguration == null) {
			return CommerceAccountConstants.SITE_TYPE_B2C;
		}

		return _commerceAccountGroupServiceConfiguration.commerceSiteType();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCommerceContext.class);

	private String[] _accountEntryAllowedTypes;
	private CommerceAccount _commerceAccount;
	private long[] _commerceAccountGroupIds;
	private CommerceAccountGroupServiceConfiguration
		_commerceAccountGroupServiceConfiguration;
	private final CommerceAccountHelper _commerceAccountHelper;
	private final long _commerceAccountId;
	private final CommerceAccountLocalService _commerceAccountLocalService;
	private final CommerceAccountService _commerceAccountService;
	private final long _commerceChannelGroupId;
	private final CommerceChannelLocalService _commerceChannelLocalService;
	private CommerceCurrency _commerceCurrency;
	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private CommerceOrder _commerceOrder;
	private final CommerceOrderService _commerceOrderService;
	private final long _companyId;
	private final long _orderId;

}