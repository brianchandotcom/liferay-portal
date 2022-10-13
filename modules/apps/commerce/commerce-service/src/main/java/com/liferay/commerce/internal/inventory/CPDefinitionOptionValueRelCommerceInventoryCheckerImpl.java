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

package com.liferay.commerce.internal.inventory;

import com.liferay.commerce.inventory.CommerceInventoryChecker;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	immediate = true,
	property = "commerce.inventory.checker.target=CPDefinitionOptionValueRel",
	service = CommerceInventoryChecker.class
)
public class CPDefinitionOptionValueRelCommerceInventoryCheckerImpl
	extends BaseCommerceInventoryChecker<CPDefinitionOptionValueRel> {

	@Override
	public List<CPDefinitionOptionValueRel> filterByAvailability(
		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels) {

		List<CPDefinitionOptionValueRel> filtered = new ArrayList<>();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			if (isAvailable(cpDefinitionOptionValueRel)) {
				filtered.add(cpDefinitionOptionValueRel);
			}
		}

		return filtered;
	}

	@Override
	public boolean isAvailable(
		BaseModel<CPDefinitionOptionValueRel> baseModel) {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			(CPDefinitionOptionValueRel)baseModel;

		if (Validator.isNull(cpDefinitionOptionValueRel.getCPInstanceUuid())) {
			return true;
		}

		return isAvailable(
			cpDefinitionOptionValueRel.fetchCPInstance(),
			cpDefinitionOptionValueRel.getQuantity());
	}

}