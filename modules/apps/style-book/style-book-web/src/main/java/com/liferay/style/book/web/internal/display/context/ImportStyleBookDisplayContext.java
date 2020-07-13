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
import com.liferay.style.book.web.internal.portlet.zip.StyleBookImporterResultEntry;

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

	public List<String> getStyleBookImporterResultEntries(
		StyleBookImporterResultEntry.Status status) {

		List<StyleBookImporterResultEntry> styleBookImporterResultEntries =
			_getStyleBookImporterResultEntries();

		if (ListUtil.isEmpty(styleBookImporterResultEntries)) {
			return null;
		}

		Stream<StyleBookImporterResultEntry> stream =
			styleBookImporterResultEntries.stream();

		return stream.filter(
			styleBookImporterResultEntry ->
				styleBookImporterResultEntry.getStatus() == status
		).map(
			StyleBookImporterResultEntry::getName
		).collect(
			Collectors.toList()
		);
	}

	private List<StyleBookImporterResultEntry>
		_getStyleBookImporterResultEntries() {

		if (_styleBookImporterResultEntries != null) {
			return _styleBookImporterResultEntries;
		}

		_styleBookImporterResultEntries =
			(List<StyleBookImporterResultEntry>)SessionMessages.get(
				_renderRequest, "styleBookImporterResultEntries");

		return _styleBookImporterResultEntries;
	}

	private final RenderRequest _renderRequest;
	private List<StyleBookImporterResultEntry> _styleBookImporterResultEntries;

}