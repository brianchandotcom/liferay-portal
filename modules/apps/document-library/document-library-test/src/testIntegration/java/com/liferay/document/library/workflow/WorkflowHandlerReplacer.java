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

package com.liferay.document.library.workflow;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class WorkflowHandlerReplacer<T> implements AutoCloseable {

	public WorkflowHandlerReplacer(
		String className, WorkflowHandler<T> replacementWorkflowHandler) {

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"service.ranking", Integer.MAX_VALUE
		).build();

		_serviceRegistration = registry.registerService(
			(Class<WorkflowHandler<?>>)(Class<?>)WorkflowHandler.class,
			replacementWorkflowHandler, properties);
	}

	@Override
	public void close() {
		_serviceRegistration.unregister();
	}

	private final ServiceRegistration<WorkflowHandler<?>> _serviceRegistration;

}