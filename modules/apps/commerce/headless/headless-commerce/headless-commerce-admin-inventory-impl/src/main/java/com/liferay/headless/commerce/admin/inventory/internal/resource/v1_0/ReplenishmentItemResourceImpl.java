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

package com.liferay.headless.commerce.admin.inventory.internal.resource.v1_0;

import com.liferay.commerce.inventory.exception.NoSuchInventoryReplenishmentItemException;
import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryReplenishmentItemService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemService;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.ReplenishmentItem;
import com.liferay.headless.commerce.admin.inventory.internal.dto.v1_0.ReplenishmentItemDTOConverter;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.ReplenishmentItemResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Crescenzo Rega
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/replenishment-item.properties",
	scope = ServiceScope.PROTOTYPE, service = ReplenishmentItemResource.class
)
public class ReplenishmentItemResourceImpl
	extends BaseReplenishmentItemResourceImpl {

	@Override
	public void deleteReplenishmentItem(Long replenishmentItemId)
		throws Exception {

		_commerceInventoryReplenishmentItemService.
			deleteCommerceInventoryReplenishmentItem(replenishmentItemId);
	}

	@Override
	public void deleteReplenishmentItemByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			_commerceInventoryReplenishmentItemService.
				fetchCommerceInventoryReplenishmentItemByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (commerceInventoryReplenishmentItem == null) {
			throw new NoSuchInventoryReplenishmentItemException(
				"Unable to find replenishment item with external reference " +
					"code " + externalReferenceCode);
		}

		_commerceInventoryReplenishmentItemService.
			deleteCommerceInventoryReplenishmentItem(
				commerceInventoryReplenishmentItem.
					getCommerceInventoryReplenishmentItemId());
	}

	@Override
	public ReplenishmentItem getReplenishmentItem(Long replenishmentItemId)
		throws Exception {

		return _toReplenishmentItem(
			_commerceInventoryReplenishmentItemService.
				getCommerceInventoryReplenishmentItem(replenishmentItemId));
	}

	@Override
	public ReplenishmentItem getReplenishmentItemByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		return _toReplenishmentItem(
			_fetchCommerceInventoryReplenishmentItemByExternalReferenceCode(
				externalReferenceCode));
	}

	@Override
	public Page<ReplenishmentItem> getReplenishmentItemsPage(
			String sku, Pagination pagination)
		throws Exception {

		return Page.of(
			TransformUtil.transform(
				_commerceInventoryReplenishmentItemService.
					getCommerceInventoryReplenishmentItemsByCompanyIdAndSku(
						contextCompany.getCompanyId(), sku,
						pagination.getStartPosition(),
						pagination.getEndPosition()),
				this::_toReplenishmentItem),
			pagination,
			_commerceInventoryReplenishmentItemService.
				getCommerceInventoryReplenishmentItemsCountByCompanyIdAndSku(
					contextCompany.getCompanyId(), sku));
	}

	@Override
	public Page<ReplenishmentItem> getWarehouseIdReplenishmentItemsPage(
			Long warehouseId, Pagination pagination)
		throws Exception {

		return Page.of(
			TransformUtil.transform(
				_commerceInventoryReplenishmentItemService.
					getCommerceInventoryReplenishmentItemsByCommerceInventoryWarehouseId(
						warehouseId, pagination.getStartPosition(),
						pagination.getEndPosition()),
				this::_toReplenishmentItem),
			pagination,
			_commerceInventoryReplenishmentItemService.
				getCommerceInventoryReplenishmentItemsCountByCommerceInventoryWarehouseId(
					warehouseId));
	}

	@Override
	public ReplenishmentItem patchReplenishmentItem(
			Long replenishmentItemId, ReplenishmentItem replenishmentItem)
		throws Exception {

		return _toReplenishmentItem(
			_updateCommerceInventoryReplenishmentItem(
				replenishmentItem,
				_commerceInventoryReplenishmentItemService.
					getCommerceInventoryReplenishmentItem(
						replenishmentItemId)));
	}

	@Override
	public ReplenishmentItem patchReplenishmentItemByExternalReferenceCode(
			String externalReferenceCode, ReplenishmentItem replenishmentItem)
		throws Exception {

		return _toReplenishmentItem(
			_updateCommerceInventoryReplenishmentItem(
				replenishmentItem,
				_fetchCommerceInventoryReplenishmentItemByExternalReferenceCode(
					externalReferenceCode)));
	}

	@Override
	public ReplenishmentItem postReplenishmentItem(
			Long warehouseId, String sku, ReplenishmentItem replenishmentItem)
		throws Exception {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemService.
				getCommerceInventoryWarehouseItem(warehouseId, sku);

		return _toReplenishmentItem(
			_commerceInventoryReplenishmentItemService.
				addCommerceInventoryReplenishmentItem(
					replenishmentItem.getExternalReferenceCode(),
					commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseId(),
					commerceInventoryWarehouseItem.getSku(),
					GetterUtil.getDate(
						replenishmentItem.getAvailabilityDate(),
						DateFormatFactoryUtil.getDate(
							contextAcceptLanguage.getPreferredLocale(),
							contextUser.getTimeZone())),
					GetterUtil.getInteger(replenishmentItem.getQuantity())));
	}

	private CommerceInventoryReplenishmentItem
			_fetchCommerceInventoryReplenishmentItemByExternalReferenceCode(
				String externalReferenceCode)
		throws Exception {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			_commerceInventoryReplenishmentItemService.
				fetchCommerceInventoryReplenishmentItemByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (commerceInventoryReplenishmentItem == null) {
			throw new NoSuchInventoryReplenishmentItemException(
				"Unable to find replenishment item with external reference " +
					"code " + externalReferenceCode);
		}

		return commerceInventoryReplenishmentItem;
	}

	private ReplenishmentItem _toReplenishmentItem(
			CommerceInventoryReplenishmentItem
				commerceInventoryReplenishmentItem)
		throws Exception {

		return _replenishmentItemDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry,
				commerceInventoryReplenishmentItem.
					getCommerceInventoryReplenishmentItemId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private CommerceInventoryReplenishmentItem
			_updateCommerceInventoryReplenishmentItem(
				ReplenishmentItem replenishmentItem,
				CommerceInventoryReplenishmentItem
					commerceInventoryReplenishmentItem)
		throws Exception {

		return _commerceInventoryReplenishmentItemService.
			updateCommerceInventoryReplenishmentItem(
				replenishmentItem.getExternalReferenceCode(),
				commerceInventoryReplenishmentItem.
					getCommerceInventoryReplenishmentItemId(),
				GetterUtil.getDate(
					replenishmentItem.getAvailabilityDate(),
					DateFormatFactoryUtil.getDate(
						contextAcceptLanguage.getPreferredLocale(),
						contextUser.getTimeZone()),
					commerceInventoryReplenishmentItem.getAvailabilityDate()),
				GetterUtil.getInteger(
					replenishmentItem.getQuantity(),
					commerceInventoryReplenishmentItem.getQuantity()),
				commerceInventoryReplenishmentItem.getMvccVersion());
	}

	@Reference
	private CommerceInventoryReplenishmentItemService
		_commerceInventoryReplenishmentItemService;

	@Reference
	private CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ReplenishmentItemDTOConverter _replenishmentItemDTOConverter;

}