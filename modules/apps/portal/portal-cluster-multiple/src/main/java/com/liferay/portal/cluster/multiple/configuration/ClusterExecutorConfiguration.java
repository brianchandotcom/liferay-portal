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

package com.liferay.portal.cluster.multiple.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.portal.cluster.multiple.configuration.ClusterExecutorConfiguration",
	localization = "content/Language",
	name = "cluster-executor-configuration-name"
)
public interface ClusterExecutorConfiguration {

	@Meta.AD(
		deflt = "1000", name = "cluster-node-address-timeout", required = false
	)
	public long clusterNodeAddressTimeout();

	@Meta.AD(deflt = "false", name = "debug-enabled", required = false)
	public boolean debugEnabled();

	@Meta.AD(
		deflt = "access_key|connection_password|connection_username|secret_access_key",
		description = "excluded-property-keys-help",
		name = "excluded-property-keys", required = false
	)
	public String[] excludedPropertyKeys();

}