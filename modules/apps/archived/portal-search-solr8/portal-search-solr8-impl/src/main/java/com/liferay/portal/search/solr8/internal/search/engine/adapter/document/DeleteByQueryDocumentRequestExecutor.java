/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.document;

import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentResponse;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;
import com.liferay.portal.search.solr8.internal.query.SolrQueryVisitor;

import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrException;

/**
 * @author Bryan Engler
 */
public class DeleteByQueryDocumentRequestExecutor {

	public DeleteByQueryDocumentRequestExecutor(
		String defaultCollection, SolrClientManager solrClientManager) {

		_defaultCollection = defaultCollection;
		_solrClientManager = solrClientManager;
	}

	public DeleteByQueryDocumentResponse execute(
		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest) {

		UpdateRequest updateRequest = new UpdateRequest();

		String queryString = SolrQueryVisitor.INSTANCE.translate(
			deleteByQueryDocumentRequest.getQuery());

		updateRequest.deleteByQuery(queryString);

		if (deleteByQueryDocumentRequest.isRefresh()) {
			updateRequest.setAction(UpdateRequest.ACTION.COMMIT, true, true);
		}

		try {
			UpdateResponse updateResponse = updateRequest.process(
				_solrClientManager.getSolrClient(), _defaultCollection);

			return new DeleteByQueryDocumentResponse(
				updateResponse.getStatus(), updateResponse.getElapsedTime());
		}
		catch (Exception exception) {
			if (exception instanceof SolrException) {
				SolrException solrException = (SolrException)exception;

				throw solrException;
			}

			throw new RuntimeException(exception);
		}
	}

	private final String _defaultCollection;
	private final SolrClientManager _solrClientManager;

}