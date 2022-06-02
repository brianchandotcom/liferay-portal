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

package com.liferay.portal.instances.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Raymond Augé
 */
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.instances.configuration.PortalInstancesConfiguration"
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