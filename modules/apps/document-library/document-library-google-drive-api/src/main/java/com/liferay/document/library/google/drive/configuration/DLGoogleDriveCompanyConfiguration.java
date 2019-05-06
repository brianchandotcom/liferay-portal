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

package com.liferay.document.library.google.drive.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.document.library.google.drive.configuration.DLGoogleDriveCompanyConfiguration",
	localization = "content/Language", name = "google-drive-configuration-name"
)
public interface DLGoogleDriveCompanyConfiguration {

	/**
	 * Returns the client ID of the Google application to use when operating on
	 * Google Drive files. If <code>null</code> or empty, the Google Drive
	 * integration disables itself.
	 *
	 * @return the client ID
	 */
	@ExtendedAttributeDefinition(
		descriptionArguments = "https://console.developers.google.com/apis/credentials/oauthclient",
		requiredInput = true
	)
	@Meta.AD(
		description = "client-id-description", name = "client-id",
		required = false
	)
	public String clientId();

	/**
	 * Returns the client secret of the Google application to use when operating
	 * on Google Drive files. If <code>null</code> or empty, the Google Drive
	 * integration disables itself.
	 *
	 * @return the client secret
	 */
	@ExtendedAttributeDefinition(
		descriptionArguments = "https://console.developers.google.com/apis/credentials/oauthclient",
		requiredInput = true
	)
	@Meta.AD(
		description = "client-secret-description", name = "client-secret",
		required = false
	)
	public String clientSecret();

	/**
	 * Returns the API Key of the Google Drive Picker API If <code>null</code>
	 * or empty, the Google Drive Shortcut integration disables itself.
	 *
	 * @return the api key
	 */
	@ExtendedAttributeDefinition(
		descriptionArguments = "https://console.developers.google.com/flows/enableapi?apiid=picker&credential=client_key"
	)
	@Meta.AD(
		description = "picker-api-key-description", name = "picker-api-key",
		required = false
	)
	public String pickerAPIKey();

}