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

package com.liferay.headless.commerce.admin.catalog.internal.graphql.mutation.v1_0;

import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Attachment;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.AttachmentBase64;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.AttachmentUrl;
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
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class Mutation {

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

	@GraphQLField
	public Attachment createProductByExternalReferenceCodeAttachment(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("attachment") Attachment attachment)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource ->
				attachmentResource.postProductByExternalReferenceCodeAttachment(
					externalReferenceCode, attachment));
	}

	@GraphQLField
	public Attachment createProductByExternalReferenceCodeAttachmentByBase64(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("attachmentBase64") AttachmentBase64 attachmentBase64)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource ->
				attachmentResource.
					postProductByExternalReferenceCodeAttachmentByBase64(
						externalReferenceCode, attachmentBase64));
	}

	@GraphQLField
	public Attachment createProductByExternalReferenceCodeAttachmentByUrl(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("attachmentUrl") AttachmentUrl attachmentUrl)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource ->
				attachmentResource.
					postProductByExternalReferenceCodeAttachmentByUrl(
						externalReferenceCode, attachmentUrl));
	}

	@GraphQLField
	public Attachment createProductByExternalReferenceCodeImage(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("attachment") Attachment attachment)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource ->
				attachmentResource.postProductByExternalReferenceCodeImage(
					externalReferenceCode, attachment));
	}

	@GraphQLField
	public Attachment createProductByExternalReferenceCodeImageByBase64(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("attachmentBase64") AttachmentBase64 attachmentBase64)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource ->
				attachmentResource.
					postProductByExternalReferenceCodeImageByBase64(
						externalReferenceCode, attachmentBase64));
	}

	@GraphQLField
	public Attachment createProductByExternalReferenceCodeImageByUrl(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("attachmentUrl") AttachmentUrl attachmentUrl)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource ->
				attachmentResource.postProductByExternalReferenceCodeImageByUrl(
					externalReferenceCode, attachmentUrl));
	}

	@GraphQLField
	public Attachment createProductIdAttachment(
			@GraphQLName("id") Long id,
			@GraphQLName("attachment") Attachment attachment)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource -> attachmentResource.postProductIdAttachment(
				id, attachment));
	}

	@GraphQLField
	public Response createProductIdAttachmentBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource ->
				attachmentResource.postProductIdAttachmentBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public Attachment createProductIdAttachmentByBase64(
			@GraphQLName("id") Long id,
			@GraphQLName("attachmentBase64") AttachmentBase64 attachmentBase64)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource ->
				attachmentResource.postProductIdAttachmentByBase64(
					id, attachmentBase64));
	}

	@GraphQLField
	public Attachment createProductIdAttachmentByUrl(
			@GraphQLName("id") Long id,
			@GraphQLName("attachmentUrl") AttachmentUrl attachmentUrl)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource ->
				attachmentResource.postProductIdAttachmentByUrl(
					id, attachmentUrl));
	}

	@GraphQLField
	public Attachment createProductIdImage(
			@GraphQLName("id") Long id,
			@GraphQLName("attachment") Attachment attachment)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource -> attachmentResource.postProductIdImage(
				id, attachment));
	}

	@GraphQLField
	public Attachment createProductIdImageByBase64(
			@GraphQLName("id") Long id,
			@GraphQLName("attachmentBase64") AttachmentBase64 attachmentBase64)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource -> attachmentResource.postProductIdImageByBase64(
				id, attachmentBase64));
	}

	@GraphQLField
	public Attachment createProductIdImageByUrl(
			@GraphQLName("id") Long id,
			@GraphQLName("attachmentUrl") AttachmentUrl attachmentUrl)
		throws Exception {

		return _applyComponentServiceObjects(
			_attachmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			attachmentResource -> attachmentResource.postProductIdImageByUrl(
				id, attachmentUrl));
	}

	@GraphQLField
	public Response deleteCatalogByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_catalogResourceComponentServiceObjects,
			this::_populateResourceContext,
			catalogResource ->
				catalogResource.deleteCatalogByExternalReferenceCode(
					externalReferenceCode));
	}

	@GraphQLField
	public Response patchCatalogByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("catalog") Catalog catalog)
		throws Exception {

		return _applyComponentServiceObjects(
			_catalogResourceComponentServiceObjects,
			this::_populateResourceContext,
			catalogResource ->
				catalogResource.patchCatalogByExternalReferenceCode(
					externalReferenceCode, catalog));
	}

	@GraphQLField
	public Response deleteCatalog(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_catalogResourceComponentServiceObjects,
			this::_populateResourceContext,
			catalogResource -> catalogResource.deleteCatalog(id));
	}

	@GraphQLField
	public Response deleteCatalogBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_catalogResourceComponentServiceObjects,
			this::_populateResourceContext,
			catalogResource -> catalogResource.deleteCatalogBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response patchCatalog(
			@GraphQLName("id") Long id, @GraphQLName("catalog") Catalog catalog)
		throws Exception {

		return _applyComponentServiceObjects(
			_catalogResourceComponentServiceObjects,
			this::_populateResourceContext,
			catalogResource -> catalogResource.patchCatalog(id, catalog));
	}

	@GraphQLField
	public Catalog createCatalog(@GraphQLName("catalog") Catalog catalog)
		throws Exception {

		return _applyComponentServiceObjects(
			_catalogResourceComponentServiceObjects,
			this::_populateResourceContext,
			catalogResource -> catalogResource.postCatalog(catalog));
	}

	@GraphQLField
	public Response createCatalogBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_catalogResourceComponentServiceObjects,
			this::_populateResourceContext,
			catalogResource -> catalogResource.postCatalogBatch(
				callbackURL, object));
	}

	@GraphQLField
	public Response patchProductByExternalReferenceCodeCategory(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("categories") Category[] categories)
		throws Exception {

		return _applyComponentServiceObjects(
			_categoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			categoryResource ->
				categoryResource.patchProductByExternalReferenceCodeCategory(
					externalReferenceCode, categories));
	}

	@GraphQLField
	public Response patchProductIdCategory(
			@GraphQLName("id") Long id,
			@GraphQLName("categories") Category[] categories)
		throws Exception {

		return _applyComponentServiceObjects(
			_categoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			categoryResource -> categoryResource.patchProductIdCategory(
				id, categories));
	}

	@GraphQLField
	public Option createOption(@GraphQLName("option") Option option)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionResource -> optionResource.postOption(option));
	}

	@GraphQLField
	public Response createOptionBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionResource -> optionResource.postOptionBatch(
				callbackURL, object));
	}

	@GraphQLField
	public Response deleteOptionByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionResource ->
				optionResource.deleteOptionByExternalReferenceCode(
					externalReferenceCode));
	}

	@GraphQLField
	public Response patchOptionByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("option") Option option)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionResource -> optionResource.patchOptionByExternalReferenceCode(
				externalReferenceCode, option));
	}

	@GraphQLField
	public Response deleteOption(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_optionResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionResource -> optionResource.deleteOption(id));
	}

	@GraphQLField
	public Response deleteOptionBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionResource -> optionResource.deleteOptionBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response patchOption(
			@GraphQLName("id") Long id, @GraphQLName("option") Option option)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionResource -> optionResource.patchOption(id, option));
	}

	@GraphQLField
	public OptionCategory createOptionCategory(
			@GraphQLName("optionCategory") OptionCategory optionCategory)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionCategoryResource -> optionCategoryResource.postOptionCategory(
				optionCategory));
	}

	@GraphQLField
	public Response createOptionCategoryBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionCategoryResource ->
				optionCategoryResource.postOptionCategoryBatch(
					callbackURL, object));
	}

	@GraphQLField
	public Response deleteOptionCategory(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionCategoryResource ->
				optionCategoryResource.deleteOptionCategory(id));
	}

	@GraphQLField
	public Response deleteOptionCategoryBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionCategoryResource ->
				optionCategoryResource.deleteOptionCategoryBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public Response patchOptionCategory(
			@GraphQLName("id") Long id,
			@GraphQLName("optionCategory") OptionCategory optionCategory)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionCategoryResource ->
				optionCategoryResource.patchOptionCategory(id, optionCategory));
	}

	@GraphQLField
	public Response deleteOptionValueByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionValueResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionValueResource ->
				optionValueResource.deleteOptionValueByExternalReferenceCode(
					externalReferenceCode));
	}

	@GraphQLField
	public Response patchOptionValueByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("optionValue") OptionValue optionValue)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionValueResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionValueResource ->
				optionValueResource.patchOptionValueByExternalReferenceCode(
					externalReferenceCode, optionValue));
	}

	@GraphQLField
	public Response deleteOptionValue(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionValueResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionValueResource -> optionValueResource.deleteOptionValue(id));
	}

	@GraphQLField
	public Response deleteOptionValueBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionValueResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionValueResource -> optionValueResource.deleteOptionValueBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response patchOptionValue(
			@GraphQLName("id") Long id,
			@GraphQLName("optionValue") OptionValue optionValue)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionValueResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionValueResource -> optionValueResource.patchOptionValue(
				id, optionValue));
	}

	@GraphQLField
	public OptionValue createOptionByExternalReferenceCodeOptionValue(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("optionValue") OptionValue optionValue)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionValueResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionValueResource ->
				optionValueResource.
					postOptionByExternalReferenceCodeOptionValue(
						externalReferenceCode, optionValue));
	}

	@GraphQLField
	public OptionValue createOptionIdOptionValue(
			@GraphQLName("id") Long id,
			@GraphQLName("optionValue") OptionValue optionValue)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionValueResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionValueResource -> optionValueResource.postOptionIdOptionValue(
				id, optionValue));
	}

	@GraphQLField
	public Response createOptionIdOptionValueBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_optionValueResourceComponentServiceObjects,
			this::_populateResourceContext,
			optionValueResource ->
				optionValueResource.postOptionIdOptionValueBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public Product createProduct(@GraphQLName("product") Product product)
		throws Exception {

		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource -> productResource.postProduct(product));
	}

	@GraphQLField
	public Response createProductBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource -> productResource.postProductBatch(
				callbackURL, object));
	}

	@GraphQLField
	public Response deleteProductByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource ->
				productResource.deleteProductByExternalReferenceCode(
					externalReferenceCode));
	}

	@GraphQLField
	public Response patchProductByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("product") Product product)
		throws Exception {

		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource ->
				productResource.patchProductByExternalReferenceCode(
					externalReferenceCode, product));
	}

	@GraphQLField
	public Product createProductByExternalReferenceCodeClone(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("catalogExternalReferenceCode") String
				catalogExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource ->
				productResource.postProductByExternalReferenceCodeClone(
					externalReferenceCode, catalogExternalReferenceCode));
	}

	@GraphQLField
	public Response deleteProduct(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource -> productResource.deleteProduct(id));
	}

	@GraphQLField
	public Response deleteProductBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource -> productResource.deleteProductBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response patchProduct(
			@GraphQLName("id") Long id, @GraphQLName("product") Product product)
		throws Exception {

		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource -> productResource.patchProduct(id, product));
	}

	@GraphQLField
	public Product createProductClone(
			@GraphQLName("id") Long id,
			@GraphQLName("catalogId") Long catalogId)
		throws Exception {

		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource -> productResource.postProductClone(id, catalogId));
	}

	@GraphQLField
	public Response patchProductByExternalReferenceCodeConfiguration(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("productConfiguration") ProductConfiguration
				productConfiguration)
		throws Exception {

		return _applyComponentServiceObjects(
			_productConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productConfigurationResource ->
				productConfigurationResource.
					patchProductByExternalReferenceCodeConfiguration(
						externalReferenceCode, productConfiguration));
	}

	@GraphQLField
	public Response patchProductIdConfiguration(
			@GraphQLName("id") Long id,
			@GraphQLName("productConfiguration") ProductConfiguration
				productConfiguration)
		throws Exception {

		return _applyComponentServiceObjects(
			_productConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productConfigurationResource ->
				productConfigurationResource.patchProductIdConfiguration(
					id, productConfiguration));
	}

	@GraphQLField
	public ProductGroup createProductGroup(
			@GraphQLName("productGroup") ProductGroup productGroup)
		throws Exception {

		return _applyComponentServiceObjects(
			_productGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupResource -> productGroupResource.postProductGroup(
				productGroup));
	}

	@GraphQLField
	public Response createProductGroupBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_productGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupResource -> productGroupResource.postProductGroupBatch(
				callbackURL, object));
	}

	@GraphQLField
	public boolean deleteProductGroupByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_productGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupResource ->
				productGroupResource.deleteProductGroupByExternalReferenceCode(
					externalReferenceCode));

		return true;
	}

	@GraphQLField
	public Response patchProductGroupByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("productGroup") ProductGroup productGroup)
		throws Exception {

		return _applyComponentServiceObjects(
			_productGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupResource ->
				productGroupResource.patchProductGroupByExternalReferenceCode(
					externalReferenceCode, productGroup));
	}

	@GraphQLField
	public boolean deleteProductGroup(@GraphQLName("id") Long id)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_productGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupResource -> productGroupResource.deleteProductGroup(
				id));

		return true;
	}

	@GraphQLField
	public Response deleteProductGroupBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_productGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupResource ->
				productGroupResource.deleteProductGroupBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public Response patchProductGroup(
			@GraphQLName("id") Long id,
			@GraphQLName("productGroup") ProductGroup productGroup)
		throws Exception {

		return _applyComponentServiceObjects(
			_productGroupResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupResource -> productGroupResource.patchProductGroup(
				id, productGroup));
	}

	@GraphQLField
	public boolean deleteProductGroupProduct(@GraphQLName("id") Long id)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_productGroupProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupProductResource ->
				productGroupProductResource.deleteProductGroupProduct(id));

		return true;
	}

	@GraphQLField
	public Response deleteProductGroupProductBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_productGroupProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupProductResource ->
				productGroupProductResource.deleteProductGroupProductBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public ProductGroupProduct
			createProductGroupByExternalReferenceCodeProductGroupProduct(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("productGroupProduct") ProductGroupProduct
					productGroupProduct)
		throws Exception {

		return _applyComponentServiceObjects(
			_productGroupProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupProductResource ->
				productGroupProductResource.
					postProductGroupByExternalReferenceCodeProductGroupProduct(
						externalReferenceCode, productGroupProduct));
	}

	@GraphQLField
	public ProductGroupProduct createProductGroupIdProductGroupProduct(
			@GraphQLName("id") Long id,
			@GraphQLName("productGroupProduct") ProductGroupProduct
				productGroupProduct)
		throws Exception {

		return _applyComponentServiceObjects(
			_productGroupProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupProductResource ->
				productGroupProductResource.
					postProductGroupIdProductGroupProduct(
						id, productGroupProduct));
	}

	@GraphQLField
	public Response createProductGroupIdProductGroupProductBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_productGroupProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			productGroupProductResource ->
				productGroupProductResource.
					postProductGroupIdProductGroupProductBatch(
						id, callbackURL, object));
	}

	@GraphQLField
	public Response deleteProductOption(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_productOptionResourceComponentServiceObjects,
			this::_populateResourceContext,
			productOptionResource -> productOptionResource.deleteProductOption(
				id));
	}

	@GraphQLField
	public Response deleteProductOptionBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_productOptionResourceComponentServiceObjects,
			this::_populateResourceContext,
			productOptionResource ->
				productOptionResource.deleteProductOptionBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public Response patchProductOption(
			@GraphQLName("id") Long id,
			@GraphQLName("productOption") ProductOption productOption)
		throws Exception {

		return _applyComponentServiceObjects(
			_productOptionResourceComponentServiceObjects,
			this::_populateResourceContext,
			productOptionResource -> productOptionResource.patchProductOption(
				id, productOption));
	}

	@GraphQLField
	public java.util.Collection<ProductOption>
			createProductByExternalReferenceCodeProductOptionsPage(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("productOptions") ProductOption[] productOptions)
		throws Exception {

		return _applyComponentServiceObjects(
			_productOptionResourceComponentServiceObjects,
			this::_populateResourceContext,
			productOptionResource -> {
				Page paginationPage =
					productOptionResource.
						postProductByExternalReferenceCodeProductOptionsPage(
							externalReferenceCode, productOptions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public java.util.Collection<ProductOption>
			createProductIdProductOptionsPage(
				@GraphQLName("id") Long id,
				@GraphQLName("productOptions") ProductOption[] productOptions)
		throws Exception {

		return _applyComponentServiceObjects(
			_productOptionResourceComponentServiceObjects,
			this::_populateResourceContext,
			productOptionResource -> {
				Page paginationPage =
					productOptionResource.postProductIdProductOptionsPage(
						id, productOptions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	public ProductOptionValue createProductOptionIdProductOptionValue(
			@GraphQLName("id") Long id,
			@GraphQLName("productOptionValue") ProductOptionValue
				productOptionValue)
		throws Exception {

		return _applyComponentServiceObjects(
			_productOptionValueResourceComponentServiceObjects,
			this::_populateResourceContext,
			productOptionValueResource ->
				productOptionValueResource.
					postProductOptionIdProductOptionValue(
						id, productOptionValue));
	}

	@GraphQLField
	public Response createProductOptionIdProductOptionValueBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_productOptionValueResourceComponentServiceObjects,
			this::_populateResourceContext,
			productOptionValueResource ->
				productOptionValueResource.
					postProductOptionIdProductOptionValueBatch(
						id, callbackURL, object));
	}

	@GraphQLField
	public Response patchProductByExternalReferenceCodeShippingConfiguration(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("productShippingConfiguration")
				ProductShippingConfiguration productShippingConfiguration)
		throws Exception {

		return _applyComponentServiceObjects(
			_productShippingConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productShippingConfigurationResource ->
				productShippingConfigurationResource.
					patchProductByExternalReferenceCodeShippingConfiguration(
						externalReferenceCode, productShippingConfiguration));
	}

	@GraphQLField
	public Response patchProductIdShippingConfiguration(
			@GraphQLName("id") Long id,
			@GraphQLName("productShippingConfiguration")
				ProductShippingConfiguration productShippingConfiguration)
		throws Exception {

		return _applyComponentServiceObjects(
			_productShippingConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productShippingConfigurationResource ->
				productShippingConfigurationResource.
					patchProductIdShippingConfiguration(
						id, productShippingConfiguration));
	}

	@GraphQLField
	public ProductSpecification createProductIdProductSpecification(
			@GraphQLName("id") Long id,
			@GraphQLName("productSpecification") ProductSpecification
				productSpecification)
		throws Exception {

		return _applyComponentServiceObjects(
			_productSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productSpecificationResource ->
				productSpecificationResource.postProductIdProductSpecification(
					id, productSpecification));
	}

	@GraphQLField
	public Response createProductIdProductSpecificationBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_productSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productSpecificationResource ->
				productSpecificationResource.
					postProductIdProductSpecificationBatch(
						id, callbackURL, object));
	}

	@GraphQLField
	public Response
			patchProductByExternalReferenceCodeSubscriptionConfiguration(
				@GraphQLName("externalReferenceCode") String
					externalReferenceCode,
				@GraphQLName("productSubscriptionConfiguration")
					ProductSubscriptionConfiguration
						productSubscriptionConfiguration)
		throws Exception {

		return _applyComponentServiceObjects(
			_productSubscriptionConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productSubscriptionConfigurationResource ->
				productSubscriptionConfigurationResource.
					patchProductByExternalReferenceCodeSubscriptionConfiguration(
						externalReferenceCode,
						productSubscriptionConfiguration));
	}

	@GraphQLField
	public Response patchProductIdSubscriptionConfiguration(
			@GraphQLName("id") Long id,
			@GraphQLName("productSubscriptionConfiguration")
				ProductSubscriptionConfiguration
					productSubscriptionConfiguration)
		throws Exception {

		return _applyComponentServiceObjects(
			_productSubscriptionConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productSubscriptionConfigurationResource ->
				productSubscriptionConfigurationResource.
					patchProductIdSubscriptionConfiguration(
						id, productSubscriptionConfiguration));
	}

	@GraphQLField
	public Response patchProductByExternalReferenceCodeTaxConfiguration(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("productTaxConfiguration") ProductTaxConfiguration
				productTaxConfiguration)
		throws Exception {

		return _applyComponentServiceObjects(
			_productTaxConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productTaxConfigurationResource ->
				productTaxConfigurationResource.
					patchProductByExternalReferenceCodeTaxConfiguration(
						externalReferenceCode, productTaxConfiguration));
	}

	@GraphQLField
	public Response patchProductIdTaxConfiguration(
			@GraphQLName("id") Long id,
			@GraphQLName("productTaxConfiguration") ProductTaxConfiguration
				productTaxConfiguration)
		throws Exception {

		return _applyComponentServiceObjects(
			_productTaxConfigurationResourceComponentServiceObjects,
			this::_populateResourceContext,
			productTaxConfigurationResource ->
				productTaxConfigurationResource.patchProductIdTaxConfiguration(
					id, productTaxConfiguration));
	}

	@GraphQLField
	public RelatedProduct createProductByExternalReferenceCodeRelatedProduct(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("relatedProduct") RelatedProduct relatedProduct)
		throws Exception {

		return _applyComponentServiceObjects(
			_relatedProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			relatedProductResource ->
				relatedProductResource.
					postProductByExternalReferenceCodeRelatedProduct(
						externalReferenceCode, relatedProduct));
	}

	@GraphQLField
	public RelatedProduct createProductIdRelatedProduct(
			@GraphQLName("id") Long id,
			@GraphQLName("relatedProduct") RelatedProduct relatedProduct)
		throws Exception {

		return _applyComponentServiceObjects(
			_relatedProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			relatedProductResource ->
				relatedProductResource.postProductIdRelatedProduct(
					id, relatedProduct));
	}

	@GraphQLField
	public Response createProductIdRelatedProductBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_relatedProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			relatedProductResource ->
				relatedProductResource.postProductIdRelatedProductBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public Response deleteRelatedProduct(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_relatedProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			relatedProductResource ->
				relatedProductResource.deleteRelatedProduct(id));
	}

	@GraphQLField
	public Response deleteRelatedProductBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_relatedProductResourceComponentServiceObjects,
			this::_populateResourceContext,
			relatedProductResource ->
				relatedProductResource.deleteRelatedProductBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public Sku createProductByExternalReferenceCodeSku(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("sku") Sku sku)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> skuResource.postProductByExternalReferenceCodeSku(
				externalReferenceCode, sku));
	}

	@GraphQLField
	public Sku createProductIdSku(
			@GraphQLName("id") Long id, @GraphQLName("sku") Sku sku)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> skuResource.postProductIdSku(id, sku));
	}

	@GraphQLField
	public Response createProductIdSkuBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> skuResource.postProductIdSkuBatch(
				id, callbackURL, object));
	}

	@GraphQLField
	public Response deleteSkuByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> skuResource.deleteSkuByExternalReferenceCode(
				externalReferenceCode));
	}

	@GraphQLField
	public Response patchSkuByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("sku") Sku sku)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> skuResource.patchSkuByExternalReferenceCode(
				externalReferenceCode, sku));
	}

	@GraphQLField
	public Response deleteSku(@GraphQLName("id") Long id) throws Exception {
		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> skuResource.deleteSku(id));
	}

	@GraphQLField
	public Response deleteSkuBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> skuResource.deleteSkuBatch(id, callbackURL, object));
	}

	@GraphQLField
	public Response patchSku(
			@GraphQLName("id") Long id, @GraphQLName("sku") Sku sku)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> skuResource.patchSku(id, sku));
	}

	@GraphQLField
	public Specification createSpecification(
			@GraphQLName("specification") Specification specification)
		throws Exception {

		return _applyComponentServiceObjects(
			_specificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			specificationResource -> specificationResource.postSpecification(
				specification));
	}

	@GraphQLField
	public Response createSpecificationBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_specificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			specificationResource ->
				specificationResource.postSpecificationBatch(
					callbackURL, object));
	}

	@GraphQLField
	public Response deleteSpecification(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_specificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			specificationResource -> specificationResource.deleteSpecification(
				id));
	}

	@GraphQLField
	public Response deleteSpecificationBatch(
			@GraphQLName("id") Long id,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_specificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			specificationResource ->
				specificationResource.deleteSpecificationBatch(
					id, callbackURL, object));
	}

	@GraphQLField
	public Response patchSpecification(
			@GraphQLName("id") Long id,
			@GraphQLName("specification") Specification specification)
		throws Exception {

		return _applyComponentServiceObjects(
			_specificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			specificationResource -> specificationResource.patchSpecification(
				id, specification));
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

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
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
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}