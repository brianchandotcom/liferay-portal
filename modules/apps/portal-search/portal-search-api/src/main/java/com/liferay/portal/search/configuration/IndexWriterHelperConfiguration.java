/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.configuration.IndexWriterHelperConfiguration",
	localization = "content/Language",
	name = "index-writer-helper-configuration-name"
)
@ProviderType
public interface IndexWriterHelperConfiguration {

	@Meta.AD(
		deflt = "true", description = "index-commit-immediately-help",
		name = "index-commit-immediately", required = false
	)
	public boolean indexCommitImmediately();

}