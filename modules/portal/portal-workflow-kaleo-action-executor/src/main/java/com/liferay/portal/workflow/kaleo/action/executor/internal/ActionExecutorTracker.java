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

package com.liferay.portal.workflow.kaleo.action.executor.internal;

import com.liferay.osgi.service.tracker.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.action.executor.ActionExecutor;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = ActionExecutorTracker.class)
public class ActionExecutorTracker {

	public ActionExecutor getActionExecutor(String scriptLanguage)
		throws WorkflowException {

		List<ActionExecutor> actionExecutors = getActionExecutors(
			scriptLanguage);

		return actionExecutors.get(0);
	}

	public ActionExecutor getActionExecutor(
			String scriptLanguage, String filter)
		throws WorkflowException {

		if (Validator.isNull(filter)) {
			throw new WorkflowException("Empty filter is not allowed");
		}

		List<ActionExecutor> actionExecutors = getActionExecutors(
			scriptLanguage);

		for (ActionExecutor actionExecutor : actionExecutors) {
			Class<?> clazz = actionExecutor.getClass();

			String className = clazz.getName();

			if (className.equals(filter.trim())) {
				return actionExecutor;
			}
		}

		throw new WorkflowException("No Action executor found");
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTrackerMap = ServiceTrackerMapFactory.multiValueMap(
			bundleContext, ActionExecutor.class,
			"com.liferay.portal.workflow.kaleo.action.script.language");

		_serviceTrackerMap.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	protected List<ActionExecutor> getActionExecutors(String scriptLanguage)
		throws WorkflowException {

		List<ActionExecutor> actionExecutors = _serviceTrackerMap.getService(
			scriptLanguage);

		if (actionExecutors.isEmpty()) {
			throw new WorkflowException(
				"Invalid script language " + scriptLanguage);
		}

		return actionExecutors;
	}

	private ServiceTrackerMap<String, List<ActionExecutor>> _serviceTrackerMap;

}