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

package com.liferay.commerce.tax.engine.fixed.internal.model.listener;

import com.liferay.commerce.model.CommerceTaxMethod;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelLocalService;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateLocalService;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = ModelListener.class)
public class CommerceTaxMethodModelListener
	extends BaseModelListener<CommerceTaxMethod> {

	@Override
	public void onBeforeRemove(CommerceTaxMethod commerceTaxMethod) {
		_commerceTaxFixedRateAddressRelLocalService.
			deleteCommerceTaxFixedRateAddressRelsByCommerceTaxMethodId(
				commerceTaxMethod.getCommerceTaxMethodId());

		_commerceTaxFixedRateLocalService.
			deleteCommerceTaxFixedRateByCommerceTaxMethodId(
				commerceTaxMethod.getCommerceTaxMethodId());
	}

	@Reference
	private CommerceTaxFixedRateAddressRelLocalService
		_commerceTaxFixedRateAddressRelLocalService;

	@Reference
	private CommerceTaxFixedRateLocalService _commerceTaxFixedRateLocalService;

}