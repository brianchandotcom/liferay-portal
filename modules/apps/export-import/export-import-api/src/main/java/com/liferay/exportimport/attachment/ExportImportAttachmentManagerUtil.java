/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.attachment;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Alvaro Saugar
 */
public class ExportImportAttachmentManagerUtil {

	public static String getFileURL(DLFileEntry dlFileEntry) throws Exception {
		ExportImportAttachmentManager exportImportAttachmentManager =
			_serviceSnapshot.get();

		return exportImportAttachmentManager.getFileURL(dlFileEntry);
	}

	public static String getImageURL(Image image) throws Exception {
		ExportImportAttachmentManager exportImportAttachmentManager =
			_serviceSnapshot.get();

		return exportImportAttachmentManager.getImageURL(image);
	}

	public static URL getURL(String urlString) throws MalformedURLException {
		ExportImportAttachmentManager exportImportAttachmentManager =
			_serviceSnapshot.get();

		return exportImportAttachmentManager.getURL(urlString);
	}

	private static final Snapshot<ExportImportAttachmentManager>
		_serviceSnapshot = new Snapshot<>(
			ExportImportAttachmentManagerUtil.class,
			ExportImportAttachmentManager.class);

}