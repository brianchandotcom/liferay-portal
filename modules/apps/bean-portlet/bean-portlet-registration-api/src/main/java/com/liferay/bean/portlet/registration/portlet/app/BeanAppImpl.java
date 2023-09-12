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
public class BeanAppImpl implements BeanApp {

	public BeanAppImpl(
		Map<String, List<String>> containerRuntimeOptions,
		Set<String> customPortletModes, String defaultNamespace,
		List<Event> events, List<Map.Entry<Integer, String>> portletListeners,
		Map<String, PublicRenderParameter> publicRenderParameters,
		String specVersion) {

		_containerRuntimeOptions = containerRuntimeOptions;
		_customPortletModes = customPortletModes;
		_defaultNamespace = defaultNamespace;
		_events = events;
		_portletListeners = portletListeners;
		_publicRenderParameters = publicRenderParameters;
		_specVersion = specVersion;
	}

	@Override
	public Map<String, List<String>> getContainerRuntimeOptions() {
		return _containerRuntimeOptions;
	}

	@Override
	public Set<String> getCustomPortletModes() {
		return _customPortletModes;
	}

	@Override
	public String getDefaultNamespace() {
		return _defaultNamespace;
	}

	@Override
	public List<Event> getEvents() {
		return _events;
	}

	@Override
	public List<Map.Entry<Integer, String>> getPortletListeners() {
		return _portletListeners;
	}

	@Override
	public Map<String, PublicRenderParameter> getPublicRenderParameters() {
		return _publicRenderParameters;
	}

	@Override
	public String getSpecVersion() {
		return _specVersion;
	}

	@Override
	public void setContainerRuntimeOptions(
		Map<String, List<String>> containerRuntimeOptions) {

		_containerRuntimeOptions = containerRuntimeOptions;
	}

	@Override
	public void setCustomPortletModes(Set<String> customPortletModes) {
		_customPortletModes = customPortletModes;
	}

	@Override
	public void setDefaultNamespace(String defaultNamespace) {
		_defaultNamespace = defaultNamespace;
	}

	@Override
	public void setEvents(List<Event> events) {
		_events = events;
	}

	@Override
	public void setPortletListeners(
		List<Map.Entry<Integer, String>> portletListeners) {

		_portletListeners = portletListeners;
	}

	@Override
	public void setPublicRenderParameters(
		Map<String, PublicRenderParameter> publicRenderParameters) {

		_publicRenderParameters = publicRenderParameters;
	}

	@Override
	public void setSpecVersion(String specVersion) {
		_specVersion = specVersion;
	}

	private Map<String, List<String>> _containerRuntimeOptions;
	private Set<String> _customPortletModes;
	private String _defaultNamespace;
	private List<Event> _events;
	private List<Map.Entry<Integer, String>> _portletListeners;
	private Map<String, PublicRenderParameter> _publicRenderParameters;
	private String _specVersion;

}