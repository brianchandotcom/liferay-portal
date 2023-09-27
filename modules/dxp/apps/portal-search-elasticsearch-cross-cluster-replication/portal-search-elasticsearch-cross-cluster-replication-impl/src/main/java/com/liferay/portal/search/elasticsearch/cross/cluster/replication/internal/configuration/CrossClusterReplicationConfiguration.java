/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		deflt = "true", description = "automatic-replication-enabled-help",
		name = "automatic-replication-enabled", required = false
	)
	public boolean automaticReplicationEnabled();

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