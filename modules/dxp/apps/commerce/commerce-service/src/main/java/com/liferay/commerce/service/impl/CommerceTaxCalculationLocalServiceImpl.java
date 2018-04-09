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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.model.CommerceTaxEngine;
import com.liferay.commerce.model.CommerceTaxMethod;
import com.liferay.commerce.model.CommerceTaxRate;
import com.liferay.commerce.service.base.CommerceTaxCalculationLocalServiceBaseImpl;
import com.liferay.commerce.util.CommerceTaxEngineRegistry;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Leo
 */
public class CommerceTaxCalculationLocalServiceImpl
	extends CommerceTaxCalculationLocalServiceBaseImpl {

	public List<CommerceTaxRate> getCommerceTaxRates(
		long siteGroupId, long userId, long commerceTaxCategoryId) {

		List<CommerceTaxRate> commerceTaxRates = new ArrayList<>();

		List<CommerceTaxMethod> commerceTaxMethods =
			commerceTaxMethodLocalService.getCommerceTaxMethods(
				siteGroupId, true);

		for (CommerceTaxMethod commerceTaxMethod : commerceTaxMethods) {
			CommerceTaxEngine commerceTaxEngine =
				_commerceTaxEngineRegistry.getCommerceTaxEngine(
					commerceTaxMethod.getEngineKey());

			//commerceTaxEngine.getCommerceTaxRates();
		}

		return commerceTaxRates;
	}

	@ServiceReference(type = CommerceTaxEngineRegistry.class)
	private CommerceTaxEngineRegistry _commerceTaxEngineRegistry;

}