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

package com.liferay.exportimport.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jorge Díaz
 * @author Michael Bowerman
 */
@ExtendedObjectClassDefinition(
	category = "infrastructure",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	description = "export-import-service-configuration-description",
	id = "com.liferay.exportimport.configuration.ExportImportServiceConfiguration",
	localization = "content/Language",
	name = "export-import-service-configuration-name"
)
@ProviderType
public interface ExportImportServiceConfiguration {

	@Meta.AD(
		deflt = "false", description = "include-all-asset-links-help",
		name = "include-all-asset-links", required = false
	)
	public boolean includeAllAssetLinks();

	@Meta.AD(
		deflt = "false",
		description = "replicate-individual-deletions-by-default-help",
		name = "replicate-individual-deletions-by-default", required = false
	)
	public boolean replicateIndividualDeletionsByDefault();

	@Meta.AD(
		deflt = "false", description = "publish-permissions-by-default-help",
		name = "publish-permissions-by-default", required = false
	)
	public boolean publishPermissionsByDefault();

	@Meta.AD(
		deflt = "true", description = "validate-file-entry-references-help",
		name = "validate-file-entry-references", required = false
	)
	public boolean validateFileEntryReferences();

	@Meta.AD(
		deflt = "true", description = "validate-journal-feed-references-help",
		name = "validate-journal-feed-references", required = false
	)
	public boolean validateJournalFeedReferences();

	@Meta.AD(
		deflt = "true", description = "validate-layout-references-help",
		name = "validate-layout-references", required = false
	)
	public boolean validateLayoutReferences();

	@Meta.AD(
		deflt = "true", description = "validate-missing-references-help",
		name = "validate-missing-references", required = false
	)
	public boolean validateMissingReferences();

	@Meta.AD(
		description = "layout-references-validation-whitelisted-url-pattern-help",
		name = "layout-references-validation-whitelisted-url-pattern", required = false
	)
	public String[] layoutReferencesValidationWhitelistedURLPatterns();

}