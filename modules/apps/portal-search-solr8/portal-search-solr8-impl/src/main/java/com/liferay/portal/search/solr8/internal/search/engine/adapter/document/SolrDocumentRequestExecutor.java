/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.document;

import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentResponse;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;

/**
 * @author Bryan Engler
 */
public class SolrDocumentRequestExecutor implements DocumentRequestExecutor {

	public SolrDocumentRequestExecutor(
		String defaultCollection, SolrClientManager solrClientManager) {

		_bulkDocumentRequestExecutor = new BulkDocumentRequestExecutor(
			defaultCollection, solrClientManager);
		_deleteByQueryDocumentRequestExecutor =
			new DeleteByQueryDocumentRequestExecutor(
				defaultCollection, solrClientManager);
		_deleteDocumentRequestExecutor = new DeleteDocumentRequestExecutor(
			solrClientManager);
		_getDocumentRequestExecutor = new GetDocumentRequestExecutor(
			solrClientManager);
		_indexDocumentRequestExecutor = new IndexDocumentRequestExecutor(
			solrClientManager);
		_updateDocumentRequestExecutor = new UpdateDocumentRequestExecutor(
			solrClientManager);
	}

	@Override
	public BulkDocumentResponse executeBulkDocumentRequest(
		BulkDocumentRequest bulkDocumentRequest) {

		return _bulkDocumentRequestExecutor.execute(bulkDocumentRequest);
	}

	@Override
	public DeleteByQueryDocumentResponse executeDocumentRequest(
		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest) {

		return _deleteByQueryDocumentRequestExecutor.execute(
			deleteByQueryDocumentRequest);
	}

	@Override
	public DeleteDocumentResponse executeDocumentRequest(
		DeleteDocumentRequest deleteDocumentRequest) {

		return _deleteDocumentRequestExecutor.execute(deleteDocumentRequest);
	}

	@Override
	public GetDocumentResponse executeDocumentRequest(
		GetDocumentRequest getDocumentRequest) {

		return _getDocumentRequestExecutor.execute(getDocumentRequest);
	}

	@Override
	public IndexDocumentResponse executeDocumentRequest(
		IndexDocumentRequest indexDocumentRequest) {

		return _indexDocumentRequestExecutor.execute(indexDocumentRequest);
	}

	@Override
	public UpdateByQueryDocumentResponse executeDocumentRequest(
		UpdateByQueryDocumentRequest updateByQueryDocumentRequest) {

		return _updateByQueryDocumentRequestExecutor.execute(
			updateByQueryDocumentRequest);
	}

	@Override
	public UpdateDocumentResponse executeDocumentRequest(
		UpdateDocumentRequest updateDocumentRequest) {

		return _updateDocumentRequestExecutor.execute(updateDocumentRequest);
	}

	private final BulkDocumentRequestExecutor _bulkDocumentRequestExecutor;
	private final DeleteByQueryDocumentRequestExecutor
		_deleteByQueryDocumentRequestExecutor;
	private final DeleteDocumentRequestExecutor _deleteDocumentRequestExecutor;
	private final GetDocumentRequestExecutor _getDocumentRequestExecutor;
	private final IndexDocumentRequestExecutor _indexDocumentRequestExecutor;
	private final UpdateByQueryDocumentRequestExecutor
		_updateByQueryDocumentRequestExecutor =
			new UpdateByQueryDocumentRequestExecutor();
	private final UpdateDocumentRequestExecutor _updateDocumentRequestExecutor;

}