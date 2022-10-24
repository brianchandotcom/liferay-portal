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

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.exporter.LayoutsExporter;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Time;

import java.io.File;
import java.io.FileInputStream;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bárbara Cabrera
 */
@Component(
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout_admin/export_layout_utility_page_entries"
	},
	service = MVCResourceCommand.class
)
public class ExportLayoutUtilityPageEntriesMVCResourceCommand
	implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		long[] layoutUtilityPageEntryIds = _getLayoutUtilityPageEntryIds(
			resourceRequest);

		if (layoutUtilityPageEntryIds.length == 0) {
			return false;
		}

		try {
			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse,
				_getFileName(layoutUtilityPageEntryIds),
				new FileInputStream(_getFile(layoutUtilityPageEntryIds)),
				ContentTypes.APPLICATION_ZIP);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		return false;
	}

	private File _getFile(long[] layoutUtilityPageEntryIds)
		throws PortletException {

		try {
			return _layoutsExporter.exportLayoutUtilityPageEntries(
				layoutUtilityPageEntryIds);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	private String _getFileName(long[] layoutUtilityPageEntryIds) {
		String fileNamePrefix = "utility-pages-";

		if (layoutUtilityPageEntryIds.length == 1) {
			LayoutUtilityPageEntry layoutUtilityPageEntry =
				_layoutUtilityPageEntryLocalService.fetchLayoutUtilityPageEntry(
					layoutUtilityPageEntryIds[0]);

			fileNamePrefix =
				"utility-pages-" +
					layoutUtilityPageEntry.getExternalReferenceCode() + "-";
		}

		return fileNamePrefix + Time.getShortTimestamp() + ".zip";
	}

	private long[] _getLayoutUtilityPageEntryIds(
		ResourceRequest resourceRequest) {

		long[] layoutUtilityPageEntryIds = null;

		long layoutUtilityPageEntryId = ParamUtil.getLong(
			resourceRequest, "layoutUtilityPageEntryId");

		if (layoutUtilityPageEntryId > 0) {
			layoutUtilityPageEntryIds = new long[] {layoutUtilityPageEntryId};
		}
		else {
			layoutUtilityPageEntryIds = ParamUtil.getLongValues(
				resourceRequest, "rowIds");
		}

		return layoutUtilityPageEntryIds;
	}

	@Reference
	private LayoutsExporter _layoutsExporter;

	@Reference
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

}