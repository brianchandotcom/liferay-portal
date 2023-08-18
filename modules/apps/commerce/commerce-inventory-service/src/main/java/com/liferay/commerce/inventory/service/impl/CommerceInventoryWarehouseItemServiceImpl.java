/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.impl;

import com.liferay.commerce.inventory.constants.CommerceInventoryActionKeys;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.base.CommerceInventoryWarehouseItemServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceInventoryWarehouseItem"
	},
	service = AopService.class
)
public class CommerceInventoryWarehouseItemServiceImpl
	extends CommerceInventoryWarehouseItemServiceBaseImpl {

	@Override
	public CommerceInventoryWarehouseItem addCommerceInventoryWarehouseItem(
			String externalReferenceCode, long commerceInventoryWarehouseId,
			BigDecimal quantity, String sku, String unitOfMeasureKey)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.UPDATE);

		return commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				externalReferenceCode, getUserId(),
				commerceInventoryWarehouseId, quantity, sku, unitOfMeasureKey);
	}

	@Override
	public CommerceInventoryWarehouseItem
			addOrUpdateCommerceInventoryWarehouseItem(
				String externalReferenceCode, long companyId,
				long commerceInventoryWarehouseId, BigDecimal quantity,
				String sku, String unitOfMeasureKey)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.UPDATE);

		return commerceInventoryWarehouseItemLocalService.
			addOrUpdateCommerceInventoryWarehouseItem(
				externalReferenceCode, companyId, getUserId(),
				commerceInventoryWarehouseId, quantity, sku, unitOfMeasureKey);
	}

	@Override
	public void deleteCommerceInventoryWarehouseItem(
			long commerceInventoryWarehouseItemId)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemLocalService.
				fetchCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseItemId);

		if (commerceInventoryWarehouseItem != null) {
			_commerceInventoryWarehouseModelResourcePermission.check(
				getPermissionChecker(),
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseId(),
				ActionKeys.UPDATE);
		}

		commerceInventoryWarehouseItemLocalService.
			deleteCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItemId);
	}

	@Override
	public void deleteCommerceInventoryWarehouseItems(
			long companyId, String sku, String unitOfMeasureKey)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		commerceInventoryWarehouseItemLocalService.
			deleteCommerceInventoryWarehouseItems(
				companyId, sku, unitOfMeasureKey);
	}

	@Override
	public CommerceInventoryWarehouseItem fetchCommerceInventoryWarehouseItem(
			long commerceInventoryWarehouseId, String sku,
			String unitOfMeasureKey)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.VIEW);

		return commerceInventoryWarehouseItemLocalService.
			fetchCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseId, sku, unitOfMeasureKey);
	}

	@Override
	public CommerceInventoryWarehouseItem
			fetchCommerceInventoryWarehouseItemByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemLocalService.
				fetchCommerceInventoryWarehouseItemByExternalReferenceCode(
					externalReferenceCode, companyId);

		if (commerceInventoryWarehouseItem != null) {
			_commerceInventoryWarehouseModelResourcePermission.check(
				getPermissionChecker(),
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseId(),
				ActionKeys.VIEW);
		}

		return commerceInventoryWarehouseItem;
	}

	@Override
	public CommerceInventoryWarehouseItem getCommerceInventoryWarehouseItem(
			long commerceInventoryWarehouseItemId)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemLocalService.
				getCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseItemId);

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(),
			commerceInventoryWarehouseItem.getCommerceInventoryWarehouseId(),
			ActionKeys.VIEW);

		return commerceInventoryWarehouseItem;
	}

	@Override
	public CommerceInventoryWarehouseItem getCommerceInventoryWarehouseItem(
			long commerceInventoryWarehouseId, String sku,
			String unitOfMeasureKey)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.VIEW);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseId, sku, unitOfMeasureKey);
	}

	@Override
	public List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItems(
				long commerceInventoryWarehouseId, int start, int end)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.VIEW);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItems(
				commerceInventoryWarehouseId, start, end);
	}

	@Override
	public List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItemsByCompanyId(
				long companyId, int start, int end)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsByCompanyId(
				companyId, start, end);
	}

	@Override
	public List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItemsByCompanyIdSkuAndUnitOfMeasureKey(
				long companyId, String sku, String unitOfMeasureKey, int start,
				int end)
		throws PrincipalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsByCompanyIdSkuAndUnitOfMeasureKey(
				companyId, sku, unitOfMeasureKey, start, end);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCount(
			long commerceInventoryWarehouseId)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.VIEW);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsCount(
				commerceInventoryWarehouseId);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCount(
			long companyId, long groupId, String sku, String unitOfMeasureKey)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsCount(
				companyId, groupId, sku, unitOfMeasureKey);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCount(
			long companyId, String sku, String unitOfMeasureKey)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsCount(
				companyId, sku, unitOfMeasureKey);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCountByCompanyId(
			long companyId)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsCountByCompanyId(companyId);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCountByModifiedDate(
			long companyId, Date startDate, Date endDate)
		throws PrincipalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsCountByModifiedDate(
				companyId, startDate, endDate);
	}

	@Override
	public List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItemsCountByModifiedDate(
				long companyId, Date startDate, Date endDate, int start,
				int end)
		throws PrincipalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsByModifiedDate(
				companyId, startDate, endDate, start, end);
	}

	@Override
	public BigDecimal getStockQuantity(
		long companyId, long groupId, String sku, String unitOfMeasureKey) {

		return commerceInventoryWarehouseItemLocalService.getStockQuantity(
			companyId, groupId, sku, unitOfMeasureKey);
	}

	@Override
	public BigDecimal getStockQuantity(
		long companyId, String sku, String unitOfMeasureKey) {

		return commerceInventoryWarehouseItemLocalService.getStockQuantity(
			companyId, sku, unitOfMeasureKey);
	}

	@Override
	public CommerceInventoryWarehouseItem
			increaseCommerceInventoryWarehouseItemQuantity(
				long commerceInventoryWarehouseItemId, BigDecimal quantity)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemLocalService.
				fetchCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseItemId);

		if (commerceInventoryWarehouseItem != null) {
			_commerceInventoryWarehouseModelResourcePermission.check(
				getPermissionChecker(),
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseId(),
				ActionKeys.UPDATE);
		}

		return commerceInventoryWarehouseItemLocalService.
			increaseCommerceInventoryWarehouseItemQuantity(
				getUserId(), commerceInventoryWarehouseItemId, quantity);
	}

	@Override
	public void moveQuantitiesBetweenWarehouses(
			long fromCommerceInventoryWarehouseId,
			long toCommerceInventoryWarehouseId, BigDecimal quantity,
			String sku, String unitOfMeasureKey)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), fromCommerceInventoryWarehouseId,
			ActionKeys.UPDATE);

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), toCommerceInventoryWarehouseId,
			ActionKeys.UPDATE);

		commerceInventoryWarehouseItemLocalService.
			moveQuantitiesBetweenWarehouses(
				getUserId(), fromCommerceInventoryWarehouseId,
				toCommerceInventoryWarehouseId, quantity, sku,
				unitOfMeasureKey);
	}

	@Override
	public CommerceInventoryWarehouseItem updateCommerceInventoryWarehouseItem(
			long commerceInventoryWarehouseItemId, BigDecimal quantity,
			BigDecimal reservedQuantity, long mvccVersion)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemLocalService.
				fetchCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseItemId);

		if (commerceInventoryWarehouseItem != null) {
			_commerceInventoryWarehouseModelResourcePermission.check(
				getPermissionChecker(),
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseId(),
				ActionKeys.UPDATE);
		}

		return commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				getUserId(), commerceInventoryWarehouseItemId, quantity,
				reservedQuantity, mvccVersion);
	}

	@Override
	public CommerceInventoryWarehouseItem updateCommerceInventoryWarehouseItem(
			long commerceInventoryWarehouseItemId, long mvccVersion,
			BigDecimal quantity, String unitOfMeasureKey)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemLocalService.
				fetchCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseItemId);

		if (commerceInventoryWarehouseItem != null) {
			_commerceInventoryWarehouseModelResourcePermission.check(
				getPermissionChecker(),
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseId(),
				ActionKeys.UPDATE);
		}

		return commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				getUserId(), commerceInventoryWarehouseItemId, mvccVersion,
				quantity, unitOfMeasureKey);
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.inventory.model.CommerceInventoryWarehouse)"
	)
	private ModelResourcePermission<CommerceInventoryWarehouse>
		_commerceInventoryWarehouseModelResourcePermission;

}