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

package com.liferay.portal.workflow.kaleo.runtime.internal.action.executor;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.definition.ActionType;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.action.ActionExecutorManager;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutor;

import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Leonardo Barros
 */
@Component(service = ActionExecutorManager.class)
public class ActionExecutorManagerImpl implements ActionExecutorManager {

	@Override
	public void executeKaleoAction(
			KaleoAction kaleoAction, ExecutionContext executionContext)
		throws PortalException {

		String actionExecutorKey = _getActionExecutorKey(kaleoAction);

		ActionExecutor actionExecutor = _serviceTrackerMap.getService(
			actionExecutorKey);

		if (actionExecutor == null) {
			throw new PortalException(
				"No action executor for " + actionExecutorKey);
		}

		actionExecutor.execute(kaleoAction, executionContext);
	}

	@Override
	public String[] getFunctionActionExecutorKeys() {
		return TransformUtil.transformToArray(
			_serviceTrackerMap.keySet(),
			key -> {
				if (key.startsWith("function")) {
					return key;
				}

				return null;
			},
			String.class);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ActionExecutor.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(actionExecutor, emitter) -> {
					for (String actionExecutorKey :
							actionExecutor.getActionExecutorKeys()) {

						emitter.emit(actionExecutorKey);
					}
				}));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private String _getActionExecutorKey(KaleoAction kaleoAction) {
		ActionType actionType = ActionType.valueOf(kaleoAction.getType());

		if (Objects.equals(actionType, ActionType.UPDATE_STATUS)) {
			return actionType.name();
		}

		return kaleoAction.getScriptLanguage();
	}

	private ServiceTrackerMap<String, ActionExecutor> _serviceTrackerMap;

}