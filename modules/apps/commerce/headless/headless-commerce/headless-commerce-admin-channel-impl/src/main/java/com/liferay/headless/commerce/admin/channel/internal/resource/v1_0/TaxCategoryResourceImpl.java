/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.internal.resource.v1_0;

import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.TaxCategory;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.TaxCategoryResource;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
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

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter.TaxCategoryDTOConverter)"
	)
	private DTOConverter<CPTaxCategory, TaxCategory> _taxCategoryDTOConverter;

}