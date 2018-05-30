/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.wish.list.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceWishListItemService}.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListItemService
 * @generated
 */
@ProviderType
public class CommerceWishListItemServiceWrapper
	implements CommerceWishListItemService,
		ServiceWrapper<CommerceWishListItemService> {
	public CommerceWishListItemServiceWrapper(
		CommerceWishListItemService commerceWishListItemService) {
		_commerceWishListItemService = commerceWishListItemService;
	}

	@Override
	public com.liferay.commerce.wish.list.model.CommerceWishListItem addCommerceWishListItem(
		long commerceWishListId, long cpDefinitionId, long cpInstanceId,
		String json,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWishListItemService.addCommerceWishListItem(commerceWishListId,
			cpDefinitionId, cpInstanceId, json, serviceContext);
	}

	@Override
	public void deleteCommerceWishListItem(long commerceWishListItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceWishListItemService.deleteCommerceWishListItem(commerceWishListItemId);
	}

	@Override
	public com.liferay.commerce.wish.list.model.CommerceWishListItem getCommerceWishListItem(
		long commerceWishListItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWishListItemService.getCommerceWishListItem(commerceWishListItemId);
	}

	@Override
	public java.util.List<com.liferay.commerce.wish.list.model.CommerceWishListItem> getCommerceWishListItems(
		long commerceWishListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.wish.list.model.CommerceWishListItem> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWishListItemService.getCommerceWishListItems(commerceWishListId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceWishListItemsCount(long commerceWishListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWishListItemService.getCommerceWishListItemsCount(commerceWishListId);
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