/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.document;

import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentResponse;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;

import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrException;

/**
 * @author Bryan Engler
 */
public class UpdateDocumentRequestExecutor {

	public UpdateDocumentRequestExecutor(SolrClientManager solrClientManager) {
		_solrClientManager = solrClientManager;
	}

	public UpdateDocumentResponse execute(
		UpdateDocumentRequest updateDocumentRequest) {

		UpdateRequest request =
			SolrBulkableDocumentRequestTranslatorUtil.translate(
				updateDocumentRequest);

		try {
			UpdateResponse updateResponse = request.process(
				_solrClientManager.getSolrClient(),
				updateDocumentRequest.getIndexName());

			return new UpdateDocumentResponse(updateResponse.getStatus());
		}
		catch (Exception exception) {
			if (exception instanceof SolrException) {
				SolrException solrException = (SolrException)exception;

				throw solrException;
			}

			throw new RuntimeException(exception);
		}
	}

	private final SolrClientManager _solrClientManager;

}