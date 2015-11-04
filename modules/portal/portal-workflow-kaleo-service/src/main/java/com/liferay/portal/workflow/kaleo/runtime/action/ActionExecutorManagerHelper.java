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

package com.liferay.portal.workflow.kaleo.runtime.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = Object.class)
public class ActionExecutorUtil {

	public static void executeKaleoActions(
			String kaleoClassName, long kaleoClassPK,
			ExecutionType executionType, ExecutionContext executionContext)
		throws PortalException {

		_actionExecutorManager.executeKaleoActions(
			kaleoClassName, kaleoClassPK, executionType, executionContext);
	}

	@Reference(unbind = "-")
	protected void setActionExecutorManager(
		ActionExecutorManager actionExecutorManager) {

		_actionExecutorManager = actionExecutorManager;
	}

	private static ActionExecutorManager _actionExecutorManager;

}