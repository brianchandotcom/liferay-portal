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

package com.liferay.commerce.wish.list.service.impl;

import com.liferay.commerce.wish.list.constants.CommerceWishListActionKeys;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.service.base.CommerceWishListServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWishListServiceImpl
	extends CommerceWishListServiceBaseImpl {

	@Override
	public CommerceWishList addCommerceWishList(
			String name, boolean defaultWishList, ServiceContext serviceContext)
		throws PortalException {

		if (getUserId() != serviceContext.getUserId()) {
			checkManagePermission(serviceContext.getScopeGroupId());
		}

		return commerceWishListLocalService.addCommerceWishList(
			name, defaultWishList, serviceContext);
	}

	@Override
	public void deleteCommerceWishList(long commerceWishListId)
		throws PortalException {

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(), commerceWishListId, ActionKeys.DELETE);

		commerceWishListLocalService.deleteCommerceWishList(commerceWishListId);
	}

	@Override
	public CommerceWishList getCommerceWishList(long commerceWishListId)
		throws PortalException {

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(), commerceWishListId, ActionKeys.VIEW);

		return commerceWishListLocalService.getCommerceWishList(
			commerceWishListId);
	}

	@Override
	public List<CommerceWishList> getCommerceWishLists(
			long groupId, int start, int end,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws PortalException {

		checkManagePermission(groupId);

		return commerceWishListLocalService.getCommerceWishLists(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceWishList> getCommerceWishLists(
			long groupId, long userId, int start, int end,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws PortalException {

		if (getUserId() != userId) {
			checkManagePermission(groupId);
		}

		return commerceWishListLocalService.getCommerceWishLists(
			groupId, userId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWishListsCount(long groupId) throws PortalException {
		checkManagePermission(groupId);

		return commerceWishListLocalService.getCommerceWishListsCount(groupId);
	}

	@Override
	public int getCommerceWishListsCount(long groupId, long userId)
		throws PortalException {

		if (getUserId() != userId) {
			checkManagePermission(groupId);
		}

		return commerceWishListLocalService.getCommerceWishListsCount(groupId);
	}

	@Override
	public CommerceWishList updateCommerceWishList(
			long commerceWishListId, String name, boolean defaultWishList)
		throws PortalException {

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(), commerceWishListId, ActionKeys.UPDATE);

		return commerceWishListLocalService.updateCommerceWishList(
			commerceWishListId, name, defaultWishList);
	}

	protected void checkManagePermission(long groupId) throws PortalException {
		PortletResourcePermission portletResourcePermission =
			_commerceWishListModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceWishListActionKeys.MANAGE_COMMERCE_WISH_LISTS);
	}

	private static volatile ModelResourcePermission<CommerceWishList>
		_commerceWishListModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceWishListServiceImpl.class,
				"_commerceWishListModelResourcePermission",
				CommerceWishList.class);

}