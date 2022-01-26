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

package com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelQualifierService;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.PaymentMethodGroupRelOrderType;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier-OrderType",
	service = {
		DTOConverter.class, PaymentMethodGroupRelOrderTypeDTOConverter.class
	}
)
public class PaymentMethodGroupRelOrderTypeDTOConverter
	implements DTOConverter
		<CommercePaymentMethodGroupRelQualifier,
		 PaymentMethodGroupRelOrderType> {

	@Override
	public String getContentType() {
		return PaymentMethodGroupRelOrderType.class.getSimpleName();
	}

	@Override
	public PaymentMethodGroupRelOrderType toDTO(
			DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier =
				_commercePaymentMethodGroupRelQualifierService.
					getCommercePaymentMethodGroupRelQualifier(
						(Long)dtoConverterContext.getId());

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeService.getCommerceOrderType(
				commercePaymentMethodGroupRelQualifier.getClassPK());

		return new PaymentMethodGroupRelOrderType() {
			{
				actions = dtoConverterContext.getActions();
				orderTypeExternalReferenceCode =
					commerceOrderType.getExternalReferenceCode();
				orderTypeId = commerceOrderType.getCommerceOrderTypeId();
				paymentMethodGroupRelId =
					commercePaymentMethodGroupRelQualifier.
						getCommercePaymentMethodGroupRelId();
				paymentMethodGroupRelOrderTypeId =
					commercePaymentMethodGroupRelQualifier.
						getCommercePaymentMethodGroupRelQualifierId();
			}
		};
	}

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference
	private CommercePaymentMethodGroupRelQualifierService
		_commercePaymentMethodGroupRelQualifierService;

	@Reference
	private CommercePaymentMethodGroupRelService
		_commercePaymentMethodGroupRelService;

}