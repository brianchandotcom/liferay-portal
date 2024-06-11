/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Mariano Álvaro Sáiz
 */
@ExtendedObjectClassDefinition(
	category = "infrastructure",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.portal.db.schema.definition.internal.configuration.DBSchemaDefinitionExporterConfiguration",
	localization = "content/Language",
	name = "db-schema-dump-configuration-name"
)
public interface DBSchemaDefinitionExporterConfiguration {

	@Meta.AD(
		description = "generated-dump-database-type-description",
		name = "generated-dump-database-type"
	)
	public String databaseType();

	@Meta.AD(
		description = "generated-dump-path-description",
		name = "generated-dump-path"
	)
	public String path();

}