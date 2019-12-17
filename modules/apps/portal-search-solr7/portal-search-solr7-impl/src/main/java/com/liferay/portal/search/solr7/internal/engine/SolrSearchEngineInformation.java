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

package com.liferay.portal.search.solr7.internal.engine;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.engine.ConnectionInformation;
import com.liferay.portal.search.engine.ConnectionInformationBuilder;
import com.liferay.portal.search.engine.ConnectionInformationBuilderFactory;
import com.liferay.portal.search.engine.NodeInformation;
import com.liferay.portal.search.engine.NodeInformationBuilder;
import com.liferay.portal.search.engine.NodeInformationBuilderFactory;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.solr7.configuration.SolrConfiguration;
import com.liferay.portal.search.solr7.internal.SolrSearchEngine;
import com.liferay.portal.search.solr7.internal.connection.SolrClientManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.util.Version;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.request.GenericSolrRequest;
import org.apache.solr.client.solrj.response.SimpleSolrResponse;
import org.apache.solr.common.util.NamedList;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(
	configurationPid = "com.liferay.portal.search.solr7.configuration.SolrConfiguration",
	immediate = true, service = SearchEngineInformation.class
)
public class SolrSearchEngineInformation implements SearchEngineInformation {

	@Override
	public String getClientVersionString() {
		return Version.LATEST.toString();
	}

	@Override
	public List<ConnectionInformation> getConnectionInformationList() {
		List<ConnectionInformation> connectionInformationList =
			new ArrayList<>();

		ConnectionInformationBuilder connectionInformationBuilder =
			connectionInformationBuilderFactory.
				getConnectionInformationBuilder();

		connectionInformationBuilder.connectionId(_defaultCollection);

		try {
			List<NodeInformation> nodeInformationList = new ArrayList<>();

			NodeInformationBuilder nodeInformationBuilder =
				nodeInformationBuilderFactory.getNodeInformationBuilder();

			nodeInformationBuilder.name(_defaultCollection);
			nodeInformationBuilder.version(getVersion());

			nodeInformationList.add(nodeInformationBuilder.build());

			connectionInformationBuilder.nodeInformationList(
				nodeInformationList);
		}
		catch (Exception exception) {
			connectionInformationBuilder.error(exception.toString());

			_log.error("Could not retrieve node information", exception);
		}

		connectionInformationList.add(connectionInformationBuilder.build());

		return connectionInformationList;
	}

	@Override
	public String getNodesString() {
		try {
			StringBundler sb = new StringBundler(5);

			sb.append(_defaultCollection);
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(getVersion());
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}
		catch (Exception exception) {
			_log.error("Could not retrieve node information", exception);

			StringBundler sb = new StringBundler(4);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append("Error: ");
			sb.append(exception.toString());
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}
	}

	@Override
	public String getVendorString() {
		return solrSearchEngine.getVendor();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_solrConfiguration = ConfigurableUtil.createConfigurable(
			SolrConfiguration.class, properties);

		_defaultCollection = _solrConfiguration.defaultCollection();
	}

	protected String getVersion() throws Exception {
		SolrClient solrClient = solrClientManager.getSolrClient();

		GenericSolrRequest request = new GenericSolrRequest(
			SolrRequest.METHOD.POST, "/admin/info/system", null);

		SimpleSolrResponse response = request.process(solrClient);

		NamedList namedList = response.getResponse();

		NamedList<Object> luceneInfo = (NamedList<Object>)namedList.get(
			"lucene");

		return (String)luceneInfo.get("solr-spec-version");
	}

	@Reference
	protected ConnectionInformationBuilderFactory
		connectionInformationBuilderFactory;

	@Reference
	protected NodeInformationBuilderFactory nodeInformationBuilderFactory;

	@Reference
	protected SolrClientManager solrClientManager;

	@Reference
	protected SolrSearchEngine solrSearchEngine;

	private static final Log _log = LogFactoryUtil.getLog(
		SolrSearchEngineInformation.class);

	private String _defaultCollection;
	private volatile SolrConfiguration _solrConfiguration;

}