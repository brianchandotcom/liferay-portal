/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Roberto Díaz
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.document.library.configuration.DLFileEntryFriendlyURLConfiguration",
	localization = "content/Language",
	name = "dl-file-entry-friendly-url-configuration-name"
)
public interface DLFileEntryFriendlyURLConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "enable-friendly-url-with-extension-description",
		name = "enable-friendly-url-with-extension", required = false
	)
	public boolean enableFriendlyURLWithExtension();

}