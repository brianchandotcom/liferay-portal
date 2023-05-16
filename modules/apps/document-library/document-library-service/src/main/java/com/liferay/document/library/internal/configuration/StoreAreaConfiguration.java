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

package com.liferay.document.library.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media", generateUI = false
)
@Meta.OCD(
	id = "com.liferay.document.library.internal.configuration.StoreAreaConfiguration",
	localization = "content/Language", name = "store-area-configuration-name"
)
public interface StoreAreaConfiguration {

	@Meta.AD(
		deflt = "2", description = "store-area-cleanup-interval-help",
		name = "store-area-cleanup-interval", required = false
	)
	public int cleanUpInterval();

	@Meta.AD(
		deflt = "100", description = "store-area-deletion-quota-help",
		name = "store-area-deletion-quota", required = false
	)
	public int deletionQuota();

	@Meta.AD(
		deflt = "31", description = "store-area-eviction-age-help",
		name = "store-area-eviction-age", required = false
	)
	public int evictionAge();

}