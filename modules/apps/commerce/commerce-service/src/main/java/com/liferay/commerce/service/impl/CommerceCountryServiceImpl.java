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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.service.base.CommerceCountryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
public class CommerceCountryServiceImpl extends CommerceCountryServiceBaseImpl {

	@Override
	public CommerceCountry addCommerceCountry(
			Map<Locale, String> nameMap, boolean billingAllowed,
			boolean shippingAllowed, String twoLettersISOCode,
			String threeLettersISOCode, int numericISOCode,
			boolean subjectToVAT, double priority, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceCountryLocalService.addCommerceCountry(
			nameMap, billingAllowed, shippingAllowed, twoLettersISOCode,
			threeLettersISOCode, numericISOCode, subjectToVAT, priority, active,
			serviceContext);
	}

	@Override
	public void deleteCommerceCountry(long commerceCountryId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		commerceCountryLocalService.deleteCommerceCountry(commerceCountryId);
	}

	@Override
	public CommerceCountry fetchCommerceCountry(
			long companyId, String twoLettersISOCode)
		throws PrincipalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceCountryLocalService.fetchCommerceCountry(
			companyId, twoLettersISOCode);
	}

	@Override
	public List<CommerceCountry> getBillingCommerceCountries(
		long companyId, boolean billingAllowed, boolean active) {

		return commerceCountryLocalService.getBillingCommerceCountries(
			companyId, billingAllowed, active);
	}

	@Override
	public List<CommerceCountry> getBillingCommerceCountriesByChannelId(
		long commerceChannelId, int start, int end) {

		return commerceCountryLocalService.
			getBillingCommerceCountriesByChannelId(
				commerceChannelId, start, end);
	}

	@Override
	public List<CommerceCountry> getCommerceCountries(
		long companyId, boolean active) {

		return commerceCountryLocalService.getCommerceCountries(
			companyId, active);
	}

	@Override
	public List<CommerceCountry> getCommerceCountries(
			long companyId, boolean active, int start, int end,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceCountryLocalService.getCommerceCountries(
			companyId, active, start, end, orderByComparator);
	}

	@Override
	public List<CommerceCountry> getCommerceCountries(
			long companyId, int start, int end,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceCountryLocalService.getCommerceCountries(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceCountriesCount(long companyId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceCountryLocalService.getCommerceCountriesCount(companyId);
	}

	@Override
	public int getCommerceCountriesCount(long companyId, boolean active)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceCountryLocalService.getCommerceCountriesCount(
			companyId, active);
	}

	@Override
	public CommerceCountry getCommerceCountry(long commerceCountryId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceCountryLocalService.getCommerceCountry(
			commerceCountryId);
	}

	@Override
	public CommerceCountry getCommerceCountry(
			long companyId, String twoLettersISOCode)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceCountryLocalService.getCommerceCountry(
			companyId, twoLettersISOCode);
	}

	@Override
	public List<CommerceCountry> getShippingCommerceCountries(
		long companyId, boolean shippingAllowed, boolean active) {

		return commerceCountryLocalService.getShippingCommerceCountries(
			companyId, shippingAllowed, active);
	}

	@Override
	public List<CommerceCountry> getShippingCommerceCountriesByChannelId(
		long commerceChannelId, int start, int end) {

		return commerceCountryLocalService.
			getShippingCommerceCountriesByChannelId(
				commerceChannelId, start, end);
	}

	@Override
	public List<CommerceCountry> getWarehouseCommerceCountries(
			long companyId, boolean all)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceCountryLocalService.getWarehouseCommerceCountries(
			companyId, all);
	}

	@Override
	public BaseModelSearchResult<CommerceCountry> searchCommerceCountries(
			long companyId, Boolean active, String keywords, int start, int end,
			Sort sort)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceCountryLocalService.searchCommerceCountries(
			companyId, active, keywords, start, end, sort);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public BaseModelSearchResult<CommerceCountry> searchCommerceCountries(
			SearchContext searchContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceCountryLocalService.searchCommerceCountries(
			searchContext);
	}

	@Override
	public CommerceCountry setActive(long commerceCountryId, boolean active)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceCountryLocalService.setActive(commerceCountryId, active);
	}

	@Override
	public CommerceCountry updateCommerceCountry(
			long commerceCountryId, Map<Locale, String> nameMap,
			boolean billingAllowed, boolean shippingAllowed,
			String twoLettersISOCode, String threeLettersISOCode,
			int numericISOCode, boolean subjectToVAT, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceCountryLocalService.updateCommerceCountry(
			commerceCountryId, nameMap, billingAllowed, shippingAllowed,
			twoLettersISOCode, threeLettersISOCode, numericISOCode,
			subjectToVAT, priority, active, serviceContext);
	}

	@Override
	public CommerceCountry updateCommerceCountryChannelFilter(
			long commerceCountryId, boolean enable)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceCountryLocalService.updateCommerceCountryChannelFilter(
			commerceCountryId, enable);
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommerceCountryServiceImpl.class, "_portletResourcePermission",
				CommerceConstants.RESOURCE_NAME_ADDRESS);

}