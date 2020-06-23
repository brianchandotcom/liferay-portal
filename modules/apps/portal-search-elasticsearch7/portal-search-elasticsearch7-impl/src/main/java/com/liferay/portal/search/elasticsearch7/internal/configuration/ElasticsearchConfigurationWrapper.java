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

package com.liferay.portal.search.elasticsearch7.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.configuration.OperationMode;
import com.liferay.portal.search.elasticsearch7.configuration.RESTClientLoggerLevel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Bryan Engler
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration",
	immediate = true, service = ElasticsearchConfigurationWrapper.class
)
public class ElasticsearchConfigurationWrapper {

	public String additionalConfigurations() {
		return elasticsearchConfiguration.additionalConfigurations();
	}

	public String additionalIndexConfigurations() {
		return elasticsearchConfiguration.additionalIndexConfigurations();
	}

	public String additionalTypeMappings() {
		return elasticsearchConfiguration.additionalTypeMappings();
	}

	public boolean authenticationEnabled() {
		return elasticsearchConfiguration.authenticationEnabled();
	}

	public boolean bootstrapMlockAll() {
		return elasticsearchConfiguration.bootstrapMlockAll();
	}

	public String clusterName() {
		return elasticsearchConfiguration.clusterName();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public int embeddedHttpPort() {
		return elasticsearchConfiguration.embeddedHttpPort();
	}

	public String httpCORSAllowOrigin() {
		return elasticsearchConfiguration.httpCORSAllowOrigin();
	}

	public String httpCORSConfigurations() {
		return elasticsearchConfiguration.httpCORSConfigurations();
	}

	public boolean httpCORSEnabled() {
		return elasticsearchConfiguration.httpCORSEnabled();
	}

	public boolean httpSSLEnabled() {
		return elasticsearchConfiguration.httpSSLEnabled();
	}

	public String indexNamePrefix() {
		return elasticsearchConfiguration.indexNamePrefix();
	}

	public String indexNumberOfReplicas() {
		return elasticsearchConfiguration.indexNumberOfReplicas();
	}

	public String indexNumberOfShards() {
		return elasticsearchConfiguration.indexNumberOfShards();
	}

	public boolean logExceptionsOnly() {
		return elasticsearchConfiguration.logExceptionsOnly();
	}

	public String networkBindHost() {
		return elasticsearchConfiguration.networkBindHost();
	}

	public String networkHost() {
		return elasticsearchConfiguration.networkHost();
	}

	public String[] networkHostAddresses() {
		return elasticsearchConfiguration.networkHostAddresses();
	}

	public String networkPublishHost() {
		return elasticsearchConfiguration.networkPublishHost();
	}

	public String nodeName() {
		return elasticsearchConfiguration.nodeName();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public OperationMode operationMode() {
		return elasticsearchConfiguration.operationMode();
	}

	public String overrideTypeMappings() {
		return elasticsearchConfiguration.overrideTypeMappings();
	}

	public String password() {
		return elasticsearchConfiguration.password();
	}

	public boolean productionModeEnabled() {
		return elasticsearchConfiguration.productionModeEnabled();
	}

	public void register(
		ElasticsearchConfigurationObserver elasticsearchConfigurationObserver) {

		_elasticsearchConfigurationObservers.add(
			elasticsearchConfigurationObserver);
	}

	public String remoteClusterConnectionId() {
		return elasticsearchConfiguration.remoteClusterConnectionId();
	}

	public RESTClientLoggerLevel restClientLoggerLevel() {
		return elasticsearchConfiguration.restClientLoggerLevel();
	}

	public boolean sidecarDebug() {
		return elasticsearchConfiguration.sidecarDebug();
	}

	public String sidecarDebugSettings() {
		return elasticsearchConfiguration.sidecarDebugSettings();
	}

	public long sidecarHeartbeatInterval() {
		return elasticsearchConfiguration.sidecarHeartbeatInterval();
	}

	public String sidecarHome() {
		return elasticsearchConfiguration.sidecarHome();
	}

	public String sidecarHttpPort() {
		return elasticsearchConfiguration.sidecarHttpPort();
	}

	public String[] sidecarJVMOptions() {
		return elasticsearchConfiguration.sidecarJVMOptions();
	}

	public long sidecarShutdownTimeout() {
		return elasticsearchConfiguration.sidecarShutdownTimeout();
	}

	public String transportTcpPort() {
		return elasticsearchConfiguration.transportTcpPort();
	}

	public String truststorePassword() {
		return elasticsearchConfiguration.truststorePassword();
	}

	public String truststorePath() {
		return elasticsearchConfiguration.truststorePath();
	}

	public String truststoreType() {
		return elasticsearchConfiguration.truststoreType();
	}

	public void unregister(
		ElasticsearchConfigurationObserver elasticsearchConfigurationObserver) {

		_elasticsearchConfigurationObservers.remove(
			elasticsearchConfigurationObserver);
	}

	public String userName() {
		return elasticsearchConfiguration.username();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		_elasticsearchConfigurationObservers.forEach(
			elasticsearchConfigurationObserver ->
				elasticsearchConfigurationObserver.
					onElasticsearchConfigurationUpdate());
	}

	protected volatile ElasticsearchConfiguration elasticsearchConfiguration;

	private final Set<ElasticsearchConfigurationObserver>
		_elasticsearchConfigurationObservers = new ConcurrentSkipListSet<>();

}