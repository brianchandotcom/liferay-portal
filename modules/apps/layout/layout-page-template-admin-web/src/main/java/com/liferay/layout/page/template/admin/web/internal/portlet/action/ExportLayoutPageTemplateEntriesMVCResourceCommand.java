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

package com.liferay.layout.page.template.admin.web.internal.portlet.action;

import com.liferay.layout.exporter.LayoutsExporter;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
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
 * @author Rubén Pulido
 */
@Component(
	property = {
		"javax.portlet.name=" + LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
		"mvc.command.name=/layout_page_template_admin/export_layout_page_template_entries"
	},
	service = MVCResourceCommand.class
)
public class ExportLayoutPageTemplateEntriesMVCResourceCommand
	implements MVCResourceCommand {

	public File getFile(long[] layoutPageTemplateEntryIds)
		throws PortletException {

		try {
			return _layoutsExporter.exportLayoutPageTemplateEntries(
				layoutPageTemplateEntryIds,
				LayoutPageTemplateEntryTypeConstants.TYPE_BASIC);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	public String getFileName(long[] layoutPageTemplateEntryIds) {
		String fileNamePrefix = "page-templates-";

		if (layoutPageTemplateEntryIds.length == 1) {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(layoutPageTemplateEntryIds[0]);

			fileNamePrefix =
				"page-template-" +
					layoutPageTemplateEntry.getLayoutPageTemplateEntryKey() +
						"-";
		}

		return fileNamePrefix + Time.getShortTimestamp() + ".zip";
	}

	public long[] getLayoutPageTemplateEntryIds(
		ResourceRequest resourceRequest) {

		long[] layoutPageTemplateEntryIds = null;

		long layoutPageTemplateEntryEntryId = ParamUtil.getLong(
			resourceRequest, "layoutPageTemplateEntryId");

		if (layoutPageTemplateEntryEntryId > 0) {
			layoutPageTemplateEntryIds = new long[] {
				layoutPageTemplateEntryEntryId
			};
		}
		else {
			layoutPageTemplateEntryIds = ParamUtil.getLongValues(
				resourceRequest, "rowIds");
		}

		return layoutPageTemplateEntryIds;
	}

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		long[] layoutPageTemplateEntryIds = getLayoutPageTemplateEntryIds(
			resourceRequest);

		if (layoutPageTemplateEntryIds.length == 0) {
			return false;
		}

		try {
			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse,
				getFileName(layoutPageTemplateEntryIds),
				new FileInputStream(getFile(layoutPageTemplateEntryIds)),
				ContentTypes.APPLICATION_ZIP);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		return false;
	}

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutsExporter _layoutsExporter;

}