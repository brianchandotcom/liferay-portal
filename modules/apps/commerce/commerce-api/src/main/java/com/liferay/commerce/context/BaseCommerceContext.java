/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.context;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.commerce.configuration.CommerceAccountGroupServiceConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.currency.exception.NoSuchCurrencyException;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.util.comparator.CommerceCurrencyPriorityComparator;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.discovery.CPConfigurationListDiscovery;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.AccountEntryAllowedTypesUtil;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class BaseCommerceContext implements CommerceContext {

	public BaseCommerceContext(
		long companyId, long commerceChannelGroupId, long orderId,
		long commerceAccountId,
		AccountEntryLocalService accountEntryLocalService,
		AccountGroupLocalService accountGroupLocalService,
		CommerceCatalogLocalService commerceCatalogLocalService,
		CommerceChannelAccountEntryRelLocalService
			commerceChannelAccountEntryRelLocalService,
		CommerceChannelLocalService commerceChannelLocalService,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		CommerceOrderService commerceOrderService,
		ConfigurationProvider configurationProvider,
		CPConfigurationListDiscovery cpConfigurationListDiscovery) {

		_companyId = companyId;
		_commerceChannelGroupId = commerceChannelGroupId;
		_orderId = orderId;
		_commerceAccountId = commerceAccountId;
		_accountEntryLocalService = accountEntryLocalService;
		_accountGroupLocalService = accountGroupLocalService;
		_commerceCatalogLocalService = commerceCatalogLocalService;
		_commerceChannelAccountEntryRelLocalService =
			commerceChannelAccountEntryRelLocalService;
		_commerceChannelLocalService = commerceChannelLocalService;
		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_commerceOrderService = commerceOrderService;
		_cpConfigurationListDiscovery = cpConfigurationListDiscovery;

		try {
			if (getCommerceChannelGroupId() > 0) {
				_commerceAccountGroupServiceConfiguration =
					configurationProvider.getConfiguration(
						CommerceAccountGroupServiceConfiguration.class,
						new GroupServiceSettingsLocator(
							_commerceChannelGroupId,
							CommerceConstants.SERVICE_NAME_COMMERCE_ACCOUNT));
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	@Override
	public AccountEntry getAccountEntry() throws PortalException {
		if (_accountEntry != null) {
			return _accountEntry;
		}

		if (_commerceAccountId <= 0) {
			return _accountEntryLocalService.getGuestAccountEntry(_companyId);
		}

		_accountEntry = _accountEntryLocalService.getAccountEntry(
			_commerceAccountId);

		return _accountEntry;
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
	public long[] getCommerceAccountGroupIds() throws PortalException {
		if (_commerceAccountGroupIds != null) {
			return _commerceAccountGroupIds.clone();
		}

		AccountEntry accountEntry = getAccountEntry();

		if (accountEntry == null) {
			return new long[0];
		}

		_commerceAccountGroupIds = _accountGroupLocalService.getAccountGroupIds(
			accountEntry.getAccountEntryId());

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

		AccountEntry accountEntry = getAccountEntry();

		if (accountEntry != null) {
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
				_commerceChannelAccountEntryRelLocalService.
					fetchCommerceChannelAccountEntryRel(
						accountEntry.getAccountEntryId(),
						commerceChannel.getCommerceChannelId(),
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

		_commerceCurrency = _getCommerceCurrency(
			_companyId, commerceChannel.getCommerceCurrencyCode());

		return _commerceCurrency;
	}

	@Override
	public CommerceOrder getCommerceOrder() {
		try {
			_commerceOrder = _commerceOrderService.fetchCommerceOrder(_orderId);

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
			return CommerceChannelConstants.SITE_TYPE_B2C;
		}

		return _commerceAccountGroupServiceConfiguration.commerceSiteType();
	}

	@Override
	public long getCPConfigurationListId(long groupId) throws PortalException {
		if (!FeatureFlagManagerUtil.isEnabled("LPD-10889")) {
			return 0;
		}

		Map<Long, CPConfigurationList> cpConfigurationListMap =
			_getCPConfigurationListMap();

		CPConfigurationList cpConfigurationList = cpConfigurationListMap.get(
			groupId);

		return cpConfigurationList.getCPConfigurationListId();
	}

	@Override
	public long[] getCPConfigurationListIds() throws PortalException {
		Map<Long, CPConfigurationList> cpConfigurationListMap =
			_getCPConfigurationListMap();

		return TransformUtil.transformToLongArray(
			cpConfigurationListMap.values(),
			CPConfigurationList::getCPConfigurationListId);
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
				CommerceCurrencyPriorityComparator.getInstance(true));

		if (!commerceCurrencies.isEmpty()) {
			commerceCurrency = commerceCurrencies.get(0);
		}

		return commerceCurrency;
	}

	private Map<Long, CPConfigurationList> _getCPConfigurationListMap()
		throws PortalException {

		if (MapUtil.isNotEmpty(_cpConfigurationListMap)) {
			return _cpConfigurationListMap;
		}

		_cpConfigurationListMap = new HashMap<>();

		long orderTypeId = 0;

		CommerceOrder commerceOrder = getCommerceOrder();

		if (commerceOrder != null) {
			orderTypeId = commerceOrder.getCommerceOrderTypeId();
		}

		for (long groupId :
				TransformUtil.transformToLongArray(
					_commerceCatalogLocalService.getCommerceCatalogs(
						_companyId),
					CommerceCatalog::getGroupId)) {

			_cpConfigurationListMap.put(
				groupId,
				_cpConfigurationListDiscovery.getCPConfigurationList(
					_companyId, groupId,
					CommerceUtil.getCommerceAccountId(this),
					getCommerceChannelId(), orderTypeId));
		}

		return _cpConfigurationListMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCommerceContext.class);

	private AccountEntry _accountEntry;
	private String[] _accountEntryAllowedTypes;
	private final AccountEntryLocalService _accountEntryLocalService;
	private final AccountGroupLocalService _accountGroupLocalService;
	private long[] _commerceAccountGroupIds;
	private CommerceAccountGroupServiceConfiguration
		_commerceAccountGroupServiceConfiguration;
	private final long _commerceAccountId;
	private final CommerceCatalogLocalService _commerceCatalogLocalService;
	private final CommerceChannelAccountEntryRelLocalService
		_commerceChannelAccountEntryRelLocalService;
	private final long _commerceChannelGroupId;
	private final CommerceChannelLocalService _commerceChannelLocalService;
	private CommerceCurrency _commerceCurrency;
	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private CommerceOrder _commerceOrder;
	private final CommerceOrderService _commerceOrderService;
	private final long _companyId;
	private final CPConfigurationListDiscovery _cpConfigurationListDiscovery;
	private Map<Long, CPConfigurationList> _cpConfigurationListMap;
	private final long _orderId;

}