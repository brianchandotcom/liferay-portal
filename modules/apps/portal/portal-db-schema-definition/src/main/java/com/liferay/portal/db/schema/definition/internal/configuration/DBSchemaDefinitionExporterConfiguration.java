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
@ExtendedObjectClassDefinition(category = "upgrades")
@Meta.OCD(
	id = "com.liferay.portal.db.schema.definition.internal.configuration.DBSchemaDefinitionExporterConfiguration",
	localization = "content/Language",
	name = "db-schema-definition-exporter-configuration-name"
)
public interface DBSchemaDefinitionExporterConfiguration {

	@Meta.AD(name = "database-type", optionValues = {"mysql", "postgresql"})
	public String databaseType();

	@Meta.AD(name = "path")
	public String path();

}