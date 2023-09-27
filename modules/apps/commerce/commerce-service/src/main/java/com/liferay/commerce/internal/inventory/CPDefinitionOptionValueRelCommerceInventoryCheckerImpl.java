/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.inventory;

import com.liferay.commerce.inventory.CommerceInventoryChecker;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
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

		BigDecimal quantity = cpDefinitionOptionValueRel.getQuantity();

		return isAvailable(
			cpDefinitionOptionValueRel.fetchCPInstance(), quantity);
	}

}