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

import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelLocalService;
import com.liferay.commerce.discount.target.CommerceDiscountProductTarget;
import com.liferay.commerce.discount.target.CommerceDiscountTarget;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePricingClassLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.discount.target.key=" + CommerceDiscountConstants.TARGET_PRODUCT_GROUPS,
		"commerce.discount.target.order:Integer=10"
	},
	service = {
		CommerceDiscountProductTarget.class, CommerceDiscountTarget.class
	}
)
public class ApplyToPricingClassCommerceDiscountTargetImpl
	implements CommerceDiscountProductTarget, CommerceDiscountTarget {

	@Override
	public void contributeDocument(
		Document document, CommerceDiscount commerceDiscount) {

		List<CommerceDiscountRel> commerceDiscountRels =
			_commerceDiscountRelLocalService.getCommerceDiscountRels(
				commerceDiscount.getCommerceDiscountId(),
				CommercePricingClass.class.getName());

		Stream<CommerceDiscountRel> stream = commerceDiscountRels.stream();

		LongStream longStream = stream.mapToLong(
			CommerceDiscountRel::getClassPK);

		long[] assetCategoryIds = longStream.toArray();

		document.addKeyword(
			"commerce_discount_target_commerce_pricing_class_ids",
			assetCategoryIds);
	}

	@Override
	public String getKey() {
		return CommerceDiscountConstants.TARGET_PRODUCT_GROUPS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "product-groups");
	}

	@Override
	public Type getType() {
		return Type.APPLY_TO_PRODUCT;
	}

	@Override
	public void postProcessContextBooleanFilter(
		BooleanFilter contextBooleanFilter, CPDefinition cpDefinition) {

		long[] pricingClassIds =
			_commercePricingClassLocalService.
				getCommercePricingClassByCPDefinition(
					cpDefinition.getCPDefinitionId());

		TermsFilter termsFilter = new TermsFilter(
			"commerce_discount_target_commerce_pricing_class_ids");

		termsFilter.addValues(ArrayUtil.toStringArray(pricingClassIds));

		Filter existFilter = new ExistsFilter(
			"commerce_discount_target_commerce_pricing_class_ids");

		BooleanFilter existBooleanFilter = new BooleanFilter();

		existBooleanFilter.add(existFilter, BooleanClauseOccur.MUST_NOT);

		BooleanFilter fieldBooleanFilter = new BooleanFilter();

		fieldBooleanFilter.add(existBooleanFilter, BooleanClauseOccur.SHOULD);
		fieldBooleanFilter.add(termsFilter, BooleanClauseOccur.SHOULD);

		contextBooleanFilter.add(fieldBooleanFilter, BooleanClauseOccur.MUST);
	}

	@Reference
	private CommerceDiscountRelLocalService _commerceDiscountRelLocalService;

	@Reference
	private CommercePricingClassLocalService _commercePricingClassLocalService;

}