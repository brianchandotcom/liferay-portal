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

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.portal.search.configuration.DefaultKeywordQueryConfiguration",
	localization = "content/Language",
	name = "default-keyword-query-configuration-name"
)
public interface DefaultKeywordQueryConfiguration {

	@Meta.AD(
		deflt = "com.liferay.asset.kernel.model.AssetCategory|com.liferay.asset.kernel.model.AssetEntry|com.liferay.asset.kernel.model.AssetTag|com.liferay.asset.kernel.model.AssetVocabulary|com.liferay.portal.kernel.model.Contact|com.liferay.portal.kernel.model.Organization|com.liferay.portal.kernel.model.User|com.liferay.trash.kernel.model.TrashEntry",
		description = "disabled-entry-class-names-help",
		name = "disabled-entry-class-names", required = false
	)
	public String[] disabledEntryClassNames();

}