/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.helper.SearchLogHelperUtil;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document.configuration.BulkDocumentRequestRetryConfiguration;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentItemResponse;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

import java.util.Map;

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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document.configuration.BulkDocumentRequestRetryConfiguration",
	service = BulkDocumentRequestExecutor.class
)
public class BulkDocumentRequestExecutorImpl
	implements BulkDocumentRequestExecutor {

	@Override
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

		BulkRequest bulkRequest = new BulkRequest();

		if (bulkDocumentRequest.isRefresh()) {
			bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
		}

		for (BulkableDocumentRequest<?> bulkableDocumentRequest :
				bulkDocumentRequest.getBulkableDocumentRequests()) {

			bulkableDocumentRequest.accept(
				request -> {
					if (request instanceof DeleteDocumentRequest) {
						DeleteRequest deleteRequest =
							_elasticsearchBulkableDocumentRequestTranslator.
								translate((DeleteDocumentRequest)request);

						bulkRequest.add(deleteRequest);
					}
					else if (request instanceof IndexDocumentRequest) {
						IndexRequest indexRequest =
							_elasticsearchBulkableDocumentRequestTranslator.
								translate((IndexDocumentRequest)request);

						bulkRequest.add(indexRequest);
					}
					else if (request instanceof UpdateDocumentRequest) {
						UpdateRequest updateRequest =
							_elasticsearchBulkableDocumentRequestTranslator.
								translate((UpdateDocumentRequest)request);

						bulkRequest.add(updateRequest);
					}
					else {
						throw new IllegalArgumentException(
							"No translator available for: " + request);
					}
				});
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
		BulkDocumentRequestExecutorImpl.class);

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	private ElasticsearchBulkableDocumentRequestTranslator
		_elasticsearchBulkableDocumentRequestTranslator;

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

	private volatile int _numberOfTries;
	private volatile int _waitInSeconds;

}