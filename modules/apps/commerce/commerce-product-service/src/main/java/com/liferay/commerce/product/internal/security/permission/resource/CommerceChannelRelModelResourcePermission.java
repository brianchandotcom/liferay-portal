/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.security.permission.resource;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.permission.CommerceDiscountPermission;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.permission.CommerceInventoryWarehousePermission;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
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
		else {
			long commerceInventoryWarehouseClassNameId =
				classNameLocalService.getClassNameId(
					CommerceInventoryWarehouse.class.getName());

			if (Objects.equals(
					commerceChannelRel.getClassNameId(),
					commerceInventoryWarehouseClassNameId)) {

				CommerceInventoryWarehouse commerceInventoryWarehouse =
					commerceInventoryWarehouseLocalService.
						getCommerceInventoryWarehouse(
							commerceChannelRel.getClassPK());

				commerceDiscountPermission.check(
					permissionChecker,
					commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId(),
					actionId);
			}
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
		else {
			long commerceInventoryWarehouseClassNameId =
				classNameLocalService.getClassNameId(
					CommerceInventoryWarehouse.class.getName());

			if (Objects.equals(
					commerceChannelRel.getClassNameId(),
					commerceInventoryWarehouseClassNameId)) {

				CommerceInventoryWarehouse commerceInventoryWarehouse =
					commerceInventoryWarehouseLocalService.
						getCommerceInventoryWarehouse(
							commerceChannelRel.getClassPK());

				commerceDiscountPermission.check(
					permissionChecker,
					commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId(),
					actionId);
			}
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

		long commerceInventoryWarehouseClassNameId =
			classNameLocalService.getClassNameId(
				CommerceInventoryWarehouse.class.getName());

		if (Objects.equals(
				commerceChannelRel.getClassNameId(),
				commerceInventoryWarehouseClassNameId)) {

			CommerceInventoryWarehouse commerceInventoryWarehouse =
				commerceInventoryWarehouseLocalService.
					getCommerceInventoryWarehouse(
						commerceChannelRel.getClassPK());

			return commerceInventoryWarehousePermission.contains(
				permissionChecker,
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
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

		long commerceInventoryWarehouseClassNameId =
			classNameLocalService.getClassNameId(
				CommerceInventoryWarehouse.class.getName());

		if (Objects.equals(
				commerceChannelRel.getClassNameId(),
				commerceInventoryWarehouseClassNameId)) {

			CommerceInventoryWarehouse commerceInventoryWarehouse =
				commerceInventoryWarehouseLocalService.
					getCommerceInventoryWarehouse(
						commerceChannelRel.getClassPK());

			return commerceInventoryWarehousePermission.contains(
				permissionChecker,
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
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

	@Reference
	protected CommerceInventoryWarehouseLocalService
		commerceInventoryWarehouseLocalService;

	@Reference
	protected CommerceInventoryWarehousePermission
		commerceInventoryWarehousePermission;

}