/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.helper.SearchLogHelperUtil;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentItemResponse;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.rest.RestStatus;

/**
 * @author Michael C. Han
 */
public class BulkDocumentRequestExecutor {

	public BulkDocumentRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver,
		int numberOfTries, int waitInSeconds) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
		_numberOfTries = numberOfTries;
		_waitInSeconds = waitInSeconds;
	}

	public BulkDocumentResponse execute(
		BulkDocumentRequest bulkDocumentRequest) {

		BulkRequest bulkRequest = createBulkRequest(bulkDocumentRequest);

		BulkResponse bulkResponse = _getBulkResponse(
			bulkRequest, bulkDocumentRequest);

		SearchLogHelperUtil.logActionResponse(_log, bulkResponse);

		TimeValue timeValue = bulkResponse.getTook();

		BulkDocumentResponse bulkDocumentResponse = new BulkDocumentResponse(
			timeValue.getMillis());

		for (BulkItemResponse bulkItemResponse : bulkResponse.getItems()) {
			BulkDocumentItemResponse bulkDocumentItemResponse =
				new BulkDocumentItemResponse();

			bulkDocumentResponse.addBulkDocumentItemResponse(
				bulkDocumentItemResponse);

			bulkDocumentItemResponse.setId(bulkItemResponse.getId());
			bulkDocumentItemResponse.setIndex(bulkItemResponse.getIndex());
			bulkDocumentItemResponse.setFailureMessage(
				bulkItemResponse.getFailureMessage());
			bulkDocumentItemResponse.setType(bulkItemResponse.getType());
			bulkDocumentItemResponse.setVersion(bulkItemResponse.getVersion());

			RestStatus restStatus = bulkItemResponse.status();

			if (bulkItemResponse.isFailed()) {
				bulkDocumentResponse.setErrors(true);

				BulkItemResponse.Failure bulkItemFailureResponse =
					bulkItemResponse.getFailure();

				bulkDocumentItemResponse.setAborted(
					bulkItemFailureResponse.isAborted());
				bulkDocumentItemResponse.setCause(
					bulkItemFailureResponse.getCause());
				restStatus = bulkItemFailureResponse.getStatus();
			}

			bulkDocumentItemResponse.setStatus(restStatus.getStatus());
		}

		return bulkDocumentResponse;
	}

	protected BulkRequest createBulkRequest(
		BulkDocumentRequest bulkDocumentRequest) {

		BulkRequest bulkRequest = new BulkRequest();

		if (bulkDocumentRequest.isRefresh()) {
			bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
		}

		for (BulkableDocumentRequest<?> bulkableDocumentRequest :
				bulkDocumentRequest.getBulkableDocumentRequests()) {

			if (bulkableDocumentRequest instanceof DeleteDocumentRequest) {
				DeleteDocumentRequest deleteDocumentRequest =
					(DeleteDocumentRequest)bulkableDocumentRequest;

				deleteDocumentRequest.setRefresh(false);

				DeleteRequest deleteRequest =
					ElasticsearchBulkableDocumentRequestTranslatorUtil.
						translate(deleteDocumentRequest);

				bulkRequest.add(deleteRequest);
			}
			else if (bulkableDocumentRequest instanceof IndexDocumentRequest) {
				IndexDocumentRequest indexDocumentRequest =
					(IndexDocumentRequest)bulkableDocumentRequest;

				indexDocumentRequest.setRefresh(false);

				IndexRequest indexRequest =
					ElasticsearchBulkableDocumentRequestTranslatorUtil.
						translate(indexDocumentRequest);

				bulkRequest.add(indexRequest);
			}
			else if (bulkableDocumentRequest instanceof UpdateDocumentRequest) {
				UpdateDocumentRequest updateDocumentRequest =
					(UpdateDocumentRequest)bulkableDocumentRequest;

				updateDocumentRequest.setRefresh(false);

				UpdateRequest updateRequest =
					ElasticsearchBulkableDocumentRequestTranslatorUtil.
						translate(updateDocumentRequest);

				bulkRequest.add(updateRequest);
			}
		}

		return bulkRequest;
	}

	private BulkResponse _getBulkResponse(
		BulkRequest bulkRequest, BulkDocumentRequest bulkDocumentRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				bulkDocumentRequest.getConnectionId(),
				bulkDocumentRequest.isPreferLocalCluster());

		for (int i = 0;;) {
			try {
				return restHighLevelClient.bulk(
					bulkRequest, RequestOptions.DEFAULT);
			}
			catch (Exception exception) {
				if (i++ >= _numberOfTries) {
					if (_numberOfTries == 1) {
						_log.error("The retry failed to get a bulk response");
					}
					else if (_numberOfTries == 2) {
						_log.error(
							"Both retries failed to get a bulk response");
					}
					else if (_numberOfTries > 2) {
						_log.error(
							"All " + _numberOfTries +
								" retries failed to get a bulk response");
					}

					throw new RuntimeException(exception);
				}

				_log.error(
					StringBundler.concat(
						"There was an exception while getting a response from ",
						"the search engine, will retry in ", _waitInSeconds,
						" seconds (", i, "/", _numberOfTries, "). ",
						exception));

				try {
					Thread.sleep(_waitInSeconds * Time.SECOND);
				}
				catch (InterruptedException interruptedException) {
					_log.error(interruptedException);

					throw new RuntimeException(exception);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BulkDocumentRequestExecutor.class);

	private final ElasticsearchClientResolver _elasticsearchClientResolver;
	private final int _numberOfTries;
	private final int _waitInSeconds;

}