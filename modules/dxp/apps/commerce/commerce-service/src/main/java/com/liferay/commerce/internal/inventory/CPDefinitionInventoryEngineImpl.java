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

package com.liferay.commerce.internal.inventory;

import com.liferay.commerce.constants.CPDefinitionInventoryConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.model.CPDefinitionAvailabilityRange;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceAvailabilityRange;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CPDefinitionAvailabilityRangeLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceWarehouseItemLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"cp.definition.inventory.engine.key=" + CPDefinitionInventoryEngineImpl.KEY,
		"cp.definition.inventory.engine.priority:Integer=1"
	},
	service = CPDefinitionInventoryEngine.class
)
public class CPDefinitionInventoryEngineImpl
	implements CPDefinitionInventoryEngine {

	public static final String KEY = "default";

	@Override
	public String[] getAllowedOrderQuantities(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return new String[0];
		}

		return StringUtil.split(
			cpDefinitionInventory.getAllowedOrderQuantities());
	}

	@Override
	public String getAvailabilityRange(CPInstance cpInstance, Locale locale)
		throws PortalException {

		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange =
			_cpDefinitionAvailabilityRangeLocalService.
				fetchCPDefinitionAvailabilityRangeByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionAvailabilityRange == null) {
			return StringPool.BLANK;
		}

		CommerceAvailabilityRange commerceAvailabilityRange =
			cpDefinitionAvailabilityRange.getCommerceAvailabilityRange();

		return commerceAvailabilityRange.getTitle(locale);
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, KEY);
	}

	@Override
	public int getMaxOrderQuantity(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return CPDefinitionInventoryConstants.DEFAULT_MAX_ORDER_QUANTITY;
		}

		return cpDefinitionInventory.getMaxOrderQuantity();
	}

	@Override
	public int getMinOrderQuantity(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return CPDefinitionInventoryConstants.DEFAULT_MIN_ORDER_QUANTITY;
		}

		return cpDefinitionInventory.getMinOrderQuantity();
	}

	@Override
	public int getMinStockQuantity(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return 0;
		}

		return cpDefinitionInventory.getMinStockQuantity();
	}

	@Override
	public int getMultipleOrderQuantity(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return
				CPDefinitionInventoryConstants.DEFAULT_MULTIPLE_ORDER_QUANTITY;
		}

		return cpDefinitionInventory.getMultipleOrderQuantity();
	}

	@Override
	public int getStockQuantity(CPInstance cpInstance) {
		int warehouseCPInstanceQuantity =
			_commerceWarehouseItemLocalService.getCPInstanceQuantity(
				cpInstance.getCPInstanceId());

		int orderCPInstanceQuantity =
			_commerceOrderItemLocalService.getCPInstanceQuantity(
				cpInstance.getCPInstanceId(),
				CommerceOrderConstants.ORDER_STATUS_COMPLETED);

		return warehouseCPInstanceQuantity - orderCPInstanceQuantity;
	}

	@Override
	public boolean isBackOrderAllowed(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return false;
		}

		return cpDefinitionInventory.getBackOrders();
	}

	@Override
	public boolean isDisplayAvailability(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return false;
		}

		return cpDefinitionInventory.getDisplayAvailability();
	}

	@Override
	public boolean isDisplayStockQuantity(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return false;
		}

		return cpDefinitionInventory.getDisplayStockQuantity();
	}

	@Override
	public int updateStockQuantity(
		CommerceWarehouseItem commerceWarehouseItem, int quantity) {

		quantity = commerceWarehouseItem.getQuantity() - quantity;

		commerceWarehouseItem.setQuantity(quantity);

		_commerceWarehouseItemLocalService.updateCommerceWarehouseItem(
			commerceWarehouseItem);

		return quantity;
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceWarehouseItemLocalService
		_commerceWarehouseItemLocalService;

	@Reference
	private CPDefinitionAvailabilityRangeLocalService
		_cpDefinitionAvailabilityRangeLocalService;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}