/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.display.context;

/**
 * @author Roberto Díaz
 */
public interface DLMimeTypeDisplayContext {

	public String getCssClassFileMimeType(String mimeType);

	public default String getIconFileMimeType(String mimeType) {
		return "document-default";
	}

}