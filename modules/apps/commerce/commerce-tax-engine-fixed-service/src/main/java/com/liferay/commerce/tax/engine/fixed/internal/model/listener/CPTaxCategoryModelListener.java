/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.fixed.internal.model.listener;

import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelLocalService;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateLocalService;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ModelListener.class)
public class CPTaxCategoryModelListener
	extends BaseModelListener<CPTaxCategory> {

	@Override
	public void onBeforeRemove(CPTaxCategory cpTaxCategory) {
		_commerceTaxFixedRateAddressRelLocalService.
			deleteCommerceTaxFixedRateAddressRelsByCPTaxCategoryId(
				cpTaxCategory.getCPTaxCategoryId());

		_commerceTaxFixedRateLocalService.
			deleteCommerceTaxFixedRateByCPTaxCategoryId(
				cpTaxCategory.getCPTaxCategoryId());
	}

	@Reference
	private CommerceTaxFixedRateAddressRelLocalService
		_commerceTaxFixedRateAddressRelLocalService;

	@Reference
	private CommerceTaxFixedRateLocalService _commerceTaxFixedRateLocalService;

}