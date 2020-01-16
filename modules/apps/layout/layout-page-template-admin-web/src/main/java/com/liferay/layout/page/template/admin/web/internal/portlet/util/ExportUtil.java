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

package com.liferay.layout.page.template.admin.web.internal.portlet.util;

import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;

import java.io.File;

import java.util.List;

import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = ExportUtil.class)
public class ExportUtil {

	public File exportLayoutPageTemplateEntries(
			List<LayoutPageTemplateEntry> layoutPageTemplateEntries)
		throws PortletException {

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		try {
			for (LayoutPageTemplateEntry layoutPageTemplateEntry :
					layoutPageTemplateEntries) {

				LayoutPageTemplateCollection layoutPageTemplateCollection =
					_layoutPageTemplateCollectionService.
						fetchLayoutPageTemplateCollection(
							layoutPageTemplateEntry.
								getLayoutPageTemplateCollectionId());

				_populateZipWriter(
					layoutPageTemplateEntry, zipWriter,
					layoutPageTemplateCollection.getName());
			}

			zipWriter.finish();

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	private FileEntry _getPreviewFileEntry(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		if (layoutPageTemplateEntry.getPreviewFileEntryId() <= 0) {
			return null;
		}

		try {
			return PortletFileRepositoryUtil.getPortletFileEntry(
				layoutPageTemplateEntry.getPreviewFileEntryId());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get file entry preview", portalException);
			}
		}

		return null;
	}

	private void _populateZipWriter(
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			ZipWriter zipWriter, String path)
		throws Exception {

		path = path + StringPool.SLASH + layoutPageTemplateEntry.getName();

		JSONObject jsonObject = JSONUtil.put(
			"name", layoutPageTemplateEntry.getName());

		FileEntry previewFileEntry = _getPreviewFileEntry(
			layoutPageTemplateEntry);

		if (previewFileEntry != null) {
			jsonObject.put(
				"thumbnailPath",
				"thumbnail." + previewFileEntry.getExtension());
		}

		zipWriter.addEntry(
			path + "/layout-template.json", jsonObject.toString());

		if (previewFileEntry != null) {
			zipWriter.addEntry(
				path + "/thumbnail." + previewFileEntry.getExtension(),
				previewFileEntry.getContentStream());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(ExportUtil.class);

	@Reference
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

}