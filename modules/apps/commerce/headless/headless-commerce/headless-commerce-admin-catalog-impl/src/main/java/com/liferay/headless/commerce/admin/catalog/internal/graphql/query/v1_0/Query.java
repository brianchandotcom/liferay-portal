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

package com.liferay.headless.commerce.admin.catalog.internal.graphql.query.v1_0;

import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Attachment;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Catalog;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Category;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Option;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.OptionCategory;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.OptionValue;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfiguration;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductGroup;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductGroupProduct;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductOptionValue;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductShippingConfiguration;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductSpecification;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductSubscriptionConfiguration;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductTaxConfiguration;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.RelatedProduct;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Specification;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.AttachmentResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.CatalogResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.CategoryResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.OptionCategoryResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.OptionResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.OptionValueResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductConfigurationResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductGroupProductResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductGroupResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductOptionResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductOptionValueResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductShippingConfigurationResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductSpecificationResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductSubscriptionConfigurationResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductTaxConfigurationResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.RelatedProductResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.SkuResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.SpecificationResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class Query {

	public static void setAttachmentResourceComponentServiceObjects(
		ComponentServiceObjects<AttachmentResource>
			attachmentResourceComponentServiceObjects) {

		_attachmentResourceComponentServiceObjects =
			attachmentResourceComponentServiceObjects;
	}

	public static void setCatalogResourceComponentServiceObjects(
		ComponentServiceObjects<CatalogResource>
			catalogResourceComponentServiceObjects) {

		_catalogResourceComponentServiceObjects =
			catalogResourceComponentServiceObjects;
	}

	public static void setCategoryResourceComponentServiceObjects(
		ComponentServiceObjects<CategoryResource>
			categoryResourceComponentServiceObjects) {

		_categoryResourceComponentServiceObjects =
			categoryResourceComponentServiceObjects;
	}

	public static void setOptionResourceComponentServiceObjects(
		ComponentServiceObjects<OptionResource>
			optionResourceComponentServiceObjects) {

		_optionResourceComponentServiceObjects =
			optionResourceComponentServiceObjects;
	}

	public static void setOptionCategoryResourceComponentServiceObjects(
		ComponentServiceObjects<OptionCategoryResource>
			optionCategoryResourceComponentServiceObjects) {

		_optionCategoryResourceComponentServiceObjects =
			optionCategoryResourceComponentServiceObjects;
	}

	public static void setOptionValueResourceComponentServiceObjects(
		ComponentServiceObjects<OptionValueResource>
			optionValueResourceComponentServiceObjects) {

		_optionValueResourceComponentServiceObjects =
			optionValueResourceComponentServiceObjects;
	}

	public static void setProductResourceComponentServiceObjects(
		ComponentServiceObjects<ProductResource>
			productResourceComponentServiceObjects) {

		_productResourceComponentServiceObjects =
			productResourceComponentServiceObjects;
	}

	public static void setProductConfigurationResourceComponentServiceObjects(
		ComponentServiceObjects<ProductConfigurationResource>
			productConfigurationResourceComponentServiceObjects) {

		_productConfigurationResourceComponentServiceObjects =
			productConfigurationResourceComponentServiceObjects;
	}

	public static void setProductGroupResourceComponentServiceObjects(
		ComponentServiceObjects<ProductGroupResource>
			productGroupResourceComponentServiceObjects) {

		_productGroupResourceComponentServiceObjects =
			productGroupResourceComponentServiceObjects;
	}

	public static void setProductGroupProductResourceComponentServiceObjects(
		ComponentServiceObjects<ProductGroupProductResource>
			productGroupProductResourceComponentServiceObjects) {

		_productGroupProductResourceComponentServiceObjects =
			productGroupProductResourceComponentServiceObjects;
	}

	public static void setProductOptionResourceComponentServiceObjects(
		ComponentServiceObjects<ProductOptionResource>
			productOptionResourceComponentServiceObjects) {

		_productOptionResourceComponentServiceObjects =
			productOptionResourceComponentServiceObjects;
	}

	public static void setProductOptionValueResourceComponentServiceObjects(
		ComponentServiceObjects<ProductOptionValueResource>
			productOptionValueResourceComponentServiceObjects) {

		_productOptionValueResourceComponentServiceObjects =
			productOptionValueResourceComponentServiceObjects;
	}

	public static void
		setProductShippingConfigurationResourceComponentServiceObjects(
			ComponentServiceObjects<ProductShippingConfigurationResource>
				productShippingConfigurationResourceComponentServiceObjects) {

		_productShippingConfigurationResourceComponentServiceObjects =
			productShippingConfigurationResourceComponentServiceObjects;
	}

	public static void setProductSpecificationResourceComponentServiceObjects(
		ComponentServiceObjects<ProductSpecificationResource>
			productSpecificationResourceComponentServiceObjects) {

		_productSpecificationResourceComponentServiceObjects =
			productSpecificationResourceComponentServiceObjects;
	}

	public static void
		setProductSubscriptionConfigurationResourceComponentServiceObjects(
			ComponentServiceObjects<ProductSubscriptionConfigurationResource>
				productSubscriptionConfigurationResourceComponentServiceObjects) {

		_productSubscriptionConfigurationResourceComponentServiceObjects =
			productSubscriptionConfigurationResourceComponentServiceObjects;
	}

	public static void
		setProductTaxConfigurationResourceComponentServiceObjects(
			ComponentServiceObjects<ProductTaxConfigurationResource>
				productTaxConfigurationResourceComponentServiceObjects) {

		_productTaxConfigurationResourceComponentServiceObjects =
			productTaxConfigurationResourceComponentServiceObjects;
	}

	public static void setRelatedProductResourceComponentServiceObjects(
		ComponentServiceObjects<RelatedProductResource>
			relatedProductResourceComponentServiceObjects) {

		_relatedProductResourceComponentServiceObjects =
			relatedProductResourceComponentServiceObjects;
	}

	public static void setSkuResourceComponentServiceObjects(
		ComponentServiceObjects<SkuResource>
			skuResourceComponentServiceObjects) {

		_skuResourceComponentServiceObjects =
			skuResourceComponentServiceObjects;
	}

	public static void setSpecificationResourceComponentServiceObjects(
		ComponentServiceObjects<SpecificationResource>
			specificationResourceComponentServiceObjects) {

		_specificationResourceComponentServiceObjects =
			specificationResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productByExternalReferenceCodeAttachments(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AttachmentPage productByExternalReferenceCodeAttachments(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource -> new AttachmentPage(
				attachmentResource.
					getProductByExternalReferenceCodeAttachmentsPage(
						externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productByExternalReferenceCodeImages(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AttachmentPage productByExternalReferenceCodeImages(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource -> new AttachmentPage(
				attachmentResource.getProductByExternalReferenceCodeImagesPage(
					externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productIdAttachments(id: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AttachmentPage productIdAttachments(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource -> new AttachmentPage(
				attachmentResource.getProductIdAttachmentsPage(
					id, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productIdImages(id: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AttachmentPage productIdImages(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource -> new AttachmentPage(
				attachmentResource.getProductIdImagesPage(
					id, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {catalogByExternalReferenceCode(externalReferenceCode: ___){actions, currencyCode, defaultLanguageId, externalReferenceCode, id, name, system}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Catalog catalogByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_catalogResourceComponentServiceObjects,
			this::_populateResourceContext,
			catalogResource ->
				catalogResource.getCatalogByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {catalog(id: ___){actions, currencyCode, defaultLanguageId, externalReferenceCode, id, name, system}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Catalog catalog(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_catalogResourceComponentServiceObjects,
			this::_populateResourceContext,
			catalogResource -> catalogResource.getCatalog(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {catalogs(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public CatalogPage catalogs(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_catalogResourceComponentServiceObjects,
			this::_populateResourceContext,
			catalogResource -> new CatalogPage(
				catalogResource.getCatalogsPage(
					search,
					_filterBiFunction.apply(catalogResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(catalogResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productByExternalReferenceCodeCatalog(externalReferenceCode: ___, page: ___, pageSize: ___){actions, currencyCode, defaultLanguageId, externalReferenceCode, id, name, system}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Catalog productByExternalReferenceCodeCatalog(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_catalogResourceComponentServiceObjects,
			this::_populateResourceContext,
			catalogResource ->
				catalogResource.getProductByExternalReferenceCodeCatalog(
					externalReferenceCode, Pagination.of(page, pageSize)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productIdCatalog(id: ___, page: ___, pageSize: ___){actions, currencyCode, defaultLanguageId, externalReferenceCode, id, name, system}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Catalog productIdCatalog(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_catalogResourceComponentServiceObjects,
			this::_populateResourceContext,
			catalogResource -> catalogResource.getProductIdCatalog(
				id, Pagination.of(page, pageSize)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productByExternalReferenceCodeCategories(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public CategoryPage productByExternalReferenceCodeCategories(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_categoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			categoryResource -> new CategoryPage(
				categoryResource.
					getProductByExternalReferenceCodeCategoriesPage(
						externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productIdCategories(id: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public CategoryPage productIdCategories(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_categoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			categoryResource -> new CategoryPage(
				categoryResource.getProductIdCategoriesPage(
					id, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {options(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OptionPage options(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionResource -> new OptionPage(
				optionResource.getOptionsPage(
					search,
					_filterBiFunction.apply(optionResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(optionResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {optionByExternalReferenceCode(externalReferenceCode: ___){actions, catalogId, description, externalReferenceCode, facetable, fieldType, id, key, name, optionValues, priority, required, skuContributor}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Option optionByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionResource -> optionResource.getOptionByExternalReferenceCode(
				externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {option(id: ___){actions, catalogId, description, externalReferenceCode, facetable, fieldType, id, key, name, optionValues, priority, required, skuContributor}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Option option(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_optionResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionResource -> optionResource.getOption(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {optionCategories(filter: ___, page: ___, pageSize: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OptionCategoryPage optionCategories(
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionCategoryResource -> new OptionCategoryPage(
				optionCategoryResource.getOptionCategoriesPage(
					_filterBiFunction.apply(
						optionCategoryResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						optionCategoryResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {optionCategory(id: ___){description, id, key, priority, title}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OptionCategory optionCategory(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionCategoryResource -> optionCategoryResource.getOptionCategory(
				id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {optionByExternalReferenceCodeOptionValues(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OptionValuePage optionByExternalReferenceCodeOptionValues(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionValueResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionValueResource -> new OptionValuePage(
				optionValueResource.
					getOptionByExternalReferenceCodeOptionValuesPage(
						externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {optionIdOptionValues(id: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OptionValuePage optionIdOptionValues(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionValueResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionValueResource -> new OptionValuePage(
				optionValueResource.getOptionIdOptionValuesPage(
					id, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {products(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductPage products(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource -> new ProductPage(
				productResource.getProductsPage(
					search,
					_filterBiFunction.apply(productResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(productResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productByExternalReferenceCode(externalReferenceCode: ___){actions, active, attachments, catalog, catalogId, categories, configuration, createDate, defaultSku, description, displayDate, expando, expirationDate, externalReferenceCode, id, images, metaDescription, metaKeyword, metaTitle, modifiedDate, name, neverExpire, productId, productOptions, productSpecifications, productStatus, productType, productTypeI18n, relatedProducts, shippingConfiguration, shortDescription, skuFormatted, skus, subscriptionConfiguration, tags, taxConfiguration, thumbnail, urls, workflowStatusInfo}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Product productByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource ->
				productResource.getProductByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {product(id: ___){actions, active, attachments, catalog, catalogId, categories, configuration, createDate, defaultSku, description, displayDate, expando, expirationDate, externalReferenceCode, id, images, metaDescription, metaKeyword, metaTitle, modifiedDate, name, neverExpire, productId, productOptions, productSpecifications, productStatus, productType, productTypeI18n, relatedProducts, shippingConfiguration, shortDescription, skuFormatted, skus, subscriptionConfiguration, tags, taxConfiguration, thumbnail, urls, workflowStatusInfo}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Product product(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource -> productResource.getProduct(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productByExternalReferenceCodeConfiguration(externalReferenceCode: ___){allowBackOrder, allowedOrderQuantities, displayAvailability, displayStockQuantity, inventoryEngine, lowStockAction, maxOrderQuantity, minOrderQuantity, minStockQuantity, multipleOrderQuantity}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductConfiguration productByExternalReferenceCodeConfiguration(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_productConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productConfigurationResource ->
				productConfigurationResource.
					getProductByExternalReferenceCodeConfiguration(
						externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productIdConfiguration(id: ___){allowBackOrder, allowedOrderQuantities, displayAvailability, displayStockQuantity, inventoryEngine, lowStockAction, maxOrderQuantity, minOrderQuantity, minStockQuantity, multipleOrderQuantity}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductConfiguration productIdConfiguration(
			@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_productConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productConfigurationResource ->
				productConfigurationResource.getProductIdConfiguration(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productGroups(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductGroupPage productGroups(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_productGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupResource -> new ProductGroupPage(
				productGroupResource.getProductGroupsPage(
					search,
					_filterBiFunction.apply(productGroupResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						productGroupResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productGroupByExternalReferenceCode(externalReferenceCode: ___){customFields, description, externalReferenceCode, id, products, productsCount, title}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductGroup productGroupByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_productGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupResource ->
				productGroupResource.getProductGroupByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productGroup(id: ___){customFields, description, externalReferenceCode, id, products, productsCount, title}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductGroup productGroup(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_productGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupResource -> productGroupResource.getProductGroup(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productGroupByExternalReferenceCodeProductGroupProducts(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductGroupProductPage
			productGroupByExternalReferenceCodeProductGroupProducts(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_productGroupProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupProductResource -> new ProductGroupProductPage(
				productGroupProductResource.
					getProductGroupByExternalReferenceCodeProductGroupProductsPage(
						externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productGroupIdProductGroupProducts(id: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductGroupProductPage productGroupIdProductGroupProducts(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_productGroupProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupProductResource -> new ProductGroupProductPage(
				productGroupProductResource.
					getProductGroupIdProductGroupProductsPage(
						id, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productOption(id: ___){catalogId, description, facetable, fieldType, id, key, name, optionId, priority, productOptionValues, required, skuContributor}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductOption productOption(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_productOptionResourceComponentServiceObjects,
			this::_populateResourceContext,
			productOptionResource -> productOptionResource.getProductOption(
				id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productByExternalReferenceCodeProductOptions(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductOptionPage productByExternalReferenceCodeProductOptions(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_productOptionResourceComponentServiceObjects,
			this::_populateResourceContext,
			productOptionResource -> new ProductOptionPage(
				productOptionResource.
					getProductByExternalReferenceCodeProductOptionsPage(
						externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productIdProductOptions(id: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductOptionPage productIdProductOptions(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_productOptionResourceComponentServiceObjects,
			this::_populateResourceContext,
			productOptionResource -> new ProductOptionPage(
				productOptionResource.getProductIdProductOptionsPage(
					id, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productOptionIdProductOptionValues(id: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductOptionValuePage productOptionIdProductOptionValues(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_productOptionValueResourceComponentServiceObjects,
			this::_populateResourceContext,
			productOptionValueResource -> new ProductOptionValuePage(
				productOptionValueResource.
					getProductOptionIdProductOptionValuesPage(
						id, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productByExternalReferenceCodeShippingConfiguration(externalReferenceCode: ___){depth, freeShipping, height, shippable, shippingExtraPrice, shippingSeparately, weight, width}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductShippingConfiguration
			productByExternalReferenceCodeShippingConfiguration(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_productShippingConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productShippingConfigurationResource ->
				productShippingConfigurationResource.
					getProductByExternalReferenceCodeShippingConfiguration(
						externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productIdShippingConfiguration(id: ___){depth, freeShipping, height, shippable, shippingExtraPrice, shippingSeparately, weight, width}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductShippingConfiguration productIdShippingConfiguration(
			@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_productShippingConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productShippingConfigurationResource ->
				productShippingConfigurationResource.
					getProductIdShippingConfiguration(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productIdProductSpecifications(id: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductSpecificationPage productIdProductSpecifications(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_productSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productSpecificationResource -> new ProductSpecificationPage(
				productSpecificationResource.
					getProductIdProductSpecificationsPage(
						id, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productByExternalReferenceCodeSubscriptionConfiguration(externalReferenceCode: ___){enable, length, numberOfLength, subscriptionType, subscriptionTypeSettings}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductSubscriptionConfiguration
			productByExternalReferenceCodeSubscriptionConfiguration(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_productSubscriptionConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productSubscriptionConfigurationResource ->
				productSubscriptionConfigurationResource.
					getProductByExternalReferenceCodeSubscriptionConfiguration(
						externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productIdSubscriptionConfiguration(id: ___){enable, length, numberOfLength, subscriptionType, subscriptionTypeSettings}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductSubscriptionConfiguration productIdSubscriptionConfiguration(
			@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_productSubscriptionConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productSubscriptionConfigurationResource ->
				productSubscriptionConfigurationResource.
					getProductIdSubscriptionConfiguration(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productByExternalReferenceCodeTaxConfiguration(externalReferenceCode: ___){id, taxCategory, taxable}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductTaxConfiguration
			productByExternalReferenceCodeTaxConfiguration(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_productTaxConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productTaxConfigurationResource ->
				productTaxConfigurationResource.
					getProductByExternalReferenceCodeTaxConfiguration(
						externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productIdTaxConfiguration(id: ___){id, taxCategory, taxable}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductTaxConfiguration productIdTaxConfiguration(
			@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_productTaxConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productTaxConfigurationResource ->
				productTaxConfigurationResource.getProductIdTaxConfiguration(
					id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productByExternalReferenceCodeRelatedProducts(externalReferenceCode: ___, page: ___, pageSize: ___, type: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public RelatedProductPage productByExternalReferenceCodeRelatedProducts(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("type") String type,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_relatedProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			relatedProductResource -> new RelatedProductPage(
				relatedProductResource.
					getProductByExternalReferenceCodeRelatedProductsPage(
						externalReferenceCode, type,
						Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productIdRelatedProducts(id: ___, page: ___, pageSize: ___, type: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public RelatedProductPage productIdRelatedProducts(
			@GraphQLName("id") Long id, @GraphQLName("type") String type,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_relatedProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			relatedProductResource -> new RelatedProductPage(
				relatedProductResource.getProductIdRelatedProductsPage(
					id, type, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {relatedProduct(id: ___){id, priority, productExternalReferenceCode, productId, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public RelatedProduct relatedProduct(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_relatedProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			relatedProductResource -> relatedProductResource.getRelatedProduct(
				id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productByExternalReferenceCodeSkus(externalReferenceCode: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SkuPage productByExternalReferenceCodeSkus(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> new SkuPage(
				skuResource.getProductByExternalReferenceCodeSkusPage(
					externalReferenceCode, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {productIdSkus(id: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SkuPage productIdSkus(
			@GraphQLName("id") Long id, @GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> new SkuPage(
				skuResource.getProductIdSkusPage(
					id, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {skus(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SkuPage skus(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> new SkuPage(
				skuResource.getSkusPage(
					search, _filterBiFunction.apply(skuResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(skuResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {skuByExternalReferenceCode(externalReferenceCode: ___){cost, depth, displayDate, expirationDate, externalReferenceCode, gtin, height, id, inventoryLevel, manufacturerPartNumber, neverExpire, options, price, productId, productName, promoPrice, published, purchasable, sku, unspsc, weight, width}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Sku skuByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> skuResource.getSkuByExternalReferenceCode(
				externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {sku(id: ___){cost, depth, displayDate, expirationDate, externalReferenceCode, gtin, height, id, inventoryLevel, manufacturerPartNumber, neverExpire, options, price, productId, productName, promoPrice, published, purchasable, sku, unspsc, weight, width}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Sku sku(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> skuResource.getSku(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {specifications(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SpecificationPage specifications(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_specificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			specificationResource -> new SpecificationPage(
				specificationResource.getSpecificationsPage(
					search,
					_filterBiFunction.apply(
						specificationResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						specificationResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {specification(id: ___){description, facetable, id, key, optionCategory, title}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Specification specification(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_specificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			specificationResource -> specificationResource.getSpecification(
				id));
	}

	@GraphQLTypeExtension(Catalog.class)
	public class GetOptionByExternalReferenceCodeOptionValuesPageTypeExtension {

		public GetOptionByExternalReferenceCodeOptionValuesPageTypeExtension(
			Catalog catalog) {

			_catalog = catalog;
		}

		@GraphQLField
		public OptionValuePage optionByExternalReferenceCodeOptionValues(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_optionValueResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				optionValueResource -> new OptionValuePage(
					optionValueResource.
						getOptionByExternalReferenceCodeOptionValuesPage(
							_catalog.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class GetProductByExternalReferenceCodeConfigurationTypeExtension {

		public GetProductByExternalReferenceCodeConfigurationTypeExtension(
			Catalog catalog) {

			_catalog = catalog;
		}

		@GraphQLField
		public ProductConfiguration
				productByExternalReferenceCodeConfiguration()
			throws Exception {

			return _applyComponentServiceObjects(
				_productConfigurationResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				productConfigurationResource ->
					productConfigurationResource.
						getProductByExternalReferenceCodeConfiguration(
							_catalog.getExternalReferenceCode()));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class GetProductByExternalReferenceCodeTypeExtension {

		public GetProductByExternalReferenceCodeTypeExtension(Catalog catalog) {
			_catalog = catalog;
		}

		@GraphQLField
		public Product productByExternalReferenceCode() throws Exception {
			return _applyComponentServiceObjects(
				_productResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				productResource ->
					productResource.getProductByExternalReferenceCode(
						_catalog.getExternalReferenceCode()));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class GetProductByExternalReferenceCodeCategoriesPageTypeExtension {

		public GetProductByExternalReferenceCodeCategoriesPageTypeExtension(
			Catalog catalog) {

			_catalog = catalog;
		}

		@GraphQLField
		public CategoryPage productByExternalReferenceCodeCategories(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_categoryResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				categoryResource -> new CategoryPage(
					categoryResource.
						getProductByExternalReferenceCodeCategoriesPage(
							_catalog.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class
		GetProductGroupByExternalReferenceCodeProductGroupProductsPageTypeExtension {

		public GetProductGroupByExternalReferenceCodeProductGroupProductsPageTypeExtension(
			Catalog catalog) {

			_catalog = catalog;
		}

		@GraphQLField
		public ProductGroupProductPage
				productGroupByExternalReferenceCodeProductGroupProducts(
					@GraphQLName("pageSize") int pageSize,
					@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_productGroupProductResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				productGroupProductResource -> new ProductGroupProductPage(
					productGroupProductResource.
						getProductGroupByExternalReferenceCodeProductGroupProductsPage(
							_catalog.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class GetProductByExternalReferenceCodeCatalogTypeExtension {

		public GetProductByExternalReferenceCodeCatalogTypeExtension(
			Catalog catalog) {

			_catalog = catalog;
		}

		@GraphQLField
		public Catalog productByExternalReferenceCodeCatalog(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_catalogResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				catalogResource ->
					catalogResource.getProductByExternalReferenceCodeCatalog(
						_catalog.getExternalReferenceCode(),
						Pagination.of(page, pageSize)));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class
		GetProductByExternalReferenceCodeProductOptionsPageTypeExtension {

		public GetProductByExternalReferenceCodeProductOptionsPageTypeExtension(
			Catalog catalog) {

			_catalog = catalog;
		}

		@GraphQLField
		public ProductOptionPage productByExternalReferenceCodeProductOptions(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_productOptionResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				productOptionResource -> new ProductOptionPage(
					productOptionResource.
						getProductByExternalReferenceCodeProductOptionsPage(
							_catalog.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Option.class)
	public class GetCatalogByExternalReferenceCodeTypeExtension {

		public GetCatalogByExternalReferenceCodeTypeExtension(Option option) {
			_option = option;
		}

		@GraphQLField
		public Catalog catalogByExternalReferenceCode() throws Exception {
			return _applyComponentServiceObjects(
				_catalogResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				catalogResource ->
					catalogResource.getCatalogByExternalReferenceCode(
						_option.getExternalReferenceCode()));
		}

		private Option _option;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class
		GetProductByExternalReferenceCodeRelatedProductsPageTypeExtension {

		public GetProductByExternalReferenceCodeRelatedProductsPageTypeExtension(
			Catalog catalog) {

			_catalog = catalog;
		}

		@GraphQLField
		public RelatedProductPage productByExternalReferenceCodeRelatedProducts(
				@GraphQLName("type") String type,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_relatedProductResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				relatedProductResource -> new RelatedProductPage(
					relatedProductResource.
						getProductByExternalReferenceCodeRelatedProductsPage(
							_catalog.getExternalReferenceCode(), type,
							Pagination.of(page, pageSize))));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class GetProductByExternalReferenceCodeSkusPageTypeExtension {

		public GetProductByExternalReferenceCodeSkusPageTypeExtension(
			Catalog catalog) {

			_catalog = catalog;
		}

		@GraphQLField
		public SkuPage productByExternalReferenceCodeSkus(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_skuResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				skuResource -> new SkuPage(
					skuResource.getProductByExternalReferenceCodeSkusPage(
						_catalog.getExternalReferenceCode(),
						Pagination.of(page, pageSize))));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class
		GetProductByExternalReferenceCodeShippingConfigurationTypeExtension {

		public GetProductByExternalReferenceCodeShippingConfigurationTypeExtension(
			Catalog catalog) {

			_catalog = catalog;
		}

		@GraphQLField
		public ProductShippingConfiguration
				productByExternalReferenceCodeShippingConfiguration()
			throws Exception {

			return _applyComponentServiceObjects(
				_productShippingConfigurationResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				productShippingConfigurationResource ->
					productShippingConfigurationResource.
						getProductByExternalReferenceCodeShippingConfiguration(
							_catalog.getExternalReferenceCode()));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class GetSkuByExternalReferenceCodeTypeExtension {

		public GetSkuByExternalReferenceCodeTypeExtension(Catalog catalog) {
			_catalog = catalog;
		}

		@GraphQLField
		public Sku skuByExternalReferenceCode() throws Exception {
			return _applyComponentServiceObjects(
				_skuResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				skuResource -> skuResource.getSkuByExternalReferenceCode(
					_catalog.getExternalReferenceCode()));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class
		GetProductByExternalReferenceCodeTaxConfigurationTypeExtension {

		public GetProductByExternalReferenceCodeTaxConfigurationTypeExtension(
			Catalog catalog) {

			_catalog = catalog;
		}

		@GraphQLField
		public ProductTaxConfiguration
				productByExternalReferenceCodeTaxConfiguration()
			throws Exception {

			return _applyComponentServiceObjects(
				_productTaxConfigurationResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				productTaxConfigurationResource ->
					productTaxConfigurationResource.
						getProductByExternalReferenceCodeTaxConfiguration(
							_catalog.getExternalReferenceCode()));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class GetProductByExternalReferenceCodeImagesPageTypeExtension {

		public GetProductByExternalReferenceCodeImagesPageTypeExtension(
			Catalog catalog) {

			_catalog = catalog;
		}

		@GraphQLField
		public AttachmentPage productByExternalReferenceCodeImages(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_attachmentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				attachmentResource -> new AttachmentPage(
					attachmentResource.
						getProductByExternalReferenceCodeImagesPage(
							_catalog.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class GetOptionByExternalReferenceCodeTypeExtension {

		public GetOptionByExternalReferenceCodeTypeExtension(Catalog catalog) {
			_catalog = catalog;
		}

		@GraphQLField
		public Option optionByExternalReferenceCode() throws Exception {
			return _applyComponentServiceObjects(
				_optionResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				optionResource ->
					optionResource.getOptionByExternalReferenceCode(
						_catalog.getExternalReferenceCode()));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class GetProductGroupByExternalReferenceCodeTypeExtension {

		public GetProductGroupByExternalReferenceCodeTypeExtension(
			Catalog catalog) {

			_catalog = catalog;
		}

		@GraphQLField
		public ProductGroup productGroupByExternalReferenceCode()
			throws Exception {

			return _applyComponentServiceObjects(
				_productGroupResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				productGroupResource ->
					productGroupResource.getProductGroupByExternalReferenceCode(
						_catalog.getExternalReferenceCode()));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class
		GetProductByExternalReferenceCodeSubscriptionConfigurationTypeExtension {

		public GetProductByExternalReferenceCodeSubscriptionConfigurationTypeExtension(
			Catalog catalog) {

			_catalog = catalog;
		}

		@GraphQLField
		public ProductSubscriptionConfiguration
				productByExternalReferenceCodeSubscriptionConfiguration()
			throws Exception {

			return _applyComponentServiceObjects(
				_productSubscriptionConfigurationResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				productSubscriptionConfigurationResource ->
					productSubscriptionConfigurationResource.
						getProductByExternalReferenceCodeSubscriptionConfiguration(
							_catalog.getExternalReferenceCode()));
		}

		private Catalog _catalog;

	}

	@GraphQLTypeExtension(Catalog.class)
	public class GetProductByExternalReferenceCodeAttachmentsPageTypeExtension {

		public GetProductByExternalReferenceCodeAttachmentsPageTypeExtension(
			Catalog catalog) {

			_catalog = catalog;
		}

		@GraphQLField
		public AttachmentPage productByExternalReferenceCodeAttachments(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_attachmentResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				attachmentResource -> new AttachmentPage(
					attachmentResource.
						getProductByExternalReferenceCodeAttachmentsPage(
							_catalog.getExternalReferenceCode(),
							Pagination.of(page, pageSize))));
		}

		private Catalog _catalog;

	}

	@GraphQLName("AttachmentPage")
	public class AttachmentPage {

		public AttachmentPage(Page attachmentPage) {
			actions = attachmentPage.getActions();

			items = attachmentPage.getItems();
			lastPage = attachmentPage.getLastPage();
			page = attachmentPage.getPage();
			pageSize = attachmentPage.getPageSize();
			totalCount = attachmentPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Attachment> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("CatalogPage")
	public class CatalogPage {

		public CatalogPage(Page catalogPage) {
			actions = catalogPage.getActions();

			items = catalogPage.getItems();
			lastPage = catalogPage.getLastPage();
			page = catalogPage.getPage();
			pageSize = catalogPage.getPageSize();
			totalCount = catalogPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Catalog> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("CategoryPage")
	public class CategoryPage {

		public CategoryPage(Page categoryPage) {
			actions = categoryPage.getActions();

			items = categoryPage.getItems();
			lastPage = categoryPage.getLastPage();
			page = categoryPage.getPage();
			pageSize = categoryPage.getPageSize();
			totalCount = categoryPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Category> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OptionPage")
	public class OptionPage {

		public OptionPage(Page optionPage) {
			actions = optionPage.getActions();

			items = optionPage.getItems();
			lastPage = optionPage.getLastPage();
			page = optionPage.getPage();
			pageSize = optionPage.getPageSize();
			totalCount = optionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Option> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OptionCategoryPage")
	public class OptionCategoryPage {

		public OptionCategoryPage(Page optionCategoryPage) {
			actions = optionCategoryPage.getActions();

			items = optionCategoryPage.getItems();
			lastPage = optionCategoryPage.getLastPage();
			page = optionCategoryPage.getPage();
			pageSize = optionCategoryPage.getPageSize();
			totalCount = optionCategoryPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<OptionCategory> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OptionValuePage")
	public class OptionValuePage {

		public OptionValuePage(Page optionValuePage) {
			actions = optionValuePage.getActions();

			items = optionValuePage.getItems();
			lastPage = optionValuePage.getLastPage();
			page = optionValuePage.getPage();
			pageSize = optionValuePage.getPageSize();
			totalCount = optionValuePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<OptionValue> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProductPage")
	public class ProductPage {

		public ProductPage(Page productPage) {
			actions = productPage.getActions();

			items = productPage.getItems();
			lastPage = productPage.getLastPage();
			page = productPage.getPage();
			pageSize = productPage.getPageSize();
			totalCount = productPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Product> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProductConfigurationPage")
	public class ProductConfigurationPage {

		public ProductConfigurationPage(Page productConfigurationPage) {
			actions = productConfigurationPage.getActions();

			items = productConfigurationPage.getItems();
			lastPage = productConfigurationPage.getLastPage();
			page = productConfigurationPage.getPage();
			pageSize = productConfigurationPage.getPageSize();
			totalCount = productConfigurationPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ProductConfiguration> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProductGroupPage")
	public class ProductGroupPage {

		public ProductGroupPage(Page productGroupPage) {
			actions = productGroupPage.getActions();

			items = productGroupPage.getItems();
			lastPage = productGroupPage.getLastPage();
			page = productGroupPage.getPage();
			pageSize = productGroupPage.getPageSize();
			totalCount = productGroupPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ProductGroup> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProductGroupProductPage")
	public class ProductGroupProductPage {

		public ProductGroupProductPage(Page productGroupProductPage) {
			actions = productGroupProductPage.getActions();

			items = productGroupProductPage.getItems();
			lastPage = productGroupProductPage.getLastPage();
			page = productGroupProductPage.getPage();
			pageSize = productGroupProductPage.getPageSize();
			totalCount = productGroupProductPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ProductGroupProduct> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProductOptionPage")
	public class ProductOptionPage {

		public ProductOptionPage(Page productOptionPage) {
			actions = productOptionPage.getActions();

			items = productOptionPage.getItems();
			lastPage = productOptionPage.getLastPage();
			page = productOptionPage.getPage();
			pageSize = productOptionPage.getPageSize();
			totalCount = productOptionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ProductOption> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProductOptionValuePage")
	public class ProductOptionValuePage {

		public ProductOptionValuePage(Page productOptionValuePage) {
			actions = productOptionValuePage.getActions();

			items = productOptionValuePage.getItems();
			lastPage = productOptionValuePage.getLastPage();
			page = productOptionValuePage.getPage();
			pageSize = productOptionValuePage.getPageSize();
			totalCount = productOptionValuePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ProductOptionValue> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProductShippingConfigurationPage")
	public class ProductShippingConfigurationPage {

		public ProductShippingConfigurationPage(
			Page productShippingConfigurationPage) {

			actions = productShippingConfigurationPage.getActions();

			items = productShippingConfigurationPage.getItems();
			lastPage = productShippingConfigurationPage.getLastPage();
			page = productShippingConfigurationPage.getPage();
			pageSize = productShippingConfigurationPage.getPageSize();
			totalCount = productShippingConfigurationPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ProductShippingConfiguration> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProductSpecificationPage")
	public class ProductSpecificationPage {

		public ProductSpecificationPage(Page productSpecificationPage) {
			actions = productSpecificationPage.getActions();

			items = productSpecificationPage.getItems();
			lastPage = productSpecificationPage.getLastPage();
			page = productSpecificationPage.getPage();
			pageSize = productSpecificationPage.getPageSize();
			totalCount = productSpecificationPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ProductSpecification> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProductSubscriptionConfigurationPage")
	public class ProductSubscriptionConfigurationPage {

		public ProductSubscriptionConfigurationPage(
			Page productSubscriptionConfigurationPage) {

			actions = productSubscriptionConfigurationPage.getActions();

			items = productSubscriptionConfigurationPage.getItems();
			lastPage = productSubscriptionConfigurationPage.getLastPage();
			page = productSubscriptionConfigurationPage.getPage();
			pageSize = productSubscriptionConfigurationPage.getPageSize();
			totalCount = productSubscriptionConfigurationPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ProductSubscriptionConfiguration> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProductTaxConfigurationPage")
	public class ProductTaxConfigurationPage {

		public ProductTaxConfigurationPage(Page productTaxConfigurationPage) {
			actions = productTaxConfigurationPage.getActions();

			items = productTaxConfigurationPage.getItems();
			lastPage = productTaxConfigurationPage.getLastPage();
			page = productTaxConfigurationPage.getPage();
			pageSize = productTaxConfigurationPage.getPageSize();
			totalCount = productTaxConfigurationPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ProductTaxConfiguration> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("RelatedProductPage")
	public class RelatedProductPage {

		public RelatedProductPage(Page relatedProductPage) {
			actions = relatedProductPage.getActions();

			items = relatedProductPage.getItems();
			lastPage = relatedProductPage.getLastPage();
			page = relatedProductPage.getPage();
			pageSize = relatedProductPage.getPageSize();
			totalCount = relatedProductPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<RelatedProduct> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SkuPage")
	public class SkuPage {

		public SkuPage(Page skuPage) {
			actions = skuPage.getActions();

			items = skuPage.getItems();
			lastPage = skuPage.getLastPage();
			page = skuPage.getPage();
			pageSize = skuPage.getPageSize();
			totalCount = skuPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Sku> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SpecificationPage")
	public class SpecificationPage {

		public SpecificationPage(Page specificationPage) {
			actions = specificationPage.getActions();

			items = specificationPage.getItems();
			lastPage = specificationPage.getLastPage();
			page = specificationPage.getPage();
			pageSize = specificationPage.getPageSize();
			totalCount = specificationPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Specification> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(AttachmentResource attachmentResource)
		throws Exception {

		attachmentResource.setContextAcceptLanguage(_acceptLanguage);
		attachmentResource.setContextCompany(_company);
		attachmentResource.setContextHttpServletRequest(_httpServletRequest);
		attachmentResource.setContextHttpServletResponse(_httpServletResponse);
		attachmentResource.setContextUriInfo(_uriInfo);
		attachmentResource.setContextUser(_user);
		attachmentResource.setGroupLocalService(_groupLocalService);
		attachmentResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(CatalogResource catalogResource)
		throws Exception {

		catalogResource.setContextAcceptLanguage(_acceptLanguage);
		catalogResource.setContextCompany(_company);
		catalogResource.setContextHttpServletRequest(_httpServletRequest);
		catalogResource.setContextHttpServletResponse(_httpServletResponse);
		catalogResource.setContextUriInfo(_uriInfo);
		catalogResource.setContextUser(_user);
		catalogResource.setGroupLocalService(_groupLocalService);
		catalogResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(CategoryResource categoryResource)
		throws Exception {

		categoryResource.setContextAcceptLanguage(_acceptLanguage);
		categoryResource.setContextCompany(_company);
		categoryResource.setContextHttpServletRequest(_httpServletRequest);
		categoryResource.setContextHttpServletResponse(_httpServletResponse);
		categoryResource.setContextUriInfo(_uriInfo);
		categoryResource.setContextUser(_user);
		categoryResource.setGroupLocalService(_groupLocalService);
		categoryResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(OptionResource optionResource)
		throws Exception {

		optionResource.setContextAcceptLanguage(_acceptLanguage);
		optionResource.setContextCompany(_company);
		optionResource.setContextHttpServletRequest(_httpServletRequest);
		optionResource.setContextHttpServletResponse(_httpServletResponse);
		optionResource.setContextUriInfo(_uriInfo);
		optionResource.setContextUser(_user);
		optionResource.setGroupLocalService(_groupLocalService);
		optionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			OptionCategoryResource optionCategoryResource)
		throws Exception {

		optionCategoryResource.setContextAcceptLanguage(_acceptLanguage);
		optionCategoryResource.setContextCompany(_company);
		optionCategoryResource.setContextHttpServletRequest(
			_httpServletRequest);
		optionCategoryResource.setContextHttpServletResponse(
			_httpServletResponse);
		optionCategoryResource.setContextUriInfo(_uriInfo);
		optionCategoryResource.setContextUser(_user);
		optionCategoryResource.setGroupLocalService(_groupLocalService);
		optionCategoryResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			OptionValueResource optionValueResource)
		throws Exception {

		optionValueResource.setContextAcceptLanguage(_acceptLanguage);
		optionValueResource.setContextCompany(_company);
		optionValueResource.setContextHttpServletRequest(_httpServletRequest);
		optionValueResource.setContextHttpServletResponse(_httpServletResponse);
		optionValueResource.setContextUriInfo(_uriInfo);
		optionValueResource.setContextUser(_user);
		optionValueResource.setGroupLocalService(_groupLocalService);
		optionValueResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(ProductResource productResource)
		throws Exception {

		productResource.setContextAcceptLanguage(_acceptLanguage);
		productResource.setContextCompany(_company);
		productResource.setContextHttpServletRequest(_httpServletRequest);
		productResource.setContextHttpServletResponse(_httpServletResponse);
		productResource.setContextUriInfo(_uriInfo);
		productResource.setContextUser(_user);
		productResource.setGroupLocalService(_groupLocalService);
		productResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ProductConfigurationResource productConfigurationResource)
		throws Exception {

		productConfigurationResource.setContextAcceptLanguage(_acceptLanguage);
		productConfigurationResource.setContextCompany(_company);
		productConfigurationResource.setContextHttpServletRequest(
			_httpServletRequest);
		productConfigurationResource.setContextHttpServletResponse(
			_httpServletResponse);
		productConfigurationResource.setContextUriInfo(_uriInfo);
		productConfigurationResource.setContextUser(_user);
		productConfigurationResource.setGroupLocalService(_groupLocalService);
		productConfigurationResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ProductGroupResource productGroupResource)
		throws Exception {

		productGroupResource.setContextAcceptLanguage(_acceptLanguage);
		productGroupResource.setContextCompany(_company);
		productGroupResource.setContextHttpServletRequest(_httpServletRequest);
		productGroupResource.setContextHttpServletResponse(
			_httpServletResponse);
		productGroupResource.setContextUriInfo(_uriInfo);
		productGroupResource.setContextUser(_user);
		productGroupResource.setGroupLocalService(_groupLocalService);
		productGroupResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ProductGroupProductResource productGroupProductResource)
		throws Exception {

		productGroupProductResource.setContextAcceptLanguage(_acceptLanguage);
		productGroupProductResource.setContextCompany(_company);
		productGroupProductResource.setContextHttpServletRequest(
			_httpServletRequest);
		productGroupProductResource.setContextHttpServletResponse(
			_httpServletResponse);
		productGroupProductResource.setContextUriInfo(_uriInfo);
		productGroupProductResource.setContextUser(_user);
		productGroupProductResource.setGroupLocalService(_groupLocalService);
		productGroupProductResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ProductOptionResource productOptionResource)
		throws Exception {

		productOptionResource.setContextAcceptLanguage(_acceptLanguage);
		productOptionResource.setContextCompany(_company);
		productOptionResource.setContextHttpServletRequest(_httpServletRequest);
		productOptionResource.setContextHttpServletResponse(
			_httpServletResponse);
		productOptionResource.setContextUriInfo(_uriInfo);
		productOptionResource.setContextUser(_user);
		productOptionResource.setGroupLocalService(_groupLocalService);
		productOptionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ProductOptionValueResource productOptionValueResource)
		throws Exception {

		productOptionValueResource.setContextAcceptLanguage(_acceptLanguage);
		productOptionValueResource.setContextCompany(_company);
		productOptionValueResource.setContextHttpServletRequest(
			_httpServletRequest);
		productOptionValueResource.setContextHttpServletResponse(
			_httpServletResponse);
		productOptionValueResource.setContextUriInfo(_uriInfo);
		productOptionValueResource.setContextUser(_user);
		productOptionValueResource.setGroupLocalService(_groupLocalService);
		productOptionValueResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ProductShippingConfigurationResource
				productShippingConfigurationResource)
		throws Exception {

		productShippingConfigurationResource.setContextAcceptLanguage(
			_acceptLanguage);
		productShippingConfigurationResource.setContextCompany(_company);
		productShippingConfigurationResource.setContextHttpServletRequest(
			_httpServletRequest);
		productShippingConfigurationResource.setContextHttpServletResponse(
			_httpServletResponse);
		productShippingConfigurationResource.setContextUriInfo(_uriInfo);
		productShippingConfigurationResource.setContextUser(_user);
		productShippingConfigurationResource.setGroupLocalService(
			_groupLocalService);
		productShippingConfigurationResource.setRoleLocalService(
			_roleLocalService);
	}

	private void _populateResourceContext(
			ProductSpecificationResource productSpecificationResource)
		throws Exception {

		productSpecificationResource.setContextAcceptLanguage(_acceptLanguage);
		productSpecificationResource.setContextCompany(_company);
		productSpecificationResource.setContextHttpServletRequest(
			_httpServletRequest);
		productSpecificationResource.setContextHttpServletResponse(
			_httpServletResponse);
		productSpecificationResource.setContextUriInfo(_uriInfo);
		productSpecificationResource.setContextUser(_user);
		productSpecificationResource.setGroupLocalService(_groupLocalService);
		productSpecificationResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ProductSubscriptionConfigurationResource
				productSubscriptionConfigurationResource)
		throws Exception {

		productSubscriptionConfigurationResource.setContextAcceptLanguage(
			_acceptLanguage);
		productSubscriptionConfigurationResource.setContextCompany(_company);
		productSubscriptionConfigurationResource.setContextHttpServletRequest(
			_httpServletRequest);
		productSubscriptionConfigurationResource.setContextHttpServletResponse(
			_httpServletResponse);
		productSubscriptionConfigurationResource.setContextUriInfo(_uriInfo);
		productSubscriptionConfigurationResource.setContextUser(_user);
		productSubscriptionConfigurationResource.setGroupLocalService(
			_groupLocalService);
		productSubscriptionConfigurationResource.setRoleLocalService(
			_roleLocalService);
	}

	private void _populateResourceContext(
			ProductTaxConfigurationResource productTaxConfigurationResource)
		throws Exception {

		productTaxConfigurationResource.setContextAcceptLanguage(
			_acceptLanguage);
		productTaxConfigurationResource.setContextCompany(_company);
		productTaxConfigurationResource.setContextHttpServletRequest(
			_httpServletRequest);
		productTaxConfigurationResource.setContextHttpServletResponse(
			_httpServletResponse);
		productTaxConfigurationResource.setContextUriInfo(_uriInfo);
		productTaxConfigurationResource.setContextUser(_user);
		productTaxConfigurationResource.setGroupLocalService(
			_groupLocalService);
		productTaxConfigurationResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			RelatedProductResource relatedProductResource)
		throws Exception {

		relatedProductResource.setContextAcceptLanguage(_acceptLanguage);
		relatedProductResource.setContextCompany(_company);
		relatedProductResource.setContextHttpServletRequest(
			_httpServletRequest);
		relatedProductResource.setContextHttpServletResponse(
			_httpServletResponse);
		relatedProductResource.setContextUriInfo(_uriInfo);
		relatedProductResource.setContextUser(_user);
		relatedProductResource.setGroupLocalService(_groupLocalService);
		relatedProductResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(SkuResource skuResource)
		throws Exception {

		skuResource.setContextAcceptLanguage(_acceptLanguage);
		skuResource.setContextCompany(_company);
		skuResource.setContextHttpServletRequest(_httpServletRequest);
		skuResource.setContextHttpServletResponse(_httpServletResponse);
		skuResource.setContextUriInfo(_uriInfo);
		skuResource.setContextUser(_user);
		skuResource.setGroupLocalService(_groupLocalService);
		skuResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			SpecificationResource specificationResource)
		throws Exception {

		specificationResource.setContextAcceptLanguage(_acceptLanguage);
		specificationResource.setContextCompany(_company);
		specificationResource.setContextHttpServletRequest(_httpServletRequest);
		specificationResource.setContextHttpServletResponse(
			_httpServletResponse);
		specificationResource.setContextUriInfo(_uriInfo);
		specificationResource.setContextUser(_user);
		specificationResource.setGroupLocalService(_groupLocalService);
		specificationResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<AttachmentResource>
		_attachmentResourceComponentServiceObjects;
	private static ComponentServiceObjects<CatalogResource>
		_catalogResourceComponentServiceObjects;
	private static ComponentServiceObjects<CategoryResource>
		_categoryResourceComponentServiceObjects;
	private static ComponentServiceObjects<OptionResource>
		_optionResourceComponentServiceObjects;
	private static ComponentServiceObjects<OptionCategoryResource>
		_optionCategoryResourceComponentServiceObjects;
	private static ComponentServiceObjects<OptionValueResource>
		_optionValueResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductResource>
		_productResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductConfigurationResource>
		_productConfigurationResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductGroupResource>
		_productGroupResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductGroupProductResource>
		_productGroupProductResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductOptionResource>
		_productOptionResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductOptionValueResource>
		_productOptionValueResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductShippingConfigurationResource>
		_productShippingConfigurationResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductSpecificationResource>
		_productSpecificationResourceComponentServiceObjects;
	private static ComponentServiceObjects
		<ProductSubscriptionConfigurationResource>
			_productSubscriptionConfigurationResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductTaxConfigurationResource>
		_productTaxConfigurationResourceComponentServiceObjects;
	private static ComponentServiceObjects<RelatedProductResource>
		_relatedProductResourceComponentServiceObjects;
	private static ComponentServiceObjects<SkuResource>
		_skuResourceComponentServiceObjects;
	private static ComponentServiceObjects<SpecificationResource>
		_specificationResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}