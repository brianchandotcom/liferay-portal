/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Mikel Lorza
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.document.library.configuration.DLFileEntryMimeTypeConfiguration",
	localization = "content/Language", name = "dl-mime-type-configuration-name"
)
public interface DLFileEntryMimeTypeConfiguration {

	@Meta.AD(
		deflt = "*", description = "file-mime-types-help",
		name = "file-mime-types", required = false
	)
	public String[] fileMimeTypes();

}