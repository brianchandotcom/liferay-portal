/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.exportimport.attachment.ExportImportAttachmentManagerUtil;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.ThumbnailURLReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Lourdes Fernández Besada
 */
public class ThumbnailUtil {

	public static ItemExternalReference
			getPortletFileEntryItemExternalReference(long fileEntryId)
		throws PortalException {

		if (fileEntryId <= 0) {
			return null;
		}

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			fileEntryId);

		return new ItemExternalReference() {
			{
				setClassName(() -> FileEntry.class.getName());
				setExternalReferenceCode(fileEntry::getExternalReferenceCode);
			}
		};
	}

	public static ThumbnailURLReference getPortletFileEntryURLReference(
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