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

import com.liferay.scr.reference.dynamic.greedy.test.DynamicGreedyComponent;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	enabled = false, immediate = true, property = "field.option=replace",
	service = DynamicGreedyComponent.class
)
public class DynamicGreedyFieldReplaceComponent
	implements DynamicGreedyComponent {

	@Override
	public List<String> getBindingCalls() {
		return _bindingCalls;
	}

	@Reference(target = "(field.option=replace)")
	private volatile List<String> _bindingCalls;

}