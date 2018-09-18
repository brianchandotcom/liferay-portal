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

package com.liferay.bean.portlet.cdi.extension.internal.xml;

import com.liferay.bean.portlet.cdi.extension.internal.BeanApp;
import com.liferay.bean.portlet.cdi.extension.internal.Event;
import com.liferay.bean.portlet.cdi.extension.internal.PublicRenderParameter;
import com.liferay.bean.portlet.cdi.extension.internal.URLGenerationListener;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Neil Griffin
 */
public class BeanAppDescriptorImpl implements BeanApp {

	public BeanAppDescriptorImpl(
		String specVersion, String defaultNamespace, List<Event> events,
		Map<String, PublicRenderParameter> publicRenderParameters,
		Map<String, List<String>> containerRuntimeOptions,
		Set<String> customPortletModes,
		List<URLGenerationListener> urlGenerationListeners) {

		_specVersion = specVersion;
		_defaultNamespace = defaultNamespace;
		_events = events;
		_publicRenderParameters = publicRenderParameters;
		_containerRuntimeOptions = containerRuntimeOptions;
		_customPortletModes = customPortletModes;
		_urlGenerationListeners = urlGenerationListeners;
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
	public Map<String, PublicRenderParameter> getPublicRenderParameters() {
		return _publicRenderParameters;
	}

	@Override
	public String getSpecVersion() {
		return _specVersion;
	}

	@Override
	public List<URLGenerationListener> getURLGenerationListeners() {
		return _urlGenerationListeners;
	}

	private final Map<String, List<String>> _containerRuntimeOptions;
	private final Set<String> _customPortletModes;
	private final String _defaultNamespace;
	private final List<Event> _events;
	private final Map<String, PublicRenderParameter> _publicRenderParameters;
	private final String _specVersion;
	private final List<URLGenerationListener> _urlGenerationListeners;

}