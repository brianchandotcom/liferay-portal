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

package com.liferay.portal.search.elasticsearch7.internal.information;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConnectionConfiguration;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.configuration.OperationModeResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnection;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.engine.ConnectionInformation;
import com.liferay.portal.search.engine.ConnectionInformationBuilder;
import com.liferay.portal.search.engine.ConnectionInformationBuilderFactory;
import com.liferay.portal.search.engine.NodeInformation;
import com.liferay.portal.search.engine.NodeInformationBuilder;
import com.liferay.portal.search.engine.NodeInformationBuilderFactory;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterResponse;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.util.EntityUtils;

import org.elasticsearch.Version;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true, service = SearchEngineInformation.class)
public class ElasticsearchSearchEngineInformation
	implements SearchEngineInformation {

	@Override
	public String getClientVersionString() {
		return Version.CURRENT.toString();
	}

	@Override
	public List<ConnectionInformation> getConnectionInformationList() {
		List<ConnectionInformation> connectionInformationList =
			new LinkedList<>();

		ElasticsearchConnection elasticsearchConnection =
			elasticsearchConnectionManager.getElasticsearchConnection();

		_addMainConnection(elasticsearchConnection, connectionInformationList);

		String filterString = String.format(
			"(&(service.factoryPid=%s)(active=%s)",
			ElasticsearchConnectionConfiguration.class.getName(), true);

		if (operationModeResolver.isProductionModeEnabled() &&
			!Validator.isBlank(
				elasticsearchConfigurationWrapper.
					remoteClusterConnectionId())) {

			filterString = filterString.concat(
				String.format(
					"(!(connectionId=%s))",
					elasticsearchConfigurationWrapper.
						remoteClusterConnectionId()));
		}

		ElasticsearchConnection localClusterElasticsearchConnection =
			elasticsearchConnectionManager.getElasticsearchConnection(true);

		if (operationModeResolver.isProductionModeEnabled() &&
			elasticsearchConnectionManager.isCrossClusterReplicationEnabled() &&
			!elasticsearchConnection.equals(
				localClusterElasticsearchConnection)) {

			_addCCRConnection(
				localClusterElasticsearchConnection, connectionInformationList);

			filterString = filterString.concat(
				String.format(
					"(!(connectionId=%s))",
					localClusterElasticsearchConnection.getConnectionId()));
		}

		filterString = filterString.concat(")");

		try {
			_addActiveConnections(filterString, connectionInformationList);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get active connections", exception);
			}
		}

		return connectionInformationList;
	}

	@Override
	public String getNodesString() {
		try {
			String clusterNodesString = _getClusterNodesString(
				elasticsearchConnectionManager.getRestHighLevelClient());

			if (operationModeResolver.isProductionModeEnabled() &&
				elasticsearchConnectionManager.
					isCrossClusterReplicationEnabled()) {

				String localClusterNodesString = _getClusterNodesString(
					elasticsearchConnectionManager.getRestHighLevelClient(
						null, true));

				if (!Validator.isBlank(localClusterNodesString)) {
					clusterNodesString = StringBundler.concat(
						"Remote Cluster = ", clusterNodesString,
						", Local Cluster = ", localClusterNodesString);
				}
			}

			return clusterNodesString;
		}
		catch (Exception exception) {
			return exception.toString();
		}
	}

	@Override
	public String getVendorString() {
		String vendor = "Elasticsearch";

		if (operationModeResolver.isDevelopmentModeEnabled()) {
			return vendor + " (Sidecar)";
		}

		return vendor;
	}

	@Reference
	protected ConfigurationAdmin configurationAdmin;

	@Reference
	protected ConnectionInformationBuilderFactory
		connectionInformationBuilderFactory;

	@Reference
	protected volatile ElasticsearchConfigurationWrapper
		elasticsearchConfigurationWrapper;

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

	@Reference
	protected NodeInformationBuilderFactory nodeInformationBuilderFactory;

	@Reference
	protected OperationModeResolver operationModeResolver;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	private void _addActiveConnections(
			String filterString,
			List<ConnectionInformation> connectionInformationList)
		throws Exception {

		Configuration[] configurations = configurationAdmin.listConfigurations(
			filterString);

		if (ArrayUtil.isEmpty(configurations)) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			String connectionId = (String)properties.get("connectionId");

			_addConnectionInformation(
				elasticsearchConnectionManager.getElasticsearchConnection(
					connectionId),
				connectionInformationList, null);
		}
	}

	private void _addCCRConnection(
		ElasticsearchConnection elasticsearchConnection,
		List<ConnectionInformation> connectionInformationList) {

		_addConnectionInformation(
			elasticsearchConnection, connectionInformationList, "read");
	}

	private void _addConnectionInformation(
		ElasticsearchConnection elasticsearchConnection,
		List<ConnectionInformation> connectionInformationList,
		String... labels) {

		if (elasticsearchConnection == null) {
			return;
		}

		ConnectionInformationBuilder connectionInformationBuilder =
			connectionInformationBuilderFactory.
				getConnectionInformationBuilder();

		try {
			_setClusterAndNodeInformation(
				connectionInformationBuilder,
				elasticsearchConnection.getRestHighLevelClient());
		}
		catch (Exception exception) {
			connectionInformationBuilder.error(exception.toString());

			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get node information", exception);
			}
		}

		connectionInformationBuilder.connectionId(
			elasticsearchConnection.getConnectionId());

		try {
			_setHealthInformation(
				connectionInformationBuilder,
				elasticsearchConnection.getConnectionId());
		}
		catch (RuntimeException runtimeException) {
			connectionInformationBuilder.health("unknown");

			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get health information", runtimeException);
			}
		}

		if (ArrayUtil.isNotEmpty(labels)) {
			connectionInformationBuilder.labels(SetUtil.fromArray(labels));
		}

		connectionInformationList.add(connectionInformationBuilder.build());
	}

	private void _addMainConnection(
		ElasticsearchConnection elasticsearchConnection,
		List<ConnectionInformation> connectionInformationList) {

		String[] labels = {"read", "write"};

		if (operationModeResolver.isProductionModeEnabled() &&
			elasticsearchConnectionManager.isCrossClusterReplicationEnabled() &&
			!elasticsearchConnection.equals(
				elasticsearchConnectionManager.getElasticsearchConnection(
					true))) {

			labels = new String[] {"write"};
		}

		_addConnectionInformation(
			elasticsearchConnection, connectionInformationList, labels);
	}

	private String _getClusterNodesString(
		RestHighLevelClient restHighLevelClient) {

		try {
			if (restHighLevelClient == null) {
				return StringPool.BLANK;
			}

			ConnectionInformationBuilder connectionInformationBuilder =
				connectionInformationBuilderFactory.
					getConnectionInformationBuilder();

			_setClusterAndNodeInformation(
				connectionInformationBuilder, restHighLevelClient);

			ConnectionInformation connectionInformation =
				connectionInformationBuilder.build();

			String clusterName = connectionInformation.getClusterName();

			List<NodeInformation> nodeInformations =
				connectionInformation.getNodeInformationList();

			Stream<NodeInformation> stream = nodeInformations.stream();

			return StringBundler.concat(
				clusterName, StringPool.COLON, StringPool.SPACE,
				StringPool.OPEN_BRACKET,
				stream.map(
					nodeInfo -> StringBundler.concat(
						nodeInfo.getName(), StringPool.SPACE,
						StringPool.OPEN_PARENTHESIS, nodeInfo.getVersion(),
						StringPool.CLOSE_PARENTHESIS)
				).collect(
					Collectors.joining(StringPool.COMMA_AND_SPACE)
				),
				StringPool.CLOSE_BRACKET);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get node information", exception);
			}

			return StringBundler.concat("(Error: ", exception.toString(), ")");
		}
	}

	private void _setClusterAndNodeInformation(
			ConnectionInformationBuilder connectionInformationBuilder,
			RestHighLevelClient restHighLevelClient)
		throws Exception {

		RestClient restClient = restHighLevelClient.getLowLevelClient();

		String endpoint = "/_nodes";

		Request request = new Request("GET", endpoint);

		request.addParameter("timeout", "10000ms");

		Response response = restClient.performRequest(request);

		String responseBody = EntityUtils.toString(response.getEntity());

		JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(
			responseBody);

		String clusterName = GetterUtil.getString(
			responseJSONObject.get("cluster_name"));

		connectionInformationBuilder.clusterName(clusterName);

		JSONObject nodesJSONObject = responseJSONObject.getJSONObject("nodes");

		Set<String> nodes = nodesJSONObject.keySet();

		List<NodeInformation> nodeInformationList = new ArrayList<>();

		for (String node : nodes) {
			JSONObject nodeJSONObject = nodesJSONObject.getJSONObject(node);

			NodeInformationBuilder nodeInformationBuilder =
				nodeInformationBuilderFactory.getNodeInformationBuilder();

			nodeInformationBuilder.name(
				GetterUtil.getString(nodeJSONObject.get("name")));
			nodeInformationBuilder.version(
				GetterUtil.getString(nodeJSONObject.get("version")));

			nodeInformationList.add(nodeInformationBuilder.build());
		}

		connectionInformationBuilder.nodeInformationList(nodeInformationList);
	}

	private void _setHealthInformation(
		ConnectionInformationBuilder connectionInformationBuilder,
		String connectionId) {

		HealthClusterRequest healthClusterRequest = new HealthClusterRequest();

		healthClusterRequest.setConnectionId(connectionId);
		healthClusterRequest.setTimeout(1000);

		HealthClusterResponse healthClusterResponse =
			searchEngineAdapter.execute(healthClusterRequest);

		connectionInformationBuilder.health(
			String.valueOf(healthClusterResponse.getClusterHealthStatus()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchSearchEngineInformation.class);

}