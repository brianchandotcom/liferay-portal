/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.elasticsearch.cross.cluster.replication.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Bryan Engler
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.elasticsearch.cross.cluster.replication.internal.configuration.CrossClusterReplicationConfiguration",
	localization = "content/Language",
	name = "cross-cluster-replication-configuration-name"
)
public interface CrossClusterReplicationConfiguration {

	@Meta.AD(
		deflt = "false", description = "cross-cluster-replication-enabled-help",
		name = "cross-cluster-replication-enabled", required = false
	)
	public boolean ccrEnabled();

	@Meta.AD(
		description = "cross-cluster-replication-local-cluster-connection-configurations-help",
		name = "cross-cluster-replication-local-cluster-connection-configurations",
		required = false
	)
	public String[] ccrLocalClusterConnectionConfigurations();

	@Meta.AD(
		deflt = "leader", description = "remote-cluster-alias-help",
		name = "remote-cluster-alias", required = false
	)
	public String remoteClusterAlias();

	@Meta.AD(
		deflt = "localhost:9300",
		description = "remote-cluster-seed-node-transport-address-help",
		name = "remote-cluster-seed-node-transport-address", required = false
	)
	public String remoteClusterSeedNodeTransportAddress();

	@Meta.AD(
		description = "excluded-indexes-help", name = "excluded-indexes",
		required = false
	)
	public String[] excludedIndexes();

}