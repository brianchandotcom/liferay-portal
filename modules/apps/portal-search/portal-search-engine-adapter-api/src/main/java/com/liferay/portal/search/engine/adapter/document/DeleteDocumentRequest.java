/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.document;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

import java.util.function.Consumer;

/**
 * @author Michael C. Han
 */
public class DeleteDocumentRequest
	extends CrossClusterRequest
	implements BulkableDocumentRequest<DeleteDocumentRequest>,
			   DocumentRequest<DeleteDocumentResponse> {

	public DeleteDocumentRequest(String indexName, String uid) {
		_indexName = indexName;
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