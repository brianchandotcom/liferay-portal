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

package com.liferay.asset.info.internal.item.provider;

import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.exception.NoSuchEntryException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.CategoriesInfoFieldType;
import com.liferay.info.field.type.TagsInfoFieldType;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.type.categorization.Category;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = AssetEntryInfoItemFieldSetProvider.class)
public class AssetEntryInfoItemFieldSetProviderImpl
	implements AssetEntryInfoItemFieldSetProvider {

	@Override
	public InfoFieldSet getInfoFieldSet(AssetEntry assetEntry) {
		return _getInfoFieldSet(_getNoninternalAssetVocabularies(assetEntry));
	}

	@Override
	public InfoFieldSet getInfoFieldSet(String itemClassName) {
		return _getInfoFieldSet(Collections.emptyList());
	}

	@Override
	public InfoFieldSet getInfoFieldSet(
		String itemClassName, long itemClassTypeId, long scopeGroupId) {

		return _getInfoFieldSet(
			_getNoninternalAssetVocabularies(
				itemClassName, itemClassTypeId, scopeGroupId));
	}

	@Override
	public List<InfoFieldValue<Object>> getInfoFieldValues(
		AssetEntry assetEntry) {

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		Set<AssetVocabulary> assetVocabularies =
			_getNoninternalAssetVocabularies(assetEntry);

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			infoFieldValues.add(
				new InfoFieldValue<>(
					InfoField.builder(
					).infoFieldType(
						CategoriesInfoFieldType.INSTANCE
					).name(
						assetVocabulary.getName()
					).labelInfoLocalizedValue(
						InfoLocalizedValue.<String>builder(
						).values(
							assetVocabulary.getTitleMap()
						).build()
					).build(),
					() -> _getCategories(
						_filterByVocabularyId(
							assetEntry.getCategories(),
							assetVocabulary.getVocabularyId()))));
		}

		infoFieldValues.add(
			new InfoFieldValue<>(
				_categoriesInfoField,
				() -> _getCategories(
					_filterByVisibilityType(assetEntry.getCategories()))));
		infoFieldValues.add(
			new InfoFieldValue<>(
				_tagsInfoField, () -> _getTags(assetEntry.getTags())));
		infoFieldValues.addAll(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
				AssetEntry.class.getName(), assetEntry));

		return infoFieldValues;
	}

	@Override
	public List<InfoFieldValue<Object>> getInfoFieldValues(
			String itemClassName, long itemClassPK)
		throws NoSuchInfoItemException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				itemClassName);

		try {
			AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
				itemClassName, itemClassPK);

			return getInfoFieldValues(assetEntry);
		}
		catch (NoSuchEntryException noSuchEntryException) {
			throw new NoSuchInfoItemException(
				StringBundler.concat(
					"Unable to get asset entry with class name ", itemClassName,
					" and class PK ", itemClassPK),
				noSuchEntryException);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private List<AssetCategory> _filterByVisibilityType(
		List<AssetCategory> assetCategories) {

		return ListUtil.filter(
			assetCategories,
			assetCategory -> {
				AssetVocabulary assetVocabulary =
					_assetVocabularyLocalService.fetchAssetVocabulary(
						assetCategory.getVocabularyId());

				return !(assetVocabulary.getVisibilityType() ==
					AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL);
			});
	}

	private List<AssetCategory> _filterByVocabularyId(
		List<AssetCategory> assetCategories, long vocabularyId) {

		return ListUtil.filter(
			assetCategories,
			assetCategory -> assetCategory.getVocabularyId() == vocabularyId);
	}

	private Set<AssetVocabulary> _getAssetVocabularies(AssetEntry assetEntry) {
		Set<AssetVocabulary> assetVocabularies = new HashSet<>(
			_getAssetVocabularies(
				assetEntry.getClassName(), assetEntry.getClassTypeId(),
				assetEntry.getGroupId()));

		for (AssetCategory assetCategory : assetEntry.getCategories()) {
			assetVocabularies.add(
				_assetVocabularyLocalService.fetchAssetVocabulary(
					assetCategory.getVocabularyId()));
		}

		return assetVocabularies;
	}

	private List<AssetVocabulary> _getAssetVocabularies(
		String itemClassName, long itemClassTypeId, long scopeGroupId) {

		try {
			if (itemClassTypeId > 0) {
				return _assetVocabularyLocalService.getGroupsVocabularies(
					_portal.getCurrentAndAncestorSiteGroupIds(scopeGroupId),
					itemClassName, itemClassTypeId);
			}

			return _assetVocabularyLocalService.getGroupsVocabularies(
				_portal.getCurrentAndAncestorSiteGroupIds(scopeGroupId),
				itemClassName);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private List<Category> _getCategories(List<AssetCategory> assetCategories) {
		List<Category> categories = new ArrayList<>(assetCategories.size());

		for (AssetCategory assetCategory : assetCategories) {
			categories.add(_getCategory(assetCategory));
		}

		return categories;
	}

	private Category _getCategory(AssetCategory assetCategory) {
		return new Category(
			assetCategory.getName(),
			(InfoLocalizedValue<String>)InfoLocalizedValue.function(
				assetCategory::getTitle));
	}

	private InfoFieldSet _getInfoFieldSet(
		Collection<AssetVocabulary> assetVocabularies) {

		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			_categoriesInfoField
		).infoFieldSetEntry(
			consumer -> assetVocabularies.forEach(
				assetVocabulary -> consumer.accept(
					InfoField.builder(
					).infoFieldType(
						CategoriesInfoFieldType.INSTANCE
					).name(
						assetVocabulary.getName()
					).labelInfoLocalizedValue(
						InfoLocalizedValue.<String>builder(
						).values(
							assetVocabulary.getTitleMap()
						).build()
					).build()))
		).infoFieldSetEntry(
			_tagsInfoField
		).infoFieldSetEntry(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				AssetEntry.class.getName())
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "categorization")
		).name(
			"categorization"
		).build();
	}

	private Set<AssetVocabulary> _getNoninternalAssetVocabularies(
		AssetEntry assetEntry) {

		Set<AssetVocabulary> assetVocabularies = new HashSet<>(
			_getNoninternalAssetVocabularies(
				assetEntry.getClassName(), assetEntry.getClassTypeId(),
				assetEntry.getGroupId()));

		for (AssetCategory assetCategory : assetEntry.getCategories()) {
			AssetVocabulary assetVocabulary =
				_assetVocabularyLocalService.fetchAssetVocabulary(
					assetCategory.getVocabularyId());

			if (!(assetVocabulary.getVisibilityType() ==
					AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL)) {

				assetVocabularies.add(assetVocabulary);
			}
		}

		return assetVocabularies;
	}

	private List<AssetVocabulary> _getNoninternalAssetVocabularies(
		String itemClassName, long itemClassTypeId, long scopeGroupId) {

		try {
			if (itemClassTypeId > 0) {
				List<AssetVocabulary> groupsAssetVocabularies =
					_assetVocabularyLocalService.getGroupsVocabularies(
						_portal.getCurrentAndAncestorSiteGroupIds(scopeGroupId),
						itemClassName, itemClassTypeId);

				return ListUtil.filter(
					groupsAssetVocabularies,
					assetVocabulary -> !(assetVocabulary.getVisibilityType() ==
						AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL));
			}

			List<AssetVocabulary> groupsAssetVocabularies =
				_assetVocabularyLocalService.getGroupsVocabularies(
					_portal.getCurrentAndAncestorSiteGroupIds(scopeGroupId),
					itemClassName);

			return ListUtil.filter(
				groupsAssetVocabularies,
				assetVocabulary -> !(assetVocabulary.getVisibilityType() ==
					AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private List<String> _getTags(List<AssetTag> assetTags) {
		List<String> tags = new ArrayList<>(assetTags.size());

		for (AssetTag assetTag : assetTags) {
			tags.add(assetTag.getName());
		}

		return tags;
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	private final InfoField<CategoriesInfoFieldType> _categoriesInfoField =
		InfoField.builder(
		).infoFieldType(
			CategoriesInfoFieldType.INSTANCE
		).name(
			"categories"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "all-categories")
		).multivalued(
			true
		).build();

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private Portal _portal;

	private final InfoField<TagsInfoFieldType> _tagsInfoField =
		InfoField.builder(
		).infoFieldType(
			TagsInfoFieldType.INSTANCE
		).name(
			"tagNames"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "tags")
		).multivalued(
			true
		).build();

}