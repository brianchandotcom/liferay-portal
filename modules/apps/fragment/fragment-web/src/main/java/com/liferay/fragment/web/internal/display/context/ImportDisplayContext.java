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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.importer.FragmentsImporterResultEntry;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class ImportDisplayContext {

	public ImportDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
	}

	public List<String> getFragmentsImporterResultEntries(
		FragmentsImporterResultEntry.Status status) {

		List<FragmentsImporterResultEntry> fragmentsImporterResultEntries =
			_getFragmentsImporterResultEntries();

		if (ListUtil.isEmpty(fragmentsImporterResultEntries)) {
			return null;
		}

		Stream<FragmentsImporterResultEntry> stream =
			fragmentsImporterResultEntries.stream();

		return stream.filter(
			fragmentsImporterResultEntry ->
				fragmentsImporterResultEntry.getStatus() == status
		).map(
			FragmentsImporterResultEntry::getName
		).collect(
			Collectors.toList()
		);
	}

	private List<FragmentsImporterResultEntry>
		_getFragmentsImporterResultEntries() {

		if (_fragmentsImporterResultEntries != null) {
			return _fragmentsImporterResultEntries;
		}

		_fragmentsImporterResultEntries =
			(List<FragmentsImporterResultEntry>)SessionMessages.get(
				_renderRequest, "fragmentsImporterResultEntries");

		return _fragmentsImporterResultEntries;
	}

	private List<FragmentsImporterResultEntry> _fragmentsImporterResultEntries;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;

}