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

package com.liferay.headless.commerce.delivery.catalog.internal.graphql.query.v1_0;

import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Attachment;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Category;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductSpecification;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.RelatedProduct;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Sku;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.AttachmentResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.CategoryResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.ProductOptionResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.ProductResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.ProductSpecificationResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.RelatedProductResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.SkuResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
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
 * @author Andrea Sbarra
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

	public static void setCategoryResourceComponentServiceObjects(
		ComponentServiceObjects<CategoryResource>
			categoryResourceComponentServiceObjects) {

		_categoryResourceComponentServiceObjects =
			categoryResourceComponentServiceObjects;
	}

	public static void setProductResourceComponentServiceObjects(
		ComponentServiceObjects<ProductResource>
			productResourceComponentServiceObjects) {

		_productResourceComponentServiceObjects =
			productResourceComponentServiceObjects;
	}

	public static void setProductOptionResourceComponentServiceObjects(
		ComponentServiceObjects<ProductOptionResource>
			productOptionResourceComponentServiceObjects) {

		_productOptionResourceComponentServiceObjects =
			productOptionResourceComponentServiceObjects;
	}

	public static void setProductSpecificationResourceComponentServiceObjects(
		ComponentServiceObjects<ProductSpecificationResource>
			productSpecificationResourceComponentServiceObjects) {

		_productSpecificationResourceComponentServiceObjects =
			productSpecificationResourceComponentServiceObjects;
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

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {channelProductAttachments(accountId: ___, channelId: ___, page: ___, pageSize: ___, productId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AttachmentPage channelProductAttachments(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("productId") Long productId,
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource -> new AttachmentPage(
				attachmentResource.getChannelProductAttachmentsPage(
					channelId, productId, accountId,
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {channelProductImages(accountId: ___, channelId: ___, page: ___, pageSize: ___, productId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AttachmentPage channelProductImages(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("productId") Long productId,
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource -> new AttachmentPage(
				attachmentResource.getChannelProductImagesPage(
					channelId, productId, accountId,
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {channelProductCategories(channelId: ___, page: ___, pageSize: ___, productId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Gets a list of Category related to a Product.")
	public CategoryPage channelProductCategories(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("productId") Long productId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_categoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			categoryResource -> new CategoryPage(
				categoryResource.getChannelProductCategoriesPage(
					channelId, productId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {channelProducts(accountId: ___, channelId: ___, filter: ___, page: ___, pageSize: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves products from selected channel.")
	public ProductPage channelProducts(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource -> new ProductPage(
				productResource.getChannelProductsPage(
					channelId, accountId,
					_filterBiFunction.apply(productResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(productResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {channelProduct(accountId: ___, channelId: ___, productId: ___){attachments, categories, createDate, description, expando, id, images, metaDescription, metaKeyword, metaTitle, modifiedDate, multipleOrderQuantity, name, productConfiguration, productId, productOptions, productSpecifications, productType, relatedProducts, shortDescription, skus, slug, tags, urlImage}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves products from selected channel.")
	public Product channelProduct(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("productId") Long productId,
			@GraphQLName("accountId") Long accountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource -> productResource.getChannelProduct(
				channelId, productId, accountId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {channelProductOptions(channelId: ___, page: ___, pageSize: ___, productId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductOptionPage channelProductOptions(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("productId") Long productId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_productOptionResourceComponentServiceObjects,
			this::_populateResourceContext,
			productOptionResource -> new ProductOptionPage(
				productOptionResource.getChannelProductOptionsPage(
					channelId, productId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {channelProductProductSpecifications(channelId: ___, page: ___, pageSize: ___, productId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ProductSpecificationPage channelProductProductSpecifications(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("productId") Long productId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_productSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productSpecificationResource -> new ProductSpecificationPage(
				productSpecificationResource.
					getChannelProductProductSpecificationsPage(
						channelId, productId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {channelProductRelatedProducts(channelId: ___, page: ___, pageSize: ___, productId: ___, type: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Gets a list of Related Products of a Product.")
	public RelatedProductPage channelProductRelatedProducts(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("productId") Long productId,
			@GraphQLName("type") String type,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_relatedProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			relatedProductResource -> new RelatedProductPage(
				relatedProductResource.getChannelProductRelatedProductsPage(
					channelId, productId, type,
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {channelProductSkus(accountId: ___, channelId: ___, page: ___, pageSize: ___, productId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves products from selected channel.")
	public SkuPage channelProductSkus(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("productId") Long productId,
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> new SkuPage(
				skuResource.getChannelProductSkusPage(
					channelId, productId, accountId,
					Pagination.of(page, pageSize))));
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

	private static ComponentServiceObjects<AttachmentResource>
		_attachmentResourceComponentServiceObjects;
	private static ComponentServiceObjects<CategoryResource>
		_categoryResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductResource>
		_productResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductOptionResource>
		_productOptionResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductSpecificationResource>
		_productSpecificationResourceComponentServiceObjects;
	private static ComponentServiceObjects<RelatedProductResource>
		_relatedProductResourceComponentServiceObjects;
	private static ComponentServiceObjects<SkuResource>
		_skuResourceComponentServiceObjects;

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