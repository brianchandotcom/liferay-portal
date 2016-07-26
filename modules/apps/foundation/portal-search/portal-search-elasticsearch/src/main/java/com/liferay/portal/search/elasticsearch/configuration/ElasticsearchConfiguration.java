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

package com.liferay.portal.search.elasticsearch.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.search.elasticsearch.connection.OperationMode;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration",
	localization = "content/Language",
	name = "elastic.search.configuration.name"
)
public interface ElasticsearchConfiguration {

	@Meta.AD(
		deflt = "LiferayElasticsearchCluster",
		description = "cluster-name-help", required = false
	)
	public String clusterName();

	@Meta.AD(
		deflt = "EMBEDDED", description = "operation-mode-help",
		required = false
	)
	public OperationMode operationMode();

	@Meta.AD(
		deflt = "liferay-", description = "index-name-prefix-help",
		required = false
	)
	public String indexNamePrefix();

	@Meta.AD(
		deflt = "false", description = "bootstrap-mlockall-help",
		required = false
	)
	public boolean bootstrapMlockAll();

	@Meta.AD(
		deflt = "true", description = "log-exceptions-only-help",
		required = false
	)
	public boolean logExceptionsOnly();

	@Meta.AD(
		deflt = "5", description = "retry-on-conflict-help", required = false
	)
	public int retryOnConflict();

	@Meta.AD(
		deflt = "9300-9400",
		description = "discovery-zen-ping-unicast-hosts-port-help",
		required = false
	)
	public String discoveryZenPingUnicastHostsPort();

	@Meta.AD(deflt = "", description = "network-host-help", required = false)
	public String networkHost();

	@Meta.AD(
		deflt = "", description = "network-bind-host-help", required = false
	)
	public String networkBindHost();

	@Meta.AD(
		deflt = "", description = "network-publish-host-help", required = false
	)
	public String networkPublishHost();

	@Meta.AD(
		deflt = "", description = "transport-tcp-port-help", required = false
	)
	public String transportTcpPort();

	@Meta.AD(
		deflt = "localhost:9300", description = "transport-addresses-help",
		required = false
	)
	public String[] transportAddresses();

	@Meta.AD(
		deflt = "true", description = "client-transport-sniff-help",
		required = false
	)
	public boolean clientTransportSniff();

	@Meta.AD(
		deflt = "false",
		description = "client-transport-ignore-cluster-name-help",
		required = false
	)
	public boolean clientTransportIgnoreClusterName();

	@Meta.AD(
		deflt = "5s",
		description = "client-transport-nodes-sampler-interval-help",
		required = false
	)
	public String clientTransportNodesSamplerInterval();

	@Meta.AD(
		deflt = "true", description = "http-enabled-help", required = false
	)
	public boolean httpEnabled();

	@Meta.AD(
		deflt = "true", description = "http-cors-enabled-help", required = false
	)
	public boolean httpCORSEnabled();

	@Meta.AD(
		deflt = "/https?:\\/\\/localhost(:[0-9]+)?/",
		description = "http-cors-allow-origin-help", required = false
	)
	public String httpCORSAllowOrigin();

	@Meta.AD(description = "%http-cors-configurations-help", required = false)
	public String httpCORSConfigurations();

	@Meta.AD(description = "%additional-configurations-help", required = false)
	public String additionalConfigurations();

	@Meta.AD(
		description = "additional-index-configurations-help", required = false
	)
	public String additionalIndexConfigurations();

	@Meta.AD(description = "%additional-type-mappings-help", required = false)
	public String additionalTypeMappings();

}