/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.store.azure.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Josef Sustacek
 */
@ExtendedObjectClassDefinition(
	category = "file-storage", liferayLearnMessageKey = "general",
	liferayLearnMessageResource = "portal-store-azure"
)
@Meta.OCD(
	id = "com.liferay.portal.store.azure.configuration.AzureStoreConfiguration",
	localization = "content/Language", name = "azure-store-configuration-name"
)
public interface AzureStoreConfiguration {

	@Meta.AD(description = "connection-string-help", name = "connection-string")
	public String connectionString();

	@Meta.AD(description = "container-name-help", name = "container-name")
	public String containerName();

	@Meta.AD(
		description = "encryption-scope-help", name = "encryption-scope",
		required = false
	)
	public String encryptionScope();

	@Meta.AD(
		description = "http-logging-enabled-help",
		name = "http-logging-enabled", required = false
	)
	public boolean httpLoggingEnabled();

}