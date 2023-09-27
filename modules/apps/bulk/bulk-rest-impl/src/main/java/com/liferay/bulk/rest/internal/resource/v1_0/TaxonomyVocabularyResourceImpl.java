/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;
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
				if (ModelResourcePermissionUtil.contains(
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

		Map<AssetVocabulary, List<AssetCategory>> assetCategoriesMap =
			new HashMap<>();

		Map<Long, List<AssetCategory>> assetVocabularyIdAssetCategoriesMap =
			new HashMap<>();

		for (AssetCategory assetCategory :
				_getAssetCategories(
					documentBulkSelection,
					PermissionCheckerFactoryUtil.create(contextUser))) {

			List<AssetCategory> assetCategories =
				assetVocabularyIdAssetCategoriesMap.computeIfAbsent(
					assetCategory.getVocabularyId(), key -> new ArrayList<>());

			assetCategories.add(assetCategory);
		}

		for (AssetVocabulary assetVocabulary : _getAssetVocabularies(siteId)) {
			assetCategoriesMap.put(
				assetVocabulary,
				assetVocabularyIdAssetCategoriesMap.computeIfAbsent(
					assetVocabulary.getVocabularyId(),
					key -> new ArrayList<>()));
		}

		return assetCategoriesMap;
	}

	private List<AssetVocabulary> _getAssetVocabularies(Long siteId)
		throws Exception {

		List<AssetVocabulary> assetVocabularies = new ArrayList<>();

		for (AssetVocabulary assetVocabulary :
				_assetVocabularyLocalService.getGroupVocabularies(
					_portal.getCurrentAndAncestorSiteGroupIds(siteId))) {

			if (!assetVocabulary.isAssociatedToClassNameId(_getClassNameId())) {
				continue;
			}

			int count = _assetCategoryLocalService.getVocabularyCategoriesCount(
				assetVocabulary.getVocabularyId());

			if (count > 0) {
				assetVocabularies.add(assetVocabulary);
			}
		}

		return assetVocabularies;
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