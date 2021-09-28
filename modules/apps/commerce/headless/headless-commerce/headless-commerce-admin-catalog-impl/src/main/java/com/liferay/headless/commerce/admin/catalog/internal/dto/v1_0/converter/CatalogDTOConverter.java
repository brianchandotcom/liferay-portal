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

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Catalog;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.product.model.CommerceCatalog",
	service = {CatalogDTOConverter.class, DTOConverter.class}
)
public class CatalogDTOConverter
	implements DTOConverter<CommerceCatalog, Catalog> {

	@Override
	public String getContentType() {
		return Catalog.class.getSimpleName();
	}

	@Override
	public Catalog toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceCatalog commerceCatalog =
			_commerceCatalogService.getCommerceCatalog(
				(Long)dtoConverterContext.getId());

		return new Catalog() {
			{
				actions = dtoConverterContext.getActions();
				currencyCode = commerceCatalog.getCommerceCurrencyCode();
				defaultLanguageId =
					commerceCatalog.getCatalogDefaultLanguageId();
				externalReferenceCode =
					commerceCatalog.getExternalReferenceCode();
				id = commerceCatalog.getCommerceCatalogId();
				name = commerceCatalog.getName();
				system = commerceCatalog.isSystem();
			}
		};
	}

	@Reference
	private CommerceCatalogService _commerceCatalogService;

}