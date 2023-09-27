/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.display.context;

import com.liferay.petra.string.StringPool;

/**
 * @author Adam Brandizzi
 */
public class FieldMappingIndexDisplayContext {

	public String getCssClass() {
		return _cssClass;
	}

	public String getName() {
		return _indexName;
	}

	public String getUrl() {
		return _url;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setName(String indexName) {
		_indexName = indexName;
	}

	public void setUrl(String url) {
		_url = url;
	}

	private String _cssClass = StringPool.BLANK;
	private String _indexName;
	private String _url;

}