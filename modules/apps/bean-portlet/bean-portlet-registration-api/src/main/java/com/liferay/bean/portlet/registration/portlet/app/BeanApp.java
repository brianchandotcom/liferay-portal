/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.registration.portlet.app;

import com.liferay.bean.portlet.registration.portlet.Event;
import com.liferay.bean.portlet.registration.portlet.PublicRenderParameter;

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