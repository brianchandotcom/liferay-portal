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

package com.liferay.headless.admin.taxonomy.internal.dto.v1_0.converter;

import com.liferay.asset.category.property.model.AssetCategoryProperty;
import com.liferay.asset.category.property.service.AssetCategoryPropertyLocalService;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.headless.admin.taxonomy.dto.v1_0.ParentTaxonomyCategory;
import com.liferay.headless.admin.taxonomy.dto.v1_0.ParentTaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyCategoryProperty;
import com.liferay.headless.admin.taxonomy.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
@Component(
	property = "dto.class.name=com.liferay.asset.kernel.model.AssetCategory",
	service = {DTOConverter.class, TaxonomyCategoryDTOConverter.class}
)
public class TaxonomyCategoryDTOConverter
	implements DTOConverter<AssetCategory, TaxonomyCategory> {

	@Override
	public String getContentType() {
		return TaxonomyCategory.class.getSimpleName();
	}

	@Override
	public TaxonomyCategory toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		AssetCategory assetCategory =
			_assetCategoryLocalService.getAssetCategory(
				(Long)dtoConverterContext.getId());

		return _toTaxonomyCategory(dtoConverterContext, assetCategory);
	}

	@Override
	public TaxonomyCategory toDTO(
			DTOConverterContext dtoConverterContext,
			AssetCategory assetCategory)
		throws Exception {

		return _toTaxonomyCategory(dtoConverterContext, assetCategory);
	}

	private ParentTaxonomyCategory _toParentTaxonomyCategory(
		AssetCategory parentAssetCategory,
		DTOConverterContext dtoConverterContext) {

		return new ParentTaxonomyCategory() {
			{
				id = parentAssetCategory.getCategoryId();
				name = parentAssetCategory.getTitle(
					dtoConverterContext.getLocale());
			}
		};
	}

	private TaxonomyCategory _toTaxonomyCategory(
			DTOConverterContext dtoConverterContext,
			AssetCategory assetCategory)
		throws Exception {

		return new TaxonomyCategory() {
			{
				actions = dtoConverterContext.getActions();
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					assetCategory.getAvailableLanguageIds());
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.fetchUser(assetCategory.getUserId()));
				dateCreated = assetCategory.getCreateDate();
				dateModified = assetCategory.getModifiedDate();
				description = assetCategory.getDescription(
					dtoConverterContext.getLocale());
				description_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					assetCategory.getDescriptionMap());
				externalReferenceCode =
					assetCategory.getExternalReferenceCode();
				id = String.valueOf(assetCategory.getCategoryId());
				name = assetCategory.getTitle(dtoConverterContext.getLocale());
				name_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					assetCategory.getTitleMap());
				numberOfTaxonomyCategories =
					_assetCategoryService.getChildCategoriesCount(
						assetCategory.getCategoryId());
				parentTaxonomyVocabulary = new ParentTaxonomyVocabulary() {
					{
						id = assetCategory.getVocabularyId();

						setName(
							() -> {
								if (assetCategory.getVocabularyId() == 0) {
									return null;
								}

								AssetVocabulary assetVocabulary =
									_assetVocabularyService.getVocabulary(
										assetCategory.getVocabularyId());

								return assetVocabulary.getTitle(
									dtoConverterContext.getLocale());
							});
					}
				};
				siteId = assetCategory.getGroupId();
				taxonomyCategoryProperties = TransformUtil.transformToArray(
					_assetCategoryPropertyLocalService.getCategoryProperties(
						assetCategory.getCategoryId()),
					assetCategoryProperties -> _toTaxonomyCategoryProperty(
						assetCategoryProperties),
					TaxonomyCategoryProperty.class);
				taxonomyCategoryUsageCount =
					(int)_assetEntryLocalService.searchCount(
						assetCategory.getCompanyId(),
						new long[] {assetCategory.getGroupId()},
						assetCategory.getUserId(), null, -1, null,
						String.valueOf(assetCategory.getCategoryId()), null,
						false, false,
						new int[] {
							WorkflowConstants.STATUS_APPROVED,
							WorkflowConstants.STATUS_PENDING,
							WorkflowConstants.STATUS_SCHEDULED
						},
						false);
				taxonomyVocabularyId = assetCategory.getVocabularyId();

				setParentTaxonomyCategory(
					() -> {
						if (assetCategory.getParentCategory() == null) {
							return null;
						}

						return _toParentTaxonomyCategory(
							assetCategory.getParentCategory(),
							dtoConverterContext);
					});
			}
		};
	}

	private TaxonomyCategoryProperty _toTaxonomyCategoryProperty(
		AssetCategoryProperty assetCategoryProperty) {

		return new TaxonomyCategoryProperty() {
			{
				key = assetCategoryProperty.getKey();
				value = assetCategoryProperty.getValue();
			}
		};
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetCategoryPropertyLocalService
		_assetCategoryPropertyLocalService;

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}