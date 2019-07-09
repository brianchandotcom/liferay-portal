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

package com.liferay.document.library.opener.one.drive.web.internal.portlet.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Cristina González
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.document.library.opener.one.drive.web.internal.portlet.configuration.DLOneDriveCompanyConfiguration",
	localization = "content/Language", name = "one-drive-configuration-name"
)
public interface DLOneDriveCompanyConfiguration {

	/**
	 * Returns the client ID of the One Drive application to use when operating
	 * on One Drive files. If <code>null</code> or empty, the One Drive
	 * integration disables itself.
	 *
	 * @return the client ID
	 * @review
	 */
	@ExtendedAttributeDefinition(
		descriptionArguments = "https://docs.microsoft.com/graph/auth-register-app-v2",
		requiredInput = true
	)
	@Meta.AD(
		description = "one-drive-client-id-description", name = "client-id",
		required = false
	)
	public String clientId();

	/**
	 * Returns the client secret of the One Drive application to use when
	 * operating on One Drive files. If <code>null</code> or empty, the One
	 * Drive integration disables itself.
	 *
	 * @return the client secret
	 * @review
	 */
	@ExtendedAttributeDefinition(
		descriptionArguments = "https://docs.microsoft.com/graph/auth-register-app-v2",
		requiredInput = true
	)
	@Meta.AD(
		description = "one-drive-client-secret-description",
		name = "client-secret", required = false
	)
	public String clientSecret();

	/**
	 * Returns the tenant of the One Drive application to use when
	 * operating on One Drive files. If <code>null</code> or empty, the One
	 * Drive integration disables itself.
	 *
	 * @return the tenant
	 * @review
	 */
	@ExtendedAttributeDefinition(
		descriptionArguments = "https://docs.microsoft.com/onedrive/find-your-office-365-tenant-id",
		requiredInput = true
	)
	@Meta.AD(
		description = "one-drive-tenant-description", name = "one-drive-tenant",
		required = false
	)
	public String tenant();

}