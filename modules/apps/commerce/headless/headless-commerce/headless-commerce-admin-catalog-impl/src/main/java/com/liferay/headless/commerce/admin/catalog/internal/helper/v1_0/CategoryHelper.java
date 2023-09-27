/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.helper.v1_0;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Category;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = CategoryHelper.class)
public class CategoryHelper {

	public Page<Category> getCategoriesPage(
			long id, Locale locale, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		List<AssetCategory> assetCategories =
			_assetCategoryService.getCategories(
				_classNameLocalService.getClassNameId(
					cpDefinition.getModelClass()),
				cpDefinition.getCPDefinitionId(), pagination.getStartPosition(),
				pagination.getEndPosition());

		int totalItems = _assetCategoryService.getCategoriesCount(
			_classNameLocalService.getClassNameId(cpDefinition.getModelClass()),
			cpDefinition.getCPDefinitionId());

		return Page.of(
			toProductCategories(assetCategories, locale), pagination,
			totalItems);
	}

	public List<Category> toProductCategories(
			List<AssetCategory> assetCategories, Locale locale)
		throws Exception {

		List<Category> categories = new ArrayList<>();

		for (AssetCategory category : assetCategories) {
			categories.add(
				_categoryDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						category.getCategoryId(), locale)));
		}

		return categories;
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.CategoryDTOConverter)"
	)
	private DTOConverter<AssetCategory, Category> _categoryDTOConverter;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

}