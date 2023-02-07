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

package com.liferay.headless.commerce.admin.catalog.internal.resource.v2_0;

import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.headless.commerce.admin.catalog.dto.v2_0.Category;
import com.liferay.headless.commerce.admin.catalog.dto.v2_0.Product;
import com.liferay.headless.commerce.admin.catalog.internal.helper.v2_0.CategoryHelper;
import com.liferay.headless.commerce.admin.catalog.resource.v2_0.CategoryResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/category.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {CategoryResource.class, NestedFieldSupport.class}
)
@CTAware
public class CategoryResourceImpl
	extends BaseCategoryResourceImpl implements NestedFieldSupport {

	@Override
	public Page<Category> getProductByExternalReferenceCodeCategoriesPage(
			String externalReferenceCode, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		return _categoryHelper.getCategoriesPage(
			cpDefinition.getCProductId(),
			contextAcceptLanguage.getPreferredLocale(), pagination);
	}

	@NestedField(parentClass = Product.class, value = "categories")
	@Override
	public Page<Category> getProductIdCategoriesPage(
			@NestedFieldId(value = "productId") Long id, Pagination pagination)
		throws Exception {

		return _categoryHelper.getCategoriesPage(
			id, contextAcceptLanguage.getPreferredLocale(), pagination);
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private CategoryHelper _categoryHelper;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}