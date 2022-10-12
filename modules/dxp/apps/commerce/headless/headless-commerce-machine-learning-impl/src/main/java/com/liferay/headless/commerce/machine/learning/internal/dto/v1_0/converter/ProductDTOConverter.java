/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CommerceChannelRelLocalService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.Product;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.ProductSpecification;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.Sku;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.product.model.CPDefinition",
	service = {DTOConverter.class, ProductDTOConverter.class}
)
public class ProductDTOConverter
	implements DTOConverter<CPDefinition, Product> {

	@Override
	public String getContentType() {
		return Product.class.getSimpleName();
	}

	@Override
	public CPDefinition getObject(String externalReferenceCode)
		throws Exception {

		return _cpDefinitionLocalService.fetchCPDefinition(
			GetterUtil.getLong(externalReferenceCode));
	}

	@Override
	public Product toDTO(
			DTOConverterContext dtoConverterContext, CPDefinition cpDefinition)
		throws Exception {

		if (cpDefinition == null) {
			return null;
		}

		CommerceCatalog commerceCatalog = cpDefinition.getCommerceCatalog();
		CProduct cProduct = cpDefinition.getCProduct();
		CPType cpType = _cpTypeServicesTracker.getCPType(
			cpDefinition.getProductTypeName());
		ExpandoBridge expandoBridge = cpDefinition.getExpandoBridge();

		return new Product() {
			{
				catalogId = commerceCatalog.getCommerceCatalogId();
				categoryIds = TransformUtil.transformToArray(
					_assetCategoryLocalService.getCategories(
						cpDefinition.getModelClassName(),
						cpDefinition.getCPDefinitionId()),
					AssetCategory::getCategoryId, Long.class);
				createDate = cpDefinition.getCreateDate();
				customFields = expandoBridge.getAttributes();
				description = LanguageUtils.getLanguageIdMap(
					cpDefinition.getDescriptionMap());
				displayDate = cpDefinition.getDisplayDate();
				expirationDate = cpDefinition.getExpirationDate();
				externalReferenceCode = cProduct.getExternalReferenceCode();
				id = cpDefinition.getCPDefinitionId();
				metaDescription = LanguageUtils.getLanguageIdMap(
					cpDefinition.getMetaDescriptionMap());
				metaKeyword = LanguageUtils.getLanguageIdMap(
					cpDefinition.getMetaKeywordsMap());
				metaTitle = LanguageUtils.getLanguageIdMap(
					cpDefinition.getMetaTitleMap());
				modifiedDate = cpDefinition.getModifiedDate();
				name = LanguageUtils.getLanguageIdMap(
					cpDefinition.getNameMap());
				productChannelIds = TransformUtil.transformToArray(
					_commerceChannelRelLocalService.getCommerceChannelRels(
						cpDefinition.getModelClassName(),
						cpDefinition.getCPDefinitionId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null),
					commerceChannelRel -> {
						CommerceChannel commerceChannel =
							commerceChannelRel.getCommerceChannel();

						return commerceChannel.getGroupId();
					},
					Long.class);
				productId = cProduct.getCProductId();
				productOptions = TransformUtil.transformToArray(
					cpDefinition.getCPDefinitionOptionRels(),
					cpDefinitionOptionRel -> _productOptionDTOConverter.toDTO(
						cpDefinitionOptionRel),
					ProductOption.class);
				productSpecifications = TransformUtil.transformToArray(
					cpDefinition.getCPDefinitionSpecificationOptionValues(),
					cpDefinitionSpecificationOptionValue ->
						_productSpecificationDTOConverter.toDTO(
							cpDefinitionSpecificationOptionValue),
					ProductSpecification.class);
				productType = cpType.getName();
				skus = TransformUtil.transformToArray(
					cpDefinition.getCPInstances(),
					cpInstance -> _skuDTOConverter.toDTO(cpInstance),
					Sku.class);
				status = cpDefinition.getStatus();
				subscriptionEnabled = cpDefinition.isSubscriptionEnabled();
				tags = TransformUtil.transformToArray(
					_assetTagLocalService.getTags(
						CPDefinition.class.getName(),
						cpDefinition.getCPDefinitionId()),
					AssetTag::getName, String.class);
				urls = LanguageUtils.getLanguageIdMap(
					cpDefinition.getUrlTitleMap());
			}
		};
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CommerceChannelRelLocalService _commerceChannelRelLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPTypeServicesTracker _cpTypeServicesTracker;

	@Reference
	private ProductOptionDTOConverter _productOptionDTOConverter;

	@Reference
	private ProductSpecificationDTOConverter _productSpecificationDTOConverter;

	@Reference
	private SkuDTOConverter _skuDTOConverter;

}