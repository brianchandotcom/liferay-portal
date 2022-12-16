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

package com.liferay.bulk.rest.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.bulk.rest.dto.v1_0.DocumentBulkSelection;
import com.liferay.bulk.rest.dto.v1_0.TaxonomyCategory;
import com.liferay.bulk.rest.dto.v1_0.TaxonomyVocabulary;
import com.liferay.bulk.rest.internal.selection.v1_0.DocumentBulkSelectionFactory;
import com.liferay.bulk.rest.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alejandro Tardín
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/taxonomy-vocabulary.properties",
	scope = ServiceScope.PROTOTYPE, service = TaxonomyVocabularyResource.class
)
@CTAware
public class TaxonomyVocabularyResourceImpl
	extends BaseTaxonomyVocabularyResourceImpl {

	@Override
	public Page<TaxonomyVocabulary> postSiteTaxonomyVocabulariesCommonPage(
			Long siteId, DocumentBulkSelection documentBulkSelection)
		throws Exception {

		Map<AssetVocabulary, List<AssetCategory>> assetCategoriesMap =
			_getAssetCategoriesMap(siteId, documentBulkSelection);

		return Page.of(
			transform(
				assetCategoriesMap.entrySet(),
				entry -> _toTaxonomyVocabulary(
					entry.getValue(), entry.getKey())));
	}

	private Set<AssetCategory> _getAssetCategories(
			DocumentBulkSelection documentBulkSelection,
			PermissionChecker permissionChecker)
		throws Exception {

		Set<AssetCategory> assetCategories = new HashSet<>();

		AtomicBoolean flag = new AtomicBoolean(true);

		BulkSelection<?> bulkSelection = _documentBulkSelectionFactory.create(
			documentBulkSelection);

		BulkSelection<AssetEntry> assetEntryBulkSelection =
			bulkSelection.toAssetEntryBulkSelection();

		assetEntryBulkSelection.forEach(
			assetEntry -> {
				if (BaseModelPermissionCheckerUtil.containsBaseModelPermission(
						permissionChecker, assetEntry.getGroupId(),
						assetEntry.getClassName(), assetEntry.getClassPK(),
						ActionKeys.UPDATE)) {

					List<AssetCategory> assetEntryAssetCategories =
						_assetCategoryLocalService.getCategories(
							assetEntry.getClassName(), assetEntry.getClassPK());

					if (flag.get()) {
						flag.set(false);

						assetCategories.addAll(assetEntryAssetCategories);
					}
					else {
						assetCategories.retainAll(assetEntryAssetCategories);
					}
				}
			});

		return assetCategories;
	}

	private Map<AssetVocabulary, List<AssetCategory>> _getAssetCategoriesMap(
			Long siteId, DocumentBulkSelection documentBulkSelection)
		throws Exception {

		List<AssetVocabulary> assetVocabularies = _getAssetVocabularies(siteId);

		Set<AssetCategory> assetCategories1 = _getAssetCategories(
			documentBulkSelection,
			PermissionCheckerFactoryUtil.create(contextUser));

		Map<Long, List<AssetCategory>> idAssetCategoriesMap = new HashMap<>();

		for (AssetCategory assetCategory : assetCategories1) {
			List<AssetCategory> assetCategories2 =
				idAssetCategoriesMap.computeIfAbsent(
					assetCategory.getVocabularyId(), key -> new ArrayList<>());

			assetCategories2.add(assetCategory);
		}

		Map<AssetVocabulary, List<AssetCategory>> assetCategoriesMap =
			new HashMap<>();

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			assetCategoriesMap.put(
				assetVocabulary,
				idAssetCategoriesMap.computeIfAbsent(
					assetVocabulary.getVocabularyId(),
					key -> new ArrayList<>()));
		}

		return assetCategoriesMap;
	}

	private List<AssetVocabulary> _getAssetVocabularies(Long siteId)
		throws Exception {

		List<AssetVocabulary> assetVocabularies =
			_assetVocabularyLocalService.getGroupVocabularies(
				_portal.getCurrentAndAncestorSiteGroupIds(siteId));

		List<AssetVocabulary> filteredAssetVocabularies = new ArrayList<>();

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			if (assetVocabulary.isAssociatedToClassNameId(_getClassNameId())) {
				int count =
					_assetCategoryLocalService.getVocabularyCategoriesCount(
						assetVocabulary.getVocabularyId());

				if (count > 0) {
					filteredAssetVocabularies.add(assetVocabulary);
				}
			}
		}

		return filteredAssetVocabularies;
	}

	private long _getClassNameId() {
		return _classNameLocalService.getClassNameId(
			DLFileEntry.class.getName());
	}

	private TaxonomyVocabulary _toTaxonomyVocabulary(
		List<AssetCategory> assetCategories, AssetVocabulary assetVocabulary) {

		return new TaxonomyVocabulary() {
			{
				multiValued = assetVocabulary.isMultiValued();
				name = assetVocabulary.getName();
				required = assetVocabulary.isRequired(
					_getClassNameId(),
					AssetCategoryConstants.ALL_CLASS_TYPE_PK);
				taxonomyCategories = transformToArray(
					assetCategories,
					assetCategory -> new TaxonomyCategory() {
						{
							taxonomyCategoryId = assetCategory.getCategoryId();
							taxonomyCategoryName = assetCategory.getName();
						}
					},
					TaxonomyCategory.class);
				taxonomyVocabularyId = assetVocabulary.getVocabularyId();
			}
		};
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DocumentBulkSelectionFactory _documentBulkSelectionFactory;

	@Reference
	private Portal _portal;

}