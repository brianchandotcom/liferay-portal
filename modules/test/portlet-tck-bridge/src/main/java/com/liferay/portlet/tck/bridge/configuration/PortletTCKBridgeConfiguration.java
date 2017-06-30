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

package com.liferay.portlet.tck.bridge.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Shuyang Zhou
 */
@Meta.OCD(
	id = "com.liferay.portlet.tck.bridge.configuration.PortletTCKBridgeConfiguration",
	localization = "content/Language",
	name = "portlet-tck-bridge-configuration-name"
)
public interface PortletTCKBridgeConfiguration {

	@Meta.AD(deflt = "8239", required = false)
	public int handshakeServerPort();

	@Meta.AD(deflt = "", required = false)
	public String[] servletContextNames();

	@Meta.AD(deflt = "1200", required = false)
	public long timeout();

}