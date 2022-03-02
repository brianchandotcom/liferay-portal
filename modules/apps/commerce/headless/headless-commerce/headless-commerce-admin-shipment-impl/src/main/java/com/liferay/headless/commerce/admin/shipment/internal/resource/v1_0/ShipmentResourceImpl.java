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

import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.exception.NoSuchShipmentException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceShipmentService;
import com.liferay.headless.commerce.admin.shipment.dto.v1_0.Shipment;
import com.liferay.headless.commerce.admin.shipment.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.admin.shipment.internal.dto.v1_0.converter.ShipmentDTOConverter;
import com.liferay.headless.commerce.admin.shipment.internal.util.v1_0.ShippingAddressUtil;
import com.liferay.headless.commerce.admin.shipment.resource.v1_0.ShipmentResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Calendar;
import java.util.Date;
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
	properties = "OSGI-INF/liferay/rest/v1_0/shipment.properties",
	scope = ServiceScope.PROTOTYPE, service = ShipmentResource.class
)
public class ShipmentResourceImpl extends BaseShipmentResourceImpl {

	@Override
	public void deleteShipment(Long shipmentId) throws Exception {
		_commerceShipmentService.deleteCommerceShipment(
			shipmentId, Boolean.FALSE);
	}

	@Override
	public void deleteShipmentByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentService.fetchCommerceShipment(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceShipment == null) {
			throw new NoSuchShipmentException(
				"Unable to find shipment with external reference code " +
					externalReferenceCode);
		}

