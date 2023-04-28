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

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Gustavo Lima
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.elasticsearch7.configuration.DeepPaginationConfiguration",
	localization = "content/Language",
	name = "deep-pagination-configuration-name"
)
@ProviderType
public interface DeepPaginationConfiguration {

	@Meta.AD(
		deflt = "false", description = "enable-deep-pagination-help",
		name = "enable-deep-pagination", required = false
	)
	public boolean enableDeepPagination();

	@Meta.AD(
		deflt = "300", description = "point-in-time-keep-alive-seconds-help",
		name = "point-in-time-keep-alive-seconds", required = false
	)
	public int pointInTimeKeepAliveSeconds();

}