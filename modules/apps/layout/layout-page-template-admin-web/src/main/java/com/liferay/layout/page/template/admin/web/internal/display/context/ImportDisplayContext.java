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

package com.liferay.layout.page.template.admin.web.internal.display.context;

import com.liferay.layout.importer.LayoutsImporterResultEntry;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.petra.string.StringPool;
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
 * @author Jürgen Kappler
 */
public class ImportDisplayContext {

	public ImportDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
	}

	public String getDialogMessage() {
		String dialogMessage =
			"some-page-templates-could-not-be-imported-but-other-page-" +
				"templates-were-imported-correctly-or-with-warnings";

		Map<Integer, List<LayoutsImporterResultEntry>>
			importedLayoutsImporterResultEntriesMap =
				getImportedLayoutsImporterResultEntriesMap();

		List<LayoutsImporterResultEntry>
			layoutsImporterResultEntriesWithWarnings =
				getLayoutsImporterResultEntriesWithWarnings();

		List<LayoutsImporterResultEntry>
			notImportedLayoutsImporterResultEntries =
				getNotImportedLayoutsImporterResultEntries();

		if (MapUtil.isNotEmpty(importedLayoutsImporterResultEntriesMap) &&
			ListUtil.isNotEmpty(layoutsImporterResultEntriesWithWarnings) &&
			ListUtil.isEmpty(notImportedLayoutsImporterResultEntries)) {

			dialogMessage =
				"some-page-templates-were-imported-correctly-and-other-page-" +
					"templates-were-imported-with-warnings";
		}
		else if (ListUtil.isEmpty(layoutsImporterResultEntriesWithWarnings) &&
				 ListUtil.isEmpty(notImportedLayoutsImporterResultEntries)) {

			dialogMessage = "all-page-templates-were-imported-correctly";
		}
		else if (MapUtil.isEmpty(importedLayoutsImporterResultEntriesMap) &&
				 ListUtil.isEmpty(layoutsImporterResultEntriesWithWarnings)) {

			dialogMessage = "no-page-template-could-be-imported";
		}
		else if (MapUtil.isEmpty(importedLayoutsImporterResultEntriesMap)) {
			dialogMessage = "some-page-templates-were-imported-with-warnings";
		}

		return LanguageUtil.get(_httpServletRequest, dialogMessage);
	}

	public String getDialogType() {
		if (MapUtil.isEmpty(getImportedLayoutsImporterResultEntriesMap()) &&
			ListUtil.isEmpty(getLayoutsImporterResultEntriesWithWarnings())) {

			return "danger";
		}

		if (ListUtil.isEmpty(getNotImportedLayoutsImporterResultEntries()) &&
			ListUtil.isEmpty(getLayoutsImporterResultEntriesWithWarnings())) {

			return "success";
		}

		return "warning";
	}

	public Map<Integer, List<LayoutsImporterResultEntry>>
		getImportedLayoutsImporterResultEntriesMap() {

		if (_importedLayoutsImporterResultEntriesMap != null) {
			return _importedLayoutsImporterResultEntriesMap;
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

		Map<Integer, List<LayoutsImporterResultEntry>>
			typeLayoutsImporterResultEntryMap = new HashMap<>();

		for (LayoutsImporterResultEntry layoutsImporterResultEntry :
				layoutsImporterResultEntries) {

			if (ArrayUtil.isNotEmpty(
					layoutsImporterResultEntry.getWarningMessages())) {

				continue;
			}

			List<LayoutsImporterResultEntry> typeLayoutsImporterResultEntries =
				new ArrayList<>();

			int type = layoutsImporterResultEntry.getType();

			if (typeLayoutsImporterResultEntryMap.get(type) != null) {
				typeLayoutsImporterResultEntries =
					typeLayoutsImporterResultEntryMap.get(type);
			}

			typeLayoutsImporterResultEntries.add(layoutsImporterResultEntry);

			typeLayoutsImporterResultEntryMap.put(
				type, typeLayoutsImporterResultEntries);
		}

		_importedLayoutsImporterResultEntriesMap =
			typeLayoutsImporterResultEntryMap;

		return _importedLayoutsImporterResultEntriesMap;
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
				_renderRequest, "layoutsImporterResultEntries");

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
		Map.Entry<Integer, List<LayoutsImporterResultEntry>> entrySet) {

		List<LayoutsImporterResultEntry> layoutsImporterResultEntries =
			entrySet.getValue();

		return LanguageUtil.format(
			_httpServletRequest, "x-x-s-imported-correctly",
			new Object[] {
				layoutsImporterResultEntries.size(),
				_getTypeLabelKey(entrySet.getKey())
			},
			true);
	}

	public String getWarningMessage(String layoutsImporterResultEntryName) {
		return LanguageUtil.format(
			_httpServletRequest, "x-was-imported-with-warnings",
			new Object[] {layoutsImporterResultEntryName}, true);
	}

	private String _getTypeLabelKey(int type) {
		if (type == LayoutPageTemplateEntryTypeConstants.TYPE_BASIC) {
			return "page-template";
		}
		else if (type ==
					LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE) {

			return "display-page-template";
		}
		else if (type ==
					LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT) {

			return "master-page";
		}

		return StringPool.BLANK;
	}

	private final HttpServletRequest _httpServletRequest;
	private Map<Integer, List<LayoutsImporterResultEntry>>
		_importedLayoutsImporterResultEntriesMap;
	private List<LayoutsImporterResultEntry>
		_layoutsImporterResultEntriesWithWarnings;
	private Map
		<LayoutsImporterResultEntry.Status, List<LayoutsImporterResultEntry>>
			_layoutsImporterResultEntryMap;
	private List<LayoutsImporterResultEntry>
		_notImportedLayoutsImporterResultEntries;
	private final RenderRequest _renderRequest;

}