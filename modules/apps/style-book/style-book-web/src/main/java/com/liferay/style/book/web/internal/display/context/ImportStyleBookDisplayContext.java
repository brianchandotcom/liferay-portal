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

package com.liferay.style.book.web.internal.display.context;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessorImportResultEntry;

import java.util.List;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class ImportStyleBookDisplayContext {

	public ImportStyleBookDisplayContext(RenderRequest renderRequest) {
		_renderRequest = renderRequest;
	}

	public List<String> getStyleBookEntryZipProcessorImportResultEntryNames(
		StyleBookEntryZipProcessorImportResultEntry.Status status) {

		List<StyleBookEntryZipProcessorImportResultEntry>
			styleBookEntryZipProcessorImportResultEntries =
				_getStyleBookEntryZipProcessorImportResultEntryNames();

		if (ListUtil.isEmpty(styleBookEntryZipProcessorImportResultEntries)) {
			return null;
		}

		return TransformUtil.transform(
			styleBookEntryZipProcessorImportResultEntries,
			styleBookEntryZipProcessorImportResultEntry -> {
				if (styleBookEntryZipProcessorImportResultEntry.getStatus() ==
						status) {

					return styleBookEntryZipProcessorImportResultEntry.
						getName();
				}

				return null;
			});
	}

	private List<StyleBookEntryZipProcessorImportResultEntry>
		_getStyleBookEntryZipProcessorImportResultEntryNames() {

		if (_styleBookEntryZipProcessorImportResultEntries != null) {
			return _styleBookEntryZipProcessorImportResultEntries;
		}

		_styleBookEntryZipProcessorImportResultEntries =
			(List<StyleBookEntryZipProcessorImportResultEntry>)
				SessionMessages.get(
					_renderRequest,
					"styleBookEntryZipProcessorImportResultEntries");

		return _styleBookEntryZipProcessorImportResultEntries;
	}

	private final RenderRequest _renderRequest;
	private List<StyleBookEntryZipProcessorImportResultEntry>
		_styleBookEntryZipProcessorImportResultEntries;

}