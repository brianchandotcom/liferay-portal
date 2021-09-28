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

import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePricingClassCPDefinitionRelService;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductGroup;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.pricing.model.CommercePricingClass",
	service = {DTOConverter.class, ProductGroupDTOConverter.class}
)
public class ProductGroupDTOConverter
	implements DTOConverter<CommercePricingClass, ProductGroup> {

	@Override
	public String getContentType() {
		return ProductGroup.class.getSimpleName();
	}

	@Override
	public ProductGroup toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePricingClass commercePricingClass =
			_commercePricingClassService.getCommercePricingClass(
				(Long)dtoConverterContext.getId());

		ExpandoBridge expandoBridge = commercePricingClass.getExpandoBridge();

		return new ProductGroup() {
			{
				customFields = expandoBridge.getAttributes();
				description = LanguageUtils.getLanguageIdMap(
					commercePricingClass.getDescriptionMap());
				externalReferenceCode =
					commercePricingClass.getExternalReferenceCode();
				id = commercePricingClass.getCommercePricingClassId();
				productsCount = _getProductsCount(
					commercePricingClass.getCommercePricingClassId());
				title = LanguageUtils.getLanguageIdMap(
					commercePricingClass.getTitleMap());
			}
		};
	}

	private int _getProductsCount(long commercePricingClassId)
		throws Exception {

		return _commercePricingClassCPDefinitionRelService.
			getCommercePricingClassCPDefinitionRelsCount(
				commercePricingClassId);
	}

	@Reference
	private CommercePricingClassCPDefinitionRelService
		_commercePricingClassCPDefinitionRelService;

	@Reference
	private CommercePricingClassService _commercePricingClassService;

}