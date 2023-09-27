/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.bulk.selection;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.bulk.selection.BaseMultipleEntryBulkSelection;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileShortcut;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class MultipleFileShortcutBulkSelection
	extends BaseMultipleEntryBulkSelection<FileShortcut> {

	public MultipleFileShortcutBulkSelection(
		long[] fileShortcutIds, Map<String, String[]> parameterMap,
		DLAppService dlAppService) {

		super(fileShortcutIds, parameterMap);

		_dlAppService = dlAppService;
	}

	@Override
	public Class<? extends BulkSelectionFactory>
		getBulkSelectionFactoryClass() {

		return FileShortcutBulkSelectionFactory.class;
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		throw new UnsupportedOperationException(
			"File shortcut is not an asset");
	}

	@Override
	protected FileShortcut fetchEntry(long fileShortcutId) {
		try {
			return _dlAppService.getFileShortcut(fileShortcutId);
		}
		catch (NoSuchFolderException noSuchFolderException) {
			if (_log.isWarnEnabled()) {
				_log.warn(noSuchFolderException);
			}

			return null;
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MultipleFileShortcutBulkSelection.class);

	private final DLAppService _dlAppService;

}