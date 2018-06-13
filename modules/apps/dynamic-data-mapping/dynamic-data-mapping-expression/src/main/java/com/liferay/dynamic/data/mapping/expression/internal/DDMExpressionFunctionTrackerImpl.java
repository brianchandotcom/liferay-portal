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

package com.liferay.dynamic.data.mapping.expression.internal;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true)
public class DDMExpressionFunctionTrackerImpl
	implements DDMExpressionFunctionTracker {

	@Override
	public DDMExpressionFunction getDDMExpressionFunction(String functionName) {
		return ddmExpressionFunctionTrackerServiceTrackerMap.getService(
			functionName);
	}

	@Override
	public Map<String, DDMExpressionFunction> getDDMExpressionFunctions() {
		Set<String> keySet =
			ddmExpressionFunctionTrackerServiceTrackerMap.keySet();

		Stream<String> stream = keySet.stream();

		return stream.collect(
			Collectors.toConcurrentMap(
				Function.identity(),
				key -> ddmExpressionFunctionTrackerServiceTrackerMap.getService(
					key)));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		ddmExpressionFunctionTrackerServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DDMExpressionFunction.class,
				"ddm.form.evaluator.function.name");
	}

	@Deactivate
	protected void deactivate() {
		ddmExpressionFunctionTrackerServiceTrackerMap.close();
	}

	protected ServiceTrackerMap<String, DDMExpressionFunction>
		ddmExpressionFunctionTrackerServiceTrackerMap;

}