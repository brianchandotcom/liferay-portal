/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal.target;

import com.liferay.commerce.discount.target.CommerceDiscountProductTarget;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(service = CommerceDiscountProductTarget.class)
public class ApplyToProductCommerceDiscountProductTarget
	extends BaseCommerceDiscountProductTarget {

	public static final String COMMERCE_DISCOUNT_TARGET_CP_DEFINITION_IDS =
		"commerce_discount_target_cp_definition_ids";

	@Override
	public String getFieldName() {
		return COMMERCE_DISCOUNT_TARGET_CP_DEFINITION_IDS;
	}

	@Override
	public Filter getFilter(CPDefinition cpDefinition) {
		return new TermFilter(
			getFieldName(), String.valueOf(cpDefinition.getCPDefinitionId()));
	}

}