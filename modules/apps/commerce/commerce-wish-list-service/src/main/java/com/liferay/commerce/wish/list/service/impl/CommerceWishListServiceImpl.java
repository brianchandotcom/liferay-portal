/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.service.impl;

import com.liferay.commerce.wish.list.constants.CommerceWishListActionKeys;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.service.base.CommerceWishListServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceWishList"
	},
	service = AopService.class
)
public class CommerceWishListServiceImpl
	extends CommerceWishListServiceBaseImpl {

	@Override
	public CommerceWishList addCommerceWishList(
			String name, boolean defaultWishList, ServiceContext serviceContext)
		throws PortalException {

		if (getUserId() != serviceContext.getUserId()) {
			_checkManagePermission(serviceContext.getScopeGroupId());
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
	public CommerceWishList fetchCommerceWishList(
			long groupId, long userId, boolean defaultWishList,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws PortalException {

		CommerceWishList commerceWishList =
			commerceWishListLocalService.fetchCommerceWishList(
				groupId, userId, defaultWishList, orderByComparator);

		if (commerceWishList != null) {
			_commerceWishListModelResourcePermission.check(
				getPermissionChecker(), commerceWishList, ActionKeys.VIEW);
		}

		return commerceWishList;
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

		_checkManagePermission(groupId);

		return commerceWishListLocalService.getCommerceWishLists(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceWishList> getCommerceWishLists(
			long groupId, long userId, int start, int end,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws PortalException {

		if (getUserId() != userId) {
			_checkManagePermission(groupId);
		}

		return commerceWishListLocalService.getCommerceWishLists(
			groupId, userId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWishListsCount(long groupId) throws PortalException {
		_checkManagePermission(groupId);

		return commerceWishListLocalService.getCommerceWishListsCount(groupId);
	}

	@Override
	public int getCommerceWishListsCount(long groupId, long userId)
		throws PortalException {

		if (getUserId() != userId) {
			_checkManagePermission(groupId);
		}

		return commerceWishListLocalService.getCommerceWishListsCount(
			groupId, userId);
	}

	@Override
	public CommerceWishList getDefaultCommerceWishList(
			long groupId, long userId)
		throws PortalException {

		CommerceWishList commerceWishList =
			commerceWishListLocalService.getDefaultCommerceWishList(
				groupId, userId, null);

		if (commerceWishList != null) {
			_commerceWishListModelResourcePermission.check(
				getPermissionChecker(), commerceWishList, ActionKeys.VIEW);
		}

		return commerceWishList;
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

	private void _checkManagePermission(long groupId) throws PortalException {
		PortletResourcePermission portletResourcePermission =
			_commerceWishListModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceWishListActionKeys.MANAGE_COMMERCE_WISH_LISTS);
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.wish.list.model.CommerceWishList)"
	)
	private ModelResourcePermission<CommerceWishList>
		_commerceWishListModelResourcePermission;

}