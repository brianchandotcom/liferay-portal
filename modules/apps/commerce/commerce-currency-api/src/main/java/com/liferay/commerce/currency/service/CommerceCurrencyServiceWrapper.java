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

package com.liferay.commerce.currency.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceCurrencyService}.
 *
 * @author Andrea Di Giorgi
 * @see CommerceCurrencyService
 * @generated
 */
public class CommerceCurrencyServiceWrapper
	implements CommerceCurrencyService,
			   ServiceWrapper<CommerceCurrencyService> {

	public CommerceCurrencyServiceWrapper(
		CommerceCurrencyService commerceCurrencyService) {

		_commerceCurrencyService = commerceCurrencyService;
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceCurrency
			addCommerceCurrency(
				String code, java.util.Map<java.util.Locale, String> nameMap,
				String symbol, java.math.BigDecimal rate,
				java.util.Map<java.util.Locale, String> formatPatternMap,
				int maxFractionDigits, int minFractionDigits,
				String roundingMode, boolean primary, double priority,
				boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCurrencyService.addCommerceCurrency(
			code, nameMap, symbol, rate, formatPatternMap, maxFractionDigits,
			minFractionDigits, roundingMode, primary, priority, active);
	}

	@Override
	public void deleteCommerceCurrency(long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceCurrencyService.deleteCommerceCurrency(commerceCurrencyId);
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceCurrency
			fetchPrimaryCommerceCurrency(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCurrencyService.fetchPrimaryCommerceCurrency(companyId);
	}

	@Override
	public java.util.List<com.liferay.commerce.currency.model.CommerceCurrency>
			getCommerceCurrencies(
				long companyId, boolean active, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.currency.model.CommerceCurrency>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCurrencyService.getCommerceCurrencies(
			companyId, active, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.currency.model.CommerceCurrency>
			getCommerceCurrencies(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.currency.model.CommerceCurrency>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCurrencyService.getCommerceCurrencies(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceCurrenciesCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCurrencyService.getCommerceCurrenciesCount(companyId);
	}

	@Override
	public int getCommerceCurrenciesCount(long companyId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCurrencyService.getCommerceCurrenciesCount(
			companyId, active);
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceCurrency
			getCommerceCurrency(long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCurrencyService.getCommerceCurrency(commerceCurrencyId);
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceCurrency
			getCommerceCurrency(long companyId, String code)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCurrencyService.getCommerceCurrency(companyId, code);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceCurrencyService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceCurrency setActive(
			long commerceCurrencyId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCurrencyService.setActive(commerceCurrencyId, active);
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceCurrency setPrimary(
			long commerceCurrencyId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCurrencyService.setPrimary(commerceCurrencyId, primary);
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceCurrency
			updateCommerceCurrency(
				long commerceCurrencyId, String code,
				java.util.Map<java.util.Locale, String> nameMap, String symbol,
				java.math.BigDecimal rate,
				java.util.Map<java.util.Locale, String> formatPatternMap,
				int maxFractionDigits, int minFractionDigits,
				String roundingMode, boolean primary, double priority,
				boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCurrencyService.updateCommerceCurrency(
			commerceCurrencyId, code, nameMap, symbol, rate, formatPatternMap,
			maxFractionDigits, minFractionDigits, roundingMode, primary,
			priority, active, serviceContext);
	}

	@Override
	public void updateExchangeRate(
			long commerceCurrencyId, String exchangeRateProviderKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceCurrencyService.updateExchangeRate(
			commerceCurrencyId, exchangeRateProviderKey);
	}

	@Override
	public void updateExchangeRates()
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceCurrencyService.updateExchangeRates();
	}

	@Override
	public CommerceCurrencyService getWrappedService() {
		return _commerceCurrencyService;
	}

	@Override
	public void setWrappedService(
		CommerceCurrencyService commerceCurrencyService) {

		_commerceCurrencyService = commerceCurrencyService;
	}

	private CommerceCurrencyService _commerceCurrencyService;

}