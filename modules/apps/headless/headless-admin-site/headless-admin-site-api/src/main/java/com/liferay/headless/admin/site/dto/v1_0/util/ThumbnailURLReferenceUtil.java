/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.dto.v1_0.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.processor.ImageProcessorUtil;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.exportimport.attachment.ExportImportAttachmentManagerUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.headless.admin.site.dto.v1_0.ThumbnailURLReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Lourdes Fernández Besada
 */
public class ThumbnailURLReferenceUtil {

	public static ThumbnailURLReference getFileEntryThumbnailURLReference(
			long fileEntryId)
		throws PortalException {

		if (fileEntryId <= 0) {
			return null;
		}

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);

		Object model = fileEntry.getModel();

		if (!(model instanceof DLFileEntry)) {
			return null;
		}

		DLFileEntry dlFileEntry = (DLFileEntry)model;

		return new ThumbnailURLReference() {
			{
				setExternalReferenceCode(dlFileEntry::getExternalReferenceCode);
				setUrl(
					() -> {
						if (!ExportImportThreadLocal.isExportInProcess() &&
							!ImageProcessorUtil.hasImages(
								fileEntry.getFileVersion())) {

							return null;
						}

						return ExportImportAttachmentManagerUtil.getFileURL(
							dlFileEntry);
					});
			}
		};
	}

}