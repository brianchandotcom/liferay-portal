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
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.configuration.SearchEngineHelperConfiguration",
	localization = "content/Language",
	name = "search-engine-helper-configuration-name"
)
@ProviderType
public interface SearchEngineHelperConfiguration {

	@Meta.AD(
		deflt = "com.liferay.asset.kernel.model.AssetCategory|com.liferay.asset.kernel.model.AssetEntry|com.liferay.asset.kernel.model.AssetVocabulary|com.liferay.calendar.model.Calendar|com.liferay.configuration.admin.web.model.ConfigurationModel|com.liferay.document.library.kernel.model.DLFileEntryMetadata|com.liferay.exportimport.kernel.model.ExportImportConfiguration|com.liferay.message.boards.model.MBThread|com.liferay.portal.kernel.model.Contact|com.liferay.portal.kernel.model.Organization|com.liferay.portal.kernel.model.UserGroup|com.liferay.portal.kernel.plugin.PluginPackage|com.liferay.trash.model.TrashEntry|com.liferay.wiki.model.WikiNode",
		name = "excluded-entry-class-names", required = false
	)
	public String[] excludedEntryClassNames();

}