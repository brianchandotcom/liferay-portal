/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.document;

import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequestTranslator;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.solr8.internal.document.SolrDocumentFactory;
import com.liferay.portal.search.solr8.internal.document.SolrInputDocumentAtomicUpdateTranslator;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.ModifiableSolrParams;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	property = "search.engine.impl=Solr",
	service = BulkableDocumentRequestTranslator.class
)
public class SolrBulkableDocumentRequestTranslator
	implements BulkableDocumentRequestTranslator {

	@Override
	public SolrRequest translate(DeleteDocumentRequest deleteDocumentRequest) {
		String uid = deleteDocumentRequest.getUid();

		UpdateRequest updateRequest = new UpdateRequest();

		updateRequest.deleteById(uid);

		if (deleteDocumentRequest.isRefresh()) {
			updateRequest.setAction(UpdateRequest.ACTION.COMMIT, true, true);
		}

		return updateRequest;
	}

	@Override
	public QueryRequest translate(GetDocumentRequest getDocumentRequest) {
		ModifiableSolrParams modifiableSolrParams = new ModifiableSolrParams();

		modifiableSolrParams.set(CommonParams.QT, "/get");

		modifiableSolrParams.set("ids", getDocumentRequest.getId());

		return new QueryRequest(modifiableSolrParams);
	}

	@Override
	public UpdateRequest translate(IndexDocumentRequest indexDocumentRequest) {
		UpdateRequest updateRequest = new UpdateRequest();

		if (indexDocumentRequest.getDocument() != null) {
			updateRequest.add(
				_solrDocumentFactory.getSolrInputDocument(
					indexDocumentRequest.getDocument()));
		}
		else {
			updateRequest.add(
				_solrDocumentFactory.getSolrInputDocument(
					indexDocumentRequest.getDocument71()));
		}

		if (indexDocumentRequest.isRefresh()) {
			updateRequest.setAction(UpdateRequest.ACTION.COMMIT, true, true);
		}

		return updateRequest;
	}

	@Override
	public SolrRequest translate(UpdateDocumentRequest updateDocumentRequest) {
		UpdateRequest updateRequest = new UpdateRequest();

		if (updateDocumentRequest.getDocument() != null) {
			updateRequest.add(
				SolrInputDocumentAtomicUpdateTranslator.translate(
					_solrDocumentFactory.getSolrInputDocument(
						updateDocumentRequest.getDocument())));
		}
		else {
			updateRequest.add(
				SolrInputDocumentAtomicUpdateTranslator.translate(
					_solrDocumentFactory.getSolrInputDocument(
						updateDocumentRequest.getDocument71())));
		}

		if (updateDocumentRequest.isRefresh()) {
			updateRequest.setAction(UpdateRequest.ACTION.COMMIT, true, true);
		}

		return updateRequest;
	}

	@Reference
	private SolrDocumentFactory _solrDocumentFactory;

}