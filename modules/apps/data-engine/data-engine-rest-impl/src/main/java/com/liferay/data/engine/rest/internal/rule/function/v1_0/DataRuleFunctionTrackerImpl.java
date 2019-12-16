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

package com.liferay.data.engine.rest.internal.rule.function.v1_0;

import com.liferay.data.engine.rule.function.DataRuleFunction;
import com.liferay.data.engine.rule.function.DataRuleFunctionTracker;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = DataRuleFunctionTracker.class)
public class DataRuleFunctionTrackerImpl implements DataRuleFunctionTracker {

	@Override
	public DataRuleFunction getDataRuleFunction(String name) {
		return _nameDataRuleFunctions.get(name);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDataRuleFunction(
		DataRuleFunction dataRuleFunction, Map<String, Object> properties) {

		String name = MapUtil.getString(
			properties, "data.engine.rule.function.name");

		_nameDataRuleFunctions.put(name, dataRuleFunction);

		List<DataRuleFunction> dataRuleFunctions =
			_typeDataRuleFunctions.getOrDefault(
				MapUtil.getString(properties, "data.engine.rule.function.type"),
				new ArrayList<>());

		dataRuleFunctions.add(dataRuleFunction);

		_typeDataRuleFunctions.put(name, dataRuleFunctions);
	}

	@Deactivate
	protected void deactivate() {
		_nameDataRuleFunctions.clear();
		_typeDataRuleFunctions.clear();
	}

	protected void removeDataRuleFunction(
		DataRuleFunction dataRuleFunction, Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "data.engine.rule.function.type");

		List<DataRuleFunction> dataRuleFunctions = _typeDataRuleFunctions.get(
			type);

		if (dataRuleFunctions != null) {
			dataRuleFunctions.remove(dataRuleFunction);

			if (dataRuleFunctions.isEmpty()) {
				_typeDataRuleFunctions.remove(type);
			}
		}
	}

	private final Map<String, DataRuleFunction> _nameDataRuleFunctions =
		new TreeMap<>();
	private final Map<String, List<DataRuleFunction>> _typeDataRuleFunctions =
		new TreeMap<>();

}