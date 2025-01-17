/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.virtual.instances.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Mariano Álvaro Sáiz
 */
@ExtendedObjectClassDefinition(generateUI = false)
@Meta.OCD(
	id = "com.liferay.portal.virtual.instances.internal.configuration.InsertVirtualInstanceConfiguration"
)
public interface InsertVirtualInstanceConfiguration {

	@Meta.AD(required = false)
	public String newName();

	@Meta.AD(required = false)
	public String newVirtualHostname();

	@Meta.AD(required = false)
	public String newWebId();

	@Meta.AD(type = Meta.Type.Long)
	public long companyId();

}