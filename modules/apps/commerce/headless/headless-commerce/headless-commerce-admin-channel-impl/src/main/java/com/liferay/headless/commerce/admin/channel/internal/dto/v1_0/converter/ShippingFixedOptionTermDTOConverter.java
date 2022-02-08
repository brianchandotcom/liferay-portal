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

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifier;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionQualifierService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.ShippingFixedOptionTerm;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.payment.model.CommerceShippingFixedOptionQualifier-Term",
	service = {DTOConverter.class, ShippingFixedOptionTermDTOConverter.class}
)
public class ShippingFixedOptionTermDTOConverter
	implements DTOConverter
		<CommerceShippingFixedOptionQualifier, ShippingFixedOptionTerm> {

	@Override
	public String getContentType() {
		return ShippingFixedOptionTerm.class.getSimpleName();
	}

	@Override
	public ShippingFixedOptionTerm toDTO(
			DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier =
				_commerceShippingFixedOptionQualifierService.
					getCommerceShippingFixedOptionQualifier(
						(Long)dtoConverterContext.getId());

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryService.getCommerceTermEntry(
				commerceShippingFixedOptionQualifier.getClassPK());

		return new ShippingFixedOptionTerm() {
			{
				actions = dtoConverterContext.getActions();
				shippingFixedOptionId =
					commerceShippingFixedOptionQualifier.
						getCommerceShippingFixedOptionId();
				shippingFixedOptionTermId =
					commerceShippingFixedOptionQualifier.
						getCommerceShippingFixedOptionQualifierId();
				termExternalReferenceCode =
					commerceTermEntry.getExternalReferenceCode();
				termId = commerceTermEntry.getCommerceTermEntryId();
			}
		};
	}

	@Reference
	private CommerceShippingFixedOptionQualifierService
		_commerceShippingFixedOptionQualifierService;

	@Reference
	private CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;

	@Reference
	private CommerceTermEntryService _commerceTermEntryService;

}