/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductSpecification;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "dto.class.name=CPDefinitionSpecificationOptionValue",
	service = DTOConverter.class
)
public class ProductSpecificationDTOConverter
	implements DTOConverter
		<CPDefinitionSpecificationOptionValue, ProductSpecification> {

	@Override
	public String getContentType() {
		return ProductSpecification.class.getSimpleName();
	}

	@Override
	public ProductSpecification toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				_cpDefinitionSpecificationOptionValueLocalService.
					getCPDefinitionSpecificationOptionValue(
						(Long)dtoConverterContext.getId());

		CPDefinition cpDefinition =
			cpDefinitionSpecificationOptionValue.getCPDefinition();

		CPSpecificationOption cpSpecificationOption =
			cpDefinitionSpecificationOptionValue.getCPSpecificationOption();

		CPOptionCategory cpOptionCategory =
			cpSpecificationOption.getCPOptionCategory();

		String languageId = _language.getLanguageId(
			dtoConverterContext.getLocale());

		ProductSpecification productSpecification = new ProductSpecification() {
			{
				id =
					cpDefinitionSpecificationOptionValue.
						getCPDefinitionSpecificationOptionValueId();
				optionCategoryId =
					cpDefinitionSpecificationOptionValue.
						getCPOptionCategoryId();
				priority = cpDefinitionSpecificationOptionValue.getPriority();
				productId = cpDefinition.getCProductId();
				specificationId =
					cpSpecificationOption.getCPSpecificationOptionId();
				specificationKey = cpSpecificationOption.getKey();
				specificationTitle = cpSpecificationOption.getTitle(languageId);
				value = cpDefinitionSpecificationOptionValue.getValue(
					languageId);
			}
		};

		if (cpOptionCategory != null) {
			productSpecification.setSpecificationGroupKey(
				cpOptionCategory.getKey());
			productSpecification.setSpecificationGroupTitle(
				cpOptionCategory.getTitle(languageId));
		}

		return productSpecification;
	}

	@Reference
	private CPDefinitionSpecificationOptionValueLocalService
		_cpDefinitionSpecificationOptionValueLocalService;

	@Reference
	private Language _language;

}