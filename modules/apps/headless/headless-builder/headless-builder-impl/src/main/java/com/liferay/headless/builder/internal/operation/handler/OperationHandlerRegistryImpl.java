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

package com.liferay.headless.builder.internal.operation.handler;

import com.liferay.headless.builder.internal.constants.HeadlessBuilderConstants;
import com.liferay.headless.builder.operation.Operation;
import com.liferay.headless.builder.operation.handler.OperationHandler;
import com.liferay.headless.builder.operation.handler.OperationHandlerRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Carlos Correa
 */
@Component(service = OperationHandlerRegistry.class)
public class OperationHandlerRegistryImpl implements OperationHandlerRegistry {

	@Override
	public OperationHandler getOperationHandler(Operation operation)
		throws Exception {

		return _operationHandlerServiceTrackerMap.getService(
			operation.getOperationType());
	}

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		_operationHandlerServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, OperationHandler.class,
				HeadlessBuilderConstants.OPERATION_NAME);
	}

	@Deactivate
	protected void deactivate() {
		_operationHandlerServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, OperationHandler>
		_operationHandlerServiceTrackerMap;

}