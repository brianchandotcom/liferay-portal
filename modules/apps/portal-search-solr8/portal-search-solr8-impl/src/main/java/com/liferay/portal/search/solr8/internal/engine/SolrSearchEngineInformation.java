/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.engine;

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
import com.liferay.portal.search.solr8.configuration.SolrConfiguration;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;

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
	configurationPid = "com.liferay.portal.search.solr8.configuration.SolrConfiguration",
	service = SearchEngineInformation.class
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

			_log.error("Unable to get node information", exception);
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
			_log.error("Unable to get node information", exception);

			StringBundler sb = new StringBundler(3);

			sb.append("(Error: ");
			sb.append(exception.toString());
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}
	}

	@Override
	public String getVendorString() {
		return "Solr";
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

		NamedList<Object> namedList = response.getResponse();

		NamedList<Object> luceneNamedList = (NamedList<Object>)namedList.get(
			"lucene");

		return (String)luceneNamedList.get("solr-spec-version");
	}

	@Reference
	protected ConnectionInformationBuilderFactory
		connectionInformationBuilderFactory;

	@Reference
	protected NodeInformationBuilderFactory nodeInformationBuilderFactory;

	@Reference
	protected SolrClientManager solrClientManager;

	private static final Log _log = LogFactoryUtil.getLog(
		SolrSearchEngineInformation.class);

	private volatile String _defaultCollection;
	private volatile SolrConfiguration _solrConfiguration;

}