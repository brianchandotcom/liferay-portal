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
import com.liferay.bulk.rest.dto.v1_0.AssetCategoryModel;
import com.liferay.bulk.rest.dto.v1_0.AssetVocabularyModel;
import com.liferay.bulk.rest.dto.v1_0.BulkActionResponseModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryActionModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryCommonCategoriesModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryCommonTagsModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryUpdateCategoriesActionModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryUpdateTagsActionModel;
import com.liferay.bulk.rest.resource.v1_0.BulkActionResponseModelResource;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.bulk.selection.BulkSelectionFactoryRegistry;
import com.liferay.bulk.selection.BulkSelectionInputParameters;
import com.liferay.bulk.selection.BulkSelectionRunner;
import com.liferay.document.library.bulk.selection.EditCategoriesBulkSelectionAction;
import com.liferay.document.library.bulk.selection.EditTagsBulkSelectionAction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
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
	properties = "OSGI-INF/liferay/rest/v1_0/bulk-action-response-model.properties",
	scope = ServiceScope.PROTOTYPE,
	service = BulkActionResponseModelResource.class
)
public class BulkActionResponseModelResourceImpl
	extends BaseBulkActionResponseModelResourceImpl {

	@Override
	public BulkActionResponseModel postCategoryClassName(
			Long categoryClassNameId,
			BulkAssetEntryUpdateCategoriesActionModel
				bulkAssetEntryUpdateCategoriesActionModel)
		throws Exception {

		try {
			BulkSelectionFactory<?> bulkSelectionFactory =
				_bulkSelectionFactoryRegistry.getBulkSelectionFactory(
					categoryClassNameId);

			BulkSelection<?> bulkSelection = bulkSelectionFactory.create(
				_getParameterMap(bulkAssetEntryUpdateCategoriesActionModel));

			BulkSelection<AssetEntry> assetEntryBulkSelection =
				bulkSelection.toAssetEntryBulkSelection();

			Map<String, Serializable> inputMap = new HashMap<>(4);

			inputMap.put(
				BulkSelectionInputParameters.ASSET_ENTRY_BULK_SELECTION, true);
			inputMap.put(
				"append",
				bulkAssetEntryUpdateCategoriesActionModel.getAppend());
			inputMap.put(
				"toAddCategoryIds",
				bulkAssetEntryUpdateCategoriesActionModel.
					getToAddCategoryIds());
			inputMap.put(
				"toRemoveCategoryIds",
				bulkAssetEntryUpdateCategoriesActionModel.
					getToRemoveCategoryIds());

			_bulkSelectionRunner.run(
				_user, assetEntryBulkSelection,
				_editCategoriesBulkSelectionAction, inputMap);

			return _toBulkActionResponseModel(null);
		}
		catch (Exception e) {
			return _toBulkActionResponseModel(e);
		}
	}

	@Override
	public BulkAssetEntryCommonCategoriesModel
			postCategoryGroupCategoryClassName(
				Long categoryGroupId, Long categoryClassNameId,
				BulkAssetEntryActionModel bulkAssetEntryActionModel)
		throws Exception {

		try {
			BulkSelectionFactory<?> bulkSelectionFactory =
				_bulkSelectionFactoryRegistry.getBulkSelectionFactory(
					categoryClassNameId);

			BulkSelection<?> bulkSelection = bulkSelectionFactory.create(
				_getParameterMap(bulkAssetEntryActionModel));

			BulkSelection<AssetEntry> assetEntryBulkSelection =
				bulkSelection.toAssetEntryBulkSelection();

			Stream<AssetEntry> stream = assetEntryBulkSelection.stream();

			Set<AssetCategory> commonCategories = stream.map(
				_getAssetEntryCategoriesFunction(
					PermissionCheckerFactoryUtil.create(_user))
			).reduce(
				SetUtil::intersect
			).orElse(
				Collections.emptySet()
			);

			Map<AssetVocabulary, List<AssetCategory>> assetVocabularyListMap =
				_groupByAssetVocabulary(
					categoryGroupId, categoryClassNameId, commonCategories);

			Set<Map.Entry<AssetVocabulary, List<AssetCategory>>> entries =
				assetVocabularyListMap.entrySet();

			Stream<Map.Entry<AssetVocabulary, List<AssetCategory>>>
				assetCategoriesStream = entries.stream();

			return new BulkAssetEntryCommonCategoriesModel() {
				{
					description = bulkSelection.describe(
						contextAcceptLanguage.getPreferredLocale());
					status = "success";
					vocabularies = assetCategoriesStream.map(
						entry -> _toAssetVocabularyModel(
							entry.getKey(), entry.getValue())
					).toArray(
						AssetVocabularyModel[]::new
					);
				}
			};
		}
		catch (Exception e) {
			return _toBulkAssetEntryCommonCategoriesModel(e);
		}
	}

	@Override
	public BulkActionResponseModel postTagClassName(
			Long tagClassNameId,
			BulkAssetEntryUpdateTagsActionModel
				bulkAssetEntryUpdateTagsActionModel)
		throws Exception {

		try {
			BulkSelectionFactory<?> bulkSelectionFactory =
				_bulkSelectionFactoryRegistry.getBulkSelectionFactory(
					tagClassNameId);

			BulkSelection<?> bulkSelection = bulkSelectionFactory.create(
				_getParameterMap(bulkAssetEntryUpdateTagsActionModel));

			BulkSelection<AssetEntry> assetEntryBulkSelection =
				bulkSelection.toAssetEntryBulkSelection();

			Map<String, Serializable> inputMap = new HashMap<>(4);

			inputMap.put(
				BulkSelectionInputParameters.ASSET_ENTRY_BULK_SELECTION, true);
			inputMap.put(
				"append", bulkAssetEntryUpdateTagsActionModel.getAppend());
			inputMap.put(
				"toAddTagNames",
				bulkAssetEntryUpdateTagsActionModel.getToAddTagNames());
			inputMap.put(
				"toRemoveTagNames",
				bulkAssetEntryUpdateTagsActionModel.getToRemoveTagNames());

			_bulkSelectionRunner.run(
				_user, assetEntryBulkSelection, _editTagsBulkSelectionAction,
				inputMap);

			return _toBulkActionResponseModel(null);
		}
		catch (Exception e) {
			return _toBulkActionResponseModel(e);
		}
	}

	@Override
	public BulkAssetEntryCommonTagsModel postTagGroupTagClassName(
			Long tagGroupId, Long tagClassNameId,
			BulkAssetEntryActionModel bulkAssetEntryActionModel)
		throws Exception {

		try {
			BulkSelectionFactory<?> bulkSelectionFactory =
				_bulkSelectionFactoryRegistry.getBulkSelectionFactory(
					tagClassNameId);

			BulkSelection<?> bulkSelection = bulkSelectionFactory.create(
				_getParameterMap(bulkAssetEntryActionModel));

			BulkSelection<AssetEntry> assetEntryBulkSelection =
				bulkSelection.toAssetEntryBulkSelection();

			Stream<AssetEntry> stream = assetEntryBulkSelection.stream();

			return new BulkAssetEntryCommonTagsModel() {
				{
					description = assetEntryBulkSelection.describe(
						contextAcceptLanguage.getPreferredLocale());
					groupIds = ArrayUtil.toLongArray(
						_portal.getCurrentAndAncestorSiteGroupIds(tagGroupId));

					tagNames = stream.map(
						_getAssetEntryTagsFunction(
							PermissionCheckerFactoryUtil.create(_user))
					).reduce(
						SetUtil::intersect
					).orElse(
						new HashSet<>()
					).toArray(
						new String[0]
					);

					status = "success";
				}
			};
		}
		catch (Exception e) {
			return new BulkAssetEntryCommonTagsModel() {
				{
					description = e.getMessage();
					status = "error";
				}
			};
		}
	}

	private Function<AssetEntry, Set<AssetCategory>>
		_getAssetEntryCategoriesFunction(PermissionChecker permissionChecker) {

		return assetEntry -> {
			if (BaseModelPermissionCheckerUtil.containsBaseModelPermission(
					permissionChecker, assetEntry.getGroupId(),
					assetEntry.getClassName(), assetEntry.getClassPK(),
					ActionKeys.UPDATE)) {

				return new HashSet<>(
					_assetCategoryLocalService.getCategories(
						assetEntry.getClassName(), assetEntry.getClassPK()));
			}

			return Collections.emptySet();
		};
	}

	private Function<AssetEntry, Set<String>> _getAssetEntryTagsFunction(
		PermissionChecker permissionChecker) {

		return assetEntry -> {
			if (BaseModelPermissionCheckerUtil.containsBaseModelPermission(
					permissionChecker, assetEntry.getGroupId(),
					assetEntry.getClassName(), assetEntry.getClassPK(),
					ActionKeys.UPDATE)) {

				return SetUtil.fromArray(
					_assetTagLocalService.getTagNames(
						assetEntry.getClassName(), assetEntry.getClassPK()));
			}

			return Collections.emptySet();
		};
	}

	private List<AssetVocabulary> _getAssetVocabularies(
			long groupId, long classNameId)
		throws PortalException {

		List<AssetVocabulary> assetVocabularies =
			_assetVocabularyLocalService.getGroupVocabularies(
				_portal.getCurrentAndAncestorSiteGroupIds(groupId));

		Stream<AssetVocabulary> stream = assetVocabularies.stream();

		return stream.filter(
			assetVocabulary -> assetVocabulary.isAssociatedToClassNameId(
				classNameId)
		).filter(
			assetVocabulary ->
				_assetCategoryLocalService.getVocabularyCategoriesCount(
					assetVocabulary.getVocabularyId()) > 0
		).collect(
			Collectors.toList()
		);
	}

	private Map<String, String[]> _getParameterMap(
		BulkAssetEntryActionModel bulkAssetEntryActionModel) {

		return _getStringMap(
			bulkAssetEntryActionModel.getSelection(),
			bulkAssetEntryActionModel.getRepositoryId(),
			bulkAssetEntryActionModel.getFolderId(),
			bulkAssetEntryActionModel.getSelectAll());
	}

	private Map<String, String[]> _getParameterMap(
		BulkAssetEntryUpdateCategoriesActionModel
			bulkAssetEntryUpdateTagsActionModel) {

		return _getStringMap(
			bulkAssetEntryUpdateTagsActionModel.getSelection(),
			bulkAssetEntryUpdateTagsActionModel.getRepositoryId(),
			bulkAssetEntryUpdateTagsActionModel.getFolderId(),
			bulkAssetEntryUpdateTagsActionModel.getSelectAll());
	}

	private Map<String, String[]> _getParameterMap(
		BulkAssetEntryUpdateTagsActionModel
			bulkAssetEntryUpdateTagsActionModel) {

		return _getStringMap(
			bulkAssetEntryUpdateTagsActionModel.getSelection(),
			bulkAssetEntryUpdateTagsActionModel.getRepositoryId(),
			bulkAssetEntryUpdateTagsActionModel.getFolderId(),
			bulkAssetEntryUpdateTagsActionModel.getSelectAll());
	}

	private Map<String, String[]> _getStringMap(
		String[] values, Long repositoryId, Long folderId, Boolean selectAll) {

		if (repositoryId == 0) {
			return Collections.singletonMap("rowIdsFileEntry", values);
		}

		Map<String, String[]> parameterMap = new HashMap<>(2);

		parameterMap.put("folderId", new String[] {String.valueOf(folderId)});
		parameterMap.put(
			"repositoryId", new String[] {String.valueOf(repositoryId)});
		parameterMap.put("rowIdsFileEntry", values);
		parameterMap.put(
			"selectAll", new String[] {Boolean.toString(selectAll)});

		return parameterMap;
	}

	private Map<AssetVocabulary, List<AssetCategory>> _groupByAssetVocabulary(
			long groupId, long classNameId, Set<AssetCategory> commonCategories)
		throws PortalException {

		List<AssetVocabulary> assetVocabularies = _getAssetVocabularies(
			groupId, classNameId);

		Stream<AssetCategory> assetCategoryStream = commonCategories.stream();

		Map<Long, List<AssetCategory>> assetVocabularyIdMap =
			assetCategoryStream.collect(
				Collectors.groupingBy(
					assetCategory -> assetCategory.getVocabularyId()));

		Stream<AssetVocabulary> assetVocabularyStream =
			assetVocabularies.stream();

		return assetVocabularyStream.collect(
			Collectors.toMap(
				Function.identity(),
				assetVocabulary -> assetVocabularyIdMap.computeIfAbsent(
					assetVocabulary.getVocabularyId(),
					key -> new ArrayList<>())));
	}

	private AssetVocabularyModel _toAssetVocabularyModel(
		AssetVocabulary assetVocabulary, List<AssetCategory> assetCategories) {

		return new AssetVocabularyModel() {
			{
				categories = transformToArray(
					assetCategories,
					assetCategory -> new AssetCategoryModel() {
						{
							categoryId = assetCategory.getCategoryId();
							name = assetCategory.getName();
						}
					},
					AssetCategoryModel.class);

				multiValued = assetVocabulary.isMultiValued();
				name = assetVocabulary.getName();
				vocabularyId = assetVocabulary.getVocabularyId();
			}
		};
	}

	private BulkActionResponseModel _toBulkActionResponseModel(Exception e) {
		return new BulkActionResponseModel() {
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

	private BulkAssetEntryCommonCategoriesModel
		_toBulkAssetEntryCommonCategoriesModel(Exception e) {

		return new BulkAssetEntryCommonCategoriesModel() {
			{
				description = e.getMessage();
				status = "error";
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