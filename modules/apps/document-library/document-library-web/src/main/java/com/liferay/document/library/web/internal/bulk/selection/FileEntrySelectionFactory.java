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

package com.liferay.document.library.web.internal.bulk.selection;

import com.liferay.bulk.selection.Selection;
import com.liferay.bulk.selection.SelectionFactory;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.language.Language;
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
	property = "model.class.name=com.liferay.portal.kernel.repository.model.FileEntry",
	service = SelectionFactory.class
)
public class FileEntrySelectionFactory implements SelectionFactory<FileEntry> {

	public Selection<FileEntry> create(Map<String, String[]> parameterMap) {
		if (!parameterMap.containsKey("rowIdsFileEntry")) {
			throw new IllegalArgumentException();
		}

		String[] values = parameterMap.get("rowIdsFileEntry");

		if (values.length == 1) {
			values = StringUtil.split(values[0]);
		}

		long[] rowIdsFileEntries = GetterUtil.getLongValues(values);

		if (rowIdsFileEntries.length == 1) {
			return new SingleFileEntrySelection(
				rowIdsFileEntries[0], _resourceBundleLoader, _language,
				_dlAppService);
		}

		return new MultipleFileEntrySelection(
			rowIdsFileEntries, _resourceBundleLoader, _language, _dlAppService);
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private Language _language;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.document.library.web)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}