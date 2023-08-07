/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOptionValue;
import com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "dto.class.name=CPDefinitionOptionRel",
	service = DTOConverter.class
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

		String languageId = _language.getLanguageId(
			dtoConverterContext.getLocale());

		return new ProductOption() {
			{
				description = cpDefinitionOptionRel.getDescription(languageId);
				fieldType = cpDefinitionOptionRel.getCommerceOptionTypeKey();
				id = cpDefinitionOptionRel.getCPDefinitionOptionRelId();
				key = cpDefinitionOptionRel.getKey();
				name = cpDefinitionOptionRel.getName(languageId);
				optionId = cpDefinitionOptionRel.getCPOptionId();
				productOptionValues = _toProductOptionValues(
					cpDefinitionOptionRel, dtoConverterContext);
				required = cpDefinitionOptionRel.isRequired();
				skuContributor = cpDefinitionOptionRel.isSkuContributor();
			}
		};
	}

	private ProductOptionValue[] _toProductOptionValues(
			CPDefinitionOptionRel cpDefinitionOptionRel,
			DTOConverterContext dtoConverterContext)
		throws Exception {

		List<ProductOptionValue> productOptionValues = new ArrayList<>();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionRel.getCPDefinitionOptionValueRels()) {

			if (cpDefinitionOptionValueRel.getCPDefinitionOptionRelId() == 0) {
				cpDefinitionOptionValueRel.setCPDefinitionOptionRelId(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId());
			}

			DefaultDTOConverterContext defaultDTOConverterContext =
				new DefaultDTOConverterContext(
					cpDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId(),
					dtoConverterContext.getLocale());

			defaultDTOConverterContext.setAttribute(
				"commerceContext",
				dtoConverterContext.getAttribute("commerceContext"));
			defaultDTOConverterContext.setAttribute(
				"productOptionValueId",
				dtoConverterContext.getAttribute("productOptionValueId"));
			defaultDTOConverterContext.setAttribute(
				"skuId", dtoConverterContext.getAttribute("skuId"));

			productOptionValues.add(
				_productOptionValueDTOConverter.toDTO(
					defaultDTOConverterContext, cpDefinitionOptionValueRel));
		}

		return productOptionValues.toArray(new ProductOptionValue[0]);
	}

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private Language _language;

	@Reference(
		target = DTOConverterConstants.PRODUCT_OPTION_VALUE_DTO_CONVERTER
	)
	private DTOConverter<CPDefinitionOptionValueRel, ProductOptionValue>
		_productOptionValueDTOConverter;

}