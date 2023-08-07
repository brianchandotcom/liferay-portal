/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Category;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.CategoryResource;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
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
	properties = "OSGI-INF/liferay/rest/v1_0/category.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = CategoryResource.class
)
@CTAware
public class CategoryResourceImpl extends BaseCategoryResourceImpl {

	@NestedField(parentClass = Product.class, value = "categories")
	@Override
	public Page<Category> getChannelProductCategoriesPage(
			Long channelId, @NestedFieldId(value = "productId") Long productId,
			Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + productId);
		}

		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getCategories(
				_classNameLocalService.getClassNameId(
					cpDefinition.getModelClass()),
				cpDefinition.getCPDefinitionId(), pagination.getStartPosition(),
				pagination.getEndPosition());

		int totalItems = _assetCategoryLocalService.getCategoriesCount(
			_classNameLocalService.getClassNameId(cpDefinition.getModelClass()),
			cpDefinition.getCPDefinitionId());

		return Page.of(
			_toProductCategories(assetCategories), pagination, totalItems);
	}

	private List<Category> _toProductCategories(
			List<AssetCategory> assetCategories)
		throws Exception {

		List<Category> categories = new ArrayList<>();

		for (AssetCategory category : assetCategories) {
			categories.add(
				_categoryDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						category.getCategoryId(),
						contextAcceptLanguage.getPreferredLocale())));
		}

		return categories;
	}

	@Reference
	private AssetCategoryService _assetCategoryLocalService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.CategoryDTOConverter)"
	)
	private DTOConverter<AssetCategory, Category> _categoryDTOConverter;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

}