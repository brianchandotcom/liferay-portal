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
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.bulk.rest.internal.model.BulkActionResponseModel;
import com.liferay.bulk.rest.internal.model.BulkAssetEntryActionModel;
import com.liferay.bulk.rest.internal.model.BulkAssetEntryCommonCategoriesModel;
import com.liferay.bulk.rest.internal.model.BulkAssetEntryCommonTagsModel;
import com.liferay.bulk.rest.internal.model.BulkAssetEntryUpdateCategoriesActionModel;
import com.liferay.bulk.rest.internal.model.BulkAssetEntryUpdateTagsActionModel;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.bulk.selection.BulkSelectionRunner;
import com.liferay.document.library.bulk.selection.EditCategoriesBulkSelectionAction;
import com.liferay.document.library.bulk.selection.EditTagsBulkSelectionAction;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

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
	@Path("/categories/{classNameId}/common")
	@POST
	@Produces(ContentTypes.APPLICATION_JSON)
	public BulkAssetEntryCommonCategoriesModel
		getBulkAssetEntryCommonCategoriesModel(
			@Context User user, @Context Locale locale,
			@PathParam("classNameId") long classNameId,
			BulkAssetEntryActionModel bulkAssetEntryActionModel) {

		try {
			BulkSelection<FileEntry> bulkSelection =
				_bulkSelectionFactory.create(
					bulkAssetEntryActionModel.getParameterMap());

			Stream<FileEntry> stream = bulkSelection.stream();

			Set<AssetCategory> commonCategories = stream.map(
				_getFileEntryCategoriesFunction(
					PermissionCheckerFactoryUtil.create(user))
			).reduce(
				SetUtil::intersect
			).orElse(
				Collections.emptySet()
			);

			return new BulkAssetEntryCommonCategoriesModel(
				bulkSelection.describe(locale),
				new ArrayList<>(commonCategories));
		}
		catch (Exception e) {
			return new BulkAssetEntryCommonCategoriesModel(e);
		}
	}

	@Consumes(ContentTypes.APPLICATION_JSON)
	@Path("/tags/{classNameId}/common")
	@POST
	@Produces(ContentTypes.APPLICATION_JSON)
	public BulkAssetEntryCommonTagsModel getBulkAssetEntryCommonTagsModel(
		@Context User user, @Context Locale locale,
		@PathParam("classNameId") long classNameId,
		BulkAssetEntryActionModel bulkAssetEntryActionModel) {

		try {
			BulkSelection<FileEntry> bulkSelection =
				_bulkSelectionFactory.create(
					bulkAssetEntryActionModel.getParameterMap());

			Stream<FileEntry> stream = bulkSelection.stream();

			Set<String> commonTags = stream.map(
				_getFileEntryTagsFunction(
					PermissionCheckerFactoryUtil.create(user))
			).reduce(
				SetUtil::intersect
			).orElse(
				Collections.emptySet()
			);

			return new BulkAssetEntryCommonTagsModel(
				bulkSelection.describe(locale), new ArrayList<>(commonTags));
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
			BulkSelection<FileEntry> bulkSelection =
				_bulkSelectionFactory.create(
					bulkAssetEntryUpdateCategoriesActionModel.
						getParameterMap());

			_bulkSelectionRunner.run(
				user, bulkSelection, _editCategoriesBulkSelectionAction,
				new HashMap<String, Serializable>() {
					{
						put(
							"append",
							bulkAssetEntryUpdateCategoriesActionModel.
								getAppend());
						put(
							"toAddCategoryIds",
							bulkAssetEntryUpdateCategoriesActionModel.
								getToAddCategoryIds());
						put(
							"toRemoveCategoryIds",
							bulkAssetEntryUpdateCategoriesActionModel.
								getToRemoveCategoryIds());
					}
				});

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
			BulkSelection<FileEntry> bulkSelection =
				_bulkSelectionFactory.create(
					bulkAssetEntryUpdateTagsActionModel.getParameterMap());

			_bulkSelectionRunner.run(
				user, bulkSelection, _editTagsBulkSelectionAction,
				new HashMap<String, Serializable>() {
					{
						put(
							"append",
							bulkAssetEntryUpdateTagsActionModel.getAppend());
						put(
							"toAddTagNames",
							bulkAssetEntryUpdateTagsActionModel.
								getToAddTagNames());
						put(
							"toRemoveTagNames",
							bulkAssetEntryUpdateTagsActionModel.
								getToRemoveTagNames());
					}
				});

			return BulkActionResponseModel.SUCCESS;
		}
		catch (Exception e) {
			return new BulkActionResponseModel(e);
		}
	}

	private Function<FileEntry, Set<AssetCategory>>
		_getFileEntryCategoriesFunction(PermissionChecker permissionChecker) {

		return fileEntry -> {
			try {
				if (_fileEntryModelResourcePermission.contains(
						permissionChecker, fileEntry, ActionKeys.UPDATE)) {

					return new HashSet<>(
						_assetCategoryLocalService.getCategories(
							DLFileEntryConstants.getClassName(),
							fileEntry.getFileEntryId()));
				}

				return Collections.emptySet();
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(pe, pe);
				}

				return Collections.emptySet();
			}
		};
	}

	private Function<FileEntry, Set<String>> _getFileEntryTagsFunction(
		PermissionChecker permissionChecker) {

		return fileEntry -> {
			try {
				if (_fileEntryModelResourcePermission.contains(
						permissionChecker, fileEntry, ActionKeys.UPDATE)) {

					return SetUtil.fromArray(
						_assetTagLocalService.getTagNames(
							DLFileEntryConstants.getClassName(),
							fileEntry.getFileEntryId()));
				}

				return Collections.emptySet();
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(pe, pe);
				}

				return Collections.emptySet();
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BulkAssetEntryResource.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private BulkSelectionFactory<FileEntry> _bulkSelectionFactory;

	@Reference
	private BulkSelectionRunner _bulkSelectionRunner;

	@Reference
	private EditCategoriesBulkSelectionAction
		_editCategoriesBulkSelectionAction;

	@Reference
	private EditTagsBulkSelectionAction _editTagsBulkSelectionAction;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission;

}