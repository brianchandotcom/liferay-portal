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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.elasticsearch7.internal.ElasticsearchSearchEngine;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnection;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch7.internal.connection.OperationMode;
import com.liferay.portal.search.engine.SearchEngineInformation;

import java.util.ArrayList;
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
	public String getNodesString() {
		try {
			RestHighLevelClient restHighLevelClient =
				elasticsearchConnectionManager.getRestHighLevelClient();

			if (restHighLevelClient == null) {
				return StringPool.BLANK;
			}

			List<NodeInfo> nodeInfos = _getClusterNodes(restHighLevelClient);

			Stream<NodeInfo> stream = nodeInfos.stream();

			return stream.map(
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
		}
		catch (Exception e) {
			_log.error("Unable to get node information", e);

			StringBundler sb = new StringBundler(4);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append("Error: ");
			sb.append(e.toString());
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	@Override
	public String getStatusString() {
		StringBundler sb = new StringBundler(8);

		sb.append("Vendor: ");
		sb.append(getVendorString());
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append("Client Version: ");
		sb.append(getClientVersionString());
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append("Nodes: ");
		sb.append(getNodesString());

		return sb.toString();
	}

	@Override
	public String getVendorString() {
		ElasticsearchConnection elasticsearchConnection =
			elasticsearchConnectionManager.getElasticsearchConnection();

		if (elasticsearchConnection.getOperationMode() ==
				OperationMode.EMBEDDED) {

			return elasticsearchSearchEngine.getVendor() + StringPool.SPACE +
				"(Embedded)";
		}

		return elasticsearchSearchEngine.getVendor();
	}

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

	@Reference
	protected ElasticsearchSearchEngine elasticsearchSearchEngine;

	private List<NodeInfo> _getClusterNodes(
		RestHighLevelClient restHighLevelClient) {

		List<NodeInfo> nodeInfoList = new ArrayList<>();

		RestClient restClient = restHighLevelClient.getLowLevelClient();

		String endpoint = "/_nodes";

		Request request = new Request("GET", endpoint);

		request.addParameter("timeout", "10000ms");

		try {
			Response response = restClient.performRequest(request);

			String responseBody = EntityUtils.toString(response.getEntity());

			JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(
				responseBody);

			JSONObject nodesJSONObject = responseJSONObject.getJSONObject(
				"nodes");

			Set<String> nodes = nodesJSONObject.keySet();

			for (String node : nodes) {
				JSONObject nodeJSONObject = nodesJSONObject.getJSONObject(node);

				NodeInfo nodeInfo = new NodeInfo();

				nodeInfo.setName(
					GetterUtil.getString(nodeJSONObject.get("name")));
				nodeInfo.setVersion(
					GetterUtil.getString(nodeJSONObject.get("version")));

				nodeInfoList.add(nodeInfo);
			}

			return nodeInfoList;
		}
		catch (Exception ioe) {
			throw new SystemException(ioe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchSearchEngineInformation.class);

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