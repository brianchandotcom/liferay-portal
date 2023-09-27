/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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