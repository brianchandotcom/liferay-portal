/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceWishListService}.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListService
 * @generated
 */
public class CommerceWishListServiceWrapper
	implements CommerceWishListService,
			   ServiceWrapper<CommerceWishListService> {

	public CommerceWishListServiceWrapper() {
		this(null);
	}

	public CommerceWishListServiceWrapper(
		CommerceWishListService commerceWishListService) {

		_commerceWishListService = commerceWishListService;
	}

	@Override
	public com.liferay.commerce.wish.list.model.CommerceWishList
			addCommerceWishList(
				String name, boolean defaultWishList,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListService.addCommerceWishList(
			name, defaultWishList, serviceContext);
	}

	@Override
	public void deleteCommerceWishList(long commerceWishListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceWishListService.deleteCommerceWishList(commerceWishListId);
	}

	@Override
	public com.liferay.commerce.wish.list.model.CommerceWishList
			fetchCommerceWishList(
				long groupId, long userId, boolean defaultWishList,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.wish.list.model.CommerceWishList>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListService.fetchCommerceWishList(
			groupId, userId, defaultWishList, orderByComparator);
	}

	@Override
	public com.liferay.commerce.wish.list.model.CommerceWishList
			getCommerceWishList(long commerceWishListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListService.getCommerceWishList(commerceWishListId);
	}

	@Override
	public java.util.List<com.liferay.commerce.wish.list.model.CommerceWishList>
			getCommerceWishLists(
				long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.wish.list.model.CommerceWishList>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListService.getCommerceWishLists(
			groupId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.wish.list.model.CommerceWishList>
			getCommerceWishLists(
				long groupId, long userId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.wish.list.model.CommerceWishList>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListService.getCommerceWishLists(
			groupId, userId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWishListsCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListService.getCommerceWishListsCount(groupId);
	}

	@Override
	public int getCommerceWishListsCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListService.getCommerceWishListsCount(
			groupId, userId);
	}

	@Override
	public com.liferay.commerce.wish.list.model.CommerceWishList
			getDefaultCommerceWishList(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListService.getDefaultCommerceWishList(
			groupId, userId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceWishListService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.wish.list.model.CommerceWishList
			updateCommerceWishList(
				long commerceWishListId, String name, boolean defaultWishList)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceWishListService.updateCommerceWishList(
			commerceWishListId, name, defaultWishList);
	}

	@Override
	public CommerceWishListService getWrappedService() {
		return _commerceWishListService;
	}

	@Override
	public void setWrappedService(
		CommerceWishListService commerceWishListService) {

		_commerceWishListService = commerceWishListService;
	}

	private CommerceWishListService _commerceWishListService;

}