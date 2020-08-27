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

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductTaxConfiguration;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductTaxConfigurationUtil {

	public static CPDefinition updateCPDefinitionTaxCategoryInfo(
			CPDefinitionService cpDefinitionService,
			ProductTaxConfiguration productTaxConfiguration,
			CPDefinition cpDefinition)
		throws PortalException {

		return cpDefinitionService.updateTaxCategoryInfo(
			cpDefinition.getCPDefinitionId(), productTaxConfiguration.getId(),
			ProductUtil.isTaxExempt(cpDefinition, productTaxConfiguration),
			false);
	}

}