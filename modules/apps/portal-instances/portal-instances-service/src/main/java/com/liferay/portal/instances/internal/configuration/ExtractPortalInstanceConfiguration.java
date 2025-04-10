/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Mariano Álvaro Sáiz
 */
@ExtendedObjectClassDefinition(
	category = "upgrades", featureFlagKey = "LPD-11342"
)
@Meta.OCD(
	id = "com.liferay.portal.instances.internal.configuration.ExtractPortalInstanceConfiguration",
	localization = "content/Language",
	name = "portal-instances-extraction-configuration-name"
)
public interface ExtractPortalInstanceConfiguration {

	@Meta.AD(name = "extract-company-id", type = Meta.Type.Long)
	public long extractCompanyId();

}