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

package com.liferay.headless.commerce.admin.shipment.internal.resource.v1_0;

import com.liferay.commerce.exception.NoSuchShipmentException;
import com.liferay.commerce.exception.NoSuchShipmentItemException;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.commerce.service.CommerceShipmentService;
import com.liferay.headless.commerce.admin.shipment.dto.v1_0.Shipment;
import com.liferay.headless.commerce.admin.shipment.dto.v1_0.ShipmentItem;
import com.liferay.headless.commerce.admin.shipment.internal.dto.v1_0.converter.ShipmentItemDTOConverter;
import com.liferay.headless.commerce.admin.shipment.resource.v1_0.ShipmentItemResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/shipment-item.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, ShipmentItemResource.class}
)
public class ShipmentItemResourceImpl
	extends BaseShipmentItemResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteShipmentItem(Long shipmentItemId) throws Exception {
		_commerceShipmentItemService.deleteCommerceShipmentItem(
			shipmentItemId, Boolean.FALSE);
	}

	@Override
	public void deleteShipmentItemByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceShipmentItem commerceShipmentItem =
			_commerceShipmentItemService.fetchCommerceShipmentItem(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceShipmentItem == null) {
			throw new NoSuchShipmentItemException(
				"Unable to find shipment item with external reference code " +
					externalReferenceCode);
		}

		_commerceShipmentItemService.deleteCommerceShipmentItem(
			commerceShipmentItem.getCommerceShipmentItemId(), Boolean.FALSE);
	}

	@Override
	public ShipmentItem getShipmentByExternalReferenceCodeItem(
			String externalReferenceCode)
		throws Exception {

		CommerceShipmentItem commerceShipmentItem =
			_commerceShipmentItemService.fetchCommerceShipmentItem(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceShipmentItem == null) {
			throw new NoSuchShipmentItemException(
				"Unable to find shipment item with external reference code " +
					externalReferenceCode);
		}

		return _toShipmentItem(commerceShipmentItem);
	}

	@Override
	public Page<ShipmentItem> getShipmentByExternalReferenceCodeItemsPage(
			String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentService.fetchCommerceShipment(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceShipment == null) {
			throw new NoSuchShipmentException(
				"Unable to find shipment with external reference code " +
					externalReferenceCode);
		}

		return Page.of(
			transform(
				_commerceShipmentItemService.getCommerceShipmentItems(
					commerceShipment.getCommerceShipmentId(),
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toShipmentItem),
			pagination,
			_commerceShipmentItemService.getCommerceShipmentItemsCount(
				commerceShipment.getCommerceShipmentId()));
	}

	@Override
	public ShipmentItem getShipmentItem(Long shipmentItemId) throws Exception {
		return _toShipmentItem(shipmentItemId);
	}

	@NestedField(parentClass = Shipment.class, value = "shipmentItems")
	@Override
	public Page<ShipmentItem> getShipmentItemsPage(
			@NestedFieldId(value = "id") Long shipmentId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_commerceShipmentItemService.getCommerceShipmentItems(
					shipmentId, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toShipmentItem),
			pagination,
			_commerceShipmentItemService.getCommerceShipmentItemsCount(
				shipmentId));
	}

	@Override
	public ShipmentItem patchShipmentItem(
			Long shipmentItemId, ShipmentItem shipmentItem)
		throws Exception {

		CommerceShipmentItem commerceShipmentItem =
			_commerceShipmentItemService.getCommerceShipmentItem(
				shipmentItemId);

		_commerceShipmentItemService.updateCommerceShipmentItem(
			shipmentItemId,
			GetterUtil.get(
				shipmentItem.getWarehouseId(),
				commerceShipmentItem.getCommerceInventoryWarehouseId()),
			GetterUtil.get(
				shipmentItem.getQuantity(),
				commerceShipmentItem.getQuantity()));

		if (!Validator.isBlank(shipmentItem.getExternalReferenceCode())) {
			_commerceShipmentItemService.
				updateCommerceShipmentItemExternalReferenceCode(
					shipmentItem.getExternalReferenceCode(),
					commerceShipmentItem.getCommerceShipmentItemId());
		}

		return _toShipmentItem(shipmentItemId);
	}

	@Override
	public ShipmentItem patchShipmentItemByExternalReferenceCode(
			String externalReferenceCode, ShipmentItem shipmentItem)
		throws Exception {

		CommerceShipmentItem commerceShipmentItem =
			_commerceShipmentItemService.fetchCommerceShipmentItem(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceShipmentItem == null) {
			throw new NoSuchShipmentItemException(
				"Unable to find shipment item with external reference code " +
					externalReferenceCode);
		}

		_commerceShipmentItemService.updateCommerceShipmentItem(
			commerceShipmentItem.getCommerceShipmentItemId(),
			GetterUtil.get(
				shipmentItem.getWarehouseId(),
				commerceShipmentItem.getCommerceInventoryWarehouseId()),
			GetterUtil.get(
				shipmentItem.getQuantity(),
				commerceShipmentItem.getQuantity()));

		return _toShipmentItem(commerceShipmentItem);
	}

	@Override
	public ShipmentItem postShipmentItem(
			Long shipmentId, ShipmentItem shipmentItem)
		throws Exception {

		CommerceShipmentItem commerceShipmentItem =
			_commerceShipmentItemService.addCommerceShipmentItem(
				shipmentItem.getExternalReferenceCode(), shipmentId,
				shipmentItem.getOrderItemId(), shipmentItem.getWarehouseId(),
				shipmentItem.getQuantity(),
				_serviceContextHelper.getServiceContext(contextUser));

		return _toShipmentItem(commerceShipmentItem);
	}

	@Override
	public ShipmentItem putShipmentByExternalReferenceCodeItem(
			String externalReferenceCode, ShipmentItem shipmentItem)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentService.fetchCommerceShipment(
				contextCompany.getCompanyId(),
				shipmentItem.getShipmentExternalReferenceCode());

		if (commerceShipment == null) {
			commerceShipment = _commerceShipmentService.getCommerceShipment(
				GetterUtil.getLong(shipmentItem.getShipmentId()));
		}

		long defaultOrderItemId = 0;
		int defaultQuantity = 0;
		long defaultWarehouseId = 0;

		CommerceShipmentItem commerceShipmentItem =
			_commerceShipmentItemService.fetchCommerceShipmentItem(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceShipmentItem != null) {
			defaultOrderItemId = commerceShipmentItem.getCommerceOrderItemId();
			defaultQuantity = commerceShipmentItem.getQuantity();
			defaultWarehouseId =
				commerceShipmentItem.getCommerceInventoryWarehouseId();
		}

		return _toShipmentItem(
			_commerceShipmentItemService.addOrUpdateCommerceShipmentItem(
				externalReferenceCode, commerceShipment.getCommerceShipmentId(),
				GetterUtil.getLong(
					shipmentItem.getOrderItemId(), defaultOrderItemId),
				GetterUtil.getLong(
					shipmentItem.getWarehouseId(), defaultWarehouseId),
				GetterUtil.getInteger(
					shipmentItem.getQuantity(), defaultQuantity),
				_serviceContextHelper.getServiceContext()));
	}

	private Map<String, Map<String, String>> _getActions(
		CommerceShipmentItem commerceShipmentItem) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"DELETE", commerceShipmentItem.getCommerceShipmentItemId(),
				"deleteShipmentItem", commerceShipmentItem.getUserId(),
				"com.liferay.commerce.model.CommerceShipmentItem",
				commerceShipmentItem.getGroupId())
		).put(
			"get",
			addAction(
				"VIEW", commerceShipmentItem.getCommerceShipmentItemId(),
				"getShipmentItem", commerceShipmentItem.getUserId(),
				"com.liferay.commerce.model.CommerceShipmentItem",
				commerceShipmentItem.getGroupId())
		).put(
			"update",
			addAction(
				"UPDATE", commerceShipmentItem.getCommerceShipmentItemId(),
				"patchShipmentItem", commerceShipmentItem.getUserId(),
				"com.liferay.commerce.model.CommerceShipmentItem",
				commerceShipmentItem.getGroupId())
		).build();
	}

	private ShipmentItem _toShipmentItem(
			CommerceShipmentItem commerceShipmentItem)
		throws Exception {

		return _shipmentItemDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceShipmentItem), _dtoConverterRegistry,
				commerceShipmentItem.getCommerceShipmentItemId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private ShipmentItem _toShipmentItem(long shipmentItemId) throws Exception {
		return _toShipmentItem(
			_commerceShipmentItemService.getCommerceShipmentItem(
				shipmentItemId));
	}

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

	@Reference
	private CommerceShipmentService _commerceShipmentService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference
	private ShipmentItemDTOConverter _shipmentItemDTOConverter;

}