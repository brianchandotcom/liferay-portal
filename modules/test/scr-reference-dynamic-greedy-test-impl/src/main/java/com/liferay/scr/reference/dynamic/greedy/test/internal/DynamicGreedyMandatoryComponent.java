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

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Preston Crary
 */
@Component(
	enabled = false, immediate = true,
	property = "reference.cardinality=mandatory",
	service = DynamicGreedyComponent.class
)
public class DynamicGreedyMandatoryComponent implements DynamicGreedyComponent {

	@Override
	public List<String> getBindingCalls() {
		return _bindingCalls;
	}

	@Reference(
		cardinality = ReferenceCardinality.MANDATORY,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(reference.cardinality=mandatory)"
	)
	protected void bindMandatoryDependency(Object object) {
		_bindingCalls.add("bindMandatoryDependency-" + object);
	}

	protected void unbindMandatoryDependency(Object object) {
		_bindingCalls.add("unbindMandatoryDependency-" + object);
	}

	private final List<String> _bindingCalls = new ArrayList<>();

}