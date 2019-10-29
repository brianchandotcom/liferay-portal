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
	id = "com.liferay.exportimport.configuration.ExportImportServiceConfiguration",
	localization = "content/Language",
	name = "export-import-service-configuration-name"
)
@ProviderType
public interface ExportImportServiceConfiguration {

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
		deflt = "true", description = "staging-delete-temp-lar-on-failure-help",
		name = "staging-delete-temp-lar-on-failure", required = false
	)
	public boolean stagingDeleteTempLarOnFailure();

	@Meta.AD(
		deflt = "true", description = "staging-delete-temp-lar-on-success-help",
		name = "staging-delete-temp-lar-on-success", required = false
	)
	public boolean stagingDeleteTempLarOnSuccess();

	@Meta.AD(
		deflt = "false",
		description = "staging-use-virtual-host-of-the-remote-site-help",
		name = "staging-use-virtual-host-of-the-remote-site", required = false
	)
	public boolean stagingUseVirtualHostForRemoteSite();

}