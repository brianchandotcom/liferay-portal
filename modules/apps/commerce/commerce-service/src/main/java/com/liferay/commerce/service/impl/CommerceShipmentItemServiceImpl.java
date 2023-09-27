/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.base.CommerceShipmentItemServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceShipmentItem"
	},
	service = AopService.class
)
public class CommerceShipmentItemServiceImpl
	extends CommerceShipmentItemServiceBaseImpl {

	@Override
	public CommerceShipmentItem addCommerceShipmentItem(
			String externalReferenceCode, long commerceShipmentId,
			long commerceOrderItemId, long commerceInventoryWarehouseId,
			BigDecimal quantity, String unitOfMeasureKey,
			boolean validateInventory, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.addCommerceShipmentItem(
			externalReferenceCode, commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId, quantity, unitOfMeasureKey,
			validateInventory, serviceContext);
	}

	@Override
	public CommerceShipmentItem addOrUpdateCommerceShipmentItem(
			String externalReferenceCode, long commerceShipmentId,
			long commerceOrderItemId, long commerceInventoryWarehouseId,
			BigDecimal quantity, String unitOfMeasureKey,
			boolean validateInventory, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.addOrUpdateCommerceShipmentItem(
			externalReferenceCode, commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId, quantity, unitOfMeasureKey,
			validateInventory, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass boolean for restoring stock
	 */
	@Deprecated
	@Override
	public void deleteCommerceShipmentItem(long commerceShipmentItemId)
		throws PortalException {

		deleteCommerceShipmentItem(commerceShipmentItemId, false);
	}

	@Override
	public void deleteCommerceShipmentItem(
			long commerceShipmentItemId, boolean restoreStockQuantity)
		throws PortalException {

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.findByPrimaryKey(
				commerceShipmentItemId);

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		commerceShipmentItemLocalService.deleteCommerceShipmentItem(
			commerceShipmentItem, restoreStockQuantity);
	}

	@Override
	public void deleteCommerceShipmentItems(
			long commerceShipmentId, boolean restoreStockQuantity)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		commerceShipmentItemLocalService.deleteCommerceShipmentItems(
			commerceShipmentId, restoreStockQuantity);
	}

	@Override
	public CommerceShipmentItem fetchCommerceShipmentItem(
			long commerceShipmentId, long commerceOrderItemId,
			long commerceInventoryWarehouseId)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.fetchCommerceShipmentItem(
			commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId);
	}

	@Override
	public CommerceShipmentItem
			fetchCommerceShipmentItemByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.
			fetchCommerceShipmentItemByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	@Override
	public CommerceShipmentItem getCommerceShipmentItem(
			long commerceShipmentItemId)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.getCommerceShipmentItem(
			commerceShipmentItemId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public List<CommerceShipmentItem> getCommerceShipmentItems(
			long commerceOrderItemId)
		throws PortalException {

		return commerceShipmentItemService.
			getCommerceShipmentItemsByCommerceOrderItemId(commerceOrderItemId);
	}

	@Override
	public List<CommerceShipmentItem> getCommerceShipmentItems(
			long commerceShipmentId, int start, int end,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.getCommerceShipmentItems(
			commerceShipmentId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceShipmentItem>
			getCommerceShipmentItemsByCommerceOrderItemId(
				long commerceOrderItemId)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				commerceOrderItemId);

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderItem.getCommerceOrderId(),
			ActionKeys.VIEW);

		return commerceShipmentItemLocalService.
			getCommerceShipmentItemsByCommerceOrderItemId(commerceOrderItemId);
	}

	@Override
	public int getCommerceShipmentItemsCount(long commerceShipmentId)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.getCommerceShipmentItemsCount(
			commerceShipmentId);
	}

	@Override
	public int getCommerceShipmentItemsCountByCommerceOrderItemId(
			long commerceOrderItemId)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.
			getCommerceShipmentItemsCountByCommerceOrderItemId(
				commerceOrderItemId);
	}

	@Override
	public int getCommerceShipmentOrderItemsQuantity(
			long commerceShipmentId, long commerceOrderItemId)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.
			getCommerceShipmentOrderItemsQuantity(
				commerceShipmentId, commerceOrderItemId);
	}

	@Override
	public CommerceShipmentItem updateCommerceShipmentItem(
			long commerceShipmentItemId, long commerceInventoryWarehouseId,
			BigDecimal quantity, boolean validateInventory)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.updateCommerceShipmentItem(
			commerceShipmentItemId, commerceInventoryWarehouseId, quantity,
			validateInventory);
	}

	@Override
	public CommerceShipmentItem updateExternalReferenceCode(
			long commerceShipmentItemId, String externalReferenceCode)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.updateExternalReferenceCode(
			commerceShipmentItemId, externalReferenceCode);
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;

	@Reference(
		target = "(resource.name=" + CommerceConstants.RESOURCE_NAME_COMMERCE_SHIPMENT + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}