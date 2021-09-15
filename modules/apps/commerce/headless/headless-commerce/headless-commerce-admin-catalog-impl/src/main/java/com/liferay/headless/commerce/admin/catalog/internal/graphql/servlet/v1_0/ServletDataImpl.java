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
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

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
@Component(enabled = false, immediate = true, service = ServletData.class)
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