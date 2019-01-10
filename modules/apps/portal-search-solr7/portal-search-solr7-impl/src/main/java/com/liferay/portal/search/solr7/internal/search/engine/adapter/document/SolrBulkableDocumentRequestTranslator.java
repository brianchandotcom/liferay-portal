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

package com.liferay.portal.search.solr7.internal.search.engine.adapter.document;

import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequestTranslator;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.solr7.internal.document.SolrDocumentFactory;
import com.liferay.portal.search.solr7.internal.document.SolrInputDocumentAtomicUpdateTranslator;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.ModifiableSolrParams;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	immediate = true, property = "search.engine.impl=Solr",
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
			SolrInputDocument solrInputDocument =
				_solrDocumentFactory.getSolrInputDocument(
					indexDocumentRequest.getDocument());

			updateRequest.add(solrInputDocument);
		}
		else {
			SolrInputDocument solrInputDocument =
				_solrDocumentFactory.getSolrInputDocument(
					indexDocumentRequest.getDocument71());

			updateRequest.add(solrInputDocument);
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
			SolrInputDocument solrInputDocument =
				_solrDocumentFactory.getSolrInputDocument(
					updateDocumentRequest.getDocument());

			updateRequest.add(
				SolrInputDocumentAtomicUpdateTranslator.translate(
					solrInputDocument));
		}
		else {
			SolrInputDocument solrInputDocument =
				_solrDocumentFactory.getSolrInputDocument(
					updateDocumentRequest.getDocument71());

			updateRequest.add(
				SolrInputDocumentAtomicUpdateTranslator.translate(
					solrInputDocument));
		}

		if (updateDocumentRequest.isRefresh()) {
			updateRequest.setAction(UpdateRequest.ACTION.COMMIT, true, true);
		}

		return updateRequest;
	}

	@Reference(unbind = "-")
	protected void setSolrDocumentFactory(
		SolrDocumentFactory solrDocumentFactory) {

		_solrDocumentFactory = solrDocumentFactory;
	}

	private SolrDocumentFactory _solrDocumentFactory;

}