/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.soy.internal.template;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Iván Zaera Avellón
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
public class ComponentDescriptor {

	public ComponentDescriptor(String templateNamespace, String module) {
		this(templateNamespace, module, null, null, true, true, false);
	}

	public ComponentDescriptor(
		String templateNamespace, String module, String componentId) {

		this(templateNamespace, module, componentId, null, true, true, false);
	}

	public ComponentDescriptor(
		String templateNamespace, String module, String componentId,
		Collection<String> dependencies) {

		this(
			templateNamespace, module, componentId, dependencies, true, true,
			false);
	}

	public ComponentDescriptor(
		String templateNamespace, String module, String componentId,
		Collection<String> dependencies, boolean wrapper,
		boolean renderJavascript, boolean positionInLine) {

		_templateNamespace = templateNamespace;
		_module = module;
		_componentId = componentId;

		if (dependencies != null) {
			_dependencies.addAll(dependencies);
		}

		_wrapper = wrapper;
		_renderJavascript = renderJavascript;
		_positionInLine = positionInLine;
	}

	public String getComponentId() {
		return _componentId;
	}

	public Set<String> getDependencies() {
		return _dependencies;
	}

	public String getModule() {
		return _module;
	}

	public String getTemplateNamespace() {
		return _templateNamespace;
	}

	public boolean isPositionInLine() {
		return _positionInLine;
	}

	public boolean isRenderJavascript() {
		return _renderJavascript;
	}

	public boolean isWrapper() {
		return _wrapper;
	}

	private String _componentId;
	private final Set<String> _dependencies = new HashSet<>();
	private String _module;
	private boolean _positionInLine;
	private boolean _renderJavascript;
	private String _templateNamespace;
	private boolean _wrapper;

}