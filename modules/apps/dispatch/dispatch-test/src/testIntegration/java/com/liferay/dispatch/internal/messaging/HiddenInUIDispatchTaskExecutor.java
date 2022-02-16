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

package com.liferay.dispatch.internal.messaging;

import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;

import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joe Duffy
 */
@Component(
	immediate = true,
	property = {
		"dispatch.task.executor.hidden-in-ui=true",
		"dispatch.task.executor.type=" + HiddenInUIDispatchTaskExecutor.DISPATCH_TASK_EXECUTOR_TYPE_TEST_HIDDEN_IN_UI
	},
	service = DispatchTaskExecutor.class
)
public class HiddenInUIDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	public static final String DISPATCH_TASK_EXECUTOR_TYPE_TEST_HIDDEN_IN_UI =
		"test-hidden-in-ui";

	@Override
	public void doExecute(
		DispatchTrigger dispatchTrigger,
		DispatchTaskExecutorOutput dispatchTaskExecutorOutput) {

		executionCounter.incrementAndGet();
	}

	@Override
	public String getName() {
		return DISPATCH_TASK_EXECUTOR_TYPE_TEST_HIDDEN_IN_UI;
	}

	protected static final AtomicInteger executionCounter = new AtomicInteger(
		0);

}