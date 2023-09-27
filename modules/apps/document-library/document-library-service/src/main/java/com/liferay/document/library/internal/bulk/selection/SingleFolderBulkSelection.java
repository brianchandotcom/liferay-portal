/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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