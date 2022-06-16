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

package com.liferay.portal.k8s.agent.internal.constants;

/**
 * @author Raymond Augé
 */
public class PortalK8sConstants {

	public static final String LXC_DXP_METADATA_SUFFIX = "-lxc-dxp-metadata";

	public static final String LXC_EXT_INIT_METADATA_SUFFIX =
		"-lxc-ext-init-metadata";

	public static final String METADATA_TYPE_DXP_VALUE = "dxp";

	public static final String METADATA_TYPE_EXT_INIT_VALUE = "ext-init";

	public static final String METADATA_TYPE_KEY =
		"lxc.liferay.com/metadataType";

	public static final String SERVICE_ID_KEY =
		"ext.lxc.liferay.com/serviceId";

	public static final String VIRTUAL_INSTANCE_ID_KEY =
		"dxp.lxc.liferay.com/virtualInstanceId";

}