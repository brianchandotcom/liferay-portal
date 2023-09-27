/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.service;

import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceListChannelRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListChannelRelService
 * @generated
 */
public class CommercePriceListChannelRelServiceWrapper
	implements CommercePriceListChannelRelService,
			   ServiceWrapper<CommercePriceListChannelRelService> {

	public CommercePriceListChannelRelServiceWrapper() {
		this(null);
	}

	public CommercePriceListChannelRelServiceWrapper(
		CommercePriceListChannelRelService commercePriceListChannelRelService) {

		_commercePriceListChannelRelService =
			commercePriceListChannelRelService;
	}

	@Override
	public CommercePriceListChannelRel addCommercePriceListChannelRel(
			long commercePriceListId, long commerceChannelId, int order,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListChannelRelService.
			addCommercePriceListChannelRel(
				commercePriceListId, commerceChannelId, order, serviceContext);
	}

	@Override
	public void deleteCommercePriceListChannelRel(
			long commercePriceListChannelRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePriceListChannelRelService.deleteCommercePriceListChannelRel(
			commercePriceListChannelRelId);
	}

	@Override
	public void deleteCommercePriceListChannelRelsByCommercePriceListId(
			long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePriceListChannelRelService.
			deleteCommercePriceListChannelRelsByCommercePriceListId(
				commercePriceListId);
	}

	@Override
	public CommercePriceListChannelRel fetchCommercePriceListChannelRel(
			long commerceChannelId, long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListChannelRelService.
			fetchCommercePriceListChannelRel(
				commerceChannelId, commercePriceListId);
	}

	@Override
	public CommercePriceListChannelRel getCommercePriceListChannelRel(
			long commercePriceListChannelRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListChannelRelService.
			getCommercePriceListChannelRel(commercePriceListChannelRelId);
	}

	@Override
	public java.util.List<CommercePriceListChannelRel>
			getCommercePriceListChannelRels(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListChannelRelService.
			getCommercePriceListChannelRels(commercePriceListId);
	}

	@Override
	public java.util.List<CommercePriceListChannelRel>
			getCommercePriceListChannelRels(
				long commercePriceListId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommercePriceListChannelRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListChannelRelService.
			getCommercePriceListChannelRels(
				commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<CommercePriceListChannelRel>
		getCommercePriceListChannelRels(
			long commercePriceListId, String name, int start, int end) {

		return _commercePriceListChannelRelService.
			getCommercePriceListChannelRels(
				commercePriceListId, name, start, end);
	}

	@Override
	public int getCommercePriceListChannelRelsCount(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListChannelRelService.
			getCommercePriceListChannelRelsCount(commercePriceListId);
	}

	@Override
	public int getCommercePriceListChannelRelsCount(
		long commercePriceListId, String name) {

		return _commercePriceListChannelRelService.
			getCommercePriceListChannelRelsCount(commercePriceListId, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePriceListChannelRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommercePriceListChannelRelService getWrappedService() {
		return _commercePriceListChannelRelService;
	}

	@Override
	public void setWrappedService(
		CommercePriceListChannelRelService commercePriceListChannelRelService) {

		_commercePriceListChannelRelService =
			commercePriceListChannelRelService;
	}

	private CommercePriceListChannelRelService
		_commercePriceListChannelRelService;

}