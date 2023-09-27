/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.bulk.selection;

import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.bulk.selection.EmptyBulkSelection;
import com.liferay.document.library.internal.bulk.selection.util.BulkSelectionFactoryUtil;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFolder",
	service = BulkSelectionFactory.class
)
public class FolderBulkSelectionFactory
	implements BulkSelectionFactory<Folder> {

	@Override
	public BulkSelection<Folder> create(Map<String, String[]> parameterMap) {
		if (BulkSelectionFactoryUtil.isSelectAll(parameterMap)) {
			return new FolderFolderBulkSelection(
				BulkSelectionFactoryUtil.getRepositoryId(parameterMap),
				BulkSelectionFactoryUtil.getFolderId(parameterMap),
				parameterMap, _repositoryProvider, _dlAppService);
		}

		if (!parameterMap.containsKey("rowIdsFolder")) {
			return new EmptyBulkSelection<>();
		}

		String[] values = parameterMap.get("rowIdsFolder");

		return _getFolderSelection(values, parameterMap);
	}

	private BulkSelection<Folder> _getFolderSelection(
		String[] values, Map<String, String[]> parameterMap) {

		if (values.length == 1) {
			values = StringUtil.split(values[0]);
		}

		long[] folderIds = GetterUtil.getLongValues(values);

		if (folderIds.length == 1) {
			return new SingleFolderBulkSelection(
				folderIds[0], parameterMap, _dlAppService);
		}

		return new MultipleFolderBulkSelection(
			folderIds, parameterMap, _dlAppService);
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private RepositoryProvider _repositoryProvider;

}