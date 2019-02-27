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

package com.liferay.bulk.rest.internal.resource;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.AssetTagService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.bulk.rest.internal.model.BulkActionResponseModel;
import com.liferay.bulk.rest.internal.model.BulkAssetEntryActionModel;
import com.liferay.bulk.rest.internal.model.BulkAssetEntryCommonCategoriesModel;
import com.liferay.bulk.rest.internal.model.BulkAssetEntryCommonTagsModel;
import com.liferay.bulk.rest.internal.model.BulkAssetEntryUpdateCategoriesActionModel;
import com.liferay.bulk.rest.internal.model.BulkAssetEntryUpdateTagsActionModel;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.bulk.selection.BulkSelectionFactoryRegistry;
import com.liferay.bulk.selection.BulkSelectionInputParameters;
import com.liferay.bulk.selection.BulkSelectionRunner;
import com.liferay.document.library.bulk.selection.EditCategoriesBulkSelectionAction;
import com.liferay.document.library.bulk.selection.EditTagsBulkSelectionAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=bulk-application)",
		"osgi.jaxrs.resource=true"
	},
	service = BulkAssetEntryResource.class
)
@Path("/asset")
public class BulkAssetEntryResource {

	@Consumes(ContentTypes.APPLICATION_JSON)
	@Path("/categories/{groupId}/{classNameId}/common")
	@POST
	@Produces(ContentTypes.APPLICATION_JSON)
	public BulkAssetEntryCommonCategoriesModel
		getBulkAssetEntryCommonCategoriesModel(
			@Context User user, @Context Locale locale,
			@PathParam("groupId") long groupId,
			@PathParam("classNameId") long classNameId,
			BulkAssetEntryActionModel bulkAssetEntryActionModel) {

		try {
			BulkSelectionFactory<?> bulkSelectionFactory =
				_bulkSelectionFactoryRegistry.getBulkSelectionFactory(
					classNameId);

			BulkSelection<?> bulkSelection = bulkSelectionFactory.create(
				bulkAssetEntryActionModel.getParameterMap());

			BulkSelection<AssetEntry> assetEntryBulkSelection =
				bulkSelection.toAssetEntryBulkSelection();

			Stream<AssetEntry> stream = assetEntryBulkSelection.stream();

			Set<AssetCategory> commonCategories = stream.map(
				_getAssetEntryCategoriesFunction(
					PermissionCheckerFactoryUtil.create(user))
			).reduce(
				SetUtil::intersect
			).orElse(
				Collections.emptySet()
			);

			return new BulkAssetEntryCommonCategoriesModel(
				bulkSelection.describe(locale),
				_groupByAssetVocabulary(
					groupId, classNameId, commonCategories));
		}
		catch (Exception e) {
			return new BulkAssetEntryCommonCategoriesModel(e);
		}
	}

	@Consumes(ContentTypes.APPLICATION_JSON)
	@Path("/tags/{groupId}/{classNameId}/common")
	@POST
	@Produces(ContentTypes.APPLICATION_JSON)
	public BulkAssetEntryCommonTagsModel getBulkAssetEntryCommonTagsModel(
		@Context User user, @Context Locale locale,
		@PathParam("groupId") long groupId,
		@PathParam("classNameId") long classNameId,
		BulkAssetEntryActionModel bulkAssetEntryActionModel) {

		try {
			BulkSelectionFactory<?> bulkSelectionFactory =
				_bulkSelectionFactoryRegistry.getBulkSelectionFactory(
					classNameId);

			BulkSelection<?> bulkSelection = bulkSelectionFactory.create(
				bulkAssetEntryActionModel.getParameterMap());

			BulkSelection<AssetEntry> assetEntryBulkSelection =
				bulkSelection.toAssetEntryBulkSelection();

			Stream<AssetEntry> stream = assetEntryBulkSelection.stream();

			Set<String> commonTags = stream.map(
				_getAssetEntryTagsFunction(
					PermissionCheckerFactoryUtil.create(user))
			).reduce(
				SetUtil::intersect
			).orElse(
				Collections.emptySet()
			);

			long[] groupIds = _portal.getCurrentAndAncestorSiteGroupIds(
				groupId);

			return new BulkAssetEntryCommonTagsModel(
				assetEntryBulkSelection.describe(locale),
				new ArrayList<>(commonTags), groupIds);
		}
		catch (Exception e) {
			return new BulkAssetEntryCommonTagsModel(e);
		}
	}

	@Consumes(ContentTypes.APPLICATION_JSON)
	@Path("/categories/{classNameId}")
	@POST
	@Produces(ContentTypes.APPLICATION_JSON)
	public BulkActionResponseModel
		updateBulkAssetEntryUpdateCategoriesActionModel(
			@Context User user, @PathParam("classNameId") long classNameId,
			BulkAssetEntryUpdateCategoriesActionModel
				bulkAssetEntryUpdateCategoriesActionModel) {

		try {
			BulkSelectionFactory<?> bulkSelectionFactory =
				_bulkSelectionFactoryRegistry.getBulkSelectionFactory(
					classNameId);

			BulkSelection<?> bulkSelection = bulkSelectionFactory.create(
				bulkAssetEntryUpdateCategoriesActionModel.getParameterMap());

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
				user, assetEntryBulkSelection,
				_editCategoriesBulkSelectionAction, inputMap);

			return BulkActionResponseModel.SUCCESS;
		}
		catch (Exception e) {
			return new BulkActionResponseModel(e);
		}
	}

	@Consumes(ContentTypes.APPLICATION_JSON)
	@Path("/tags/{classNameId}")
	@POST
	@Produces(ContentTypes.APPLICATION_JSON)
	public BulkActionResponseModel updateBulkAssetEntryUpdateTagsActionModel(
		@Context User user, @PathParam("classNameId") long classNameId,
		BulkAssetEntryUpdateTagsActionModel
			bulkAssetEntryUpdateTagsActionModel) {

		try {
			BulkSelectionFactory<?> bulkSelectionFactory =
				_bulkSelectionFactoryRegistry.getBulkSelectionFactory(
					classNameId);

			BulkSelection<?> bulkSelection = bulkSelectionFactory.create(
				bulkAssetEntryUpdateTagsActionModel.getParameterMap());

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
				user, assetEntryBulkSelection, _editTagsBulkSelectionAction,
				inputMap);

			return BulkActionResponseModel.SUCCESS;
		}
		catch (Exception e) {
			return new BulkActionResponseModel(e);
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

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private AssetTagService _assetTagService;

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

}