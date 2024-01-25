/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
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
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document.configuration.BulkDocumentRequestRetryConfiguration;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;
import com.liferay.portal.search.opensearch2.internal.util.SetterUtil;

import java.util.Map;

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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document.configuration.BulkDocumentRequestRetryConfiguration",
	service = BulkDocumentRequestExecutor.class
)
public class BulkDocumentRequestExecutorImpl
	implements BulkDocumentRequestExecutor {

	@Override
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

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		BulkDocumentRequestRetryConfiguration
			bulkDocumentRequestRetryConfiguration =
				ConfigurableUtil.createConfigurable(
					BulkDocumentRequestRetryConfiguration.class, properties);

		_numberOfTries = bulkDocumentRequestRetryConfiguration.numberOfTries();
		_waitInSeconds = bulkDocumentRequestRetryConfiguration.waitInSeconds();
	}

	protected BulkRequest createBulkRequest(
		BulkDocumentRequest bulkDocumentRequest) {

		BulkRequest.Builder builder = new BulkRequest.Builder();

		if (bulkDocumentRequest.isRefresh()) {
			builder.refresh(Refresh.True);
		}

		for (BulkableDocumentRequest<?> bulkableDocumentRequest :
				bulkDocumentRequest.getBulkableDocumentRequests()) {

			bulkableDocumentRequest.accept(
				request -> {
					if (request instanceof DeleteDocumentRequest) {
						DeleteOperation deleteOperation =
							_openSearchBulkableDocumentRequestTranslator.
								translate((DeleteDocumentRequest)request);

						builder.operations(new BulkOperation(deleteOperation));
					}
					else if (request instanceof IndexDocumentRequest) {
						IndexOperation<JsonData> indexOperation =
							_openSearchBulkableDocumentRequestTranslator.
								translate((IndexDocumentRequest)request);

						builder.operations(new BulkOperation(indexOperation));
					}
					else if (request instanceof UpdateDocumentRequest) {
						UpdateOperation<JsonData> updateOperation =
							_openSearchBulkableDocumentRequestTranslator.
								translate((UpdateDocumentRequest)request);

						builder.operations(new BulkOperation(updateOperation));
					}
					else {
						throw new IllegalArgumentException(
							"No translator available for " + request);
					}
				});
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
		BulkDocumentRequestExecutorImpl.class);

	private volatile int _numberOfTries;

	@Reference(target = "(search.engine.impl=OpenSearch)")
	private OpenSearchBulkableDocumentRequestTranslator
		_openSearchBulkableDocumentRequestTranslator;

	@Reference
	private OpenSearchConnectionManager _openSearchConnectionManager;

	private volatile int _waitInSeconds;

}