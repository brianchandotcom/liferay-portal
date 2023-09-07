/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Raymond Augé
 */
@ExtendedObjectClassDefinition(generateUI = false)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.instances.internal.configuration.PortalInstancesConfiguration"
)
public interface PortalInstancesConfiguration {

	@Meta.AD(deflt = "true", required = false, type = Meta.Type.Boolean)
	public boolean active();

	@Meta.AD(required = false, type = Meta.Type.Integer)
	public int maxUsers();

	@Meta.AD(type = Meta.Type.String)
	public String mx();

	@Meta.AD(required = false, type = Meta.Type.String)
	public String siteInitializerKey();

	@Meta.AD(type = Meta.Type.String)
	public String virtualHostname();

}