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

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CProductLocalServiceUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionLinkImpl extends CPDefinitionLinkBaseImpl {

	public CPDefinitionLinkImpl() {
	}

	@Override
	public CPDefinition getCPDefinition() {
		return CPDefinitionLocalServiceUtil.fetchCPDefinition(
			getCPDefinitionId());
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public CPDefinition getCPDefinition1() {
		return getCPDefinition();
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public CPDefinition getCPDefinition2() {
		CProduct cProduct = getCProduct();

		if (cProduct == null) {
			return null;
		}

		return CPDefinitionLocalServiceUtil.fetchCPDefinition(
			cProduct.getPublishedCPDefinitionId());
	}

	@Override
	public CProduct getCProduct() {
		return CProductLocalServiceUtil.fetchCProduct(getCProductId());
	}

}