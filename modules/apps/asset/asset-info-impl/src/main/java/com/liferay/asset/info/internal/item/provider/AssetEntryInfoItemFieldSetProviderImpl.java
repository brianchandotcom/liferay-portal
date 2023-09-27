/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.info.type.KeyLocalizedLabelPair;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SortedArrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = AssetEntryInfoItemFieldSetProvider.class)
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
					).uniqueId(
						AssetVocabulary.class.getSimpleName() +
							StringPool.UNDERLINE +
								assetVocabulary.getVocabularyId()
					).name(
						assetVocabulary.getName()
					).labelInfoLocalizedValue(
						InfoLocalizedValue.<String>builder(
						).defaultLocale(
							LocaleUtil.fromLanguageId(
								assetVocabulary.getDefaultLanguageId())
						).values(
							assetVocabulary.getTitleMap()
						).build()
					).build(),
					() -> _getKeyLocalizedLabelPairs(
						_filterByVocabularyId(
							assetEntry.getCategories(),
							assetVocabulary.getVocabularyId()))));
		}

		infoFieldValues.add(
			new InfoFieldValue<>(
				_categoriesInfoField,
				() -> _getKeyLocalizedLabelPairs(
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
			return getInfoFieldValues(
				assetRendererFactory.getAssetEntry(itemClassName, itemClassPK));
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

	private InfoFieldSet _getInfoFieldSet(
		Collection<AssetVocabulary> assetVocabularies) {

		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			_categoriesInfoField
		).infoFieldSetEntry(
			unsafeConsumer -> assetVocabularies.forEach(
				assetVocabulary -> unsafeConsumer.accept(
					InfoField.builder(
					).infoFieldType(
						CategoriesInfoFieldType.INSTANCE
					).uniqueId(
						AssetVocabulary.class.getSimpleName() +
							StringPool.UNDERLINE +
								assetVocabulary.getVocabularyId()
					).name(
						assetVocabulary.getName()
					).labelInfoLocalizedValue(
						InfoLocalizedValue.<String>builder(
						).defaultLocale(
							LocaleUtil.fromLanguageId(
								assetVocabulary.getDefaultLanguageId())
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

	private KeyLocalizedLabelPair _getKeyLocalizedLabelPair(
		AssetCategory assetCategory) {

		return new KeyLocalizedLabelPair(
			assetCategory.getName(),
			(InfoLocalizedValue<String>)InfoLocalizedValue.function(
				assetCategory::getTitle));
	}

	private List<KeyLocalizedLabelPair> _getKeyLocalizedLabelPairs(
		List<AssetCategory> assetCategories) {

		List<KeyLocalizedLabelPair> keyLocalizedLabelPairs =
			new SortedArrayList<>(
				Comparator.comparing(KeyLocalizedLabelPair::getKey));

		for (AssetCategory assetCategory : assetCategories) {
			keyLocalizedLabelPairs.add(
				_getKeyLocalizedLabelPair(assetCategory));
		}

		return keyLocalizedLabelPairs;
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
					assetVocabulary ->
						!(assetVocabulary.getVisibilityType() ==
							AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL));
			}

			List<AssetVocabulary> groupsAssetVocabularies =
				_assetVocabularyLocalService.getGroupsVocabularies(
					_portal.getCurrentAndAncestorSiteGroupIds(scopeGroupId),
					itemClassName);

			return ListUtil.filter(
				groupsAssetVocabularies,
				assetVocabulary ->
					!(assetVocabulary.getVisibilityType() ==
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
		).namespace(
			AssetCategory.class.getSimpleName()
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
		).namespace(
			AssetTag.class.getSimpleName()
		).name(
			"tagNames"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "tags")
		).multivalued(
			true
		).build();

}