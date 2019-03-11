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
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.bulk.rest.dto.v1_0.BulkActionResponse;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryAction;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryCommonCategories;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryCommonTags;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryUpdateCategoriesAction;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryUpdateTagsAction;
import com.liferay.bulk.rest.dto.v1_0.Category;
import com.liferay.bulk.rest.dto.v1_0.Vocabulary;
import com.liferay.bulk.rest.resource.v1_0.BulkActionResponseResource;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.bulk.selection.BulkSelectionFactoryRegistry;
import com.liferay.bulk.selection.BulkSelectionInputParameters;
import com.liferay.bulk.selection.BulkSelectionRunner;
import com.liferay.document.library.bulk.selection.EditCategoriesBulkSelectionAction;
import com.liferay.document.library.bulk.selection.EditTagsBulkSelectionAction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/bulk-action-response.properties",
	scope = ServiceScope.PROTOTYPE, service = BulkActionResponseResource.class
)
public class BulkActionResponseResourceImpl
	extends BaseBulkActionResponseResourceImpl {

	@Override
	public BulkActionResponse postCategoryClassName(
		Long classNameId,
		BulkAssetEntryUpdateCategoriesAction
			bulkAssetEntryUpdateCategoriesAction) {

		try {
			BulkSelectionFactory<?> bulkSelectionFactory =
				_bulkSelectionFactoryRegistry.getBulkSelectionFactory(
					classNameId);

			BulkSelection<?> bulkSelection = bulkSelectionFactory.create(
				_getParameterMap(bulkAssetEntryUpdateCategoriesAction));

			_bulkSelectionRunner.run(
				_user, bulkSelection.toAssetEntryBulkSelection(),
				_editCategoriesBulkSelectionAction,
				new HashMap<String, Serializable>() {
					{
						put(
							BulkSelectionInputParameters.
								ASSET_ENTRY_BULK_SELECTION,
							true);
						put(
							"append",
							bulkAssetEntryUpdateCategoriesAction.getAppend());
						put(
							"toAddCategoryIds",
							bulkAssetEntryUpdateCategoriesAction.
								getToAddCategoryIds());
						put(
							"toRemoveCategoryIds",
							bulkAssetEntryUpdateCategoriesAction.
								getToRemoveCategoryIds());
					}
				});

			return _toBulkActionResponse(null);
		}
		catch (Exception e) {
			return _toBulkActionResponse(e);
		}
	}

	@Override
	public BulkAssetEntryCommonCategories
		postCategoryContentSpaceClassNameCommon(
			Long contentSpaceId, Long classNameId,
			BulkAssetEntryAction bulkAssetEntryAction) {

		try {
			BulkSelectionFactory<?> bulkSelectionFactory =
				_bulkSelectionFactoryRegistry.getBulkSelectionFactory(
					classNameId);

			BulkSelection<?> bulkSelection = bulkSelectionFactory.create(
				_getParameterMap(bulkAssetEntryAction));

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

			Map<AssetVocabulary, List<AssetCategory>> assetCatogoriesMap =
				_getAssetCategoriesMap(
					contentSpaceId, classNameId, assetCategories);

			Set<Map.Entry<AssetVocabulary, List<AssetCategory>>> entries =
				assetCatogoriesMap.entrySet();

			Stream<Map.Entry<AssetVocabulary, List<AssetCategory>>>
				assetCategoriesStream = entries.stream();

			return new BulkAssetEntryCommonCategories() {
				{
					description = bulkSelection.describe(
						contextAcceptLanguage.getPreferredLocale());
					status = "success";

					vocabularies = assetCategoriesStream.map(
						entry -> _toVocabulary(entry.getValue(), entry.getKey())
					).toArray(
						Vocabulary[]::new
					);
				}
			};
		}
		catch (Exception e) {
			return new BulkAssetEntryCommonCategories() {
				{
					description = e.getMessage();
					status = "error";
				}
			};
		}
	}

	@Override
	public BulkActionResponse postTagClassName(
		Long classNameId,
		BulkAssetEntryUpdateTagsAction bulkAssetEntryUpdateTagsAction) {

		try {
			BulkSelectionFactory<?> bulkSelectionFactory =
				_bulkSelectionFactoryRegistry.getBulkSelectionFactory(
					classNameId);

			BulkSelection<?> bulkSelection = bulkSelectionFactory.create(
				_getParameterMap(bulkAssetEntryUpdateTagsAction));

			_bulkSelectionRunner.run(
				_user, bulkSelection.toAssetEntryBulkSelection(),
				_editTagsBulkSelectionAction,
				new HashMap<String, Serializable>() {
					{
						put(
							BulkSelectionInputParameters.
								ASSET_ENTRY_BULK_SELECTION,
							true);
						put(
							"append",
							bulkAssetEntryUpdateTagsAction.getAppend());
						put(
							"toAddTagNames",
							bulkAssetEntryUpdateTagsAction.getToAddTagNames());
						put(
							"toRemoveTagNames",
							bulkAssetEntryUpdateTagsAction.
								getToRemoveTagNames());
					}
				});

			return _toBulkActionResponse(null);
		}
		catch (Exception e) {
			return _toBulkActionResponse(e);
		}
	}

	@Override
	public BulkAssetEntryCommonTags postTagContentSpaceClassNameCommon(
		Long contentSpaceId, Long classNameId,
		BulkAssetEntryAction bulkAssetEntryAction) {

		try {
			BulkSelectionFactory<?> bulkSelectionFactory =
				_bulkSelectionFactoryRegistry.getBulkSelectionFactory(
					classNameId);

			BulkSelection<?> bulkSelection = bulkSelectionFactory.create(
				_getParameterMap(bulkAssetEntryAction));

			BulkSelection<AssetEntry> assetEntryBulkSelection =
				bulkSelection.toAssetEntryBulkSelection();

			Stream<AssetEntry> stream = assetEntryBulkSelection.stream();

			return new BulkAssetEntryCommonTags() {
				{
					description = assetEntryBulkSelection.describe(
						contextAcceptLanguage.getPreferredLocale());
					groupIds = ArrayUtil.toLongArray(
						_portal.getCurrentAndAncestorSiteGroupIds(
							contentSpaceId));
					status = "success";

					tagNames = stream.map(
						_getAssetTagNamesFunction(
							PermissionCheckerFactoryUtil.create(_user))
					).reduce(
						SetUtil::intersect
					).orElse(
						new HashSet<>()
					).toArray(
						new String[0]
					);
				}
			};
		}
		catch (Exception e) {
			return new BulkAssetEntryCommonTags() {
				{
					description = e.getMessage();
					status = "error";
				}
			};
		}
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
				Collectors.groupingBy(
					assetCategory -> assetCategory.getVocabularyId()));

		return assetVocabulariesStream.collect(
			Collectors.toMap(
				Function.identity(),
				assetVocabulary -> assetCategoriesMap.computeIfAbsent(
					assetVocabulary.getVocabularyId(),
					key -> new ArrayList<>())));
	}

	private Function<AssetEntry, Set<String>> _getAssetTagNamesFunction(
		PermissionChecker permissionChecker) {

		return assetEntry -> {
			if (!BaseModelPermissionCheckerUtil.containsBaseModelPermission(
					permissionChecker, assetEntry.getGroupId(),
					assetEntry.getClassName(), assetEntry.getClassPK(),
					ActionKeys.UPDATE)) {

				return Collections.emptySet();
			}

			return SetUtil.fromArray(
				_assetTagLocalService.getTagNames(
					assetEntry.getClassName(), assetEntry.getClassPK()));
		};
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

	private Map<String, String[]> _getParameterMap(
		BulkAssetEntryAction bulkAssetEntryAction) {

		return _getParamterMap(
			bulkAssetEntryAction.getFolderId(),
			bulkAssetEntryAction.getRepositoryId(),
			bulkAssetEntryAction.getSelection(),
			bulkAssetEntryAction.getSelectAll());
	}

	private Map<String, String[]> _getParameterMap(
		BulkAssetEntryUpdateCategoriesAction bulkAssetEntryUpdateTagsAction) {

		return _getParamterMap(
			bulkAssetEntryUpdateTagsAction.getFolderId(),
			bulkAssetEntryUpdateTagsAction.getRepositoryId(),
			bulkAssetEntryUpdateTagsAction.getSelection(),
			bulkAssetEntryUpdateTagsAction.getSelectAll());
	}

	private Map<String, String[]> _getParameterMap(
		BulkAssetEntryUpdateTagsAction bulkAssetEntryUpdateTagsAction) {

		return _getParamterMap(
			bulkAssetEntryUpdateTagsAction.getFolderId(),
			bulkAssetEntryUpdateTagsAction.getRepositoryId(),
			bulkAssetEntryUpdateTagsAction.getSelection(),
			bulkAssetEntryUpdateTagsAction.getSelectAll());
	}

	private Map<String, String[]> _getParamterMap(
		Long folderId, Long repositoryId, String[] rowIdsFileEntry,
		Boolean selectAll) {

		if (repositoryId == 0) {
			return Collections.singletonMap("rowIdsFileEntry", rowIdsFileEntry);
		}

		return new HashMap<String, String[]>() {
			{
				put("folderId", new String[] {String.valueOf(folderId)});
				put(
					"repositoryId",
					new String[] {String.valueOf(repositoryId)});
				put("rowIdsFileEntry", rowIdsFileEntry);
				put("selectAll", new String[] {Boolean.toString(selectAll)});
			}
		};
	}

	private BulkActionResponse _toBulkActionResponse(Exception e) {
		return new BulkActionResponse() {
			{
				if (e == null) {
					description = StringPool.BLANK;
					status = "success";
				}
				else {
					description = e.getMessage();
					status = "error";
				}
			}
		};
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
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private BulkSelectionFactoryRegistry _bulkSelectionFactoryRegistry;

	@Reference
	private BulkSelectionRunner _bulkSelectionRunner;

	@Reference
	private EditCategoriesBulkSelectionAction
		_editCategoriesBulkSelectionAction;

	@Reference
	private EditTagsBulkSelectionAction _editTagsBulkSelectionAction;

	@Reference
	private Portal _portal;

	@Context
	private User _user;

}