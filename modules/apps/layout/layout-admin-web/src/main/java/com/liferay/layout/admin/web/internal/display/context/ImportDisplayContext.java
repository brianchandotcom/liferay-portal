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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.layout.importer.LayoutsImporterResultEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bárbara Cabrera
 */
public class ImportDisplayContext {

	public ImportDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
	}

	public String getDialogMessage() {
		String dialogMessage =
			"some-utility-pages-could-not-be-imported-but-other-utility-" +
				"pages-were-imported-correctly-or-with-warnings";

		List<LayoutsImporterResultEntry> importedLayoutsImporterResultEntries =
			getImportedLayoutsImporterResultEntries();

		List<LayoutsImporterResultEntry>
			layoutsImporterResultEntriesWithWarnings =
				getLayoutsImporterResultEntriesWithWarnings();

		List<LayoutsImporterResultEntry>
			notImportedLayoutsImporterResultEntries =
				getNotImportedLayoutsImporterResultEntries();

		if (ListUtil.isNotEmpty(importedLayoutsImporterResultEntries) &&
			ListUtil.isNotEmpty(layoutsImporterResultEntriesWithWarnings) &&
			ListUtil.isEmpty(notImportedLayoutsImporterResultEntries)) {

			dialogMessage =
				"some-utility-pages-were-imported-correctly-and-other-" +
					"utility-pages-were-imported-with-warnings";
		}
		else if (ListUtil.isEmpty(layoutsImporterResultEntriesWithWarnings) &&
				 ListUtil.isEmpty(notImportedLayoutsImporterResultEntries)) {

			dialogMessage = "all-utility-pages-were-imported-correctly";
		}
		else if (ListUtil.isEmpty(importedLayoutsImporterResultEntries) &&
				 ListUtil.isEmpty(layoutsImporterResultEntriesWithWarnings)) {

			dialogMessage = "no-utility-pages-could-be-imported";
		}
		else if (ListUtil.isEmpty(importedLayoutsImporterResultEntries)) {
			dialogMessage = "some-utility-pages-were-imported-with-warnings";
		}

		return LanguageUtil.get(_httpServletRequest, dialogMessage);
	}

	public String getDialogType() {
		if (ListUtil.isEmpty(getImportedLayoutsImporterResultEntries()) &&
			ListUtil.isEmpty(getLayoutsImporterResultEntriesWithWarnings())) {

			return "danger";
		}

		if (ListUtil.isEmpty(getNotImportedLayoutsImporterResultEntries()) &&
			ListUtil.isEmpty(getLayoutsImporterResultEntriesWithWarnings())) {

			return "success";
		}

		return "warning";
	}

	public List<LayoutsImporterResultEntry>
		getImportedLayoutsImporterResultEntries() {

		if (_importedLayoutsImporterResultEntries != null) {
			return _importedLayoutsImporterResultEntries;
		}

		Map<LayoutsImporterResultEntry.Status, List<LayoutsImporterResultEntry>>
			layoutsImporterResultEntryMap = getLayoutsImporterResultEntryMap();

		if (MapUtil.isEmpty(layoutsImporterResultEntryMap)) {
			return null;
		}

		List<LayoutsImporterResultEntry> layoutsImporterResultEntries =
			layoutsImporterResultEntryMap.get(
				LayoutsImporterResultEntry.Status.IMPORTED);

		if (layoutsImporterResultEntries == null) {
			return null;
		}

		List<LayoutsImporterResultEntry> importedLayoutsImporterResultEntries =
			new ArrayList<>();

		for (LayoutsImporterResultEntry layoutsImporterResultEntry :
				layoutsImporterResultEntries) {

			if (ArrayUtil.isEmpty(
					layoutsImporterResultEntry.getWarningMessages())) {

				importedLayoutsImporterResultEntries.add(
					layoutsImporterResultEntry);
			}
		}

		return importedLayoutsImporterResultEntries;
	}

	public List<LayoutsImporterResultEntry>
		getLayoutsImporterResultEntriesWithWarnings() {

		if (_layoutsImporterResultEntriesWithWarnings != null) {
			return _layoutsImporterResultEntriesWithWarnings;
		}

		Map<LayoutsImporterResultEntry.Status, List<LayoutsImporterResultEntry>>
			layoutsImporterResultEntryMap = getLayoutsImporterResultEntryMap();

		if (MapUtil.isEmpty(layoutsImporterResultEntryMap)) {
			return null;
		}

		List<LayoutsImporterResultEntry> layoutsImporterResultEntries =
			layoutsImporterResultEntryMap.get(
				LayoutsImporterResultEntry.Status.IMPORTED);

		if (layoutsImporterResultEntries == null) {
			return null;
		}

		Stream<LayoutsImporterResultEntry> stream =
			layoutsImporterResultEntries.stream();

		_layoutsImporterResultEntriesWithWarnings = stream.filter(
			layoutsImporterResultEntry -> ArrayUtil.isNotEmpty(
				layoutsImporterResultEntry.getWarningMessages())
		).collect(
			Collectors.toList()
		);

		return _layoutsImporterResultEntriesWithWarnings;
	}

	public Map
		<LayoutsImporterResultEntry.Status, List<LayoutsImporterResultEntry>>
			getLayoutsImporterResultEntryMap() {

		if (MapUtil.isNotEmpty(_layoutsImporterResultEntryMap)) {
			return _layoutsImporterResultEntryMap;
		}

		List<LayoutsImporterResultEntry> layoutsImporterResultEntries =
			(List<LayoutsImporterResultEntry>)SessionMessages.get(
				_renderRequest, "layoutUtilityPageImporterResultEntries");

		if (layoutsImporterResultEntries == null) {
			return null;
		}

		_layoutsImporterResultEntryMap = new HashMap<>();

		for (LayoutsImporterResultEntry layoutsImporterResultEntry :
				layoutsImporterResultEntries) {

			List<LayoutsImporterResultEntry>
				statusLayoutsImporterResultEntries = new ArrayList<>();

			LayoutsImporterResultEntry.Status status =
				layoutsImporterResultEntry.getStatus();

			if (_layoutsImporterResultEntryMap.get(status) != null) {
				statusLayoutsImporterResultEntries =
					_layoutsImporterResultEntryMap.get(status);
			}

			statusLayoutsImporterResultEntries.add(layoutsImporterResultEntry);

			_layoutsImporterResultEntryMap.put(
				status, statusLayoutsImporterResultEntries);
		}

		return _layoutsImporterResultEntryMap;
	}

	public List<LayoutsImporterResultEntry>
		getNotImportedLayoutsImporterResultEntries() {

		if (_notImportedLayoutsImporterResultEntries != null) {
			return _notImportedLayoutsImporterResultEntries;
		}

		Map<LayoutsImporterResultEntry.Status, List<LayoutsImporterResultEntry>>
			layoutsImporterResultEntryMap = getLayoutsImporterResultEntryMap();

		if (MapUtil.isEmpty(layoutsImporterResultEntryMap)) {
			return null;
		}

		List<LayoutsImporterResultEntry>
			notImportedLayoutsImporterResultEntries = new ArrayList<>();

		for (Map.Entry
				<LayoutsImporterResultEntry.Status,
				 List<LayoutsImporterResultEntry>> entrySet :
					layoutsImporterResultEntryMap.entrySet()) {

			if (entrySet.getKey() !=
					LayoutsImporterResultEntry.Status.IMPORTED) {

				notImportedLayoutsImporterResultEntries.addAll(
					entrySet.getValue());
			}
		}

		return notImportedLayoutsImporterResultEntries;
	}

	public String getSuccessMessage(
		List<LayoutsImporterResultEntry> layoutsImporterResultEntries) {

		return LanguageUtil.format(
			_httpServletRequest, "x-x-s-imported-correctly",
			new String[] {
				"utility-page",
				String.valueOf(layoutsImporterResultEntries.size())
			},
			true);
	}

	public String getWarningMessage(String layoutsImporterResultEntryName) {
		return LanguageUtil.format(
			_httpServletRequest, "x-was-imported-with-warnings",
			new Object[] {layoutsImporterResultEntryName}, true);
	}

	private final HttpServletRequest _httpServletRequest;
	private List<LayoutsImporterResultEntry>
		_importedLayoutsImporterResultEntries;
	private List<LayoutsImporterResultEntry>
		_layoutsImporterResultEntriesWithWarnings;
	private Map
		<LayoutsImporterResultEntry.Status, List<LayoutsImporterResultEntry>>
			_layoutsImporterResultEntryMap;
	private List<LayoutsImporterResultEntry>
		_notImportedLayoutsImporterResultEntries;
	private final RenderRequest _renderRequest;

}