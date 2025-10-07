/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.attachment;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Alvaro Saugar
 */
public class ExportImportAttachmentManagerUtil {

	public static String getFileURL(DLFileEntry dlFileEntry) throws Exception {
		return getService().getFileURL(dlFileEntry);
	}

	public static ExportImportAttachmentManager getService() {
		return _serviceSnapshot.get();
	}

	public static URL getURL(String url) throws MalformedURLException {
		return getService().getURL(url);
	}

	private static final Snapshot<ExportImportAttachmentManager>
		_serviceSnapshot = new Snapshot<>(
			ExportImportAttachmentManagerUtil.class,
			ExportImportAttachmentManager.class);

}