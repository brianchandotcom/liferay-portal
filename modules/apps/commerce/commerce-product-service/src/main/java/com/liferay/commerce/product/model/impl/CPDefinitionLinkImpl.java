/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CProductLocalServiceUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionLinkImpl extends CPDefinitionLinkBaseImpl {

	@Override
	public CPDefinition getCPDefinition() {
		return CPDefinitionLocalServiceUtil.fetchCPDefinition(
			getCPDefinitionId());
	}

	@Override
	public CProduct getCProduct() {
		return CProductLocalServiceUtil.fetchCProduct(getCProductId());
	}

	@Override
	public String getCProductName() {
		CProduct cProduct = getCProduct();

		CPDefinition cpDefinition =
			CPDefinitionLocalServiceUtil.fetchCPDefinition(
				cProduct.getPublishedCPDefinitionId());

		return cpDefinition.getName();
	}

}