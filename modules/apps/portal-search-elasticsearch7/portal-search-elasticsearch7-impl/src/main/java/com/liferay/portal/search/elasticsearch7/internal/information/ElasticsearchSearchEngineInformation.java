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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.configuration.CrossClusterReplicationConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.configuration.OperationMode;
import com.liferay.portal.search.elasticsearch7.internal.ElasticsearchSearchEngine;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.engine.SearchEngineInformation;

import java.util.ArrayList;
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
	public String getNodesString() {
		String clusterNodesString = getClusterNodesString(
			elasticsearchConnectionManager.getRestHighLevelClient());

		if (isCrossClusterReplicationEnabled()) {
			String localClusterNodesString = getClusterNodesString(
				elasticsearchConnectionManager.getRestHighLevelClient(
					null, true));

			if (!Validator.isBlank(localClusterNodesString)) {
				StringBundler sb = new StringBundler(5);

				sb.append("Remote Cluster = ");
				sb.append(clusterNodesString);
				sb.append(StringPool.COMMA_AND_SPACE);
				sb.append("Local Cluster = ");
				sb.append(localClusterNodesString);

				clusterNodesString = sb.toString();
			}
		}

		return clusterNodesString;
	}

	@Override
	public String getVendorString() {
		OperationMode operationMode =
			elasticsearchConfiguration.operationMode();

		if (Objects.equals(operationMode, OperationMode.EMBEDDED)) {
			return elasticsearchSearchEngine.getVendor() + StringPool.SPACE +
				"(Embedded)";
		}

		return elasticsearchSearchEngine.getVendor();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);
	}

	protected String getClusterNodesString(
		RestHighLevelClient restHighLevelClient) {

		try {
			if (restHighLevelClient == null) {
				return StringPool.BLANK;
			}

			ClusterInfo clusterInfo = _getClusterInfo(restHighLevelClient);

			String clusterName = clusterInfo.getClusterName();

			List<NodeInfo> nodeInfos = clusterInfo.getNodeInfoList();

			Stream<NodeInfo> stream = nodeInfos.stream();

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

			StringBundler sb = new StringBundler(4);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append("Error: ");
			sb.append(exception.toString());
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}
	}

	protected boolean isCrossClusterReplicationEnabled() {
		if (crossClusterReplicationConfigurationWrapper == null) {
			return false;
		}

		return crossClusterReplicationConfigurationWrapper.isCCREnabled();
	}

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	protected volatile CrossClusterReplicationConfigurationWrapper
		crossClusterReplicationConfigurationWrapper;

	protected volatile ElasticsearchConfiguration elasticsearchConfiguration;

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

	@Reference
	protected ElasticsearchSearchEngine elasticsearchSearchEngine;

	private ClusterInfo _getClusterInfo(RestHighLevelClient restHighLevelClient)
		throws Exception {

		ClusterInfo clusterInfo = new ClusterInfo();

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

		clusterInfo.setClusterName(clusterName);

		JSONObject nodesJSONObject = responseJSONObject.getJSONObject("nodes");

		Set<String> nodes = nodesJSONObject.keySet();

		List<NodeInfo> nodeInfoList = new ArrayList<>();

		for (String node : nodes) {
			JSONObject nodeJSONObject = nodesJSONObject.getJSONObject(node);

			NodeInfo nodeInfo = new NodeInfo();

			nodeInfo.setName(GetterUtil.getString(nodeJSONObject.get("name")));
			nodeInfo.setVersion(
				GetterUtil.getString(nodeJSONObject.get("version")));

			nodeInfoList.add(nodeInfo);
		}

		clusterInfo.setNodeInfoList(nodeInfoList);

		return clusterInfo;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchSearchEngineInformation.class);

	private class ClusterInfo {

		public String getClusterName() {
			return _clusterName;
		}

		public List<NodeInfo> getNodeInfoList() {
			return _nodeInfoList;
		}

		public void setClusterName(String clusterName) {
			_clusterName = clusterName;
		}

		public void setNodeInfoList(List<NodeInfo> nodeInfoList) {
			_nodeInfoList = nodeInfoList;
		}

		private String _clusterName;
		private List<NodeInfo> _nodeInfoList;

	}

	private class NodeInfo {

		public String getName() {
			return _name;
		}

		public String getVersion() {
			return _version;
		}

		public void setName(String name) {
			_name = name;
		}

		public void setVersion(String version) {
			_version = version;
		}

		private String _name;
		private String _version;

	}

}