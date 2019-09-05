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

package com.liferay.portal.search.engine.adapter.document;

import com.liferay.portal.search.document.Document;

/**
 * @author Bryan Engler
 */
public class GetDocumentResponse implements DocumentResponse {

	public GetDocumentResponse(boolean exists) {
		_exists = exists;
	}

	public Document getDocument() {
		return _document;
	}

	public String getSource() {
		return _source;
	}

	public long getVersion() {
		return _version;
	}

	public boolean isExists() {
		return _exists;
	}

	public void setDocument(Document document) {
		_document = document;
	}

	public void setSource(String source) {
		_source = source;
	}

	public void setVersion(long version) {
		_version = version;
	}

	private Document _document;
	private final boolean _exists;
	private String _source;
	private long _version;

}