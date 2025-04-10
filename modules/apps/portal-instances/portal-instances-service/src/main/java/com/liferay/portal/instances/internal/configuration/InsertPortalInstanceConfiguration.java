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
	id = "com.liferay.portal.instances.internal.configuration.InsertPortalInstanceConfiguration",
	localization = "content/Language",
	name = "portal-instances-insertion-configuration-name"
)
public interface InsertPortalInstanceConfiguration {

	@Meta.AD(name = "insert-company-id", type = Meta.Type.Long)
	public long insertCompanyId();

	@Meta.AD(name = "new-name", required = false)
	public String newName();

	@Meta.AD(name = "new-virtual-hostname", required = false)
	public String newVirtualHostname();

	@Meta.AD(name = "new-web-id", required = false)
	public String newWebId();

}