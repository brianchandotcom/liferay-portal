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

package com.liferay.dynamic.data.mapping.expression.internal.pool;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;

/**
 * @author Rafael Praxedes
 */
public class DDMExpressionFunctionPooledFactory
	extends BasePooledObjectFactory<DDMExpressionFunction> {

	public DDMExpressionFunctionPooledFactory(
		ComponentFactory componentFactory) {

		_componentFactory = componentFactory;
	}

	@Override
	public DDMExpressionFunction create() throws Exception {
		ComponentInstance componentInstance = _componentFactory.newInstance(
			null);

		DDMExpressionFunction expressionFunction =
			(DDMExpressionFunction)componentInstance.getInstance();

		componentInstance.dispose();

		return expressionFunction;
	}

	public ComponentFactory getComponentFactory() {
		return _componentFactory;
	}

	@Override
	public PooledObject<DDMExpressionFunction> wrap(
		DDMExpressionFunction expressionFunction) {

		return new DefaultPooledObject<>(expressionFunction);
	}

	private final ComponentFactory _componentFactory;

}