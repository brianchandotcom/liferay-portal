/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.model;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class LayoutSEOEntryCustomMetaTagProperty implements Serializable {

	public LayoutSEOEntryCustomMetaTagProperty(
		String property, Map<Locale, String> contentMap) {

		_property = property;
		_contentMap = contentMap;
	}

	public Map<Locale, String> getContentMap() {
		return _contentMap;
	}

	public String getProperty() {
		return _property;
	}

	private final Map<Locale, String> _contentMap;
	private final String _property;

}