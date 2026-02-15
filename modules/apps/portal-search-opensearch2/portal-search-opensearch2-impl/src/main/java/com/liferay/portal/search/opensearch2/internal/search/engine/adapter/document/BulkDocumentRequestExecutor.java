/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentItemResponse;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;
import com.liferay.portal.search.opensearch2.internal.util.SetterUtil;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.ErrorCause;
import org.opensearch.client.opensearch._types.Refresh;
import org.opensearch.client.opensearch.core.BulkRequest;
import org.opensearch.client.opensearch.core.BulkResponse;
import org.opensearch.client.opensearch.core.bulk.BulkOperation;
import org.opensearch.client.opensearch.core.bulk.BulkResponseItem;
import org.opensearch.client.opensearch.core.bulk.DeleteOperation;
import org.opensearch.client.opensearch.core.bulk.IndexOperation;
import org.opensearch.client.opensearch.core.bulk.UpdateOperation;

/**
 * @author Michael C. Han
 */
public class BulkDocumentRequestExecutor {

	public BulkDocumentRequestExecutor(
		int numberOfTries,
		OpenSearchConnectionManager openSearchConnectionManager,
		int waitInSeconds) {

		_numberOfTries = numberOfTries;
		_openSearchConnectionManager = openSearchConnectionManager;
		_waitInSeconds = waitInSeconds;
	}

	public BulkDocumentResponse execute(
		BulkDocumentRequest bulkDocumentRequest) {

		BulkResponse bulkResponse = _getBulkResponse(
			bulkDocumentRequest, createBulkRequest(bulkDocumentRequest));

		JsonpUtil.logBulkResponse(bulkResponse, _log);

		BulkDocumentResponse bulkDocumentResponse = new BulkDocumentResponse(
			bulkResponse.took());

		for (BulkResponseItem bulkResponseItem : bulkResponse.items()) {
			BulkDocumentItemResponse bulkDocumentItemResponse =
				new BulkDocumentItemResponse();

			bulkDocumentItemResponse.setId(bulkResponseItem.id());
			bulkDocumentItemResponse.setIndex(bulkResponseItem.index());
			bulkDocumentItemResponse.setStatus(bulkResponseItem.status());

			SetterUtil.setNotNullLong(
				bulkDocumentItemResponse::setVersion,
				bulkResponseItem.version());

			ErrorCause errorCause = bulkResponseItem.error();

			if (errorCause != null) {
				if (errorCause.causedBy() != null) {
					ErrorCause causedByErrorCause = errorCause.causedBy();

					bulkDocumentItemResponse.setFailureMessage(
						causedByErrorCause.reason());
					bulkDocumentItemResponse.setCause(
						new Exception(JsonpUtil.toString(causedByErrorCause)));
				}
				else {
					bulkDocumentItemResponse.setFailureMessage(
						errorCause.reason());
					bulkDocumentItemResponse.setCause(
						new Exception(JsonpUtil.toString(errorCause)));
				}

				bulkDocumentResponse.setErrors(true);
			}

			bulkDocumentResponse.addBulkDocumentItemResponse(
				bulkDocumentItemResponse);
		}

		return bulkDocumentResponse;
	}

	protected BulkRequest createBulkRequest(
		BulkDocumentRequest bulkDocumentRequest) {

		BulkRequest.Builder builder = new BulkRequest.Builder();

		if (bulkDocumentRequest.isRefresh()) {
			builder.refresh(Refresh.True);
		}

		for (BulkableDocumentRequest<?> bulkableDocumentRequest :
				bulkDocumentRequest.getBulkableDocumentRequests()) {

			if (bulkableDocumentRequest instanceof DeleteDocumentRequest) {
				DeleteOperation deleteOperation =
					OpenSearchBulkableDocumentRequestTranslatorUtil.translate(
						(DeleteDocumentRequest)bulkableDocumentRequest);

				builder.operations(new BulkOperation(deleteOperation));
			}
			else if (bulkableDocumentRequest instanceof IndexDocumentRequest) {
				IndexOperation<JsonData> indexOperation =
					OpenSearchBulkableDocumentRequestTranslatorUtil.translate(
						(IndexDocumentRequest)bulkableDocumentRequest);

				builder.operations(new BulkOperation(indexOperation));
			}
			else if (bulkableDocumentRequest instanceof UpdateDocumentRequest) {
				UpdateOperation<JsonData> updateOperation =
					OpenSearchBulkableDocumentRequestTranslatorUtil.translate(
						(UpdateDocumentRequest)bulkableDocumentRequest);

				builder.operations(new BulkOperation(updateOperation));
			}
		}

		return builder.build();
	}

	private BulkResponse _getBulkResponse(
		BulkDocumentRequest bulkDocumentRequest, BulkRequest bulkRequest) {

		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient(
				bulkDocumentRequest.getConnectionId(),
				bulkDocumentRequest.isPreferLocalCluster());

		for (int i = 0;;) {
			try {
				return openSearchClient.bulk(bulkRequest);
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

	private final int _numberOfTries;
	private final OpenSearchConnectionManager _openSearchConnectionManager;
	private final int _waitInSeconds;

}