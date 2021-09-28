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
import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel;
import com.liferay.commerce.pricing.service.CommercePricingClassCPDefinitionRelService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductGroupProduct;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel",
	service = {DTOConverter.class, ProductGroupProductDTOConverter.class}
)
public class ProductGroupProductDTOConverter
	implements DTOConverter
		<CommercePricingClassCPDefinitionRel, ProductGroupProduct> {

	@Override
	public String getContentType() {
		return ProductGroupProduct.class.getSimpleName();
	}

	@Override
	public ProductGroupProduct toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel =
				_commercePricingClassCPDefinitionRelService.
					getCommercePricingClassCPDefinitionRel(
						(Long)dtoConverterContext.getId());

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			commercePricingClassCPDefinitionRel.getCPDefinitionId());

		CProduct cProduct = cpDefinition.getCProduct();

		CommercePricingClass commercePricingClass =
			commercePricingClassCPDefinitionRel.getCommercePricingClass();

		Locale locale = dtoConverterContext.getLocale();

		String languageId = LanguageUtil.getLanguageId(locale);

		return new ProductGroupProduct() {
			{
				id =
					commercePricingClassCPDefinitionRel.
						getCommercePricingClassCPDefinitionRelId();
				productExternalReferenceCode =
					cProduct.getExternalReferenceCode();
				productGroupExternalReferenceCode =
					commercePricingClass.getExternalReferenceCode();
				productGroupId =
					commercePricingClass.getCommercePricingClassId();
				productId = cProduct.getCProductId();
				productName = cpDefinition.getName(languageId);
				sku = _getSku(cpDefinition, locale);
			}
		};
	}

	private String _getSku(CPDefinition cpDefinition, Locale locale) {
		List<CPInstance> cpInstances = cpDefinition.getCPInstances();

		if (cpInstances.isEmpty()) {
			return StringPool.BLANK;
		}

		if (cpInstances.size() > 1) {
			return LanguageUtil.get(locale, "multiple-skus");
		}

		CPInstance cpInstance = cpInstances.get(0);

		return cpInstance.getSku();
	}

	@Reference
	private CommercePricingClassCPDefinitionRelService
		_commercePricingClassCPDefinitionRelService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

}