		_commerceShipmentService.deleteCommerceShipment(
			commerceShipment.getCommerceShipmentId(), Boolean.FALSE);
	}

	@Override
	public Shipment getShipment(Long shipmentId) throws Exception {
		return _toShipment(shipmentId);
	}

	@Override
	public Shipment getShipmentByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentService.fetchCommerceShipment(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceShipment == null) {
			throw new NoSuchShipmentException(
				"Unable to find shipment with external reference code " +
					externalReferenceCode);
		}

		return _toShipment(commerceShipment);
	}

	@Override
	public Page<Shipment> getShipmentsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			null, booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			CommerceShipment.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			new UnsafeConsumer() {

				public void accept(Object object) throws Exception {
					SearchContext searchContext = (SearchContext)object;

					searchContext.setAttribute(
						"status", WorkflowConstants.STATUS_ANY);
					searchContext.setCompanyId(contextCompany.getCompanyId());
				}

			},
			sorts,
			document -> _toShipment(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public Shipment patchShipment(Long shipmentId, Shipment shipment)
		throws Exception {

		_updateCommerceShipment(
			_commerceShipmentService.getCommerceShipment(shipmentId), shipment);

		if (!Validator.isBlank(shipment.getExternalReferenceCode())) {
			_commerceShipmentService.
				updateCommerceShipmentExternalReferenceCode(
					shipment.getExternalReferenceCode(), shipmentId);
		}

		return _toShipment(shipmentId);
	}

	@Override
	public Shipment patchShipmentByExternalReferenceCode(
			String externalReferenceCode, Shipment shipment)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentService.fetchCommerceShipment(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceShipment == null) {
			throw new NoSuchShipmentException(
				"Unable to find shipment with external reference code " +
					externalReferenceCode);
		}

		_updateCommerceShipment(commerceShipment, shipment);

		return _toShipment(commerceShipment);
	}

	@Override
	public Shipment postShipment(Shipment shipment) throws Exception {
		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			shipment.getOrderId());

		CommerceShipment commerceShipment =
			_commerceShipmentService.addCommerceShipment(
				shipment.getExternalReferenceCode(), commerceOrder.getGroupId(),
				commerceOrder.getCommerceAccountId(),
				commerceOrder.getShippingAddressId(),
				commerceOrder.getCommerceShippingMethodId(),
				commerceOrder.getShippingOptionName(),
				_serviceContextHelper.getServiceContext(contextUser));

		return _toShipment(commerceShipment.getCommerceShipmentId());
	}

	@Override
	public Shipment postShipmentByExternalReferenceCodeStatusDelivered(
			String externalReferenceCode)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentService.fetchCommerceShipment(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceShipment == null) {
			throw new NoSuchShipmentException(
				"Unable to find shipment with external reference code " +
					externalReferenceCode);
		}

		return _toShipment(
			_commerceShipmentService.updateStatus(
				commerceShipment.getCommerceShipmentId(),
				CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED));
	}

	@Override
	public Shipment postShipmentByExternalReferenceCodeStatusFinishProcessing(
			String externalReferenceCode)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentService.fetchCommerceShipment(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceShipment == null) {
			throw new NoSuchShipmentException(
				"Unable to find shipment with external reference code " +
					externalReferenceCode);
		}

		return _toShipment(
			_commerceShipmentService.updateStatus(
				commerceShipment.getCommerceShipmentId(),
				CommerceShipmentConstants.SHIPMENT_STATUS_READY_TO_BE_SHIPPED));
	}

	@Override
	public Shipment postShipmentByExternalReferenceCodeStatusShipped(
			String externalReferenceCode)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentService.fetchCommerceShipment(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceShipment == null) {
			throw new NoSuchShipmentException(
				"Unable to find shipment with external reference code " +
					externalReferenceCode);
		}

		return _toShipment(
			_commerceShipmentService.updateStatus(
				commerceShipment.getCommerceShipmentId(),
				CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED));
	}

	@Override
	public Shipment postShipmentStatusDelivered(Long shipmentId)
		throws Exception {

		return _toShipment(
			_commerceShipmentService.updateStatus(
				shipmentId,
				CommerceShipmentConstants.SHIPMENT_STATUS_DELIVERED));
	}

	@Override
	public Shipment postShipmentStatusFinishProcessing(Long shipmentId)
		throws Exception {

		return _toShipment(
			_commerceShipmentService.updateStatus(
				shipmentId,
				CommerceShipmentConstants.SHIPMENT_STATUS_READY_TO_BE_SHIPPED));
	}

	@Override
	public Shipment postShipmentStatusShipped(Long shipmentId)
		throws Exception {

		return _toShipment(
			_commerceShipmentService.updateStatus(
				shipmentId, CommerceShipmentConstants.SHIPMENT_STATUS_SHIPPED));
	}

	@Override
	public Shipment putShipmentByExternalReferenceCode(
			String externalReferenceCode, Shipment shipment)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentService.fetchCommerceShipment(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceShipment == null) {
			CommerceOrder commerceOrder =
				_commerceOrderService.getCommerceOrder(shipment.getOrderId());

			commerceShipment = _commerceShipmentService.addCommerceShipment(
				shipment.getExternalReferenceCode(), commerceOrder.getGroupId(),
				commerceOrder.getCommerceAccountId(),
				commerceOrder.getShippingAddressId(),
				commerceOrder.getCommerceShippingMethodId(),
				commerceOrder.getShippingOptionName(),
				_serviceContextHelper.getServiceContext(contextUser));
		}

		_updateCommerceShipment(commerceShipment, shipment);

		return _toShipment(commerceShipment);
	}

	private Map<String, Map<String, String>> _getActions(
		CommerceShipment commerceShipment) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"DELETE", commerceShipment.getCommerceShipmentId(),
				"deleteShipment", commerceShipment.getUserId(),
				"com.liferay.commerce.model.CommerceShipment",
				commerceShipment.getGroupId())
		).put(
			"get",
			addAction(
				"VIEW", commerceShipment.getCommerceShipmentId(), "getShipment",
				commerceShipment.getUserId(),
				"com.liferay.commerce.model.CommerceShipment",
				commerceShipment.getGroupId())
		).put(
			"update",
			addAction(
				"UPDATE", commerceShipment.getCommerceShipmentId(),
				"patchShipment", commerceShipment.getUserId(),
				"com.liferay.commerce.model.CommerceShipment",
				commerceShipment.getGroupId())
		).build();
	}

	private Shipment _toShipment(CommerceShipment commerceShipment)
		throws Exception {

		return _toShipment(commerceShipment.getCommerceShipmentId());
	}

	private Shipment _toShipment(long commerceShipmentId) throws Exception {
		CommerceShipment commerceShipment =
			_commerceShipmentService.getCommerceShipment(commerceShipmentId);

		return _shipmentDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceShipment), _dtoConverterRegistry,
				commerceShipmentId, contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser));
	}

	private void _updateCommerceShipment(
			CommerceShipment commerceShipment, Shipment shipment)
		throws Exception {

		_commerceShipmentService.updateCarrierDetails(
			commerceShipment.getCommerceShipmentId(),
			GetterUtil.get(
				shipment.getCarrier(), commerceShipment.getCarrier()),
			GetterUtil.get(
				shipment.getTrackingNumber(),
				commerceShipment.getTrackingNumber()));

		Date expectedDate = shipment.getExpectedDate();

		if (expectedDate != null) {
			Calendar calendar = CalendarFactoryUtil.getCalendar(
				expectedDate.getTime());

			int expectedDay = calendar.get(Calendar.DAY_OF_MONTH);
			int expectedMonth = calendar.get(Calendar.MONTH);
			int expectedYear = calendar.get(Calendar.YEAR);
			int expectedHour = calendar.get(Calendar.HOUR_OF_DAY);
			int expectedMinute = calendar.get(Calendar.MINUTE);

			_commerceShipmentService.updateExpectedDate(
				commerceShipment.getCommerceShipmentId(), expectedMonth,
				expectedDay, expectedYear, expectedHour, expectedMinute);
		}

		ShippingAddress shippingAddress = shipment.getShippingAddress();

		if (shippingAddress != null) {
			ShippingAddressUtil.updateShippingAddress(
				_commerceAddressService, _commerceShipmentService,
				commerceShipment, _countryService, _regionService,
				shippingAddress, _serviceContextHelper);
		}

		Date shippingDate = shipment.getShippingDate();

		if (shippingDate != null) {
			Calendar calendar = CalendarFactoryUtil.getCalendar(
				shippingDate.getTime());

			int shippingDay = calendar.get(Calendar.DAY_OF_MONTH);
			int shippingMonth = calendar.get(Calendar.MONTH);
			int shippingYear = calendar.get(Calendar.YEAR);
			int shippingHour = calendar.get(Calendar.HOUR_OF_DAY);
			int shippingMinute = calendar.get(Calendar.MINUTE);

			_commerceShipmentService.updateShippingDate(
				commerceShipment.getCommerceShipmentId(), shippingMonth,
				shippingDay, shippingYear, shippingHour, shippingMinute);
		}
	}

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceShipmentService _commerceShipmentService;

	@Reference
	private CountryService _countryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private RegionService _regionService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference
	private ShipmentDTOConverter _shipmentDTOConverter;

}