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

package com.liferay.change.tracking.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tomas Polesovsky
 */
@ExtendedObjectClassDefinition(
	category = "publications",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.change.tracking.web.internal.configuration.CTConfiguration",
	localization = "content/Language",
	name = "publications-portal-configuration-name"
)
public interface CTConfiguration {

	@Meta.AD(
		deflt = "1000", description = "context-view-limit-count-help",
		name = "context-view-limit-count", required = false
	)
	public int contextViewLimitCount();

	@Meta.AD(
		deflt = "true", description = "context-view-include-production-help",
		name = "context-view-include-production", required = false
	)
	public boolean contextViewIncludeProduction();

	@Meta.AD(
		deflt = "true", description = "show-all-items-enabled-help",
		name = "show-all-items-enabled", required = false
	)
	public boolean showAllItemsEnabled();

}