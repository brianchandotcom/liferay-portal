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

package com.liferay.layout.content.page.editor.web.internal.workflow;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFunction;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;

import java.util.function.Function;

import javax.portlet.ActionRequest;

/**
 * @author Alejandro Tardín
 */
public class WorkflowUtil {

	public static Function<String, ServiceContext> getServiceContextFunction(
		int workflowAction, ActionRequest actionRequest) {

		Function<String, ServiceContext> serviceContextFunction =
			new ServiceContextFunction(actionRequest);

		return serviceContextFunction.andThen(
			serviceContext -> {
				serviceContext.setWorkflowAction(workflowAction);

				return serviceContext;
			});
	}

	public static <E extends Exception> void withoutWorkflow(
			UnsafeRunnable<E> unsafeRunnable)
		throws E {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		WorkflowThreadLocal.setEnabled(false);

		try {
			unsafeRunnable.run();
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

}