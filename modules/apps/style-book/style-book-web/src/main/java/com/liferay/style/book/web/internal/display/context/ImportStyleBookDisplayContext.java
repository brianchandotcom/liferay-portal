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

import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.style.book.web.internal.portlet.zip.StyleBookEntryZipProcessor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class ImportStyleBookDisplayContext {

	public ImportStyleBookDisplayContext(RenderRequest renderRequest) {
		_renderRequest = renderRequest;
	}

	public List<String> getImportResultEntries(
		StyleBookEntryZipProcessor.ImportResultEntry.Status status) {

		List<StyleBookEntryZipProcessor.ImportResultEntry> importResultEntries =
			_getImportResultEntries();

		if (ListUtil.isEmpty(importResultEntries)) {
			return null;
		}

		Stream<StyleBookEntryZipProcessor.ImportResultEntry> stream =
			importResultEntries.stream();

		return stream.filter(
			importResultEntry -> importResultEntry.getStatus() == status
		).map(
			StyleBookEntryZipProcessor.ImportResultEntry::getName
		).collect(
			Collectors.toList()
		);
	}

	private List<StyleBookEntryZipProcessor.ImportResultEntry>
		_getImportResultEntries() {

		if (_importResultEntries != null) {
			return _importResultEntries;
		}

		_importResultEntries =
			(List<StyleBookEntryZipProcessor.ImportResultEntry>)
				SessionMessages.get(_renderRequest, "importResultEntries");

		return _importResultEntries;
	}

	private List<StyleBookEntryZipProcessor.ImportResultEntry>
		_importResultEntries;
	private final RenderRequest _renderRequest;

}