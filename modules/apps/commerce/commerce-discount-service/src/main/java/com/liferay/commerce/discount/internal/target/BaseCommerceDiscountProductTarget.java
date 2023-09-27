/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal.target;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelLocalService;
import com.liferay.commerce.discount.target.CommerceDiscountProductTarget;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.Filter;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
public abstract class BaseCommerceDiscountProductTarget
	implements CommerceDiscountProductTarget {

	@Override
	public void contributeDocument(
		Document document, CommerceDiscount commerceDiscount) {

		document.addKeyword(
			getFieldName(),
			TransformUtil.transformToLongArray(
				commerceDiscountRelLocalService.getCommerceDiscountRels(
					commerceDiscount.getCommerceDiscountId(),
					CPDefinition.class.getName()),
				CommerceDiscountRel::getClassPK));
	}

	public abstract String getFieldName();

	public abstract Filter getFilter(CPDefinition cpDefinition);

	@Override
	public void postProcessContextBooleanFilter(
		BooleanFilter contextBooleanFilter, CPDefinition cpDefinition) {

		BooleanFilter fieldBooleanFilter = new BooleanFilter();

		BooleanFilter existBooleanFilter = new BooleanFilter();

		existBooleanFilter.add(
			new ExistsFilter(getFieldName()), BooleanClauseOccur.MUST_NOT);

		fieldBooleanFilter.add(existBooleanFilter, BooleanClauseOccur.SHOULD);

		fieldBooleanFilter.add(
			getFilter(cpDefinition), BooleanClauseOccur.SHOULD);

		contextBooleanFilter.add(fieldBooleanFilter, BooleanClauseOccur.MUST);
	}

	@Reference
	protected CommerceDiscountRelLocalService commerceDiscountRelLocalService;

}