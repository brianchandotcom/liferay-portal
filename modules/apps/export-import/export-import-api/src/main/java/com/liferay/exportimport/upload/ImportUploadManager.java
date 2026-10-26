/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.upload;

import com.liferay.portal.kernel.exception.PortalException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.function.LongConsumer;

/**
 * @author Jaime León
 */
public interface ImportUploadManager {

	public ImportUpload addImportUpload(
			String contentType, String fileName, long groupId, long plid,
			String portletId, long size, long userId)
		throws PortalException;

	public void deleteImportUpload(
			long groupId, String importUploadId, long userId)
		throws PortalException;

	public ImportUpload fetchImportUpload(
			long groupId, String importUploadId, long userId)
		throws IOException, PortalException;

	public ImportUpload putChunk(
			int chunkIndex, long groupId, ImportUpload importUpload,
			InputStream inputStream, long userId)
		throws IOException, PortalException;

	public void writeTo(
			File file, long groupId, String importUploadId, long userId)
		throws IOException, PortalException;

	public void writeTo(
			LongConsumer bytesWrittenConsumer, File file, long groupId,
			String importUploadId, long userId)
		throws IOException, PortalException;

}