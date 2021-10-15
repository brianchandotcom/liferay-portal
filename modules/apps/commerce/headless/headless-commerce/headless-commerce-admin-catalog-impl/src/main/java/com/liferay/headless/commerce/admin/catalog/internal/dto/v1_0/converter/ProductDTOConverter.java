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

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Category;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Status;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
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
	public Product toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			(Long)dtoConverterContext.getId());

		CProduct cProduct = cpDefinition.getCProduct();

		ExpandoBridge expandoBridge = cpDefinition.getExpandoBridge();

		Locale locale = dtoConverterContext.getLocale();

		ResourceBundle resourceBundle = LanguageResources.getResourceBundle(
			locale);

		String productStatusLabel = WorkflowConstants.getStatusLabel(
			cpDefinition.getStatus());

		String productStatusLabelI18n = LanguageUtil.get(
			resourceBundle,
			WorkflowConstants.getStatusLabel(cpDefinition.getStatus()));

		CPType cpType = _getCPType(cpDefinition.getProductTypeName());

		return new Product() {
			{
				actions = dtoConverterContext.getActions();
				active = !cpDefinition.isInactive();
				catalogId = _getCommerceCatalogId(cpDefinition);
				categories = TransformUtil.transformToArray(
					_assetCategoryLocalService.getCategories(
						cpDefinition.getModelClassName(),
						cpDefinition.getCPDefinitionId()),
					assetCategory -> _toCategory(assetCategory),
					Category.class);
				createDate = cpDefinition.getCreateDate();
				customFields = CustomFieldsUtil.toCustomFields(
					dtoConverterContext.isAcceptAllLanguages(),
					CPDefinition.class.getName(),
					cpDefinition.getCPDefinitionId(),
					cpDefinition.getCompanyId(),
					dtoConverterContext.getLocale());
				description = LanguageUtils.getLanguageIdMap(
					cpDefinition.getDescriptionMap());
				displayDate = cpDefinition.getDisplayDate();
				expando = expandoBridge.getAttributes();
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
				productAccountGroupFilter =
					cpDefinition.isAccountGroupFilterEnabled();
				productChannelFilter = cpDefinition.isChannelFilterEnabled();
				productId = cProduct.getCProductId();
				productStatus = cpDefinition.getStatus();
				productType = cpType.getName();
				productTypeI18n = cpType.getLabel(locale);
				shortDescription = LanguageUtils.getLanguageIdMap(
					cpDefinition.getShortDescriptionMap());
				skuFormatted = _getSku(
					cpDefinition, dtoConverterContext.getLocale());
				tags = _getTags(cpDefinition);
				thumbnail = cpDefinition.getDefaultImageThumbnailSrc();
				urls = LanguageUtils.getLanguageIdMap(
					_cpDefinitionService.getUrlTitleMap(
						cpDefinition.getCPDefinitionId()));
				workflowStatusInfo = _toStatus(
					cpDefinition.getStatus(), productStatusLabel,
					productStatusLabelI18n);
			}
		};
	}

	private long _getCommerceCatalogId(CPDefinition cpDefinition) {
		CommerceCatalog commerceCatalog = cpDefinition.getCommerceCatalog();

		if (commerceCatalog == null) {
			return 0;
		}

		return commerceCatalog.getCommerceCatalogId();
	}

	private CPType _getCPType(String name) {
		return _cpTypeServicesTracker.getCPType(name);
	}

	private String _getSku(CPDefinition cpDefinition, Locale locale) {
		List<CPInstance> cpInstances = cpDefinition.getCPInstances();

		if (cpInstances.isEmpty()) {
			return StringPool.BLANK;
		}

		if (cpInstances.size() > 1) {
			return LanguageUtil.get(locale, "multiple-skus");
		}

		CPInstance cpInstance = cpInstances.get(0);

		return cpInstance.getSku();
	}

	private String[] _getTags(CPDefinition cpDefinition) {
		List<AssetTag> assetEntryAssetTags = _assetTagService.getTags(
			cpDefinition.getModelClassName(), cpDefinition.getCPDefinitionId());

		Stream<AssetTag> stream = assetEntryAssetTags.stream();

		return stream.map(
			AssetTag::getName
		).toArray(
			String[]::new
		);
	}

	private Category _toCategory(AssetCategory assetCategory) {
		return new Category() {
			{
				externalReferenceCode =
					assetCategory.getExternalReferenceCode();
				id = assetCategory.getCategoryId();
				name = assetCategory.getName();

				setVocabulary(
					() -> {
						AssetVocabulary assetVocabulary =
							_assetVocabularyLocalService.fetchAssetVocabulary(
								assetCategory.getVocabularyId());

						if (assetVocabulary == null) {
							return null;
						}

						return assetVocabulary.getName();
					});
			}
		};
	}

	private Status _toStatus(
		int statusCode, String productStatusLabel,
		String productStatusLabelI18n) {

		return new Status() {
			{
				code = statusCode;
				label = productStatusLabel;
				label_i18n = productStatusLabelI18n;
			}
		};
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagService _assetTagService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPTypeServicesTracker _cpTypeServicesTracker;

}