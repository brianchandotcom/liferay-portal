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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch.index.IndexFactory;
import com.liferay.registry.util.StringPlus;

import java.net.InetAddress;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	property = {
		"configFileName=/META-INF/elasticsearch-remote.yml",
		"service.ranking:Integer=1000",
		"testConfigFileName=/META-INF/elasticsearch-test.yml"
	},
	service = ElasticsearchConnection.class
)
public class RemoteElasticsearchConnection extends BaseElasticsearchConnection {

	@Override
	public void close() {
		super.close();
	}

	@Override
	@Reference
	public void setIndexFactory(IndexFactory indexFactory) {
		super.setIndexFactory(indexFactory);
	}

	public void setTransportAddresses(Set<String> transportAddresses) {
		_transportAddresses = transportAddresses;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		setClusterName(
			MapUtil.getString(properties, "clusterName", CLUSTER_NAME));
		setConfigFileName(MapUtil.getString(properties, "configFileName"));
		setTestConfigFileName(
			MapUtil.getString(properties, "testConfigFileName"));

		List<String> transportAddresses = StringPlus.asList(
			properties.get("transportAddresses"));

		setTransportAddresses(new HashSet<String>(transportAddresses));

		initialize();
	}

	@Override
	protected Client createClient(ImmutableSettings.Builder builder) {
		if (_transportAddresses.isEmpty()) {
			throw new IllegalStateException(
				"There must be at least one transport address");
		}

		Class<?> clazz = getClass();

		builder.classLoader(clazz.getClassLoader());

		builder.loadFromClasspath(getConfigFileName());

		TransportClient transportClient = new TransportClient(builder);

		builder.put("client.transport.sniff", true);
		builder.put("cluster.name", getClusterName());

		for (String transportAddress : _transportAddresses) {
			String[] transportAddressParts = StringUtil.split(
				transportAddress, StringPool.COLON);

			try {
				InetAddress inetAddress = InetAddress.getByName(
					transportAddressParts[0]);

				int port = GetterUtil.getInteger(transportAddressParts[1]);

				transportClient.addTransportAddress(
					new InetSocketTransportAddress(inetAddress, port));
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to add transport address " + transportAddress,
						e);
				}
			}
		}

		return transportClient;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteElasticsearchConnection.class);

	private Set<String> _transportAddresses = new HashSet<String>();

}