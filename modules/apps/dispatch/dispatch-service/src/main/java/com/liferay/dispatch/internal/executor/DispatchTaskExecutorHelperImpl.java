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

package com.liferay.dispatch.internal.executor;

import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Matija Petanjek
 */
@Component(service = DispatchTaskExecutorHelper.class)
public class DispatchTaskExecutorHelperImpl
	implements DispatchTaskExecutorHelper {

	@Override
	public DispatchTaskExecutor getDispatchTaskExecutor(
		String taskExecutorType) {

		return _typeToDispatchTaskExecutorMap.get(taskExecutorType);
	}

	@Override
	public String getDispatchTaskExecutorName(String taskExecutorType) {
		return _typeToNameMap.get(taskExecutorType);
	}

	@Override
	public Set<String> getDispatchTaskExecutorTypes() {
		return _typeToNameMap.keySet();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDispatchTaskExecutor(
		DispatchTaskExecutor dispatchTaskExecutor,
		Map<String, Object> properties) {

		String type = (String)properties.get(_KEY_DISPATCH_TASK_EXECUTOR_TYPE);

		_typeToNameMap.put(
			type, (String)properties.get(_KEY_DISPATCH_TASK_EXECUTOR_NAME));

		_typeToDispatchTaskExecutorMap.put(type, dispatchTaskExecutor);
	}

	protected void removeDispatchTaskExecutor(
		DispatchTaskExecutor dispatchTaskExecutor,
		Map<String, Object> properties) {

		String type = (String)properties.get(_KEY_DISPATCH_TASK_EXECUTOR_TYPE);

		_typeToNameMap.remove(type);

		_typeToDispatchTaskExecutorMap.remove(type);
	}

	private static final String _KEY_DISPATCH_TASK_EXECUTOR_NAME =
		"dispatch.task.executor.name";

	private static final String _KEY_DISPATCH_TASK_EXECUTOR_TYPE =
		"dispatch.task.executor.type";

	private final Map<String, DispatchTaskExecutor>
		_typeToDispatchTaskExecutorMap = new HashMap<>();
	private final Map<String, String> _typeToNameMap = new HashMap<>();

}