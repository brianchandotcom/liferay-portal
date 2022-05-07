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

package com.liferay.commerce.currency.internal.model.listener;

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