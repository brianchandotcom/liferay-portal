/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.service.impl;

import com.liferay.commerce.currency.constants.CommerceCurrencyActionKeys;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceCurrencyConstants;
import com.liferay.commerce.currency.service.base.CommerceCurrencyServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceCurrency"
	},
	service = AopService.class
)
public class CommerceCurrencyServiceImpl
	extends CommerceCurrencyServiceBaseImpl {

	@Override
	public CommerceCurrency addCommerceCurrency(
			String code, Map<Locale, String> nameMap, String symbol,
			BigDecimal rate, Map<Locale, String> formatPatternMap,
			int maxFractionDigits, int minFractionDigits, String roundingMode,
			boolean primary, double priority, boolean active)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.addCommerceCurrency(
			getUserId(), code, nameMap, symbol, rate, formatPatternMap,
			maxFractionDigits, minFractionDigits, roundingMode, primary,
			priority, active);
	}

	@Override
	public void deleteCommerceCurrency(long commerceCurrencyId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		commerceCurrencyLocalService.deleteCommerceCurrency(commerceCurrencyId);
	}

	@Override
	public CommerceCurrency fetchPrimaryCommerceCurrency(long companyId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
			companyId);
	}

	@Override
	public List<CommerceCurrency> getCommerceCurrencies(
			long companyId, boolean active, int start, int end,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.getCommerceCurrencies(
			companyId, active, start, end, orderByComparator);
	}

	@Override
	public List<CommerceCurrency> getCommerceCurrencies(
			long companyId, int start, int end,
			OrderByComparator<CommerceCurrency> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.getCommerceCurrencies(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceCurrenciesCount(long companyId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.getCommerceCurrenciesCount(
			companyId);
	}

	@Override
	public int getCommerceCurrenciesCount(long companyId, boolean active)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.getCommerceCurrenciesCount(
			companyId, active);
	}

	@Override
	public CommerceCurrency getCommerceCurrency(long commerceCurrencyId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.getCommerceCurrency(
			commerceCurrencyId);
	}

	@Override
	public CommerceCurrency getCommerceCurrency(long companyId, String code)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.getCommerceCurrency(
			companyId, code);
	}

	@Override
	public CommerceCurrency setActive(long commerceCurrencyId, boolean active)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.setActive(
			commerceCurrencyId, active);
	}

	@Override
	public CommerceCurrency setPrimary(long commerceCurrencyId, boolean primary)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.setPrimary(
			commerceCurrencyId, primary);
	}

	@Override
	public CommerceCurrency updateCommerceCurrency(
			long commerceCurrencyId, Map<Locale, String> nameMap, String symbol,
			BigDecimal rate, Map<Locale, String> formatPatternMap,
			int maxFractionDigits, int minFractionDigits, String roundingMode,
			boolean primary, double priority, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		return commerceCurrencyLocalService.updateCommerceCurrency(
			commerceCurrencyId, nameMap, symbol, rate, formatPatternMap,
			maxFractionDigits, minFractionDigits, roundingMode, primary,
			priority, active, serviceContext);
	}

	@Override
	public void updateExchangeRate(
			long commerceCurrencyId, String exchangeRateProviderKey)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		commerceCurrencyLocalService.updateExchangeRate(
			commerceCurrencyId, exchangeRateProviderKey);
	}

	@Override
	public void updateExchangeRates() throws PortalException {
		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);

		commerceCurrencyLocalService.updateExchangeRates();
	}

	@Reference(
		target = "(resource.name=" + CommerceCurrencyConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}