/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.graphql.servlet.v1_0;

import com.liferay.headless.commerce.admin.catalog.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.commerce.admin.catalog.internal.graphql.query.v1_0.Query;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.AttachmentResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.CatalogResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.CategoryResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.DiagramResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.GroupedProductResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.LinkedProductResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.LowStockActionResourceImpl;
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
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.ProductVirtualSettingsResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.RelatedProductResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.SkuResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.SkuSubscriptionConfigurationResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.SkuUnitOfMeasureResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.SkuVirtualSettingsResourceImpl;
import com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0.SpecificationResourceImpl;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.AttachmentResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.CatalogResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.CategoryResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.DiagramResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.GroupedProductResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.LinkedProductResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.LowStockActionResource;
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
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductVirtualSettingsResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.RelatedProductResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.SkuResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.SkuSubscriptionConfigurationResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.SkuUnitOfMeasureResource;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.SkuVirtualSettingsResource;
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
		Mutation.setGroupedProductResourceComponentServiceObjects(
			_groupedProductResourceComponentServiceObjects);
		Mutation.setLowStockActionResourceComponentServiceObjects(
			_lowStockActionResourceComponentServiceObjects);
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
		Mutation.setSkuUnitOfMeasureResourceComponentServiceObjects(
			_skuUnitOfMeasureResourceComponentServiceObjects);
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
		Query.setGroupedProductResourceComponentServiceObjects(
			_groupedProductResourceComponentServiceObjects);
		Query.setLinkedProductResourceComponentServiceObjects(
			_linkedProductResourceComponentServiceObjects);
		Query.setLowStockActionResourceComponentServiceObjects(
			_lowStockActionResourceComponentServiceObjects);
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
		Query.setProductVirtualSettingsResourceComponentServiceObjects(
			_productVirtualSettingsResourceComponentServiceObjects);
		Query.setRelatedProductResourceComponentServiceObjects(
			_relatedProductResourceComponentServiceObjects);
		Query.setSkuResourceComponentServiceObjects(
			_skuResourceComponentServiceObjects);
		Query.setSkuSubscriptionConfigurationResourceComponentServiceObjects(
			_skuSubscriptionConfigurationResourceComponentServiceObjects);
		Query.setSkuUnitOfMeasureResourceComponentServiceObjects(
			_skuUnitOfMeasureResourceComponentServiceObjects);
		Query.setSkuVirtualSettingsResourceComponentServiceObjects(
			_skuVirtualSettingsResourceComponentServiceObjects);
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

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#deleteAttachmentByExternalReferenceCode",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"deleteAttachmentByExternalReferenceCode"));
					put(
						"mutation#deleteAttachment",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class, "deleteAttachment"));
					put(
						"mutation#deleteAttachmentBatch",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"deleteAttachmentBatch"));
					put(
						"mutation#createProductByExternalReferenceCodeAttachment",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"postProductByExternalReferenceCodeAttachment"));
					put(
						"mutation#createProductByExternalReferenceCodeAttachmentByBase64",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"postProductByExternalReferenceCodeAttachmentByBase64"));
					put(
						"mutation#createProductByExternalReferenceCodeAttachmentByUrl",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"postProductByExternalReferenceCodeAttachmentByUrl"));
					put(
						"mutation#createProductByExternalReferenceCodeImage",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"postProductByExternalReferenceCodeImage"));
					put(
						"mutation#createProductByExternalReferenceCodeImageByBase64",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"postProductByExternalReferenceCodeImageByBase64"));
					put(
						"mutation#createProductByExternalReferenceCodeImageByUrl",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"postProductByExternalReferenceCodeImageByUrl"));
					put(
						"mutation#createProductIdAttachment",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"postProductIdAttachment"));
					put(
						"mutation#createProductIdAttachmentBatch",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"postProductIdAttachmentBatch"));
					put(
						"mutation#createProductIdAttachmentByBase64",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"postProductIdAttachmentByBase64"));
					put(
						"mutation#createProductIdAttachmentByUrl",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"postProductIdAttachmentByUrl"));
					put(
						"mutation#createProductIdImage",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"postProductIdImage"));
					put(
						"mutation#createProductIdImageByBase64",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"postProductIdImageByBase64"));
					put(
						"mutation#createProductIdImageByUrl",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"postProductIdImageByUrl"));
					put(
						"mutation#deleteCatalogByExternalReferenceCode",
						new ObjectValuePair<>(
							CatalogResourceImpl.class,
							"deleteCatalogByExternalReferenceCode"));
					put(
						"mutation#patchCatalogByExternalReferenceCode",
						new ObjectValuePair<>(
							CatalogResourceImpl.class,
							"patchCatalogByExternalReferenceCode"));
					put(
						"mutation#deleteCatalog",
						new ObjectValuePair<>(
							CatalogResourceImpl.class, "deleteCatalog"));
					put(
						"mutation#deleteCatalogBatch",
						new ObjectValuePair<>(
							CatalogResourceImpl.class, "deleteCatalogBatch"));
					put(
						"mutation#patchCatalog",
						new ObjectValuePair<>(
							CatalogResourceImpl.class, "patchCatalog"));
					put(
						"mutation#createCatalogsPageExportBatch",
						new ObjectValuePair<>(
							CatalogResourceImpl.class,
							"postCatalogsPageExportBatch"));
					put(
						"mutation#createCatalog",
						new ObjectValuePair<>(
							CatalogResourceImpl.class, "postCatalog"));
					put(
						"mutation#createCatalogBatch",
						new ObjectValuePair<>(
							CatalogResourceImpl.class, "postCatalogBatch"));
					put(
						"mutation#patchProductByExternalReferenceCodeCategory",
						new ObjectValuePair<>(
							CategoryResourceImpl.class,
							"patchProductByExternalReferenceCodeCategory"));
					put(
						"mutation#patchProductIdCategory",
						new ObjectValuePair<>(
							CategoryResourceImpl.class,
							"patchProductIdCategory"));
					put(
						"mutation#patchDiagram",
						new ObjectValuePair<>(
							DiagramResourceImpl.class, "patchDiagram"));
					put(
						"mutation#createProductByExternalReferenceCodeDiagram",
						new ObjectValuePair<>(
							DiagramResourceImpl.class,
							"postProductByExternalReferenceCodeDiagram"));
					put(
						"mutation#createProductIdDiagram",
						new ObjectValuePair<>(
							DiagramResourceImpl.class, "postProductIdDiagram"));
					put(
						"mutation#createProductIdDiagramBatch",
						new ObjectValuePair<>(
							DiagramResourceImpl.class,
							"postProductIdDiagramBatch"));
					put(
						"mutation#deleteGroupedProduct",
						new ObjectValuePair<>(
							GroupedProductResourceImpl.class,
							"deleteGroupedProduct"));
					put(
						"mutation#deleteGroupedProductBatch",
						new ObjectValuePair<>(
							GroupedProductResourceImpl.class,
							"deleteGroupedProductBatch"));
					put(
						"mutation#patchGroupedProduct",
						new ObjectValuePair<>(
							GroupedProductResourceImpl.class,
							"patchGroupedProduct"));
					put(
						"mutation#createProductByExternalReferenceCodeGroupedProduct",
						new ObjectValuePair<>(
							GroupedProductResourceImpl.class,
							"postProductByExternalReferenceCodeGroupedProduct"));
					put(
						"mutation#createProductIdGroupedProduct",
						new ObjectValuePair<>(
							GroupedProductResourceImpl.class,
							"postProductIdGroupedProduct"));
					put(
						"mutation#createProductIdGroupedProductBatch",
						new ObjectValuePair<>(
							GroupedProductResourceImpl.class,
							"postProductIdGroupedProductBatch"));
					put(
						"mutation#createLowStockActionsPageExportBatch",
						new ObjectValuePair<>(
							LowStockActionResourceImpl.class,
							"postLowStockActionsPageExportBatch"));
					put(
						"mutation#deleteMappedProduct",
						new ObjectValuePair<>(
							MappedProductResourceImpl.class,
							"deleteMappedProduct"));
					put(
						"mutation#deleteMappedProductBatch",
						new ObjectValuePair<>(
							MappedProductResourceImpl.class,
							"deleteMappedProductBatch"));
					put(
						"mutation#patchMappedProduct",
						new ObjectValuePair<>(
							MappedProductResourceImpl.class,
							"patchMappedProduct"));
					put(
						"mutation#createProductByExternalReferenceCodeMappedProduct",
						new ObjectValuePair<>(
							MappedProductResourceImpl.class,
							"postProductByExternalReferenceCodeMappedProduct"));
					put(
						"mutation#createProductIdMappedProduct",
						new ObjectValuePair<>(
							MappedProductResourceImpl.class,
							"postProductIdMappedProduct"));
					put(
						"mutation#createProductIdMappedProductBatch",
						new ObjectValuePair<>(
							MappedProductResourceImpl.class,
							"postProductIdMappedProductBatch"));
					put(
						"mutation#createOptionsPageExportBatch",
						new ObjectValuePair<>(
							OptionResourceImpl.class,
							"postOptionsPageExportBatch"));
					put(
						"mutation#createOption",
						new ObjectValuePair<>(
							OptionResourceImpl.class, "postOption"));
					put(
						"mutation#createOptionBatch",
						new ObjectValuePair<>(
							OptionResourceImpl.class, "postOptionBatch"));
					put(
						"mutation#deleteOptionByExternalReferenceCode",
						new ObjectValuePair<>(
							OptionResourceImpl.class,
							"deleteOptionByExternalReferenceCode"));
					put(
						"mutation#patchOptionByExternalReferenceCode",
						new ObjectValuePair<>(
							OptionResourceImpl.class,
							"patchOptionByExternalReferenceCode"));
					put(
						"mutation#deleteOption",
						new ObjectValuePair<>(
							OptionResourceImpl.class, "deleteOption"));
					put(
						"mutation#deleteOptionBatch",
						new ObjectValuePair<>(
							OptionResourceImpl.class, "deleteOptionBatch"));
					put(
						"mutation#patchOption",
						new ObjectValuePair<>(
							OptionResourceImpl.class, "patchOption"));
					put(
						"mutation#createOptionCategoriesPageExportBatch",
						new ObjectValuePair<>(
							OptionCategoryResourceImpl.class,
							"postOptionCategoriesPageExportBatch"));
					put(
						"mutation#createOptionCategory",
						new ObjectValuePair<>(
							OptionCategoryResourceImpl.class,
							"postOptionCategory"));
					put(
						"mutation#createOptionCategoryBatch",
						new ObjectValuePair<>(
							OptionCategoryResourceImpl.class,
							"postOptionCategoryBatch"));
					put(
						"mutation#deleteOptionCategory",
						new ObjectValuePair<>(
							OptionCategoryResourceImpl.class,
							"deleteOptionCategory"));
					put(
						"mutation#deleteOptionCategoryBatch",
						new ObjectValuePair<>(
							OptionCategoryResourceImpl.class,
							"deleteOptionCategoryBatch"));
					put(
						"mutation#patchOptionCategory",
						new ObjectValuePair<>(
							OptionCategoryResourceImpl.class,
							"patchOptionCategory"));
					put(
						"mutation#deleteOptionValueByExternalReferenceCode",
						new ObjectValuePair<>(
							OptionValueResourceImpl.class,
							"deleteOptionValueByExternalReferenceCode"));
					put(
						"mutation#patchOptionValueByExternalReferenceCode",
						new ObjectValuePair<>(
							OptionValueResourceImpl.class,
							"patchOptionValueByExternalReferenceCode"));
					put(
						"mutation#deleteOptionValue",
						new ObjectValuePair<>(
							OptionValueResourceImpl.class,
							"deleteOptionValue"));
					put(
						"mutation#deleteOptionValueBatch",
						new ObjectValuePair<>(
							OptionValueResourceImpl.class,
							"deleteOptionValueBatch"));
					put(
						"mutation#patchOptionValue",
						new ObjectValuePair<>(
							OptionValueResourceImpl.class, "patchOptionValue"));
					put(
						"mutation#createOptionByExternalReferenceCodeOptionValue",
						new ObjectValuePair<>(
							OptionValueResourceImpl.class,
							"postOptionByExternalReferenceCodeOptionValue"));
					put(
						"mutation#createOptionIdOptionValue",
						new ObjectValuePair<>(
							OptionValueResourceImpl.class,
							"postOptionIdOptionValue"));
					put(
						"mutation#createOptionIdOptionValueBatch",
						new ObjectValuePair<>(
							OptionValueResourceImpl.class,
							"postOptionIdOptionValueBatch"));
					put(
						"mutation#deletePin",
						new ObjectValuePair<>(
							PinResourceImpl.class, "deletePin"));
					put(
						"mutation#deletePinBatch",
						new ObjectValuePair<>(
							PinResourceImpl.class, "deletePinBatch"));
					put(
						"mutation#patchPin",
						new ObjectValuePair<>(
							PinResourceImpl.class, "patchPin"));
					put(
						"mutation#createProductByExternalReferenceCodePin",
						new ObjectValuePair<>(
							PinResourceImpl.class,
							"postProductByExternalReferenceCodePin"));
					put(
						"mutation#createProductIdPin",
						new ObjectValuePair<>(
							PinResourceImpl.class, "postProductIdPin"));
					put(
						"mutation#createProductIdPinBatch",
						new ObjectValuePair<>(
							PinResourceImpl.class, "postProductIdPinBatch"));
					put(
						"mutation#createProductsPageExportBatch",
						new ObjectValuePair<>(
							ProductResourceImpl.class,
							"postProductsPageExportBatch"));
					put(
						"mutation#createProduct",
						new ObjectValuePair<>(
							ProductResourceImpl.class, "postProduct"));
					put(
						"mutation#createProductBatch",
						new ObjectValuePair<>(
							ProductResourceImpl.class, "postProductBatch"));
					put(
						"mutation#deleteProductByExternalReferenceCode",
						new ObjectValuePair<>(
							ProductResourceImpl.class,
							"deleteProductByExternalReferenceCode"));
					put(
						"mutation#patchProductByExternalReferenceCode",
						new ObjectValuePair<>(
							ProductResourceImpl.class,
							"patchProductByExternalReferenceCode"));
					put(
						"mutation#deleteProductByExternalReferenceCodeByVersion",
						new ObjectValuePair<>(
							ProductResourceImpl.class,
							"deleteProductByExternalReferenceCodeByVersion"));
					put(
						"mutation#createProductByExternalReferenceCodeClone",
						new ObjectValuePair<>(
							ProductResourceImpl.class,
							"postProductByExternalReferenceCodeClone"));
					put(
						"mutation#deleteProduct",
						new ObjectValuePair<>(
							ProductResourceImpl.class, "deleteProduct"));
					put(
						"mutation#deleteProductBatch",
						new ObjectValuePair<>(
							ProductResourceImpl.class, "deleteProductBatch"));
					put(
						"mutation#patchProduct",
						new ObjectValuePair<>(
							ProductResourceImpl.class, "patchProduct"));
					put(
						"mutation#deleteProductByVersion",
						new ObjectValuePair<>(
							ProductResourceImpl.class,
							"deleteProductByVersion"));
					put(
						"mutation#createProductClone",
						new ObjectValuePair<>(
							ProductResourceImpl.class, "postProductClone"));
					put(
						"mutation#deleteProductAccountGroup",
						new ObjectValuePair<>(
							ProductAccountGroupResourceImpl.class,
							"deleteProductAccountGroup"));
					put(
						"mutation#deleteProductAccountGroupBatch",
						new ObjectValuePair<>(
							ProductAccountGroupResourceImpl.class,
							"deleteProductAccountGroupBatch"));
					put(
						"mutation#deleteProductChannel",
						new ObjectValuePair<>(
							ProductChannelResourceImpl.class,
							"deleteProductChannel"));
					put(
						"mutation#deleteProductChannelBatch",
						new ObjectValuePair<>(
							ProductChannelResourceImpl.class,
							"deleteProductChannelBatch"));
					put(
						"mutation#patchProductByExternalReferenceCodeConfiguration",
						new ObjectValuePair<>(
							ProductConfigurationResourceImpl.class,
							"patchProductByExternalReferenceCodeConfiguration"));
					put(
						"mutation#patchProductIdConfiguration",
						new ObjectValuePair<>(
							ProductConfigurationResourceImpl.class,
							"patchProductIdConfiguration"));
					put(
						"mutation#createProductGroupsPageExportBatch",
						new ObjectValuePair<>(
							ProductGroupResourceImpl.class,
							"postProductGroupsPageExportBatch"));
					put(
						"mutation#createProductGroup",
						new ObjectValuePair<>(
							ProductGroupResourceImpl.class,
							"postProductGroup"));
					put(
						"mutation#createProductGroupBatch",
						new ObjectValuePair<>(
							ProductGroupResourceImpl.class,
							"postProductGroupBatch"));
					put(
						"mutation#deleteProductGroupByExternalReferenceCode",
						new ObjectValuePair<>(
							ProductGroupResourceImpl.class,
							"deleteProductGroupByExternalReferenceCode"));
					put(
						"mutation#patchProductGroupByExternalReferenceCode",
						new ObjectValuePair<>(
							ProductGroupResourceImpl.class,
							"patchProductGroupByExternalReferenceCode"));
					put(
						"mutation#deleteProductGroup",
						new ObjectValuePair<>(
							ProductGroupResourceImpl.class,
							"deleteProductGroup"));
					put(
						"mutation#deleteProductGroupBatch",
						new ObjectValuePair<>(
							ProductGroupResourceImpl.class,
							"deleteProductGroupBatch"));
					put(
						"mutation#patchProductGroup",
						new ObjectValuePair<>(
							ProductGroupResourceImpl.class,
							"patchProductGroup"));
					put(
						"mutation#deleteProductGroupProduct",
						new ObjectValuePair<>(
							ProductGroupProductResourceImpl.class,
							"deleteProductGroupProduct"));
					put(
						"mutation#deleteProductGroupProductBatch",
						new ObjectValuePair<>(
							ProductGroupProductResourceImpl.class,
							"deleteProductGroupProductBatch"));
					put(
						"mutation#createProductGroupByExternalReferenceCodeProductGroupProduct",
						new ObjectValuePair<>(
							ProductGroupProductResourceImpl.class,
							"postProductGroupByExternalReferenceCodeProductGroupProduct"));
					put(
						"mutation#createProductGroupIdProductGroupProduct",
						new ObjectValuePair<>(
							ProductGroupProductResourceImpl.class,
							"postProductGroupIdProductGroupProduct"));
					put(
						"mutation#createProductGroupIdProductGroupProductBatch",
						new ObjectValuePair<>(
							ProductGroupProductResourceImpl.class,
							"postProductGroupIdProductGroupProductBatch"));
					put(
						"mutation#deleteProductOption",
						new ObjectValuePair<>(
							ProductOptionResourceImpl.class,
							"deleteProductOption"));
					put(
						"mutation#deleteProductOptionBatch",
						new ObjectValuePair<>(
							ProductOptionResourceImpl.class,
							"deleteProductOptionBatch"));
					put(
						"mutation#patchProductOption",
						new ObjectValuePair<>(
							ProductOptionResourceImpl.class,
							"patchProductOption"));
					put(
						"mutation#createProductByExternalReferenceCodeProductOptionsPage",
						new ObjectValuePair<>(
							ProductOptionResourceImpl.class,
							"postProductByExternalReferenceCodeProductOptionsPage"));
					put(
						"mutation#createProductIdProductOptionsPage",
						new ObjectValuePair<>(
							ProductOptionResourceImpl.class,
							"postProductIdProductOptionsPage"));
					put(
						"mutation#deleteProductOptionValue",
						new ObjectValuePair<>(
							ProductOptionValueResourceImpl.class,
							"deleteProductOptionValue"));
					put(
						"mutation#deleteProductOptionValueBatch",
						new ObjectValuePair<>(
							ProductOptionValueResourceImpl.class,
							"deleteProductOptionValueBatch"));
					put(
						"mutation#patchProductOptionValue",
						new ObjectValuePair<>(
							ProductOptionValueResourceImpl.class,
							"patchProductOptionValue"));
					put(
						"mutation#createProductOptionIdProductOptionValue",
						new ObjectValuePair<>(
							ProductOptionValueResourceImpl.class,
							"postProductOptionIdProductOptionValue"));
					put(
						"mutation#createProductOptionIdProductOptionValueBatch",
						new ObjectValuePair<>(
							ProductOptionValueResourceImpl.class,
							"postProductOptionIdProductOptionValueBatch"));
					put(
						"mutation#patchProductByExternalReferenceCodeShippingConfiguration",
						new ObjectValuePair<>(
							ProductShippingConfigurationResourceImpl.class,
							"patchProductByExternalReferenceCodeShippingConfiguration"));
					put(
						"mutation#patchProductIdShippingConfiguration",
						new ObjectValuePair<>(
							ProductShippingConfigurationResourceImpl.class,
							"patchProductIdShippingConfiguration"));
					put(
						"mutation#deleteProductSpecification",
						new ObjectValuePair<>(
							ProductSpecificationResourceImpl.class,
							"deleteProductSpecification"));
					put(
						"mutation#deleteProductSpecificationBatch",
						new ObjectValuePair<>(
							ProductSpecificationResourceImpl.class,
							"deleteProductSpecificationBatch"));
					put(
						"mutation#patchProductSpecification",
						new ObjectValuePair<>(
							ProductSpecificationResourceImpl.class,
							"patchProductSpecification"));
					put(
						"mutation#createProductIdProductSpecification",
						new ObjectValuePair<>(
							ProductSpecificationResourceImpl.class,
							"postProductIdProductSpecification"));
					put(
						"mutation#createProductIdProductSpecificationBatch",
						new ObjectValuePair<>(
							ProductSpecificationResourceImpl.class,
							"postProductIdProductSpecificationBatch"));
					put(
						"mutation#patchProductByExternalReferenceCodeSubscriptionConfiguration",
						new ObjectValuePair<>(
							ProductSubscriptionConfigurationResourceImpl.class,
							"patchProductByExternalReferenceCodeSubscriptionConfiguration"));
					put(
						"mutation#patchProductIdSubscriptionConfiguration",
						new ObjectValuePair<>(
							ProductSubscriptionConfigurationResourceImpl.class,
							"patchProductIdSubscriptionConfiguration"));
					put(
						"mutation#patchProductByExternalReferenceCodeTaxConfiguration",
						new ObjectValuePair<>(
							ProductTaxConfigurationResourceImpl.class,
							"patchProductByExternalReferenceCodeTaxConfiguration"));
					put(
						"mutation#patchProductIdTaxConfiguration",
						new ObjectValuePair<>(
							ProductTaxConfigurationResourceImpl.class,
							"patchProductIdTaxConfiguration"));
					put(
						"mutation#createProductByExternalReferenceCodeRelatedProduct",
						new ObjectValuePair<>(
							RelatedProductResourceImpl.class,
							"postProductByExternalReferenceCodeRelatedProduct"));
					put(
						"mutation#createProductIdRelatedProduct",
						new ObjectValuePair<>(
							RelatedProductResourceImpl.class,
							"postProductIdRelatedProduct"));
					put(
						"mutation#createProductIdRelatedProductBatch",
						new ObjectValuePair<>(
							RelatedProductResourceImpl.class,
							"postProductIdRelatedProductBatch"));
					put(
						"mutation#deleteRelatedProduct",
						new ObjectValuePair<>(
							RelatedProductResourceImpl.class,
							"deleteRelatedProduct"));
					put(
						"mutation#deleteRelatedProductBatch",
						new ObjectValuePair<>(
							RelatedProductResourceImpl.class,
							"deleteRelatedProductBatch"));
					put(
						"mutation#createProductByExternalReferenceCodeSku",
						new ObjectValuePair<>(
							SkuResourceImpl.class,
							"postProductByExternalReferenceCodeSku"));
					put(
						"mutation#createProductIdSku",
						new ObjectValuePair<>(
							SkuResourceImpl.class, "postProductIdSku"));
					put(
						"mutation#createProductIdSkuBatch",
						new ObjectValuePair<>(
							SkuResourceImpl.class, "postProductIdSkuBatch"));
					put(
						"mutation#createSkusPageExportBatch",
						new ObjectValuePair<>(
							SkuResourceImpl.class, "postSkusPageExportBatch"));
					put(
						"mutation#deleteSkuByExternalReferenceCode",
						new ObjectValuePair<>(
							SkuResourceImpl.class,
							"deleteSkuByExternalReferenceCode"));
					put(
						"mutation#patchSkuByExternalReferenceCode",
						new ObjectValuePair<>(
							SkuResourceImpl.class,
							"patchSkuByExternalReferenceCode"));
					put(
						"mutation#deleteSku",
						new ObjectValuePair<>(
							SkuResourceImpl.class, "deleteSku"));
					put(
						"mutation#deleteSkuBatch",
						new ObjectValuePair<>(
							SkuResourceImpl.class, "deleteSkuBatch"));
					put(
						"mutation#patchSku",
						new ObjectValuePair<>(
							SkuResourceImpl.class, "patchSku"));
					put(
						"mutation#deleteSkuUnitOfMeasure",
						new ObjectValuePair<>(
							SkuUnitOfMeasureResourceImpl.class,
							"deleteSkuUnitOfMeasure"));
					put(
						"mutation#deleteSkuUnitOfMeasureBatch",
						new ObjectValuePair<>(
							SkuUnitOfMeasureResourceImpl.class,
							"deleteSkuUnitOfMeasureBatch"));
					put(
						"mutation#patchSkuUnitOfMeasure",
						new ObjectValuePair<>(
							SkuUnitOfMeasureResourceImpl.class,
							"patchSkuUnitOfMeasure"));
					put(
						"mutation#createSkuByExternalReferenceCodeSkuUnitOfMeasure",
						new ObjectValuePair<>(
							SkuUnitOfMeasureResourceImpl.class,
							"postSkuByExternalReferenceCodeSkuUnitOfMeasure"));
					put(
						"mutation#createSkuIdSkuUnitOfMeasure",
						new ObjectValuePair<>(
							SkuUnitOfMeasureResourceImpl.class,
							"postSkuIdSkuUnitOfMeasure"));
					put(
						"mutation#createSkuIdSkuUnitOfMeasureBatch",
						new ObjectValuePair<>(
							SkuUnitOfMeasureResourceImpl.class,
							"postSkuIdSkuUnitOfMeasureBatch"));
					put(
						"mutation#createSpecificationsPageExportBatch",
						new ObjectValuePair<>(
							SpecificationResourceImpl.class,
							"postSpecificationsPageExportBatch"));
					put(
						"mutation#createSpecification",
						new ObjectValuePair<>(
							SpecificationResourceImpl.class,
							"postSpecification"));
					put(
						"mutation#createSpecificationBatch",
						new ObjectValuePair<>(
							SpecificationResourceImpl.class,
							"postSpecificationBatch"));
					put(
						"mutation#deleteSpecification",
						new ObjectValuePair<>(
							SpecificationResourceImpl.class,
							"deleteSpecification"));
					put(
						"mutation#deleteSpecificationBatch",
						new ObjectValuePair<>(
							SpecificationResourceImpl.class,
							"deleteSpecificationBatch"));
					put(
						"mutation#patchSpecification",
						new ObjectValuePair<>(
							SpecificationResourceImpl.class,
							"patchSpecification"));

					put(
						"query#productByExternalReferenceCodeAttachments",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"getProductByExternalReferenceCodeAttachmentsPage"));
					put(
						"query#productByExternalReferenceCodeImages",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"getProductByExternalReferenceCodeImagesPage"));
					put(
						"query#productIdAttachments",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"getProductIdAttachmentsPage"));
					put(
						"query#productIdImages",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"getProductIdImagesPage"));
					put(
						"query#catalogByExternalReferenceCode",
						new ObjectValuePair<>(
							CatalogResourceImpl.class,
							"getCatalogByExternalReferenceCode"));
					put(
						"query#catalog",
						new ObjectValuePair<>(
							CatalogResourceImpl.class, "getCatalog"));
					put(
						"query#catalogs",
						new ObjectValuePair<>(
							CatalogResourceImpl.class, "getCatalogsPage"));
					put(
						"query#productByExternalReferenceCodeCatalog",
						new ObjectValuePair<>(
							CatalogResourceImpl.class,
							"getProductByExternalReferenceCodeCatalog"));
					put(
						"query#productIdCatalog",
						new ObjectValuePair<>(
							CatalogResourceImpl.class, "getProductIdCatalog"));
					put(
						"query#productByExternalReferenceCodeCategories",
						new ObjectValuePair<>(
							CategoryResourceImpl.class,
							"getProductByExternalReferenceCodeCategoriesPage"));
					put(
						"query#productIdCategories",
						new ObjectValuePair<>(
							CategoryResourceImpl.class,
							"getProductIdCategoriesPage"));
					put(
						"query#productByExternalReferenceCodeDiagram",
						new ObjectValuePair<>(
							DiagramResourceImpl.class,
							"getProductByExternalReferenceCodeDiagram"));
					put(
						"query#productIdDiagram",
						new ObjectValuePair<>(
							DiagramResourceImpl.class, "getProductIdDiagram"));
					put(
						"query#productByExternalReferenceCodeGroupedProducts",
						new ObjectValuePair<>(
							GroupedProductResourceImpl.class,
							"getProductByExternalReferenceCodeGroupedProductsPage"));
					put(
						"query#productIdGroupedProducts",
						new ObjectValuePair<>(
							GroupedProductResourceImpl.class,
							"getProductIdGroupedProductsPage"));
					put(
						"query#productIdLinkedProducts",
						new ObjectValuePair<>(
							LinkedProductResourceImpl.class,
							"getProductIdLinkedProductsPage"));
					put(
						"query#lowStockActions",
						new ObjectValuePair<>(
							LowStockActionResourceImpl.class,
							"getLowStockActionsPage"));
					put(
						"query#productByExternalReferenceCodeMappedProducts",
						new ObjectValuePair<>(
							MappedProductResourceImpl.class,
							"getProductByExternalReferenceCodeMappedProductsPage"));
					put(
						"query#productByExternalReferenceCodeMappedProductBySequence",
						new ObjectValuePair<>(
							MappedProductResourceImpl.class,
							"getProductByExternalReferenceCodeMappedProductBySequence"));
					put(
						"query#productIdMappedProducts",
						new ObjectValuePair<>(
							MappedProductResourceImpl.class,
							"getProductIdMappedProductsPage"));
					put(
						"query#productIdMappedProductBySequence",
						new ObjectValuePair<>(
							MappedProductResourceImpl.class,
							"getProductIdMappedProductBySequence"));
					put(
						"query#options",
						new ObjectValuePair<>(
							OptionResourceImpl.class, "getOptionsPage"));
					put(
						"query#optionByExternalReferenceCode",
						new ObjectValuePair<>(
							OptionResourceImpl.class,
							"getOptionByExternalReferenceCode"));
					put(
						"query#option",
						new ObjectValuePair<>(
							OptionResourceImpl.class, "getOption"));
					put(
						"query#optionCategories",
						new ObjectValuePair<>(
							OptionCategoryResourceImpl.class,
							"getOptionCategoriesPage"));
					put(
						"query#optionCategory",
						new ObjectValuePair<>(
							OptionCategoryResourceImpl.class,
							"getOptionCategory"));
					put(
						"query#optionValueByExternalReferenceCode",
						new ObjectValuePair<>(
							OptionValueResourceImpl.class,
							"getOptionValueByExternalReferenceCode"));
					put(
						"query#optionValue",
						new ObjectValuePair<>(
							OptionValueResourceImpl.class, "getOptionValue"));
					put(
						"query#optionByExternalReferenceCodeOptionValues",
						new ObjectValuePair<>(
							OptionValueResourceImpl.class,
							"getOptionByExternalReferenceCodeOptionValuesPage"));
					put(
						"query#optionIdOptionValues",
						new ObjectValuePair<>(
							OptionValueResourceImpl.class,
							"getOptionIdOptionValuesPage"));
					put(
						"query#productByExternalReferenceCodePins",
						new ObjectValuePair<>(
							PinResourceImpl.class,
							"getProductByExternalReferenceCodePinsPage"));
					put(
						"query#productIdPins",
						new ObjectValuePair<>(
							PinResourceImpl.class, "getProductIdPinsPage"));
					put(
						"query#products",
						new ObjectValuePair<>(
							ProductResourceImpl.class, "getProductsPage"));
					put(
						"query#productByExternalReferenceCode",
						new ObjectValuePair<>(
							ProductResourceImpl.class,
							"getProductByExternalReferenceCode"));
					put(
						"query#productByExternalReferenceCodeByVersion",
						new ObjectValuePair<>(
							ProductResourceImpl.class,
							"getProductByExternalReferenceCodeByVersion"));
					put(
						"query#product",
						new ObjectValuePair<>(
							ProductResourceImpl.class, "getProduct"));
					put(
						"query#productByVersion",
						new ObjectValuePair<>(
							ProductResourceImpl.class, "getProductByVersion"));
					put(
						"query#productAccountGroup",
						new ObjectValuePair<>(
							ProductAccountGroupResourceImpl.class,
							"getProductAccountGroup"));
					put(
						"query#productByExternalReferenceCodeProductAccountGroups",
						new ObjectValuePair<>(
							ProductAccountGroupResourceImpl.class,
							"getProductByExternalReferenceCodeProductAccountGroupsPage"));
					put(
						"query#productIdProductAccountGroups",
						new ObjectValuePair<>(
							ProductAccountGroupResourceImpl.class,
							"getProductIdProductAccountGroupsPage"));
					put(
						"query#productChannel",
						new ObjectValuePair<>(
							ProductChannelResourceImpl.class,
							"getProductChannel"));
					put(
						"query#productByExternalReferenceCodeProductChannels",
						new ObjectValuePair<>(
							ProductChannelResourceImpl.class,
							"getProductByExternalReferenceCodeProductChannelsPage"));
					put(
						"query#productIdProductChannels",
						new ObjectValuePair<>(
							ProductChannelResourceImpl.class,
							"getProductIdProductChannelsPage"));
					put(
						"query#productByExternalReferenceCodeConfiguration",
						new ObjectValuePair<>(
							ProductConfigurationResourceImpl.class,
							"getProductByExternalReferenceCodeConfiguration"));
					put(
						"query#productIdConfiguration",
						new ObjectValuePair<>(
							ProductConfigurationResourceImpl.class,
							"getProductIdConfiguration"));
					put(
						"query#productGroups",
						new ObjectValuePair<>(
							ProductGroupResourceImpl.class,
							"getProductGroupsPage"));
					put(
						"query#productGroupByExternalReferenceCode",
						new ObjectValuePair<>(
							ProductGroupResourceImpl.class,
							"getProductGroupByExternalReferenceCode"));
					put(
						"query#productGroup",
						new ObjectValuePair<>(
							ProductGroupResourceImpl.class, "getProductGroup"));
					put(
						"query#productGroupByExternalReferenceCodeProductGroupProducts",
						new ObjectValuePair<>(
							ProductGroupProductResourceImpl.class,
							"getProductGroupByExternalReferenceCodeProductGroupProductsPage"));
					put(
						"query#productGroupIdProductGroupProducts",
						new ObjectValuePair<>(
							ProductGroupProductResourceImpl.class,
							"getProductGroupIdProductGroupProductsPage"));
					put(
						"query#productOption",
						new ObjectValuePair<>(
							ProductOptionResourceImpl.class,
							"getProductOption"));
					put(
						"query#productByExternalReferenceCodeProductOptions",
						new ObjectValuePair<>(
							ProductOptionResourceImpl.class,
							"getProductByExternalReferenceCodeProductOptionsPage"));
					put(
						"query#productIdProductOptions",
						new ObjectValuePair<>(
							ProductOptionResourceImpl.class,
							"getProductIdProductOptionsPage"));
					put(
						"query#productOptionValue",
						new ObjectValuePair<>(
							ProductOptionValueResourceImpl.class,
							"getProductOptionValue"));
					put(
						"query#productOptionIdProductOptionValues",
						new ObjectValuePair<>(
							ProductOptionValueResourceImpl.class,
							"getProductOptionIdProductOptionValuesPage"));
					put(
						"query#productByExternalReferenceCodeShippingConfiguration",
						new ObjectValuePair<>(
							ProductShippingConfigurationResourceImpl.class,
							"getProductByExternalReferenceCodeShippingConfiguration"));
					put(
						"query#productIdShippingConfiguration",
						new ObjectValuePair<>(
							ProductShippingConfigurationResourceImpl.class,
							"getProductIdShippingConfiguration"));
					put(
						"query#productSpecification",
						new ObjectValuePair<>(
							ProductSpecificationResourceImpl.class,
							"getProductSpecification"));
					put(
						"query#productIdProductSpecifications",
						new ObjectValuePair<>(
							ProductSpecificationResourceImpl.class,
							"getProductIdProductSpecificationsPage"));
					put(
						"query#productByExternalReferenceCodeSubscriptionConfiguration",
						new ObjectValuePair<>(
							ProductSubscriptionConfigurationResourceImpl.class,
							"getProductByExternalReferenceCodeSubscriptionConfiguration"));
					put(
						"query#productIdSubscriptionConfiguration",
						new ObjectValuePair<>(
							ProductSubscriptionConfigurationResourceImpl.class,
							"getProductIdSubscriptionConfiguration"));
					put(
						"query#productByExternalReferenceCodeTaxConfiguration",
						new ObjectValuePair<>(
							ProductTaxConfigurationResourceImpl.class,
							"getProductByExternalReferenceCodeTaxConfiguration"));
					put(
						"query#productIdTaxConfiguration",
						new ObjectValuePair<>(
							ProductTaxConfigurationResourceImpl.class,
							"getProductIdTaxConfiguration"));
					put(
						"query#productByExternalReferenceCodeProductVirtualSettings",
						new ObjectValuePair<>(
							ProductVirtualSettingsResourceImpl.class,
							"getProductByExternalReferenceCodeProductVirtualSettings"));
					put(
						"query#productIdProductVirtualSettings",
						new ObjectValuePair<>(
							ProductVirtualSettingsResourceImpl.class,
							"getProductIdProductVirtualSettings"));
					put(
						"query#productByExternalReferenceCodeRelatedProducts",
						new ObjectValuePair<>(
							RelatedProductResourceImpl.class,
							"getProductByExternalReferenceCodeRelatedProductsPage"));
					put(
						"query#productIdRelatedProducts",
						new ObjectValuePair<>(
							RelatedProductResourceImpl.class,
							"getProductIdRelatedProductsPage"));
					put(
						"query#relatedProduct",
						new ObjectValuePair<>(
							RelatedProductResourceImpl.class,
							"getRelatedProduct"));
					put(
						"query#productByExternalReferenceCodeSkus",
						new ObjectValuePair<>(
							SkuResourceImpl.class,
							"getProductByExternalReferenceCodeSkusPage"));
					put(
						"query#productIdSkus",
						new ObjectValuePair<>(
							SkuResourceImpl.class, "getProductIdSkusPage"));
					put(
						"query#skus",
						new ObjectValuePair<>(
							SkuResourceImpl.class, "getSkusPage"));
					put(
						"query#skuByExternalReferenceCode",
						new ObjectValuePair<>(
							SkuResourceImpl.class,
							"getSkuByExternalReferenceCode"));
					put(
						"query#sku",
						new ObjectValuePair<>(SkuResourceImpl.class, "getSku"));
					put(
						"query#unitOfMeasureSkus",
						new ObjectValuePair<>(
							SkuResourceImpl.class, "getUnitOfMeasureSkusPage"));
					put(
						"query#skuByExternalReferenceCodeSkuSubscriptionConfiguration",
						new ObjectValuePair<>(
							SkuSubscriptionConfigurationResourceImpl.class,
							"getSkuByExternalReferenceCodeSkuSubscriptionConfiguration"));
					put(
						"query#skuIdSkuSubscriptionConfiguration",
						new ObjectValuePair<>(
							SkuSubscriptionConfigurationResourceImpl.class,
							"getSkuIdSkuSubscriptionConfiguration"));
					put(
						"query#skuUnitOfMeasure",
						new ObjectValuePair<>(
							SkuUnitOfMeasureResourceImpl.class,
							"getSkuUnitOfMeasure"));
					put(
						"query#skuByExternalReferenceCodeSkuUnitOfMeasures",
						new ObjectValuePair<>(
							SkuUnitOfMeasureResourceImpl.class,
							"getSkuByExternalReferenceCodeSkuUnitOfMeasuresPage"));
					put(
						"query#skuIdSkuUnitOfMeasures",
						new ObjectValuePair<>(
							SkuUnitOfMeasureResourceImpl.class,
							"getSkuIdSkuUnitOfMeasuresPage"));
					put(
						"query#skuByExternalReferenceCodeSkuVirtualSettings",
						new ObjectValuePair<>(
							SkuVirtualSettingsResourceImpl.class,
							"getSkuByExternalReferenceCodeSkuVirtualSettings"));
					put(
						"query#skuIdSkuVirtualSettings",
						new ObjectValuePair<>(
							SkuVirtualSettingsResourceImpl.class,
							"getSkuIdSkuVirtualSettings"));
					put(
						"query#specifications",
						new ObjectValuePair<>(
							SpecificationResourceImpl.class,
							"getSpecificationsPage"));
					put(
						"query#specification",
						new ObjectValuePair<>(
							SpecificationResourceImpl.class,
							"getSpecification"));

					put(
						"query#Catalog.productByExternalReferenceCodeConfiguration",
						new ObjectValuePair<>(
							ProductConfigurationResourceImpl.class,
							"getProductByExternalReferenceCodeConfiguration"));
					put(
						"query#Catalog.productByExternalReferenceCode",
						new ObjectValuePair<>(
							ProductResourceImpl.class,
							"getProductByExternalReferenceCode"));
					put(
						"query#Catalog.productByExternalReferenceCodeCategories",
						new ObjectValuePair<>(
							CategoryResourceImpl.class,
							"getProductByExternalReferenceCodeCategoriesPage"));
					put(
						"query#Catalog.productByExternalReferenceCodeGroupedProducts",
						new ObjectValuePair<>(
							GroupedProductResourceImpl.class,
							"getProductByExternalReferenceCodeGroupedProductsPage"));
					put(
						"query#Catalog.productByExternalReferenceCodeProductChannels",
						new ObjectValuePair<>(
							ProductChannelResourceImpl.class,
							"getProductByExternalReferenceCodeProductChannelsPage"));
					put(
						"query#Catalog.skuByExternalReferenceCodeSkuSubscriptionConfiguration",
						new ObjectValuePair<>(
							SkuSubscriptionConfigurationResourceImpl.class,
							"getSkuByExternalReferenceCodeSkuSubscriptionConfiguration"));
					put(
						"query#Catalog.optionValueByExternalReferenceCode",
						new ObjectValuePair<>(
							OptionValueResourceImpl.class,
							"getOptionValueByExternalReferenceCode"));
					put(
						"query#Catalog.productByExternalReferenceCodeCatalog",
						new ObjectValuePair<>(
							CatalogResourceImpl.class,
							"getProductByExternalReferenceCodeCatalog"));
					put(
						"query#Option.catalogByExternalReferenceCode",
						new ObjectValuePair<>(
							CatalogResourceImpl.class,
							"getCatalogByExternalReferenceCode"));
					put(
						"query#Catalog.productByExternalReferenceCodeMappedProducts",
						new ObjectValuePair<>(
							MappedProductResourceImpl.class,
							"getProductByExternalReferenceCodeMappedProductsPage"));
					put(
						"query#Catalog.skuByExternalReferenceCodeSkuVirtualSettings",
						new ObjectValuePair<>(
							SkuVirtualSettingsResourceImpl.class,
							"getSkuByExternalReferenceCodeSkuVirtualSettings"));
					put(
						"query#Catalog.productByExternalReferenceCodeDiagram",
						new ObjectValuePair<>(
							DiagramResourceImpl.class,
							"getProductByExternalReferenceCodeDiagram"));
					put(
						"query#Catalog.productByExternalReferenceCodeProductAccountGroups",
						new ObjectValuePair<>(
							ProductAccountGroupResourceImpl.class,
							"getProductByExternalReferenceCodeProductAccountGroupsPage"));
					put(
						"query#Catalog.productByExternalReferenceCodeSkus",
						new ObjectValuePair<>(
							SkuResourceImpl.class,
							"getProductByExternalReferenceCodeSkusPage"));
					put(
						"query#Catalog.productByExternalReferenceCodeShippingConfiguration",
						new ObjectValuePair<>(
							ProductShippingConfigurationResourceImpl.class,
							"getProductByExternalReferenceCodeShippingConfiguration"));
					put(
						"query#Catalog.skuByExternalReferenceCode",
						new ObjectValuePair<>(
							SkuResourceImpl.class,
							"getSkuByExternalReferenceCode"));
					put(
						"query#Catalog.productByExternalReferenceCodeByVersion",
						new ObjectValuePair<>(
							ProductResourceImpl.class,
							"getProductByExternalReferenceCodeByVersion"));
					put(
						"query#Catalog.productByExternalReferenceCodeAttachments",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"getProductByExternalReferenceCodeAttachmentsPage"));
					put(
						"query#Catalog.optionByExternalReferenceCodeOptionValues",
						new ObjectValuePair<>(
							OptionValueResourceImpl.class,
							"getOptionByExternalReferenceCodeOptionValuesPage"));
					put(
						"query#Catalog.productGroupByExternalReferenceCodeProductGroupProducts",
						new ObjectValuePair<>(
							ProductGroupProductResourceImpl.class,
							"getProductGroupByExternalReferenceCodeProductGroupProductsPage"));
					put(
						"query#Catalog.productByExternalReferenceCodeProductOptions",
						new ObjectValuePair<>(
							ProductOptionResourceImpl.class,
							"getProductByExternalReferenceCodeProductOptionsPage"));
					put(
						"query#Catalog.productByExternalReferenceCodeRelatedProducts",
						new ObjectValuePair<>(
							RelatedProductResourceImpl.class,
							"getProductByExternalReferenceCodeRelatedProductsPage"));
					put(
						"query#Catalog.productByExternalReferenceCodeMappedProductBySequence",
						new ObjectValuePair<>(
							MappedProductResourceImpl.class,
							"getProductByExternalReferenceCodeMappedProductBySequence"));
					put(
						"query#Catalog.productByExternalReferenceCodeTaxConfiguration",
						new ObjectValuePair<>(
							ProductTaxConfigurationResourceImpl.class,
							"getProductByExternalReferenceCodeTaxConfiguration"));
					put(
						"query#Catalog.productByExternalReferenceCodeImages",
						new ObjectValuePair<>(
							AttachmentResourceImpl.class,
							"getProductByExternalReferenceCodeImagesPage"));
					put(
						"query#Catalog.productByExternalReferenceCodePins",
						new ObjectValuePair<>(
							PinResourceImpl.class,
							"getProductByExternalReferenceCodePinsPage"));
					put(
						"query#Catalog.optionByExternalReferenceCode",
						new ObjectValuePair<>(
							OptionResourceImpl.class,
							"getOptionByExternalReferenceCode"));
					put(
						"query#Catalog.productGroupByExternalReferenceCode",
						new ObjectValuePair<>(
							ProductGroupResourceImpl.class,
							"getProductGroupByExternalReferenceCode"));
					put(
						"query#Catalog.productByExternalReferenceCodeSubscriptionConfiguration",
						new ObjectValuePair<>(
							ProductSubscriptionConfigurationResourceImpl.class,
							"getProductByExternalReferenceCodeSubscriptionConfiguration"));
					put(
						"query#Catalog.skuByExternalReferenceCodeSkuUnitOfMeasures",
						new ObjectValuePair<>(
							SkuUnitOfMeasureResourceImpl.class,
							"getSkuByExternalReferenceCodeSkuUnitOfMeasuresPage"));
					put(
						"query#Catalog.productByExternalReferenceCodeProductVirtualSettings",
						new ObjectValuePair<>(
							ProductVirtualSettingsResourceImpl.class,
							"getProductByExternalReferenceCodeProductVirtualSettings"));
				}
			};

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
	private ComponentServiceObjects<GroupedProductResource>
		_groupedProductResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<LowStockActionResource>
		_lowStockActionResourceComponentServiceObjects;

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
	private ComponentServiceObjects<SkuUnitOfMeasureResource>
		_skuUnitOfMeasureResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SpecificationResource>
		_specificationResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<LinkedProductResource>
		_linkedProductResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductVirtualSettingsResource>
		_productVirtualSettingsResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SkuSubscriptionConfigurationResource>
		_skuSubscriptionConfigurationResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SkuVirtualSettingsResource>
		_skuVirtualSettingsResourceComponentServiceObjects;

}