/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.portletconfiguration.util;

import javax.portlet.PortletPreferences;
import javax.portlet.ResourceRequest;
import javax.portlet.filter.ResourceRequestWrapper;

/**
 * @author Raymond Augé
 */
public class ConfigurationResourceRequest
	extends ResourceRequestWrapper implements ConfigurationPortletRequest {

	public ConfigurationResourceRequest(
		ResourceRequest resourceRequest,
		PortletPreferences portletPreferences) {

		super(resourceRequest);

		_portletPreferences = portletPreferences;
	}

	@Override
	public PortletPreferences getPreferences() {
		return _portletPreferences;
	}

	private PortletPreferences _portletPreferences;

}