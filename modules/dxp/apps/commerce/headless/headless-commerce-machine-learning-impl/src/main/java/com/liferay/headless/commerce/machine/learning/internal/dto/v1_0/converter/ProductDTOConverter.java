/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CommerceChannelRelLocalService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeRegistry;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.Product;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.ProductSpecification;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.Sku;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.product.model.CPDefinition",
	service = DTOConverter.class
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
		CPType cpType = _cpTypeRegistry.getCPType(
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

						return commerceChannel.getCommerceChannelId();
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
	private CPTypeRegistry _cpTypeRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter.ProductOptionDTOConverter)"
	)
	private DTOConverter<CPDefinitionOptionRel, ProductOption>
		_productOptionDTOConverter;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter.ProductSpecificationDTOConverter)"
	)
	private DTOConverter
		<CPDefinitionSpecificationOptionValue, ProductSpecification>
			_productSpecificationDTOConverter;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter.SkuDTOConverter)"
	)
	private DTOConverter<CPInstance, Sku> _skuDTOConverter;

}