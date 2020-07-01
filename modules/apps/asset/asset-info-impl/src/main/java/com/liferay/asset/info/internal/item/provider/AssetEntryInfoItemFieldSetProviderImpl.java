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
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		return _getInfoFieldSet(
			assetEntry.getClassName(), assetEntry.getClassTypeId(),
			assetEntry.getGroupId(), assetEntry);
	}

	@Override
	public InfoFieldSet getInfoFieldSet(String itemClassName) {
		return InfoFieldSet.builder(
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "categorization")
		).name(
			"categorization"
		).add(
			_categoriesInfoField
		).add(
			_tagsInfoField
		).add(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				AssetEntry.class.getName())
		).build();
	}

	@Override
	public InfoFieldSet getInfoFieldSet(
		String itemClassName, long itemClassTypeId, long scopeGroupId) {

		return _getInfoFieldSet(
			itemClassName, itemClassTypeId, scopeGroupId, null);
	}

	@Override
	public List<InfoFieldValue<Object>> getInfoFieldValues(
		AssetEntry assetEntry) {

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		Set<AssetVocabulary> assetVocabularies = _getAssetVocabularies(
			assetEntry);

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			infoFieldValues.add(
				new InfoFieldValue<>(
					new InfoField(
						TextInfoFieldType.INSTANCE,
						InfoLocalizedValue.<String>builder(
						).putAll(
							assetVocabulary.getTitleMap()
						).build(),
						assetVocabulary.getName()),
					() -> _getAssetCategoryNames(
						_filterByVocabularyId(
							assetEntry.getCategories(),
							assetVocabulary.getVocabularyId()))));
		}

		infoFieldValues.add(
			new InfoFieldValue<>(
				_categoriesInfoField,
				() -> _getAssetCategoryNames(assetEntry.getCategories())));
		infoFieldValues.add(
			new InfoFieldValue<>(
				_tagsInfoField,
				() -> ListUtil.toString(
					assetEntry.getTags(), AssetTag.NAME_ACCESSOR)));
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

	private List<AssetCategory> _filterByVocabularyId(
		List<AssetCategory> assetCategories, long vocabularyId) {

		return ListUtil.filter(
			assetCategories,
			assetCategory -> assetCategory.getVocabularyId() == vocabularyId);
	}

	private String _getAssetCategoryNames(List<AssetCategory> assetCategories) {
		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		Stream<AssetCategory> stream = assetCategories.stream();

		return stream.map(
			assetCategory -> {
				String title = assetCategory.getTitle(locale);

				if (Validator.isNull(title)) {
					return assetCategory.getName();
				}

				return title;
			}
		).collect(
			Collectors.joining(StringPool.COMMA_AND_SPACE)
		);
	}

	private Set<AssetVocabulary> _getAssetVocabularies(AssetEntry assetEntry) {
		List<AssetCategory> assetCategories = assetEntry.getCategories();
		Set<AssetVocabulary> assetVocabularies = new HashSet<>();

		for (AssetCategory assetCategory : assetCategories) {
			AssetVocabulary assetVocabulary =
				_assetVocabularyLocalService.fetchAssetVocabulary(
					assetCategory.getVocabularyId());

			assetVocabularies.add(assetVocabulary);
		}

		return assetVocabularies;
	}

	private List<AssetVocabulary> _getAssetVocabularies(
			String itemClassName, long itemClassTypeId, long scopeGroupId,
			AssetEntry assetEntry)
		throws PortalException {

		List<AssetVocabulary> assetVocabularies;

		if (itemClassTypeId > 0) {
			assetVocabularies =
				_assetVocabularyLocalService.getGroupsVocabularies(
					_portal.getCurrentAndAncestorSiteGroupIds(scopeGroupId),
					itemClassName, itemClassTypeId);
		}
		else {
			assetVocabularies =
				_assetVocabularyLocalService.getGroupsVocabularies(
					_portal.getCurrentAndAncestorSiteGroupIds(scopeGroupId),
					itemClassName);
		}

		if (assetEntry != null) {
			assetVocabularies.addAll(_getAssetVocabularies(assetEntry));
		}

		return assetVocabularies;
	}

	private List<InfoFieldSetEntry> _getAssetVocabulariesInfoFieldsCollect(
			String itemClassName, long itemClassTypeId, long scopeGroupId,
			AssetEntry assetEntry)
		throws PortalException {

		List<AssetVocabulary> assetVocabularies = _getAssetVocabularies(
			itemClassName, itemClassTypeId, scopeGroupId, assetEntry);

		Stream<AssetVocabulary> stream = assetVocabularies.stream();

		return stream.map(
			assetVocabulary -> new InfoField(
				TextInfoFieldType.INSTANCE,
				InfoLocalizedValue.<String>builder(
				).putAll(
					assetVocabulary.getTitleMap()
				).build(),
				assetVocabulary.getName())
		).collect(
			Collectors.toList()
		);
	}

	private InfoFieldSet _getInfoFieldSet(
		String itemClassName, long itemClassTypeId, long scopeGroupId,
		AssetEntry assetEntry) {

		try {
			return InfoFieldSet.builder(
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(getClass(), "categorization")
			).name(
				"categorization"
			).addAll(
				_getAssetVocabulariesInfoFieldsCollect(
					itemClassName, itemClassTypeId, scopeGroupId, assetEntry)
			).add(
				_categoriesInfoField
			).add(
				_tagsInfoField
			).add(
				_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
					AssetEntry.class.getName())
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(getClass(), "asset")
			).name(
				"asset"
			).build();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	private final InfoField _categoriesInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "all-categories"),
		"categories");

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private Portal _portal;

	private final InfoField _tagsInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "tags"), "tagNames");

}