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

package com.liferay.scr.reference.dynamic.greedy.test.internal;

import com.liferay.scr.reference.dynamic.greedy.test.ComponentController;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = ComponentController.class)
public class ComponentControllerImpl implements ComponentController {

	@Override
	public void disableComponent(String name) {
		_componentContext.disableComponent(name);
	}

	@Override
	public void enabledComponent(String name) {
		_componentContext.enableComponent(name);
	}

	@Activate
	protected void activate(ComponentContext componentContext) {
		_componentContext = componentContext;
	}

	private ComponentContext _componentContext;

}