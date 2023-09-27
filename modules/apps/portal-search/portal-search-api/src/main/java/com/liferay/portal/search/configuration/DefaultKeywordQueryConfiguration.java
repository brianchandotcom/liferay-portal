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
	id = "com.liferay.portal.search.configuration.DefaultKeywordQueryConfiguration",
	localization = "content/Language",
	name = "default-keyword-query-configuration-name"
)
@ProviderType
public interface DefaultKeywordQueryConfiguration {

	@Meta.AD(
		deflt = "com.liferay.asset.kernel.model.AssetCategory|com.liferay.asset.kernel.model.AssetEntry|com.liferay.asset.kernel.model.AssetTag|com.liferay.asset.kernel.model.AssetVocabulary|com.liferay.portal.kernel.model.Contact|com.liferay.portal.kernel.model.Organization|com.liferay.portal.kernel.model.User|com.liferay.trash.kernel.model.TrashEntry",
		description = "disabled-entry-class-names-help",
		name = "disabled-entry-class-names", required = false
	)
	public String[] disabledEntryClassNames();

}