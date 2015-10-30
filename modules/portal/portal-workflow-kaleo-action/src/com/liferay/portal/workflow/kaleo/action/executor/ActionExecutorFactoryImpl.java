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

package com.liferay.portal.workflow.kaleo.action.executor;

import com.liferay.osgi.service.tracker.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.runtime.action.ActionExecutor;
import com.liferay.portal.workflow.kaleo.runtime.action.ActionExecutorFactory;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true)
public class ActionExecutorFactoryImpl implements ActionExecutorFactory {

	public ActionExecutor getActionExecutor(String scriptLanguage)
		throws WorkflowException {

		List<ServiceWrapper<ActionExecutor>> serviceWrappers =
			getServiceWrappers(scriptLanguage);

		return serviceWrappers.get(0).getService();
	}

	public ActionExecutor getActionExecutor(
			String scriptLanguage, String filter)
		throws WorkflowException {

		List<ServiceWrapper<ActionExecutor>> serviceWrappers =
			getServiceWrappers(scriptLanguage);

		if (Validator.isNull(filter)) {
			return serviceWrappers.get(0).getService();
		}
		else {
			for (ServiceWrapper<ActionExecutor>
				serviceWrapper : serviceWrappers) {

				ActionExecutor actionExecutor = serviceWrapper.getService();

				String className = actionExecutor.getClass().getName();

				if (className.equals(filter)) {
					return actionExecutor;
				}
			}

			throw new WorkflowException("Action executor not found");
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_actionExecutors = ServiceTrackerMapFactory.multiValueMap(
				bundleContext, ActionExecutor.class,
				"com.liferay.portal.workflow.kaleo.action.script.language",
				ServiceTrackerCustomizerFactory.<ActionExecutor>
					serviceWrapper(bundleContext));

		_actionExecutors.open();
	}

	@Deactivate
	protected void deactivate() {
		_actionExecutors.close();
	}

	protected List<ServiceWrapper<ActionExecutor>> getServiceWrappers(
			String scriptLanguage)
		throws WorkflowException {

		List<ServiceWrapper<ActionExecutor>> serviceWrappers =
			_actionExecutors.getService(scriptLanguage);

		if (serviceWrappers.isEmpty()) {
			throw new WorkflowException(
				"Invalid script language " + scriptLanguage);
		}

		return serviceWrappers;
	}

	private ServiceTrackerMap <String, List<ServiceWrapper<ActionExecutor>>>
		_actionExecutors;

}