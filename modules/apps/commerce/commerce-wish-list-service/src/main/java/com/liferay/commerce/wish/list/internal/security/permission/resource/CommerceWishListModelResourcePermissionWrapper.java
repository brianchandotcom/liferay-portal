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

package com.liferay.commerce.wish.list.internal.security.permission.resource;

import com.liferay.commerce.wish.list.constants.CommerceWishListActionKeys;
import com.liferay.commerce.wish.list.constants.CommerceWishListConstants;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.service.CommerceWishListLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.wish.list.model.CommerceWishList",
	service = ModelResourcePermission.class
)
public class CommerceWishListModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<CommerceWishList> {

	@Override
	protected ModelResourcePermission<CommerceWishList>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			CommerceWishList.class, CommerceWishList::getCommerceWishListId,
			_commerceWishListLocalService::getCommerceWishList,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> consumer.accept(
				new CommerceWishListModelResourcePermissionLogic()));
	}

	@Reference
	private CommerceWishListLocalService _commerceWishListLocalService;

	@Reference(
		target = "(resource.name=" + CommerceWishListConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	private class CommerceWishListModelResourcePermissionLogic
		implements ModelResourcePermissionLogic<CommerceWishList> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name,
				CommerceWishList commerceWishList, String actionId)
			throws PortalException {

			if (actionId.equals(ActionKeys.DELETE) &&
				!permissionChecker.isSignedIn()) {

				return false;
			}

			if (commerceWishList.getUserId() == permissionChecker.getUserId()) {
				return true;
			}

			return _portletResourcePermission.contains(
				permissionChecker, commerceWishList.getGroupId(),
				CommerceWishListActionKeys.MANAGE_COMMERCE_WISH_LISTS);
		}

	}

}