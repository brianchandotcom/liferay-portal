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

package com.liferay.portal.search.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.configuration.SearchPermissionCheckerConfiguration",
	localization = "content/Language",
	name = "search-permission-checker-configuration-name"
)
@ProviderType
public interface SearchPermissionCheckerConfiguration {

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Meta.AD(
		deflt = "true", description = "include-inherited-permissions-help",
		name = "include-inherited-permissions", required = false
	)
	public boolean includeInheritedPermissions();

	@Meta.AD(
		deflt = "250", description = "permission-terms-limit-help",
		name = "permission-terms-limit", required = false
	)
	public int permissionTermsLimit();

}