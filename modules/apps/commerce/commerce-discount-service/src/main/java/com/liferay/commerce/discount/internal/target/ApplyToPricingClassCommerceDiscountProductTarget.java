/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal.target;

import com.liferay.commerce.discount.target.CommerceDiscountProductTarget;
import com.liferay.commerce.pricing.service.CommercePricingClassLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(service = CommerceDiscountProductTarget.class)
public class ApplyToPricingClassCommerceDiscountProductTarget
	extends BaseCommerceDiscountProductTarget {

	public static final String
		COMMERCE_DISCOUNT_TARGET_COMMERCE_PRICING_CLASS_IDS =
			"commerce_discount_target_commerce_pricing_class_ids";

	@Override
	public String getFieldName() {
		return COMMERCE_DISCOUNT_TARGET_COMMERCE_PRICING_CLASS_IDS;
	}

	@Override
	public Filter getFilter(CPDefinition cpDefinition) {
		TermsFilter termsFilter = new TermsFilter(getFieldName());

		termsFilter.addValues(
			ArrayUtil.toStringArray(
				_commercePricingClassLocalService.
					getCommercePricingClassByCPDefinition(
						cpDefinition.getCPDefinitionId())));

		return termsFilter;
	}

	@Reference
	private CommercePricingClassLocalService _commercePricingClassLocalService;

}