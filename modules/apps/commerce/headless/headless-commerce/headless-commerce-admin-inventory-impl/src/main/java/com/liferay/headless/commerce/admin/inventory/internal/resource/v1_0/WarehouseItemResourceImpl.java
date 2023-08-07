/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.internal.resource.v1_0;

import com.liferay.commerce.inventory.exception.CommerceInventoryInvalidDateException;
import com.liferay.commerce.inventory.exception.DuplicateCommerceInventoryWarehouseItemException;
import com.liferay.commerce.inventory.exception.NoSuchInventoryWarehouseException;
import com.liferay.commerce.inventory.exception.NoSuchInventoryWarehouseItemException;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.Warehouse;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.WarehouseItem;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.WarehouseItemResource;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/warehouse-item.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = WarehouseItemResource.class
)
public class WarehouseItemResourceImpl extends BaseWarehouseItemResourceImpl {

	@Override
	public Response deleteWarehouseItem(Long id) throws Exception {
		_commerceInventoryWarehouseItemService.
			deleteCommerceInventoryWarehouseItem(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response deleteWarehouseItemByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemService.
				fetchCommerceInventoryWarehouseItemByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (commerceInventoryWarehouseItem == null) {
			throw new NoSuchInventoryWarehouseItemException(
				"Unable to find warehouse item with external reference code " +
					externalReferenceCode);
		}

		_commerceInventoryWarehouseItemService.
			deleteCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseItemId());

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Page<WarehouseItem>
			getWarehouseByExternalReferenceCodeWarehouseItemsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceInventoryWarehouse == null) {
			throw new NoSuchInventoryWarehouseException(
				"Unable to find warehouse with external reference code " +
					externalReferenceCode);
		}

		List<CommerceInventoryWarehouseItem> commerceInventoryWarehouseItems =
			_commerceInventoryWarehouseItemService.
				getCommerceInventoryWarehouseItems(
					commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId(),
					pagination.getStartPosition(), pagination.getEndPosition());

		int totalItems =
			_commerceInventoryWarehouseItemService.
				getCommerceInventoryWarehouseItemsCount(
					commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId());

