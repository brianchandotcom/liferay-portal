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

package com.liferay.portal.k8s.agent.configuration.v1;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Raymond Augé
 */
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.k8s.agent.configuration.v1.PortalK8sAgentConfiguration",
	localization = "content/Language", name = "k8s-agent-configuration-name"
)
public interface PortalK8sAgentConfiguration {

	@Meta.AD(
		description = "api-server-host-description", name = "api-server-host",
		type = Meta.Type.String
	)
	public String apiServerHost();

	@Meta.AD(
		description = "api-server-port-description", name = "api-server-port",
		type = Meta.Type.String
	)
	public int apiServerPort();

	@Meta.AD(
		deflt = "true", description = "api-server-ssl-description",
		name = "api-server-ssl", required = false, type = Meta.Type.String
	)
	public boolean apiServerSSL();

	@Meta.AD(
		description = "ca-cert-data-description", name = "ca-cert-data",
		type = Meta.Type.String
	)
	public String caCertData();

	@Meta.AD(
		deflt = "dxp.liferay.com/configs=true",
		description = "label-selector-description", name = "label-selector",
		required = false, type = Meta.Type.String
	)
	public String labelSelector();

	@Meta.AD(
		description = "namespace-description", name = "namespace",
		type = Meta.Type.String
	)
	public String namespace();

	@Meta.AD(
		description = "sa-token-description", name = "sa-token",
		type = Meta.Type.String
	)
	public String saToken();

}