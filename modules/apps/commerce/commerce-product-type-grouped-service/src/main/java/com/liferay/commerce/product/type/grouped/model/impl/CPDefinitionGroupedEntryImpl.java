/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.model.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CProductLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrea Di Giorgi
 * @author Ethan Bustad
 */
public class CPDefinitionGroupedEntryImpl
	extends CPDefinitionGroupedEntryBaseImpl {

	@Override
	public CPDefinition getCPDefinition() throws PortalException {
		return CPDefinitionLocalServiceUtil.getCPDefinition(
			getCPDefinitionId());
	}

	@Override
	public CPDefinition getEntryCPDefinition() throws PortalException {
		CProduct cProduct = getEntryCProduct();

		return CPDefinitionLocalServiceUtil.fetchCPDefinition(
			cProduct.getPublishedCPDefinitionId());
	}

	@Override
	public long getEntryCPDefinitionId() throws PortalException {
		CProduct cProduct = getEntryCProduct();

		return cProduct.getPublishedCPDefinitionId();
	}

	@Override
	public CProduct getEntryCProduct() throws PortalException {
		return CProductLocalServiceUtil.getCProduct(getEntryCProductId());
	}

}