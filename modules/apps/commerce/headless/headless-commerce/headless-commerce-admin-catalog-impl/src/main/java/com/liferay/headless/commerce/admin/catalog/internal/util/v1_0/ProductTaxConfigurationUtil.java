/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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