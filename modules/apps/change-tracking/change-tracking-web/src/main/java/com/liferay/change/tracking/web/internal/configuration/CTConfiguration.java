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
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.change.tracking.web.internal.configuration.CTConfiguration",
	localization = "content/Language",
	name = "publications-portal-configuration-name"
)
public interface CTConfiguration {

	@Meta.AD(
		deflt = "Administrator", name = "administrator-role-names",
		required = false
	)
	public String[] administratorRoleNames();

	@Meta.AD(
		deflt = "com.liferay.portal.kernel.model.Group|com.liferay.portal.kernel.model.User",
		name = "root-display-class-names", required = false
	)
	public String[] rootDisplayClassNames();

	@Meta.AD(
		deflt = "com.liferay.asset.kernel.model.AssetEntry",
		name = "root-display-child-class-names", required = false
	)
	public String[] rootDisplayChildClassNames();

}