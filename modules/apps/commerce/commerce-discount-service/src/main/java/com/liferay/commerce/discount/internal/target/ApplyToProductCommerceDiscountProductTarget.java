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