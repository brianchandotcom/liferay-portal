/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.dto.v1_0.util;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.headless.admin.site.dto.v1_0.ThumbnailURLReference;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Lourdes Fernández Besada
 */
public class FileEntryUtil {

	public static long getPreviewFileEntryId(
			long groupId, String portletId,
			ThumbnailURLReference thumbnailURLReference, long userId)
		throws Exception {

		if (thumbnailURLReference == null) {
			return 0;
		}

		String externalReferenceCode =
			thumbnailURLReference.getExternalReferenceCode();
		String fileBase64 = thumbnailURLReference.getFileBase64();
		String url = thumbnailURLReference.getUrl();

		if (Validator.isNull(externalReferenceCode) &&
			Validator.isNull(fileBase64) && Validator.isNull(url)) {

			return 0;
		}

		FileEntry fileEntry = null;

		if (Validator.isNotNull(externalReferenceCode)) {
			fileEntry =
				PortletFileRepositoryUtil.
					fetchPortletFileEntryByExternalReferenceCode(
						externalReferenceCode, groupId);
		}

		if (fileEntry == null) {
			fileEntry = _getFileEntry(
				externalReferenceCode, fileBase64, groupId, portletId, url,
				userId);
		}

		return fileEntry.getFileEntryId();
	}

	private static byte[] _decodeOrGetByteArray(String fileBase64, String url)
		throws Exception {

		if (Validator.isNotNull(fileBase64)) {
			return Base64.decode(fileBase64);
		}

		if (Validator.isNotNull(url)) {
			try {
				return URLUtil.getByteArray(url);
			}
			catch (IOException ioException) {
				throw new IllegalArgumentException(
					"Unable to download file from " + url, ioException);
			}
		}

		throw new IllegalArgumentException("Unable to resolve file");
	}

	private static FileEntry _getFileEntry(
			String externalReferenceCode, String fileBase64, long groupId,
			String portletId, String url, long userId)
		throws Exception {

		File file = null;

		try {
			file = FileUtil.createTempFile(
				_decodeOrGetByteArray(fileBase64, url));

			String mimeType = MimeTypesUtil.getContentType(file);

			Set<String> extensions = MimeTypesUtil.getExtensions(mimeType);

			String extension = StringPool.BLANK;

			if (!extensions.isEmpty()) {
				Iterator<String> iterator = extensions.iterator();

				extension = iterator.next();
			}

			String fileNamePrefix = externalReferenceCode;

			if (Validator.isNull(externalReferenceCode)) {
				fileNamePrefix = PortalUUIDUtil.generate();
			}

			String fileName = fileNamePrefix + "_preview" + extension;

			return PortletFileRepositoryUtil.addPortletFileEntry(
				externalReferenceCode, groupId, userId, null, 0, portletId,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, file, fileName,
				mimeType, false);
		}
		finally {
			if (file != null) {
				FileUtil.delete(file);
			}
		}
	}

}