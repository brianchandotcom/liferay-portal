/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.document.library.display.context.DLMimeTypeDisplayContext;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.repository.model.FileVersion;

/**
 * @author Eudaldo Alonso
 */
public class DLMimeTypeDisplayContextUtil {

	public static String getCssClassFileMimeType(FileVersion fileVersion) {
		DLMimeTypeDisplayContext dlMimeTypeDisplayContext =
			_dlMimeTypeDisplayContextSnapshot.get();

		return dlMimeTypeDisplayContext.getCssClassFileMimeType(
			fileVersion.getMimeType());
	}

	public static String getIconFileMimeType(FileVersion fileVersion) {
		DLMimeTypeDisplayContext dlMimeTypeDisplayContext =
			_dlMimeTypeDisplayContextSnapshot.get();

		return dlMimeTypeDisplayContext.getIconFileMimeType(
			fileVersion.getMimeType());
	}

	private static final Snapshot<DLMimeTypeDisplayContext>
		_dlMimeTypeDisplayContextSnapshot = new Snapshot<>(
			DLMimeTypeDisplayContextUtil.class, DLMimeTypeDisplayContext.class);

}