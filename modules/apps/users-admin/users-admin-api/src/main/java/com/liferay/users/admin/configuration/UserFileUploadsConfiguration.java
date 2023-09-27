/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Meta.AD(
		deflt = "true",
		description = "use-initials-for-default-user-portrait-help",
		name = "use-initials-for-default-user-portrait", required = false
	)
	public boolean imageDefaultUseInitials();

}