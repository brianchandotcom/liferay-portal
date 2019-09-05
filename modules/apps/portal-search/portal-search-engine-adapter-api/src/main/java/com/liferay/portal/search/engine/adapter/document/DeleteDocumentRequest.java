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
 * @author Michael C. Han
 */
public class DeleteDocumentRequest
	implements BulkableDocumentRequest<DeleteDocumentRequest>,
			   DocumentRequest<DeleteDocumentResponse> {

	public DeleteDocumentRequest(String indexName, String uid) {
		_indexName = indexName;
		_uid = uid;
	}

	/**
	 * @param      indexName
	 * @param      type
	 * @param      uid
	 * @deprecated As of Judson (7.1.x), since 7.1.x, replaced by {@link
	 *             DeleteDocumentRequest#DeleteDocumentRequest(String, String)}
	 */
	@Deprecated
	public DeleteDocumentRequest(String indexName, String type, String uid) {
		_indexName = indexName;
		_type = type;
		_uid = uid;
	}

	@Override
	public void accept(Consumer<DeleteDocumentRequest> consumer) {
		consumer.accept(this);
	}

	@Override
	public DeleteDocumentResponse accept(
		DocumentRequestExecutor documentRequestExecutor) {

		return documentRequestExecutor.executeDocumentRequest(this);
	}

	public String getIndexName() {
		return _indexName;
	}

	public String getType() {
		return _type;
	}

	public String getUid() {
		return _uid;
	}

	public boolean isRefresh() {
		return _refresh;
	}

	public void setRefresh(boolean refresh) {
		_refresh = refresh;
	}

	public void setType(String type) {
		_type = type;
	}

	private final String _indexName;
	private boolean _refresh;
	private String _type;
	private final String _uid;

}