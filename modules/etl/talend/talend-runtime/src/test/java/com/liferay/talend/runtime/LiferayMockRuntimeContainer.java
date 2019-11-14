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

package com.liferay.talend.runtime;

import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;

/**
 * @author Igor Beslic
 */
public class LiferayMockRuntimeContainer implements RuntimeContainer {

	public LiferayMockRuntimeContainer(
		ComponentProperties componentProperties) {

		_componentProperties = componentProperties;
	}

	@Override
	public Object getComponentData(String componentId, String key) {
		return _componentProperties;
	}

	@Override
	public String getCurrentComponentId() {
		return _componentProperties.getName();
	}

	@Override
	public Object getGlobalData(String key) {
		return null;
	}

	@Override
	public void setComponentData(String componentId, String key, Object data) {
	}

	private final ComponentProperties _componentProperties;

}