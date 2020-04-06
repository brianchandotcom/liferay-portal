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
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
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

	public Map<Integer, List<LayoutPageTemplatesImporterResultEntry>>
		getImportedLayoutPageTemplatesImporterResultEntries() {

		Map
			<LayoutPageTemplatesImporterResultEntry.Status,
			 List<LayoutPageTemplatesImporterResultEntry>>
				layoutPageTemplatesImporterResultEntryMap =
					_getLayoutPageTemplatesImporterResultEntryMap();

		if (MapUtil.isEmpty(layoutPageTemplatesImporterResultEntryMap)) {
			return null;
		}

		List<LayoutPageTemplatesImporterResultEntry>
			importedLayoutPageTemplatesImporterResultEntries =
				layoutPageTemplatesImporterResultEntryMap.get(
					LayoutPageTemplatesImporterResultEntry.Status.IMPORTED);

		if (importedLayoutPageTemplatesImporterResultEntries == null) {
			return null;
		}

		Stream<LayoutPageTemplatesImporterResultEntry> stream =
			importedLayoutPageTemplatesImporterResultEntries.stream();

		return stream.collect(
			Collectors.toMap(
				LayoutPageTemplatesImporterResultEntry::getType,
				layoutPageTemplatesImporterResultEntry ->
					importedLayoutPageTemplatesImporterResultEntries));
	}

	public List<LayoutPageTemplatesImporterResultEntry>
		getNotImportedLayoutPageTemplatesImporterResultEntries() {

		Map
			<LayoutPageTemplatesImporterResultEntry.Status,
			 List<LayoutPageTemplatesImporterResultEntry>>
				layoutPageTemplatesImporterResultEntryMap =
					_getLayoutPageTemplatesImporterResultEntryMap();

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

	private Map
		<LayoutPageTemplatesImporterResultEntry.Status,
		 List<LayoutPageTemplatesImporterResultEntry>>
			_getLayoutPageTemplatesImporterResultEntryMap() {

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

		Stream<LayoutPageTemplatesImporterResultEntry> stream =
			layoutPageTemplatesImporterResultEntries.stream();

		_layoutPageTemplatesImporterResultEntryMap = stream.collect(
			Collectors.toMap(
				LayoutPageTemplatesImporterResultEntry::getStatus,
				layoutPageTemplatesImporterResultEntry ->
					layoutPageTemplatesImporterResultEntries));

		return _layoutPageTemplatesImporterResultEntryMap;
	}

	private String _getTypeLabelKey(int type) {
		if (type == LayoutPageTemplateEntryTypeConstants.TYPE_BASIC) {
			return "page-template";
		}
		else if (type ==
					LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT) {

			return "master-page";
		}

		return StringPool.BLANK;
	}

	private final HttpServletRequest _httpServletRequest;
	private Map
		<LayoutPageTemplatesImporterResultEntry.Status,
		 List<LayoutPageTemplatesImporterResultEntry>>
			_layoutPageTemplatesImporterResultEntryMap;
	private final RenderRequest _renderRequest;

}