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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.configuration.CrossClusterReplicationConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConnectionConfiguration;
import com.liferay.portal.search.elasticsearch7.configuration.OperationMode;
import com.liferay.portal.search.elasticsearch7.internal.ElasticsearchSearchEngine;
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
import com.liferay.portal.search.engine.adapter.cluster.ClusterHealthStatus;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterResponse;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Adam Brandizzi
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration",
	immediate = true, service = SearchEngineInformation.class
)
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

		addMainConnection(
			elasticsearchConnectionManager.getElasticsearchConnection(),
			connectionInformationList);

		String filterString = String.format(
			"(&(service.factoryPid=%s)(active=%s)",
			ElasticsearchConnectionConfiguration.class.getName(), true);

		if (!isOperationModeEmbedded()) {
			filterString = filterString.concat(
				String.format(
					"(!(connectionId=%s))",
					elasticsearchConfiguration.remoteClusterConnectionId()));
		}

		if (!isOperationModeEmbedded() &&
			elasticsearchConnectionManager.isCrossClusterReplicationEnabled()) {

			addCCRConnection(
				elasticsearchConnectionManager.getElasticsearchConnection(true),
				connectionInformationList);

			String connectionId =
				crossClusterReplicationConfigurationWrapper.
					getCCRLocalClusterConnectionId();

			if (!Validator.isBlank(connectionId)) {
				filterString = filterString.concat(
					String.format("(!(connectionId=%s))", connectionId));
			}
		}

		filterString = filterString.concat(")");

		try {
			addActiveConnections(filterString, connectionInformationList);
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
			String clusterNodesString = getClusterNodesString(
				elasticsearchConnectionManager.getRestHighLevelClient());

			if (!isOperationModeEmbedded() &&
				elasticsearchConnectionManager.
					isCrossClusterReplicationEnabled()) {

				String localClusterNodesString = getClusterNodesString(
					elasticsearchConnectionManager.getRestHighLevelClient(
						null, true));

				if (!Validator.isBlank(localClusterNodesString)) {
					StringBundler sb = new StringBundler(11);

					sb.append("Remote Cluster");
					sb.append(StringPool.SPACE);
					sb.append(StringPool.EQUAL);
					sb.append(StringPool.SPACE);
					sb.append(clusterNodesString);
					sb.append(StringPool.COMMA_AND_SPACE);
					sb.append("Local Cluster");
					sb.append(StringPool.SPACE);
					sb.append(StringPool.EQUAL);
					sb.append(StringPool.SPACE);
					sb.append(localClusterNodesString);

					clusterNodesString = sb.toString();
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
		String vendor = elasticsearchSearchEngine.getVendor();

		if (isOperationModeEmbedded()) {
			StringBundler sb = new StringBundler(5);

			sb.append(vendor);
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append("Embedded");
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		return vendor;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);
	}

	protected void addActiveConnections(
			String filterString,
			List<ConnectionInformation> connectionInformationList)
		throws Exception {

		Configuration[] configurations = configurationAdmin.listConfigurations(
			filterString);

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			String connectionId = (String)properties.get("connectionId");

			addConnectionInformation(
				elasticsearchConnectionManager.getElasticsearchConnection(
					connectionId),
				connectionInformationList, null);
		}
	}

	protected void addCCRConnection(
		ElasticsearchConnection elasticsearchConnection,
		List<ConnectionInformation> connectionInformationList) {

		addConnectionInformation(
			elasticsearchConnection, connectionInformationList, "read");
	}

	protected void addConnectionInformation(
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

	protected void addMainConnection(
		ElasticsearchConnection elasticsearchConnection,
		List<ConnectionInformation> connectionInformationList) {

		String[] labels = {"read", "write"};

		if (!isOperationModeEmbedded() &&
			elasticsearchConnectionManager.isCrossClusterReplicationEnabled()) {

			labels = new String[] {"write"};
		}

		addConnectionInformation(
			elasticsearchConnection, connectionInformationList, labels);
	}

	protected String getClusterNodesString(
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

			String nodesString = stream.map(
				nodeInfo -> {
					StringBundler sb = new StringBundler(5);

					sb.append(nodeInfo.getName());
					sb.append(StringPool.SPACE);
					sb.append(StringPool.OPEN_PARENTHESIS);
					sb.append(nodeInfo.getVersion());
					sb.append(StringPool.CLOSE_PARENTHESIS);

					return sb.toString();
				}
			).collect(
				Collectors.joining(StringPool.COMMA_AND_SPACE)
			);

			StringBundler sb = new StringBundler(6);

			sb.append(clusterName);
			sb.append(StringPool.COLON);
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_BRACKET);
			sb.append(nodesString);
			sb.append(StringPool.CLOSE_BRACKET);

			return sb.toString();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get node information", exception);
			}

			StringBundler sb = new StringBundler(6);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append("Error");
			sb.append(StringPool.COLON);
			sb.append(StringPool.SPACE);
			sb.append(exception.toString());
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}
	}

	protected boolean isOperationModeEmbedded() {
		OperationMode operationMode =
			elasticsearchConfiguration.operationMode();

		return Objects.equals(operationMode, OperationMode.EMBEDDED);
	}

	@Reference
	protected ConfigurationAdmin configurationAdmin;

	@Reference
	protected ConnectionInformationBuilderFactory
		connectionInformationBuilderFactory;

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	protected volatile CrossClusterReplicationConfigurationWrapper
		crossClusterReplicationConfigurationWrapper;

	protected volatile ElasticsearchConfiguration elasticsearchConfiguration;

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

	@Reference
	protected ElasticsearchSearchEngine elasticsearchSearchEngine;

	@Reference
	protected NodeInformationBuilderFactory nodeInformationBuilderFactory;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

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

		ClusterHealthStatus clusterHealthStatus =
			healthClusterResponse.getClusterHealthStatus();

		connectionInformationBuilder.health(clusterHealthStatus.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchSearchEngineInformation.class);

}