/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.attachment;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.model.Image;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Alejandro Tardín
 */
public interface ExportImportAttachmentManager {

	public String getFileURL(DLFileEntry dlFileEntry) throws Exception;

	public String getImageURL(Image image) throws Exception;

	public URL getURL(String url) throws MalformedURLException;

}