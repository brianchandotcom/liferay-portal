/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.model.listener;

import com.liferay.commerce.currency.internal.model.listener.util.ImportDefaultValuesUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;

/**
 * @author Marco Leo
 */
public class PortalInstanceLifecycleListenerImpl
	extends BasePortalInstanceLifecycleListener {

	public PortalInstanceLifecycleListenerImpl(
		CommerceCurrencyLocalService commerceCurrencyLocalService) {

		_commerceCurrencyLocalService = commerceCurrencyLocalService;
	}

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				company.getCompanyId());

		if (commerceCurrency != null) {
			return;
		}

		ImportDefaultValuesUtil.importDefaultValues(
			_commerceCurrencyLocalService, company);
	}

	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;

}