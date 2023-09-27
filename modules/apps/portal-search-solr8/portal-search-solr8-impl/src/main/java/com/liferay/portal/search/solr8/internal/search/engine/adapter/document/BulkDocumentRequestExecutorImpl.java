/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.document;

import com.liferay.petra.function.transform.TransformUtil;
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
import com.liferay.portal.search.solr8.configuration.SolrConfiguration;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;
import com.liferay.portal.search.solr8.internal.document.SolrDocumentFactory;
import com.liferay.portal.search.solr8.internal.document.SolrInputDocumentAtomicUpdateTranslator;
import com.liferay.portal.search.solr8.internal.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrException;
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
	configurationPid = "com.liferay.portal.search.solr8.configuration.SolrConfiguration",
	service = BulkDocumentRequestExecutor.class
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

		updateRequest.deleteById(
			TransformUtil.transform(
				deleteDocumentRequests, DeleteDocumentRequest::getUid));

		if (refresh) {
			updateRequest.setAction(UpdateRequest.ACTION.COMMIT, true, true);
		}

		return updateRequest;
	}

	protected SolrRequest buildGetSolrRequest(
		List<GetDocumentRequest> getDocumentRequests) {

		ModifiableSolrParams modifiableSolrParams = new ModifiableSolrParams();

		modifiableSolrParams.set(CommonParams.QT, "/get");

		modifiableSolrParams.set(
			"ids",
			TransformUtil.transformToArray(
				getDocumentRequests, GetDocumentRequest::getId, String.class));

		return new QueryRequest(modifiableSolrParams);
	}

	protected SolrRequest buildIndexSolrRequest(
		List<IndexDocumentRequest> indexDocumentRequests, boolean refresh) {

		UpdateRequest updateRequest = new UpdateRequest();

		for (IndexDocumentRequest indexDocumentRequest :
				indexDocumentRequests) {

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
		catch (Exception exception) {
			if (exception instanceof SolrException) {
				SolrException solrException = (SolrException)exception;

				LogUtil.logSolrException(_log, solrException);

				BulkDocumentResponse bulkDocumentResponse =
					new BulkDocumentResponse(-1);

				BulkDocumentItemResponse bulkDocumentItemResponse =
					new BulkDocumentItemResponse();

				bulkDocumentItemResponse.setCause(solrException);
				bulkDocumentItemResponse.setFailureMessage(
					solrException.getMessage());
				bulkDocumentItemResponse.setStatus(solrException.code());

				bulkDocumentResponse.addBulkDocumentItemResponse(
					bulkDocumentItemResponse);

				bulkDocumentResponse.setErrors(true);

				return bulkDocumentResponse;
			}

			throw new RuntimeException(exception);
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

	private static final Log _log = LogFactoryUtil.getLog(
		BulkDocumentRequestExecutorImpl.class);

	private volatile String _defaultCollection;

	@Reference
	private SolrClientManager _solrClientManager;

	private volatile SolrConfiguration _solrConfiguration;

	@Reference
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