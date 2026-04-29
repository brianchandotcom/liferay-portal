/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.web.internal.display.context;

/**
 * @author Luis Ortiz
 */
public class StyleBookEditorBreadcrumbDisplayContext {

	public StyleBookEditorBreadcrumbDisplayContext(
		String assetLibraryName, String assetLibraryURL, String styleBookName) {

		_assetLibraryName = assetLibraryName;
		_assetLibraryURL = assetLibraryURL;
		_styleBookName = styleBookName;
	}

	public String getAssetLibraryName() {
		return _assetLibraryName;
	}

	public String getAssetLibraryURL() {
		return _assetLibraryURL;
	}

	public String getStyleBookName() {
		return _styleBookName;
	}

	private final String _assetLibraryName;
	private final String _assetLibraryURL;
	private final String _styleBookName;

}