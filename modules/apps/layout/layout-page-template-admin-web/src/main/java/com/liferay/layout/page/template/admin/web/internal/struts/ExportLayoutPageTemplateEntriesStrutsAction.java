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

package com.liferay.layout.page.template.admin.web.internal.struts;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentCompositionService;
import com.liferay.layout.page.template.admin.web.internal.exporter.LayoutPageTemplatesExporter;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = "path=/portal/layout_page_template/export_layout_page_template_entries",
	service = StrutsAction.class
)
public class ExportLayoutPageTemplateEntriesStrutsAction
	implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(httpServletRequest, "groupId");

		File file = _layoutPageTemplatesExporter.exportGroupLayoutPageTemplates(
			groupId);

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter(file);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getFragmentCollections(groupId);

		for (FragmentCollection fragmentCollection : fragmentCollections) {
			fragmentCollection.populateZipWriter(zipWriter, "fragments");
		}

		zipWriter.finish();

		try {
			file = zipWriter.getFile();

			try (InputStream inputStream = new FileInputStream(file)) {
				ServletResponseUtil.sendFile(
					httpServletRequest, httpServletResponse,
					"page-templates-" + Time.getTimestamp() + ".zip",
					inputStream, file.length(), ContentTypes.APPLICATION_ZIP);
			}
		}
		catch (Exception exception) {
			_portal.sendError(
				exception, httpServletRequest, httpServletResponse);
		}

		return null;
	}

	@Reference
	private FragmentCollectionService _fragmentCollectionService;

	@Reference
	private FragmentCompositionService _fragmentCompositionService;

	@Reference
	private LayoutPageTemplatesExporter _layoutPageTemplatesExporter;

	@Reference
	private Portal _portal;

}