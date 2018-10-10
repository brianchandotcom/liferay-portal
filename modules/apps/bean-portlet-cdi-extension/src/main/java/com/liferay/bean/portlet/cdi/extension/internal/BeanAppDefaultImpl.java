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

/**
 * @author Neil Griffin
 */
public class BeanAppDefaultImpl implements BeanApp {

	@Override
	public Map<String, List<String>> getContainerRuntimeOptions() {
		return Collections.emptyMap();
	}

	@Override
	public Set<String> getCustomPortletModes() {
		return Collections.emptySet();
	}

	@Override
	public String getDefaultNamespace() {
		return null;
	}

	@Override
	public List<Event> getEvents() {
		return Collections.emptyList();
	}

	@Override
	public List<Map.Entry<Integer, String>> getPortletListeners() {
		return Collections.emptyList();
	}

	@Override
	public Map<String, PublicRenderParameter> getPublicRenderParameters() {
		return Collections.emptyMap();
	}

	@Override
	public String getSpecVersion() {
		return "3.0";
	}

}