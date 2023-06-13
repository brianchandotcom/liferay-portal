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

package com.liferay.portal.k8s.agent.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Raymond Augé
 */
@ExtendedObjectClassDefinition(generateUI = false)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.k8s.agent.configuration.PortalK8sAgentConfiguration"
)
public interface PortalK8sAgentConfiguration {

	@Meta.AD(type = Meta.Type.String)
	public String apiServerHost();

	@Meta.AD(type = Meta.Type.String)
	public int apiServerPort();

	@Meta.AD(deflt = "true", required = false, type = Meta.Type.String)
	public boolean apiServerSSL();

	@Meta.AD(type = Meta.Type.String)
	public String caCertData();

	@Meta.AD(
		deflt = "lxc.liferay.com/metadataType=ext-provision", required = false,
		type = Meta.Type.String
	)
	public String labelSelector();

	@Meta.AD(type = Meta.Type.String)
	public String namespace();

	@Meta.AD(type = Meta.Type.String)
	public String saToken();

}