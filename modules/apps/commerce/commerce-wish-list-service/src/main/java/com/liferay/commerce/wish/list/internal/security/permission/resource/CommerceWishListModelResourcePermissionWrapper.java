/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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