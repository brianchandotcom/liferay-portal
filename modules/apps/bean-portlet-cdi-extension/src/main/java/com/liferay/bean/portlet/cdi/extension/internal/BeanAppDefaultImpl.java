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

package com.liferay.bean.portlet.cdi.extension.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.annotations.PortletApplication;

/**
 * @author Neil Griffin
 */
public class BeanAppDefaultImpl extends BaseBeanAppImpl {

	@Override
	public Map<String, List<String>> getContainerRuntimeOptions() {
		return Collections.emptyMap();
	}

	@Override
	public Set<String> getCustomPortletModes() {
		return Collections.emptySet();
	}

	@Override
	public List<Event> getEvents() {
		return Collections.emptyList();
	}

	@Override
	public Map<String, PublicRenderParameter> getPublicRenderParameterMap() {
		return Collections.emptyMap();
	}

	@Override
	public String getSpecVersion() {
		PortletApplication defaultPortletApplication =
			PortletApplicationFactory.getDefaultPortletApplication();

		return defaultPortletApplication.version();
	}

	@Override
	public List<URLGenerationListener> getURLGenerationListeners() {
		return Collections.emptyList();
	}

}