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

package com.liferay.headless.commerce.admin.catalog.internal.graphql.servlet.v1_0;

import com.liferay.headless.commerce.admin.catalog.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.commerce.admin.catalog.internal.graphql.query.v1_0.Query;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.AttachmentResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.CatalogResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.CategoryResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.DiagramResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.MappedProductResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.OptionCategoryResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.OptionResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.OptionValueResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.PinResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.ProductAccountGroupResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.ProductChannelResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.ProductConfigurationResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.ProductGroupProductResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.ProductGroupResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.ProductOptionResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.ProductOptionValueResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.ProductResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.ProductShippingConfigurationResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.ProductSpecificationResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.ProductSubscriptionConfigurationResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.ProductTaxConfigurationResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.RelatedProductResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.SkuResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.SpecificationResourceImpl;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.AttachmentResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.CatalogResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.CategoryResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.DiagramResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.MappedProductResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.OptionCategoryResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.OptionResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.OptionValueResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.PinResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductAccountGroupResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductChannelResource;
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
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Zoltán Takács
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setAttachmentResourceComponentServiceObjects(
			_attachmentResourceComponentServiceObjects);
		Mutation.setCatalogResourceComponentServiceObjects(
			_catalogResourceComponentServiceObjects);
		Mutation.setCategoryResourceComponentServiceObjects(
			_categoryResourceComponentServiceObjects);
		Mutation.setDiagramResourceComponentServiceObjects(
			_diagramResourceComponentServiceObjects);
		Mutation.setMappedProductResourceComponentServiceObjects(
			_mappedProductResourceComponentServiceObjects);
		Mutation.setOptionResourceComponentServiceObjects(
			_optionResourceComponentServiceObjects);
		Mutation.setOptionCategoryResourceComponentServiceObjects(
			_optionCategoryResourceComponentServiceObjects);
		Mutation.setOptionValueResourceComponentServiceObjects(
			_optionValueResourceComponentServiceObjects);
		Mutation.setPinResourceComponentServiceObjects(
			_pinResourceComponentServiceObjects);
		Mutation.setProductResourceComponentServiceObjects(
			_productResourceComponentServiceObjects);
		Mutation.setProductAccountGroupResourceComponentServiceObjects(
			_productAccountGroupResourceComponentServiceObjects);
		Mutation.setProductChannelResourceComponentServiceObjects(
			_productChannelResourceComponentServiceObjects);
		Mutation.setProductConfigurationResourceComponentServiceObjects(
			_productConfigurationResourceComponentServiceObjects);
		Mutation.setProductGroupResourceComponentServiceObjects(
			_productGroupResourceComponentServiceObjects);
		Mutation.setProductGroupProductResourceComponentServiceObjects(
			_productGroupProductResourceComponentServiceObjects);
		Mutation.setProductOptionResourceComponentServiceObjects(
			_productOptionResourceComponentServiceObjects);
		Mutation.setProductOptionValueResourceComponentServiceObjects(
			_productOptionValueResourceComponentServiceObjects);
		Mutation.setProductShippingConfigurationResourceComponentServiceObjects(
			_productShippingConfigurationResourceComponentServiceObjects);
		Mutation.setProductSpecificationResourceComponentServiceObjects(
			_productSpecificationResourceComponentServiceObjects);
		Mutation.
			setProductSubscriptionConfigurationResourceComponentServiceObjects(
				_productSubscriptionConfigurationResourceComponentServiceObjects);
		Mutation.setProductTaxConfigurationResourceComponentServiceObjects(
			_productTaxConfigurationResourceComponentServiceObjects);
		Mutation.setRelatedProductResourceComponentServiceObjects(
			_relatedProductResourceComponentServiceObjects);
		Mutation.setSkuResourceComponentServiceObjects(
			_skuResourceComponentServiceObjects);
		Mutation.setSpecificationResourceComponentServiceObjects(
			_specificationResourceComponentServiceObjects);

		Query.setAttachmentResourceComponentServiceObjects(
			_attachmentResourceComponentServiceObjects);
		Query.setCatalogResourceComponentServiceObjects(
			_catalogResourceComponentServiceObjects);
		Query.setCategoryResourceComponentServiceObjects(
			_categoryResourceComponentServiceObjects);
		Query.setDiagramResourceComponentServiceObjects(
			_diagramResourceComponentServiceObjects);
		Query.setMappedProductResourceComponentServiceObjects(
			_mappedProductResourceComponentServiceObjects);
		Query.setOptionResourceComponentServiceObjects(
			_optionResourceComponentServiceObjects);
		Query.setOptionCategoryResourceComponentServiceObjects(
			_optionCategoryResourceComponentServiceObjects);
		Query.setOptionValueResourceComponentServiceObjects(
			_optionValueResourceComponentServiceObjects);
		Query.setPinResourceComponentServiceObjects(
			_pinResourceComponentServiceObjects);
		Query.setProductResourceComponentServiceObjects(
			_productResourceComponentServiceObjects);
		Query.setProductAccountGroupResourceComponentServiceObjects(
			_productAccountGroupResourceComponentServiceObjects);
		Query.setProductChannelResourceComponentServiceObjects(
			_productChannelResourceComponentServiceObjects);
		Query.setProductConfigurationResourceComponentServiceObjects(
			_productConfigurationResourceComponentServiceObjects);
		Query.setProductGroupResourceComponentServiceObjects(
			_productGroupResourceComponentServiceObjects);
		Query.setProductGroupProductResourceComponentServiceObjects(
			_productGroupProductResourceComponentServiceObjects);
		Query.setProductOptionResourceComponentServiceObjects(
			_productOptionResourceComponentServiceObjects);
		Query.setProductOptionValueResourceComponentServiceObjects(
			_productOptionValueResourceComponentServiceObjects);
		Query.setProductShippingConfigurationResourceComponentServiceObjects(
			_productShippingConfigurationResourceComponentServiceObjects);
		Query.setProductSpecificationResourceComponentServiceObjects(
			_productSpecificationResourceComponentServiceObjects);
		Query.
			setProductSubscriptionConfigurationResourceComponentServiceObjects(
				_productSubscriptionConfigurationResourceComponentServiceObjects);
		Query.setProductTaxConfigurationResourceComponentServiceObjects(
			_productTaxConfigurationResourceComponentServiceObjects);
		Query.setRelatedProductResourceComponentServiceObjects(
			_relatedProductResourceComponentServiceObjects);
		Query.setSkuResourceComponentServiceObjects(
			_skuResourceComponentServiceObjects);
		Query.setSpecificationResourceComponentServiceObjects(
			_specificationResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Commerce.Admin.Catalog";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-commerce-admin-catalog-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodPair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodPairs.get("mutation#" + methodName);
		}

		return _resourceMethodPairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodPairs = new HashMap<>();

	static {
		_resourceMethodPairs.put(
			"mutation#createProductByExternalReferenceCodeAttachment",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class,
				"postProductByExternalReferenceCodeAttachment"));
		_resourceMethodPairs.put(
			"mutation#createProductByExternalReferenceCodeAttachmentByBase64",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class,
				"postProductByExternalReferenceCodeAttachmentByBase64"));
		_resourceMethodPairs.put(
			"mutation#createProductByExternalReferenceCodeAttachmentByUrl",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class,
				"postProductByExternalReferenceCodeAttachmentByUrl"));
		_resourceMethodPairs.put(
			"mutation#createProductByExternalReferenceCodeImage",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class,
				"postProductByExternalReferenceCodeImage"));
		_resourceMethodPairs.put(
			"mutation#createProductByExternalReferenceCodeImageByBase64",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class,
				"postProductByExternalReferenceCodeImageByBase64"));
		_resourceMethodPairs.put(
			"mutation#createProductByExternalReferenceCodeImageByUrl",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class,
				"postProductByExternalReferenceCodeImageByUrl"));
		_resourceMethodPairs.put(
			"mutation#createProductIdAttachment",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class, "postProductIdAttachment"));
		_resourceMethodPairs.put(
			"mutation#createProductIdAttachmentBatch",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class, "postProductIdAttachmentBatch"));
		_resourceMethodPairs.put(
			"mutation#createProductIdAttachmentByBase64",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class,
				"postProductIdAttachmentByBase64"));
		_resourceMethodPairs.put(
			"mutation#createProductIdAttachmentByUrl",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class, "postProductIdAttachmentByUrl"));
		_resourceMethodPairs.put(
			"mutation#createProductIdImage",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class, "postProductIdImage"));
		_resourceMethodPairs.put(
			"mutation#createProductIdImageByBase64",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class, "postProductIdImageByBase64"));
		_resourceMethodPairs.put(
			"mutation#createProductIdImageByUrl",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class, "postProductIdImageByUrl"));
		_resourceMethodPairs.put(
			"mutation#deleteCatalogByExternalReferenceCode",
			new ObjectValuePair<>(
				CatalogResourceImpl.class,
				"deleteCatalogByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchCatalogByExternalReferenceCode",
			new ObjectValuePair<>(
				CatalogResourceImpl.class,
				"patchCatalogByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteCatalog",
			new ObjectValuePair<>(CatalogResourceImpl.class, "deleteCatalog"));
		_resourceMethodPairs.put(
			"mutation#deleteCatalogBatch",
			new ObjectValuePair<>(
				CatalogResourceImpl.class, "deleteCatalogBatch"));
		_resourceMethodPairs.put(
			"mutation#patchCatalog",
			new ObjectValuePair<>(CatalogResourceImpl.class, "patchCatalog"));
		_resourceMethodPairs.put(
			"mutation#createCatalog",
			new ObjectValuePair<>(CatalogResourceImpl.class, "postCatalog"));
		_resourceMethodPairs.put(
			"mutation#createCatalogBatch",
			new ObjectValuePair<>(
				CatalogResourceImpl.class, "postCatalogBatch"));
		_resourceMethodPairs.put(
			"mutation#patchProductByExternalReferenceCodeCategory",
			new ObjectValuePair<>(
				CategoryResourceImpl.class,
				"patchProductByExternalReferenceCodeCategory"));
		_resourceMethodPairs.put(
			"mutation#patchProductIdCategory",
			new ObjectValuePair<>(
				CategoryResourceImpl.class, "patchProductIdCategory"));
		_resourceMethodPairs.put(
			"mutation#patchDiagram",
			new ObjectValuePair<>(DiagramResourceImpl.class, "patchDiagram"));
		_resourceMethodPairs.put(
			"mutation#createProductByExternalReferenceCodeDiagram",
			new ObjectValuePair<>(
				DiagramResourceImpl.class,
				"postProductByExternalReferenceCodeDiagram"));
		_resourceMethodPairs.put(
			"mutation#createProductIdDiagram",
			new ObjectValuePair<>(
				DiagramResourceImpl.class, "postProductIdDiagram"));
		_resourceMethodPairs.put(
			"mutation#deleteMappedProduct",
			new ObjectValuePair<>(
				MappedProductResourceImpl.class, "deleteMappedProduct"));
		_resourceMethodPairs.put(
			"mutation#deleteMappedProductBatch",
			new ObjectValuePair<>(
				MappedProductResourceImpl.class, "deleteMappedProductBatch"));
		_resourceMethodPairs.put(
			"mutation#patchMappedProduct",
			new ObjectValuePair<>(
				MappedProductResourceImpl.class, "patchMappedProduct"));
		_resourceMethodPairs.put(
			"mutation#createProductByExternalReferenceCodeMappedProduct",
			new ObjectValuePair<>(
				MappedProductResourceImpl.class,
				"postProductByExternalReferenceCodeMappedProduct"));
		_resourceMethodPairs.put(
			"mutation#createProductIdMappedProduct",
			new ObjectValuePair<>(
				MappedProductResourceImpl.class, "postProductIdMappedProduct"));
		_resourceMethodPairs.put(
			"mutation#createOption",
			new ObjectValuePair<>(OptionResourceImpl.class, "postOption"));
		_resourceMethodPairs.put(
			"mutation#createOptionBatch",
			new ObjectValuePair<>(OptionResourceImpl.class, "postOptionBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteOptionByExternalReferenceCode",
			new ObjectValuePair<>(
				OptionResourceImpl.class,
				"deleteOptionByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchOptionByExternalReferenceCode",
			new ObjectValuePair<>(
				OptionResourceImpl.class,
				"patchOptionByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteOption",
			new ObjectValuePair<>(OptionResourceImpl.class, "deleteOption"));
		_resourceMethodPairs.put(
			"mutation#deleteOptionBatch",
			new ObjectValuePair<>(
				OptionResourceImpl.class, "deleteOptionBatch"));
		_resourceMethodPairs.put(
			"mutation#patchOption",
			new ObjectValuePair<>(OptionResourceImpl.class, "patchOption"));
		_resourceMethodPairs.put(
			"mutation#createOptionCategory",
			new ObjectValuePair<>(
				OptionCategoryResourceImpl.class, "postOptionCategory"));
		_resourceMethodPairs.put(
			"mutation#createOptionCategoryBatch",
			new ObjectValuePair<>(
				OptionCategoryResourceImpl.class, "postOptionCategoryBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteOptionCategory",
			new ObjectValuePair<>(
				OptionCategoryResourceImpl.class, "deleteOptionCategory"));
		_resourceMethodPairs.put(
			"mutation#deleteOptionCategoryBatch",
			new ObjectValuePair<>(
				OptionCategoryResourceImpl.class, "deleteOptionCategoryBatch"));
		_resourceMethodPairs.put(
			"mutation#patchOptionCategory",
			new ObjectValuePair<>(
				OptionCategoryResourceImpl.class, "patchOptionCategory"));
		_resourceMethodPairs.put(
			"mutation#deleteOptionValueByExternalReferenceCode",
			new ObjectValuePair<>(
				OptionValueResourceImpl.class,
				"deleteOptionValueByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchOptionValueByExternalReferenceCode",
			new ObjectValuePair<>(
				OptionValueResourceImpl.class,
				"patchOptionValueByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteOptionValue",
			new ObjectValuePair<>(
				OptionValueResourceImpl.class, "deleteOptionValue"));
		_resourceMethodPairs.put(
			"mutation#deleteOptionValueBatch",
			new ObjectValuePair<>(
				OptionValueResourceImpl.class, "deleteOptionValueBatch"));
		_resourceMethodPairs.put(
			"mutation#patchOptionValue",
			new ObjectValuePair<>(
				OptionValueResourceImpl.class, "patchOptionValue"));
		_resourceMethodPairs.put(
			"mutation#createOptionByExternalReferenceCodeOptionValue",
			new ObjectValuePair<>(
				OptionValueResourceImpl.class,
				"postOptionByExternalReferenceCodeOptionValue"));
		_resourceMethodPairs.put(
			"mutation#createOptionIdOptionValue",
			new ObjectValuePair<>(
				OptionValueResourceImpl.class, "postOptionIdOptionValue"));
		_resourceMethodPairs.put(
			"mutation#createOptionIdOptionValueBatch",
			new ObjectValuePair<>(
				OptionValueResourceImpl.class, "postOptionIdOptionValueBatch"));
		_resourceMethodPairs.put(
			"mutation#deletePin",
			new ObjectValuePair<>(PinResourceImpl.class, "deletePin"));
		_resourceMethodPairs.put(
			"mutation#deletePinBatch",
			new ObjectValuePair<>(PinResourceImpl.class, "deletePinBatch"));
		_resourceMethodPairs.put(
			"mutation#patchPin",
			new ObjectValuePair<>(PinResourceImpl.class, "patchPin"));
		_resourceMethodPairs.put(
			"mutation#createProductByExternalReferenceCodePin",
			new ObjectValuePair<>(
				PinResourceImpl.class,
				"postProductByExternalReferenceCodePin"));
		_resourceMethodPairs.put(
			"mutation#createProductIdPin",
			new ObjectValuePair<>(PinResourceImpl.class, "postProductIdPin"));
		_resourceMethodPairs.put(
			"mutation#createProduct",
			new ObjectValuePair<>(ProductResourceImpl.class, "postProduct"));
		_resourceMethodPairs.put(
			"mutation#createProductBatch",
			new ObjectValuePair<>(
				ProductResourceImpl.class, "postProductBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteProductByExternalReferenceCode",
			new ObjectValuePair<>(
				ProductResourceImpl.class,
				"deleteProductByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchProductByExternalReferenceCode",
			new ObjectValuePair<>(
				ProductResourceImpl.class,
				"patchProductByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteProductByExternalReferenceCodeByVersion",
			new ObjectValuePair<>(
				ProductResourceImpl.class,
				"deleteProductByExternalReferenceCodeByVersion"));
		_resourceMethodPairs.put(
			"mutation#createProductByExternalReferenceCodeClone",
			new ObjectValuePair<>(
				ProductResourceImpl.class,
				"postProductByExternalReferenceCodeClone"));
		_resourceMethodPairs.put(
			"mutation#deleteProduct",
			new ObjectValuePair<>(ProductResourceImpl.class, "deleteProduct"));
		_resourceMethodPairs.put(
			"mutation#deleteProductBatch",
			new ObjectValuePair<>(
				ProductResourceImpl.class, "deleteProductBatch"));
		_resourceMethodPairs.put(
			"mutation#patchProduct",
			new ObjectValuePair<>(ProductResourceImpl.class, "patchProduct"));
		_resourceMethodPairs.put(
			"mutation#deleteProductByVersion",
			new ObjectValuePair<>(
				ProductResourceImpl.class, "deleteProductByVersion"));
		_resourceMethodPairs.put(
			"mutation#createProductClone",
			new ObjectValuePair<>(
				ProductResourceImpl.class, "postProductClone"));
		_resourceMethodPairs.put(
			"mutation#deleteProductAccountGroup",
			new ObjectValuePair<>(
				ProductAccountGroupResourceImpl.class,
				"deleteProductAccountGroup"));
		_resourceMethodPairs.put(
			"mutation#deleteProductAccountGroupBatch",
			new ObjectValuePair<>(
				ProductAccountGroupResourceImpl.class,
				"deleteProductAccountGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteProductChannel",
			new ObjectValuePair<>(
				ProductChannelResourceImpl.class, "deleteProductChannel"));
		_resourceMethodPairs.put(
			"mutation#deleteProductChannelBatch",
			new ObjectValuePair<>(
				ProductChannelResourceImpl.class, "deleteProductChannelBatch"));
		_resourceMethodPairs.put(
			"mutation#patchProductByExternalReferenceCodeConfiguration",
			new ObjectValuePair<>(
				ProductConfigurationResourceImpl.class,
				"patchProductByExternalReferenceCodeConfiguration"));
		_resourceMethodPairs.put(
			"mutation#patchProductIdConfiguration",
			new ObjectValuePair<>(
				ProductConfigurationResourceImpl.class,
				"patchProductIdConfiguration"));
		_resourceMethodPairs.put(
			"mutation#createProductGroup",
			new ObjectValuePair<>(
				ProductGroupResourceImpl.class, "postProductGroup"));
		_resourceMethodPairs.put(
			"mutation#createProductGroupBatch",
			new ObjectValuePair<>(
				ProductGroupResourceImpl.class, "postProductGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteProductGroupByExternalReferenceCode",
			new ObjectValuePair<>(
				ProductGroupResourceImpl.class,
				"deleteProductGroupByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchProductGroupByExternalReferenceCode",
			new ObjectValuePair<>(
				ProductGroupResourceImpl.class,
				"patchProductGroupByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteProductGroup",
			new ObjectValuePair<>(
				ProductGroupResourceImpl.class, "deleteProductGroup"));
		_resourceMethodPairs.put(
			"mutation#deleteProductGroupBatch",
			new ObjectValuePair<>(
				ProductGroupResourceImpl.class, "deleteProductGroupBatch"));
		_resourceMethodPairs.put(
			"mutation#patchProductGroup",
			new ObjectValuePair<>(
				ProductGroupResourceImpl.class, "patchProductGroup"));
		_resourceMethodPairs.put(
			"mutation#deleteProductGroupProduct",
			new ObjectValuePair<>(
				ProductGroupProductResourceImpl.class,
				"deleteProductGroupProduct"));
		_resourceMethodPairs.put(
			"mutation#deleteProductGroupProductBatch",
			new ObjectValuePair<>(
				ProductGroupProductResourceImpl.class,
				"deleteProductGroupProductBatch"));
		_resourceMethodPairs.put(
			"mutation#createProductGroupByExternalReferenceCodeProductGroupProduct",
			new ObjectValuePair<>(
				ProductGroupProductResourceImpl.class,
				"postProductGroupByExternalReferenceCodeProductGroupProduct"));
		_resourceMethodPairs.put(
			"mutation#createProductGroupIdProductGroupProduct",
			new ObjectValuePair<>(
				ProductGroupProductResourceImpl.class,
				"postProductGroupIdProductGroupProduct"));
		_resourceMethodPairs.put(
			"mutation#createProductGroupIdProductGroupProductBatch",
			new ObjectValuePair<>(
				ProductGroupProductResourceImpl.class,
				"postProductGroupIdProductGroupProductBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteProductOption",
			new ObjectValuePair<>(
				ProductOptionResourceImpl.class, "deleteProductOption"));
		_resourceMethodPairs.put(
			"mutation#deleteProductOptionBatch",
			new ObjectValuePair<>(
				ProductOptionResourceImpl.class, "deleteProductOptionBatch"));
		_resourceMethodPairs.put(
			"mutation#patchProductOption",
			new ObjectValuePair<>(
				ProductOptionResourceImpl.class, "patchProductOption"));
		_resourceMethodPairs.put(
			"mutation#createProductByExternalReferenceCodeProductOptionsPage",
			new ObjectValuePair<>(
				ProductOptionResourceImpl.class,
				"postProductByExternalReferenceCodeProductOptionsPage"));
		_resourceMethodPairs.put(
			"mutation#createProductIdProductOptionsPage",
			new ObjectValuePair<>(
				ProductOptionResourceImpl.class,
				"postProductIdProductOptionsPage"));
		_resourceMethodPairs.put(
			"mutation#createProductOptionIdProductOptionValue",
			new ObjectValuePair<>(
				ProductOptionValueResourceImpl.class,
				"postProductOptionIdProductOptionValue"));
		_resourceMethodPairs.put(
			"mutation#createProductOptionIdProductOptionValueBatch",
			new ObjectValuePair<>(
				ProductOptionValueResourceImpl.class,
				"postProductOptionIdProductOptionValueBatch"));
		_resourceMethodPairs.put(
			"mutation#patchProductByExternalReferenceCodeShippingConfiguration",
			new ObjectValuePair<>(
				ProductShippingConfigurationResourceImpl.class,
				"patchProductByExternalReferenceCodeShippingConfiguration"));
		_resourceMethodPairs.put(
			"mutation#patchProductIdShippingConfiguration",
			new ObjectValuePair<>(
				ProductShippingConfigurationResourceImpl.class,
				"patchProductIdShippingConfiguration"));
		_resourceMethodPairs.put(
			"mutation#createProductIdProductSpecification",
			new ObjectValuePair<>(
				ProductSpecificationResourceImpl.class,
				"postProductIdProductSpecification"));
		_resourceMethodPairs.put(
			"mutation#createProductIdProductSpecificationBatch",
			new ObjectValuePair<>(
				ProductSpecificationResourceImpl.class,
				"postProductIdProductSpecificationBatch"));
		_resourceMethodPairs.put(
			"mutation#patchProductByExternalReferenceCodeSubscriptionConfiguration",
			new ObjectValuePair<>(
				ProductSubscriptionConfigurationResourceImpl.class,
				"patchProductByExternalReferenceCodeSubscriptionConfiguration"));
		_resourceMethodPairs.put(
			"mutation#patchProductIdSubscriptionConfiguration",
			new ObjectValuePair<>(
				ProductSubscriptionConfigurationResourceImpl.class,
				"patchProductIdSubscriptionConfiguration"));
		_resourceMethodPairs.put(
			"mutation#patchProductByExternalReferenceCodeTaxConfiguration",
			new ObjectValuePair<>(
				ProductTaxConfigurationResourceImpl.class,
				"patchProductByExternalReferenceCodeTaxConfiguration"));
		_resourceMethodPairs.put(
			"mutation#patchProductIdTaxConfiguration",
			new ObjectValuePair<>(
				ProductTaxConfigurationResourceImpl.class,
				"patchProductIdTaxConfiguration"));
		_resourceMethodPairs.put(
			"mutation#createProductByExternalReferenceCodeRelatedProduct",
			new ObjectValuePair<>(
				RelatedProductResourceImpl.class,
				"postProductByExternalReferenceCodeRelatedProduct"));
		_resourceMethodPairs.put(
			"mutation#createProductIdRelatedProduct",
			new ObjectValuePair<>(
				RelatedProductResourceImpl.class,
				"postProductIdRelatedProduct"));
		_resourceMethodPairs.put(
			"mutation#createProductIdRelatedProductBatch",
			new ObjectValuePair<>(
				RelatedProductResourceImpl.class,
				"postProductIdRelatedProductBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteRelatedProduct",
			new ObjectValuePair<>(
				RelatedProductResourceImpl.class, "deleteRelatedProduct"));
		_resourceMethodPairs.put(
			"mutation#deleteRelatedProductBatch",
			new ObjectValuePair<>(
				RelatedProductResourceImpl.class, "deleteRelatedProductBatch"));
		_resourceMethodPairs.put(
			"mutation#createProductByExternalReferenceCodeSku",
			new ObjectValuePair<>(
				SkuResourceImpl.class,
				"postProductByExternalReferenceCodeSku"));
		_resourceMethodPairs.put(
			"mutation#createProductIdSku",
			new ObjectValuePair<>(SkuResourceImpl.class, "postProductIdSku"));
		_resourceMethodPairs.put(
			"mutation#createProductIdSkuBatch",
			new ObjectValuePair<>(
				SkuResourceImpl.class, "postProductIdSkuBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteSkuByExternalReferenceCode",
			new ObjectValuePair<>(
				SkuResourceImpl.class, "deleteSkuByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchSkuByExternalReferenceCode",
			new ObjectValuePair<>(
				SkuResourceImpl.class, "patchSkuByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteSku",
			new ObjectValuePair<>(SkuResourceImpl.class, "deleteSku"));
		_resourceMethodPairs.put(
			"mutation#deleteSkuBatch",
			new ObjectValuePair<>(SkuResourceImpl.class, "deleteSkuBatch"));
		_resourceMethodPairs.put(
			"mutation#patchSku",
			new ObjectValuePair<>(SkuResourceImpl.class, "patchSku"));
		_resourceMethodPairs.put(
			"mutation#createSpecification",
			new ObjectValuePair<>(
				SpecificationResourceImpl.class, "postSpecification"));
		_resourceMethodPairs.put(
			"mutation#createSpecificationBatch",
			new ObjectValuePair<>(
				SpecificationResourceImpl.class, "postSpecificationBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteSpecification",
			new ObjectValuePair<>(
				SpecificationResourceImpl.class, "deleteSpecification"));
		_resourceMethodPairs.put(
			"mutation#deleteSpecificationBatch",
			new ObjectValuePair<>(
				SpecificationResourceImpl.class, "deleteSpecificationBatch"));
		_resourceMethodPairs.put(
			"mutation#patchSpecification",
			new ObjectValuePair<>(
				SpecificationResourceImpl.class, "patchSpecification"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeAttachments",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class,
				"getProductByExternalReferenceCodeAttachmentsPage"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeImages",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class,
				"getProductByExternalReferenceCodeImagesPage"));
		_resourceMethodPairs.put(
			"query#productIdAttachments",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class, "getProductIdAttachmentsPage"));
		_resourceMethodPairs.put(
			"query#productIdImages",
			new ObjectValuePair<>(
				AttachmentResourceImpl.class, "getProductIdImagesPage"));
		_resourceMethodPairs.put(
			"query#catalogByExternalReferenceCode",
			new ObjectValuePair<>(
				CatalogResourceImpl.class,
				"getCatalogByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#catalog",
			new ObjectValuePair<>(CatalogResourceImpl.class, "getCatalog"));
		_resourceMethodPairs.put(
			"query#catalogs",
			new ObjectValuePair<>(
				CatalogResourceImpl.class, "getCatalogsPage"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeCatalog",
			new ObjectValuePair<>(
				CatalogResourceImpl.class,
				"getProductByExternalReferenceCodeCatalog"));
		_resourceMethodPairs.put(
			"query#productIdCatalog",
			new ObjectValuePair<>(
				CatalogResourceImpl.class, "getProductIdCatalog"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeCategories",
			new ObjectValuePair<>(
				CategoryResourceImpl.class,
				"getProductByExternalReferenceCodeCategoriesPage"));
		_resourceMethodPairs.put(
			"query#productIdCategories",
			new ObjectValuePair<>(
				CategoryResourceImpl.class, "getProductIdCategoriesPage"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeDiagram",
			new ObjectValuePair<>(
				DiagramResourceImpl.class,
				"getProductByExternalReferenceCodeDiagram"));
		_resourceMethodPairs.put(
			"query#productIdDiagram",
			new ObjectValuePair<>(
				DiagramResourceImpl.class, "getProductIdDiagram"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeMappedProducts",
			new ObjectValuePair<>(
				MappedProductResourceImpl.class,
				"getProductByExternalReferenceCodeMappedProductsPage"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeMappedProductBySequence",
			new ObjectValuePair<>(
				MappedProductResourceImpl.class,
				"getProductByExternalReferenceCodeMappedProductBySequence"));
		_resourceMethodPairs.put(
			"query#productIdMappedProducts",
			new ObjectValuePair<>(
				MappedProductResourceImpl.class,
				"getProductIdMappedProductsPage"));
		_resourceMethodPairs.put(
			"query#productMappedProductBySequence",
			new ObjectValuePair<>(
				MappedProductResourceImpl.class,
				"getProductMappedProductBySequence"));
		_resourceMethodPairs.put(
			"query#options",
			new ObjectValuePair<>(OptionResourceImpl.class, "getOptionsPage"));
		_resourceMethodPairs.put(
			"query#optionByExternalReferenceCode",
			new ObjectValuePair<>(
				OptionResourceImpl.class, "getOptionByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#option",
			new ObjectValuePair<>(OptionResourceImpl.class, "getOption"));
		_resourceMethodPairs.put(
			"query#optionCategories",
			new ObjectValuePair<>(
				OptionCategoryResourceImpl.class, "getOptionCategoriesPage"));
		_resourceMethodPairs.put(
			"query#optionCategory",
			new ObjectValuePair<>(
				OptionCategoryResourceImpl.class, "getOptionCategory"));
		_resourceMethodPairs.put(
			"query#optionValueByExternalReferenceCode",
			new ObjectValuePair<>(
				OptionValueResourceImpl.class,
				"getOptionValueByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#optionValue",
			new ObjectValuePair<>(
				OptionValueResourceImpl.class, "getOptionValue"));
		_resourceMethodPairs.put(
			"query#optionByExternalReferenceCodeOptionValues",
			new ObjectValuePair<>(
				OptionValueResourceImpl.class,
				"getOptionByExternalReferenceCodeOptionValuesPage"));
		_resourceMethodPairs.put(
			"query#optionIdOptionValues",
			new ObjectValuePair<>(
				OptionValueResourceImpl.class, "getOptionIdOptionValuesPage"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodePins",
			new ObjectValuePair<>(
				PinResourceImpl.class,
				"getProductByExternalReferenceCodePinsPage"));
		_resourceMethodPairs.put(
			"query#productIdPins",
			new ObjectValuePair<>(
				PinResourceImpl.class, "getProductIdPinsPage"));
		_resourceMethodPairs.put(
			"query#products",
			new ObjectValuePair<>(
				ProductResourceImpl.class, "getProductsPage"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCode",
			new ObjectValuePair<>(
				ProductResourceImpl.class,
				"getProductByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeByVersion",
			new ObjectValuePair<>(
				ProductResourceImpl.class,
				"getProductByExternalReferenceCodeByVersion"));
		_resourceMethodPairs.put(
			"query#product",
			new ObjectValuePair<>(ProductResourceImpl.class, "getProduct"));
		_resourceMethodPairs.put(
			"query#productByVersion",
			new ObjectValuePair<>(
				ProductResourceImpl.class, "getProductByVersion"));
		_resourceMethodPairs.put(
			"query#productAccountGroup",
			new ObjectValuePair<>(
				ProductAccountGroupResourceImpl.class,
				"getProductAccountGroup"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeProductAccountGroups",
			new ObjectValuePair<>(
				ProductAccountGroupResourceImpl.class,
				"getProductByExternalReferenceCodeProductAccountGroupsPage"));
		_resourceMethodPairs.put(
			"query#productIdProductAccountGroups",
			new ObjectValuePair<>(
				ProductAccountGroupResourceImpl.class,
				"getProductIdProductAccountGroupsPage"));
		_resourceMethodPairs.put(
			"query#productChannel",
			new ObjectValuePair<>(
				ProductChannelResourceImpl.class, "getProductChannel"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeProductChannels",
			new ObjectValuePair<>(
				ProductChannelResourceImpl.class,
				"getProductByExternalReferenceCodeProductChannelsPage"));
		_resourceMethodPairs.put(
			"query#productIdProductChannels",
			new ObjectValuePair<>(
				ProductChannelResourceImpl.class,
				"getProductIdProductChannelsPage"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeConfiguration",
			new ObjectValuePair<>(
				ProductConfigurationResourceImpl.class,
				"getProductByExternalReferenceCodeConfiguration"));
		_resourceMethodPairs.put(
			"query#productIdConfiguration",
			new ObjectValuePair<>(
				ProductConfigurationResourceImpl.class,
				"getProductIdConfiguration"));
		_resourceMethodPairs.put(
			"query#productGroups",
			new ObjectValuePair<>(
				ProductGroupResourceImpl.class, "getProductGroupsPage"));
		_resourceMethodPairs.put(
			"query#productGroupByExternalReferenceCode",
			new ObjectValuePair<>(
				ProductGroupResourceImpl.class,
				"getProductGroupByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#productGroup",
			new ObjectValuePair<>(
				ProductGroupResourceImpl.class, "getProductGroup"));
		_resourceMethodPairs.put(
			"query#productGroupByExternalReferenceCodeProductGroupProducts",
			new ObjectValuePair<>(
				ProductGroupProductResourceImpl.class,
				"getProductGroupByExternalReferenceCodeProductGroupProductsPage"));
		_resourceMethodPairs.put(
			"query#productGroupIdProductGroupProducts",
			new ObjectValuePair<>(
				ProductGroupProductResourceImpl.class,
				"getProductGroupIdProductGroupProductsPage"));
		_resourceMethodPairs.put(
			"query#productOption",
			new ObjectValuePair<>(
				ProductOptionResourceImpl.class, "getProductOption"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeProductOptions",
			new ObjectValuePair<>(
				ProductOptionResourceImpl.class,
				"getProductByExternalReferenceCodeProductOptionsPage"));
		_resourceMethodPairs.put(
			"query#productIdProductOptions",
			new ObjectValuePair<>(
				ProductOptionResourceImpl.class,
				"getProductIdProductOptionsPage"));
		_resourceMethodPairs.put(
			"query#productOptionIdProductOptionValues",
			new ObjectValuePair<>(
				ProductOptionValueResourceImpl.class,
				"getProductOptionIdProductOptionValuesPage"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeShippingConfiguration",
			new ObjectValuePair<>(
				ProductShippingConfigurationResourceImpl.class,
				"getProductByExternalReferenceCodeShippingConfiguration"));
		_resourceMethodPairs.put(
			"query#productIdShippingConfiguration",
			new ObjectValuePair<>(
				ProductShippingConfigurationResourceImpl.class,
				"getProductIdShippingConfiguration"));
		_resourceMethodPairs.put(
			"query#productIdProductSpecifications",
			new ObjectValuePair<>(
				ProductSpecificationResourceImpl.class,
				"getProductIdProductSpecificationsPage"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeSubscriptionConfiguration",
			new ObjectValuePair<>(
				ProductSubscriptionConfigurationResourceImpl.class,
				"getProductByExternalReferenceCodeSubscriptionConfiguration"));
		_resourceMethodPairs.put(
			"query#productIdSubscriptionConfiguration",
			new ObjectValuePair<>(
				ProductSubscriptionConfigurationResourceImpl.class,
				"getProductIdSubscriptionConfiguration"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeTaxConfiguration",
			new ObjectValuePair<>(
				ProductTaxConfigurationResourceImpl.class,
				"getProductByExternalReferenceCodeTaxConfiguration"));
		_resourceMethodPairs.put(
			"query#productIdTaxConfiguration",
			new ObjectValuePair<>(
				ProductTaxConfigurationResourceImpl.class,
				"getProductIdTaxConfiguration"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeRelatedProducts",
			new ObjectValuePair<>(
				RelatedProductResourceImpl.class,
				"getProductByExternalReferenceCodeRelatedProductsPage"));
		_resourceMethodPairs.put(
			"query#productIdRelatedProducts",
			new ObjectValuePair<>(
				RelatedProductResourceImpl.class,
				"getProductIdRelatedProductsPage"));
		_resourceMethodPairs.put(
			"query#relatedProduct",
			new ObjectValuePair<>(
				RelatedProductResourceImpl.class, "getRelatedProduct"));
		_resourceMethodPairs.put(
			"query#productByExternalReferenceCodeSkus",
			new ObjectValuePair<>(
				SkuResourceImpl.class,
				"getProductByExternalReferenceCodeSkusPage"));
		_resourceMethodPairs.put(
			"query#productIdSkus",
			new ObjectValuePair<>(
				SkuResourceImpl.class, "getProductIdSkusPage"));
		_resourceMethodPairs.put(
			"query#skus",
			new ObjectValuePair<>(SkuResourceImpl.class, "getSkusPage"));
		_resourceMethodPairs.put(
			"query#skuByExternalReferenceCode",
			new ObjectValuePair<>(
				SkuResourceImpl.class, "getSkuByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#sku",
			new ObjectValuePair<>(SkuResourceImpl.class, "getSku"));
		_resourceMethodPairs.put(
			"query#specifications",
			new ObjectValuePair<>(
				SpecificationResourceImpl.class, "getSpecificationsPage"));
		_resourceMethodPairs.put(
			"query#specification",
			new ObjectValuePair<>(
				SpecificationResourceImpl.class, "getSpecification"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AttachmentResource>
		_attachmentResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CatalogResource>
		_catalogResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CategoryResource>
		_categoryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DiagramResource>
		_diagramResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<MappedProductResource>
		_mappedProductResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OptionResource>
		_optionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OptionCategoryResource>
		_optionCategoryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OptionValueResource>
		_optionValueResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PinResource>
		_pinResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductResource>
		_productResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductAccountGroupResource>
		_productAccountGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductChannelResource>
		_productChannelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductConfigurationResource>
		_productConfigurationResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductGroupResource>
		_productGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductGroupProductResource>
		_productGroupProductResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductOptionResource>
		_productOptionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductOptionValueResource>
		_productOptionValueResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductShippingConfigurationResource>
		_productShippingConfigurationResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductSpecificationResource>
		_productSpecificationResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductSubscriptionConfigurationResource>
		_productSubscriptionConfigurationResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductTaxConfigurationResource>
		_productTaxConfigurationResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<RelatedProductResource>
		_relatedProductResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SkuResource>
		_skuResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SpecificationResource>
		_specificationResourceComponentServiceObjects;

}