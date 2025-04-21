/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author István András Dézsi
 */
@ExtendedObjectClassDefinition(
	category = "virtual-instances", featureFlagKey = "LPD-11342"
)
@Meta.OCD(
	id = "com.liferay.portal.instances.internal.configuration.CopyPortalInstanceConfiguration",
	localization = "content/Language",
	name = "portal-instances-copy-configuration-name"
)
public interface CopyPortalInstanceConfiguration {

	@Meta.AD(
		name = "destination-company-id", required = false, type = Meta.Type.Long
	)
	public long destinationCompanyId();

	@Meta.AD(name = "name")
	public String name();

	@Meta.AD(name = "source-company-id", type = Meta.Type.Long)
	public long sourceCompanyId();

	@Meta.AD(name = "virtual-hostname")
	public String virtualHostname();

	@Meta.AD(name = "web-id")
	public String webId();

}