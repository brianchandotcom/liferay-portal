/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.util;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.exportimport.attachment.ExportImportAttachmentManagerUtil;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.URLReference;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.tika.Tika;

/**
 * @author Lourdes Fernández Besada
 */
public class FileEntryUtil {

	public static FileEntry getFileEntryLar(
			long groupId, String resourceName, ServiceContext serviceContext,
			URLReference urlReference, User user)
		throws IOException, PortalException {

		URL url = ExportImportAttachmentManagerUtil.getURL(
			urlReference.getUrl());

		URLConnection urlConnection = url.openConnection();

		if ((urlConnection instanceof HttpURLConnection httpURLConnection) &&
			(httpURLConnection.getResponseCode() !=
				HttpURLConnection.HTTP_OK)) {

			throw new IllegalArgumentException(
				StringBundler.concat(
					"Unable to download file from ", urlReference.getUrl(),
					", unexpected HTTP code: ",
					httpURLConnection.getResponseCode()));
		}

		byte[] fileBytes = StreamUtil.toByteArray(
			urlConnection.getInputStream());

		Tika tika = new Tika();

		String mimeType = tika.detect(fileBytes);

		String extension = mimeType.split("/")[1];

		long repositoryId = groupId;
		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		String fileName =
			urlReference.getExternalReferenceCode() + "_preview." + extension;

		ServiceContext sc = new ServiceContext();

		sc.setScopeGroupId(groupId);

		sc.setAddGroupPermissions(true);
		sc.setAddGuestPermissions(false);

		return DLAppLocalServiceUtil.addFileEntry(
			urlReference.getExternalReferenceCode(), user.getUserId(),
			repositoryId, folderId, resourceName + "_" + fileName, mimeType,
			fileName, null, null, null, fileBytes, null, null, null,
			serviceContext);
	}

	public static long getPreviewFileEntryId(
			long groupId, ItemExternalReference itemExternalReference)
		throws Exception {

		if ((itemExternalReference == null) ||
			Validator.isNull(
				itemExternalReference.getExternalReferenceCode())) {

			return 0;
		}

		FileEntry fileEntry =
			PortletFileRepositoryUtil.
				fetchPortletFileEntryByExternalReferenceCode(
					itemExternalReference.getExternalReferenceCode(), groupId);

		if (fileEntry == null) {
			throw new UnsupportedOperationException();
		}

		return fileEntry.getFileEntryId();
	}

	public static long getPreviewFileEntryId(
			long groupId, String resourceName, ServiceContext serviceContext,
			URLReference urlReference, User user)
		throws Exception {

		if ((urlReference == null) ||
			Validator.isNull(urlReference.getExternalReferenceCode())) {

			return 0;
		}

		FileEntry fileEntry =
			PortletFileRepositoryUtil.
				fetchPortletFileEntryByExternalReferenceCode(
					urlReference.getExternalReferenceCode(), groupId);

		if (fileEntry == null) {
			fileEntry = getFileEntryLar(
				groupId, resourceName, serviceContext, urlReference, user);
		}

		return fileEntry.getFileEntryId();
	}

}