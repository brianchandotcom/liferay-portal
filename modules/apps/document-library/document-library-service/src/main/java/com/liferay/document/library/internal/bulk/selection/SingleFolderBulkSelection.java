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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.bulk.selection.BaseSingleEntryBulkSelection;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.Folder;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class SingleFolderBulkSelection
	extends BaseSingleEntryBulkSelection<Folder> {

	public SingleFolderBulkSelection(
		long folderId, Map<String, String[]> parameterMap,
		DLAppService dlAppService) {

		super(folderId, parameterMap);

		_folderId = folderId;
		_dlAppService = dlAppService;
	}

	@Override
	public Class<? extends BulkSelectionFactory>
		getBulkSelectionFactoryClass() {

		return FolderBulkSelectionFactory.class;
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		throw new UnsupportedOperationException("Folder is not an asset");
	}

	@Override
	protected Folder getEntry() throws PortalException {
		return _dlAppService.getFolder(_folderId);
	}

	@Override
	protected String getEntryName() throws PortalException {
		Folder folder = getEntry();

		return folder.getName();
	}

	private final DLAppService _dlAppService;
	private final long _folderId;

}