/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.field.attachment;

import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Carlos Correa
 */
public interface AttachmentValidator {

	public String[] getAcceptedFileExtensions(long objectFieldId);

	public DLFolder getDLFolder(
		long objectFieldId, long companyId, long groupId,
		ServiceContext serviceContext, long userId);

	public long getMaximumFileSize(long objectFieldId, boolean signedIn);

	public void validateFileExtension(String fileName, long objectFieldId)
		throws FileExtensionException;

	public void validateFileName(String fileName) throws FileNameException;

	public void validateFileSize(
			String fileName, long fileSize, long objectFieldId,
			boolean signedIn)
		throws FileSizeException;

}