/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.util;

import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;
import com.liferay.asset.entry.rel.service.AssetEntryAssetCategoryRelLocalService;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class AssetCategorizationLoader {

	public AssetCategorizationLoader(
		AssetCategoryLocalService assetCategoryLocalService,
		AssetEntryAssetCategoryRelLocalService
			assetEntryAssetCategoryRelLocalService,
		AssetEntryLocalService assetEntryLocalService,
		AssetTagLocalService assetTagLocalService,
		AssetVocabularyLocalService assetVocabularyLocalService,
		long classNameId, long companyId, long[] objectEntryIds) {

		_assetCategoryLocalService = assetCategoryLocalService;
		_assetEntryAssetCategoryRelLocalService =
			assetEntryAssetCategoryRelLocalService;
		_assetEntryLocalService = assetEntryLocalService;
		_assetTagLocalService = assetTagLocalService;
		_assetVocabularyLocalService = assetVocabularyLocalService;
		_classNameId = classNameId;
		_companyId = companyId;
		_objectEntryIds = objectEntryIds;
	}

	public List<AssetCategory> getAssetCategories(long objectEntryId) {
		if (_getCompanyCategoriesCount() == 0) {
			return Collections.emptyList();
		}

		AssetEntry assetEntry = _getAssetEntry(objectEntryId);

		if (assetEntry == null) {
			return Collections.emptyList();
		}

		Map<Long, List<AssetCategory>> assetCategoriesMap =
			_getAssetCategoriesMap();

		return assetCategoriesMap.getOrDefault(
			assetEntry.getEntryId(), Collections.emptyList());
	}

	public List<AssetTag> getAssetTags(long objectEntryId) {
		if (_getCompanyTagsCount() == 0) {
			return Collections.emptyList();
		}

		AssetEntry assetEntry = _getAssetEntry(objectEntryId);

		if (assetEntry == null) {
			return Collections.emptyList();
		}

		return _assetTagLocalService.getEntryTags(assetEntry.getEntryId());
	}

	public AssetVocabulary getAssetVocabulary(long vocabularyId)
		throws PortalException {

		Map<Serializable, AssetVocabulary> assetVocabulariesMap =
			_getAssetVocabulariesMap();

		if (assetVocabulariesMap != null) {
			AssetVocabulary assetVocabulary = assetVocabulariesMap.get(
				vocabularyId);

			if (assetVocabulary != null) {
				return assetVocabulary;
			}
		}

		return _assetVocabularyLocalService.getAssetVocabulary(vocabularyId);
	}

	private Map<Long, List<AssetCategory>> _getAssetCategoriesMap() {
		if (_assetCategoriesMap != null) {
			return _assetCategoriesMap;
		}

		Map<Long, List<AssetEntryAssetCategoryRel>>
			assetEntryAssetCategoryRelsMap =
				_assetEntryAssetCategoryRelLocalService.
					getAssetEntryAssetCategoryRelsByAssetEntryIds(
						TransformUtil.transformToLongArray(
							_assetEntriesMap.values(), AssetEntry::getEntryId));

		Set<Serializable> assetCategoryIds = new HashSet<>();

		for (List<AssetEntryAssetCategoryRel> assetEntryAssetCategoryRels :
				assetEntryAssetCategoryRelsMap.values()) {

			for (AssetEntryAssetCategoryRel assetEntryAssetCategoryRel :
					assetEntryAssetCategoryRels) {

				assetCategoryIds.add(
					assetEntryAssetCategoryRel.getAssetCategoryId());
			}
		}

		Map<Serializable, AssetCategory> assetCategories =
			_assetCategoryLocalService.fetchPersistedModels(assetCategoryIds);

		_assetCategoriesMap = new HashMap<>();

		for (Map.Entry<Long, List<AssetEntryAssetCategoryRel>> entry :
				assetEntryAssetCategoryRelsMap.entrySet()) {

			_assetCategoriesMap.put(
				entry.getKey(),
				TransformUtil.transform(
					entry.getValue(),
					assetEntryAssetCategoryRel -> assetCategories.get(
						assetEntryAssetCategoryRel.getAssetCategoryId())));
		}

		return _assetCategoriesMap;
	}

	private AssetEntry _getAssetEntry(long objectEntryId) {
		if (_assetEntriesMap == null) {
			_assetEntriesMap = _assetEntryLocalService.fetchEntries(
				_classNameId, _objectEntryIds);
		}

		return _assetEntriesMap.get(objectEntryId);
	}

	private Map<Serializable, AssetVocabulary> _getAssetVocabulariesMap() {
		if ((_assetVocabulariesMap != null) || (_assetCategoriesMap == null)) {
			return _assetVocabulariesMap;
		}

		Set<Serializable> vocabularyIds = new HashSet<>();

		for (List<AssetCategory> assetCategories :
				_assetCategoriesMap.values()) {

			for (AssetCategory assetCategory : assetCategories) {
				vocabularyIds.add(assetCategory.getVocabularyId());
			}
		}

		_assetVocabulariesMap =
			_assetVocabularyLocalService.fetchPersistedModels(vocabularyIds);

		return _assetVocabulariesMap;
	}

	private int _getCompanyCategoriesCount() {
		if (_companyCategoriesCount == null) {
			_companyCategoriesCount =
				_assetCategoryLocalService.getCompanyCategoriesCount(
					_companyId);
		}

		return _companyCategoriesCount;
	}

	private int _getCompanyTagsCount() {
		if (_companyTagsCount == null) {
			_companyTagsCount = _assetTagLocalService.getCompanyTagsCount(
				_companyId);
		}

		return _companyTagsCount;
	}

	private Map<Long, List<AssetCategory>> _assetCategoriesMap;
	private final AssetCategoryLocalService _assetCategoryLocalService;
	private Map<Long, AssetEntry> _assetEntriesMap;
	private final AssetEntryAssetCategoryRelLocalService
		_assetEntryAssetCategoryRelLocalService;
	private final AssetEntryLocalService _assetEntryLocalService;
	private final AssetTagLocalService _assetTagLocalService;
	private Map<Serializable, AssetVocabulary> _assetVocabulariesMap;
	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final long _classNameId;
	private Integer _companyCategoriesCount;
	private final long _companyId;
	private Integer _companyTagsCount;
	private final long[] _objectEntryIds;

}