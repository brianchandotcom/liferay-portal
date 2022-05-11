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
import com.liferay.commerce.discount.target.CommerceDiscountSKUTarget;
import com.liferay.commerce.discount.target.CommerceDiscountTarget;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.discount.target.key=" + CommerceDiscountConstants.TARGET_SKUS,
		"commerce.discount.target.order:Integer=20"
	},
	service = {CommerceDiscountSKUTarget.class, CommerceDiscountTarget.class}
)
public class ApplyToSKUCommerceDiscountTargetImpl
	implements CommerceDiscountSKUTarget, CommerceDiscountTarget {

	@Override
	public void contributeDocument(
		Document document, CommerceDiscount commerceDiscount) {

		List<CommerceDiscountRel> commerceDiscountRels =
			_commerceDiscountRelLocalService.getCommerceDiscountRels(
				commerceDiscount.getCommerceDiscountId(),
				CPInstance.class.getName());

		Stream<CommerceDiscountRel> stream = commerceDiscountRels.stream();

		LongStream longStream = stream.mapToLong(
			CommerceDiscountRel::getClassPK);

		long[] cpInstanceIds = longStream.toArray();

		document.addKeyword(
			"commerce_discount_target_cp_instance_ids", cpInstanceIds);
	}

	@Override
	public String getKey() {
		return CommerceDiscountConstants.TARGET_SKUS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.get(
			resourceBundle, CommerceDiscountConstants.TARGET_SKUS);
	}

	@Override
	public Type getType() {
		return Type.APPLY_TO_SKU;
	}

	@Override
	public void postProcessContextBooleanFilter(
		BooleanFilter contextBooleanFilter, CPInstance cpInstance) {

		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(
			new BooleanFilter() {
				{
					add(
						new ExistsFilter(
							"commerce_discount_target_cp_instance_ids"),
						BooleanClauseOccur.MUST_NOT);
				}
			},
			BooleanClauseOccur.SHOULD);
		booleanFilter.add(
			new TermFilter(
				"commerce_discount_target_cp_instance_ids",
				String.valueOf(cpInstance.getCPDefinitionId())),
			BooleanClauseOccur.SHOULD);

		contextBooleanFilter.add(booleanFilter, BooleanClauseOccur.MUST);
	}

	@Reference
	private CommerceDiscountRelLocalService _commerceDiscountRelLocalService;

	@Reference
	private Language _language;

}