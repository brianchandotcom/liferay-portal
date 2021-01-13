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

package com.liferay.commerce.product.internal.security.permission.resource;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.permission.CommerceDiscountPermission;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = "model.class.name=com.liferay.commerce.product.model.CommerceChannelRel",
	service = ModelResourcePermission.class
)
public class CommerceChannelRelModelResourcePermission
	implements ModelResourcePermission<CommerceChannelRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceChannelRel commerceChannelRel, String actionId)
		throws PortalException {

		long commerceDiscountClassNameId = classNameLocalService.getClassNameId(
			CommerceDiscount.class.getName());

		if (Objects.equals(
				commerceChannelRel.getClassNameId(),
				commerceDiscountClassNameId)) {

			CommerceDiscount commerceDiscount =
				commerceDiscountLocalService.getCommerceDiscount(
					commerceChannelRel.getClassPK());

			commerceDiscountPermission.check(
				permissionChecker, commerceDiscount.getCommerceDiscountId(),
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceChannelRelId,
			String actionId)
		throws PortalException {

		CommerceChannelRel commerceChannelRel =
			commerceChannelRelLocalService.getCommerceChannelRel(
				commerceChannelRelId);

		long commerceDiscountClassNameId = classNameLocalService.getClassNameId(
			CommerceDiscount.class.getName());

		if (Objects.equals(
				commerceChannelRel.getClassNameId(),
				commerceDiscountClassNameId)) {

			CommerceDiscount commerceDiscount =
				commerceDiscountLocalService.getCommerceDiscount(
					commerceChannelRel.getClassPK());

			commerceDiscountPermission.check(
				permissionChecker, commerceDiscount.getCommerceDiscountId(),
				actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceChannelRel commerceChannelRel, String actionId)
		throws PortalException {

		long commerceDiscountClassNameId = classNameLocalService.getClassNameId(
			CommerceDiscount.class.getName());

		if (Objects.equals(
				commerceChannelRel.getClassNameId(),
				commerceDiscountClassNameId)) {

			CommerceDiscount commerceDiscount =
				commerceDiscountLocalService.getCommerceDiscount(
					commerceChannelRel.getClassPK());

			return commerceDiscountPermission.contains(
				permissionChecker, commerceDiscount.getCommerceDiscountId(),
				actionId);
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceChannelRelId,
			String actionId)
		throws PortalException {

		CommerceChannelRel commerceChannelRel =
			commerceChannelRelLocalService.getCommerceChannelRel(
				commerceChannelRelId);

		long commerceDiscountClassNameId = classNameLocalService.getClassNameId(
			CommerceDiscount.class.getName());

		if (Objects.equals(
				commerceChannelRel.getClassNameId(),
				commerceDiscountClassNameId)) {

			CommerceDiscount commerceDiscount =
				commerceDiscountLocalService.getCommerceDiscount(
					commerceChannelRel.getClassPK());

			return commerceDiscountPermission.contains(
				permissionChecker, commerceDiscount.getCommerceDiscountId(),
				actionId);
		}

		return false;
	}

	@Override
	public String getModelName() {
		return CommerceChannelRel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected CommerceChannelRelLocalService commerceChannelRelLocalService;

	@Reference
	protected CommerceDiscountLocalService commerceDiscountLocalService;

	@Reference
	protected CommerceDiscountPermission commerceDiscountPermission;

}