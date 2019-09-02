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

package com.liferay.portal.template.react.renderer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Chema Balsas
 */
public class ComponentDescriptor {

	public ComponentDescriptor(String module) {
		this(module, null, null, false);
	}

	public ComponentDescriptor(String module, String componentId) {
		this(module, componentId, null, false);
	}

	public ComponentDescriptor(
		String module, String componentId, Collection<String> dependencies) {

		this(module, componentId, dependencies, false);
	}

	public ComponentDescriptor(
		String module, String componentId, Collection<String> dependencies,
		boolean positionInLine) {

		_module = module;
		_componentId = componentId;

		if (dependencies != null) {
			_dependencies.addAll(dependencies);
		}

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

	public boolean isPositionInLine() {
		return _positionInLine;
	}

	private String _componentId;
	private final Set<String> _dependencies = new HashSet<>();
	private String _module;
	private boolean _positionInLine;

}