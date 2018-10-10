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

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Neil Griffin
 */
public class BeanAppImpl implements BeanApp {

	public BeanAppImpl(
		String specVersion, String defaultNamespace, List<Event> events,
		Map<String, PublicRenderParameter> publicRenderParameters,
		Map<String, List<String>> containerRuntimeOptions,
		Set<String> customPortletModes,
		List<Map.Entry<Integer, String>> portletListeners) {

		_specVersion = specVersion;
		_defaultNamespace = defaultNamespace;
		_events = events;
		_publicRenderParameters = publicRenderParameters;
		_containerRuntimeOptions = containerRuntimeOptions;
		_customPortletModes = customPortletModes;
		_portletListeners = portletListeners;
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

	private final Map<String, List<String>> _containerRuntimeOptions;
	private final Set<String> _customPortletModes;
	private final String _defaultNamespace;
	private final List<Event> _events;
	private final List<Map.Entry<Integer, String>> _portletListeners;
	private final Map<String, PublicRenderParameter> _publicRenderParameters;
	private final String _specVersion;

}