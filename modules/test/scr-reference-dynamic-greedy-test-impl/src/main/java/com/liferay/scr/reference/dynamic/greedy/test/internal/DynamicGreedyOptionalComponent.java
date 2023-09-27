/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	enabled = false, property = "reference.cardinality=optional",
	service = DynamicGreedyComponent.class
)
public class DynamicGreedyOptionalComponent implements DynamicGreedyComponent {

	@Override
	public List<String> getBindingCalls() {
		return _bindingCalls;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(reference.cardinality=optional)"
	)
	protected void bindOptionalDependency(Object object) {
		_bindingCalls.add("bindOptionalDependency-" + object);
	}

	protected void unbindOptionalDependency(Object object) {
		_bindingCalls.add("unbindOptionalDependency-" + object);
	}

	private final List<String> _bindingCalls = new ArrayList<>();

}