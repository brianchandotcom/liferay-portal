/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.url.provider;

import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;

/**
 * @author Alejandro Tardín
 */
public interface DLFileVersionURLProvider {

	public List<Type> getTypes();

	public String getURL(FileVersion fileVersion, ThemeDisplay themeDisplay);

	public enum Type {

		DOWNLOAD, IMAGE_PREVIEW, PREVIEW, THUMBNAIL

	}

}