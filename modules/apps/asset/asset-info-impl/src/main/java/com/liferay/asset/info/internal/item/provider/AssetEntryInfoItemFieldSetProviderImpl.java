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
import java.util.Collection;
import java.util.Collections;
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
		return _getInfoFieldSet(_getNonSystemAssetVocabularies(assetEntry));
	}

	@Override
	public InfoFieldSet getInfoFieldSet(String itemClassName) {
		return _getInfoFieldSet(Collections.emptyList());
	}

	@Override
	public InfoFieldSet getInfoFieldSet(
		String itemClassName, long itemClassTypeId, long scopeGroupId) {

		return _getInfoFieldSet(
			_getNonSystemAssetVocabularies(
				itemClassName, itemClassTypeId, scopeGroupId));
	}

	@Override
	public List<InfoFieldValue<Object>> getInfoFieldValues(
		AssetEntry assetEntry) {

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		Set<AssetVocabulary> assetVocabularies = _getNonSystemAssetVocabularies(
			assetEntry);

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			infoFieldValues.add(
				new InfoFieldValue<>(
					InfoField.builder(
					).infoFieldType(
						TextInfoFieldType.INSTANCE
					).name(
						assetVocabulary.getName()
					).labelInfoLocalizedValue(
						InfoLocalizedValue.<String>builder(
						).values(
							assetVocabulary.getTitleMap()
						).build()
					).build(),
					() -> _getAssetCategoryNames(
						_filterByVocabularyId(
							assetEntry.getCategories(),
							assetVocabulary.getVocabularyId()))));
		}

		infoFieldValues.add(
			new InfoFieldValue<>(
				_categoriesInfoField,
				() -> _getAssetCategoryNames(
					_filterBySystem(assetEntry.getCategories()))));
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

	private List<AssetCategory> _filterBySystem(
		List<AssetCategory> assetCategories) {

		return ListUtil.filter(
			assetCategories,
			assetCategory -> {
				AssetVocabulary assetVocabulary =
					_assetVocabularyLocalService.fetchAssetVocabulary(
						assetCategory.getVocabularyId());

				return !assetVocabulary.isSystem();
			});
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

	private InfoFieldSet _getInfoFieldSet(
		Collection<AssetVocabulary> assetVocabularies) {

		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			consumer -> assetVocabularies.forEach(
				assetVocabulary -> consumer.accept(
					InfoField.builder(
					).infoFieldType(
						TextInfoFieldType.INSTANCE
					).name(
						assetVocabulary.getName()
					).labelInfoLocalizedValue(
						InfoLocalizedValue.<String>builder(
						).values(
							assetVocabulary.getTitleMap()
						).build()
					).build()))
		).infoFieldSetEntry(
			_categoriesInfoField
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

	private Set<AssetVocabulary> _getNonSystemAssetVocabularies(
		AssetEntry assetEntry) {

		Set<AssetVocabulary> assetVocabularies = new HashSet<>(
			_getNonSystemAssetVocabularies(
				assetEntry.getClassName(), assetEntry.getClassTypeId(),
				assetEntry.getGroupId()));

		for (AssetCategory assetCategory : assetEntry.getCategories()) {
			AssetVocabulary assetVocabulary =
				_assetVocabularyLocalService.fetchAssetVocabulary(
					assetCategory.getVocabularyId());

			if (!assetVocabulary.isSystem()) {
				assetVocabularies.add(assetVocabulary);
			}
		}

		return assetVocabularies;
	}

	private List<AssetVocabulary> _getNonSystemAssetVocabularies(
		String itemClassName, long itemClassTypeId, long scopeGroupId) {

		try {
			if (itemClassTypeId > 0) {
				List<AssetVocabulary> groupsVocabularies =
					_assetVocabularyLocalService.getGroupsVocabularies(
						_portal.getCurrentAndAncestorSiteGroupIds(scopeGroupId),
						itemClassName, itemClassTypeId);

				return ListUtil.filter(
					groupsVocabularies,
					assetVocabulary -> !assetVocabulary.isSystem());
			}

			List<AssetVocabulary> groupsVocabularies =
				_assetVocabularyLocalService.getGroupsVocabularies(
					_portal.getCurrentAndAncestorSiteGroupIds(scopeGroupId),
					itemClassName);

			return ListUtil.filter(
				groupsVocabularies,
				assetVocabulary -> !assetVocabulary.isSystem());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	private final InfoField<TextInfoFieldType> _categoriesInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"categories"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "all-categories")
		).build();

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private Portal _portal;

	private final InfoField<TextInfoFieldType> _tagsInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"tagNames"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "tags")
		).build();

}