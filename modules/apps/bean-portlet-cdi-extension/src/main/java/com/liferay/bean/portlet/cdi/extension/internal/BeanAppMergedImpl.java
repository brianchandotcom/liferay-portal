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

import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Neil Griffin
 */
public class BeanAppMergedImpl implements BeanApp {

	public BeanAppMergedImpl(
		BeanApp annotatedBeanApp, BeanApp descriptorBeanApp) {

		_containerRuntimeOptions = new HashMap<>(
			annotatedBeanApp.getContainerRuntimeOptions());

		_containerRuntimeOptions.putAll(
			descriptorBeanApp.getContainerRuntimeOptions());

		_customPortletModes = new LinkedHashSet<>(
			annotatedBeanApp.getCustomPortletModes());

		_customPortletModes.addAll(descriptorBeanApp.getCustomPortletModes());

		if (Validator.isNull(descriptorBeanApp.getDefaultNamespace())) {
			_defaultNamespace = annotatedBeanApp.getDefaultNamespace();
		}
		else {
			_defaultNamespace = descriptorBeanApp.getDefaultNamespace();
		}

		_events = new ArrayList<>(annotatedBeanApp.getEvents());

		_events.addAll(descriptorBeanApp.getEvents());

		_publicRenderParameterMap = new HashMap<>(
			annotatedBeanApp.getPublicRenderParameters());

		_publicRenderParameterMap.putAll(
			descriptorBeanApp.getPublicRenderParameters());

		if (Validator.isNull(descriptorBeanApp.getSpecVersion())) {
			_specVersion = annotatedBeanApp.getSpecVersion();
		}
		else {
			_specVersion = descriptorBeanApp.getSpecVersion();
		}

		_urlGenerationListeners = new ArrayList<>(
			annotatedBeanApp.getURLGenerationListeners());

		_urlGenerationListeners.addAll(
			descriptorBeanApp.getURLGenerationListeners());
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
		return _publicRenderParameterMap;
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
	private final Map<String, PublicRenderParameter> _publicRenderParameterMap;
	private final String _specVersion;
	private final List<URLGenerationListener> _urlGenerationListeners;

}