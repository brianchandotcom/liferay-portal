/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.common.spi.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.exportimport.attachment.ExportImportAttachmentManagerUtil;
import com.liferay.headless.admin.site.dto.v1_0.ThumbnailURLReference;
import com.liferay.portal.kernel.exception.PortalException;

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

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
			fileEntryId);

		return new ThumbnailURLReference() {
			{
				setExternalReferenceCode(dlFileEntry::getExternalReferenceCode);
				setUrl(
					() -> ExportImportAttachmentManagerUtil.getFileURL(
						dlFileEntry));
			}
		};
	}

}