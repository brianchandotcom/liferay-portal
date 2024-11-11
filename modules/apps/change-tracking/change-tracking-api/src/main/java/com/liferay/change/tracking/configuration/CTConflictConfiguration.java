/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author David Truong
 */
@ExtendedObjectClassDefinition(
	category = "publications",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.change.tracking.configuration.CTConflictConfiguration",
	localization = "content/Language",
	name = "publications-portal-conflicts-configuration-name"
)
public interface CTConflictConfiguration {

	@Meta.AD(
		deflt = "false", name = "modification-deletion-conflict-check-enabled",
		required = false
	)
	public boolean modificationDeletionConflictCheckEnabled();

	@Meta.AD(
		deflt = "false",
		description = "schema-version-check-enabled-description",
		name = "schema-version-check-enabled", required = false
	)
	public boolean schemaVersionCheckEnabled();

}