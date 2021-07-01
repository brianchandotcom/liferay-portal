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

package com.liferay.portal.search.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ExtendedObjectClassDefinition(category = "search", generateUI = false)
@Meta.OCD(
	id = "com.liferay.portal.search.configuration.ReindexerConfiguration",
	localization = "content/Language", name = "reindexer-configuration-name"
)
@ProviderType
public interface ReindexerConfiguration {

	@Meta.AD(
		description = "nonbulk-indexing-override-help",
		name = "nonbulk-indexing-override", required = false
	)
	public String nonbulkIndexingOverride();

	@Meta.AD(
		description = "synchronous-execution-override-help",
		name = "synchronous-execution-override", required = false
	)
	public String synchronousExecutionOverride();

}