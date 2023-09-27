/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.inventory;

import com.liferay.commerce.inventory.CommerceInventoryChecker;
import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.portal.kernel.model.BaseModel;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	property = "commerce.inventory.checker.target=CPInstanceOptionValueRel",
	service = CommerceInventoryChecker.class
)
public class CPInstanceOptionValueRelCommerceInventoryCheckerImpl
	extends BaseCommerceInventoryChecker<CPInstanceOptionValueRel> {

	@Override
	public List<CPInstanceOptionValueRel> filterByAvailability(
		List<CPInstanceOptionValueRel> cpInstanceOptionValueRels) {

		List<CPInstanceOptionValueRel> filtered = new ArrayList<>();

		for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
				cpInstanceOptionValueRels) {

			if (isAvailable(cpInstanceOptionValueRel)) {
				filtered.add(cpInstanceOptionValueRel);
			}
		}

		return filtered;
	}

	@Override
	public boolean isAvailable(BaseModel<CPInstanceOptionValueRel> baseModel) {
		CPInstanceOptionValueRel cpInstanceOptionValueRel =
			(CPInstanceOptionValueRel)baseModel;

		return isAvailable(
			_cpInstanceLocalService.fetchCPInstance(
				cpInstanceOptionValueRel.getCPInstanceId()),
			BigDecimal.ONE);
	}

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}