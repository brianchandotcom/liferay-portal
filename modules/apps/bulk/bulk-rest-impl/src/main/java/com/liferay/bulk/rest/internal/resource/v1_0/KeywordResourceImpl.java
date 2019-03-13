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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.bulk.rest.dto.v1_0.DocumentSelection;
import com.liferay.bulk.rest.dto.v1_0.Keyword;
import com.liferay.bulk.rest.dto.v1_0.SelectionToAddTagNames;
import com.liferay.bulk.rest.internal.helper.BulkSelectionHelper;
import com.liferay.bulk.rest.resource.v1_0.KeywordResource;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionInputParameters;
import com.liferay.bulk.selection.BulkSelectionRunner;
import com.liferay.document.library.bulk.selection.EditTagsBulkSelectionAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.vulcan.pagination.Page;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alejandro Tardín
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/keyword.properties",
	scope = ServiceScope.PROTOTYPE, service = KeywordResource.class
)
public class KeywordResourceImpl extends BaseKeywordResourceImpl {

	@Override
	public boolean patchKeywordBatch(DocumentSelection documentSelection)
		throws Exception {

		_updateKeywords(documentSelection, false);

		return true;
	}

	@Override
	public Page<Keyword> postKeywordCommonPage(
			DocumentSelection documentSelection)
		throws Exception {

		BulkSelection<?> bulkSelection = _bulkSelectionHelper.getBulkSelection(
			documentSelection);

		BulkSelection<AssetEntry> assetEntryBulkSelection =
			bulkSelection.toAssetEntryBulkSelection();

		Stream<AssetEntry> stream = assetEntryBulkSelection.stream();

		return Page.of(
			transform(
				new ArrayList<>(
					stream.map(
						_getAssetTagNamesFunction(
							PermissionCheckerFactoryUtil.create(_user))
					).reduce(
						SetUtil::intersect
					).orElse(
						new HashSet<>()
					)),
				this::_toTag));
	}

	@Override
	public boolean putKeywordBatch(DocumentSelection documentSelection)
		throws Exception {

		_updateKeywords(documentSelection, true);

		return true;
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

	private Keyword _toTag(String keywordName) {
		return new Keyword() {
			{
				name = keywordName;
			}
		};
	}

	private void _updateKeywords(
			DocumentSelection documentSelection, boolean append)
		throws PortalException {

		BulkSelection<?> bulkSelection = _bulkSelectionHelper.getBulkSelection(
			documentSelection);

		SelectionToAddTagNames selectionToAddTagNames =
			(SelectionToAddTagNames)documentSelection;

		_bulkSelectionRunner.run(
			_user, bulkSelection.toAssetEntryBulkSelection(),
			_editTagsBulkSelectionAction,
			new HashMap<String, Serializable>() {
				{
					put(
						BulkSelectionInputParameters.ASSET_ENTRY_BULK_SELECTION,
						true);
					put("append", append);
					put(
						"selectionToAddTagNames",
						selectionToAddTagNames.getSelectionToAddTagNames());
					put(
						"toRemoveTagNames",
						selectionToAddTagNames.getSelectionToRemoveTagNames());
				}
			});
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private BulkSelectionHelper _bulkSelectionHelper;

	@Reference
	private BulkSelectionRunner _bulkSelectionRunner;

	@Reference
	private EditTagsBulkSelectionAction _editTagsBulkSelectionAction;

	@Context
	private User _user;

}