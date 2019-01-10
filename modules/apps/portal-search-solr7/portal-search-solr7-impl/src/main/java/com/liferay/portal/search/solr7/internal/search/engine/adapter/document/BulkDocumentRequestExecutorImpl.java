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

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentItemResponse;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.solr7.configuration.SolrConfiguration;
import com.liferay.portal.search.solr7.internal.connection.SolrClientManager;
import com.liferay.portal.search.solr7.internal.document.SolrDocumentFactory;
import com.liferay.portal.search.solr7.internal.document.SolrInputDocumentAtomicUpdateTranslator;
import com.liferay.portal.search.solr7.internal.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.ModifiableSolrParams;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	configurationPid = "com.liferay.portal.search.solr7.configuration.SolrConfiguration",
	immediate = true, service = BulkDocumentRequestExecutor.class
)
public class BulkDocumentRequestExecutorImpl
	implements BulkDocumentRequestExecutor {

	@Override
	public BulkDocumentResponse execute(
		BulkDocumentRequest bulkDocumentRequest) {

		BulkDocumentRequestClassifier bulkDocumentRequestClassifier =
			new BulkDocumentRequestClassifier(bulkDocumentRequest);

		List<BulkDocumentResponse> bulkDocumentResponses = new ArrayList<>();

		executeDeleteDocumentRequests(
			bulkDocumentRequest, bulkDocumentRequestClassifier,
			bulkDocumentResponses);

		executeIndexDocumentRequests(
			bulkDocumentRequest, bulkDocumentRequestClassifier,
			bulkDocumentResponses);

		executeUpdateDocumentRequests(
			bulkDocumentRequest, bulkDocumentRequestClassifier,
			bulkDocumentResponses);

		executeGetDocumentRequests(
			bulkDocumentRequestClassifier, bulkDocumentResponses);

		List<BulkDocumentItemResponse> bulkDocumentItemResponses =
			new ArrayList<>();

		boolean errors = false;
		long took = 0;

		for (BulkDocumentResponse bulkDocumentResponse :
				bulkDocumentResponses) {

			if (bulkDocumentResponse.hasErrors()) {
				errors = true;
			}

			bulkDocumentItemResponses.addAll(
				bulkDocumentResponse.getBulkDocumentItemResponses());

			took += bulkDocumentResponse.getTook();
		}

		BulkDocumentResponse bulkDocumentResponse = new BulkDocumentResponse(
			took);

		bulkDocumentResponse.setErrors(errors);

		bulkDocumentItemResponses.forEach(
			bulkDocumentItemResponse ->
				bulkDocumentResponse.addBulkDocumentItemResponse(
					bulkDocumentItemResponse));

		return bulkDocumentResponse;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_solrConfiguration = ConfigurableUtil.createConfigurable(
			SolrConfiguration.class, properties);

		_defaultCollection = _solrConfiguration.defaultCollection();
	}

	protected SolrRequest buildDeleteSolrRequest(
		List<DeleteDocumentRequest> deleteDocumentRequests, boolean refresh) {

		UpdateRequest updateRequest = new UpdateRequest();

		Stream<DeleteDocumentRequest> stream = deleteDocumentRequests.stream();

		List<String> uids = stream.map(
			DeleteDocumentRequest::getUid
		).collect(
			Collectors.toList()
		);

		updateRequest.deleteById(uids);

		if (refresh) {
			updateRequest.setAction(UpdateRequest.ACTION.COMMIT, true, true);
		}

		return updateRequest;
	}

	protected SolrRequest buildGetSolrRequest(
		List<GetDocumentRequest> getDocumentRequests) {

		ModifiableSolrParams modifiableSolrParams = new ModifiableSolrParams();

		modifiableSolrParams.set(CommonParams.QT, "/get");

		Stream<GetDocumentRequest> stream = getDocumentRequests.stream();

		String[] ids = stream.map(
			GetDocumentRequest::getId
		).toArray(
			String[]::new
		);

		modifiableSolrParams.set("ids", ids);

		return new QueryRequest(modifiableSolrParams);
	}

	protected SolrRequest buildIndexSolrRequest(
		List<IndexDocumentRequest> indexDocumentRequests, boolean refresh) {

		UpdateRequest updateRequest = new UpdateRequest();

		for (IndexDocumentRequest indexDocumentRequest :
				indexDocumentRequests) {

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
		}

		if (refresh) {
			updateRequest.setAction(UpdateRequest.ACTION.COMMIT, true, true);
		}

		return updateRequest;
	}

	protected SolrRequest buildUpdateSolrRequest(
		List<UpdateDocumentRequest> updateDocumentRequests, boolean refresh) {

		UpdateRequest updateRequest = new UpdateRequest();

		for (UpdateDocumentRequest updateDocumentRequest :
				updateDocumentRequests) {

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
		}

		if (refresh) {
			updateRequest.setAction(UpdateRequest.ACTION.COMMIT, true, true);
		}

		return updateRequest;
	}

	protected BulkDocumentResponse execute(SolrRequest solrRequest) {
		try {
			SolrResponse solrResponse = solrRequest.process(
				_solrClientManager.getSolrClient(), _defaultCollection);

			LogUtil.logSolrResponse(_log, solrResponse);

			BulkDocumentResponse bulkDocumentResponse =
				new BulkDocumentResponse(solrResponse.getElapsedTime());

			BulkDocumentItemResponse bulkDocumentItemResponse =
				new BulkDocumentItemResponse();

			if (solrResponse instanceof UpdateResponse) {
				UpdateResponse updateResponse = (UpdateResponse)solrResponse;

				bulkDocumentItemResponse.setStatus(updateResponse.getStatus());
			}

			if (solrResponse instanceof QueryResponse) {
				QueryResponse queryResponse = (QueryResponse)solrResponse;

				SolrDocumentList solrDocumentList = queryResponse.getResults();

				bulkDocumentItemResponse.setResult(solrDocumentList.toString());

				bulkDocumentItemResponse.setStatus(queryResponse.getStatus());
			}

			bulkDocumentResponse.addBulkDocumentItemResponse(
				bulkDocumentItemResponse);

			return bulkDocumentResponse;
		}
		catch (Exception e) {
			if (e instanceof SolrException) {
				SolrException se = (SolrException)e;

				LogUtil.logSolrException(_log, se);

				BulkDocumentResponse bulkDocumentResponse =
					new BulkDocumentResponse(-1);

				BulkDocumentItemResponse bulkDocumentItemResponse =
					new BulkDocumentItemResponse();

				bulkDocumentItemResponse.setCause(se);
				bulkDocumentItemResponse.setFailureMessage(se.getMessage());
				bulkDocumentItemResponse.setStatus(se.code());

				bulkDocumentResponse.addBulkDocumentItemResponse(
					bulkDocumentItemResponse);

				bulkDocumentResponse.setErrors(true);

				return bulkDocumentResponse;
			}

			throw new RuntimeException(e);
		}
	}

	protected void executeDeleteDocumentRequests(
		BulkDocumentRequest bulkDocumentRequest,
		BulkDocumentRequestClassifier bulkDocumentRequestClassifier,
		List<BulkDocumentResponse> bulkDocumentResponses) {

		if (bulkDocumentRequestClassifier.hasDeleteDocumentRequests()) {
			BulkDocumentResponse bulkDocumentResponse = execute(
				buildDeleteSolrRequest(
					bulkDocumentRequestClassifier.getDeleteDocumentRequests(),
					bulkDocumentRequest.isRefresh()));

			bulkDocumentResponses.add(bulkDocumentResponse);
		}
	}

	protected void executeGetDocumentRequests(
		BulkDocumentRequestClassifier bulkDocumentRequestClassifier,
		List<BulkDocumentResponse> bulkDocumentResponses) {

		if (bulkDocumentRequestClassifier.hasGetDocumentRequests()) {
			BulkDocumentResponse bulkDocumentResponse = execute(
				buildGetSolrRequest(
					bulkDocumentRequestClassifier.getGetDocumentRequests()));

			bulkDocumentResponses.add(bulkDocumentResponse);
		}
	}

	protected void executeIndexDocumentRequests(
		BulkDocumentRequest bulkDocumentRequest,
		BulkDocumentRequestClassifier bulkDocumentRequestClassifier,
		List<BulkDocumentResponse> bulkDocumentResponses) {

		if (bulkDocumentRequestClassifier.hasIndexDocumentRequests()) {
			BulkDocumentResponse bulkDocumentResponse = execute(
				buildIndexSolrRequest(
					bulkDocumentRequestClassifier.getIndexDocumentRequests(),
					bulkDocumentRequest.isRefresh()));

			bulkDocumentResponses.add(bulkDocumentResponse);
		}
	}

	protected void executeUpdateDocumentRequests(
		BulkDocumentRequest bulkDocumentRequest,
		BulkDocumentRequestClassifier bulkDocumentRequestClassifier,
		List<BulkDocumentResponse> bulkDocumentResponses) {

		if (bulkDocumentRequestClassifier.hasUpdateDocumentRequests()) {
			BulkDocumentResponse bulkDocumentResponse = execute(
				buildUpdateSolrRequest(
					bulkDocumentRequestClassifier.getUpdateDocumentRequests(),
					bulkDocumentRequest.isRefresh()));

			bulkDocumentResponses.add(bulkDocumentResponse);
		}
	}

	@Reference(unbind = "-")
	protected void setSolrClientManager(SolrClientManager solrClientManager) {
		_solrClientManager = solrClientManager;
	}

	@Reference(unbind = "-")
	protected void setSolrDocumentFactory(
		SolrDocumentFactory solrDocumentFactory) {

		_solrDocumentFactory = solrDocumentFactory;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BulkDocumentRequestExecutorImpl.class);

	private String _defaultCollection;
	private SolrClientManager _solrClientManager;
	private volatile SolrConfiguration _solrConfiguration;
	private SolrDocumentFactory _solrDocumentFactory;

	private class BulkDocumentRequestClassifier {

		public BulkDocumentRequestClassifier(
			BulkDocumentRequest bulkDocumentRequest) {

			_classify(bulkDocumentRequest);
		}

		public List<DeleteDocumentRequest> getDeleteDocumentRequests() {
			return _deleteDocumentRequests;
		}

		public List<GetDocumentRequest> getGetDocumentRequests() {
			return _getDocumentRequests;
		}

		public List<IndexDocumentRequest> getIndexDocumentRequests() {
			return _indexDocumentRequests;
		}

		public List<UpdateDocumentRequest> getUpdateDocumentRequests() {
			return _updateDocumentRequests;
		}

		public boolean hasDeleteDocumentRequests() {
			return !_deleteDocumentRequests.isEmpty();
		}

		public boolean hasGetDocumentRequests() {
			return !_getDocumentRequests.isEmpty();
		}

		public boolean hasIndexDocumentRequests() {
			return !_indexDocumentRequests.isEmpty();
		}

		public boolean hasUpdateDocumentRequests() {
			return !_updateDocumentRequests.isEmpty();
		}

		private void _classify(BulkDocumentRequest bulkDocumentRequest) {
			for (BulkableDocumentRequest<?> bulkableDocumentRequest :
					bulkDocumentRequest.getBulkableDocumentRequests()) {

				bulkableDocumentRequest.accept(
					request -> {
						if (request instanceof DeleteDocumentRequest) {
							_deleteDocumentRequests.add(
								(DeleteDocumentRequest)request);
						}
						else if (request instanceof GetDocumentRequest) {
							_getDocumentRequests.add(
								(GetDocumentRequest)request);
						}
						else if (request instanceof IndexDocumentRequest) {
							_indexDocumentRequests.add(
								(IndexDocumentRequest)request);
						}
						else if (request instanceof UpdateDocumentRequest) {
							_updateDocumentRequests.add(
								(UpdateDocumentRequest)request);
						}
					});
			}
		}

		private List<DeleteDocumentRequest> _deleteDocumentRequests =
			new ArrayList<>();
		private List<GetDocumentRequest> _getDocumentRequests =
			new ArrayList<>();
		private List<IndexDocumentRequest> _indexDocumentRequests =
			new ArrayList<>();
		private List<UpdateDocumentRequest> _updateDocumentRequests =
			new ArrayList<>();

	}

}