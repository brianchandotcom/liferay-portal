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

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOptionValue;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false, property = "dto.class.name=CPDefinitionOptionRel",
	service = {DTOConverter.class, ProductOptionDTOConverter.class}
)
public class ProductOptionDTOConverter
	implements DTOConverter<CPDefinitionOptionRel, ProductOption> {

	@Override
	public String getContentType() {
		return ProductOption.class.getSimpleName();
	}

	@Override
	public ProductOption toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRel(
				(Long)dtoConverterContext.getId());

		String languageId = LanguageUtil.getLanguageId(
			dtoConverterContext.getLocale());

		CPOption cpOption = cpDefinitionOptionRel.getCPOption();

		return new ProductOption() {
			{
				description = cpOption.getDescription(languageId);
				fieldType = cpDefinitionOptionRel.getDDMFormFieldTypeName();
				id = cpDefinitionOptionRel.getCPDefinitionOptionRelId();
				key = cpOption.getKey();
				name = cpOption.getName(languageId);
				optionId = cpOption.getCPOptionId();
				productOptionValues = _toProductOptionValues(
					cpDefinitionOptionRel, languageId);
			}
		};
	}

	private ProductOptionValue _toProductOptionValue(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel,
		String languageId) {

		return new ProductOptionValue() {
			{
				id =
					cpDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId();
				key = cpDefinitionOptionValueRel.getKey();
				name = cpDefinitionOptionValueRel.getName(languageId);
				priority = cpDefinitionOptionValueRel.getPriority();
			}
		};
	}

	private ProductOptionValue[] _toProductOptionValues(
		CPDefinitionOptionRel cpDefinitionOptionRel, String languageId) {

		int total =
			_cpDefinitionOptionValueRelLocalService.
				getCPDefinitionOptionValueRelsCount(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId());

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			_cpDefinitionOptionValueRelLocalService.
				getCPDefinitionOptionValueRels(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId(), 0,
					total);

		List<ProductOptionValue> productOptionValues = new ArrayList<>();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			productOptionValues.add(
				_toProductOptionValue(cpDefinitionOptionValueRel, languageId));
		}

		return productOptionValues.toArray(new ProductOptionValue[0]);
	}

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

}