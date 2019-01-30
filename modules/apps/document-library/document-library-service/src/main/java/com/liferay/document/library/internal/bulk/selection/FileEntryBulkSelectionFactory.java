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

package com.liferay.document.library.internal.bulk.selection;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = {BulkSelectionFactory.class, FileEntryBulkSelectionFactory.class}
)
public class FileEntryBulkSelectionFactory
	implements BulkSelectionFactory<FileEntry> {

	public BulkSelection<FileEntry> create(Map<String, String[]> parameterMap) {
		if (!parameterMap.containsKey("rowIdsFileEntry")) {
			throw new IllegalArgumentException();
		}

		String[] values = parameterMap.get("rowIdsFileEntry");

		if (values.length > 1) {
			return _getFileEntrySelection(values, parameterMap);
		}

		String value = values[0];

		if (!value.startsWith("all:")) {
			return _getFileEntrySelection(values, parameterMap);
		}

		if (!parameterMap.containsKey("repositoryId")) {
			throw new IllegalArgumentException();
		}

		String[] repositoryIds = parameterMap.get("repositoryId");

		long repositoryId = GetterUtil.getLong(repositoryIds[0]);

		if (repositoryId == 0) {
			throw new IllegalArgumentException();
		}

		long folderId = GetterUtil.getLong(value.substring(4));

		return new FolderFileEntryBulkSelection(
			repositoryId, folderId, parameterMap, _resourceBundleLoader,
			_language, _repositoryProvider, _dlAppService,
			_assetEntryLocalService);
	}

	private BulkSelection<FileEntry> _getFileEntrySelection(
		String[] values, Map<String, String[]> parameterMap) {

		if (values.length == 1) {
			values = StringUtil.split(values[0]);
		}

		long[] fileEntryIds = GetterUtil.getLongValues(values);

		if (fileEntryIds.length == 1) {
			return new SingleFileEntryBulkSelection(
				fileEntryIds[0], parameterMap, _resourceBundleLoader, _language,
				_dlAppService, _assetEntryLocalService);
		}

		return new MultipleFileEntryBulkSelection(
			fileEntryIds, parameterMap, _resourceBundleLoader, _language,
			_dlAppService, _assetEntryLocalService);
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private Language _language;

	@Reference
	private RepositoryProvider _repositoryProvider;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.document.library.service)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}