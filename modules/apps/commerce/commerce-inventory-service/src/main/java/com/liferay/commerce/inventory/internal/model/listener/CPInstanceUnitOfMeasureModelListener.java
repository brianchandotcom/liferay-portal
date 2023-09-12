/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.model.listener;

import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryReplenishmentItemLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.math.BigDecimal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(service = ModelListener.class)
public class CPInstanceUnitOfMeasureModelListener
	extends BaseModelListener<CPInstanceUnitOfMeasure> {

	@Override
	public void onAfterCreate(CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {
		List<CPInstanceUnitOfMeasure> cpInstanceUnitOfMeasures =
			_cpInstanceUnitOfMeasureLocalService.getCPInstanceUnitOfMeasures(
				cpInstanceUnitOfMeasure.getCPInstanceId(), 0, 2, null);

		if (cpInstanceUnitOfMeasures.size() == 1) {
			_updateUnitOfMeasureKey(
				cpInstanceUnitOfMeasure.getCompanyId(),
				cpInstanceUnitOfMeasure.getKey(), StringPool.BLANK,
				cpInstanceUnitOfMeasure.getSku());
		}
		else {
			for (CommerceInventoryWarehouse commerceInventoryWarehouse :
					_commerceInventoryWarehouseLocalService.
						getCommerceInventoryWarehouses(
							cpInstanceUnitOfMeasure.getCompanyId())) {

				try {
					_commerceInventoryWarehouseItemLocalService.
						addCommerceInventoryWarehouseItem(
							null, cpInstanceUnitOfMeasure.getUserId(),
							commerceInventoryWarehouse.
								getCommerceInventoryWarehouseId(),
							BigDecimal.ZERO, cpInstanceUnitOfMeasure.getSku(),
							cpInstanceUnitOfMeasure.getKey());
				}
				catch (PortalException portalException) {
					_log.error(portalException);
				}
			}
		}
	}

	@Override
	public void onAfterRemove(CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure)
		throws ModelListenerException {

		List<CPInstanceUnitOfMeasure> cpInstanceUnitOfMeasures =
			_cpInstanceUnitOfMeasureLocalService.getCPInstanceUnitOfMeasures(
				cpInstanceUnitOfMeasure.getCPInstanceId(), 0, 1, null);

		if (cpInstanceUnitOfMeasures.isEmpty()) {
			_updateUnitOfMeasureKey(
				cpInstanceUnitOfMeasure.getCompanyId(), StringPool.BLANK,
				cpInstanceUnitOfMeasure.getKey(),
				cpInstanceUnitOfMeasure.getSku());
		}
		else {
			for (CommerceInventoryWarehouse commerceInventoryWarehouse :
					_commerceInventoryWarehouseLocalService.
						getCommerceInventoryWarehouses(
							cpInstanceUnitOfMeasure.getCompanyId())) {

				_commerceInventoryWarehouseItemLocalService.
					deleteCommerceInventoryWarehouseItems(
						commerceInventoryWarehouse.getCompanyId(),
						cpInstanceUnitOfMeasure.getSku(),
						cpInstanceUnitOfMeasure.getKey());
			}
		}
	}

	@Override
	public void onAfterUpdate(
			CPInstanceUnitOfMeasure originalCPInstanceUnitOfMeasure,
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure)
		throws ModelListenerException {

		String key = cpInstanceUnitOfMeasure.getKey();

		if (key.equals(originalCPInstanceUnitOfMeasure.getKey())) {
			return;
		}

		_updateUnitOfMeasureKey(
			originalCPInstanceUnitOfMeasure.getCompanyId(),
			cpInstanceUnitOfMeasure.getKey(),
			originalCPInstanceUnitOfMeasure.getKey(),
			originalCPInstanceUnitOfMeasure.getSku());
	}

	private void _updateUnitOfMeasureKey(
		long companyId, String key, String originalKey, String sku) {

		for (CommerceInventoryBookedQuantity commerceInventoryBookedQuantity :
				_commerceInventoryBookedQuantityLocalService.
					getCommerceInventoryBookedQuantities(
						companyId, sku, originalKey, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS)) {

			commerceInventoryBookedQuantity.setUnitOfMeasureKey(key);

			_commerceInventoryBookedQuantityLocalService.
				updateCommerceInventoryBookedQuantity(
					commerceInventoryBookedQuantity);
		}

		for (CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem :
					_commerceInventoryReplenishmentItemLocalService.
						getCommerceInventoryReplenishmentItemsByCompanyIdSkuAndUnitOfMeasureKey(
							companyId, sku, originalKey, QueryUtil.ALL_POS,
							QueryUtil.ALL_POS)) {

			commerceInventoryReplenishmentItem.setUnitOfMeasureKey(key);

			_commerceInventoryReplenishmentItemLocalService.
				updateCommerceInventoryReplenishmentItem(
					commerceInventoryReplenishmentItem);
		}

		for (CommerceInventoryWarehouseItem commerceInventoryWarehouseItem :
				_commerceInventoryWarehouseItemLocalService.
					getCommerceInventoryWarehouseItemsByCompanyIdSkuAndUnitOfMeasureKey(
						companyId, sku, originalKey, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS)) {

			commerceInventoryWarehouseItem.setUnitOfMeasureKey(key);

			_commerceInventoryWarehouseItemLocalService.
				updateCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseItem);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPInstanceUnitOfMeasureModelListener.class);

	@Reference
	private CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;

	@Reference
	private CommerceInventoryReplenishmentItemLocalService
		_commerceInventoryReplenishmentItemLocalService;

	@Reference
	private CommerceInventoryWarehouseItemLocalService
		_commerceInventoryWarehouseItemLocalService;

	@Reference
	private CommerceInventoryWarehouseLocalService
		_commerceInventoryWarehouseLocalService;

	@Reference
	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

}