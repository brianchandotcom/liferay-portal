/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Jorge Díaz
 */
@ExtendedObjectClassDefinition(category = "dynamic-data-mapping")
@Meta.OCD(
	id = "com.liferay.dynamic.data.mapping.configuration.DDMIndexerConfiguration",
	localization = "content/Language", name = "ddm-indexer-configuration-name"
)
public interface DDMIndexerConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "enable-legacy-ddm-index-fields-description",
		name = "enable-legacy-ddm-index-fields", required = false
	)
	public boolean enableLegacyDDMIndexFields();

}