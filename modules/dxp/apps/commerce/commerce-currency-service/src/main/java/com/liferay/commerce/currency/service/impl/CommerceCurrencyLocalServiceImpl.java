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

package com.liferay.commerce.currency.service.impl;

import com.liferay.commerce.currency.configuration.ExchangeRateProviderGroupServiceConfiguration;
import com.liferay.commerce.currency.constants.CommerceCurrencyExchangeRateConstants;
import com.liferay.commerce.currency.exception.CommerceCurrencyCodeException;
import com.liferay.commerce.currency.exception.CommerceCurrencyNameException;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.base.CommerceCurrencyLocalServiceBaseImpl;
import com.liferay.commerce.currency.util.ExchangeRateProvider;
import com.liferay.commerce.currency.util.ExchangeRateProviderRegistry;
import com.liferay.commerce.currency.util.comparator.CommerceCurrencyPriorityComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceCurrencyLocalServiceImpl
	extends CommerceCurrencyLocalServiceBaseImpl {

	@Override
	public CommerceCurrency addCommerceCurrency(
			String code, Map<Locale, String> nameMap, BigDecimal rate,
			String roundingType, boolean primary, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		if (primary) {
			rate = BigDecimal.ONE;
		}

		validate(0, groupId, code, nameMap, primary);

		long commerceCurrencyId = counterLocalService.increment();

		CommerceCurrency commerceCurrency = commerceCurrencyPersistence.create(
			commerceCurrencyId);

		commerceCurrency.setUuid(serviceContext.getUuid());
		commerceCurrency.setGroupId(groupId);
		commerceCurrency.setCompanyId(user.getCompanyId());
		commerceCurrency.setUserId(user.getUserId());
		commerceCurrency.setUserName(user.getFullName());
		commerceCurrency.setCode(code);
		commerceCurrency.setNameMap(nameMap);
		commerceCurrency.setRate(rate);
		commerceCurrency.setRoundingType(roundingType);
		commerceCurrency.setPrimary(primary);
		commerceCurrency.setPriority(priority);
		commerceCurrency.setActive(active);

		commerceCurrencyPersistence.update(commerceCurrency);

		return commerceCurrency;
	}

	@Override
	public void deleteCommerceCurrencies(long groupId) {
		commerceCurrencyPersistence.removeByGroupId(groupId);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceCurrency deleteCommerceCurrency(
		CommerceCurrency commerceCurrency) {

		return commerceCurrencyPersistence.remove(commerceCurrency);
	}

	@Override
	public CommerceCurrency deleteCommerceCurrency(long commerceCurrencyId)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			commerceCurrencyPersistence.findByPrimaryKey(commerceCurrencyId);

		return commerceCurrencyLocalService.deleteCommerceCurrency(
			commerceCurrency);
	}

	@Override
	public CommerceCurrency fetchPrimaryCommerceCurrency(long groupId) {
		return commerceCurrencyPersistence.fetchByG_P_A_First(
			groupId, true, true, new CommerceCurrencyPriorityComparator());
	}

	@Override
	public List<CommerceCurrency> getCommerceCurrencies(
		long groupId, boolean active) {

		return commerceCurrencyPersistence.findByG_A(groupId, active);
	}

	@Override
	public List<CommerceCurrency> getCommerceCurrencies(
		long groupId, boolean active, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return commerceCurrencyPersistence.findByG_A(
			groupId, active, start, end, orderByComparator);
	}

	@Override
	public List<CommerceCurrency> getCommerceCurrencies(
		long groupId, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {

		return commerceCurrencyPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceCurrenciesCount(long groupId) {
		return commerceCurrencyPersistence.countByGroupId(groupId);
	}

	@Override
	public int getCommerceCurrenciesCount(long groupId, boolean active) {
		return commerceCurrencyPersistence.countByG_A(groupId, active);
	}

	@Override
	public void importDefaultValues(ServiceContext serviceContext)
		throws Exception {

		Class<?> clazz = getClass();

		String currenciesPath =
			"com/liferay/commerce/currency/service/impl/dependencies" +
				"/currencies.json";

		String countriesJSON = StringUtil.read(
			clazz.getClassLoader(), currenciesPath, false);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(countriesJSON);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String code = jsonObject.getString("code");
			String name = jsonObject.getString("name");
			boolean primary = jsonObject.getBoolean("primary");
			double priority = jsonObject.getDouble("priority");

			Map<Locale, String> nameMap = new HashMap<>();

			nameMap.put(serviceContext.getLocale(), name);

			addCommerceCurrency(
				code, nameMap, BigDecimal.ONE, StringPool.BLANK, primary,
				priority, true, serviceContext);
		}

		for (String exchangeRateProviderKey :
				_exchangeRateProviderRegistry.getExchangeRateProviderKeys()) {

			_updateExchangeRates(
				serviceContext.getScopeGroupId(), exchangeRateProviderKey);

			break;
		}
	}

	@Override
	public CommerceCurrency setActive(long commerceCurrencyId, boolean active)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			commerceCurrencyPersistence.findByPrimaryKey(commerceCurrencyId);

		commerceCurrency.setActive(active);

		commerceCurrencyPersistence.update(commerceCurrency);

		return commerceCurrency;
	}

	@Override
	public CommerceCurrency setPrimary(long commerceCurrencyId, boolean primary)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			commerceCurrencyPersistence.findByPrimaryKey(commerceCurrencyId);

		validate(
			commerceCurrencyId, commerceCurrency.getGroupId(),
			commerceCurrency.getCode(), commerceCurrency.getNameMap(), primary);

		commerceCurrency.setPrimary(primary);

		commerceCurrencyPersistence.update(commerceCurrency);

		return commerceCurrency;
	}

	@Override
	public CommerceCurrency updateCommerceCurrency(
			long commerceCurrencyId, String code, Map<Locale, String> nameMap,
			BigDecimal rate, String roundingType, boolean primary,
			double priority, boolean active, ServiceContext serviceContext)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			commerceCurrencyPersistence.findByPrimaryKey(commerceCurrencyId);

		if (primary) {
			rate = BigDecimal.ONE;
		}

		validate(
			commerceCurrency.getCommerceCurrencyId(),
			commerceCurrency.getGroupId(), code, nameMap, primary);

		commerceCurrency.setCode(code);
		commerceCurrency.setNameMap(nameMap);
		commerceCurrency.setRate(rate);
		commerceCurrency.setRoundingType(roundingType);
		commerceCurrency.setPrimary(primary);
		commerceCurrency.setPriority(priority);
		commerceCurrency.setActive(active);

		commerceCurrencyPersistence.update(commerceCurrency);

		return commerceCurrency;
	}

	@Override
	public CommerceCurrency updateCommerceCurrencyRate(
			long commerceCurrencyId, BigDecimal rate)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			commerceCurrencyPersistence.findByPrimaryKey(commerceCurrencyId);

		commerceCurrency.setRate(rate);

		return commerceCurrencyPersistence.update(commerceCurrency);
	}

	@Override
	public void updateExchangeRate(
			long commerceCurrencyId, String exchangeRateProviderKey)
		throws PortalException {

		ExchangeRateProvider exchangeRateProvider =
			_exchangeRateProviderRegistry.getExchangeRateProvider(
				exchangeRateProviderKey);

		if (exchangeRateProvider == null) {
			return;
		}

		CommerceCurrency commerceCurrency =
			commerceCurrencyPersistence.findByPrimaryKey(commerceCurrencyId);

		CommerceCurrency primaryCommerceCurrency = fetchPrimaryCommerceCurrency(
			commerceCurrency.getGroupId());

		if (primaryCommerceCurrency == null) {
			return;
		}

		BigDecimal exchangeRate = BigDecimal.ZERO;

		try {
			exchangeRate = exchangeRateProvider.getExchangeRate(
				primaryCommerceCurrency, commerceCurrency);
		}
		catch (Exception e) {
			_log.error(e, e);

			return;
		}

		commerceCurrency.setRate(exchangeRate);

		updateCommerceCurrency(commerceCurrency);
	}

	@Override
	public void updateExchangeRates() throws PortalException {
		long[] groupIds = ArrayUtil.toLongArray(
			commerceCurrencyFinder.getGroupIds());

		for (long groupId : groupIds) {
			ExchangeRateProviderGroupServiceConfiguration
				exchangeRateProviderGroupServiceConfiguration =
					_configurationProvider.getConfiguration(
						ExchangeRateProviderGroupServiceConfiguration.class,
						new GroupServiceSettingsLocator(
							groupId,
							CommerceCurrencyExchangeRateConstants.
								SERVICE_NAME));

			if (exchangeRateProviderGroupServiceConfiguration.autoUpdate()) {
				String groupDefaultExchangeRateProviderKey =
					exchangeRateProviderGroupServiceConfiguration.
						defaultExchangeRateProviderKey();

				_updateExchangeRates(
					groupId, groupDefaultExchangeRateProviderKey);
			}
		}
	}

	protected void validate(
			long commerceCurrencyId, long groupId, String code,
			Map<Locale, String> nameMap, boolean primary)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		if (Validator.isNull(code)) {
			throw new CommerceCurrencyCodeException();
		}

		String name = nameMap.get(locale);

		if (Validator.isNull(name)) {
			throw new CommerceCurrencyNameException();
		}

		if (primary) {
			List<CommerceCurrency> commerceCurrencies =
				commerceCurrencyPersistence.findByG_P(groupId, primary);

			for (CommerceCurrency commerceCurrency : commerceCurrencies) {
				if (commerceCurrency.getCommerceCurrencyId() !=
						commerceCurrencyId) {

					commerceCurrency.setPrimary(false);

					commerceCurrencyPersistence.update(commerceCurrency);
				}
			}
		}
	}

	private void _updateExchangeRates(
			long groupId, String exchangeRateProviderKey)
		throws PortalException {

		List<CommerceCurrency> commerceCurrencies = getCommerceCurrencies(
			groupId, true);

		for (CommerceCurrency commerceCurrency : commerceCurrencies) {
			updateExchangeRate(
				commerceCurrency.getCommerceCurrencyId(),
				exchangeRateProviderKey);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCurrencyLocalServiceImpl.class);

	@ServiceReference(type = ConfigurationProvider.class)
	private ConfigurationProvider _configurationProvider;

	@ServiceReference(type = ExchangeRateProviderRegistry.class)
	private ExchangeRateProviderRegistry _exchangeRateProviderRegistry;

}