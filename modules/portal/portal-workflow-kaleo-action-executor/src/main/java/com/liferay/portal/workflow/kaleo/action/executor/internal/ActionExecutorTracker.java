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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.action.executor.ActionExecutor;

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

	public ActionExecutor getActionExecutor(
			String scriptLanguage, String script)
		throws WorkflowException {

		String key = _actionExecutorServiceReferenceMapper.getKey(
			scriptLanguage, StringUtil.trim(script));

		ActionExecutor actionExecutor = _serviceTrackerMap.getService(key);

		if (actionExecutor == null) {
			throw new WorkflowException(
				"No Action executor registered with key " + key);
		}

		return actionExecutor;
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTrackerMap = ServiceTrackerMapFactory.singleValueMap(
			bundleContext, ActionExecutor.class, null,
			_actionExecutorServiceReferenceMapper);

		_serviceTrackerMap.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private final ActionExecutorServiceReferenceMapper
		_actionExecutorServiceReferenceMapper =
			new ActionExecutorServiceReferenceMapper();
	private ServiceTrackerMap<String, ActionExecutor> _serviceTrackerMap;

}