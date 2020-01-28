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

import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
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

import java.util.Map;

import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = ExportUtil.class)
public class ExportUtil {

	public File exportPageDefinitions(
			Map<Long, PageDefinition> pageDefinitionsMap)
		throws PortletException {

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		try {
			for (Map.Entry<Long, PageDefinition> entry :
					pageDefinitionsMap.entrySet()) {

				PageDefinition pageDefinition = entry.getValue();

				LayoutPageTemplateEntry layoutPageTemplateEntry =
					_layoutPageTemplateEntryLocalService.
						fetchLayoutPageTemplateEntryByPlid(entry.getKey());

				_populateZipWriter(
					pageDefinition,
					layoutPageTemplateEntry.getPreviewFileEntryId(), zipWriter,
					pageDefinition.getCollectionName());
			}

			zipWriter.finish();

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	private FileEntry _getPreviewFileEntry(long previewFileEntryId) {
		if (previewFileEntryId <= 0) {
			return null;
		}

		try {
			return PortletFileRepositoryUtil.getPortletFileEntry(
				previewFileEntryId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get file entry preview", portalException);
			}
		}

		return null;
	}

	private void _populateZipWriter(
			PageDefinition pageDefinition, long previewFileEntryId,
			ZipWriter zipWriter, String path)
		throws Exception {

		path = path + StringPool.SLASH + pageDefinition.getName();

		JSONObject jsonObject = JSONUtil.put("name", pageDefinition.getName());

		FileEntry previewFileEntry = _getPreviewFileEntry(previewFileEntryId);

		zipWriter.addEntry(
			path + "/page-definition.json", jsonObject.toString());

		if (previewFileEntry != null) {
			zipWriter.addEntry(
				path + "/thumbnail." + previewFileEntry.getExtension(),
				previewFileEntry.getContentStream());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(ExportUtil.class);

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}