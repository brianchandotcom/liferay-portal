/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.search.spi.model.index.contributor;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = DocumentContributor.class)
public class AssetCategoryDocumentContributor
	implements DocumentContributor<AssetCategory> {

	@Override
	public void contribute(
		Document document, BaseModel<AssetCategory> baseModel) {

		String className = document.get(Field.ENTRY_CLASS_NAME);
		long classPK = GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK));

		if (Validator.isNull(className) || (classPK <= 0)) {
			return;
		}

		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getCategories(className, classPK);

		Map<Integer, Map<Long, List<AssetCategory>>>
			assetVocabularyVisibilityTypeMap =
				_getAssetVocabularyVisibilityTypeMap(assetCategories);

		_addAssetCategoriesFields(
			document, Field.ASSET_CATEGORY_IDS, Field.ASSET_CATEGORY_TITLES,
			Field.ASSET_VOCABULARY_IDS,
			assetVocabularyVisibilityTypeMap.get(
				AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC));
		_addAssetCategoriesFields(
			document, Field.ASSET_INTERNAL_CATEGORY_IDS,
			Field.ASSET_INTERNAL_CATEGORY_TITLES,
			Field.ASSET_INTERNAL_VOCABULARY_IDS,
			assetVocabularyVisibilityTypeMap.get(
				AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL));
		_addAssetVocabularyCategoriesFields(
			document, "assetVocabularyCategoryIds",
			"groupAssetVocabularyCategoryExternalReferenceCodes",
			assetVocabularyVisibilityTypeMap.get(
				AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC));
	}

	private void _addAssetCategoriesFields(
		Document document, String assetCategoryIdsFieldName,
		String assetCategoryTitlesFieldName, String assetVocabularyIdsFieldName,
		Map<Long, List<AssetCategory>> assetVocabularyMap) {

		List<AssetCategory> assetCategories = new ArrayList<>();
		long[] assetVocabularyIds = {};

		if (MapUtil.isNotEmpty(assetVocabularyMap)) {
			for (Map.Entry<Long, List<AssetCategory>> entry :
					assetVocabularyMap.entrySet()) {

				assetCategories.addAll(entry.getValue());

				assetVocabularyIds = ArrayUtil.append(
					assetVocabularyIds, entry.getKey());
			}
		}

		long[] assetCategoryIds = ListUtil.toLongArray(
			assetCategories, AssetCategory.CATEGORY_ID_ACCESSOR);

		document.addKeyword(assetCategoryIdsFieldName, assetCategoryIds);

		document.addKeyword(assetVocabularyIdsFieldName, assetVocabularyIds);

		_addAssetCategoryTitles(
			document, assetCategoryTitlesFieldName, assetCategories);
	}

	private void _addAssetCategoryTitles(
		Document document, String field, List<AssetCategory> assetCategories) {

		Map<Locale, List<String>> assetCategoryTitles = new HashMap<>();

		for (AssetCategory assetCategory : assetCategories) {
			Map<Locale, String> titleMap = assetCategory.getTitleMap();

			for (Map.Entry<Locale, String> entry : titleMap.entrySet()) {
				String title = entry.getValue();

				if (Validator.isNull(title)) {
					continue;
				}

				Locale locale = entry.getKey();

				List<String> titles = assetCategoryTitles.computeIfAbsent(
					locale, k -> new ArrayList<>());

				titles.add(StringUtil.toLowerCase(title));
			}
		}

		for (Map.Entry<Locale, List<String>> entry :
				assetCategoryTitles.entrySet()) {

			Locale locale = entry.getKey();

			List<String> titles = entry.getValue();

			String[] titlesArray = titles.toArray(new String[0]);

			document.addText(
				StringBundler.concat(field, StringPool.UNDERLINE, locale),
				titlesArray);
		}
	}

	private void _addAssetVocabularyCategoriesFields(
		Document document, String assetVocabularyCategoryIdsFieldName,
		String groupAssetVocabularyCategoryERCFieldName,
		Map<Long, List<AssetCategory>> assetVocabularyMap) {

		String[] assetVocabularyCategories = {};
		String[] assetVocabularyCategoryERCs = {};

		if (MapUtil.isNotEmpty(assetVocabularyMap)) {
			for (Map.Entry<Long, List<AssetCategory>> entry :
					assetVocabularyMap.entrySet()) {

				assetVocabularyCategories = ArrayUtil.append(
					assetVocabularyCategories,
					TransformUtil.transformToArray(
						entry.getValue(),
						assetCategory ->
							assetCategory.getVocabularyId() + StringPool.DASH +
								assetCategory.getCategoryId(),
						String.class));

				assetVocabularyCategoryERCs = ArrayUtil.append(
					assetVocabularyCategoryERCs,
					TransformUtil.transformToArray(
						entry.getValue(),
						assetCategory ->
							_getAssetVocabularyCategoryExternalReferenceCodes(
								assetCategory),
						String.class));
			}
		}

		document.addKeyword(
			assetVocabularyCategoryIdsFieldName, assetVocabularyCategories);
		document.addKeyword(
			groupAssetVocabularyCategoryERCFieldName,
			assetVocabularyCategoryERCs);
	}

	private String _getAssetVocabularyCategoryExternalReferenceCodes(
			AssetCategory assetCategory)
		throws Exception {

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.getAssetVocabulary(
				assetCategory.getVocabularyId());

		Group group = _groupLocalService.getGroup(assetCategory.getGroupId());

		return StringBundler.concat(
			group.getExternalReferenceCode(), _DELIMITER,
			assetVocabulary.getExternalReferenceCode(), _DELIMITER,
			assetCategory.getExternalReferenceCode());
	}

	private Map<Integer, Map<Long, List<AssetCategory>>>
		_getAssetVocabularyVisibilityTypeMap(
			List<AssetCategory> assetCategories) {

		Map<Integer, Map<Long, List<AssetCategory>>>
			assetVocabularyVisibilityTypeMap = new HashMap<>();
		Map<Long, Integer> assetVocabularyMap = new HashMap<>();

		for (AssetCategory assetCategory : assetCategories) {
			Integer visibilityType = assetVocabularyMap.computeIfAbsent(
				assetCategory.getVocabularyId(),
				vocabularyId -> {
					AssetVocabulary assetVocabulary =
						_assetVocabularyLocalService.fetchAssetVocabulary(
							assetCategory.getVocabularyId());

					return assetVocabulary.getVisibilityType();
				});

			Map<Long, List<AssetCategory>> assetVocabularyAssetCategoriesMap =
				assetVocabularyVisibilityTypeMap.computeIfAbsent(
					visibilityType, key -> new HashMap<>());

			List<AssetCategory> assetVocabularyAssetCategories =
				assetVocabularyAssetCategoriesMap.computeIfAbsent(
					assetCategory.getVocabularyId(), key -> new ArrayList<>());

			assetVocabularyAssetCategories.add(assetCategory);
		}

		return assetVocabularyVisibilityTypeMap;
	}

	private static final String _DELIMITER =
		StringPool.AMPERSAND + StringPool.AMPERSAND;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}