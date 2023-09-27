/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.document;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

import java.util.function.Consumer;

/**
 * @author Michael C. Han
 */
public class IndexDocumentRequest
	extends CrossClusterRequest
	implements BulkableDocumentRequest<IndexDocumentRequest>,
			   DocumentRequest<IndexDocumentResponse> {

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by
	 *             IndexDocumentRequest.IndexDocumentRequest(String, Document)
	 */
	@Deprecated
	public IndexDocumentRequest(
		String indexName, com.liferay.portal.kernel.search.Document document) {

		this(indexName, null, document);
	}

	public IndexDocumentRequest(String indexName, Document document) {
		this(indexName, null, document);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by
	 *             IndexDocumentRequest.IndexDocumentRequest(String, String,
	 *             Document)
	 */
	@Deprecated
	public IndexDocumentRequest(
		String indexName, String uid,
		com.liferay.portal.kernel.search.Document legacyDocument) {

		_indexName = indexName;
		_uid = uid;
		_legacyDocument = legacyDocument;

		_document = null;
	}

	public IndexDocumentRequest(
		String indexName, String uid, Document document) {

		_indexName = indexName;
		_uid = uid;
		_document = document;

		_legacyDocument = null;
	}

	@Override
	public void accept(Consumer<IndexDocumentRequest> consumer) {
		consumer.accept(this);
	}

	@Override
	public IndexDocumentResponse accept(
		DocumentRequestExecutor documentRequestExecutor) {

		return documentRequestExecutor.executeDocumentRequest(this);
	}

	public Document getDocument() {
		return _document;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by getDocument
	 */
	@Deprecated
	public com.liferay.portal.kernel.search.Document getDocument71() {
		return _legacyDocument;
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

	private final Document _document;
	private final String _indexName;
	private final com.liferay.portal.kernel.search.Document _legacyDocument;
	private boolean _refresh;
	private String _type;
	private final String _uid;

}