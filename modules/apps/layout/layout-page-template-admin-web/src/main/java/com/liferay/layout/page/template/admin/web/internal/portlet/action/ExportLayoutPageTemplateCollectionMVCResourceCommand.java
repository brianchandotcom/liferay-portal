/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.portlet.action;

import com.liferay.layout.exporter.LayoutsExporter;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Time;

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
		"javax.portlet.name=" + LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
		"mvc.command.name=/layout_page_template_admin/export_layout_page_template_collection"
	},
	service = MVCResourceCommand.class
)
public class ExportLayoutPageTemplateCollectionMVCResourceCommand
	implements MVCResourceCommand {

	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		long layoutPageTemplateCollectionId = ParamUtil.getLong(
			resourceRequest, "layoutPageTemplateCollectionId");

		try {
			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse,
				"collections-" + Time.getTimestamp() + ".zip",
				new FileInputStream(
					_layoutsExporter.exportLayoutPageTemplateCollections(
						new long[] {layoutPageTemplateCollectionId})),
				ContentTypes.APPLICATION_ZIP);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		return false;
	}

	@Reference
	private LayoutsExporter _layoutsExporter;

}