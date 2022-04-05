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
		throws PortalException {

		CPSpecificationOption cpSpecificationOption =
			cpSpecificationOptionService.fetchCPSpecificationOption(
				serviceContext.getCompanyId(),
				FriendlyURLNormalizerUtil.normalize(
					productSpecification.getSpecificationKey()));

		return cpDefinitionSpecificationOptionValueService.
			addCPDefinitionSpecificationOptionValue(
				cpDefinitionId,
				_getCPSpecificationOptionId(
					cpSpecificationOption, cpSpecificationOptionService,
					productSpecification, serviceContext),
				_getCPOptionCategoryId(
					cpSpecificationOption, productSpecification),
				LanguageUtils.getLocalizedMap(productSpecification.getValue()),
				GetterUtil.get(productSpecification.getPriority(), 0D),
				serviceContext);
	}

	public static CPDefinitionSpecificationOptionValue
			updateCPDefinitionSpecificationOptionValue(
				CPDefinitionSpecificationOptionValueService
					cpDefinitionSpecificationOptionValueService,
				CPDefinitionSpecificationOptionValue
					cpDefinitionSpecificationOptionValue,
				CPSpecificationOptionService cpSpecificationOptionService,
				ProductSpecification productSpecification,
				ServiceContext serviceContext)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption =
			cpSpecificationOptionService.fetchCPSpecificationOption(
				serviceContext.getCompanyId(),
				FriendlyURLNormalizerUtil.normalize(
					productSpecification.getSpecificationKey()));

		return cpDefinitionSpecificationOptionValueService.
			updateCPDefinitionSpecificationOptionValue(
				cpDefinitionSpecificationOptionValue.
					getCPDefinitionSpecificationOptionValueId(),
				_getCPOptionCategoryId(
					cpSpecificationOption, productSpecification),
				LanguageUtils.getLocalizedMap(productSpecification.getValue()),
				GetterUtil.get(
					productSpecification.getPriority(),
					cpDefinitionSpecificationOptionValue.getPriority()),
				serviceContext);
	}

	private static long _getCPOptionCategoryId(
		CPSpecificationOption cpSpecificationOption,
		ProductSpecification productSpecification) {

		if (productSpecification.getOptionCategoryId() == null) {
			if (cpSpecificationOption != null) {
				return cpSpecificationOption.getCPOptionCategoryId();
			}

			return 0;
		}

		return productSpecification.getOptionCategoryId();
	}

	private static long _getCPSpecificationOptionId(
			CPSpecificationOption cpSpecificationOption,
			CPSpecificationOptionService cpSpecificationOptionService,
			ProductSpecification productSpecification,
			ServiceContext serviceContext)
		throws PortalException {

		if (cpSpecificationOption == null) {
			cpSpecificationOption =
				cpSpecificationOptionService.addCPSpecificationOption(
					GetterUtil.get(
						productSpecification.getOptionCategoryId(), 0),
					LanguageUtils.getLocalizedMap(
						productSpecification.getLabel()),
					LanguageUtils.getLocalizedMap(
						productSpecification.getLabel()),
					false, productSpecification.getSpecificationKey(),
					serviceContext);
		}

		return cpSpecificationOption.getCPSpecificationOptionId();
	}

}