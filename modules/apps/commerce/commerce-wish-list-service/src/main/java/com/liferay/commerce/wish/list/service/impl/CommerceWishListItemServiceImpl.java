/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.service.impl;

import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.service.base.CommerceWishListItemServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
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
		"json.web.service.context.path=CommerceWishListItem"
	},
	service = AopService.class
)
public class CommerceWishListItemServiceImpl
	extends CommerceWishListItemServiceBaseImpl {

	@Override
	public CommerceWishListItem addCommerceWishListItem(
			long commerceAccountId, long commerceWishListId, long cProductId,
			String cpInstanceUuid, String json, ServiceContext serviceContext)
		throws PortalException {

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(), commerceWishListId, ActionKeys.UPDATE);

		CProduct cProduct = cProductLocalService.getCProduct(cProductId);

		commerceProductViewPermission.check(
			getPermissionChecker(), commerceAccountId,
			cProduct.getPublishedCPDefinitionId());

		return commerceWishListItemLocalService.addCommerceWishListItem(
			commerceWishListId, cProductId, cpInstanceUuid, json,
			serviceContext);
	}

	@Override
	public void deleteCommerceWishListItem(long commerceWishListItemId)
		throws PortalException {

		CommerceWishListItem commerceWishListItem =
			commerceWishListItemLocalService.getCommerceWishListItem(
				commerceWishListItemId);

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(),
			commerceWishListItem.getCommerceWishListId(), ActionKeys.UPDATE);

		commerceWishListItemLocalService.deleteCommerceWishListItem(
			commerceWishListItem);
	}

	@Override
	public CommerceWishListItem getCommerceWishListItem(
			long commerceWishListItemId)
		throws PortalException {

		CommerceWishListItem commerceWishListItem =
			commerceWishListItemLocalService.getCommerceWishListItem(
				commerceWishListItemId);

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(),
			commerceWishListItem.getCommerceWishListId(), ActionKeys.VIEW);

		return commerceWishListItem;
	}

	@Override
	public CommerceWishListItem getCommerceWishListItem(
			long commerceWishListId, String cpInstanceUuid, long cProductId)
		throws PortalException {

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(), commerceWishListId, ActionKeys.VIEW);

		return commerceWishListItemLocalService.getCommerceWishListItem(
			commerceWishListId, cpInstanceUuid, cProductId);
	}

	@Override
	public int getCommerceWishListItemByContainsCPInstanceCount(
			long commerceWishListId, String cpInstanceUuid)
		throws PortalException {

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(), commerceWishListId, ActionKeys.VIEW);

		return commerceWishListItemPersistence.countByCW_CPI(
			commerceWishListId, cpInstanceUuid);
	}

	@Override
	public int getCommerceWishListItemByContainsCProductCount(
			long commerceWishListId, long cProductId)
		throws PortalException {

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(), commerceWishListId, ActionKeys.VIEW);

		return commerceWishListItemPersistence.countByCW_CP(
			commerceWishListId, cProductId);
	}

	@Override
	public List<CommerceWishListItem> getCommerceWishListItems(
			long commerceWishListId, int start, int end,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws PortalException {

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(), commerceWishListId, ActionKeys.VIEW);

		return commerceWishListItemLocalService.getCommerceWishListItems(
			commerceWishListId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWishListItemsCount(long commerceWishListId)
		throws PortalException {

		_commerceWishListModelResourcePermission.check(
			getPermissionChecker(), commerceWishListId, ActionKeys.VIEW);

		return commerceWishListItemLocalService.getCommerceWishListItemsCount(
			commerceWishListId);
	}

	@Reference
	protected CommerceProductViewPermission commerceProductViewPermission;

	@Reference
	protected CProductLocalService cProductLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.wish.list.model.CommerceWishList)"
	)
	private ModelResourcePermission<CommerceWishList>
		_commerceWishListModelResourcePermission;

}