/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Raymond Augé
 */
@ExtendedObjectClassDefinition(
	generateUI = false, scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.client.extension.type.configuration.CETConfiguration"
)
public interface CETConfiguration {

	@Meta.AD(type = Meta.Type.String)
	public String baseURL();

	@Meta.AD(required = false, type = Meta.Type.String)
	public String description();

	@Meta.AD(type = Meta.Type.String)
	public String name();

	@Meta.AD(required = false, type = Meta.Type.String)
	public String[] properties();

	@Meta.AD(required = false, type = Meta.Type.String)
	public String sourceCodeURL();

	@Meta.AD(type = Meta.Type.String)
	public String type();

	@Meta.AD(required = false, type = Meta.Type.String)
	public String[] typeSettings();

}