		return Page.of(
			_toWarehouseItems(commerceInventoryWarehouseItems), pagination,
			totalItems);
	}

	@NestedField(parentClass = Warehouse.class, value = "items")
	@Override
	public Page<WarehouseItem> getWarehouseIdWarehouseItemsPage(
			Long id, Pagination pagination)
		throws Exception {

		List<CommerceInventoryWarehouseItem> commerceInventoryWarehouseItems =
			_commerceInventoryWarehouseItemService.
				getCommerceInventoryWarehouseItems(
					id, pagination.getStartPosition(),
					pagination.getEndPosition());

		int totalItems =
			_commerceInventoryWarehouseItemService.
				getCommerceInventoryWarehouseItemsCount(id);

		return Page.of(
			_toWarehouseItems(commerceInventoryWarehouseItems), pagination,
			totalItems);
	}

	@Override
	public WarehouseItem getWarehouseItem(Long id) throws Exception {
		return _warehouseItemDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				GetterUtil.getLong(id),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public WarehouseItem getWarehouseItemByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemService.
				fetchCommerceInventoryWarehouseItemByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (commerceInventoryWarehouseItem == null) {
			throw new NoSuchInventoryWarehouseItemException(
				"Unable to find warehouse item with external reference code " +
					externalReferenceCode);
		}

		return _toWarehouseItem(commerceInventoryWarehouseItem);
	}

	@Override
	public Page<WarehouseItem> getWarehouseItemsUpdatedPage(
			Date end, Date start, Pagination pagination)
		throws Exception {

		if ((start != null) && (end != null) && (start.compareTo(end) > 0)) {
			throw new CommerceInventoryInvalidDateException(
				"End date should be after start date");
		}

		if ((start == null) && (end == null)) {
			start = new Date();
		}

		if (start == null) {
			start = _addDaysToDate(end, -_DEFAULT_INCREMENT_DAYS);
		}

		if (end == null) {
			end = _addDaysToDate(start, _DEFAULT_INCREMENT_DAYS);
		}

		List<CommerceInventoryWarehouseItem> commerceInventoryWarehouseItems =
			_commerceInventoryWarehouseItemService.
				getCommerceInventoryWarehouseItemsCountByModifiedDate(
					contextCompany.getCompanyId(), start, end,
					pagination.getStartPosition(), pagination.getEndPosition());

		int totalItems =
			_commerceInventoryWarehouseItemService.
				getCommerceInventoryWarehouseItemsCountByModifiedDate(
					contextCompany.getCompanyId(), start, end);

		return Page.of(
			_toWarehouseItems(commerceInventoryWarehouseItems), pagination,
			totalItems);
	}

	@Override
	public Response patchWarehouseItem(Long id, WarehouseItem warehouseItem)
		throws Exception {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemService.
				getCommerceInventoryWarehouseItem(id);

		_commerceInventoryWarehouseItemService.
			updateCommerceInventoryWarehouseItem(
				id, commerceInventoryWarehouseItem.getMvccVersion(),
				BigDecimalUtil.get(
					warehouseItem.getQuantity(),
					commerceInventoryWarehouseItem.getQuantity()),
				GetterUtil.get(
					warehouseItem.getUnitOfMeasureKey(),
					commerceInventoryWarehouseItem.getUnitOfMeasureKey()));

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response patchWarehouseItemByExternalReferenceCode(
			String externalReferenceCode, WarehouseItem warehouseItem)
		throws Exception {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemService.
				fetchCommerceInventoryWarehouseItemByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (commerceInventoryWarehouseItem == null) {
			throw new NoSuchInventoryWarehouseItemException(
				"Unable to find warehouse item with external reference code " +
					externalReferenceCode);
		}

		_commerceInventoryWarehouseItemService.
			updateCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseItemId(),
				commerceInventoryWarehouseItem.getMvccVersion(),
				BigDecimalUtil.get(
					warehouseItem.getQuantity(),
					commerceInventoryWarehouseItem.getQuantity()),
				GetterUtil.get(
					warehouseItem.getUnitOfMeasureKey(),
					commerceInventoryWarehouseItem.getUnitOfMeasureKey()));

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public WarehouseItem postWarehouseByExternalReferenceCodeWarehouseItem(
			String externalReferenceCode, WarehouseItem warehouseItem)
		throws Exception {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceInventoryWarehouse == null) {
			throw new NoSuchInventoryWarehouseException(
				"Unable to find warehouse with external reference code " +
					externalReferenceCode);
		}

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemService.
				addCommerceInventoryWarehouseItem(
					warehouseItem.getExternalReferenceCode(),
					commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId(),
					BigDecimalUtil.get(
						warehouseItem.getQuantity(), BigDecimal.ZERO),
					warehouseItem.getSku(),
					warehouseItem.getUnitOfMeasureKey());

		return _warehouseItemDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseItemId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public WarehouseItem postWarehouseIdWarehouseItem(
			Long id, WarehouseItem warehouseItem)
		throws Exception {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseService.getCommerceInventoryWarehouse(
				id);

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemService.
				addCommerceInventoryWarehouseItem(
					warehouseItem.getExternalReferenceCode(),
					commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId(),
					BigDecimalUtil.get(
						warehouseItem.getQuantity(), BigDecimal.ZERO),
					warehouseItem.getSku(),
					warehouseItem.getUnitOfMeasureKey());

		return _warehouseItemDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseItemId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public WarehouseItem postWarehouseItemByExternalReferenceCode(
			String externalReferenceCode, WarehouseItem warehouseItem)
		throws Exception {

		CommerceInventoryWarehouse commerceInventoryWarehouse = null;

		if (warehouseItem.getWarehouseId() != null) {
			commerceInventoryWarehouse =
				_commerceInventoryWarehouseService.
					getCommerceInventoryWarehouse(
						warehouseItem.getWarehouseId());
		}
		else if (warehouseItem.getWarehouseExternalReferenceCode() != null) {
			commerceInventoryWarehouse =
				_commerceInventoryWarehouseService.fetchByExternalReferenceCode(
					warehouseItem.getWarehouseExternalReferenceCode(),
					contextUser.getCompanyId());
		}

		if (commerceInventoryWarehouse == null) {
			throw new NoSuchInventoryWarehouseException(
				"Unable to find Warehouse");
		}

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemService.
				fetchCommerceInventoryWarehouseItemByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (commerceInventoryWarehouseItem != null) {
			throw new DuplicateCommerceInventoryWarehouseItemException(
				"External reference code already associated with this " +
					"Warehouse");
		}

		commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemService.
				addCommerceInventoryWarehouseItem(
					externalReferenceCode,
					commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId(),
					BigDecimalUtil.get(
						warehouseItem.getQuantity(), BigDecimal.ZERO),
					warehouseItem.getSku(),
					warehouseItem.getUnitOfMeasureKey());

		return _warehouseItemDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseItemId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	private Date _addDaysToDate(Date date, int increment) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.DATE, increment);

		return cal.getTime();
	}

	private WarehouseItem _toWarehouseItem(
			CommerceInventoryWarehouseItem commerceInventoryWarehouseItem)
		throws Exception {

		return _warehouseItemDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseItemId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<WarehouseItem> _toWarehouseItems(
			List<CommerceInventoryWarehouseItem>
				commerceInventoryWarehouseItems)
		throws Exception {

		List<WarehouseItem> warehouseItems = new ArrayList<>();

		for (CommerceInventoryWarehouseItem commerceInventoryWarehouseItem :
				commerceInventoryWarehouseItems) {

			warehouseItems.add(
				_warehouseItemDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						commerceInventoryWarehouseItem.
							getCommerceInventoryWarehouseItemId(),
						contextAcceptLanguage.getPreferredLocale())));
		}

		return warehouseItems;
	}

	private static final int _DEFAULT_INCREMENT_DAYS = 30;

	@Reference
	private CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;

	@Reference
	private CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.inventory.internal.dto.v1_0.converter.WarehouseItemDTOConverter)"
	)
	private DTOConverter<CommerceInventoryWarehouseItem, WarehouseItem>
		_warehouseItemDTOConverter;

}