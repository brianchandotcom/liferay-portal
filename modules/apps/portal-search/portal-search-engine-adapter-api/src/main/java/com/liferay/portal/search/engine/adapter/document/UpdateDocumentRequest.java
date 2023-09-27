/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.document;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;
import com.liferay.portal.search.script.Script;

import java.util.function.Consumer;

/**
 * @author Michael C. Han
 */
public class UpdateDocumentRequest
	extends CrossClusterRequest
	implements BulkableDocumentRequest<UpdateDocumentRequest>,
			   DocumentRequest<UpdateDocumentResponse> {

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by
	 *             UpdateDocumentRequest.UpdateDocumentRequest(String, String,
	 *             Document)
	 */
	@Deprecated
	public UpdateDocumentRequest(
		String indexName, String uid,
		com.liferay.portal.kernel.search.Document legacyDocument) {

		_indexName = indexName;
		_uid = uid;
		_legacyDocument = legacyDocument;

		_document = null;
	}

	public UpdateDocumentRequest(
		String indexName, String uid, Document document) {

		_indexName = indexName;
		_uid = uid;
		_document = document;

		_legacyDocument = null;
	}

	public UpdateDocumentRequest(String indexName, String uid, Script script) {
		_indexName = indexName;
		_uid = uid;
		_script = script;

		_document = null;
		_legacyDocument = null;
	}

	@Override
	public void accept(Consumer<UpdateDocumentRequest> consumer) {
		consumer.accept(this);
	}

	@Override
	public UpdateDocumentResponse accept(
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

	public Script getScript() {
		return _script;
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

	public boolean isScriptedUpsert() {
		return _scriptedUpsert;
	}

	public boolean isUpsert() {
		return _upsert;
	}

	public void setRefresh(boolean refresh) {
		_refresh = refresh;
	}

	public void setScript(Script script) {
		_script = script;
	}

	public void setScriptedUpsert(boolean scriptedUpsert) {
		_scriptedUpsert = scriptedUpsert;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setUpsert(boolean upsert) {
		_upsert = upsert;
	}

	private final Document _document;
	private final String _indexName;
	private final com.liferay.portal.kernel.search.Document _legacyDocument;
	private boolean _refresh;
	private Script _script;
	private boolean _scriptedUpsert;
	private String _type;
	private final String _uid;
	private boolean _upsert;

}