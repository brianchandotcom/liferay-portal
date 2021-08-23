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

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueService;
import com.liferay.commerce.product.service.CPSpecificationOptionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductSpecification;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductSpecificationUtil {

	public static CPDefinitionSpecificationOptionValue
			addCPDefinitionSpecificationOptionValue(
				CPDefinitionSpecificationOptionValueService
					cpDefinitionSpecificationOptionValueService,
				CPSpecificationOptionService cpSpecificationOptionService,
				long cpDefinitionId, ProductSpecification productSpecification,
				ServiceContext serviceContext)
		throws Exception {

		return cpDefinitionSpecificationOptionValueService.
			addCPDefinitionSpecificationOptionValue(
				cpDefinitionId,
				getCPSpecificationOptionId(
					cpSpecificationOptionService, productSpecification,
					serviceContext.getCompanyId(), serviceContext),
				getCPOptionCategoryId(productSpecification),
				LanguageUtils.getLocalizedMap(productSpecification.getValue()),
				GetterUtil.get(productSpecification.getPriority(), 0D),
				serviceContext);
	}

	public static long getCPOptionCategoryId(
		ProductSpecification productSpecification) {

		if (productSpecification.getOptionCategoryId() == null) {
			return 0;
		}

		return productSpecification.getOptionCategoryId();
	}

	public static long getCPSpecificationOptionId(
			CPSpecificationOptionService cpSpecificationOptionService,
			ProductSpecification productSpecification, long companyId,
			ServiceContext serviceContext)
		throws PortalException {

		String specificationKey = FriendlyURLNormalizerUtil.normalize(
			productSpecification.getSpecificationKey());

		CPSpecificationOption cpSpecificationOption =
			cpSpecificationOptionService.fetchCPSpecificationOption(
				companyId, specificationKey);

		if (cpSpecificationOption == null) {
			cpSpecificationOption =
				cpSpecificationOptionService.addCPSpecificationOption(
					getCPOptionCategoryId(productSpecification),
					LanguageUtils.getLocalizedMap(
						productSpecification.getLabel()),
					LanguageUtils.getLocalizedMap(
						productSpecification.getLabel()),
					false, specificationKey, serviceContext);
		}

		return cpSpecificationOption.getCPSpecificationOptionId();
	}

	public static CPDefinitionSpecificationOptionValue
			updateCPDefinitionSpecificationOptionValue(
				CPDefinitionSpecificationOptionValueService
					cpDefinitionSpecificationOptionValueService,
				CPDefinitionSpecificationOptionValue
					cpDefinitionSpecificationOptionValue,
				ProductSpecification productSpecification,
				ServiceContext serviceContext)
		throws PortalException {

		return cpDefinitionSpecificationOptionValueService.
			updateCPDefinitionSpecificationOptionValue(
				cpDefinitionSpecificationOptionValue.
					getCPDefinitionSpecificationOptionValueId(),
				getCPOptionCategoryId(productSpecification),
				LanguageUtils.getLocalizedMap(productSpecification.getValue()),
				GetterUtil.get(
					productSpecification.getPriority(),
					cpDefinitionSpecificationOptionValue.getPriority()),
				serviceContext);
	}

}