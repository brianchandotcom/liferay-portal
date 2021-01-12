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

package com.liferay.headless.commerce.admin.catalog.internal.batch.v1_0;

import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.headless.commerce.admin.catalog.constants.v1_0.ProductBatchEngineTaskItemDelegateConstants;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Category;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductSpecification;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.ProductDTOConverter;
import com.liferay.headless.commerce.admin.catalog.internal.helper.v1_0.CategoryHelper;
import com.liferay.headless.commerce.admin.catalog.internal.helper.v1_0.ProductHelper;
import com.liferay.headless.commerce.admin.catalog.internal.helper.v1_0.ProductSpecificationHelper;
import com.liferay.headless.commerce.admin.catalog.internal.helper.v1_0.SkuHelper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.io.Serializable;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false, immediate = true,
	property = "batch.engine.task.item.delegate.name=" + ProductBatchEngineTaskItemDelegateConstants.COMMERCE_ML_PRODUCT,
	service = BatchEngineTaskItemDelegate.class
)
public class CommerceMLProductBatchEngineTaskItemDelegate
	extends BaseBatchEngineTaskItemDelegate<Product> {

	@Override
	public Class<Product> getItemClass() {
		return Product.class;
	}

	@Override
	public Page<Product> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		com.liferay.portal.vulcan.pagination.Pagination vulcanPagination =
			com.liferay.portal.vulcan.pagination.Pagination.of(
				pagination.getPage(), pagination.getPageSize());

		com.liferay.portal.vulcan.pagination.Page<Product> productsPage =
			_productHelper.getProductsPage(
				contextCompany.getCompanyId(), search, filter, vulcanPagination,
				sorts,
				document -> {
					long productId = GetterUtil.getLong(
						document.get(Field.ENTRY_CLASS_PK));

					return _toProduct(productId, contextUser.getLocale());
				},
				null);

		return Page.of(
			productsPage.getItems(),
			Pagination.of(
				(int)productsPage.getPage(), (int)productsPage.getPageSize()),
			productsPage.getTotalCount());
	}

	private Product _toProduct(long productId, Locale locale) throws Exception {
		Product product = _productDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				false, null, _dtoConverterRegistry, productId, locale, null,
				contextUser));

		com.liferay.portal.vulcan.pagination.Pagination fullPagination =
			com.liferay.portal.vulcan.pagination.Pagination.of(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		// Product Specifications

		com.liferay.portal.vulcan.pagination.Page<ProductSpecification>
			productSpecificationsPage =
				_productSpecificationHelper.getProductSpecificationsPage(
					product.getProductId(), contextUser.getLocale(),
					fullPagination);

		Collection<ProductSpecification> productSpecifications =
			productSpecificationsPage.getItems();

		product.setProductSpecifications(
			productSpecifications.toArray(new ProductSpecification[0]));

		// Asset Categories

		com.liferay.portal.vulcan.pagination.Page<Category> categoriesPage =
			_categoryHelper.getCategoriesPage(
				product.getProductId(), contextUser.getLocale(),
				fullPagination);

		Collection<Category> categories = categoriesPage.getItems();

		product.setCategories(categories.toArray(new Category[0]));

		// Sku

		com.liferay.portal.vulcan.pagination.Page<Sku> skusPage =
			_skuHelper.getSkusPage(
				product.getProductId(), contextUser.getLocale(),
				fullPagination);

		Collection<Sku> skus = skusPage.getItems();

		product.setSkus(skus.toArray(new Sku[0]));

		return product;
	}

	@Reference
	private CategoryHelper _categoryHelper;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ProductDTOConverter _productDTOConverter;

	@Reference
	private ProductHelper _productHelper;

	@Reference
	private ProductSpecificationHelper _productSpecificationHelper;

	@Reference
	private SkuHelper _skuHelper;

}