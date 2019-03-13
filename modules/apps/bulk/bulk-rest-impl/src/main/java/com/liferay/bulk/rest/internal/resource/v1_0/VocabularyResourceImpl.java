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
import com.liferay.asset.kernel.model.AssetCategoryModel;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.bulk.rest.dto.v1_0.Category;
import com.liferay.bulk.rest.dto.v1_0.DocumentSelection;
import com.liferay.bulk.rest.dto.v1_0.Vocabulary;
import com.liferay.bulk.rest.internal.helper.BulkSelectionHelper;
import com.liferay.bulk.rest.resource.v1_0.VocabularyResource;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alejandro Tardín
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/vocabulary.properties",
	scope = ServiceScope.PROTOTYPE, service = VocabularyResource.class
)
public class VocabularyResourceImpl extends BaseVocabularyResourceImpl {

	@Override
	public Page<Vocabulary> postContentSpaceVocabularyCommonPage(
			Long contentSpaceId, DocumentSelection documentSelection)
		throws Exception {

		BulkSelection<?> bulkSelection = _bulkSelectionHelper.getBulkSelection(
			documentSelection);

		BulkSelection<AssetEntry> assetEntryBulkSelection =
			bulkSelection.toAssetEntryBulkSelection();

		Stream<AssetEntry> stream = assetEntryBulkSelection.stream();

		Set<AssetCategory> assetCategories = stream.map(
			_getAssetCategoriesFunction(
				PermissionCheckerFactoryUtil.create(_user))
		).reduce(
			SetUtil::intersect
		).orElse(
			Collections.emptySet()
		);

		ClassName className = _classNameLocalService.getClassName(
			FileEntry.class.getName());

		long classNameId = className.getClassNameId();

		Map<AssetVocabulary, List<AssetCategory>> assetCategoriesMap =
			_getAssetCategoriesMap(
				contentSpaceId, classNameId, assetCategories);

		Set<Map.Entry<AssetVocabulary, List<AssetCategory>>> entries =
			assetCategoriesMap.entrySet();

		Map.Entry<AssetVocabulary, List<AssetCategory>>[]
			assetCategoriesEntries = entries.toArray(new Map.Entry[0]);

		return Page.of(
			transformToList(
				assetCategoriesEntries,
				assetVocabularyListEntry -> _toVocabulary(
					assetVocabularyListEntry.getValue(),
					assetVocabularyListEntry.getKey())));
	}

	private Function<AssetEntry, Set<AssetCategory>>
		_getAssetCategoriesFunction(PermissionChecker permissionChecker) {

		return assetEntry -> {
			if (!BaseModelPermissionCheckerUtil.containsBaseModelPermission(
					permissionChecker, assetEntry.getGroupId(),
					assetEntry.getClassName(), assetEntry.getClassPK(),
					ActionKeys.UPDATE)) {

				return Collections.emptySet();
			}

			return new HashSet<>(
				_assetCategoryLocalService.getCategories(
					assetEntry.getClassName(), assetEntry.getClassPK()));
		};
	}

	private Map<AssetVocabulary, List<AssetCategory>> _getAssetCategoriesMap(
			Long groupId, Long classNameId, Set<AssetCategory> assetCategories)
		throws Exception {

		List<AssetVocabulary> assetVocabularies = _getAssetVocabularies(
			groupId, classNameId);

		Stream<AssetVocabulary> assetVocabulariesStream =
			assetVocabularies.stream();

		Stream<AssetCategory> assetCategoriesStream = assetCategories.stream();

		Map<Long, List<AssetCategory>> assetCategoriesMap =
			assetCategoriesStream.collect(
				Collectors.groupingBy(AssetCategoryModel::getVocabularyId));

		return assetVocabulariesStream.collect(
			Collectors.toMap(
				Function.identity(),
				assetVocabulary -> assetCategoriesMap.computeIfAbsent(
					assetVocabulary.getVocabularyId(),
					key -> new ArrayList<>())));
	}

	private List<AssetVocabulary> _getAssetVocabularies(
			Long groupId, Long classNameId)
		throws Exception {

		List<AssetVocabulary> assetVocabularies =
			_assetVocabularyLocalService.getGroupVocabularies(
				_portal.getCurrentAndAncestorSiteGroupIds(groupId));

		Stream<AssetVocabulary> stream = assetVocabularies.stream();

		return stream.filter(
			assetVocabulary -> assetVocabulary.isAssociatedToClassNameId(
				classNameId)
		).filter(
			assetVocabulary -> {
				int count =
					_assetCategoryLocalService.getVocabularyCategoriesCount(
						assetVocabulary.getVocabularyId());

				return count > 0;
			}
		).collect(
			Collectors.toList()
		);
	}

	private Vocabulary _toVocabulary(
		List<AssetCategory> assetCategories, AssetVocabulary assetVocabulary) {

		return new Vocabulary() {
			{
				categories = transformToArray(
					assetCategories,
					assetCategory -> new Category() {
						{
							categoryId = assetCategory.getCategoryId();
							name = assetCategory.getName();
						}
					},
					Category.class);
				multiValued = assetVocabulary.isMultiValued();
				name = assetVocabulary.getName();
				vocabularyId = assetVocabulary.getVocabularyId();
			}
		};
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private BulkSelectionHelper _bulkSelectionHelper;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private Portal _portal;

	@Context
	private User _user;

}