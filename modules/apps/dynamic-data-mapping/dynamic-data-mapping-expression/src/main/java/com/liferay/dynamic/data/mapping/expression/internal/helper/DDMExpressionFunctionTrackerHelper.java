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

package com.liferay.dynamic.data.mapping.expression.internal.helper;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;

/**
 * @author Rafael Praxedes
 */
public class DDMExpressionFunctionTrackerHelper {

	public void addComponentFactory(ComponentFactory componentFactory) {
		DDMExpressionFunction ddmExpressionFunction =
			_createDDMExpressionFunction(componentFactory);

		ddmExpressionFunctionComponentFactoryMap.put(
			ddmExpressionFunction.getName(), componentFactory);
	}

	public void clear() {
		ddmExpressionFunctionComponentFactoryMap.clear();
	}

	public DDMExpressionFunction getDDMExpressionFunction(String functionName) {
		ComponentFactory componentFactory =
			ddmExpressionFunctionComponentFactoryMap.get(functionName);

		if (componentFactory == null) {
			return null;
		}

		return _createDDMExpressionFunction(componentFactory);
	}

	public void removeComponentFactory(ComponentFactory componentFactory) {
		DDMExpressionFunction ddmExpressionFunction =
			_createDDMExpressionFunction(componentFactory);

		ddmExpressionFunctionComponentFactoryMap.remove(
			ddmExpressionFunction.getName(), componentFactory);
	}

	protected Map<String, ComponentFactory>
		ddmExpressionFunctionComponentFactoryMap = new ConcurrentHashMap<>();

	private DDMExpressionFunction _createDDMExpressionFunction(
		ComponentFactory componentFactory) {

		ComponentInstance componentInstance = componentFactory.newInstance(
			null);

		DDMExpressionFunction ddmExpressionFunction =
			(DDMExpressionFunction)componentInstance.getInstance();

		componentInstance.dispose();

		return ddmExpressionFunction;
	}

}