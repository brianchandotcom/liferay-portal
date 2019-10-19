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

package com.liferay.portal.search.elasticsearch7.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration",
	localization = "content/Language",
	name = "elasticsearch7-configuration-name"
)
public interface ElasticsearchConfiguration {

	@Meta.AD(
		deflt = "EMBEDDED", description = "operation-mode-help",
		name = "operation-mode", required = false
	)
	public OperationMode operationMode();

	@Meta.AD(
		deflt = "http://localhost:9200",
		description = "network-host-addresses-help",
		name = "network-host-addresses", required = false
	)
	public String[] networkHostAddresses();

	@Meta.AD(
		deflt = "liferay-", description = "index-name-prefix-help",
		name = "index-name-prefix", required = false
	)
	public String indexNamePrefix();

	@Meta.AD(
		deflt = "", description = "number-of-index-replicas-help",
		name = "number-of-index-replicas", required = false
	)
	public String indexNumberOfReplicas();

	@Meta.AD(
		deflt = "", description = "number-of-index-shards-help",
		name = "number-of-index-shards", required = false
	)
	public String indexNumberOfShards();

	@Meta.AD(
		description = "additional-index-configurations-help",
		name = "additional-index-configurations", required = false
	)
	public String additionalIndexConfigurations();

	@Meta.AD(
		description = "additional-type-mappings-help",
		name = "additional-type-mappings", required = false
	)
	public String additionalTypeMappings();

	@Meta.AD(
		description = "override-type-mappings-help",
		name = "override-type-mappings", required = false
	)
	public String overrideTypeMappings();

	@Meta.AD(
		deflt = "false", description = "authentication-enabled-help",
		name = "authentication-enabled", required = false
	)
	public boolean authenticationEnabled();

	@Meta.AD(
		deflt = "elastic", description = "username-help", name = "username",
		required = false
	)
	public String username();

	@Meta.AD(
		description = "password-help", name = "password", required = false,
		type = Meta.Type.Password
	)
	public String password();

	@Meta.AD(
		deflt = "false", description = "http-ssl-enabled-help",
		name = "http-ssl-enabled", required = false
	)
	public boolean httpSSLEnabled();

	@Meta.AD(
		deflt = "pkcs12", description = "truststore-type-help",
		name = "truststore-type", required = false
	)
	public String truststoreType();

	@Meta.AD(
		deflt = "/path/to/localhost.p12", description = "truststore-path-help",
		name = "truststore-path", required = false
	)
	public String truststorePath();

	@Meta.AD(
		description = "truststore-password-help", name = "truststore-password",
		required = false, type = Meta.Type.Password
	)
	public String truststorePassword();

	@Meta.AD(
		deflt = "true", description = "log-exceptions-only-help",
		name = "log-exceptions-only", required = false
	)
	public boolean logExceptionsOnly();

	@Meta.AD(
		deflt = "ERROR", description = "rest-client-logger-level-help",
		name = "rest-client-logger-level", required = false
	)
	public String restClientLoggerLevel();

	@Meta.AD(
		deflt = "LiferayElasticsearchCluster",
		description = "cluster-name-help", name = "cluster-name",
		required = false
	)
	public String clusterName();

	@Meta.AD(
		deflt = "false", description = "bootstrap-mlockall-help",
		name = "bootstrap-mlockall", required = false
	)
	public boolean bootstrapMlockAll();

	@Meta.AD(
		deflt = "9201", description = "embedded-http-port-help",
		name = "embedded-http-port", required = false
	)
	public int embeddedHttpPort();

	@Meta.AD(
		deflt = "9300-9400",
		description = "discovery-zen-ping-unicast-hosts-port-help",
		name = "discovery-zen-ping-unicast-hosts-port", required = false
	)
	public String discoveryZenPingUnicastHostsPort();

	@Meta.AD(
		deflt = "", description = "network-host-help", name = "network-host",
		required = false
	)
	public String networkHost();

	@Meta.AD(
		deflt = "", description = "network-bind-host-help",
		name = "network-bind-host", required = false
	)
	public String networkBindHost();

	@Meta.AD(
		deflt = "", description = "network-publish-host-help",
		name = "network-publish-host", required = false
	)
	public String networkPublishHost();

	@Meta.AD(
		deflt = "", description = "transport-tcp-port-help",
		name = "transport-tcp-port", required = false
	)
	public String transportTcpPort();

	@Meta.AD(
		description = "additional-configurations-help",
		name = "additional-configurations", required = false
	)
	public String additionalConfigurations();

	@Meta.AD(
		deflt = "true", description = "http-cors-enabled-help",
		name = "http-cors-enabled", required = false
	)
	public boolean httpCORSEnabled();

	@Meta.AD(
		deflt = "/https?:\\/\\/localhost(:[0-9]+)?/",
		description = "http-cors-allow-origin-help",
		name = "http-cors-allow-origin", required = false
	)
	public String httpCORSAllowOrigin();

	@Meta.AD(
		description = "http-cors-configurations-help",
		name = "http-cors-configurations", required = false
	)
	public String httpCORSConfigurations();

}