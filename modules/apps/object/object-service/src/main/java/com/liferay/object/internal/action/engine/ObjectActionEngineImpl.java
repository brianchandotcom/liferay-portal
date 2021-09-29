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

package com.liferay.object.internal.action.engine;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.action.request.ObjectActionRequest;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ObjectActionEngine.class)
public class ObjectActionEngineImpl implements ObjectActionEngine {

	@Override
	public void executeObjectActions(
		long userId, String className, String objectActionTriggerKey,
		Map<String, Serializable> parameters) {

		try {
			_executeObjectActions(
				userId, className, objectActionTriggerKey, parameters);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectActionExecutor.class, null,
			(serviceReference, emitter) -> {
				ObjectActionExecutor objectActionExecutor =
					bundleContext.getService(serviceReference);

				emitter.emit(objectActionExecutor.getKey());
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private void _executeObjectActions(
			long userId, String className, String objectActionTriggerKey,
			Map<String, Serializable> parameters)
		throws Exception {

		if (userId == 0) {
			return;
		}

		User user = _userLocalService.getUser(userId);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				user.getCompanyId(), className);

		if (objectDefinition != null) {
			return;
		}

		List<ObjectAction> objectActions =
			_objectActionLocalService.getObjectActions(
				objectDefinition.getObjectDefinitionId(),
				objectActionTriggerKey);

		for (ObjectAction objectAction : objectActions) {
			ObjectActionExecutor objectActionExecutor =
				_serviceTrackerMap.getService(
					objectAction.getObjectActionExecutorKey());

			objectActionExecutor.execute(
				new ObjectActionRequest(
					HashMapBuilder.<String, Serializable>putAll(
						objectAction.getParametersUnicodeProperties()
					).putAll(
						parameters
					).build(),
					userId));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectActionEngineImpl.class);

	@Reference
	private ObjectActionLocalService _objectActionLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ServiceTrackerMap<String, ObjectActionExecutor> _serviceTrackerMap;

	@Reference
	private UserLocalService _userLocalService;

}