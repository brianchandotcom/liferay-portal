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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Iván Zaera Avellón
 */
public class ComponentDescriptor {

	public ComponentDescriptor(String templateNamespace, String module) {
		this(templateNamespace, module, null);
	}

	public ComponentDescriptor(
		String templateNamespace, String module,
		Collection<String> dependencies) {

		this(templateNamespace, module, dependencies, null, null, null, null);
	}

	public ComponentDescriptor(
		String templateNamespace, String module,
		Collection<String> dependencies, String id, String componentId,
		Boolean wrapper, Boolean positionInLine) {

		_templateNamespace = templateNamespace;
		_module = module;

		if (dependencies != null) {
			_dependencies.addAll(dependencies);
		}

		if (id != null) {
			_id = id;
		}

		if (componentId != null) {
			_componentId = componentId;
		}

		if (wrapper != null) {
			_wrapper = wrapper;
		}

		if (positionInLine != null) {
			_positionInLine = positionInLine;
		}
	}

	public String getComponentId() {
		return _componentId;
	}

	public Set<String> getDependencies() {
		return _dependencies;
	}

	public String getId() {
		return _id;
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

	public boolean isWrapper() {
		return _wrapper;
	}

	private String _componentId;
	private Set<String> _dependencies = new HashSet<>();
	private String _id;
	private String _module;
	private boolean _positionInLine;
	private String _templateNamespace;
	private boolean _wrapper = true;

}