/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.service;

import com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceListOrderTypeRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListOrderTypeRelService
 * @generated
 */
public class CommercePriceListOrderTypeRelServiceWrapper
	implements CommercePriceListOrderTypeRelService,
			   ServiceWrapper<CommercePriceListOrderTypeRelService> {

	public CommercePriceListOrderTypeRelServiceWrapper() {
		this(null);
	}

	public CommercePriceListOrderTypeRelServiceWrapper(
		CommercePriceListOrderTypeRelService
			commercePriceListOrderTypeRelService) {

		_commercePriceListOrderTypeRelService =
			commercePriceListOrderTypeRelService;
	}

	@Override
	public CommercePriceListOrderTypeRel addCommercePriceListOrderTypeRel(
			long commercePriceListId, long commerceOrderTypeId, int priority,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListOrderTypeRelService.
			addCommercePriceListOrderTypeRel(
				commercePriceListId, commerceOrderTypeId, priority,
				serviceContext);
	}

	@Override
	public void deleteCommercePriceListOrderTypeRel(
			long commercePriceListOrderTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePriceListOrderTypeRelService.
			deleteCommercePriceListOrderTypeRel(
				commercePriceListOrderTypeRelId);
	}

	@Override
	public CommercePriceListOrderTypeRel fetchCommercePriceListOrderTypeRel(
			long commercePriceListId, long commerceOrderTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListOrderTypeRelService.
			fetchCommercePriceListOrderTypeRel(
				commercePriceListId, commerceOrderTypeId);
	}

	@Override
	public CommercePriceListOrderTypeRel getCommercePriceListOrderTypeRel(
			long commercePriceListOrderTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListOrderTypeRelService.
			getCommercePriceListOrderTypeRel(commercePriceListOrderTypeRelId);
	}

	@Override
	public java.util.List<CommercePriceListOrderTypeRel>
			getCommercePriceListOrderTypeRels(
				long commercePriceListId, String name, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommercePriceListOrderTypeRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListOrderTypeRelService.
			getCommercePriceListOrderTypeRels(
				commercePriceListId, name, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListOrderTypeRelsCount(
			long commercePriceListId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListOrderTypeRelService.
			getCommercePriceListOrderTypeRelsCount(commercePriceListId, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePriceListOrderTypeRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommercePriceListOrderTypeRelService getWrappedService() {
		return _commercePriceListOrderTypeRelService;
	}

	@Override
	public void setWrappedService(
		CommercePriceListOrderTypeRelService
			commercePriceListOrderTypeRelService) {

		_commercePriceListOrderTypeRelService =
			commercePriceListOrderTypeRelService;
	}

	private CommercePriceListOrderTypeRelService
		_commercePriceListOrderTypeRelService;

}