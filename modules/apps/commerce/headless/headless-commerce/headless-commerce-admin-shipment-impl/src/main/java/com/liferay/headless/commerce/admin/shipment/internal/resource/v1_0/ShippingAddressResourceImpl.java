/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.shipment.internal.resource.v1_0;

import com.liferay.commerce.exception.NoSuchShipmentException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceShipmentService;
import com.liferay.headless.commerce.admin.shipment.dto.v1_0.Shipment;
import com.liferay.headless.commerce.admin.shipment.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.admin.shipment.internal.util.v1_0.ShippingAddressUtil;
import com.liferay.headless.commerce.admin.shipment.resource.v1_0.ShippingAddressResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/shipping-address.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = ShippingAddressResource.class
)
public class ShippingAddressResourceImpl
	extends BaseShippingAddressResourceImpl {

	@Override
	public ShippingAddress getShipmentByExternalReferenceCodeShippingAddress(
			String externalReferenceCode)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentService.
				fetchCommerceShipmentByExternalReferenceCode(
					contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceShipment == null) {
			throw new NoSuchShipmentException(
				"Unable to find shipment with external reference code " +
					externalReferenceCode);
		}

		CommerceAddress commerceAddress =
			_commerceAddressService.fetchCommerceAddress(
				commerceShipment.getCommerceAddressId());

		if (commerceAddress == null) {
			return new ShippingAddress();
		}

		return _shippingAddressDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAddress.getCommerceAddressId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = Shipment.class, value = "shippingAddress")
	@Override
	public ShippingAddress getShipmentShippingAddress(
			@NestedFieldId(value = "id") Long shipmentId)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentService.getCommerceShipment(shipmentId);

		CommerceAddress commerceAddress =
			_commerceAddressService.fetchCommerceAddress(
				commerceShipment.getCommerceAddressId());

		if (commerceAddress == null) {
			return new ShippingAddress();
		}

		return _shippingAddressDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAddress.getCommerceAddressId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public ShippingAddress patchShipmentByExternalReferenceCodeShippingAddress(
			String externalReferenceCode, ShippingAddress shippingAddress)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentService.
				fetchCommerceShipmentByExternalReferenceCode(
					contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceShipment == null) {
			throw new NoSuchShipmentException(
				"Unable to find shipment with external reference code " +
					externalReferenceCode);
		}

		commerceShipment = ShippingAddressUtil.updateShippingAddress(
			_commerceAddressService, _commerceShipmentService, commerceShipment,
			_countryService, _regionService, shippingAddress,
			_serviceContextHelper);

		return _shippingAddressDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceShipment.getCommerceAddressId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public ShippingAddress patchShipmentShippingAddress(
			Long shipmentId, ShippingAddress shippingAddress)
		throws Exception {

		CommerceShipment commerceShipment =
			_commerceShipmentService.getCommerceShipment(shipmentId);

		commerceShipment = ShippingAddressUtil.updateShippingAddress(
			_commerceAddressService, _commerceShipmentService, commerceShipment,
			_countryService, _regionService, shippingAddress,
			_serviceContextHelper);

		return _shippingAddressDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceShipment.getCommerceAddressId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceShipmentService _commerceShipmentService;

	@Reference
	private CountryService _countryService;

	@Reference
	private RegionService _regionService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.shipment.internal.dto.v1_0.converter.ShippingAddressDTOConverter)"
	)
	private DTOConverter<CommerceAddress, ShippingAddress>
		_shippingAddressDTOConverter;

}