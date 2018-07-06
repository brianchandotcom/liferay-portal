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

package com.liferay.users.admin.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Drew Brokke
 */
@ExtendedObjectClassDefinition(category = "users")
@Meta.OCD(
	id = "com.liferay.users.admin.configuration.UserFileUploadsConfiguration",
	localization = "content/Language",
	name = "user-file-uploads-configuration-name"
)
public interface UserFileUploadsConfiguration {

	@Meta.AD(
		deflt = "307200", description = "users-image-maximum-file-size-help",
		name = "maximum-file-size", required = false
	)
	public long imageMaxSize();

	@Meta.AD(
		deflt = "290", description = "users-image-maximum-height-help",
		name = "maximum-height", required = false
	)
	public int imageMaxHeight();

	@Meta.AD(
		deflt = "290", description = "users-image-maximum-width-help",
		name = "maximum-width", required = false
	)
	public int imageMaxWidth();

	@Meta.AD(
		deflt = "true", description = "users-image-check-token-help",
		name = "check-token", required = false
	)
	public boolean imageCheckToken();

	@Meta.AD(
		deflt = "true",
		description = "use-initials-for-default-user-portrait-help",
		name = "use-initials-for-default-user-portrait", required = false
	)
	public boolean imageDefaultUseInitials();

}