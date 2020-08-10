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

package com.liferay.headless.commerce.admin.catalog.internal.helper.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductSpecification;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.ProductSpecificationDTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = ProductSpecificationHelper.class)
public class ProductSpecificationHelper {

	public Page<ProductSpecification> getProductSpecificationsPage(
			long productId, Locale locale, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + productId);
		}

		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				_cpDefinitionSpecificationOptionValueService.
					getCPDefinitionSpecificationOptionValues(
						cpDefinition.getCPDefinitionId(),
						pagination.getStartPosition(),
						pagination.getEndPosition(), null);

		int totalItems =
			_cpDefinitionSpecificationOptionValueService.
				getCPDefinitionSpecificationOptionValuesCount(
					cpDefinition.getCPDefinitionId());

		return Page.of(
			toProductSpecifications(
				cpDefinitionSpecificationOptionValues, locale),
			pagination, totalItems);
	}

	public List<ProductSpecification> toProductSpecifications(
			List<CPDefinitionSpecificationOptionValue>
				cpDefinitionSpecificationOptionValues,
			Locale locale)
		throws Exception {

		List<ProductSpecification> productSpecifications = new ArrayList<>();

		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					cpDefinitionSpecificationOptionValues) {

			productSpecifications.add(
				_productSpecificationDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						cpDefinitionSpecificationOptionValue.
							getCPDefinitionSpecificationOptionValueId(),
						locale)));
		}

		return productSpecifications;
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPDefinitionSpecificationOptionValueService
		_cpDefinitionSpecificationOptionValueService;

	@Reference
	private ProductSpecificationDTOConverter _productSpecificationDTOConverter;

}