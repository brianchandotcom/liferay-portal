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

import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.bulk.selection.EmptyBulkSelection;
import com.liferay.document.library.internal.bulk.selection.util.BulkSelectionFactoryUtil;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileShortcut",
	service = {
		BulkSelectionFactory.class, FileShortcutBulkSelectionFactory.class
	}
)
public class FileShortcutBulkSelectionFactory
	implements BulkSelectionFactory<FileShortcut> {

	@Override
	public BulkSelection<FileShortcut> create(
		Map<String, String[]> parameterMap) {

		if (BulkSelectionFactoryUtil.isSelectAll(parameterMap)) {
			return new FolderFileShortcutBulkSelection(
				BulkSelectionFactoryUtil.getRepositoryId(parameterMap),
				BulkSelectionFactoryUtil.getFolderId(parameterMap),
				parameterMap, _repositoryProvider, _dlAppService);
		}

		if (!parameterMap.containsKey("rowIdsDLFileShortcut")) {
			return new EmptyBulkSelection<>();
		}

		String[] values = parameterMap.get("rowIdsDLFileShortcut");

		return _getFileShortcutSelection(values, parameterMap);
	}

	private BulkSelection<FileShortcut> _getFileShortcutSelection(
		String[] values, Map<String, String[]> parameterMap) {

		if (values.length == 1) {
			values = StringUtil.split(values[0]);
		}

		long[] fileShortcutIds = GetterUtil.getLongValues(values);

		if (fileShortcutIds.length == 1) {
			return new SingleFileShortcutBulkSelection(
				fileShortcutIds[0], parameterMap, _dlAppService);
		}

		return new MultipleFileShortcutBulkSelection(
			fileShortcutIds, parameterMap, _dlAppService);
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private RepositoryProvider _repositoryProvider;

}