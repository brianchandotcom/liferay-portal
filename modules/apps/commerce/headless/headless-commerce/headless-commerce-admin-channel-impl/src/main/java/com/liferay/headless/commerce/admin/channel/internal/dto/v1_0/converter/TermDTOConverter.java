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

import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.Term;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.term.model.CommerceTermEntry",
	service = {DTOConverter.class, TermDTOConverter.class}
)
public class TermDTOConverter implements DTOConverter<CommerceTermEntry, Term> {

	@Override
	public String getContentType() {
		return Term.class.getSimpleName();
	}

	@Override
	public Term toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryService.getCommerceTermEntry(
				(Long)dtoConverterContext.getId());

		return new Term() {
			{
				id = commerceTermEntry.getCommerceTermEntryId();
				name = commerceTermEntry.getName();
			}
		};
	}

	@Reference
	private CommerceTermEntryService _commerceTermEntryService;

}