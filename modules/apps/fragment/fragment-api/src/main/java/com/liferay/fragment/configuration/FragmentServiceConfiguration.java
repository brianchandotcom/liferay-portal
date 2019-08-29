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

package com.liferay.fragment.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Pavel Savinov
 */
@ExtendedObjectClassDefinition(
	category = "page-fragments",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.fragment.configuration.FragmentServiceConfiguration",
	localization = "content/Language", name = "fragment-configuration-name"
)
@ProviderType
public interface FragmentServiceConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "propagate-fragment-changes-automatically-description",
		name = "propagate-fragment-changes-automatically", required = false
	)
	public boolean propagateChanges();

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	@Meta.AD(
		deflt = "false", description = "enable-configuration-help",
		name = "enable-configuration", required = false
	)
	public boolean enableConfiguration();

}