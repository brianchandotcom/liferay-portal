/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceWishListItemService}.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListItemService
 * @generated
 */
public class CommerceWishListItemServiceWrapper
	implements CommerceWishListItemService,
			   ServiceWrapper<CommerceWishListItemService> {

	public CommerceWishListItemServiceWrapper() {
		this(null);
	}

	public CommerceWishListItemServiceWrapper(
		CommerceWishListItemService commerceWishListItemService) {

		_commerceWishListItemService = commerceWishListItemService;
	}

	@Override
	public com.liferay.commerce.wish.list.model.CommerceWishListItem
			addCommerceWishListItem(
				long commerceAccountId, long commerceWishListId,
				long cProductId, String cpInstanceUuid, String json,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListItemService.addCommerceWishListItem(
			commerceAccountId, commerceWishListId, cProductId, cpInstanceUuid,
			json, serviceContext);
	}

	@Override
	public void deleteCommerceWishListItem(long commerceWishListItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceWishListItemService.deleteCommerceWishListItem(
			commerceWishListItemId);
	}

	@Override
	public com.liferay.commerce.wish.list.model.CommerceWishListItem
			getCommerceWishListItem(long commerceWishListItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListItemService.getCommerceWishListItem(
			commerceWishListItemId);
	}

	@Override
	public com.liferay.commerce.wish.list.model.CommerceWishListItem
			getCommerceWishListItem(
				long commerceWishListId, String cpInstanceUuid, long cProductId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListItemService.getCommerceWishListItem(
			commerceWishListId, cpInstanceUuid, cProductId);
	}

	@Override
	public int getCommerceWishListItemByContainsCPInstanceCount(
			long commerceWishListId, String cpInstanceUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListItemService.
			getCommerceWishListItemByContainsCPInstanceCount(
				commerceWishListId, cpInstanceUuid);
	}

	@Override
	public int getCommerceWishListItemByContainsCProductCount(
			long commerceWishListId, long cProductId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListItemService.
			getCommerceWishListItemByContainsCProductCount(
				commerceWishListId, cProductId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.wish.list.model.CommerceWishListItem>
				getCommerceWishListItems(
					long commerceWishListId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.wish.list.model.
							CommerceWishListItem> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListItemService.getCommerceWishListItems(
			commerceWishListId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWishListItemsCount(long commerceWishListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListItemService.getCommerceWishListItemsCount(
			commerceWishListId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceWishListItemService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceWishListItemService getWrappedService() {
		return _commerceWishListItemService;
	}

	@Override
	public void setWrappedService(
		CommerceWishListItemService commerceWishListItemService) {

		_commerceWishListItemService = commerceWishListItemService;
	}

	private CommerceWishListItemService _commerceWishListItemService;

}