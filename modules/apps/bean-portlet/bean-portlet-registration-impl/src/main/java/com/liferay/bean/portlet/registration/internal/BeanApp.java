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

package com.liferay.bean.portlet.registration.internal;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Neil Griffin
 */
public interface BeanApp {

	public Map<String, List<String>> getContainerRuntimeOptions();

	public Set<String> getCustomPortletModes();

	public String getDefaultNamespace();

	public List<Event> getEvents();

	public List<Map.Entry<Integer, String>> getPortletListeners();

	public Map<String, PublicRenderParameter> getPublicRenderParameters();

	public String getSpecVersion();

	public void setContainerRuntimeOptions(
		Map<String, List<String>> containerRuntimeOptions);

	public void setCustomPortletModes(Set<String> customPortletModes);

	public void setDefaultNamespace(String defaultNamespace);

	public void setEvents(List<Event> events);

	public void setPortletListeners(
		List<Map.Entry<Integer, String>> portletListeners);

	public void setPublicRenderParameters(
		Map<String, PublicRenderParameter> publicRenderParameters);

	public void setSpecVersion(String specVersion);

}