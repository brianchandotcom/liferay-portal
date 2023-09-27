/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductTaxConfiguration;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductUtil {

	public static boolean isTaxExempt(
		CPDefinition cpDefinition,
		ProductTaxConfiguration productTaxConfiguration) {

		if (productTaxConfiguration.getTaxable() != null) {
			return !productTaxConfiguration.getTaxable();
		}

		if (cpDefinition != null) {
			return cpDefinition.isTaxExempt();
		}

		return false;
	}

}