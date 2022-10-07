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
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Leonardo Barros
 */
@Component(service = DDMExpressionFunctionTracker.class)
public class DDMExpressionFunctionTrackerImpl
	implements DDMExpressionFunctionTracker {

	@Override
	public Map<String, DDMExpressionFunction>
		getCustomDDMExpressionFunctions() {

		Map<String, DDMExpressionFunction> customDDMExpressionFunctions =
			new HashMap<>();

		ServiceTrackerMap<String, DDMExpressionFunctionFactory>
			serviceTrackerMap = _getServiceTrackerMap();

		for (DDMExpressionFunctionFactory ddmExpressionFunctionFactory :
				serviceTrackerMap.values()) {

			DDMExpressionFunction ddmExpressionFunction =
				ddmExpressionFunctionFactory.create();

			if (ddmExpressionFunction.isCustomDDMExpressionFunction()) {
				customDDMExpressionFunctions.put(
					ddmExpressionFunction.getName(), ddmExpressionFunction);
			}
		}

		return customDDMExpressionFunctions;
	}

	@Override
	public Map<String, DDMExpressionFunctionFactory>
		getDDMExpressionFunctionFactories(Set<String> functionNames) {

		Map<String, DDMExpressionFunctionFactory>
			ddmExpressionFunctionFactories = new HashMap<>();

		ServiceTrackerMap<String, DDMExpressionFunctionFactory>
			serviceTrackerMap = _getServiceTrackerMap();

		for (String functionName : functionNames) {
			DDMExpressionFunctionFactory ddmExpressionFunctionFactory =
				serviceTrackerMap.getService(functionName);

			if (ddmExpressionFunctionFactory != null) {
				ddmExpressionFunctionFactories.put(
					functionName, ddmExpressionFunctionFactory);
			}
		}

		return ddmExpressionFunctionFactories;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public Map<String, DDMExpressionFunction> getDDMExpressionFunctions(
		Set<String> functionNames) {

		return Collections.emptyMap();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void ungetDDMExpressionFunctions(
		Map<String, DDMExpressionFunction> ddmExpressionFunctionsMap) {
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();
		}
	}

	private ServiceTrackerMap<String, DDMExpressionFunctionFactory>
		_getServiceTrackerMap() {

		if (_serviceTrackerMap != null) {
			return _serviceTrackerMap;
		}

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, DDMExpressionFunctionFactory.class, "name");

		return _serviceTrackerMap;
	}

	private BundleContext _bundleContext;
	private ServiceTrackerMap<String, DDMExpressionFunctionFactory>
		_serviceTrackerMap;

}