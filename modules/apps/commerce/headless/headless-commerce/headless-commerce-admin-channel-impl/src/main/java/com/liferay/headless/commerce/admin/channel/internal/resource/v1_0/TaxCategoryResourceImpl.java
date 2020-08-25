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

package com.liferay.headless.commerce.admin.channel.internal.resource.v1_0;

import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.TaxCategory;
import com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter.TaxCategoryDTOConverter;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.TaxCategoryResource;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/tax-category.properties",
	scope = ServiceScope.PROTOTYPE, service = TaxCategoryResource.class
)
public class TaxCategoryResourceImpl extends BaseTaxCategoryResourceImpl {

	@Override
	public Page<TaxCategory> getTaxCategoriesPage(
			String search, Pagination pagination)
		throws Exception {

		List<TaxCategory> taxCategories = new ArrayList<>();

		List<CPTaxCategory> cpTaxCategories =
			_cpTaxCategoryService.findCPTaxCategoriesByCompanyId(
				contextCompany.getCompanyId(), search,
				pagination.getStartPosition(), pagination.getEndPosition());

		for (CPTaxCategory cpTaxCategory : cpTaxCategories) {
			taxCategories.add(
				_toTaxCategory(cpTaxCategory.getCPTaxCategoryId()));
		}

		int count = _cpTaxCategoryService.countCPTaxCategoriesByCompanyId(
			contextCompany.getCompanyId(), search);

		return Page.of(taxCategories, pagination, count);
	}

	@Override
	public TaxCategory getTaxCategory(Long id) throws Exception {
		CPTaxCategory cpTaxCategory = _cpTaxCategoryService.getCPTaxCategory(
			id);

		return _toTaxCategory(cpTaxCategory.getCPTaxCategoryId());
	}

	private TaxCategory _toTaxCategory(Long taxCategoryId) throws Exception {
		return _taxCategoryDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				taxCategoryId, contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CPTaxCategoryService _cpTaxCategoryService;

	@Reference
	private TaxCategoryDTOConverter _taxCategoryDTOConverter;

}