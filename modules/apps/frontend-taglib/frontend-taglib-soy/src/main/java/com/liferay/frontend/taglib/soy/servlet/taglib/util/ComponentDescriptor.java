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

package com.liferay.frontend.taglib.soy.servlet.taglib.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Iván Zaera Avellón
 */
public class ComponentDescriptor {

	public ComponentDescriptor(String templateNamespace, String module) {
		this(templateNamespace, module, null, null);
	}

	public ComponentDescriptor(
		String templateNamespace, String module, String componentId) {

		this(templateNamespace, module, componentId, null);
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

		if (dependencies != null) {
			_dependencies.addAll(dependencies);
		}

		_componentId = componentId;
		_wrapper = wrapper;
		_renderJavascript = renderJavascript;
		_positionInLine = positionInLine;

		_moduleName = _generateModuleName(_module);
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

	public String getModuleName() {
		return _moduleName;
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

	private String _generateModuleName(String module) {
		String moduleName = StringUtil.extractLast(
			module, CharPool.FORWARD_SLASH);

		return StringUtil.strip(moduleName, _UNSAFE_MODULE_NAME_CHARS);
	}

	private static final char[] _UNSAFE_MODULE_NAME_CHARS = {
		CharPool.PERIOD, CharPool.DASH
	};

	private String _componentId;
	private Set<String> _dependencies = new HashSet<>();
	private String _module;
	private String _moduleName;
	private boolean _positionInLine;
	private boolean _renderJavascript;
	private String _templateNamespace;
	private boolean _wrapper;

}