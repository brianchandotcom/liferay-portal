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

package com.liferay.portal.search.elasticsearch.connection;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.elasticsearch.index.IndexFactory;

import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"configFileName=/META-INF/elasticsearch-embedded.yml",
		"testConfigFileName=/META-INF/elasticsearch-test.yml"
	},
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
	@Reference
	public void setIndexFactory(IndexFactory indexFactory) {
		super.setIndexFactory(indexFactory);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		setClusterName(
			MapUtil.getString(properties, "clusterName", CLUSTER_NAME));
		setConfigFileName(MapUtil.getString(properties, "configFileName"));
		setTestConfigFileName(
			MapUtil.getString(properties, "testConfigFileName"));

		initialize();
	}

	@Override
	protected Client createClient(ImmutableSettings.Builder builder) {
		NodeBuilder nodeBuilder = NodeBuilder.nodeBuilder();

		Class<?> clazz = getClass();

		builder.classLoader(clazz.getClassLoader());

		builder.loadFromClasspath(getConfigFileName());

		nodeBuilder.settings(builder);

		nodeBuilder.clusterName(getClusterName());

		_node = nodeBuilder.node();

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Starting embedded Elasticsearch cluster " + getClusterName());
		}

		_node.start();

		Client client = _node.client();

		if (_log.isDebugEnabled()) {
			stopWatch.stop();

			_log.debug(
				"Finished starting " + getClusterName() + " in " +
					stopWatch.getTime() + " ms");
		}

		return client;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmbeddedElasticsearchConnection.class);

	private Node _node;

}