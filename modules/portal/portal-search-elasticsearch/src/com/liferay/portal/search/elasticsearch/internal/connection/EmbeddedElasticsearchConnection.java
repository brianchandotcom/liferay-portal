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

package com.liferay.portal.search.elasticsearch.internal.connection;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.connection.BaseElasticsearchConnection;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnection;
import com.liferay.portal.search.elasticsearch.connection.OperationMode;
import com.liferay.portal.search.elasticsearch.index.IndexFactory;

import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration",
	immediate = true, property = { "operation.mode=EMBEDDED" },
	service = ElasticsearchConnection.class
)
public class EmbeddedElasticsearchConnection
	extends BaseElasticsearchConnection {

	@Override
	public void close() {
		super.close();

		if (_node != null) {
			_node.close();
		}
	}

	@Override
	public OperationMode getOperationMode() {
		return OperationMode.EMBEDDED;
	}

	@Override
	@Reference
	public void setIndexFactory(IndexFactory indexFactory) {
		super.setIndexFactory(indexFactory);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		elasticsearchConfiguration = Configurable.createConfigurable(
			ElasticsearchConfiguration.class, properties);
	}

	@Override
	protected Client createClient(ImmutableSettings.Builder builder) {
		NodeBuilder nodeBuilder = NodeBuilder.nodeBuilder();

		nodeBuilder.settings(builder);

		_node = nodeBuilder.node();

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Starting embedded Elasticsearch cluster " +
					elasticsearchConfiguration.clusterName());
		}

		_node.start();

		Client client = _node.client();

		if (_log.isDebugEnabled()) {
			stopWatch.stop();

			_log.debug(
				"Finished starting " +
					elasticsearchConfiguration.clusterName() + " in " +
						stopWatch.getTime() + " ms");
		}

		return client;
	}

	@Deactivate
	protected void deactivate(Map<String, Object> properties) {
		close();
	}

	@Override
	protected void loadRequiredDefaultConfigurations(
		ImmutableSettings.Builder builder) {

		builder.put("cluster.name", elasticsearchConfiguration.clusterName());
		builder.put(
			"http.cors.enabled", elasticsearchConfiguration.httpCORSEnabled());
		builder.put("http.enabled", elasticsearchConfiguration.httpEnabled());
		builder.put("index.number_of_replicas", 0);
		builder.put("index.number_of_shards", 1);
		builder.put("node.client", false);
		builder.put("node.data", true);
		builder.put("node.local", true);
		builder.put(
			"path.data",
			_props.get(PropsKeys.LIFERAY_HOME) + "/data/elasticsearch");
		builder.put("path.logs", _props.get(PropsKeys.LIFERAY_HOME) + "/logs");
		builder.put(
			"path.work", SystemProperties.get(SystemProperties.TMP_DIR));

		if (PortalRunMode.isTestMode()) {
			builder.put("index.refresh_interval", "1ms");
			builder.put("index.store.type", "memory");
			builder.put("index.translog.flush_threshold_ops", "1");
			builder.put("index.translog.interval", "1ms");
		}
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_props = props;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmbeddedElasticsearchConnection.class);

	private Node _node;
	private Props _props;

}