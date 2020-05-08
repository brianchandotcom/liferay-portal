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

import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporterResultEntry;
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

		Map<Integer, List<LayoutPageTemplatesImporterResultEntry>>
			importedLayoutPageTemplatesImporterResultEntriesMap =
				getImportedLayoutPageTemplatesImporterResultEntriesMap();

		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntriesWithWarnings =
				getLayoutPageTemplatesImporterResultEntriesWithWarnings();

		List<LayoutPageTemplatesImporterResultEntry>
			notImportedLayoutPageTemplatesImporterResultEntries =
				getNotImportedLayoutPageTemplatesImporterResultEntries();

		if (MapUtil.isNotEmpty(
				importedLayoutPageTemplatesImporterResultEntriesMap) &&
			ListUtil.isNotEmpty(
				layoutPageTemplatesImporterResultEntriesWithWarnings) &&
			ListUtil.isEmpty(
				notImportedLayoutPageTemplatesImporterResultEntries)) {

			dialogMessage =
				"some-page-templates-were-imported-correctly-and-other-page-" +
					"templates-were-imported-with-warnings";
		}
		else if (ListUtil.isEmpty(
					layoutPageTemplatesImporterResultEntriesWithWarnings) &&
				 ListUtil.isEmpty(
					 notImportedLayoutPageTemplatesImporterResultEntries)) {

			dialogMessage = "all-page-templates-were-imported-correctly";
		}
		else if (MapUtil.isEmpty(
					importedLayoutPageTemplatesImporterResultEntriesMap) &&
				 ListUtil.isEmpty(
					 layoutPageTemplatesImporterResultEntriesWithWarnings)) {

			dialogMessage = "no-page-template-could-be-imported";
		}
		else if (MapUtil.isEmpty(
					importedLayoutPageTemplatesImporterResultEntriesMap)) {

			dialogMessage = "some-page-templates-were-imported-with-warnings";
		}

		return LanguageUtil.get(_httpServletRequest, dialogMessage);
	}

	public String getDialogType() {
		if (MapUtil.isEmpty(
				getImportedLayoutPageTemplatesImporterResultEntriesMap()) &&
			ListUtil.isEmpty(
				getLayoutPageTemplatesImporterResultEntriesWithWarnings())) {

			return "danger";
		}

		if (ListUtil.isEmpty(
				getNotImportedLayoutPageTemplatesImporterResultEntries()) &&
			ListUtil.isEmpty(
				getLayoutPageTemplatesImporterResultEntriesWithWarnings())) {

			return "success";
		}

		return "warning";
	}

	public Map<Integer, List<LayoutPageTemplatesImporterResultEntry>>
		getImportedLayoutPageTemplatesImporterResultEntriesMap() {

		if (_importedLayoutPageTemplatesImporterResultEntriesMap != null) {
			return _importedLayoutPageTemplatesImporterResultEntriesMap;
		}

		Map
			<LayoutPageTemplatesImporterResultEntry.Status,
			 List<LayoutPageTemplatesImporterResultEntry>>
				layoutPageTemplatesImporterResultEntryMap =
					getLayoutPageTemplatesImporterResultEntryMap();

		if (MapUtil.isEmpty(layoutPageTemplatesImporterResultEntryMap)) {
			return null;
		}

		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntries =
				layoutPageTemplatesImporterResultEntryMap.get(
					LayoutPageTemplatesImporterResultEntry.Status.IMPORTED);

		if (layoutPageTemplatesImporterResultEntries == null) {
			return null;
		}

		Map<Integer, List<LayoutPageTemplatesImporterResultEntry>>
			typeLayoutPageTemplatesImporterResultEntryMap = new HashMap<>();

		for (LayoutPageTemplatesImporterResultEntry
				layoutPageTemplatesImporterResultEntry :
					layoutPageTemplatesImporterResultEntries) {

			if (ArrayUtil.isNotEmpty(
					layoutPageTemplatesImporterResultEntry.
						getWarningMessages())) {

				continue;
			}

			List<LayoutPageTemplatesImporterResultEntry>
				typeLayoutPageTemplatesImporterResultEntries =
					new ArrayList<>();

			int type = layoutPageTemplatesImporterResultEntry.getType();

			if (typeLayoutPageTemplatesImporterResultEntryMap.get(type) !=
					null) {

				typeLayoutPageTemplatesImporterResultEntries =
					typeLayoutPageTemplatesImporterResultEntryMap.get(type);
			}

			typeLayoutPageTemplatesImporterResultEntries.add(
				layoutPageTemplatesImporterResultEntry);

			typeLayoutPageTemplatesImporterResultEntryMap.put(
				type, typeLayoutPageTemplatesImporterResultEntries);
		}

		_importedLayoutPageTemplatesImporterResultEntriesMap =
			typeLayoutPageTemplatesImporterResultEntryMap;

		return _importedLayoutPageTemplatesImporterResultEntriesMap;
	}

	public List<LayoutPageTemplatesImporterResultEntry>
		getLayoutPageTemplatesImporterResultEntriesWithWarnings() {

		if (_layoutPageTemplatesImporterResultEntriesWithWarnings != null) {
			return _layoutPageTemplatesImporterResultEntriesWithWarnings;
		}

		Map
			<LayoutPageTemplatesImporterResultEntry.Status,
			 List<LayoutPageTemplatesImporterResultEntry>>
				layoutPageTemplatesImporterResultEntryMap =
					getLayoutPageTemplatesImporterResultEntryMap();

		if (MapUtil.isEmpty(layoutPageTemplatesImporterResultEntryMap)) {
			return null;
		}

		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntries =
				layoutPageTemplatesImporterResultEntryMap.get(
					LayoutPageTemplatesImporterResultEntry.Status.IMPORTED);

		if (layoutPageTemplatesImporterResultEntries == null) {
			return null;
		}

		Stream<LayoutPageTemplatesImporterResultEntry> stream =
			layoutPageTemplatesImporterResultEntries.stream();

		_layoutPageTemplatesImporterResultEntriesWithWarnings = stream.filter(
			layoutPageTemplatesImporterResultEntry -> ArrayUtil.isNotEmpty(
				layoutPageTemplatesImporterResultEntry.getWarningMessages())
		).collect(
			Collectors.toList()
		);

		return _layoutPageTemplatesImporterResultEntriesWithWarnings;
	}

	public Map
		<LayoutPageTemplatesImporterResultEntry.Status,
		 List<LayoutPageTemplatesImporterResultEntry>>
			getLayoutPageTemplatesImporterResultEntryMap() {

		if (MapUtil.isNotEmpty(_layoutPageTemplatesImporterResultEntryMap)) {
			return _layoutPageTemplatesImporterResultEntryMap;
		}

		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntries =
				(List<LayoutPageTemplatesImporterResultEntry>)
					SessionMessages.get(
						_renderRequest,
						"layoutPageTemplatesImporterResultEntries");

		if (layoutPageTemplatesImporterResultEntries == null) {
			return null;
		}

		_layoutPageTemplatesImporterResultEntryMap = new HashMap<>();

		for (LayoutPageTemplatesImporterResultEntry
				layoutPageTemplatesImporterResultEntry :
					layoutPageTemplatesImporterResultEntries) {

			List<LayoutPageTemplatesImporterResultEntry>
				statusLayoutPageTemplatesImporterResultEntries =
					new ArrayList<>();

			LayoutPageTemplatesImporterResultEntry.Status status =
				layoutPageTemplatesImporterResultEntry.getStatus();

			if (_layoutPageTemplatesImporterResultEntryMap.get(status) !=
					null) {

				statusLayoutPageTemplatesImporterResultEntries =
					_layoutPageTemplatesImporterResultEntryMap.get(status);
			}

			statusLayoutPageTemplatesImporterResultEntries.add(
				layoutPageTemplatesImporterResultEntry);

			_layoutPageTemplatesImporterResultEntryMap.put(
				status, statusLayoutPageTemplatesImporterResultEntries);
		}

		return _layoutPageTemplatesImporterResultEntryMap;
	}

	public List<LayoutPageTemplatesImporterResultEntry>
		getNotImportedLayoutPageTemplatesImporterResultEntries() {

		if (_notImportedLayoutPageTemplatesImporterResultEntries != null) {
			return _notImportedLayoutPageTemplatesImporterResultEntries;
		}

		Map
			<LayoutPageTemplatesImporterResultEntry.Status,
			 List<LayoutPageTemplatesImporterResultEntry>>
				layoutPageTemplatesImporterResultEntryMap =
					getLayoutPageTemplatesImporterResultEntryMap();

		if (MapUtil.isEmpty(layoutPageTemplatesImporterResultEntryMap)) {
			return null;
		}

		List<LayoutPageTemplatesImporterResultEntry>
			notImportedLayoutPageTemplatesImporterResultEntries =
				new ArrayList<>();

		for (Map.Entry
				<LayoutPageTemplatesImporterResultEntry.Status,
				 List<LayoutPageTemplatesImporterResultEntry>> entrySet :
					layoutPageTemplatesImporterResultEntryMap.entrySet()) {

			if (entrySet.getKey() !=
					LayoutPageTemplatesImporterResultEntry.Status.IMPORTED) {

				notImportedLayoutPageTemplatesImporterResultEntries.addAll(
					entrySet.getValue());
			}
		}

		return notImportedLayoutPageTemplatesImporterResultEntries;
	}

	public String getSuccessMessage(
		Map.Entry<Integer, List<LayoutPageTemplatesImporterResultEntry>>
			entrySet) {

		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntries = entrySet.getValue();

		return LanguageUtil.format(
			_httpServletRequest, "x-x-s-imported-correctly",
			new Object[] {
				layoutPageTemplatesImporterResultEntries.size(),
				_getTypeLabelKey(entrySet.getKey())
			},
			true);
	}

	public String getWarningMessage(
		String layoutPageTemplatesImporterResultEntryName) {

		return LanguageUtil.format(
			_httpServletRequest, "x-was-imported-with-warnings",
			new Object[] {layoutPageTemplatesImporterResultEntryName}, true);
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
	private Map<Integer, List<LayoutPageTemplatesImporterResultEntry>>
		_importedLayoutPageTemplatesImporterResultEntriesMap;
	private List<LayoutPageTemplatesImporterResultEntry>
		_layoutPageTemplatesImporterResultEntriesWithWarnings;
	private Map
		<LayoutPageTemplatesImporterResultEntry.Status,
		 List<LayoutPageTemplatesImporterResultEntry>>
			_layoutPageTemplatesImporterResultEntryMap;
	private List<LayoutPageTemplatesImporterResultEntry>
		_notImportedLayoutPageTemplatesImporterResultEntries;
	private final RenderRequest _renderRequest;

}