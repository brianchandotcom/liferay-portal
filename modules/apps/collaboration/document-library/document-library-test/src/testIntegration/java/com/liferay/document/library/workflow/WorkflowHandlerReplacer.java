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

import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

/**
 * @author Adolfo Pérez
 */
public class WorkflowHandlerReplacer<T> implements AutoCloseable {

	public WorkflowHandlerReplacer(
		String className, WorkflowHandler<T> replacementWorkflowHandler) {

		_originalWorkflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(className);
		_replacementWorkflowHandler = replacementWorkflowHandler;

		WorkflowHandlerRegistryUtil.unregister(_originalWorkflowHandler);
		WorkflowHandlerRegistryUtil.register(_replacementWorkflowHandler);
	}

	@Override
	public void close() throws Exception {
		WorkflowHandlerRegistryUtil.unregister(_replacementWorkflowHandler);
		WorkflowHandlerRegistryUtil.register(_originalWorkflowHandler);
	}

	private final WorkflowHandler<T> _originalWorkflowHandler;
	private final WorkflowHandler<T> _replacementWorkflowHandler;

}