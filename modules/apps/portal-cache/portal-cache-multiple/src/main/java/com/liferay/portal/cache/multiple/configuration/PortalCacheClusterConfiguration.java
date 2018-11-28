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

package com.liferay.portal.cache.multiple.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.cluster.Priority;

/**
 * @author Tina Tian
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.portal.cache.multiple.configuration.PortalCacheClusterConfiguration",
	name = "portal-cache-cluster-configuration-name"
)
public interface PortalCacheClusterConfiguration {

	@Meta.AD(deflt = "LEVEL1,LEVEL2", name = "priorities", required = false)
	public Priority[] priorities();

	@Meta.AD(deflt = "false", name = "using-coalesced-pipe", required = false)
	public boolean usingCoalescedPipe();

}