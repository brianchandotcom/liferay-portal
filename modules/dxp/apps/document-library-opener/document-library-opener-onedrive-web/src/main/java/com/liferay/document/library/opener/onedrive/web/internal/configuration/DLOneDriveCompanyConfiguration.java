/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.configuration;

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
	id = "com.liferay.document.library.opener.onedrive.web.internal.configuration.DLOneDriveCompanyConfiguration",
	localization = "content/Language", name = "onedrive-configuration-name"
)
public interface DLOneDriveCompanyConfiguration {

	/**
	 * Returns the client ID of the OneDrive application to use when operating
	 * on OneDrive files. If <code>null</code> or empty, the OneDrive
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
		description = "onedrive-client-id-description", name = "client-id",
		required = false
	)
	public String clientId();

	/**
	 * Returns the client secret of the OneDrive application to use when
	 * operating on OneDrive files. If <code>null</code> or empty, the One Drive
	 * integration disables itself.
	 *
	 * @return the client secret
	 * @review
	 */
	@ExtendedAttributeDefinition(
		descriptionArguments = "https://docs.microsoft.com/graph/auth-register-app-v2",
		requiredInput = true
	)
	@Meta.AD(
		description = "onedrive-client-secret-description",
		name = "client-secret", required = false
	)
	public String clientSecret();

	/**
	 * Returns the tenant of the OneDrive application to use when operating on
	 * OneDrive files. If <code>null</code> or empty, the One Drive integration
	 * disables itself.
	 *
	 * @return the tenant
	 * @review
	 */
	@ExtendedAttributeDefinition(
		descriptionArguments = "https://docs.microsoft.com/onedrive/find-your-office-365-tenant-id",
		requiredInput = true
	)
	@Meta.AD(
		description = "onedrive-tenant-description", name = "onedrive-tenant",
		required = false
	)
	public String tenant();

}