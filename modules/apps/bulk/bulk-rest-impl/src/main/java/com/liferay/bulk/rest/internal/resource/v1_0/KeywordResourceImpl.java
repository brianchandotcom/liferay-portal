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
import com.liferay.bulk.rest.dto.v1_0.DocumentBulkSelection;
import com.liferay.bulk.rest.dto.v1_0.Keyword;
import com.liferay.bulk.rest.dto.v1_0.KeywordBulkSelection;
import com.liferay.bulk.rest.internal.selection.v1_0.DocumentBulkSelectionFactory;
import com.liferay.bulk.rest.resource.v1_0.KeywordResource;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionInputParameters;
import com.liferay.bulk.selection.BulkSelectionRunner;
import com.liferay.document.library.bulk.selection.EditTagsBulkSelectionAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.vulcan.pagination.Page;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

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
	public void patchKeywordBatch(KeywordBulkSelection keywordBulkSelection)
		throws Exception {

		_update(true, keywordBulkSelection);
	}

	@Override
	public Page<Keyword> postKeywordsCommonPage(
			DocumentBulkSelection documentSelection)
		throws Exception {

		return Page.of(
			transform(
				_getAssetTagNames(
					documentSelection,
					PermissionCheckerFactoryUtil.create(contextUser)),
				this::_toTag));
	}

	@Override
	public void putKeywordBatch(KeywordBulkSelection keywordBulkSelection)
		throws Exception {

		_update(false, keywordBulkSelection);
	}

	private Set<String> _getAssetTagNames(
			DocumentBulkSelection documentSelection,
			PermissionChecker permissionChecker)
		throws Exception {

		Set<String> assetTagNames = new HashSet<>();

		AtomicBoolean flag = new AtomicBoolean(true);

		BulkSelection<?> bulkSelection = _documentBulkSelectionFactory.create(
			documentSelection);

		BulkSelection<AssetEntry> assetEntryBulkSelection =
			bulkSelection.toAssetEntryBulkSelection();

		assetEntryBulkSelection.forEach(
			assetEntry -> {
				if (BaseModelPermissionCheckerUtil.containsBaseModelPermission(
						permissionChecker, assetEntry.getGroupId(),
						assetEntry.getClassName(), assetEntry.getClassPK(),
						ActionKeys.UPDATE)) {

					String[] assetEntryAssetTagNames =
						_assetTagLocalService.getTagNames(
							assetEntry.getClassName(), assetEntry.getClassPK());

					if (flag.get()) {
						flag.set(false);

						Collections.addAll(
							assetTagNames, assetEntryAssetTagNames);
					}
					else {
						assetTagNames.retainAll(
							Arrays.asList(assetEntryAssetTagNames));
					}
				}
			});

		return assetTagNames;
	}

	private Keyword _toTag(String assetTagName) {
		return new Keyword() {
			{
				name = assetTagName;
			}
		};
	}

	private void _update(
			boolean append, KeywordBulkSelection keywordBulkSelection)
		throws PortalException {

		BulkSelection<?> bulkSelection = _documentBulkSelectionFactory.create(
			keywordBulkSelection.getDocumentBulkSelection());

		_bulkSelectionRunner.run(
			contextUser, bulkSelection.toAssetEntryBulkSelection(),
			_editTagsBulkSelectionAction,
			new HashMap<String, Serializable>() {
				{
					put(
						BulkSelectionInputParameters.ASSET_ENTRY_BULK_SELECTION,
						true);
					put("append", append);
					put(
						"toAddTagNames",
						keywordBulkSelection.getKeywordsToAdd());
					put(
						"toRemoveTagNames",
						keywordBulkSelection.getKeywordsToRemove());
				}
			});
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private BulkSelectionRunner _bulkSelectionRunner;

	@Reference
	private DocumentBulkSelectionFactory _documentBulkSelectionFactory;

	@Reference
	private EditTagsBulkSelectionAction _editTagsBulkSelectionAction;

}