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

package com.liferay.commerce.vat.service.impl;

import com.liferay.commerce.vat.constants.CommerceVatActionKeys;
import com.liferay.commerce.vat.constants.CommerceVatConstants;
import com.liferay.commerce.vat.model.CommerceVatNumber;
import com.liferay.commerce.vat.service.base.CommerceVatNumberServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceVatNumberServiceImpl
	extends CommerceVatNumberServiceBaseImpl {

	@Override
	public CommerceVatNumber addCommerceVatNumber(
			String className, long classPK, String vatNumber,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceVatActionKeys.MANAGE_COMMERCE_VAT_NUMBERS);

		return commerceVatNumberLocalService.addCommerceVatNumber(
			className, classPK, vatNumber, serviceContext);
	}

	@Override
	public void deleteCommerceVatNumber(long commerceVatNumberId)
		throws PortalException {

		CommerceVatNumber commerceVatNumber =
			commerceVatNumberLocalService.getCommerceVatNumber(
				commerceVatNumberId);

		_portletResourcePermission.check(
			getPermissionChecker(), commerceVatNumber.getGroupId(),
			CommerceVatActionKeys.MANAGE_COMMERCE_VAT_NUMBERS);

		commerceVatNumberLocalService.deleteCommerceVatNumber(
			commerceVatNumber);
	}

	@Override
	public CommerceVatNumber fetchCommerceVatNumber(long commerceVatNumberId)
		throws PortalException {

		CommerceVatNumber commerceVatNumber =
			commerceVatNumberLocalService.fetchCommerceVatNumber(
				commerceVatNumberId);

		if (commerceVatNumber != null) {
			_portletResourcePermission.check(
				getPermissionChecker(), commerceVatNumber.getGroupId(),
				CommerceVatActionKeys.MANAGE_COMMERCE_VAT_NUMBERS);
		}

		return commerceVatNumber;
	}

	@Override
	public CommerceVatNumber fetchCommerceVatNumber(
			long groupId, String className, long classPK)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceVatActionKeys.MANAGE_COMMERCE_VAT_NUMBERS);

		return commerceVatNumberLocalService.fetchCommerceVatNumber(
			groupId, className, classPK);
	}

	@Override
	public List<CommerceVatNumber> getCommerceVatNumbers(
			long groupId, int start, int end,
			OrderByComparator<CommerceVatNumber> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceVatActionKeys.MANAGE_COMMERCE_VAT_NUMBERS);

		return commerceVatNumberLocalService.getCommerceVatNumbers(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceVatNumbersCount(long groupId) throws PortalException {
		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceVatActionKeys.MANAGE_COMMERCE_VAT_NUMBERS);

		return commerceVatNumberLocalService.getCommerceVatNumbersCount(
			groupId);
	}

	@Override
	public BaseModelSearchResult<CommerceVatNumber> searchCommerceVatNumbers(
			long companyId, long groupId, String keywords, int start, int end,
			Sort sort)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceVatActionKeys.MANAGE_COMMERCE_VAT_NUMBERS);

		return commerceVatNumberLocalService.searchCommerceVatNumbers(
			companyId, groupId, keywords, start, end, sort);
	}

	@Override
	public CommerceVatNumber updateCommerceVatNumber(
			long commerceVatNumberId, String vatNumber)
		throws PortalException {

		CommerceVatNumber commerceVatNumber =
			commerceVatNumberLocalService.updateCommerceVatNumber(
				commerceVatNumberId, vatNumber);

		_portletResourcePermission.check(
			getPermissionChecker(), commerceVatNumber.getGroupId(),
			CommerceVatActionKeys.MANAGE_COMMERCE_VAT_NUMBERS);

		return commerceVatNumber;
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommerceVatNumberServiceImpl.class,
				"_portletResourcePermission",
				CommerceVatConstants.RESOURCE_NAME);

}