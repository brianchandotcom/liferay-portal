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

package com.liferay.portal.dependencymanager.component.executor.factory.internal;

import java.util.concurrent.Executor;

import org.apache.felix.dm.Component;
import org.apache.felix.dm.ComponentExecutorFactory;

/**
 * @author Shuyang Zhou
 */
public class ComponentExecutorFactoryImpl implements ComponentExecutorFactory {

	public ComponentExecutorFactoryImpl(Executor executor) {
		_executor = executor;
	}

	@Override
	public Executor getExecutorFor(Component component) {
		return _executor;
	}

	private final Executor _executor;

}