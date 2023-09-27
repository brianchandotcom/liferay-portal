/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.model.listener;

import com.liferay.commerce.currency.internal.model.listener.util.ImportDefaultValuesUtil;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ModelListener.class)
public class CompanyModelListener extends BaseModelListener<Company> {

	@Override
	public void onAfterCreate(Company company) {
		ImportDefaultValuesUtil.importDefaultValues(
			_commerceCurrencyLocalService, company);
	}

	@Override
	public void onBeforeRemove(Company company) {
		_commerceCurrencyLocalService.deleteCommerceCurrencies(
			company.getCompanyId());
	}

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

}