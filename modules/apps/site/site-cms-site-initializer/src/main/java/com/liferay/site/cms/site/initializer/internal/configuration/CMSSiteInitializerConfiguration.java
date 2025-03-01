/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Sam Ziemer
 */
@ExtendedObjectClassDefinition(category = "assets", generateUI = false)
@Meta.OCD(
	id = "com.liferay.site.cms.site.initializer.internal.configuration.CMSSiteInitializerConfiguration",
	localization = "content/Language",
	name = "cms-site-initializer-configuration-name"
)
public interface CMSSiteInitializerConfiguration {

	@Meta.AD(
		deflt = "com.liferay.asset.kernel.model.AssetVocabulary",
		name = "categorization-class-names", required = false
	)
	public String[] categorizationClassNames();

	@Meta.AD(
		deflt = "L_CMS_CONTENT_STRUCTURES",
		name = "contents-object-definition-folder-external-reference-codes",
		required = false
	)
	public String[] contentsObjectDefinitionFolderExternalReferenceCodes();

	@Meta.AD(
		deflt = "L_CMS_FILE_TYPES",
		name = "files-object-definition-folder-external-reference-codes",
		required = false
	)
	public String[] filesObjectDefinitionFolderExternalReferenceCodes();

}