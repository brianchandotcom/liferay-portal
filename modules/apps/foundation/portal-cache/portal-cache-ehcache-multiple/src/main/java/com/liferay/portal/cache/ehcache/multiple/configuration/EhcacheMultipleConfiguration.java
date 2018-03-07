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

package com.liferay.portal.cache.ehcache.multiple.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tina Tian
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.portal.cache.ehcache.multiple.configuration.EhcacheMultipleConfiguration",
	localization = "content/Language",
	name = "ehcache-multiple-configuration-name"
)
public interface EhcacheMultipleConfiguration {

	@Meta.AD(
		deflt = "com.liferay.portal.cache.ehcache.multiple.internal.bootstrap.RMIBootstrapCacheLoaderFactory",
		required = false
	)
	public String bootstrapCacheLoaderFactoryClass();

	@Meta.AD(
		deflt = "com.liferay.portal.cache.ehcache.multiple.internal.distribution.RMICacheReplicatorFactory",
		required = false
	)
	public String cacheReplicatorFactoryClass();

}