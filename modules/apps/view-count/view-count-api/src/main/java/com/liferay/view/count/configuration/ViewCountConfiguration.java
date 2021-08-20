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

package com.liferay.view.count.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Eric Yan
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	description = "view-count-configuration-name-help",
	id = "com.liferay.view.count.configuration.ViewCountConfiguration",
	localization = "content/Language", name = "view-count-configuration-name"
)
@ProviderType
public interface ViewCountConfiguration {

	@Meta.AD(
		deflt = "true", description = "enabled-help[view-count]",
		name = "enabled", required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "", description = "disabled-class-names-help",
		name = "disabled-class-names", required = false
	)
	public String[] disabledClassNames();

}