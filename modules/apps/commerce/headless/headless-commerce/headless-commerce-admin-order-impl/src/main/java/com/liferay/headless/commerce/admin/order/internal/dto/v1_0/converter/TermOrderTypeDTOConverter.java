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

package com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.model.CommerceTermEntryRel;
import com.liferay.commerce.term.service.CommerceTermEntryRelService;
import com.liferay.commerce.term.service.CommerceTermEntryService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.TermOrderType;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.term.model.CommerceTermEntryRel-OrderType",
	service = {DTOConverter.class, TermOrderTypeDTOConverter.class}
)
public class TermOrderTypeDTOConverter
	implements DTOConverter<CommerceTermEntryRel, TermOrderType> {

	@Override
	public String getContentType() {
		return TermOrderType.class.getSimpleName();
	}

	@Override
	public TermOrderType toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceTermEntryRel commerceTermEntryRel =
			_commerceTermEntryRelService.getCommerceTermEntryRel(
				(Long)dtoConverterContext.getId());

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeService.getCommerceOrderType(
				commerceTermEntryRel.getClassPK());

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryService.getCommerceTermEntry(
				commerceTermEntryRel.getCommerceTermEntryId());

		return new TermOrderType() {
			{
				actions = dtoConverterContext.getActions();
				orderTypeExternalReferenceCode =
					commerceOrderType.getExternalReferenceCode();
				orderTypeId = commerceOrderType.getCommerceOrderTypeId();
				termExternalReferenceCode =
					commerceTermEntry.getExternalReferenceCode();
				termId = commerceTermEntry.getCommerceTermEntryId();
				termOrderTypeId =
					commerceTermEntryRel.getCommerceTermEntryRelId();
			}
		};
	}

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference
	private CommerceTermEntryRelService _commerceTermEntryRelService;

	@Reference
	private CommerceTermEntryService _commerceTermEntryService;

}