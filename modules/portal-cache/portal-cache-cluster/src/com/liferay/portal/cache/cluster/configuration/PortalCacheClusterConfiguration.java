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

package com.liferay.portal.cache.cluster.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.kernel.cluster.Priority;

/**
 * @author Tina Tian
 */
@Meta.OCD(
	id = "com.liferay.portal.cache.cluster.configuration.PortalCacheClusterConfiguration"
)
public interface PortalCacheClusterConfiguration {

	@Meta.AD(deflt = "2", required = false)
	public int channelNumber();

	@Meta.AD(deflt = "liferay/ehcache_cluster", required = false)
	public String destinationName();

	@Meta.AD(deflt = "LEVEL1|LEVEL2", required = false)
	public Priority[] priorities();

}