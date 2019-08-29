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

import java.util.function.Consumer;

/**
 * @author Bryan Engler
 */
public class GetDocumentRequest
	implements BulkableDocumentRequest<GetDocumentRequest>,
			   DocumentRequest<GetDocumentResponse> {

	public GetDocumentRequest(String indexName, String id) {
		_indexName = indexName;
		_id = id;
	}

	@Override
	public void accept(Consumer<GetDocumentRequest> consumer) {
		consumer.accept(this);
	}

	@Override
	public GetDocumentResponse accept(
		DocumentRequestExecutor documentRequestExecutor) {

		return documentRequestExecutor.executeDocumentRequest(this);
	}

	public String[] getFetchSourceExcludes() {
		return _fetchSourceExclude;
	}

	public String[] getFetchSourceIncludes() {
		return _fetchSourceInclude;
	}

	public String getId() {
		return _id;
	}

	public String getIndexName() {
		return _indexName;
	}

	public String[] getStoredFields() {
		return _storedFields;
	}

	public String getType() {
		return _type;
	}

	public boolean isFetchSource() {
		return _fetchSource;
	}

	public boolean isRefresh() {
		return _refresh;
	}

	public void setFetchSource(boolean fetchSource) {
		_fetchSource = fetchSource;
	}

	public void setFetchSourceExclude(String... fetchSourceExclude) {
		_fetchSourceExclude = fetchSourceExclude;
	}

	public void setFetchSourceInclude(String... fetchSourceInclude) {
		_fetchSourceInclude = fetchSourceInclude;
	}

	public void setRefresh(boolean refresh) {
		_refresh = refresh;
	}

	public void setStoredFields(String... storedFields) {
		_storedFields = storedFields;
	}

	public void setType(String type) {
		_type = type;
	}

	private boolean _fetchSource;
	private String[] _fetchSourceExclude;
	private String[] _fetchSourceInclude;
	private final String _id;
	private final String _indexName;
	private boolean _refresh;
	private String[] _storedFields;
	private String _type;

}