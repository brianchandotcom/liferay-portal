/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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