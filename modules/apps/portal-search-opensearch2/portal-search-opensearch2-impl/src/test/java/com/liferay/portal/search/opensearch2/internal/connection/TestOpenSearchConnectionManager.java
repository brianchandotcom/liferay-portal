/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.connection;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.opensearch2.configuration.OpenSearchConfiguration;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationWrapper;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationWrapperImpl;

import java.io.IOException;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

import org.mockito.Mockito;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.json.JsonpMapper;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.ingest.OpenSearchIngestClient;
import org.opensearch.client.opensearch.ingest.Processor;
import org.opensearch.client.opensearch.ingest.PutPipelineRequest;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class TestOpenSearchConnectionManager
	extends OpenSearchConnectionManagerImpl {

	public static final String REMOTE_TEST_CONNECTION = "__REMOTE__";

	public TestOpenSearchConnectionManager() {
		this(
			HashMapBuilder.<String, Object>put(
				"logExceptionsOnly", false).build());
	}

	public TestOpenSearchConnectionManager(
		Map<String, Object> configurationProperties) {

		_configurationProperties = configurationProperties;

		super.openSearchConfigurationWrapper =
			_createOpenSearchConfigurationWrapper();

		activate();

		OpenSearchConnectionsHolderImpl openSearchConnectionsHolderImpl =
			new OpenSearchConnectionsHolderImpl();

		openSearchConnectionsHolderImpl.http = Mockito.mock(Http.class);

		super.openSearchConnectionsHolder = openSearchConnectionsHolderImpl;

		ReflectionTestUtil.setFieldValue(
			this, "_clusterExecutor", Mockito.mock(ClusterExecutor.class));

		_addTestConnection();

		_putTimestampPipeline();
	}

	@Override
	public JsonpMapper getJsonpMapper(String connectionId) {
		return new JacksonJsonpMapper();
	}

	@Override
	public OpenSearchClient getOpenSearchClient(
		String connectionId, boolean preferLocalCluster) {

		if (Validator.isBlank(connectionId)) {
			return getOpenSearchClient(REMOTE_TEST_CONNECTION);
		}

		return super.getOpenSearchClient(connectionId, preferLocalCluster);
	}

	public Map<String, Object> getOpenSearchConfigurationProperties() {
		return _configurationProperties;
	}

	@Override
	protected OpenSearchConnection getOpenSearchConnection(
		String connectionId, boolean preferLocalCluster) {

		if (Validator.isBlank(connectionId)) {
			connectionId = REMOTE_TEST_CONNECTION;
		}

		return super.getOpenSearchConnection(connectionId, preferLocalCluster);
	}

	private void _addTestConnection() {
		OpenSearchConnection.Builder builder =
			new OpenSearchConnection.Builder();

		builder.active(true);
		builder.authenticationEnabled(false);
		builder.connectionId(REMOTE_TEST_CONNECTION);
		builder.httpSSLEnabled(false);
		builder.networkHostAddresses(new String[] {"http://localhost:9200"});
		builder.postCloseRunnable(Mockito.mock(Runnable.class));
		builder.preConnectOpenSearchConnectionConsumer(
			Mockito.mock(Consumer.class));

		addOpenSearchConnection(builder.build());
	}

	private OpenSearchConfigurationWrapper
		_createOpenSearchConfigurationWrapper() {

		return new OpenSearchConfigurationWrapperImpl() {
			{
				if (_configurationProperties == null) {
					setOpenSearchConfiguration(
						ConfigurableUtil.createConfigurable(
							OpenSearchConfiguration.class,
							Collections.emptyMap()));
				}
				else {
					setOpenSearchConfiguration(
						ConfigurableUtil.createConfigurable(
							OpenSearchConfiguration.class,
							_configurationProperties));
				}
			}
		};
	}

	private void _putTimestampPipeline() {
		PutPipelineRequest putPipelineRequest = new PutPipelineRequest.Builder(
		).id(
			"timestamp"
		).description(
			"Adds timestamp to documents"
		).processors(
			new Processor.Builder(
			).set(
				setProcessor -> setProcessor.field(
					"_source.timestamp"
				).value(
					JsonData.of("{{{_ingest.timestamp}}}")
				)
			).build()
		).build();

		OpenSearchClient openSearchClient = getOpenSearchClient();

		OpenSearchIngestClient openSearchIngestClient =
			openSearchClient.ingest();

		try {
			openSearchIngestClient.putPipeline(putPipelineRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private Map<String, Object> _configurationProperties;

}