/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.internal.resource.v1_0;

import com.liferay.commerce.inventory.exception.NoSuchInventoryReplenishmentItemException;
import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryReplenishmentItemService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemService;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.ReplenishmentItem;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.ReplenishmentItemResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.math.BigDecimal;

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
			transform(
				_commerceInventoryReplenishmentItemService.
					getCommerceInventoryReplenishmentItemsByCompanyIdSkuAndUnitOfMeasureKey(
						contextCompany.getCompanyId(), sku, StringPool.BLANK,
						pagination.getStartPosition(),
						pagination.getEndPosition()),
				this::_toReplenishmentItem),
			pagination,
			_commerceInventoryReplenishmentItemService.
				getCommerceInventoryReplenishmentItemsCountByCompanyIdSkuAndUnitOfMeasureKey(
					contextCompany.getCompanyId(), sku, StringPool.BLANK));
	}

	@Override
	public Page<ReplenishmentItem> getWarehouseIdReplenishmentItemsPage(
			Long warehouseId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
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

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			_commerceInventoryReplenishmentItemService.
				getCommerceInventoryReplenishmentItem(replenishmentItemId);

		BigDecimal commerceInventoryWarehouseItemQuantity =
			commerceInventoryReplenishmentItem.getQuantity();

		return _toReplenishmentItem(
			_commerceInventoryReplenishmentItemService.
				updateCommerceInventoryReplenishmentItem(
					replenishmentItem.getExternalReferenceCode(),
					commerceInventoryReplenishmentItem.
						getCommerceInventoryReplenishmentItemId(),
					GetterUtil.getDate(
						replenishmentItem.getAvailabilityDate(),
						DateFormatFactoryUtil.getDate(
							contextAcceptLanguage.getPreferredLocale(),
							contextUser.getTimeZone()),
						commerceInventoryReplenishmentItem.
							getAvailabilityDate()),
					BigDecimal.valueOf(
						GetterUtil.getInteger(
							replenishmentItem.getQuantity(),
							commerceInventoryWarehouseItemQuantity.intValue())),
					commerceInventoryReplenishmentItem.getMvccVersion()));
	}

	@Override
	public ReplenishmentItem patchReplenishmentItemByExternalReferenceCode(
			String externalReferenceCode, ReplenishmentItem replenishmentItem)
		throws Exception {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			_fetchCommerceInventoryReplenishmentItemByExternalReferenceCode(
				externalReferenceCode);

		BigDecimal commerceInventoryWarehouseItemQuantity =
			commerceInventoryReplenishmentItem.getQuantity();

		return _toReplenishmentItem(
			_commerceInventoryReplenishmentItemService.
				updateCommerceInventoryReplenishmentItem(
					commerceInventoryReplenishmentItem.
						getExternalReferenceCode(),
					commerceInventoryReplenishmentItem.
						getCommerceInventoryReplenishmentItemId(),
					GetterUtil.getDate(
						replenishmentItem.getAvailabilityDate(),
						DateFormatFactoryUtil.getDate(
							contextAcceptLanguage.getPreferredLocale(),
							contextUser.getTimeZone()),
						commerceInventoryReplenishmentItem.
							getAvailabilityDate()),
					BigDecimal.valueOf(
						GetterUtil.getInteger(
							replenishmentItem.getQuantity(),
							commerceInventoryWarehouseItemQuantity.intValue())),
					commerceInventoryReplenishmentItem.getMvccVersion()));
	}

	@Override
	public ReplenishmentItem postReplenishmentItem(
			Long warehouseId, String sku, ReplenishmentItem replenishmentItem)
		throws Exception {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemService.
				getCommerceInventoryWarehouseItem(
					warehouseId, sku, StringPool.BLANK);

		return _toReplenishmentItem(
			_commerceInventoryReplenishmentItemService.
				addCommerceInventoryReplenishmentItem(
					replenishmentItem.getExternalReferenceCode(),
					commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseId(),
					GetterUtil.getDate(
						replenishmentItem.getAvailabilityDate(),
						DateFormatFactoryUtil.getDate(
							contextAcceptLanguage.getPreferredLocale(),
							contextUser.getTimeZone())),
					BigDecimal.valueOf(
						GetterUtil.getInteger(replenishmentItem.getQuantity())),
					commerceInventoryWarehouseItem.getSku(), StringPool.BLANK));
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

	@Reference
	private CommerceInventoryReplenishmentItemService
		_commerceInventoryReplenishmentItemService;

	@Reference
	private CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.inventory.internal.dto.v1_0.converter.ReplenishmentItemDTOConverter)"
	)
	private DTOConverter<CommerceInventoryReplenishmentItem, ReplenishmentItem>
		_replenishmentItemDTOConverter;

}